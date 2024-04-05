package com.eliasfs06.spring.stockmanager.controller;

import com.eliasfs06.spring.stockmanager.model.User;
import com.eliasfs06.spring.stockmanager.model.dto.RegisterDTO;
import com.eliasfs06.spring.stockmanager.model.enums.UserRole;
import com.eliasfs06.spring.stockmanager.service.UserService;
import com.eliasfs06.spring.stockmanager.service.helper.MessageCode;
import com.eliasfs06.spring.stockmanager.service.helper.MessageHelper;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/user")
public class UserController extends GenericController<User> {

    @Autowired
    private UserService userService;

    @Autowired
    private MessageHelper messageHelper;

    @GetMapping("/login")
    public String login(Model model, @RequestParam(name = "error", required = false) String error) {
        model.addAttribute("user", new User());
        if (error != null) {
            model.addAttribute("errorMessage", messageHelper.getMessage(MessageCode.INVALID_LOGIN_INFO));
        }
        return "login";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute("newUser") @Valid RegisterDTO userData, BindingResult br, Model model){
        if (br.hasErrors()) {
            return "register";
        }
        userService.registerUser(userData);
        return "login";
    }


    @GetMapping("/register/form")
    public String registerFomr(Model model){
        model.addAttribute("newUser", new RegisterDTO());
        model.addAttribute("roles", UserRole.values());
        return "register";
    }

    @RequestMapping("logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:login";
    }

}
