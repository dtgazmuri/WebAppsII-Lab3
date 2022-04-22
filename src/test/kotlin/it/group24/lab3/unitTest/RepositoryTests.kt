package it.group24.lab3.unitTest

import it.group24.lab3.entities.User
import it.group24.lab3.repositories.UserRepository
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestMethodOrder
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest

@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
class UnitTests(){

    @Autowired
    lateinit var userRepository: UserRepository
    //lateinit var activationRepository: UserRepository

    fun initialize(): List<User>{
        val users: List<User> = mutableListOf(
            User().apply {
                //id = 1
                username = "Luca"
                password = "secret"
                email = "luca@luca.it"
                isActive = true
            },
            User().apply {
                //id = 2
                username = "Cristina"
                password = "secret"
                email = "cristina@cristina.it"
                isActive = true
            },
            User().apply {
                //id = 2
                username = "Diego"
                password = "secret"
                email = "diego@diego.cile"
                isActive = true
            },
            User().apply {
                //id = 3
                username = "Aliresa"
                password = "secret"
                email = "aliresa@aliresa.iran"
                isActive = true
            },
            User().apply {
                //id = 4
                username = "Mammata"
                password = "secret"
                email = "mammata@mammata.put"
                isActive = false
            }
        )
        return userRepository.saveAll(users).toList()
    }

    @BeforeEach
    fun emptyTables(){
        userRepository.deleteAll()
        //activationRepository.deleteAll()
    }


    @Test
    @Order(1)
    fun testSave(){
        try{
            val userToSave: User = User().apply {
                username = "Luca"
                password = "secret"
                email = "luca@luca.it"
                isActive = true
            }
            val userToCompare: User = User().apply {
                id = 1
                username = "Luca"
                password = "secret"
                email = "luca@luca.it"
                isActive = true
            }

            Assertions.assertTrue(userToCompare == userRepository.save(userToSave))
        }finally {
            emptyTables()
        }
    }

    @Test
    @Order(2)
    fun testSaveAll() {
        try {
            val usersToSave: List<User> = mutableListOf(
                User().apply {
                    id = 2
                    username = "Luca"
                    password = "secret"
                    email = "luca@luca.it"
                    isActive = true
                },
                User().apply {
                    id = 3
                    username = "Cristina"
                    password = "secret"
                    email = "cristina@cristina.it"
                    isActive = true
                },
                User().apply {
                    id = 4
                    username = "Diego"
                    password = "secret"
                    email = "diego@diego.cile"
                    isActive = true
                },
                User().apply {
                    id = 5
                    username = "Aliresa"
                    password = "secret"
                    email = "aliresa@aliresa.iran"
                    isActive = true
                },
                User().apply {
                    id = 6
                    username = "Mammata"
                    password = "secret"
                    email = "mammata@mammata.put"
                    isActive = false
                }
            )
            val usersToCompare: List<User> = mutableListOf(
                User().apply {
                    id = 2
                    username = "Luca"
                    password = "secret"
                    email = "luca@luca.it"
                    isActive = true
                },
                User().apply {
                    id = 3
                    username = "Cristina"
                    password = "secret"
                    email = "cristina@cristina.it"
                    isActive = true
                },
                User().apply {
                    id = 4
                    username = "Diego"
                    password = "secret"
                    email = "diego@diego.cile"
                    isActive = true
                },
                User().apply {
                    id = 5
                    username = "Aliresa"
                    password = "secret"
                    email = "aliresa@aliresa.iran"
                    isActive = true
                },
                User().apply {
                    id = 6
                    username = "Mammata"
                    password = "secret"
                    email = "mammata@mammata.put"
                    isActive = false
                }
            )
            val inserted = userRepository.saveAll(usersToSave).toList()
            for (i in 0 until usersToCompare.count()) {
                Assertions.assertTrue(usersToCompare[i] == inserted[i])
            }
        }finally {
            emptyTables()
        }
    }

    @Test
    @Order(3)
    fun testCount(){
        try{
            val users = initialize()
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
            initialize()
            userRepository.deleteAll()
            Assertions.assertEquals(0, userRepository.count())
        }finally {
            emptyTables()
        }
    }

    @Test
    @Order(6)
    fun testFind(){
        try{
            val users = initialize()
            val found = userRepository.findById(22).get()
            Assertions.assertTrue(users[0] == found)
        }finally {
            emptyTables()
        }
    }
}