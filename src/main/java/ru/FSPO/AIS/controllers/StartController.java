package ru.FSPO.AIS.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import ru.FSPO.AIS.newdao.RequestToBcLinkRepository;
import ru.FSPO.AIS.newmodels.Placement;
import ru.FSPO.AIS.newmodels.RenterLink;
import ru.FSPO.AIS.newmodels.RequestToBcLink;

@Controller
public class StartController {
    @Autowired
    RequestToBcLinkRepository repository;
    @GetMapping("/start")
    public String getStartWindow(){
        RequestToBcLink requestToBcLink = new RequestToBcLink();
        requestToBcLink.setRenterLink(new RenterLink());
        requestToBcLink.setPlacement(new Placement());
        repository.save(requestToBcLink);
        return "index";
    }
}
