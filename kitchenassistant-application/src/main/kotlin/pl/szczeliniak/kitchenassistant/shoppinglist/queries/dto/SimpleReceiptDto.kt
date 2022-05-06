package pl.szczeliniak.kitchenassistant.shoppinglist.queries.dto

import pl.szczeliniak.kitchenassistant.receipt.queries.dto.ReceiptDto

data class SimpleReceiptDto(
    val id: Int,
    val name: String
) {

    companion object {
        fun fromDomain(receiptDto: ReceiptDto): SimpleReceiptDto {
            return SimpleReceiptDto(
                receiptDto.id,
                receiptDto.name
            )
        }
    }

}