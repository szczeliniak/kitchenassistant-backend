package pl.szczeliniak.kitchenassistant.user.db

interface UserDao {

    fun findById(id: Int): User?

    fun findAll(criteria: UserCriteria, offset: Int, limit: Int): Set<User>

    fun save(user: User): User

    fun count(criteria: UserCriteria): Long

}