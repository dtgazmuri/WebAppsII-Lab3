package it.group24.lab3.services

import it.group24.lab3.dtos.UserDTO
import org.springframework.mail.MailException
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.JavaMailSenderImpl
import org.springframework.mail.javamail.MimeMessagePreparator
import org.springframework.stereotype.Service
import java.util.*
import javax.mail.Message
import javax.mail.internet.InternetAddress


@Service
public class EmailServiceImplementation() : EmailService {

    private var mailSender: JavaMailSender = getJavaMailSender()

    override fun sendEmail(userDTO: UserDTO, activationCode: String): Boolean {
        val companyEmail = "group24@polito.it"
        val preparator = MimeMessagePreparator{ mimeMessage ->
            mimeMessage.setRecipient(Message.RecipientType.TO,
                InternetAddress(userDTO.username))
            mimeMessage.setFrom(InternetAddress(companyEmail))
            mimeMessage.setSubject("")
            mimeMessage.setText("Dear ${userDTO.username}, your activation code is $activationCode")
        }
        return try {
            this.mailSender.send(preparator)
            true
        } catch (ex: MailException) {
            println(ex.message)
            false
        }
    }

    private fun getJavaMailSender(): JavaMailSender {
        val mailSender = JavaMailSenderImpl()
        mailSender.host = "smtp.freesmtpservers.com"
        mailSender.port = 25
        mailSender.username = "group24@polito.it"
        val props: Properties = mailSender.javaMailProperties
        props["mail.transport.protocol"] = "smtp"
        props["mail.smtp.auth"] = "false"
        props["mail.smtp.starttls.enable"] = "true"
        props["mail.debug"] = "false"
        return mailSender
    }

}