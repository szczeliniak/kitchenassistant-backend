package pl.szczeliniak.kitchenassistant.receipt

import pl.szczeliniak.kitchenassistant.receipt.queries.dto.ReceiptCriteriaDto

interface ReceiptDao {

    fun findById(id: Int): Receipt?

    fun findAll(criteria: ReceiptCriteriaDto): List<Receipt>

    fun save(receipt: Receipt): Receipt

}