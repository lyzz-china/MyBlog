package com.lyz.web.admin;

import com.lyz.po.User;
import com.lyz.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.server.Session;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author ych
 * @date 16/4/2020 12:01 AM
 */

@Controller
@RequestMapping(value = "/admin")
public class LoginController {
    @Autowired
    private UserService userService;

    @RequestMapping
    public String loginPage() {
        return "admin/login";
    }

    @GetMapping("/login")
    public String loginPage1(HttpSession session) {
        if(session.getAttribute("user") == null){
            return "admin/login";
        }else {
            return "admin/index";
        }
    }

    @PostMapping(value = "login")
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        HttpSession session,
                        RedirectAttributes attributes) {
        User user = userService.checkUser(username, password);
        System.out.println(user);
        if (user != null) {
            user.setPassword(null);
            session.setAttribute("user", user);
            return "admin/index";
        } else {
            attributes.addFlashAttribute("message", "用户名或者密码错误！请重新输入！");
            return "redirect:/admin/login";
        }
    }

    @GetMapping(value = "/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("user");
        return "redirect:/admin";
    }
}
