package pl.szczeliniak.kitchenassistant.recipe.commands

import pl.szczeliniak.kitchenassistant.recipe.db.*
import pl.szczeliniak.kitchenassistant.shared.dtos.SuccessResponse
import spock.lang.Specification
import spock.lang.Subject

import java.time.ZonedDateTime

class DeleteCategoryCommandSpec extends Specification {

    def categoryDao = Mock(CategoryDao)
    def recipeDao = Mock(RecipeDao)

    @Subject
    def deleteCategoryCommand = new DeleteCategoryCommand(categoryDao, recipeDao)

    def 'should delete category'() {
        given:
        def category = category()
        def recipe = recipe()

        categoryDao.findById(1) >> category
        recipeDao.findAll(new RecipeCriteria(false, 3, 1, null, null), null, null) >> List.of(recipe)

        when:
        def result = deleteCategoryCommand.execute(1)

        then:
        recipe.category == null
        result == new SuccessResponse(1)
        1 * recipeDao.save(Set.of(recipe))
        1 * categoryDao.delete(category)
    }

    private static Category category() {
        return new Category(1, '', 3, 2, ZonedDateTime.now(), ZonedDateTime.now())
    }

    private static Recipe recipe() {
        return new Recipe(10, "", 1, "", null, null, false, category(), Collections.emptySet(), Collections.emptySet(), null, Collections.emptySet(), ZonedDateTime.now(), ZonedDateTime.now())
    }

}
