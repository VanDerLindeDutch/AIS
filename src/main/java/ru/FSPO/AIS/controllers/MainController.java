package ru.FSPO.AIS.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.FSPO.AIS.dao.*;
import ru.FSPO.AIS.models.*;
import ru.FSPO.AIS.security.SecurityUser;
import ru.FSPO.AIS.services.TimeLib;
import ru.FSPO.AIS.services.TypeOfDownloadedFile;

import javax.validation.Valid;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static ru.FSPO.AIS.services.DownloadFileService.DownloadFile;


@Controller
@RequestMapping("/main")
public class MainController {

    private final Permission permission = Permission.RENT;
    private final BcLinkDAO bcLinkDAO;
    private final BusinessCenterDAO businessCenterDAO;
    private final FloorDAO floorDAO;
    private final PlacementDAO placementDAO;
    private final RentedPlacementDAO rentedPlacementDAO;
    private final RequestToBcLinkDAO requestToBcLinkDAO;
    private final RenterLinkDAO renterLinkDAO;
    private final ServiceDAO serviceDAO;

    @Autowired
    public MainController(BcLinkDAO bcLinkDAO, BusinessCenterDAO businessCenterDAO, FloorDAO floorDAO, PlacementDAO placementDAO, RentedPlacementDAO rentedPlacementDAO, RequestToBcLinkDAO requestToBcLinkDAO, RenterLinkDAO renterLinkDAO, ServiceDAO serviceDAO) {
        this.bcLinkDAO = bcLinkDAO;
        this.businessCenterDAO = businessCenterDAO;
        this.floorDAO = floorDAO;
        this.placementDAO = placementDAO;
        this.rentedPlacementDAO = rentedPlacementDAO;
        this.requestToBcLinkDAO = requestToBcLinkDAO;
        this.renterLinkDAO = renterLinkDAO;
        this.serviceDAO = serviceDAO;
    }


    private void setPresentUser(Model model) {
        if (!(SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken)) {
            SecurityUser securityUser = (SecurityUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if (securityUser.getRole().equals(Role.LANDLORD)) {
                int bcId = (int) securityUser.getId();
                model.addAttribute("numberOfUnreadRequests", requestToBcLinkDAO.getByBcLink(bcId).stream().filter(x -> !x.isCheked()).count());
            }
            addIdAndUsertype(model, securityUser.getId(), securityUser.getRole());
        }

    }

    private void addIdAndUsertype(Model model, long ID, Role role) {
        model.addAttribute("USERTYPE", role.toString());
        model.addAttribute("ID", ID);
    }


    @GetMapping("")
    public String getMainWindow(Model model) {
        setPresentUser(model);
        SecurityUser securityUser = (SecurityUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        model.addAttribute("centers", businessCenterDAO.getAll());
        model.addAttribute("i", 0);
        return "main/main";
    }


    @PreAuthorize("hasAuthority('CreateBC')")
    @GetMapping("/requests")
    public String getRequests(Model model) {
        setPresentUser(model);
        SecurityUser securityUser = (SecurityUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<RequestToBcLink> requests = requestToBcLinkDAO.getByBcLink((int) ((SecurityUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId());
        List<Placement> placements = placementDAO.getRequestedByLandlordID((int) securityUser.getId());
        Map<Placement, RequestToBcLink> map = IntStream.range(0, placements.size()).boxed().collect(Collectors.toMap(placements::get, requests::get));
        map.forEach((key, value) -> value.setTotalAmount(TimeLib.getDifferenceInMonths(value.getStartOfRent(), value.getEndOfRent()) * key.getPrice()));
        SortedSet<Map.Entry<Placement, RequestToBcLink>> sortedSet = new TreeSet<>(new Comparator<Map.Entry<Placement, RequestToBcLink>>() {
            @Override
            public int compare(Map.Entry<Placement, RequestToBcLink> o1, Map.Entry<Placement, RequestToBcLink> o2) {
                if (o1.getValue().getTotalAmount() == o2.getValue().getTotalAmount()) {
                    return 0;
                }
                return o1.getValue().getTotalAmount() > o2.getValue().getTotalAmount() ? -1 : 1;
            }
        });
        sortedSet.addAll(map.entrySet());
        model.addAttribute("map", sortedSet);
        return "landlord/requestsToRent";
    }


    @PreAuthorize("hasAuthority('CreateBC')")
    @GetMapping("/requests/{rID}")
    public String getRequest(@PathVariable("rID") int rID, Model model) {
        setPresentUser(model);
        RequestToBcLink request = requestToBcLinkDAO.getById(rID);
        request.setTotalAmount(TimeLib.getDifferenceInMonths(request.getStartOfRent(), request.getEndOfRent()) * placementDAO.get((int) request.getPlacementId()).getPrice());
        request.setCheked(true);
        requestToBcLinkDAO.update(request);
        model.addAttribute("requestToBcLink", request);
        model.addAttribute("renter", renterLinkDAO.getById((int) request.getRenterId()));
        model.addAttribute("rID", rID);
        return "landlord/request";
    }

    @PreAuthorize("hasAuthority('CreateBC')")
    @PostMapping("/request/{rID}")
    public String acceptRent(@PathVariable("rID") int rID) {
        RequestToBcLink request = requestToBcLinkDAO.getById(rID);
        requestToBcLinkDAO.delete(rID);
        Placement placement = placementDAO.get((int) request.getPlacementId());
        RentedPlacement rentedPlacement = new RentedPlacement();
        rentedPlacement.setRenterId(request.getRenterId());
        rentedPlacement.setPlacementId(request.getPlacementId());
        rentedPlacement.setStartOfRent(request.getStartOfRent());
        rentedPlacement.setEndOfRent(request.getEndOfRent());
        rentedPlacement.setTotalAmount(placement.getPrice() * TimeLib.getDifferenceInMonths(request.getStartOfRent(), request.getEndOfRent()));
        rentedPlacementDAO.insert(rentedPlacement);
        return "redirect:/main";
    }

    @PreAuthorize("hasAuthority('CreateBC')")
    @PostMapping("/requests/drop/{id}")
    public String dropRequest(@PathVariable("id") int id) {
        requestToBcLinkDAO.delete(id);
        return "redirect:/main/requests";
    }

    @GetMapping("/renter/{rID}/rentedPlacements")
    public String getRentedPlacements(@PathVariable("rID") int rID, Model model) {
        setPresentUser(model);
        List<RentedPlacement> rentedPlacements = rentedPlacementDAO.getAllForUser(rID);
        List<Placement> placements = placementDAO.getJoinPlacements(rID);
        rentedPlacements.sort((o1, o2) -> {
            if (o1.getPlacementId() == o2.getPlacementId()) {
                return 0;
            }
            return o1.getPlacementId() > o2.getPlacementId() ? 1 : -1;
        });

        placements.sort((o1, o2) -> {
            if (o1.getPlacementId() == o2.getPlacementId()) {
                return 0;
            }
            return o1.getPlacementId() > o2.getPlacementId() ? 1 : -1;
        });

        Map<Placement, RentedPlacement> map = IntStream.range(0, placements.size()).boxed().collect(Collectors.toMap(placements::get, rentedPlacements::get));
        model.addAttribute("map", map);
        return "renter/RentedPlacements";
    }

    @GetMapping("/{bcID}")
    public String show(@PathVariable("bcID") int id, Model model) {
        setPresentUser(model);
        SecurityUser securityUser = (SecurityUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("businessCenter", businessCenterDAO.get(id));
        System.out.println(businessCenterDAO.get(id).getBcLinkId() == securityUser.getId() && securityUser.getRole().equals(Role.LANDLORD));
        model.addAttribute("isCurrentUser", businessCenterDAO.get(id).getBcLinkId() == securityUser.getId() && securityUser.getRole().equals(Role.LANDLORD));
        return "main/show";
    }

    @PreAuthorize("hasAuthority('CreateBC')")
    @GetMapping("/{bcID}/edit")
    public String getChangePage(@PathVariable("bcID") int id, Model model) {
        setPresentUser(model);
        model.addAttribute("businessCenter", businessCenterDAO.get(id));
        return "main/change";
    }


    @PreAuthorize("hasAuthority('CreateBC')")
    @GetMapping("/addBc")
    public String getBcForm(Model model) {
        setPresentUser(model);
        model.addAttribute("businessCenter", new BusinessCenter());
        return "main/addBcForm";
    }

    @PreAuthorize("hasAuthority('CreateBC')")
    @PostMapping("/addBc")
    public String addBc(@RequestParam MultipartFile file, Model model, @ModelAttribute("businessCenter") @Valid BusinessCenter businessCenter, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "main/addBcForm";
        }
        try {
            DownloadFile(file, TypeOfDownloadedFile.IMAGE_BC);
        } catch (IOException e) {
            e.printStackTrace();
        }
        SecurityUser securityUser = (SecurityUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        businessCenter.setImagePath(file.getOriginalFilename());
        businessCenter.setBcLinkId(securityUser.getId());
        businessCenterDAO.save(businessCenter);
        return "redirect:/main";
    }

    @PreAuthorize("hasAuthority('CreateBC')")
    @PostMapping("/{bcID}/edit")
    public String change(@PathVariable("bcID") int bcID, @ModelAttribute("businessCenter") @Valid BusinessCenter businessCenter, BindingResult bindingResult, Model model, @RequestParam MultipartFile file) {
        if (bindingResult.hasErrors()) {
            return "main/change";
        }
        try {
            DownloadFile(file, TypeOfDownloadedFile.IMAGE_BC);
        } catch (IOException e) {
            e.printStackTrace();
        }
        businessCenter.setImagePath(file.getOriginalFilename());
        businessCenterDAO.update(bcID, businessCenter);
        return "redirect:/main";
    }

    @PreAuthorize("hasAuthority('CreateBC')")
    @PostMapping("/{bcID}/del")
    public String delete(@PathVariable("bcID") int bcId, Model model) {
        try {
            businessCenterDAO.drop(bcId);
        } catch (Exception exception) {
            return "redirect:/main/" + bcId + "/floors";
        }
        return "redirect:/main";
    }


    @GetMapping("{bcID}/bcLink")
    public String showLink(@PathVariable("bcID") int bcID, Model model) {
        setPresentUser(model);
        model.addAttribute("bcID", bcID);
        model.addAttribute("bcLink", bcLinkDAO.get((int) businessCenterDAO.get(bcID).getBcLinkId()));
        return "main/userInfo";
    }

    @GetMapping("{bcID}/floors")
    public String getFloors(@PathVariable("bcID") int bcID, Model model) {
        setPresentUser(model);
        SecurityUser securityUser = (SecurityUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("floors", floorDAO.getBcsFloors(bcID));
        model.addAttribute("bcID", bcID);
        model.addAttribute("isCurrentUser", businessCenterDAO.get(bcID).getBcLinkId() == securityUser.getId() && securityUser.getRole().equals(Role.LANDLORD));
        return "floors/floors";
    }

    @PreAuthorize("hasAuthority('CreateBC')")
    @GetMapping("{bcID}/floors/new")
    public String getCreateFloorPage(@PathVariable("bcID") int bcID, Model model) {
        setPresentUser(model);
        model.addAttribute("bcID", bcID);
        model.addAttribute("floor", new Floor());
        return "floors/newFloor";
    }

    @PreAuthorize("hasAuthority('CreateBC')")
    @PostMapping("{bcID}/floors/new")
    public String createFloor(@PathVariable("bcID") int bcID, @ModelAttribute("floor") @Valid Floor floor, BindingResult bindingResult, @RequestParam MultipartFile file, Model model) {
        if (bindingResult.hasErrors()) {
            return "main/floors/newFloor";
        }
        try {
            DownloadFile(file, TypeOfDownloadedFile.IMAGE_FLOOR);
        } catch (IOException e) {
            e.printStackTrace();
        }
        floor.setBcId(bcID);
        floor.setImagePath(file.getOriginalFilename());
        floorDAO.insert(floor);

        return "redirect:/main/" + bcID + "/floors";
    }

    @PreAuthorize("hasAuthority('CreateBC')")
    @PostMapping("{bcID}/floors/{floorID}/del")
    public String deleteFloor(@PathVariable("bcID") int bcID, @PathVariable("floorID") int floorId, Model model) {
        try {
            floorDAO.delete(floorId);
        } catch (Exception exception) {
            return "redirect:/main/" + bcID + "/floors/" + floorId + "/placements";
        }
        return "redirect:/main/" + bcID + "/floors";
    }


    @GetMapping("{bcID}/floors/{floorID}/placements")
    public String getPlacements(@PathVariable("bcID") int bcID, @PathVariable("floorID") int floorID, Model model) {
        SecurityUser securityUser = (SecurityUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        int userId = (int) securityUser.getId();
        List<Placement> placements = placementDAO.getAllforOneFloor(floorID);
        if (securityUser.getRole().equals(Role.RENTER)) {
            List<Long> requestedPlacementID = requestToBcLinkDAO.getRequestsJoinPlacements(userId, floorID).stream().map(RequestToBcLink::getPlacementId).collect(Collectors.toList());
            model.addAttribute("requestedPlacementsID", requestedPlacementID);
        }
        List<RentedPlacement> rentedPlacements = rentedPlacementDAO.getForFloor(floorID);

        List<Map<String, Object>> list = serviceDAO.getAllForFloor(floorID);
        Map<Placement, List<Service>> placementServices = placements.
                stream()
                .collect(Collectors.toMap(x->x,
                y->new ArrayList<>()));
        for(Map map:list){
            Stream.ofNullable(placementServices)
                    .flatMap(x->x.entrySet().stream())
                    .filter(x->x.getKey().getPlacementId()==((Number) map.get("placement_id")).longValue())
                    .findFirst()
                    .get()
                    .getValue()
                    .add(new Service(((Number) map.get("service_id")).longValue(), (String) map.get("description"))) ;
        }
        model.addAttribute("rentedPlacementsID", rentedPlacements.stream().map(RentedPlacement::getPlacementId).collect(Collectors.toList()));
        model.addAttribute("rentedPlacements", rentedPlacements);
        model.addAttribute("bcID", bcID);
        model.addAttribute("floorID", floorID);
        model.addAttribute("isCurrentUser", businessCenterDAO.get(bcID).getBcLinkId() == securityUser.getId() && securityUser.getRole().equals(Role.LANDLORD));
        model.addAttribute("placements", placementServices);
        model.addAttribute("floorNumber", floorDAO.get(floorID).getFloorNumber());
        setPresentUser(model);
        return "placement/placements";
    }

    @PreAuthorize("hasAuthority('CreateBC')")
    @GetMapping("{bcID}/floors/{floorID}/placements/new")
    public String getPlaceCreatePage(@PathVariable("bcID") int bcID, @PathVariable("floorID") int floorID, Model model) {
        setPresentUser(model);
        model.addAttribute("placement", new Placement());
        model.addAttribute("bcID", bcID);
        model.addAttribute("services", serviceDAO.getAll());
        model.addAttribute("floorID", floorID);
        return "placement/newPlacement";
    }

    @PreAuthorize("hasAuthority('Rent')")
    @GetMapping("placements/{plID}/rent")
    public String getPlacementRentPage(@PathVariable("plID") int plID, Model model) {
        setPresentUser(model);
        model.addAttribute("requestToBcLink", new RequestToBcLink());
        model.addAttribute("plID", plID);
        model.addAttribute("price", placementDAO.get(plID).getPrice());
        model.addAttribute("areDatesCorrect", true);
        return "main/rentPlacement";
    }

    @PreAuthorize("hasAuthority('Rent')")
    @PostMapping("placements/{plID}/rent")
    public String createRentPlacement(Model model, @PathVariable("plID") int plID, @RequestParam String startOfRent, @RequestParam String endOfRent) {

        try {
            RequestToBcLink requestToBcLink = new RequestToBcLink();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM");
            Date dateStartOfRent = simpleDateFormat.parse(startOfRent);
            Date dateEndOfRent = simpleDateFormat.parse(endOfRent);
            if (dateEndOfRent.before(dateStartOfRent)) {
                model.addAttribute("areDatesCorrect", false);
                return "main/rentPlacement";
            }
            SecurityUser securityUser = (SecurityUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.MONTH, 1);
            requestToBcLink.setStartOfRent(dateStartOfRent);
            requestToBcLink.setEndOfRent(dateEndOfRent);
            requestToBcLink.setExpirationDate(new Date(calendar.getTimeInMillis()));
            requestToBcLink.setRenterId(securityUser.getId());
            requestToBcLink.setPlacementId(plID);
            requestToBcLinkDAO.insert(requestToBcLink);
        } catch (ParseException e) {
            e.printStackTrace();
        }


        return "redirect:/main";
    }


    @PreAuthorize("hasAuthority('CreateBC')")
    @PostMapping("{bcID}/floors/{floorID}/placements/new")
    public String createPlacement(@PathVariable("bcID") int bcID, @PathVariable("floorID") int floorID, @ModelAttribute("placement") Placement placement, Model model, @RequestParam(name = "checkbox", required = false) String string, @RequestParam MultipartFile file) {
        placement.setFloorId(floorID);
        model.addAttribute("bcID", bcID);
        model.addAttribute("floorID", floorID);
        try {
            DownloadFile(file, TypeOfDownloadedFile.IMAGE_PLACEMENT);
            placement.setImagePath(file.getOriginalFilename());
        } catch (IOException e) {
            e.printStackTrace();
        }
        int plId = placementDAO.insert(placement);
        if (string != null) {
            List<Integer> list = Arrays.stream(string.split(",")).map(Integer::parseInt).collect(Collectors.toList());
            list.forEach(x -> serviceDAO.insertServiceToPlacement(plId, x));
        }

        return "redirect:/main/" + bcID + "/floors/" + floorID + "/placements";
    }

    @PreAuthorize("hasAuthority('CreateBC')")
    @PostMapping("{bcID}/floors/{floorID}/placements/{plID}/del")
    public String deletePlacement(@PathVariable("bcID") int bcID, @PathVariable("floorID") int floorID, @PathVariable("plID") int plID, Model model) {
        placementDAO.delete(plID);
        return "redirect:/main/" + bcID + "/floors/" + floorID + "/placements";
    }


    @GetMapping("/servPlacements")
    public String getServicesPlacementsPage(Model model){
        setPresentUser(model);
        model.addAttribute("services", serviceDAO.getAll());
        return "main/placementsServices";
    }

    @PostMapping("/servPlacements")
    public String group(Model model,  @RequestParam(name = "checkbox", required = false) String string){
        setPresentUser(model);
        model.addAttribute("services", serviceDAO.getAll());
        if (string != null) {

            Map<Placement, List<Service>> placements = placementDAO.getJoinServices(Arrays.stream(string.split(","))
                            .map(Integer::parseInt).toArray(Integer[]::new));
            System.out.println(placements);
            model.addAttribute("placements", placements);
            model.addAttribute("requestedPlacementsID", requestToBcLinkDAO.getAll());
            model.addAttribute("rentedPlacements", rentedPlacementDAO.getAll());
            System.out.println(requestToBcLinkDAO.getAll());
            System.out.println(rentedPlacementDAO.getAllJoinPlacement());
            model.addAttribute("rentedPlacementsID", rentedPlacementDAO.getAllJoinPlacement());
        }
        return "main/placementsServices";
    }

}
