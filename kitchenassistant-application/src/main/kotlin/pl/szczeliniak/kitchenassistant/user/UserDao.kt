package pl.szczeliniak.kitchenassistant.user

interface UserDao {

    fun findById(userId: Int): User?

    fun findAll(): List<User>

    fun save(user: User): User

}