package com.devtalles.tu_cv_spring_boot.cv.controller;

import com.devtalles.tu_cv_spring_boot.cv.model.Person;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value="/cv")
public class CvController {

    @GetMapping({"/home","/", "/index"})
    public String index(Model model){


        Person person = new Person("Jhonny","Zambrano","Backend dev");

        model.addAttribute("name","Jhonny");
        model.addAttribute("person",person);
        return "index";
    }

//    @GetMapping("/pep")
//    public String pep(){
//        return "pep";
//    }
}
