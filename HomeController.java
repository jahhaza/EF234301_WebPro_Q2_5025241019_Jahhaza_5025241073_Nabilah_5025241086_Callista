package com.freshandfix.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("pageTitle", "FreshAndFix - Fresh Groceries, Fixed Prices, Fast Delivery");
        model.addAttribute("activePage", "home");
        return "home";
    }
}