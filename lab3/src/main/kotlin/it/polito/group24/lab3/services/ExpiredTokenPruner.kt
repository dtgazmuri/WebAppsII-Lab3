package it.polito.group24.lab3.services

import it.polito.group24.lab3.entities.Activation
import it.polito.group24.lab3.repositories.ActivationRepository
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import java.sql.Timestamp

@Service
class ExpiredTokenPruner(
    val activationRepository: ActivationRepository
) {
    fun getExpiredTokens(): Set<Activation> =
        activationRepository.findByDeadlineBefore(Timestamp(System.currentTimeMillis()))

    @Scheduled(fixedDelay = 900000)
    fun pruneExpiredTokens() {
        val activations = getExpiredTokens()
        activations.forEach{
            activationRepository.deleteById(it.getId()!!)
        }
    }
}