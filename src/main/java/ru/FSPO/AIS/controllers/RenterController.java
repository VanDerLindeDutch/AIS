package ru.FSPO.AIS.controllers;

import org.apache.commons.collections4.IterableUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.FSPO.AIS.config.SecurityConfig;
import ru.FSPO.AIS.newdao.RenterLinkRepository;
import ru.FSPO.AIS.newmodels.BcLink;
import ru.FSPO.AIS.newmodels.RenterLink;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/renter")
public class RenterController {
    private final RenterLinkRepository renterLinkRepository;

    @Autowired
    public RenterController(RenterLinkRepository renterLinkRepository) {
        this.renterLinkRepository = renterLinkRepository;
    }

    @GetMapping("/login")
    public String getLoginPage() {
        return "renter/login";
    }


    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("renterLink", new RenterLink());
        return "renter/register";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute("renterLink") @Valid RenterLink renterLink, BindingResult bindingResult, Model model){

        List<RenterLink> list = IterableUtils.toList(renterLinkRepository.findAll());

        if(list.stream().anyMatch(x->x.getLogin().equals(renterLink.getLogin()))){
            model.addAttribute("nonUniqueLogin", true);
            return "renter/register";
        }
        if(list.stream().anyMatch(x->x.getEmail().equals(renterLink.getEmail()))){
            model.addAttribute("nonUniqueEmail", true);
            return "renter/register";
        }

        if(bindingResult.hasErrors()){
            return "renter/register";
        }
        renterLink.setPassword(SecurityConfig.passwordEncoder().encode(renterLink.getPassword()));
        renterLinkRepository.save(renterLink);
        return "redirect:/start";
    }

}
