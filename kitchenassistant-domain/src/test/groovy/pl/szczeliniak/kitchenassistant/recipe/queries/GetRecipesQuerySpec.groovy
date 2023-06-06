package pl.szczeliniak.kitchenassistant.recipe.queries

import pl.szczeliniak.kitchenassistant.recipe.db.Author
import pl.szczeliniak.kitchenassistant.recipe.db.Recipe
import pl.szczeliniak.kitchenassistant.recipe.db.RecipeCriteria
import pl.szczeliniak.kitchenassistant.recipe.db.RecipeDao
import pl.szczeliniak.kitchenassistant.recipe.queries.dto.RecipeDto
import pl.szczeliniak.kitchenassistant.recipe.queries.dto.RecipesResponse
import pl.szczeliniak.kitchenassistant.shared.dtos.Pagination
import pl.szczeliniak.kitchenassistant.user.db.User
import spock.lang.Specification
import spock.lang.Subject

import java.time.ZonedDateTime

class GetRecipesQuerySpec extends Specification {

    private RecipeDao recipeDao = Mock(RecipeDao)
    private recipeConverter = Mock(RecipeConverter)

    @Subject
    private GetRecipesQuery getRecipesQuery = new GetRecipesQuery(recipeDao, recipeConverter)

    def "should return recipe"() {
        given:
        def criteria = new RecipeCriteria(false, 1, 1, '', '', null)
        def recipe = recipe(user())
        def recipeDto = recipeDto()
        recipeDao.findAll(criteria, 40, 10) >> Collections.singletonList(recipe)
        recipeDao.count(criteria) >> 413
        recipeConverter.map(recipe) >> recipeDto

        when:
        def result = getRecipesQuery.execute(5, 10, criteria)

        then:
        result == new RecipesResponse(Collections.singletonList(recipeDto),
                new Pagination(5, 10, 42))
    }

    private static Recipe recipe(User user) {
        return new Recipe(1,
                '',
                user,
                '',
                new Author(2, "", user, ZonedDateTime.now(), ZonedDateTime.now()),
                '',
                false,
                null,
                Collections.emptySet(),
                Collections.emptySet(),
                null,
                Collections.emptySet(),
                ZonedDateTime.now(),
                ZonedDateTime.now())
    }

    private static RecipeDto recipeDto() {
        return new RecipeDto(1, '', "", false, null, Collections.emptySet(), null)
    }

    private static User user() {
        return new User(1, "", "", "", ZonedDateTime.now(), ZonedDateTime.now())
    }

}
