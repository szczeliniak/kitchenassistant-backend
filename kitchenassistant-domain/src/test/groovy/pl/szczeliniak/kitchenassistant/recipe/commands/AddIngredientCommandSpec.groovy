package pl.szczeliniak.kitchenassistant.recipe.commands

import pl.szczeliniak.kitchenassistant.recipe.commands.dto.NewIngredientDto
import pl.szczeliniak.kitchenassistant.recipe.commands.factories.IngredientFactory
import pl.szczeliniak.kitchenassistant.recipe.db.*
import pl.szczeliniak.kitchenassistant.shared.dtos.SuccessResponse
import spock.lang.Specification
import spock.lang.Subject

import java.time.ZonedDateTime

class AddIngredientCommandSpec extends Specification {
    def recipeDao = Mock(RecipeDao)
    def ingredientDao = Mock(IngredientDao)
    def ingredientFactory = Mock(IngredientFactory)
    @Subject
    def addIngredientCommand = new AddIngredientCommand(recipeDao, ingredientDao, ingredientFactory)

    def 'should save ingredient'() {
        given:
        def recipe = recipe()
        def dto = newIngredientDto()
        def ingredient = ingredient()
        recipeDao.findById(1) >> recipe
        ingredientFactory.create(dto) >> ingredient
        ingredientDao.save(ingredient) >> ingredient
        recipeDao.save(recipe) >> recipe

        when:
        def result = addIngredientCommand.execute(1, 2, dto)

        then:
        result == new SuccessResponse(3)
        recipe.ingredientGroups[0].ingredients == Set.of(ingredient)
    }

    private static NewIngredientDto newIngredientDto() {
        return new NewIngredientDto("", "")
    }

    private static Ingredient ingredient() {
        return new Ingredient(3, "", "", ZonedDateTime.now(), ZonedDateTime.now())
    }

    private static Recipe recipe() {
        return new Recipe(1, "", 0, "", new Author(2, "", 1, ZonedDateTime.now(), ZonedDateTime.now()), "", false, null, Collections.singleton(ingredientGroup()), Collections.emptySet(), null, Collections.emptySet(), ZonedDateTime.now(), ZonedDateTime.now())
    }

    private static IngredientGroup ingredientGroup() {
        return new IngredientGroup(2, "GROUP_NAME", new HashSet<Ingredient>(), ZonedDateTime.now(), ZonedDateTime.now())
    }

}
