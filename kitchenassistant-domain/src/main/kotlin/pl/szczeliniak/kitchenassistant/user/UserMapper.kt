package pl.szczeliniak.kitchenassistant.user

import org.mapstruct.Mapper
import pl.szczeliniak.kitchenassistant.user.db.User
import pl.szczeliniak.kitchenassistant.user.dto.response.UserResponse
import pl.szczeliniak.kitchenassistant.user.dto.response.UsersResponse

@Mapper
interface UserMapper {

    fun mapDetails(user: User): UserResponse.UserDto

    fun map(user: User): UsersResponse.UserDto

}