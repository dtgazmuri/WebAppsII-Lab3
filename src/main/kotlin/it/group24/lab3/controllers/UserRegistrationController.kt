package it.group24.lab3.controllers


import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import it.group24.lab3.customExceptions.WrongCredentials
import it.group24.lab3.customExceptions.WrongFieldException
import it.group24.lab3.dtos.LoginFormDTO
import it.group24.lab3.dtos.UserDTO
import it.group24.lab3.dtos.ValidationDTO
import it.group24.lab3.services.UserServiceImplementation
import org.json.JSONObject
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

import org.springframework.stereotype.Controller

import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import java.nio.charset.StandardCharsets
import java.util.*
import javax.validation.Valid
import kotlin.reflect.full.memberProperties

@RequestMapping("/user")
@Controller
class UserRegistrationController(private val userService: UserServiceImplementation) : WebMvcConfigurer {

    @Value("\${application.jwt.key}")
    private val secretKey: String? = null

    @PostMapping("/register")
    @ResponseBody
    @ResponseStatus(HttpStatus.ACCEPTED)
    fun registerUser(@RequestBody @Valid userDTO: UserDTO,
                     bindingResult: BindingResult): String {
        println(userDTO)
        if(bindingResult.hasErrors()){
            var errors = ""
            for (param in userDTO::class.memberProperties){
                errors = errors + (bindingResult.getFieldError(param.name)?.defaultMessage ?: "") + "---"
            }
            throw WrongFieldException(errors)
        }
        userDTO.password = BCryptPasswordEncoder(BCryptPasswordEncoder.BCryptVersion.`$2A`,10).encode(userDTO.password)
        userService.addUser(userDTO)
        val activationId = userService.getActivationIDByUser(userDTO)
        return JSONObject().put("provisional_id", activationId)
            .put("email", userDTO.email).toString()
    }

    @PostMapping("/validate")
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

    @PostMapping("/login")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    fun logIn(@RequestBody loginFormDTO: LoginFormDTO): String {

        fun getExpiration(): Date {
            val c: Calendar = Calendar.getInstance()
            c.time = Date()
            c.add(Calendar.HOUR, 1)
            return c.time
        }

        val username = loginFormDTO.username
        val password = loginFormDTO.password
        val user = userService.getUserByUsername(username)

        val bCryptPasswordEncoder = BCryptPasswordEncoder()
        if(!bCryptPasswordEncoder.matches(password, user.password))
            throw WrongCredentials("Credentials you have provided are wrong!")

        var rolesString = ""
        for ((index, r) in user.roles!!.withIndex()) {
            rolesString = rolesString.plus(r.name)
            if (index != user.roles!!.size)
                rolesString = rolesString.plus(",")
        }
        return "Bearer " + Jwts.builder()
            .setSubject(user.username)
            .setIssuedAt(Date())
            .setExpiration(getExpiration())
            .claim("roles", rolesString)
            .signWith(Keys.hmacShaKeyFor(secretKey!!.toByteArray(StandardCharsets.UTF_8)))
            .compact()
    }

    @GetMapping("/{username}/authorities")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    fun getUsernameAuthorities(@PathVariable username: String): String{
        var rolesString = ""
        val roles = userService.getUserByUsername(username).roles
        for ((index, r) in roles!!.withIndex()) {
            rolesString = rolesString.plus(r.name)
            if (index != roles!!.size)
                rolesString = rolesString.plus(",")
        }
        return rolesString
    }
}

