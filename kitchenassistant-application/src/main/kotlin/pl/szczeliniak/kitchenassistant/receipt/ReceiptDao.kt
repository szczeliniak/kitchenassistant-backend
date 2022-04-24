package pl.szczeliniak.kitchenassistant.receipt

interface ReceiptDao {

    fun findById(id: Int): Receipt?

    fun findAll(criteria: ReceiptCriteria, offset: Int, limit: Int): Set<Receipt>

    fun save(receipt: Receipt): Receipt

    fun count(criteria: ReceiptCriteria): Long

}