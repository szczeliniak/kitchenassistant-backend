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

    override fun findById(userId: Int): User? {
        return userRepository.findById(userId)
            .map { userMapper.toDomain(it) }
            .orElse(null)
    }

    override fun findAll(): List<User> {
        return userRepository.findAll()
            .map { userMapper.toDomain(it) }
    }

}