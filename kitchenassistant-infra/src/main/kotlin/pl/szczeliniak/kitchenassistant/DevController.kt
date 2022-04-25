package pl.szczeliniak.kitchenassistant

import org.springframework.context.annotation.Profile
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import pl.szczeliniak.kitchenassistant.receipt.CategoryRepository
import pl.szczeliniak.kitchenassistant.receipt.ReceiptRepository
import pl.szczeliniak.kitchenassistant.shoppinglist.ShoppingListRepository
import pl.szczeliniak.kitchenassistant.user.UserRepository

@Profile("dev")
@RestController
@RequestMapping("/dev")
class DevController(
    private val userRepository: UserRepository,
    private val receiptRepository: ReceiptRepository,
    private val shoppingListRepository: ShoppingListRepository,
    private val categoryRepository: CategoryRepository
) {

    @GetMapping("/cleanup")
    fun cleanup(): String {
        shoppingListRepository.clear()
        receiptRepository.clear()
        userRepository.clear()
        categoryRepository.clear()
        return "OK"
    }

}