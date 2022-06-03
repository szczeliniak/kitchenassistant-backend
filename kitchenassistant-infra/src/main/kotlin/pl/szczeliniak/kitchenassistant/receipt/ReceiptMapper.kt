package pl.szczeliniak.kitchenassistant.receipt

import org.springframework.stereotype.Component

@Component
class ReceiptMapper(
    private val stepMapper: StepMapper,
    private val ingredientMapper: IngredientMapper,
    private val categoryMapper: CategoryMapper,
    private val photoMapper: PhotoMapper,
    private val tagMapper: TagMapper,
    private val authorMapper: AuthorMapper
) {

    fun toDomain(receiptEntity: ReceiptEntity): Receipt {
        return Receipt(
            receiptEntity.id,
            receiptEntity.userId,
            receiptEntity.name,
            receiptEntity.description,
            receiptEntity.author?.let { authorMapper.toDomain(it) },
            receiptEntity.source,
            receiptEntity.favorite,
            receiptEntity.category?.let { categoryMapper.toDomain(it) },
            receiptEntity.ingredients.map { ingredientMapper.toDomain(it) }.toMutableSet(),
            receiptEntity.steps.map { stepMapper.toDomain(it) }.toMutableSet(),
            receiptEntity.photos.map { photoMapper.toDomain(it) }.toMutableSet(),
            receiptEntity.tags.map { tagMapper.toDomain(it) }.toMutableSet(),
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
            receipt.author?.let { authorMapper.toEntity(it) },
            receipt.source,
            receipt.favorite,
            receipt.category?.let { categoryMapper.toEntity(it) },
            receipt.ingredients.map { ingredientMapper.toEntity(it) }.toMutableSet(),
            receipt.steps.map { stepMapper.toEntity(it) }.toMutableSet(),
            receipt.photos.map { photoMapper.toEntity(it) }.toMutableSet(),
            receipt.tags.map { tagMapper.toEntity(it) }.toMutableSet(),
            receipt.deleted,
            receipt.createdAt,
            receipt.modifiedAt
        )
    }

}