package pl.szczeliniak.kitchenassistant.shoppinglist.commands.factories

import org.junit.jupiter.api.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import pl.szczeliniak.kitchenassistant.JunitBaseClass
import pl.szczeliniak.kitchenassistant.shoppinglist.commands.dto.NewShoppingListRequest
import pl.szczeliniak.kitchenassistant.shoppinglist.commands.dto.NewShoppingListItemRequest
import pl.szczeliniak.kitchenassistant.shoppinglist.db.ShoppingList
import pl.szczeliniak.kitchenassistant.shoppinglist.db.ShoppingListItem
import pl.szczeliniak.kitchenassistant.user.db.User
import pl.szczeliniak.kitchenassistant.user.db.UserDao
import java.time.LocalDate

internal class ShoppingListFactoryTest : JunitBaseClass() {

    @Mock
    private lateinit var shoppingListItemFactory: ShoppingListItemFactory

    @Mock
    private lateinit var userDao: UserDao

    @InjectMocks
    private lateinit var shoppingListFactory: ShoppingListFactory

    @Test
    fun shouldCreateShoppingList() {
        val date = LocalDate.now()
        val newShoppingListItemRequest = NewShoppingListItemRequest()
        val newShoppingListRequest =
            NewShoppingListRequest(1, "NAME", "DESCRIPTION", date, mutableSetOf(newShoppingListItemRequest))
        val shoppingListItem = shoppingListItem()
        val user = user()

        whenever(shoppingListItemFactory.create(newShoppingListItemRequest)).thenReturn(shoppingListItem)
        whenever(userDao.findById(1)).thenReturn(user)
        val result = shoppingListFactory.create(newShoppingListRequest)

        assertThat(result).usingRecursiveComparison()
            .ignoringFields("createdAt", "modifiedAt")
            .isEqualTo(shoppingList(date, mutableSetOf(shoppingListItem), user))
    }

    private fun shoppingListItem(): ShoppingListItem {
        return ShoppingListItem(name = "", quantity = "", sequence = null)
    }

    private fun shoppingList(date: LocalDate, items: MutableSet<ShoppingListItem>, user: User): ShoppingList {
        return ShoppingList(user = user, name = "NAME", description = "DESCRIPTION", date = date, items = items)
    }

    private fun user(): User {
        return User(id = 1, email = "", name = "")
    }

}