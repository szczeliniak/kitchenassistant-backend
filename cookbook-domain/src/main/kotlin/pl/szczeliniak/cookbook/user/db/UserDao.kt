package pl.szczeliniak.cookbook.user.db

interface UserDao {

    fun findById(id: Int): User?

    fun findAll(criteria: UserCriteria, offset: Int, limit: Int): List<User>

    fun save(user: User): User

    fun count(criteria: UserCriteria): Long

}