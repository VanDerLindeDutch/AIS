package ru.FSPO.AIS.controllers;

import org.joda.time.DateTime;
import org.joda.time.Months;
import org.joda.time.Seconds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.FSPO.AIS.dao.*;
import ru.FSPO.AIS.models.*;
import ru.FSPO.AIS.security.SecurityUser;
import ru.FSPO.AIS.services.TypeOfDownloadedFile;

import javax.validation.Valid;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

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
    private static final String UPLOAD_DIR = "C:\\Users\\Lamer\\Desktop\\AIS\\src\\main\\webapp\\resources\\uploads\\";
    private static final String UPLOAD_FLOOR_DIR = "C:\\Users\\Lamer\\Desktop\\AIS\\src\\main\\webapp\\resources\\floorsPhotos\\";

    @Autowired
    public MainController(BcLinkDAO bcLinkDAO, BusinessCenterDAO businessCenterDAO, FloorDAO floorDAO, PlacementDAO placementDAO, RentedPlacementDAO rentedPlacementDAO, RequestToBcLinkDAO requestToBcLinkDAO) {
        this.bcLinkDAO = bcLinkDAO;
        this.businessCenterDAO = businessCenterDAO;
        this.floorDAO = floorDAO;
        this.placementDAO = placementDAO;
        this.rentedPlacementDAO = rentedPlacementDAO;
        this.requestToBcLinkDAO = requestToBcLinkDAO;
    }


    private void setPresentUser(Model model) {
        if (!(SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken)) {
            SecurityUser securityUser = (SecurityUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
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
        model.addAttribute("centers", businessCenterDAO.getAll());
        model.addAttribute("i", 0);
        return "main/main";
    }

    @GetMapping("/renter/{rID}/rentedPlacements")
    public String getRentedPlacements(@PathVariable("rID") int rID, Model model) {
        setPresentUser(model);
        model.addAttribute("rentedPlacements", rentedPlacementDAO.getAllForUser(rID));
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
        String imagePath = file.getOriginalFilename();
        Path path = Paths.get(UPLOAD_DIR + StringUtils.cleanPath(Objects.requireNonNull(imagePath)));
        try {
            Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
        businessCenter.setImagePath(imagePath);
        businessCenterDAO.update(bcID, businessCenter);
        return "redirect:/main";
    }

    @PreAuthorize("hasAuthority('CreateBC')")
    @PostMapping("/{bcID}/del")
    public String delete(@PathVariable("bcID") int bcId, Model model) {
        businessCenterDAO.drop(bcId);
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
        floorDAO.delete(floorId);
        return "redirect:/main/" + bcID + "/floors";
    }


    @GetMapping("{bcID}/floors/{floorID}/placements")
    public String getPlacements(@PathVariable("bcID") int bcID, @PathVariable("floorID") int floorID, Model model) {
        SecurityUser securityUser = (SecurityUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("bcID", bcID);
        model.addAttribute("floorID", floorID);
        model.addAttribute("isCurrentUser", businessCenterDAO.get(bcID).getBcLinkId() == securityUser.getId() && securityUser.getRole().equals(Role.LANDLORD));
//        System.out.println(businessCenterDAO.get(bcID).getBcLinkId() == ID && USERTYPE.equals(UserType.LANDLORD));
        model.addAttribute("placements", placementDAO.getAllforOneFloor(floorID));
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
        model.addAttribute("floorID", floorID);
        return "placement/newPlacement";
    }

    @PreAuthorize("hasAuthority('Rent')")
    @GetMapping("{bcID}/floors/{floorID}/placements/{plID}/rent")
    public String getPlacementRentPage(@PathVariable("plID") int plID, @PathVariable("bcID") int bcID, @PathVariable("floorID") int floorID, Model model) {
        setPresentUser(model);
        model.addAttribute("requestToBcLink", new RequestToBcLink());
        model.addAttribute("plID", plID);
        model.addAttribute("bcID", bcID);
        model.addAttribute("floorID", floorID);
        model.addAttribute("areDatesCorrect", true);
        return "main/rentPlacement";
    }

    @PreAuthorize("hasAuthority('Rent')")
    @PostMapping("{bcID}/floors/{floorID}/placements/{plID}/rent")
    public String createRentPlacement(Model model, @PathVariable("plID") int plID, @PathVariable("bcID") int bcID, @PathVariable("floorID") int floorID, @RequestParam String startOfRent, @RequestParam String endOfRent) {

        try {
            RequestToBcLink requestToBcLink = new RequestToBcLink();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM");
            Date dateStartOfRent = simpleDateFormat.parse(startOfRent);
            Date dateEndOfRent = simpleDateFormat.parse(endOfRent);
            if(dateEndOfRent.before(dateStartOfRent)){
                model.addAttribute("areDatesCorrect", false);
                return "main/rentPlacement";
            }
            SecurityUser securityUser = (SecurityUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.MONTH,1);
            requestToBcLink.setStartOfRent(dateStartOfRent);
            requestToBcLink.setEndOfRent(dateEndOfRent);
            requestToBcLink.setExpirationDate(new Date(calendar.getTimeInMillis()));
            requestToBcLink.setRenterId(securityUser.getId());
            requestToBcLink.setPlacementId(plID);
            requestToBcLinkDAO.insert(requestToBcLink);
            model.addAttribute("bcID", bcID);
            model.addAttribute("floorID", floorID);
        } catch (ParseException e) {
            e.printStackTrace();
        }


        return "redirect:/main/" + bcID + "/floors/" + floorID + "/placements";
    }


    @PreAuthorize("hasAuthority('CreateBC')")
    @PostMapping("{bcID}/floors/{floorID}/placements/new")
    public String createPlacement(@PathVariable("bcID") int bcID, @PathVariable("floorID") int floorID, @ModelAttribute("placement") Placement placement, Model model) {
        placement.setFloorId(floorID);
        model.addAttribute("bcID", bcID);
        model.addAttribute("floorID", floorID);
        placementDAO.insert(placement);
        return "redirect:/main/" + bcID + "/floors/" + floorID + "/placements";
    }

    @PreAuthorize("hasAuthority('CreateBC')")
    @PostMapping("{bcID}/floors/{floorID}/placements/{plID}/del")
    public String deletePlacement(@PathVariable("bcID") int bcID, @PathVariable("floorID") int floorID, @PathVariable("plID") int plID, Model model) {
        placementDAO.delete(plID);
        return "redirect:/main/" + bcID + "/floors/" + floorID + "/placements";
    }

}
