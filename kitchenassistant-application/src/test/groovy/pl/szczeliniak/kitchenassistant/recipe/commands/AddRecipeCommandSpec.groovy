package pl.szczeliniak.kitchenassistant.recipe.commands

import pl.szczeliniak.kitchenassistant.recipe.Author
import pl.szczeliniak.kitchenassistant.recipe.Recipe
import pl.szczeliniak.kitchenassistant.recipe.RecipeDao
import pl.szczeliniak.kitchenassistant.recipe.commands.dto.NewRecipeDto
import pl.szczeliniak.kitchenassistant.recipe.commands.factories.RecipeFactory
import pl.szczeliniak.kitchenassistant.shared.dtos.SuccessResponse
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
        def dto = newRecipeDto()
        recipeFactory.create(dto) >> recipe
        recipeDao.save(recipe) >> recipe

        when:
        def result = addRecipeCommand.execute(dto)

        then:
        result == new SuccessResponse(1)
    }

    private static NewRecipeDto newRecipeDto() {
        return new NewRecipeDto(1, "", null, "", "", "", Collections.emptySet(), Collections.emptySet(), Collections.emptySet(), Collections.emptySet())
    }

    private static Recipe recipe() {
        return new Recipe(1, "", 2, "", new Author(2, "", 1, ZonedDateTime.now(), ZonedDateTime.now()), "", false, null, Collections.emptySet(), Collections.emptySet(), Collections.emptySet(), Collections.emptySet(), false, ZonedDateTime.now(), ZonedDateTime.now())
    }

}
