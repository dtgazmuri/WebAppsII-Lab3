package it.group24.lab3.integrationTest

import it.group24.lab3.repositories.ActivationRepository
import it.group24.lab3.repositories.UserRepository
import org.assertj.core.api.Assertions.assertThat
import org.json.JSONObject
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.client.postForEntity
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.http.*
import kotlin.test.assertTrue


@SpringBootTest(webEnvironment=SpringBootTest.WebEnvironment.RANDOM_PORT)
class RateLimiterTests {

    @LocalServerPort
    protected val port: Int = 0

    @Autowired
    lateinit var restTemplate: TestRestTemplate

    @Autowired
    lateinit var activationRepository: ActivationRepository

    @Autowired
    lateinit var userRepository: UserRepository

    @AfterEach
    fun cleanUp(){
        activationRepository.deleteAll()
        userRepository.deleteAll()
    }

    @Test
    fun rateLimitOverload(){
        var counter = 0
        val payload = JSONObject().put("username", "Luca")
            .put("password", "SonoSegret@98!").put("email", "luca@polito.it")
        val headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_JSON
        val request = HttpEntity<String>(payload.toString(), headers)

        for (i in 0..30) {
            var entity = restTemplate.postForEntity<String>("/user/register", request)
            if (entity.statusCode == HttpStatus.TOO_MANY_REQUESTS)
                counter += 1
        }
        Assertions.assertTrue(counter > 0)
    }
}