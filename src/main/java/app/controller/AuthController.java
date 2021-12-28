package app.controller;

import app.model.User;
import app.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
@RequestMapping("/auth")
public class AuthController {
    public static final Logger log = LoggerFactory.getLogger(AuthController.class);
    @Qualifier("userValidator")
    private Validator userValidator;
    private UserService userService;

    @Autowired
    public void setUserValidator(Validator userValidator) {
        this.userValidator = userValidator;
    }

    @Autowired
    public void setUserService(@Qualifier("userService") UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String getLoginPage() {
        return "login";
    }

    @GetMapping("/signUp")
    public ModelAndView getSignUpPage(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/signUp");
        modelAndView.addObject("userForm", new User());
        return modelAndView;
    }

    @PostMapping("/signUp")
    public ModelAndView addUser(@Valid @ModelAttribute("userForm") User userForm, BindingResult bindingResult,
                                HttpServletRequest request){
        ModelAndView modelAndView = new ModelAndView();
        userValidator.validate(userForm, bindingResult);
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("/signUp");
            return modelAndView;
        }
        userService.saveUser(userForm, request);
        modelAndView.setViewName("redirect:/");
        return modelAndView;
    }
}
