package pl.szczeliniak.kitchenassistant.recipe.commands


import pl.szczeliniak.kitchenassistant.recipe.db.Author
import pl.szczeliniak.kitchenassistant.recipe.db.Recipe
import pl.szczeliniak.kitchenassistant.recipe.db.RecipeDao
import pl.szczeliniak.kitchenassistant.recipe.db.Step
import pl.szczeliniak.kitchenassistant.shared.KitchenAssistantException
import pl.szczeliniak.kitchenassistant.shared.dtos.SuccessResponse
import spock.lang.Specification
import spock.lang.Subject

import java.time.ZonedDateTime

class DeleteStepCommandSpec extends Specification {

    def recipeDao = Mock(RecipeDao)

    @Subject
    def deleteStepCommand = new DeleteStepCommand(recipeDao)

    def 'should delete step'() {
        given:
        def step = step(false)
        def recipe = recipe(Set.of(step))
        recipeDao.findById(1) >> recipe
        recipeDao.save(recipe) >> recipe

        when:
        def result = deleteStepCommand.execute(1, 3)

        then:
        step.deleted
        result == new SuccessResponse(3)
    }

    def 'should throw exception when step not found'() {
        given:
        def recipe = recipe(Collections.emptySet())
        recipeDao.findById(1) >> recipe

        when:
        deleteStepCommand.execute(1, 3)

        then:
        def e = thrown(KitchenAssistantException)
        e.message == "Step not found"
    }

    def 'should throw exception when step is already marked as deleted'() {
        given:
        def step = step(true)
        def recipe = recipe(Set.of(step))
        recipeDao.findById(1) >> recipe

        when:
        deleteStepCommand.execute(1, 3)

        then:
        def e = thrown(KitchenAssistantException)
        e.message == "Step is already marked as deleted!"
    }

    private static Recipe recipe(Set<Step> steps) {
        return new Recipe(1, '', 2, '', new Author(2, "", 1, ZonedDateTime.now(), ZonedDateTime.now()), '', false, null, Collections.emptySet(), steps, null, Collections.emptySet(), false, ZonedDateTime.now(), ZonedDateTime.now())
    }

    private static Step step(boolean deleted) {
        return new Step(3, '', '', 0, deleted, ZonedDateTime.now(), ZonedDateTime.now())
    }

}
