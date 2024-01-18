package hello.oauth2demo.global.controller;

import hello.oauth2demo.global.config.dto.SessionUser;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

//@Controller
@RequiredArgsConstructor
public class HomeController {

    private final HttpSession httpSession;
//    @GetMapping("/")
    public void home( Model model) {
        SessionUser user = (SessionUser) httpSession.getAttribute("user");
        if (user != null) {
            model.addAttribute("userName", user.getName());
        }
    }
}
