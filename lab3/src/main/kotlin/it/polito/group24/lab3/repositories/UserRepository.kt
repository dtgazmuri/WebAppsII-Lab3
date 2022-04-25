package it.polito.group24.lab3.repositories

import it.polito.group24.lab3.entities.User
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository: CrudRepository<User, Long> {
}