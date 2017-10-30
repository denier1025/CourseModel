package ru.coursemodel.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by Alexey on 30.10.2017.
 */
@PropertySource("classpath:application.properties")
@Controller
public class IndexController {
    @Value("${spring.application.name}")
    String appName;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String homePage(Model model) {
        model.addAttribute("appName", appName);
        return "index";
    }
}
