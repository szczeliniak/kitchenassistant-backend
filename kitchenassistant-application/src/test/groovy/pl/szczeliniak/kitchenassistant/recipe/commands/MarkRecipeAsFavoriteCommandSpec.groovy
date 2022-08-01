package pl.szczeliniak.kitchenassistant.recipe.commands

import pl.szczeliniak.kitchenassistant.recipe.Author
import pl.szczeliniak.kitchenassistant.recipe.Recipe
import pl.szczeliniak.kitchenassistant.recipe.RecipeDao
import pl.szczeliniak.kitchenassistant.shared.dtos.SuccessResponse
import spock.lang.Specification
import spock.lang.Subject

import java.time.ZonedDateTime

class MarkRecipeAsFavoriteCommandSpec extends Specification {

    def recipeDao = Mock(RecipeDao)

    @Subject
    def markRecipeAsFavoriteCommand = new MarkRecipeAsFavoriteCommand(recipeDao)

    def 'should mark recipe as favorite'() {
        given:
        def recipe = recipe()
        recipeDao.findById(1) >> recipe
        recipeDao.save(recipe) >> recipe

        when:
        def result = markRecipeAsFavoriteCommand.execute(1, true)

        then:
        recipe.favorite
        result == new SuccessResponse(1)
    }

    private static Recipe recipe() {
        return new Recipe(1, '', 2, '', new Author(2, "", 1, ZonedDateTime.now(), ZonedDateTime.now()), '', false, null, Collections.emptySet(), Collections.emptySet(), Collections.emptySet(), Collections.emptySet(), false, ZonedDateTime.now(), ZonedDateTime.now())
    }

}
