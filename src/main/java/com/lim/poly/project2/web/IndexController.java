package com.lim.poly.project2.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;
import java.security.Principal;

@Controller
public class IndexController {

    @GetMapping("/")
    public String Index(Principal principal, HttpSession session) {
        session.setAttribute("id", principal.getName());
        return "index";
    }
}
