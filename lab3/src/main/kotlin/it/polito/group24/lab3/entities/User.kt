package it.polito.group24.lab3.entities

import javax.persistence.*

@Entity
@Table(name="users")
class User {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    var id: Long = 0L

    var username: String = ""
    var password: String = ""
    var email: String = ""
}