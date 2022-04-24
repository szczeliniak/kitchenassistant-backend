package pl.szczeliniak.kitchenassistant.user

interface UserDao {

    fun findById(userId: Int): User?

    fun findAll(offset: Int, limit: Int): Set<User>

    fun save(user: User): User

    fun findByEmail(email: String): User?

    fun count(): Long

}