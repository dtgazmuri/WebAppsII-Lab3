package it.polito.group24.lab3.dtos

import it.polito.group24.lab3.entities.Activation
import java.util.*

data class ActivationDTO(
    val userId: Long,
    val attemptCounter: Int
)

fun Activation.toDTO() = ActivationDTO(
    user!!.getId()!!,
    attemptCounter
)

