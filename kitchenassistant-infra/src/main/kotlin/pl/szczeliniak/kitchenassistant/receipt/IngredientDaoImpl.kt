package pl.szczeliniak.kitchenassistant.receipt

import org.springframework.stereotype.Component
import pl.szczeliniak.kitchenassistant.receipt.persistance.IngredientRepository

@Component
class IngredientDaoImpl(
    private val ingredientRepository: IngredientRepository,
    private val ingredientMapper: IngredientMapper
) : IngredientDao {

    override fun save(ingredient: Ingredient): Ingredient {
        return ingredientMapper.toDomain(ingredientRepository.save(ingredientMapper.toEntity(ingredient)))
    }

}