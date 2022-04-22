package it.group24.lab3.controllers

import it.group24.lab3.dtos.UserDTO

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.ui.set
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import org.thymeleaf.context.WebContext
import javax.validation.Valid

@Controller
class UserRegistrationController() : WebMvcConfigurer {


    override fun addViewControllers(registry: ViewControllerRegistry) {
        registry.addViewController("/logInResult").setViewName("logInResult");
    }

    @GetMapping("/user/register")
    fun registration(model: Model): String {
        var userDTO = UserDTO()
        model["user"] = userDTO
        return "registrationForm"
    }


    @PostMapping("/user/register")

    fun checkFormsFields(
        @ModelAttribute("user")
        @Valid
        userDTO: UserDTO,
        bindingResult: BindingResult,
        model: Model,):  String{
        if(bindingResult.hasErrors()){
            return "registrationForm"
        }
        return "redirect:/logInResult"
    }
}

