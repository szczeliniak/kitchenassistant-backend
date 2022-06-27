package pl.szczeliniak.kitchenassistant.receipt

interface ReceiptDao {

    fun findById(id: Int): Receipt?

    fun findAll(criteria: ReceiptCriteria, offset: Int? = null, limit: Int? = null): Set<Receipt>

    fun save(receipt: Receipt): Receipt

    fun save(receipts: Set<Receipt>)

    fun count(criteria: ReceiptCriteria): Long

}