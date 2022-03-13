package pl.szczeliniak.kitchenassistant.receipt

import org.springframework.stereotype.Component
import pl.szczeliniak.kitchenassistant.receipt.persistance.ReceiptEntity
import pl.szczeliniak.kitchenassistant.user.UserMapper

@Component
class ReceiptMapper(
    private val userMapper: UserMapper,
    private val stepMapper: StepMapper,
    private val ingredientMapper: IngredientMapper
) {

    fun toDomain(receiptEntity: ReceiptEntity): Receipt {
        return Receipt(
            receiptEntity.id,
            userMapper.toDomain(receiptEntity.user),
            receiptEntity.name,
            receiptEntity.description,
            receiptEntity.author,
            receiptEntity.source,
            receiptEntity.ingredients.map { ingredientMapper.toDomain(it) },
            receiptEntity.steps.map { stepMapper.toDomain(it) },
            receiptEntity.createdAt,
            receiptEntity.modifiedAt
        )
    }

    fun toEntity(receipt: Receipt): ReceiptEntity {
        return ReceiptEntity(
            receipt.id,
            receipt.name,
            userMapper.toEntity(receipt.user),
            receipt.description,
            receipt.author,
            receipt.source,
            receipt.ingredients.map { ingredientMapper.toEntity(it) },
            receipt.steps.map { stepMapper.toEntity(it) },
            receipt.createdAt,
            receipt.modifiedAt
        )
    }

}