package ru.FSPO.AIS.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import ru.FSPO.AIS.newdao.TestRepository;
import ru.FSPO.AIS.newmodels.DataJpaTestEntity;

@Controller
public class StartController {
    @Autowired
    TestRepository testRepository;
    @GetMapping("/start")
    public String getStartWindow(){
        DataJpaTestEntity dataJpaTestEntity = new DataJpaTestEntity();
        dataJpaTestEntity.setFirstName("dsdssd");
        testRepository.save(dataJpaTestEntity);
        return "index";
    }
}
