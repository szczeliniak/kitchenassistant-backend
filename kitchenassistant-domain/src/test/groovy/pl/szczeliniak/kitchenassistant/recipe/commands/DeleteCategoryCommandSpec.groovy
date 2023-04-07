package pl.szczeliniak.kitchenassistant.recipe.commands


import pl.szczeliniak.kitchenassistant.recipe.db.Category
import pl.szczeliniak.kitchenassistant.recipe.db.CategoryDao
import pl.szczeliniak.kitchenassistant.recipe.db.Recipe
import pl.szczeliniak.kitchenassistant.recipe.db.RecipeCriteria
import pl.szczeliniak.kitchenassistant.recipe.db.RecipeDao
import pl.szczeliniak.kitchenassistant.shared.KitchenAssistantException
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
        def category = category(false)
        def recipe = recipe()

        categoryDao.findById(1) >> category
        categoryDao.save(category) >> category
        recipeDao.findAll(new RecipeCriteria(false, 3, 1, null, null), null, null) >> List.of(recipe)

        when:
        def result = deleteCategoryCommand.execute(1)

        then:
        category.deleted
        recipe.category == null
        result == new SuccessResponse(1)
        1 * recipeDao.save(Set.of(recipe))
    }

    def 'should throw exception when category not found'() {
        given:
        categoryDao.findById(1) >> null

        when:
        deleteCategoryCommand.execute(1)

        then:
        def e = thrown(KitchenAssistantException)
        e.message == "Category not found"
    }

    def 'should throw exception when category is already marked as deleted'() {
        given:
        def category = category(true)
        categoryDao.findById(1) >> category

        when:
        deleteCategoryCommand.execute(1)

        then:
        def e = thrown(KitchenAssistantException)
        e.message == "Category is already marked as deleted!"
    }

    private static Category category(boolean deleted) {
        return new Category(1, '', 3, 2, deleted, ZonedDateTime.now(), ZonedDateTime.now())
    }

    private static Recipe recipe() {
        return new Recipe(10, "", 1, "", null, null, false, category(false), Collections.emptySet(), Collections.emptySet(), null, Collections.emptySet(), false, ZonedDateTime.now(), ZonedDateTime.now())
    }

}
