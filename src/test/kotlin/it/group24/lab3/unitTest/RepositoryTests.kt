package it.group24.lab3.unitTest

import it.group24.lab3.entities.Activation
import it.group24.lab3.entities.User
import it.group24.lab3.repositories.ActivationRepository
import it.group24.lab3.repositories.UserRepository
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestMethodOrder
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.util.*

@SpringBootTest

@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
class UnitTests(){

    @Autowired
    lateinit var userRepository: UserRepository
    @Autowired
    lateinit var activationRepository: ActivationRepository

    fun initialize(): List<User>{
        val users: List<User> = mutableListOf(
            User().apply {
                username = "Luca"
                password = "secret"
                email = "luca@luca.it"
            },
            User().apply {
                username = "Cristina"
                password = "secret"
                email = "cristina@cristina.it"
            },
            User().apply {
                username = "Diego"
                password = "secret"
                email = "diego@diego.cile"
            },
            User().apply {
                username = "Aliresa"
                password = "secret"
                email = "aliresa@aliresa.iran"
            },
            User().apply {
                username = "Mammata"
                password = "secret"
                email = "mammata@mammata.put"
            }
        )
        return users
    }

    fun getDeadLine(): Date{
        var c: Calendar = Calendar.getInstance()
        c.time = Date()
        c.add(Calendar.DAY_OF_MONTH, 5)
        return c.time
    }

    @BeforeEach
    fun emptyTables(){
        activationRepository.deleteAll()
        userRepository.deleteAll()
    }


    @Test
    @Order(1)
    fun testSave(){
        try{
            var date = getDeadLine()
            val userToSave: User = User().apply {
                username = "Luca"
                password = "secret"
                email = "luca@luca.it"
                isActive = false
            }
            val activationToSave: Activation = Activation().apply {
                activationCode = "ciao"
                deadline = date
                attemptCounter = 5
                user = userToSave
            }
            Assertions.assertDoesNotThrow{userRepository.save(userToSave)}
            Assertions.assertDoesNotThrow{activationRepository.save(activationToSave)}
        }finally {
            emptyTables()
        }
    }

    @Test
    @Order(2)
    fun testSaveAll() {
        try {
            var usersInserted: List<User> = mutableListOf()
            val date = getDeadLine()
            val usersToSave: List<User> = mutableListOf(
                User().apply {
                    username = "Luca"
                    password = "secret"
                    email = "luca@luca.it"
                },
                User().apply {
                    username = "Cristina"
                    password = "secret"
                    email = "cristina@cristina.it"

                },
                User().apply {
                    username = "Diego"
                    password = "secret"
                    email = "diego@diego.cile"

                },
                User().apply {
                    username = "Aliresa"
                    password = "secret"
                    email = "aliresa@aliresa.iran"

                },
                User().apply {
                    username = "Mammata"
                    password = "secret"
                    email = "mammata@mammata.put"

                }
            )
            Assertions.assertDoesNotThrow { usersInserted = userRepository.saveAll(usersToSave).toList() }
            val activationsToSave: List<Activation> = mutableListOf(
                Activation().apply {
                    activationCode = "ciao"
                    deadline = date
                    attemptCounter = 5
                    user = usersInserted[0]
                },
                Activation().apply {
                    activationCode = "come"
                    deadline = date
                    attemptCounter = 5
                    user = usersInserted[1]
                },
                Activation().apply {
                    activationCode = "stai"
                    deadline = date
                    attemptCounter = 5
                    user = usersInserted[2]
                },
                Activation().apply {
                    activationCode = "io"
                    deadline = date
                    attemptCounter = 5
                    user = usersInserted[3]
                },
                Activation().apply {
                    activationCode = "bene"
                    deadline = date
                    attemptCounter = 5
                    user = usersInserted[4]
                }
            )
            Assertions.assertDoesNotThrow{activationRepository.saveAll(activationsToSave)}
        }finally {
            emptyTables()
        }
    }

    @Test
    @Order(3)
    fun testCount(){
        try{
            val users = initialize()
            userRepository.saveAll(users)
            Assertions.assertEquals(5, userRepository.count())
        }finally {
            emptyTables()
        }
    }

    @Test
    @Order(4)
    fun testDelete(){
        try{
            val users = initialize()
            userRepository.saveAll(users)
            userRepository.delete(users[4])
            Assertions.assertEquals(4, userRepository.count())
        }finally {
            emptyTables()
        }
    }

    @Test
    @Order(5)
    fun testDeleteAll(){
        try{
            val users = initialize()
            userRepository.saveAll(users)
            userRepository.deleteAll()
            Assertions.assertEquals(0, userRepository.count())
        }finally {
            emptyTables()
        }
    }

    @Test
    @Order(6)
    fun testFindById(){
        try{
            val users = initialize()
            userRepository.saveAll(users)
            Assertions.assertTrue(userRepository.findById(22).isPresent)
        }finally {
            emptyTables()
        }
    }

    @Test
    @Order(7)
    fun testFind(){
        try{
            val date = getDeadLine()
            val usersToSave = initialize()
            val activationsToSave: List<Activation> = mutableListOf(
                Activation().apply {
                    activationCode = "ciao"
                    deadline = date
                    attemptCounter = 5
                    user = usersToSave[0]
                },
                Activation().apply {
                    activationCode = "come"
                    deadline = date
                    attemptCounter = 5
                    user = usersToSave[1]
                },
                Activation().apply {
                    activationCode = "stai"
                    deadline = date
                    attemptCounter = 5
                    user = usersToSave[2]
                },
                Activation().apply {
                    activationCode = "io"
                    deadline = date
                    attemptCounter = 5
                    user = usersToSave[3]
                },
                Activation().apply {
                    activationCode = "bene"
                    deadline = date
                    attemptCounter = 5
                    user = usersToSave[4]
                }
            )
            activationRepository.saveAll(activationsToSave)
            Assertions.assertTrue(userRepository.findByUsername("Luca").isPresent)
            Assertions.assertTrue(activationRepository.findByUser(usersToSave[0]).isPresent)
        }finally {
            emptyTables()
        }
    }
}