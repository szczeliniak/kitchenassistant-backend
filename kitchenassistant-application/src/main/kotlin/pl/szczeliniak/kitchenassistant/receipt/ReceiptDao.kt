package pl.szczeliniak.kitchenassistant.receipt

interface ReceiptDao {

    fun findById(id: Int): Receipt?

    fun findAll(criteria: ReceiptCriteria): List<Receipt>

    fun save(receipt: Receipt): Receipt

}