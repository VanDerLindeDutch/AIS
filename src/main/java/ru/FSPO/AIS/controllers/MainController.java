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
import ru.FSPO.AIS.newmodels.Permission;
import ru.FSPO.AIS.newmodels.Role;
import ru.FSPO.AIS.newdao.*;
import ru.FSPO.AIS.newmodels.BusinessCenter;
import ru.FSPO.AIS.newmodels.Floor;
import ru.FSPO.AIS.newmodels.Placement;
import ru.FSPO.AIS.security.SecurityUser;
import ru.FSPO.AIS.services.TypeOfDownloadedFile;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.Valid;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import static ru.FSPO.AIS.services.DownloadFileService.DownloadFile;


@Controller
@RequestMapping("/main")
public class MainController {

    private final Permission permission = Permission.RENT;
    private final ServiceRepository serviceRepository;
    private final RentedPlacementsRepository rentedPlacementsRepository;
    private final PlacementRepository placementRepository;
    private final FloorRepository floorRepository;
    private final BcLinkRepository bcLinkRepository;
    private final BusinessCenterRepository businessCenterRepository;
    private final RequestToBcLinkRepository requestToBcLinkRepository;

    @Autowired
    public MainController(ServiceRepository serviceRepository, RentedPlacementsRepository rentedPlacementsRepository, PlacementRepository placementRepository, FloorRepository floorRepository, BcLinkRepository bcLinkRepository, BusinessCenterRepository businessCenterRepository, RequestToBcLinkRepository requestToBcLinkRepository) {
        this.serviceRepository = serviceRepository;
        this.rentedPlacementsRepository = rentedPlacementsRepository;
        this.placementRepository = placementRepository;
        this.floorRepository = floorRepository;
        this.bcLinkRepository = bcLinkRepository;
        this.businessCenterRepository = businessCenterRepository;
        this.requestToBcLinkRepository = requestToBcLinkRepository;
    }


    private void setPresentUser(Model model) {
        if (!(SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken)) {
            SecurityUser securityUser = (SecurityUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if (securityUser.getRole().equals(Role.LANDLORD)) {
                long bcId =  securityUser.getId();
                model.addAttribute("numberOfUnreadRequests", requestToBcLinkRepository.countRequestToBcLinkByBcLink(bcId));
                System.out.println(requestToBcLinkRepository.countRequestToBcLinkByBcLink(bcId));
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

        model.addAttribute("centers", businessCenterRepository.findAll());
        model.addAttribute("i", 0);
        return "main/main";
    }


    @PreAuthorize("hasAuthority('CreateBC')")
    @GetMapping("/requests")
    public String getRequests(Model model) {
        setPresentUser(model);
        SecurityUser securityUser = (SecurityUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Set<ru.FSPO.AIS.newmodels.RequestToBcLink> requests = requestToBcLinkRepository.findRequestsToBcLinkByBcLink(new ru.FSPO.AIS.newmodels.BcLink(securityUser.getId()));

        model.addAttribute("requests", requests);
        return "landlord/requestsToRent";
    }


    @PreAuthorize("hasAuthority('CreateBC')")
    @GetMapping("/requests/{rID}")
    public String getRequest(@PathVariable("rID") long rID, Model model) {
        setPresentUser(model);
        ru.FSPO.AIS.newmodels.RequestToBcLink request = requestToBcLinkRepository.findById(rID).get();
        request.setCheked(true);
        requestToBcLinkRepository.save(request);
        model.addAttribute("requestToBcLink", request);
        model.addAttribute("rID", rID);
        return "landlord/request";
    }

    @PreAuthorize("hasAuthority('CreateBC')")
    @PostMapping("/request/{rID}")
    public String acceptRent(@PathVariable("rID") long rID) {
        ru.FSPO.AIS.newmodels.RequestToBcLink request = requestToBcLinkRepository.findById(rID).get();
        requestToBcLinkRepository.deleteById(rID);
        ru.FSPO.AIS.newmodels.RentedPlacement rentedPlacement = new ru.FSPO.AIS.newmodels.RentedPlacement();
        rentedPlacement.setRenterLink(request.getRenterLink());
        rentedPlacement.setPlacement(request.getPlacement());
        rentedPlacement.setPlacementId(request.getPlacement().getPlacementId());
        rentedPlacement.setStartOfRent(request.getStartOfRent());
        rentedPlacement.setEndOfRent(request.getEndOfRent());
        rentedPlacement.setTotalAmount(request.getTotalAmount());
        rentedPlacementsRepository.save(rentedPlacement);
        return "redirect:/main";
    }

    @PreAuthorize("hasAuthority('CreateBC')")
    @PostMapping("/requests/drop/{id}")
    public String dropRequest(@PathVariable("id") long id) {
        requestToBcLinkRepository.deleteById(id);
        return "redirect:/main/requests";
    }

    @GetMapping("/renter/{rID}/rentedPlacements")
    public String getRentedPlacements(@PathVariable("rID") long rID, Model model) {
        setPresentUser(model);
        List<ru.FSPO.AIS.newmodels.RentedPlacement> rentedPlacements = rentedPlacementsRepository.findRentedPlacementsByRenterLink(new ru.FSPO.AIS.newmodels.RenterLink(rID));
        model.addAttribute("rPlacements", rentedPlacements);
        return "renter/RentedPlacements";
    }

    @GetMapping("/{bcID}")
    public String show(@PathVariable("bcID") long id, Model model) {
        setPresentUser(model);
        SecurityUser securityUser = (SecurityUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("businessCenter", businessCenterRepository.findById(id).get());
        model.addAttribute("isCurrentUser", businessCenterRepository.findById(id).get().getBcLink().getId() == securityUser.getId() && securityUser.getRole().equals(Role.LANDLORD));
        return "main/show";
    }

    @PreAuthorize("hasAuthority('CreateBC')")
    @GetMapping("/{bcID}/edit")
    public String getChangePage(@PathVariable("bcID") long id, Model model) {
        setPresentUser(model);
        model.addAttribute("businessCenter", businessCenterRepository.findById(id).get());
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
        businessCenter.setBcLink(new ru.FSPO.AIS.newmodels.BcLink(securityUser.getId()));
        businessCenterRepository.save(businessCenter);
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
        businessCenter.setBcId((long) bcID);
        businessCenterRepository.save(businessCenter);
        return "redirect:/main";
    }

    @PreAuthorize("hasAuthority('CreateBC')")
    @PostMapping("/{bcID}/del")
    public String delete(@PathVariable("bcID") long bcId, Model model) {
        try {
            businessCenterRepository.delete(new BusinessCenter(bcId));
        } catch (Exception exception) {
            return "redirect:/main/" + bcId + "/floors";
        }
        return "redirect:/main";
    }


    @GetMapping("{bcID}/bcLink")
    public String showLink(@PathVariable("bcID") long bcID, Model model) {
        setPresentUser(model);
        model.addAttribute("bcID", bcID);
        model.addAttribute("bcLink", bcLinkRepository.findBcLinkByBusinessCenters(new BusinessCenter(bcID)));
        return "main/userInfo";
    }

    @GetMapping("{bcID}/floors")
    public String getFloors(@PathVariable("bcID") long bcID, Model model) {
        setPresentUser(model);
        SecurityUser securityUser = (SecurityUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("floors", floorRepository.findFloorByBusinessCenterBcId(bcID));
        System.out.println(floorRepository.findFloorByBusinessCenterBcId(bcID));
        model.addAttribute("bcID", bcID);
        model.addAttribute("isCurrentUser", businessCenterRepository.
                findById(bcID).get(). //no need to check
                getBcLink().getId() == securityUser.getId()
                &&
                securityUser.getRole().equals(Role.LANDLORD));
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
    public String createFloor(@PathVariable("bcID") long bcID, @ModelAttribute("floor") @Valid Floor floor, BindingResult bindingResult, @RequestParam MultipartFile file, Model model) {
        if (bindingResult.hasErrors()) {
            return "main/floors/newFloor";
        }
        try {
            DownloadFile(file, TypeOfDownloadedFile.IMAGE_FLOOR);
        } catch (IOException e) {
            e.printStackTrace();
        }
        floor.setBusinessCenter(new BusinessCenter(bcID));
        floor.setImagePath(file.getOriginalFilename());
        floorRepository.save(floor);

        return "redirect:/main/" + bcID + "/floors";
    }

    @PreAuthorize("hasAuthority('CreateBC')")
    @PostMapping("{bcID}/floors/{floorID}/del")
    public String deleteFloor(@PathVariable("bcID") long bcID, @PathVariable("floorID") long floorId, Model model) {
        try {
            floorRepository.deleteById(floorId);
        } catch (Exception exception) {
            return "redirect:/main/" + bcID + "/floors/" + floorId + "/placements";
        }
        return "redirect:/main/" + bcID + "/floors";
    }

    @PersistenceContext
    private EntityManager entityManager;

    @GetMapping("{bcID}/floors/{floorID}/placements")
    public String getPlacements(@PathVariable("bcID") long bcID, @PathVariable("floorID") long floorID, Model model) {

        SecurityUser securityUser = (SecurityUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();



        entityManager.clear();
        Set<Placement> placements = placementRepository.findPlacementsByFloorId( floorID);
        if (securityUser.getRole().equals(Role.RENTER)) {
            final Long renterId = securityUser.getId();
            System.out.println(renterId);
            placements.stream()
                    .filter(x->x.getRequestsSet().size()!=0)
                    .filter(x->x.getRequestsSet().stream()
                            .noneMatch(y->y
                                    .getRenterLink()
                                    .getId()
                                    .equals(renterId)))
                    .forEach(x->x.setRequestsSet(Collections.emptySet()));
        }
        model.addAttribute("placements", placements);
        model.addAttribute("bcID", bcID);
        model.addAttribute("floorID", floorID);
        model.addAttribute("isCurrentUser", businessCenterRepository.findById(bcID).get().getBcLink().getId() == securityUser.getId() && securityUser.getRole().equals(Role.LANDLORD));
        model.addAttribute("floorNumber", floorRepository.findById(floorID).get().getFloorNumber());
        setPresentUser(model);
        return "placement/placements";
    }

    @PreAuthorize("hasAuthority('CreateBC')")
    @GetMapping("{bcID}/floors/{floorID}/placements/new")
    public String getPlaceCreatePage(@PathVariable("bcID") int bcID, @PathVariable("floorID") int floorID, Model model) {
        setPresentUser(model);
        model.addAttribute("placement", new Placement());
        model.addAttribute("bcID", bcID);
        model.addAttribute("services", serviceRepository.findAll());
        model.addAttribute("floorID", floorID);
        return "placement/newPlacement";
    }

    @PreAuthorize("hasAuthority('Rent')")
    @GetMapping("placements/{plID}/rent")
    public String getPlacementRentPage(@PathVariable("plID") long plID, Model model) {
        setPresentUser(model);
        ru.FSPO.AIS.newmodels.RequestToBcLink request = new ru.FSPO.AIS.newmodels.RequestToBcLink();
        model.addAttribute("requestToBcLink", request);
        model.addAttribute("plID", plID);
        model.addAttribute("price", placementRepository.findById(plID).get().getPrice());
        model.addAttribute("areDatesCorrect", true);
        return "main/rentPlacement";
    }

    @PreAuthorize("hasAuthority('Rent')")
    @PostMapping("placements/{plID}/rent")
    public String createRentPlacement(Model model, @PathVariable("plID") long plID, @RequestParam String startOfRent, @RequestParam String endOfRent, @RequestParam long totalAmount) {

        try {
            ru.FSPO.AIS.newmodels.RequestToBcLink requestToBcLink = new ru.FSPO.AIS.newmodels.RequestToBcLink();
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
            requestToBcLink.setTotalAmount(totalAmount);
            requestToBcLink.setStartOfRent(dateStartOfRent);
            requestToBcLink.setEndOfRent(dateEndOfRent);
            requestToBcLink.setExpirationDate(new Date(calendar.getTimeInMillis()));
            requestToBcLink.setRenterLink(new ru.FSPO.AIS.newmodels.RenterLink(securityUser.getId()));
            requestToBcLink.setBcLink(new ru.FSPO.AIS.newmodels.BcLink(bcLinkRepository.findBcLinkByPlacementID(plID).get()));
            requestToBcLink.setPlacement(new Placement(plID));
            requestToBcLinkRepository.save(requestToBcLink);
        } catch (ParseException e) {
            e.printStackTrace();
        }


        return "redirect:/main";
    }


    @PreAuthorize("hasAuthority('CreateBC')")
    @PostMapping("{bcID}/floors/{floorID}/placements/new")
    public String createPlacement(@PathVariable("bcID") int bcID, @PathVariable("floorID") long floorID, @ModelAttribute("placement") Placement placement, Model model, @RequestParam(name = "checkbox", required = false) String string, @RequestParam MultipartFile file) {
        placement.setFloor(new Floor(floorID));
        model.addAttribute("bcID", bcID);
        model.addAttribute("floorID", floorID);
        try {
            DownloadFile(file, TypeOfDownloadedFile.IMAGE_PLACEMENT);
            placement.setImagePath(file.getOriginalFilename());
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (string != null) {
            List<Integer> list = Arrays.stream(string.split(",")).map(Integer::parseInt).collect(Collectors.toList());
            Set<ru.FSPO.AIS.newmodels.Service> set = list.stream().map(
                    x -> new ru.FSPO.AIS.newmodels.Service(x.longValue()))
                    .collect(Collectors.toSet());
            placement.setServiceSet(set);
        }
        placementRepository.save(placement);


        return "redirect:/main/" + bcID + "/floors/" + floorID + "/placements";
    }

    @PreAuthorize("hasAuthority('CreateBC')")
    @PostMapping("{bcID}/floors/{floorID}/placements/{plID}/del")
    public String deletePlacement(@PathVariable("bcID") int bcID, @PathVariable("floorID") int floorID, @PathVariable("plID") long plID, Model model) {
        placementRepository.delete(new Placement(plID));
        return "redirect:/main/" + bcID + "/floors/" + floorID + "/placements";
    }


    @GetMapping("/servPlacements")
    public String getServicesPlacementsPage(Model model){
        setPresentUser(model);
        model.addAttribute("services", serviceRepository.findAll());
        return "main/placementsServices";
    }

    @PostMapping("/servPlacements")
    public String group(Model model,  @RequestParam(name = "checkbox", required = false) String string){
        setPresentUser(model);
        model.addAttribute("services", serviceRepository.findAll());
        if (string != null) {
            SecurityUser securityUser = (SecurityUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            final Set<ru.FSPO.AIS.newmodels.Service> services = Arrays.stream(string.split(","))
                    .map(x->new ru.FSPO.AIS.newmodels.Service(Long.valueOf(x)))
                    .collect(Collectors.toSet());

            List<Placement> placements = placementRepository.findPlacementsByServiceSetContains(entityManager, services);
            placements = placements.stream()
                    .filter(
                            x->x.getServiceSet().containsAll(services)).collect(Collectors.toList());
            if (securityUser.getRole().equals(Role.RENTER)) {
                final Long renterId = securityUser.getId();
                System.out.println(renterId);
                placements.stream()
                        .filter(x->x.getRequestsSet().size()!=0)
                        .filter(x->x.getRequestsSet().stream()
                                .noneMatch(y->y
                                        .getRenterLink()
                                        .getId()
                                        .equals(renterId)))
                        .forEach(x->x.setRequestsSet(Collections.emptySet()));
            }
            model.addAttribute("placements", placements);
        }
        else {

        }
        return "main/placementsServices";
    }

}
