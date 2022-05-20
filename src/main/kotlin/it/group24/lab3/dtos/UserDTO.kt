package it.group24.lab3.dtos

import it.group24.lab3.entities.Role
import it.group24.lab3.validators.PasswordConstraint
import javax.validation.constraints.*


data class UserDTO(
    @NotEmpty
    var username: String? = null,
    @NotEmpty
    @PasswordConstraint
    var password: String? = null,
    @Email
    @NotEmpty
    var email: String? = null,

    var roles: MutableSet<Role>?
)

