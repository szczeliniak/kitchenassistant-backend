package pl.szczeliniak.kitchenassistant.dev

import org.springframework.context.annotation.Profile
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import pl.szczeliniak.kitchenassistant.receipt.persistance.ReceiptRepository
import pl.szczeliniak.kitchenassistant.shoppinglist.persistance.ShoppingListRepository
import pl.szczeliniak.kitchenassistant.user.persistance.UserRepository

@Profile("dev")
@RestController
@RequestMapping("/dev")
class DevController(
    private val userRepository: UserRepository,
    private val receiptRepository: ReceiptRepository,
    private val shoppingListRepository: ShoppingListRepository
) {

    @GetMapping("/cleanup")
    fun cleanup(): String {
        shoppingListRepository.clear()
        receiptRepository.clear()
        userRepository.clear()
        return "OK"
    }

}