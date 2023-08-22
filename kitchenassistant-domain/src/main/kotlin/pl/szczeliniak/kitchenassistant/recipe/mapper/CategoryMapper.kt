package pl.szczeliniak.kitchenassistant.recipe.mapper

import org.mapstruct.Mapper
import pl.szczeliniak.kitchenassistant.recipe.db.Category
import pl.szczeliniak.kitchenassistant.recipe.dto.response.CategoriesResponse

@Mapper
abstract class CategoryMapper {

    abstract fun map(category: Category): CategoriesResponse.CategoryDto

}