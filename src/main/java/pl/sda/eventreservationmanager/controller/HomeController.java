package pl.sda.eventreservationmanager.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping(path = "/about")
    public String about(){
        return "about";
    }
}
