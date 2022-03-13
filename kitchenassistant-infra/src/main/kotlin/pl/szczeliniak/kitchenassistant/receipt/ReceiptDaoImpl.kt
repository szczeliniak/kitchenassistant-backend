package pl.szczeliniak.kitchenassistant.receipt

import org.springframework.stereotype.Component
import pl.szczeliniak.kitchenassistant.receipt.persistance.ReceiptRepository
import pl.szczeliniak.kitchenassistant.receipt.queries.dto.ReceiptCriteriaDto

@Component
class ReceiptDaoImpl(
    private val receiptRepository: ReceiptRepository,
    private val receiptMapper: ReceiptMapper
) : ReceiptDao {

    override fun save(receipt: Receipt): Receipt {
        return receiptMapper.toDomain(receiptRepository.save(receiptMapper.toEntity(receipt)))
    }

    override fun findById(id: Int): Receipt? {
        return receiptRepository.findById(id)
            .map { receiptMapper.toDomain(it) }
            .orElse(null)
    }

    override fun findAll(criteria: ReceiptCriteriaDto): List<Receipt> {
        return receiptRepository.findAll() //TODO search by criteria
            .map { receiptMapper.toDomain(it) }
    }

}