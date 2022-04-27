package it.polito.group24.lab3.dtos

data class ValidateUserResponseDTO(
    val userId: Long,
    val nickname: String,
    val email: String
)
