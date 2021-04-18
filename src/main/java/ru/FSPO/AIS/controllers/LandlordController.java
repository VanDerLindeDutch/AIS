package ru.FSPO.AIS.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.FSPO.AIS.config.SecurityConfig;
import ru.FSPO.AIS.dao.BcLinkDAO;
import ru.FSPO.AIS.dao.UserType;
import ru.FSPO.AIS.models.BcLink;

import javax.validation.Valid;

@Controller
@RequestMapping("/landlord")
public class LandlordController {
    private final BcLinkDAO bcLinkDAO;

    @Autowired
    public LandlordController(BcLinkDAO bcLinkDAO) {
        this.bcLinkDAO = bcLinkDAO;
    }

    @GetMapping("/login")
    public String getLoginPage() {
        return "landlord/login";
    }

    @GetMapping("/register")
    public String getRegisterPage(Model model) {
        model.addAttribute("bcLink", new BcLink());
        return "landlord/register";
    }



    @PostMapping("/register")
    public String register(@ModelAttribute("bcLink") @Valid BcLink bcLink, BindingResult bindingResult) {
        bcLink.setPassword(SecurityConfig.passwordEncoder().encode(bcLink.getPassword()));
        if (bindingResult.hasErrors()) {
            return "landlord/register";
        }
        bcLinkDAO.insert(bcLink);
        return "redirect:/start";
    }

}
