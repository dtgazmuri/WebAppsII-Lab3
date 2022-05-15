package it.group24.lab3.services

import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.stereotype.Service

@Service
class EmailServiceImplementation(val emailSender: JavaMailSender): EmailService {

    override fun sendEmail(
        subject: String,
        text: String,
        targetEmail: String
    ) {
        val message = SimpleMailMessage()
        message.setSubject(subject)
        message.setText(text)
        message.setTo(targetEmail)

        emailSender.send(message)
    }
}