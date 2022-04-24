package it.group24.lab3.entities

import it.group24.lab3.dtos.ActivationDTO
import org.hibernate.annotations.GenericGenerator
import org.springframework.data.util.ProxyUtils
import java.util.Date
import java.util.UUID
import javax.persistence.*



@Entity
@Table(name = "activations")
public class Activation {
    @Id @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(updatable = false, nullable = false)
    var activationID: UUID? = null
    @Column(unique = true, nullable = false)
    var activationCode: String? = ""
    @Column(updatable = false, nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    var deadline: Date? = null
    @Column(nullable = false)
    var attemptCounter: Int? = 0
    @OneToOne(cascade = [CascadeType.REMOVE])
    var user: User? = null

    override fun equals(other: Any?): Boolean {
        if (other === this) return true
        if (other == null) return false
        if (this.activationID == null) return false
        if (ProxyUtils.getUserClass(other) != javaClass) return false
        other as Activation
        if (other.activationID != this.activationID) return false
        return true
    }

    override fun hashCode(): Int {
        return this.activationID.hashCode() + 22
    }

    override fun toString(): String {
        return "@Entity ${this.javaClass.name}(id=$activationID)"
    }
}

fun Activation.toDTO(): ActivationDTO{
    val activationDTO = ActivationDTO()
    activationDTO.activationCode = this.activationCode
    activationDTO.user = this.user
    activationDTO.attemptCounter = this.attemptCounter
    activationDTO.deadline = this.deadline
    return activationDTO
}