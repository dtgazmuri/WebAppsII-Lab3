package it.polito.group24.lab3.dtos

import java.util.UUID

data class RegisterUserResponseDTO(
    val provisionalId : UUID,
    val email: String,
)
