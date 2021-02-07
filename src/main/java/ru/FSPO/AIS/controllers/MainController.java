package ru.FSPO.AIS.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.FSPO.AIS.dao.BusinessCenterDAO;
import ru.FSPO.AIS.dao.UserType;
import ru.FSPO.AIS.models.BusinessCenter;

import javax.validation.Valid;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Objects;


@Controller
@RequestMapping("/main")
public class MainController {


    private final BusinessCenterDAO businessCenterDAO;
    private static final String UPLOAD_DIR = "C:\\Users\\Lamer\\Desktop\\AIS\\src\\main\\webapp\\resources\\uploads\\";
    private int ID;
    private UserType USERTYPE;

    @Autowired
    public MainController(BusinessCenterDAO businessCenterDAO) {
        this.businessCenterDAO = businessCenterDAO;
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
        String imagePath = file.getOriginalFilename();
        System.out.println(imagePath);
        Path path = Paths.get(UPLOAD_DIR + StringUtils.cleanPath(Objects.requireNonNull(imagePath)));
        try {
            Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
        businessCenter.setImagePath(imagePath);
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
}
