package it.group24.lab3.integrationTest

import it.group24.lab3.repositories.ActivationRepository
import it.group24.lab3.repositories.UserRepository
import it.group24.lab3.services.UserServiceImplementation
import jdk.nashorn.internal.parser.JSONParser
import org.assertj.core.api.Assertions.assertThat
import org.json.JSONObject
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.client.postForEntity
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import java.util.*

@Testcontainers
@SpringBootTest(webEnvironment=SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase(replace=AutoConfigureTestDatabase.Replace.NONE)
class DbT1ApplicationTests {
    companion object {

        @Container
        val postgres = PostgreSQLContainer<Nothing>("postgres:latest")

        @DynamicPropertySource
        @JvmStatic
        fun properties(registry: DynamicPropertyRegistry) {
            registry.add("spring.datasource.url", postgres::getJdbcUrl)
            registry.add("spring.datasource.username", postgres::getUsername)
            registry.add("spring.datasource.password", postgres::getPassword)
            registry.add("spring.jpa.hibernate.ddl-auto") {"create-drop"}
        }

    }

    @LocalServerPort
    protected val port: Int = 0

    @Autowired
    lateinit var restTemplate: TestRestTemplate

    @Autowired
    lateinit var userService: UserServiceImplementation

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
    fun `container is up and running`(){
        Assertions.assertTrue(postgres.isRunning)
    }

    @Test
    fun `test register end point good request`() {
        val payload = JSONObject().put("username", "Luca")
            .put("password", "SonoSegret@98!").put("email", "luca@polito.it")
        val headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_JSON
        val request = HttpEntity<String>(payload.toString(), headers)
        val entity = restTemplate.postForEntity<String>("/user/register", request)
        assertThat(entity.statusCode).isEqualTo(HttpStatus.ACCEPTED)
    }

    @Test
    fun `test register end point empty username`() {
        val payload = JSONObject().put("username", "")
            .put("password", "SonoSegret@98!").put("email", "luca@polito.it")
        val headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_JSON
        val request = HttpEntity<String>(payload.toString(), headers)
        val entity = restTemplate.postForEntity<String>("/user/register", request)
        assertThat(entity.statusCode).isEqualTo(HttpStatus.BAD_REQUEST)
    }

    @Test
    fun `test register end point wrong password`() {
        val payload = JSONObject().put("username", "Luca")
            .put("password", "sonosegret@98!").put("email", "luca@polito.it")
        val headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_JSON
        val request = HttpEntity<String>(payload.toString(), headers)
        val entity = restTemplate.postForEntity<String>("/user/register", request)
        assertThat(entity.statusCode).isEqualTo(HttpStatus.BAD_REQUEST)
    }

    @Test
    fun `test register end point malformed mail`() {
        val payload = JSONObject().put("username", "Luca")
            .put("password", "SonoSegret@98!").put("email", "lucapolito.it")
        val headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_JSON
        val request = HttpEntity<String>(payload.toString(), headers)
        val entity = restTemplate.postForEntity<String>("/user/register", request)
        assertThat(entity.statusCode).isEqualTo(HttpStatus.BAD_REQUEST)
    }

    @Test
    fun `test validate end point good request`() {
        val payload = JSONObject().put("username", "Luca")
            .put("password", "SonoSegret@98!").put("email", "luca@polito.it")
        val headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_JSON
        val request = HttpEntity<String>(payload.toString(), headers)
        var entity = restTemplate.postForEntity<String>("/user/register", request)
        val provisional_id: UUID = UUID.fromString(JSONObject(entity.body)["provisional_id"].toString())
        val activation_code = userService.getActivationCodeByActivationID(provisional_id)
        val payload2 = JSONObject().put("provisional_id", provisional_id)
            .put("activation_code", activation_code)
        val request2 = HttpEntity<String>(payload2.toString(), headers)
        entity = restTemplate.postForEntity<String>("/user/validate", request2)
        assertThat(entity.statusCode).isEqualTo(HttpStatus.CREATED)
    }

    @Test
    fun `test validate end point bad activation id`() {
        val payload = JSONObject().put("username", "Luca")
            .put("password", "SonoSegret@98!").put("email", "luca@polito.it")
        val headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_JSON
        val request = HttpEntity<String>(payload.toString(), headers)
        var entity = restTemplate.postForEntity<String>("/user/register", request)
        val provisional_id: UUID = UUID.fromString(JSONObject(entity.body)["provisional_id"].toString())
        val activation_code = userService.getActivationCodeByActivationID(provisional_id)
        val payload2 = JSONObject().put("provisional_id", UUID.randomUUID())
            .put("activation_code", activation_code)
        val request2 = HttpEntity<String>(payload2.toString(), headers)
        entity = restTemplate.postForEntity<String>("/user/validate", request2)
        assertThat(entity.statusCode).isEqualTo(HttpStatus.NOT_FOUND)
    }

    @Test
    fun `test validate end point bad activation code`() {
        val payload = JSONObject().put("username", "Luca")
            .put("password", "SonoSegret@98!").put("email", "luca@polito.it")
        val headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_JSON
        val request = HttpEntity<String>(payload.toString(), headers)
        var entity = restTemplate.postForEntity<String>("/user/register", request)
        val provisional_id: UUID = UUID.fromString(JSONObject(entity.body)["provisional_id"].toString())
        val payload2 = JSONObject().put("provisional_id", provisional_id)
            .put("activation_code", "ciao")
        val request2 = HttpEntity<String>(payload2.toString(), headers)
        entity = restTemplate.postForEntity<String>("/user/validate", request2)
        assertThat(entity.statusCode).isEqualTo(HttpStatus.NOT_FOUND)
    }
}

