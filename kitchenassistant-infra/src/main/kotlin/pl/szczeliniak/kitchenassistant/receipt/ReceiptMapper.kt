package pl.szczeliniak.kitchenassistant.receipt

import org.springframework.stereotype.Component
import pl.szczeliniak.kitchenassistant.receipt.persistance.ReceiptEntity

@Component
class ReceiptMapper(
    private val stepMapper: StepMapper,
    private val ingredientMapper: IngredientMapper
) {

    fun toDomain(receiptEntity: ReceiptEntity): Receipt {
        return Receipt(
            receiptEntity.id,
            receiptEntity.userId,
            receiptEntity.name,
            receiptEntity.description,
            receiptEntity.author,
            receiptEntity.source,
            receiptEntity.ingredients.map { ingredientMapper.toDomain(it) }.toMutableList(),
            receiptEntity.steps.map { stepMapper.toDomain(it) }.toMutableList(),
            receiptEntity.deleted,
            receiptEntity.createdAt,
            receiptEntity.modifiedAt
        )
    }

    fun toEntity(receipt: Receipt): ReceiptEntity {
        return ReceiptEntity(
            receipt.id,
            receipt.name,
            receipt.userId,
            receipt.description,
            receipt.author,
            receipt.source,
            receipt.ingredients.map { ingredientMapper.toEntity(it) }.toMutableList(),
            receipt.steps.map { stepMapper.toEntity(it) }.toMutableList(),
            receipt.deleted,
            receipt.createdAt,
            receipt.modifiedAt
        )
    }

}