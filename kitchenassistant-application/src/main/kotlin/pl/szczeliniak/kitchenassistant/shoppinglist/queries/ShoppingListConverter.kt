package pl.szczeliniak.kitchenassistant.shoppinglist.queries

import pl.szczeliniak.kitchenassistant.receipt.queries.GetReceiptQuery
import pl.szczeliniak.kitchenassistant.receipt.queries.dto.ReceiptDetailsDto
import pl.szczeliniak.kitchenassistant.shoppinglist.ShoppingList
import pl.szczeliniak.kitchenassistant.shoppinglist.ShoppingListItem
import pl.szczeliniak.kitchenassistant.shoppinglist.queries.dto.ShoppingListDetailsDto
import pl.szczeliniak.kitchenassistant.shoppinglist.queries.dto.ShoppingListDto
import pl.szczeliniak.kitchenassistant.shoppinglist.queries.dto.ShoppingListItemDto
import pl.szczeliniak.kitchenassistant.shoppinglist.queries.dto.SimpleReceiptDto

open class ShoppingListConverter(private val getReceiptQuery: GetReceiptQuery) {

    open fun map(shoppingList: ShoppingList): ShoppingListDto {
        return ShoppingListDto(
            shoppingList.id,
            shoppingList.name,
            shoppingList.description,
            shoppingList.date
        )
    }

    open fun mapDetails(shoppingList: ShoppingList): ShoppingListDetailsDto {
        return ShoppingListDetailsDto(
            shoppingList.id,
            shoppingList.name,
            shoppingList.description,
            shoppingList.date,
            shoppingList.items.map { map(it) }.toSet()
        )
    }

    open fun map(shoppingListItem: ShoppingListItem): ShoppingListItemDto {
        return ShoppingListItemDto(
            shoppingListItem.id,
            shoppingListItem.name,
            shoppingListItem.quantity,
            shoppingListItem.sequence,
            shoppingListItem.completed,
            shoppingListItem.receiptId?.let { map(getReceiptQuery.execute(it).receipt) }
        )
    }

    fun map(receiptDto: ReceiptDetailsDto): SimpleReceiptDto {
        return SimpleReceiptDto(receiptDto.id, receiptDto.name)
    }

}