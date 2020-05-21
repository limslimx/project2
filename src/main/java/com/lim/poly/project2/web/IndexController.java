package com.lim.poly.project2.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;

@Slf4j
@Controller
public class IndexController {

    @GetMapping("/")
    public String Index(HttpSession httpSession) {
        String userId = (String)httpSession.getAttribute("userId");
        log.info("######### userID: " + userId);
        if (userId == null) {
            return "member/login";
        } else {
            return "book/search";
        }
    }
}
