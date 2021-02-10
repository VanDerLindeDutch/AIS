package ru.FSPO.AIS.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.FSPO.AIS.dao.*;
import ru.FSPO.AIS.models.BusinessCenter;
import ru.FSPO.AIS.models.Floor;
import ru.FSPO.AIS.models.Placement;
import ru.FSPO.AIS.services.TypeOfDownloadedFile;

import javax.validation.Valid;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;

import static ru.FSPO.AIS.services.DownloadFileService.DownloadFile;


@Controller
@RequestMapping("/main")
public class MainController {

    private final BcLinkDAO bcLinkDAO;
    private final BusinessCenterDAO businessCenterDAO;
    private final FloorDAO floorDAO;
    private final PlacementDAO placementDAO;
    private static final String UPLOAD_DIR = "C:\\Users\\Lamer\\Desktop\\AIS\\src\\main\\webapp\\resources\\uploads\\";
    private static final String UPLOAD_FLOOR_DIR = "C:\\Users\\Lamer\\Desktop\\AIS\\src\\main\\webapp\\resources\\floorsPhotos\\";
    private int ID;
    private UserType USERTYPE;

    @Autowired
    public MainController(BcLinkDAO bcLinkDAO, BusinessCenterDAO businessCenterDAO, FloorDAO floorDAO, PlacementDAO placementDAO) {
        this.bcLinkDAO = bcLinkDAO;
        this.businessCenterDAO = businessCenterDAO;
        this.floorDAO = floorDAO;
        this.placementDAO = placementDAO;
    }

    private void addIdAndUsertype(Model model){
        model.addAttribute("USERTYPE", USERTYPE.getName());
        model.addAttribute("ID", ID);
    }

    @GetMapping("")
    public String getMainWindow(@RequestParam("USERTYPE") String userType, @RequestParam("ID") int id, Model model) {
        model.addAttribute("ID", id);
        model.addAttribute("USERTYPE", userType);
        ID = id;
        USERTYPE = UserType.valueOf(userType);
        model.addAttribute("centers", businessCenterDAO.getAll());
        model.addAttribute("i", 0);
        return "main/main";
    }

    @GetMapping("/{bcID}")
    public String show(@PathVariable("bcID") int id, Model model){
        model.addAttribute("businessCenter", businessCenterDAO.get(id));
        addIdAndUsertype(model);
        return "main/show";
    }

    @GetMapping("/{bcID}/edit")
    public String getChangePage(@PathVariable("bcID") int id, Model model){
        model.addAttribute("businessCenter", businessCenterDAO.get(id));
        addIdAndUsertype(model);
        return "main/change";
    }


    @GetMapping("/addBc")
    public String getBcForm(Model model) {
        addIdAndUsertype(model);
        model.addAttribute("businessCenter", new BusinessCenter());
        return "main/addBcForm";
    }


    @PostMapping("/addBc")
    public String addBc(@RequestParam MultipartFile file, Model model, @ModelAttribute("businessCenter") @Valid BusinessCenter businessCenter, BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            return "main/addBcForm";
        }
        try {
            DownloadFile(file, TypeOfDownloadedFile.IMAGE_BC);
        } catch (IOException e) {
            e.printStackTrace();
        }
        businessCenter.setImagePath(file.getOriginalFilename());
        businessCenter.setBcLinkId(ID);
        businessCenterDAO.save(businessCenter);
        addIdAndUsertype(model);
        return "redirect:/main";
    }

    @PostMapping("/{bcID}/edit")
    public String change(@PathVariable("bcID") int bcID, @ModelAttribute("businessCenter") @Valid BusinessCenter businessCenter, BindingResult bindingResult, Model model,  @RequestParam MultipartFile file){
        if(bindingResult.hasErrors()){
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
        addIdAndUsertype(model);
        return "redirect:/main";
    }

    @PostMapping("/{bcID}/del")
    public String delete(@PathVariable("bcID") int bcId, Model model){
        addIdAndUsertype(model);
        businessCenterDAO.drop(bcId);
        return "redirect:/main";
    }

    @GetMapping("{bcID}/bcLink")
    public String showLink(@PathVariable("bcID") int bcID, Model model){
        addIdAndUsertype(model);
        model.addAttribute("bcID", bcID);
        model.addAttribute("bcLink", bcLinkDAO.get((int) businessCenterDAO.get(bcID).getBcLinkId()));
        return "main/userInfo";
    }

    @GetMapping("{bcID}/floors")
    public String getFloors(@PathVariable("bcID") int bcID, Model model){
        addIdAndUsertype(model);
//        System.out.println(bcLinkDAO.get((int) businessCenterDAO.get(bcID).getBcLinkId()));
        model.addAttribute("floors", floorDAO.getBcsFloors(bcID));
        model.addAttribute("bcID", bcID);
        return "main/floors/floors";
    }

    @GetMapping("{bcID}/floors/new")
    public String getCreateFloorPage(@PathVariable("bcID") int bcID, Model model){
        addIdAndUsertype(model);
        model.addAttribute("bcID", bcID);
        model.addAttribute("floor", new Floor());
        return "main/floors/newFloor";
    }

    @PostMapping("{bcID}/floors/new")
    public String createFloor(@PathVariable("bcID") int bcID, @ModelAttribute("floor") @Valid Floor floor, BindingResult bindingResult ,@RequestParam MultipartFile file ,Model model){
        if(bindingResult.hasErrors()){
            return "main/floors/newFloor";
        }
        try {
            DownloadFile(file, TypeOfDownloadedFile.IMAGE_FLOOR);
        } catch (IOException e) {
            e.printStackTrace();
        }
        addIdAndUsertype(model);
        floor.setBcId(bcID);
        floor.setImagePath(file.getOriginalFilename());
        floorDAO.insert(floor);
        return "redirect:/main/"+bcID+"/floors";
    }

//    @GetMapping("{bcID}/floors/{floorID}")
//    public String getFloorPage(@PathVariable("bcID") int bcID, @PathVariable("floorID") int floorID, Model model){
//        addIdAndUsertype(model);
//        model.addAttribute("floor", floorDAO.get(floorID));
//        return "main/floors/showFloor";
//    }

    @PostMapping("{bcID}/floors/{floorID}/del")
    public String deleteFloor(@PathVariable("bcID") int bcID,@PathVariable("floorID") int floorId, Model model){
        addIdAndUsertype(model);
        floorDAO.delete(floorId);
        return "redirect:/main/" + bcID+ "/floors";
    }

    @GetMapping("{bcID}/floors/{floorID}/placements")
    public String getPlacements(@PathVariable("bcID") int bcID, @PathVariable("floorID") int floorID, Model model){
        model.addAttribute("bcID", bcID);
        model.addAttribute("floorID", floorID);
        System.out.println(placementDAO.getAllforOneFloor(floorID));
        model.addAttribute("placements", placementDAO.getAllforOneFloor(floorID));
        model.addAttribute("floorNumber", floorDAO.get(floorID).getFloorNumber());
        addIdAndUsertype(model);
        return "main/placement/placements";
    }

    @GetMapping("{bcID}/floors/{floorID}/placements/new")
    public String getPlaceCreatePage(@PathVariable("bcID") int bcID, @PathVariable("floorID") int floorID, Model model){
        addIdAndUsertype(model);
        model.addAttribute("placement", new Placement());
        model.addAttribute("bcID", bcID);
        model.addAttribute("floorID", floorID);
        return "main/placement/newPlacement";
    }


    @PostMapping("{bcID}/floors/{floorID}/placements/new")
    public String createPlacement(@PathVariable("bcID") int bcID, @PathVariable("floorID") int floorID, @ModelAttribute("placement") Placement placement, Model model){
        addIdAndUsertype(model);
        placement.setFloorId(floorID);
        model.addAttribute("bcID", bcID);
        model.addAttribute("floorID", floorID);
        placementDAO.insert(placement);
        return "redirect:/main/" + bcID+ "/floors/" + floorID +"/placements";
    }

    @PostMapping("{bcID}/floors/{floorID}/placements/{plID}/del")
    public String deletePlacement(@PathVariable("bcID") int bcID, @PathVariable("floorID") int floorID, @PathVariable("plID") int plID, Model model){
        addIdAndUsertype(model);
        placementDAO.delete(plID);
        return "redirect:/main/" + bcID+ "/floors/" + floorID +"/placements";
    }

}
