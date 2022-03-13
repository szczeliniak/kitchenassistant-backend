package pl.szczeliniak.kitchenassistant.user

import org.springframework.web.bind.annotation.*
import pl.szczeliniak.kitchenassistant.dto.SuccessResponse
import pl.szczeliniak.kitchenassistant.user.commands.AddUserCommand
import pl.szczeliniak.kitchenassistant.user.commands.dto.AddUserDto
import pl.szczeliniak.kitchenassistant.user.queries.GetUserQuery
import pl.szczeliniak.kitchenassistant.user.queries.GetUsersQuery
import pl.szczeliniak.kitchenassistant.user.queries.dto.UserResponse
import pl.szczeliniak.kitchenassistant.user.queries.dto.UsersResponse

@RestController
@RequestMapping("/users")
class UserController(
    private val getUserQuery: GetUserQuery,
    private val getUsersQuery: GetUsersQuery,
    private val addUserCommand: AddUserCommand
) {

    @GetMapping("/{id}")
    fun getUser(@PathVariable id: Int): UserResponse {
        return getUserQuery.execute(id)
    }

    @GetMapping
    fun getUsers(): UsersResponse {
        return getUsersQuery.execute()
    }

    @PostMapping
    fun addUser(@RequestBody dto: AddUserDto): SuccessResponse {
        return addUserCommand.execute(dto)
    }

}