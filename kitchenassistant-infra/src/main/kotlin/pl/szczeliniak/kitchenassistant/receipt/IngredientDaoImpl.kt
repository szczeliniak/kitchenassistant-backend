package pl.szczeliniak.kitchenassistant.receipt

import org.springframework.stereotype.Component

@Component
class IngredientDaoImpl(
    private val ingredientRepository: IngredientRepository,
    private val ingredientMapper: IngredientMapper
) : IngredientDao {

    override fun save(ingredient: Ingredient): Ingredient {
        return ingredientMapper.toDomain(ingredientRepository.save(ingredientMapper.toEntity(ingredient)))
    }

}