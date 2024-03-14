package com.ilya.products.controllers.Impl;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * OpenAPI api документация
 */
@Controller
public class HomeController {

    /**
     * @return страница Swagger
     */
    @RequestMapping("/")
    public String index() {
        return "redirect:swagger-ui.html";
    }

}
