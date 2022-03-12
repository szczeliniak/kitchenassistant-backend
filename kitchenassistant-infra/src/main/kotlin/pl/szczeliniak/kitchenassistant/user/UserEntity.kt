package pl.szczeliniak.kitchenassistant.user

import java.time.LocalDateTime
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Int = 0
    lateinit var email: String
    lateinit var password: String
    lateinit var name: String
    var createdAt: LocalDateTime = LocalDateTime.now()
    var modifiedAt: LocalDateTime = LocalDateTime.now()

}