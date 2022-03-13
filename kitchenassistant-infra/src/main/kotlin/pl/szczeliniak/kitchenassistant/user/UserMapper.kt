package pl.szczeliniak.kitchenassistant.user

import org.springframework.stereotype.Component
import pl.szczeliniak.kitchenassistant.user.persistance.UserEntity

@Component
class UserMapper {

    fun toDomain(userEntity: UserEntity): User {
        return User(
            userEntity.id,
            userEntity.email,
            userEntity.password,
            userEntity.name,
            userEntity.createdAt,
            userEntity.modifiedAt
        )
    }

    fun toEntity(user: User): UserEntity {
        return UserEntity(user.id, user.email, user.password, user.name, user.createdAt, user.modifiedAt)
    }

}