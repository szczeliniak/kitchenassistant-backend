package pl.szczeliniak.kitchenassistant.recipe.commands


import pl.szczeliniak.kitchenassistant.recipe.IngredientGroup
import pl.szczeliniak.kitchenassistant.recipe.IngredientGroupDao
import pl.szczeliniak.kitchenassistant.recipe.Recipe
import pl.szczeliniak.kitchenassistant.recipe.RecipeDao
import pl.szczeliniak.kitchenassistant.recipe.commands.dto.NewIngredientGroupDto
import pl.szczeliniak.kitchenassistant.recipe.commands.factories.IngredientGroupFactory
import pl.szczeliniak.kitchenassistant.shared.dtos.SuccessResponse
import spock.lang.Specification
import spock.lang.Subject

import java.time.ZonedDateTime

class AddIngredientGroupCommandSpec extends Specification {

    def recipeDao = Mock(RecipeDao)
    def ingredientGroupFactory = Mock(IngredientGroupFactory)
    def ingredientGroupDao = Mock(IngredientGroupDao)

    @Subject
    def addIngredientGroupCommand = new AddIngredientGroupCommand(recipeDao, ingredientGroupFactory, ingredientGroupDao)

    def 'should add ingredient group'() {
        given:
        def newIngredientGroupDto = newIngredientGroupDto()
        def ingredientGroup = ingredientGroup()
        def recipe = recipe()
        recipeDao.findById(1) >> recipe
        ingredientGroupFactory.create(newIngredientGroupDto) >> ingredientGroup
        ingredientGroupDao.save(ingredientGroup) >> ingredientGroup
        recipeDao.save(recipe) >> recipe
        when:
        def result = addIngredientGroupCommand.execute(1, newIngredientGroupDto)
        then:
        result == new SuccessResponse(2)
        recipe.ingredientGroups.contains(ingredientGroup)
    }

    private static NewIngredientGroupDto newIngredientGroupDto() {
        return new NewIngredientGroupDto("GROUP", Collections.emptySet())
    }

    private static IngredientGroup ingredientGroup() {
        return new IngredientGroup(2, "NAME", Collections.emptySet(), false, ZonedDateTime.now(), ZonedDateTime.now())
    }

    private static Recipe recipe() {
        return new Recipe(1, "", 1, "", null, "", false, null, new HashSet<IngredientGroup>(), Collections.emptySet(), Collections.emptySet(), Collections.emptySet(), false, ZonedDateTime.now(), ZonedDateTime.now())
    }
}