package it.group24.lab3.entities

import it.group24.lab3.dtos.UserDTO
import org.springframework.data.util.ProxyUtils
import javax.persistence.*


@Entity
@Table(name = "users")
class User(
    @Id
    @GeneratedValue (strategy = GenerationType.SEQUENCE, generator = "user_generator")
    @SequenceGenerator(name = "user_generator",
        initialValue = 1,
        allocationSize = 1)
    @Column(updatable = false, nullable = false)
    var id: Long?,
    @Column(unique = true, nullable = false)
    var username: String?,
    @Column(nullable = false)
    var password: String?,
    @Column(unique = true, nullable = false)
    var email: String?,
    @Column(updatable = true, nullable = false)
    var isActive: Boolean = false,
    @ElementCollection
    var roles: MutableSet<Role> = mutableSetOf(Role.CUSTOMER)
){

    override fun equals(other: Any?): Boolean {
        if (other === this) return true
        if (other == null) return false
        if (this.id == null) return false
        if (ProxyUtils.getUserClass(other) != javaClass) return false
        other as User
        if (other.id != this.id) return false
        return true
    }

    override fun hashCode(): Int {
        return this.id.hashCode() + 22
    }

    override fun toString(): String {
        return "@Entity ${this.javaClass.name}(id=$id)"
    }
}

fun User.toDTO() = UserDTO(
    username,
    password,
    email,
    roles
)



