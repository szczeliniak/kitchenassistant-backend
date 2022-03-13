package pl.szczeliniak.kitchenassistant.user

import org.springframework.web.bind.annotation.*
import pl.szczeliniak.kitchenassistant.dto.SuccessResponse
import pl.szczeliniak.kitchenassistant.user.commands.AddNewUser
import pl.szczeliniak.kitchenassistant.user.commands.dto.AddNewUserDto
import pl.szczeliniak.kitchenassistant.user.queries.GetUser
import pl.szczeliniak.kitchenassistant.user.queries.GetUsers
import pl.szczeliniak.kitchenassistant.user.queries.dto.UserResponse
import pl.szczeliniak.kitchenassistant.user.queries.dto.UsersResponse

@RestController
@RequestMapping("/users")
class UserController(
    private val getUser: GetUser,
    private val getUsers: GetUsers,
    private val addNewUser: AddNewUser
) {

    @GetMapping("/{id}")
    fun getUser(@PathVariable id: Int): UserResponse {
        return getUser.execute(id)
    }

    @GetMapping
    fun getUsers(): UsersResponse {
        return getUsers.execute()
    }

    @PostMapping
    fun addUser(@RequestBody dto: AddNewUserDto): SuccessResponse {
        return addNewUser.execute(dto)
    }

}