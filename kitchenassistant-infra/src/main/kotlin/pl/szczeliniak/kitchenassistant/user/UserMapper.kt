package pl.szczeliniak.kitchenassistant.user

import org.springframework.stereotype.Component

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
        val userEntity = UserEntity()
        userEntity.id = user.id
        userEntity.email = user.email
        userEntity.password = user.password
        userEntity.name = user.name
        userEntity.createdAt = user.createdAt
        userEntity.modifiedAt = user.modifiedAt
        return userEntity
    }

}