package it.group24.lab3.dtos

import java.util.*

data class ErrorDetails(
    val dateTime: Date,
    val message: List<String>
)
