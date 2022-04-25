package it.polito.group24.lab3.repositories

import it.polito.group24.lab3.entities.Activation
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface ActivationRepository : CrudRepository<Activation, UUID>{
}