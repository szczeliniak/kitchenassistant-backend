package pl.szczeliniak.kitchenassistant.shoppinglist.commands.dto

data class NewShoppingListDto(
    var userId: Int = 0,
    var title: String = "",
    var description: String? = null,
    var items: List<NewShoppingListItemDto> = mutableListOf(),
)