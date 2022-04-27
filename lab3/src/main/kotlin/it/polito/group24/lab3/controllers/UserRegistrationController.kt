package it.polito.group24.lab3.controllers

import it.polito.group24.lab3.dtos.RegisterUserResponseDTO
import it.polito.group24.lab3.dtos.UserRegistrationDTO
import it.polito.group24.lab3.dtos.UserValidationDTO
import it.polito.group24.lab3.dtos.ValidateUserResponseDTO
import it.polito.group24.lab3.services.UserService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
class UserRegistrationController(val userService: UserService) {

    @PostMapping("/user/register")
    fun registerUser(
        @Valid
        @RequestBody
        userRegistrationDTO: UserRegistrationDTO
    ): ResponseEntity<RegisterUserResponseDTO> {
        val provisionalId = userService.registerNewUser(
            userRegistrationDTO.username,
            userRegistrationDTO.password,
            userRegistrationDTO.email
        )
        val registerUserResponseDTO = RegisterUserResponseDTO(
            provisionalId,
            userRegistrationDTO.email
        )
        return ResponseEntity(registerUserResponseDTO, HttpStatus.ACCEPTED)
    }

    @PostMapping("/user/validate")
    fun validateUser(
        @Valid
        @RequestBody
        userValidationDTO: UserValidationDTO
    ): ResponseEntity<ValidateUserResponseDTO> {
        val validateUserResponseDTO = userService.validateUser(
            userValidationDTO.provisionalId,
            userValidationDTO.activationCode
        )
        return ResponseEntity(validateUserResponseDTO, HttpStatus.CREATED)
    }
}