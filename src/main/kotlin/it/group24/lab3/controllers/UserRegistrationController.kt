package it.group24.lab3.controllers


import it.group24.lab3.CustomExceptions.WrongFieldException
import it.group24.lab3.dtos.UserDTO
import it.group24.lab3.dtos.ValidationDTO
import it.group24.lab3.services.UserServiceImplementation
import org.json.JSONObject
import org.springframework.http.HttpStatus

import org.springframework.stereotype.Controller

import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import javax.validation.Valid
import kotlin.reflect.full.memberProperties

@Controller
class UserRegistrationController(val userService: UserServiceImplementation) : WebMvcConfigurer {

    /*
    override fun addViewControllers(registry: ViewControllerRegistry) {
        registry.addViewController("/logInResult").setViewName("logInResult");
    }

            ----HERE THERE IS THE THE FORM------
              ----BUT IT WASN'T REQUIRED-----
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
     */

    @PostMapping("/user/register")
    @ResponseBody
    @ResponseStatus(HttpStatus.ACCEPTED)
    fun registerUser(@RequestBody @Valid userDTO: UserDTO,
                     bindingResult: BindingResult): String {
        if(bindingResult.hasErrors()){
            var errors = ""
            for (param in userDTO::class.memberProperties){
                errors = errors + (bindingResult.getFieldError(param.name)?.defaultMessage ?: "") + "---"
            }
            throw WrongFieldException(errors)
        }
        userService.addUser(userDTO)
        val activationId = userService.getActivationIDByUser(userDTO)
        return JSONObject().put("provisional_id", activationId)
            .put("email", userDTO.email).toString()
    }

    @PostMapping("/user/validate")
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    fun validateUser(@RequestBody validationDTO: ValidationDTO): String{
        userService.checkActivationByID(validationDTO.activationID, validationDTO.activationCode)
        val user: UserDTO = userService.getUserByActivationID(validationDTO.activationID)
        userService.changeActiveState(user)
        return JSONObject().put("id", userService.getUserIDByUsername(user.username!!))
            .put("nickname", user.username).put("email", user.email).toString()
    }
}

