package pl.szczeliniak.kitchenassistant.recipe.commands

import pl.szczeliniak.kitchenassistant.recipe.commands.dto.UpdateStepDto
import pl.szczeliniak.kitchenassistant.recipe.db.Author
import pl.szczeliniak.kitchenassistant.recipe.db.Recipe
import pl.szczeliniak.kitchenassistant.recipe.db.RecipeDao
import pl.szczeliniak.kitchenassistant.recipe.db.Step
import pl.szczeliniak.kitchenassistant.shared.dtos.SuccessResponse
import spock.lang.Specification
import spock.lang.Subject

import java.time.ZonedDateTime

class UpdateStepCommandSpec extends Specification {

    def recipeDao = Mock(RecipeDao)

    @Subject
    def updateStepCommand = new UpdateStepCommand(recipeDao)

    def 'should update step'() {
        given:
        def step = step()
        def recipe = recipe(Set.of(step))
        recipeDao.findById(1) >> recipe
        recipeDao.save(recipe) >> recipe

        when:
        def result = updateStepCommand.execute(1, 2, updateStepDto())

        then:
        step.name == "NAME"
        step.description == "DESCRIPTION"
        step.sequence == 1
        result == new SuccessResponse(2)
    }

    private static UpdateStepDto updateStepDto() {
        return new UpdateStepDto("NAME", "DESCRIPTION", 1)
    }

    private static Recipe recipe(Set<Step> steps) {
        return new Recipe(1, "", 1, "", new Author(2, "", 1, ZonedDateTime.now(), ZonedDateTime.now()), "", false, null, Collections.emptySet(),
                steps, null, Collections.emptySet(), ZonedDateTime.now(), ZonedDateTime.now())
    }

    private static Step step() {
        return new Step(2, "", "", 0, ZonedDateTime.now(), ZonedDateTime.now())
    }

}