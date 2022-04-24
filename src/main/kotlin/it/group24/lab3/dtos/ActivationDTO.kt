package it.group24.lab3.dtos

import it.group24.lab3.entities.Activation
import it.group24.lab3.entities.User
import java.util.*
import javax.validation.constraints.NotEmpty

class ActivationDTO {
    @NotEmpty
    var activationCode: String? = null

    @NotEmpty
    var deadline: Date? = null

    @NotEmpty
    var attemptCounter: Int? = null

    @NotEmpty
    var user: User? = null
}

fun ActivationDTO.toActivation(): Activation{
    val activation = Activation()
    activation.activationCode = this.activationCode
    activation.deadline = this.deadline
    activation.attemptCounter = this.attemptCounter
    return activation
}
