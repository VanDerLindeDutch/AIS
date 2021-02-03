package ru.FSPO.AIS.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class MainController {


    @GetMapping("/main")
    public String mainWindow(){
        return "index";
    }
}
