package it.group24.lab3.dtos

import it.group24.lab3.entities.User
import it.group24.lab3.validators.PasswordConstraint
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
    @NotEmpty
    var roles: List<String> = mutableListOf<String>()
}


fun UserDTO.toUser(): User{
    val user = User()
    user.username = this.username
    user.email = this.email
    user.password = this.password
    user.roles = this.roles
    return user
}

