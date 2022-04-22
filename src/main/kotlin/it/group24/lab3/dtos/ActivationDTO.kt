package it.group24.lab3.dtos

import it.group24.lab3.entities.Activation
import it.group24.lab3.entities.User
import java.sql.Timestamp
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
