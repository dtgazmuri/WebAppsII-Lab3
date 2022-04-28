package it.group24.lab3.controllers


import io.github.bucket4j.Bucket
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
class UserRegistrationController(private val userService: UserServiceImplementation) : WebMvcConfigurer {




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
        userService.checkActivationByID(validationDTO.provisional_id, validationDTO.activation_code)
        val user: UserDTO = userService.getUserByActivationID(validationDTO.provisional_id)
        userService.changeActiveState(user)
        userService.deleteActivationByID(validationDTO.provisional_id)
        return JSONObject().put("id", userService.getUserIDByUsername(user.username!!))
            .put("nickname", user.username).put("email", user.email).toString()
    }
}

