package edu.northwesternmall.demo.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PracticeController {

@RequestMapping("/practice")
public String index(Model model){
    model.addAttribute("name","Jack");
    model.addAttribute("age",20);
    model.addAttribute("info","I love studying");
    return "practice";
}


}
