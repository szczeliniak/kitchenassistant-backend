package pl.szczeliniak.kitchenassistant.user

import org.springframework.web.bind.annotation.*
import pl.szczeliniak.kitchenassistant.dto.SuccessResponse
import pl.szczeliniak.kitchenassistant.user.commands.AddNewUser
import pl.szczeliniak.kitchenassistant.user.commands.dto.AddNewUserDto
import pl.szczeliniak.kitchenassistant.user.dto.UserResponse
import pl.szczeliniak.kitchenassistant.user.dto.UsersResponse
import pl.szczeliniak.kitchenassistant.user.queries.GetUser
import pl.szczeliniak.kitchenassistant.user.queries.GetUsers

@RestController
@RequestMapping("/users")
class UserController(
    private val getUser: GetUser,
    private val getUsers: GetUsers,
    private val addNewUser: AddNewUser
) {

    @GetMapping("/{id}")
    fun getUser(@PathVariable id: Int): UserResponse {
        return UserResponse(getUser.execute(id))
    }

    @GetMapping
    fun getUsers(): UsersResponse {
        return UsersResponse(getUsers.execute())
    }

    @PostMapping
    fun addUser(@RequestBody dto: AddNewUserDto): SuccessResponse {
        addNewUser.execute(dto)
        return SuccessResponse()
    }

}