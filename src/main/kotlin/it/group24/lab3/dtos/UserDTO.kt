package it.group24.lab3.dtos
import it.group24.lab3.entities.User

data class UserDTO(
    val id: Long?,
    val username: String,
    val password: String,
    val email: String,
    val isActive: Boolean
)

fun User.toDTO(): UserDTO {
    return UserDTO( id,
                    username,
                    password,
                    email,
                    isActive)
}