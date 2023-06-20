package pl.szczeliniak.kitchenassistant.recipe.commands

import pl.szczeliniak.kitchenassistant.recipe.commands.dto.NewRecipeRequest
import pl.szczeliniak.kitchenassistant.recipe.commands.factories.RecipeFactory
import pl.szczeliniak.kitchenassistant.recipe.db.Author
import pl.szczeliniak.kitchenassistant.recipe.db.Recipe
import pl.szczeliniak.kitchenassistant.recipe.db.RecipeDao
import pl.szczeliniak.kitchenassistant.shared.dtos.SuccessResponse
import pl.szczeliniak.kitchenassistant.user.db.User
import spock.lang.Specification
import spock.lang.Subject

import java.time.ZonedDateTime

class AddRecipeCommandSpec extends Specification {
    def recipeDao = Mock(RecipeDao)
    def recipeFactory = Mock(RecipeFactory)
    @Subject
    def addRecipeCommand = new AddRecipeCommand(recipeDao, recipeFactory)

    def 'should save recipe'() {
        given:
        def recipe = recipe()
        def request = newRecipeRequest()
        recipeFactory.create(request) >> recipe
        recipeDao.save(recipe) >> recipe

        when:
        def result = addRecipeCommand.execute(request)

        then:
        result == new SuccessResponse(2)
    }

    private static NewRecipeRequest newRecipeRequest() {
        return new NewRecipeRequest(1, "", null, "", "", "", null, Collections.emptySet(), Collections.emptySet(), Collections.emptySet())
    }

    private static Recipe recipe() {
        return new Recipe(2, "", new User(1, "", "", "", ZonedDateTime.now(), ZonedDateTime.now()), "", new Author(2, "", new User(1, "", "", "", ZonedDateTime.now(), ZonedDateTime.now()), ZonedDateTime.now(), ZonedDateTime.now()), "", false, null, Collections.emptySet(), Collections.emptySet(), null, Collections.emptySet(), ZonedDateTime.now(), ZonedDateTime.now())
    }

}
