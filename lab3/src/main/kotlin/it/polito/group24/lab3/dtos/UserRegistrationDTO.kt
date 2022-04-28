package it.polito.group24.lab3.dtos

import it.polito.group24.lab3.validators.PasswordConstraint
import javax.validation.constraints.Email
import javax.validation.constraints.NotEmpty

data class UserRegistrationDTO(

    @NotEmpty(message = "Provide a valid nickname!")
    val nickname: String,

    @NotEmpty(message = "Password must be at least 8 characters long, it must" +
            " contain at least one digit, one uppercase letter, one lowercase letter, one" +
            " non alphanumeric character" )
    @PasswordConstraint(message = "Password must be at least 8 characters long, it must" +
            " contain at least one digit, one uppercase letter, one lowercase letter, one" +
            " non alphanumeric character" )
    val password: String,

    @NotEmpty(message = "Provide a valid email!")
    @Email(message = "Provide a valid email!")
    val email: String
) {

}
