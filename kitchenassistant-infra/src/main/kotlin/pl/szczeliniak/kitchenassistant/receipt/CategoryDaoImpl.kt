package pl.szczeliniak.kitchenassistant.receipt

import org.springframework.stereotype.Component
import pl.szczeliniak.kitchenassistant.receipt.persistance.CategoryRepository

@Component
class CategoryDaoImpl(
    private val categoryRepository: CategoryRepository,
    private val categoryMapper: CategoryMapper
) : CategoryDao {

    override fun findAll(criteria: CategoryCriteria): List<Category> {
        return categoryRepository.findAll(CategoryRepository.SearchCriteria(criteria.userId))
            .map { categoryMapper.toDomain(it) }
    }

    override fun save(category: Category): Category {
        return categoryMapper.toDomain(categoryRepository.save(categoryMapper.toEntity(category)))
    }

}