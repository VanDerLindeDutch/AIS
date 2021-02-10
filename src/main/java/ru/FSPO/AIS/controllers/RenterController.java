package ru.FSPO.AIS.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.FSPO.AIS.dao.RenterLinkDAO;
import ru.FSPO.AIS.dao.UserType;
import ru.FSPO.AIS.models.RenterLink;

import javax.validation.Valid;

@Controller
@RequestMapping("/renter")
public class RenterController {
    private final RenterLinkDAO renterLinkDao;
    @Autowired
    public RenterController(RenterLinkDAO renterLinkDao) {
        this.renterLinkDao = renterLinkDao;
    }

    @GetMapping("/login")
    public String getLoginPage(Model model) {
        model.addAttribute("renterLink", new RenterLink());
        return "renter/login";
    }
    @PostMapping("/login")
    public String login(@ModelAttribute("renterLink") RenterLink renterLink, Model model){
        renterLink =renterLinkDao.login(renterLink);
        if(renterLink==null){

            model.addAttribute("isCorrect", false);
            return "renter/login";
        }
        else {
            model.addAttribute("USERTYPE", UserType.RENTER);
            model.addAttribute("ID", renterLink.getRenterId());
            return "redirect:/main";
        }

    }

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("renterLink", new RenterLink());
        return "renter/register";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute("renterLink") @Valid RenterLink renterLink, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "renter/register";
        }
        renterLinkDao.save(renterLink);
        return "redirect:/start";
    }

}
