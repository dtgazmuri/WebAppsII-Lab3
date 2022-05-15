package it.group24.lab3

import it.group24.lab3.controllers.UserRegistrationController
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.JavaMailSenderImpl

@SpringBootApplication
class Lab3Application {
    @Value("\${spring.mail.host}")
    private val host: String? = null

    @Value("\${spring.mail.port}")
    private val port: Int? = null

    @Value("\${spring.mail.username}")
    private val username: String? = null

    @Value("\${spring.mail.password}")
    private val password: String? = null

    @Value("\${spring.mail.properties.mail.smtp.auth}")
    private val mailSmtpAuth: String? = null

    @Value("\${spring.mail.properties.mail.smtp.starttls.enable}")
    private val mailSmtpStartTlsEnable: String? = null

    @Value("\${spring.mail.properties.mail.debug}")
    private val mailDebug: String? = null

    @Bean
    fun getJavaMailSender(): JavaMailSender {
        val mailSender = JavaMailSenderImpl()
        mailSender.host = host
        mailSender.port = port!!
        mailSender.username = username
        mailSender.password = password
        mailSender.javaMailProperties["mail.smtp.auth"] = mailSmtpAuth
        mailSender.javaMailProperties["mail.smtp.starttls.enable"] = mailSmtpStartTlsEnable
        mailSender.javaMailProperties["mail.debug"] = mailDebug
        return mailSender
    }
}

fun main(args: Array<String>) {
    runApplication<Lab3Application>(*args)
    //SpringApplication.run(UserRegistrationController::class, args);

}
