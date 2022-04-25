package it.polito.group24.lab3.entities

import java.util.*
import javax.persistence.*

@Entity
@Table(name = "activations")
class Activation {
    @Id
    @GeneratedValue(generator = "uuid2")
    var id: UUID? = null

    @OneToOne(cascade = [CascadeType.ALL])
    var user: User? = null

    var attemptCounter: Int = 5
}
