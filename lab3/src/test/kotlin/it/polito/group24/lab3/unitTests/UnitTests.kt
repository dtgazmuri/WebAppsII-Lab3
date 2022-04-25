package it.polito.group24.lab3.unitTests

import it.polito.group24.lab3.dtos.toDTO
import it.polito.group24.lab3.entities.Activation
import it.polito.group24.lab3.entities.User
import it.polito.group24.lab3.repositories.ActivationRepository
import it.polito.group24.lab3.repositories.UserRepository
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class UnitTests {

    fun createUser() : User {
        return User().apply{
            username = "user"
            password = "password"
            email = "mail@mail.com"
            isActive = false
        }
    }

    fun createActivation(u: User) : Activation {
        return Activation().apply{
            user = u
            attemptCounter = 5
        }
    }

    @Test
    fun userDTOisOK() {
        val userDTO = createUser().toDTO()
        assertNotNull(userDTO)
        assertEquals(userDTO.username, "user")
        assertEquals(userDTO.password, "password")
        assertEquals(userDTO.email, "mail@mail.com")
        assertFalse(userDTO.isActive)
    }
}