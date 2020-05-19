package com.lim.poly.project2.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.logging.Logger;

@Slf4j
@Controller
public class IndexController {

    @GetMapping("/")
    public String Index() {
        log.info(this.getClass().getName() + ".index start");
        return "index";
    }
}
