package pl.sda.eventreservationmanager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import pl.sda.eventreservationmanager.model.dto.RegisterAppUserDto;
import pl.sda.eventreservationmanager.service.AppUserService;

@Controller
public class IndexController {
    @Autowired
    private AppUserService appUserService;

    @GetMapping(path = "/")
    public String index() {
        return "home";
    }

    @GetMapping(path = "/about")
    public String about() {
        return "about";
    }

    @GetMapping(path = "/login")
    public String login() {
        return "loginForm";
    }

    @GetMapping(path = "/register")
    public String register(Model model) {
        RegisterAppUserDto user_dto = new RegisterAppUserDto();
        model.addAttribute("user_dto", user_dto);
        return "registerForm";
    }

    @PostMapping(path = "/register")
    public String register(Model model, RegisterAppUserDto dto) {
        if (!dto.getConfirm_password().equals(dto.getPassword())) {
            model.addAttribute("user_dto", dto);
            model.addAttribute("errorMessage", "Passwords do not match");
            return "registerForm";
        }
        if(!appUserService.registerUser(dto)){
            model.addAttribute("user_dto", dto);
            model.addAttribute("errorMessage", "This username is already taken");
            return "registerForm";
        }
        return "redirect:/login";
    }
}
