package com.projects.app.controllers.frontend;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import static com.projects.app.commons.constants.AppConstants.APP_NAME;

@Controller
public class HomeController {
    @GetMapping("/")
    public String greeting(@RequestParam(name="name", required=false,
            defaultValue="World") String name, Model model) {
        model.addAttribute("app_name",APP_NAME);
        return "index";
    }
}
