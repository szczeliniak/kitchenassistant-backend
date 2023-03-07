package pl.szczeliniak.kitchenassistant.recipe.queries

import pl.szczeliniak.kitchenassistant.recipe.db.Author
import pl.szczeliniak.kitchenassistant.recipe.db.Recipe
import pl.szczeliniak.kitchenassistant.recipe.db.RecipeDao
import pl.szczeliniak.kitchenassistant.recipe.queries.dto.RecipeDetailsDto
import pl.szczeliniak.kitchenassistant.recipe.queries.dto.RecipeResponse
import pl.szczeliniak.kitchenassistant.shared.KitchenAssistantException
import spock.lang.Specification
import spock.lang.Subject

import java.time.ZonedDateTime

class GetRecipeQuerySpec extends Specification {

    private RecipeDao recipeDao = Mock(RecipeDao)
    private recipeConverter = Mock(RecipeConverter)

    @Subject
    private GetRecipeQuery getRecipeQuery = new GetRecipeQuery(recipeDao, recipeConverter)

    def "should return recipe"() {
        given:
        def recipe = recipe()
        def recipeDetailsDto = recipeDetailsDto()
        recipeDao.findById(1) >> recipe
        recipeConverter.mapDetails(recipe) >> recipeDetailsDto

        when:
        def result = getRecipeQuery.execute(1)

        then:
        result == new RecipeResponse(recipeDetailsDto)
    }

    def "should throw exception recipe not found"() {
        given:
        recipeDao.findById(1) >> null

        when:
        getRecipeQuery.execute(1)

        then:
        def e = thrown(KitchenAssistantException)
        e.message == "Recipe not found"
    }

    private static Recipe recipe() {
        return new Recipe(1,
                '',
                0,
                '',
                new Author(2, "", 1, ZonedDateTime.now(), ZonedDateTime.now()),
                '',
                false,
                null,
                Collections.emptySet(),
                Collections.emptySet(),
                Collections.emptySet(),
                Collections.emptySet(),
                false,
                ZonedDateTime.now(),
                ZonedDateTime.now())
    }

    private static RecipeDetailsDto recipeDetailsDto() {
        return new RecipeDetailsDto(1, '', '', "", "", null, null, Collections.emptySet(), Collections.emptySet(), Collections.emptySet(), Collections.emptySet())
    }

}
