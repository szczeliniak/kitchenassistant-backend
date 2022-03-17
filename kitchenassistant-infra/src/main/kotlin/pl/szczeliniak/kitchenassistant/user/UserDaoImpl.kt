package pl.szczeliniak.kitchenassistant.user

import org.springframework.stereotype.Component
import pl.szczeliniak.kitchenassistant.user.persistance.UserRepository

@Component
class UserDaoImpl(
    private val userRepository: UserRepository,
    private val userMapper: UserMapper
) : UserDao {

    override fun save(user: User): User {
        return userMapper.toDomain(userRepository.save(userMapper.toEntity(user)))
    }

    override fun findByEmail(email: String): User? {
        val byEmail = userRepository.findByEmail(email) ?: return null
        return userMapper.toDomain(byEmail)
    }

    override fun findById(userId: Int): User? {
        val byId = userRepository.findById(userId) ?: return null
        return userMapper.toDomain(byId)
    }

    override fun findAll(): List<User> {
        return userRepository.findAll()
            .map { userMapper.toDomain(it) }
    }

}