package it.group24.lab3.services

interface EmailService {

    fun sendEmail(subject: String,
                  text: String,
                  targetEmail: String)
}