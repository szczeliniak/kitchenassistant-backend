package pl.szczeliniak.kitchenassistant.receipt

import org.springframework.stereotype.Component
import pl.szczeliniak.kitchenassistant.receipt.persistance.ReceiptRepository

@Component
class ReceiptDaoImpl(
    private val receiptRepository: ReceiptRepository,
    private val receiptMapper: ReceiptMapper
) : ReceiptDao {

    override fun save(receipt: Receipt): Receipt {
        return receiptMapper.toDomain(receiptRepository.save(receiptMapper.toEntity(receipt)))
    }

    override fun count(criteria: ReceiptCriteria): Long {
        return receiptRepository.count(mapCriteria(criteria))
    }

    override fun findById(id: Int): Receipt? {
        val byId = receiptRepository.findById(id) ?: return null
        return receiptMapper.toDomain(byId)
    }

    override fun findAll(criteria: ReceiptCriteria, offset: Int, limit: Int): Set<Receipt> {
        return receiptRepository.findAll(mapCriteria(criteria), offset, limit)
            .map { receiptMapper.toDomain(it) }
            .toSet()
    }

    private fun mapCriteria(criteria: ReceiptCriteria): ReceiptRepository.SearchCriteria {
        return ReceiptRepository.SearchCriteria(
            criteria.userId,
            criteria.categoryId,
            criteria.name,
            criteria.tag
        )
    }

}