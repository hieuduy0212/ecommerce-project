package com.hieuduy.admin.controllers;

import com.hieuduy.core.dto.AdminDto;
import com.hieuduy.core.entities.Admin;
import com.hieuduy.core.services.AdminService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    @Autowired
    private AdminService adminService;

    @GetMapping("/login")
    public String loginFormPage(Model model) {
        model.addAttribute("title", "Login Admin");
        return "login";
    }

    @GetMapping("/register")
    public String goRegisterPage(Model model) {
        model.addAttribute("title", "Register Admin");
        model.addAttribute("adminDto", new AdminDto());
        return "register";
    }

    @GetMapping("/forgot-password")
    public String goForgotPasswordPage(Model model) {
        model.addAttribute("title", "Forgot password");
        return "forgot-password";
    }

    @PostMapping("/register")
    public String doRegister(@Valid @ModelAttribute("adminDto") AdminDto adminDto, BindingResult result, Model model) {
        model.addAttribute("title", "Register Admin");
        System.out.println(adminDto.toString());
        if (result.hasErrors()) {
            model.addAttribute("adminDto", adminDto);
            return "register";
        }
        Admin admin = adminService.findByUsername(adminDto.getUsername());
        if (admin != null) {
            model.addAttribute("adminDto", adminDto);
            model.addAttribute("errorMessage", "This email has been registered!");
            return "register";
        }
        if (adminDto.getPassword().equals(adminDto.getRepeatPassword())) {
            adminService.save(adminDto);
            model.addAttribute("successMessage", "Register successfully!");
        } else {
            model.addAttribute("errorMessage", "Passwords do not match!");
        }
        model.addAttribute("adminDto", adminDto);
        return "register";
    }

    @GetMapping("/index")
    public String goHomePage(Model model){
        model.addAttribute("title", "Homepage Admin");
        return "index";
    }
}
