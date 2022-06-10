package pl.szczeliniak.kitchenassistant.receipt

import org.springframework.stereotype.Component

@Component
class IngredientGroupDaoImpl(
    private val ingredientGroupRepository: IngredientGroupRepository,
    private val ingredientGroupMapper: IngredientGroupMapper
) : IngredientGroupDao {

    override fun save(ingredientGroup: IngredientGroup): IngredientGroup {
        return ingredientGroupMapper.toDomain(
            ingredientGroupRepository.save(
                ingredientGroupMapper.toEntity(
                    ingredientGroup
                )
            )
        )
    }

}