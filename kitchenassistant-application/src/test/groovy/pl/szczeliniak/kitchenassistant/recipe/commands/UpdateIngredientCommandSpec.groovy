package pl.szczeliniak.kitchenassistant.recipe.commands

import pl.szczeliniak.kitchenassistant.recipe.*
import pl.szczeliniak.kitchenassistant.recipe.commands.dto.UpdateIngredientDto
import pl.szczeliniak.kitchenassistant.shared.dtos.SuccessResponse
import spock.lang.Specification
import spock.lang.Subject

import java.time.ZonedDateTime

class UpdateIngredientCommandSpec extends Specification {

    def recipeDao = Mock(RecipeDao)

    @Subject
    def updateIngredientCommand = new UpdateIngredientCommand(recipeDao)

    def 'should update ingredient without ingredient group'() {
        given:
        def ingredient = ingredient()
        def ingredientGroup = group(2, Set.of(ingredient))
        def recipe = recipe(Set.of(ingredientGroup))
        recipeDao.findById(1) >> recipe
        recipeDao.save(recipe) >> recipe

        when:
        def result = updateIngredientCommand.execute(1, 2, 3, updateIngredientDto(2))

        then:
        ingredientGroup.ingredients.contains(ingredient)
        ingredient.name == "NAME"
        ingredient.quantity == "QUANTITY"
        result == new SuccessResponse(3)
    }

    def 'should update ingredient with ingredient group'() {
        given:
        def ingredient = ingredient()
        def ingredientGroup = group(2, new HashSet<Ingredient>(Arrays.asList(ingredient)))
        def newIngredientGroup = group(4, new HashSet<Ingredient>())
        def recipe = recipe(Set.of(ingredientGroup, newIngredientGroup))
        recipeDao.findById(1) >> recipe
        recipeDao.save(recipe) >> recipe

        when:
        def result = updateIngredientCommand.execute(1, 2, 3, updateIngredientDto(4))

        then:
        ingredientGroup.ingredients.isEmpty()
        newIngredientGroup.ingredients.contains(ingredient)
        ingredient.name == "NAME"
        ingredient.quantity == "QUANTITY"
        result == new SuccessResponse(3)
    }

    private static UpdateIngredientDto updateIngredientDto(Integer ingredientGroupId) {
        return new UpdateIngredientDto("NAME", "QUANTITY", ingredientGroupId)
    }

    private static Recipe recipe(Set<IngredientGroup> ingredientGroups) {
        return new Recipe(1, "", 1, "", new Author(2, "", 1, ZonedDateTime.now(), ZonedDateTime.now()), "", false, null, ingredientGroups,
                Collections.emptySet(), Collections.emptySet(), Collections.emptySet(), false, ZonedDateTime.now(), ZonedDateTime.now())
    }

    private static Ingredient ingredient() {
        return new Ingredient(3, "", "", false, ZonedDateTime.now(), ZonedDateTime.now())
    }

    private static IngredientGroup group(Integer id, Set<Ingredient> ingredients) {
        return new IngredientGroup(id, "GROUP_NAME", ingredients, false, ZonedDateTime.now(), ZonedDateTime.now())
    }

}