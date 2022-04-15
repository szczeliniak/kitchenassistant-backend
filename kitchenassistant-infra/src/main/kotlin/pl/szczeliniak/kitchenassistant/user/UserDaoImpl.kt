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
        return userRepository.findAll(UserRepository.SearchCriteria(email), 0, 1).stream().findFirst()
            .map { userMapper.toDomain(it) }
            .orElse(null)
    }

    override fun count(): Long {
        return userRepository.count(UserRepository.SearchCriteria())
    }

    override fun findById(userId: Int): User? {
        val byId = userRepository.findById(userId) ?: return null
        return userMapper.toDomain(byId)
    }

    override fun findAll(offset: Int, limit: Int): List<User> {
        return userRepository.findAll(UserRepository.SearchCriteria(), offset, limit)
            .map { userMapper.toDomain(it) }
    }

}