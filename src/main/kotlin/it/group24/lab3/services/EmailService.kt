package it.group24.lab3.services

import it.group24.lab3.dtos.UserDTO

interface EmailService {

    fun sendEmail(userDTO: UserDTO, activationCode: String): Boolean
}