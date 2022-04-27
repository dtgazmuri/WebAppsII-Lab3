package it.polito.group24.lab3.dtos

import java.util.*
import javax.validation.constraints.NotEmpty

data class UserValidationDTO(
    @NotEmpty(message = "Provisional id is not valid!")
    val provisionalId: UUID,

    @NotEmpty(message = "Activation code is not valid!")
    val activationCode: String
)
