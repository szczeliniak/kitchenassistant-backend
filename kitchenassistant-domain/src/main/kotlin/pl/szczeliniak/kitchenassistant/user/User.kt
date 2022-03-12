package pl.szczeliniak.kitchenassistant.user

import java.time.LocalDateTime

class User(
    id: Int = 0,
    email: String,
    password: String,
    name: String,
    createdAt: LocalDateTime = LocalDateTime.now(),
    modifiedAt: LocalDateTime = LocalDateTime.now()
) {

    var id: Int
        private set
    var email: String
        private set
    var password: String
        private set
    var name: String
        private set
    var createdAt: LocalDateTime
        private set
    var modifiedAt: LocalDateTime
        private set

    init {
        this.id = id
        this.email = email
        this.password = password
        this.name = name
        this.createdAt = createdAt
        this.modifiedAt = modifiedAt
    }

}