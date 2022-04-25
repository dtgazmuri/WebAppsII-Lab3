package it.polito.group24.lab3.dtos

import it.polito.group24.lab3.entities.User

data class UserDTO(
    val username: String,
    val password: String,
    val email: String,
    val isActive: Boolean
 )

fun User.toDTO() = UserDTO(
    username,
    password,
    email,
    isActive
)

