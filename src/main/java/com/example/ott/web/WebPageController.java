package com.example.ott.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class WebPageController {

    @RequestMapping("/page")
    String showPage() {
        return "page";
    }

}
