package com.hieuduy.customer.controller;

import com.hieuduy.core.dto.AdminDto;
import com.hieuduy.core.dto.CustomerDto;
import com.hieuduy.core.entities.Admin;
import com.hieuduy.core.entities.Customer;
import com.hieuduy.core.services.AdminService;
import com.hieuduy.core.services.CustomerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LoginController {
    @Autowired
    private CustomerService customerService;

    @GetMapping("/login")
    public String loginFormPage(Model model) {
        model.addAttribute("title", "Login customer");
        return "login";
    }

    @GetMapping("/register")
    public String goRegisterPage(Model model) {
        model.addAttribute("title", "Register customer");
        model.addAttribute("customerDto", new CustomerDto());
        return "register";
    }

    @GetMapping("/forgot-password")
    public String goForgotPasswordPage(Model model) {
        model.addAttribute("title", "Forgot password");
        return "forgot-password";
    }

    @PostMapping("/register")
    public String doRegister(@Valid @ModelAttribute("customerDto") CustomerDto customerDto, BindingResult result, Model model) {
        model.addAttribute("title", "Register customer");
        System.out.println(customerDto.toString());
        if (result.hasErrors()) {
            model.addAttribute("customerDto", customerDto);
            return "register";
        }
        Customer customer = customerService.findByUsername(customerDto.getUsername());
        if (customer != null) {
            model.addAttribute("customerDto", customerDto);
            model.addAttribute("errorMessage", "This email/username has been registered!");
            return "register";
        }
        if (customerDto.getPassword().equals(customerDto.getRepeatPassword())) {
            customerService.save(customerDto);
            model.addAttribute("successMessage", "Register successfully!");
        } else {
            model.addAttribute("errorMessage", "Passwords do not match!");
        }
        model.addAttribute("customerDto", customerDto);
        return "register";
    }

    @GetMapping("/index")
    public String goHomePage(Model model){
        model.addAttribute("title", "Homepage Admin");
        return "index";
    }
}

