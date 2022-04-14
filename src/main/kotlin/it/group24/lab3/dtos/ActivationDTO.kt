package it.group24.lab3.dtos

import it.group24.lab3.entities.Activation
import it.group24.lab3.entities.User
import java.sql.Timestamp
import java.util.*

data class ActivationDTO(
    val activationID: UUID?,
    val activationCode: String,
    val deadline: Date?,
    val attemptCounter: Int,
    val user: User?,
)

fun Activation.toDTO(): ActivationDTO {
    return ActivationDTO( activationID,
        activationCode,
        deadline,
        attemptCounter,
        user)
}