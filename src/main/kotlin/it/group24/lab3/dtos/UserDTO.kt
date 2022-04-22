package it.group24.lab3.dtos

import it.group24.lab3.entities.User
import it.group24.lab3.validators.PasswordConstraint
import org.springframework.web.bind.annotation.ModelAttribute
import javax.validation.constraints.*


class UserDTO{
    @NotEmpty
    var username: String? = null
    @NotEmpty
    @PasswordConstraint
    var password: String? = null
    @Email
    @NotEmpty
    var email: String? = null
    var isActive: Boolean = false
}


