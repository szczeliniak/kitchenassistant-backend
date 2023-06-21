package pl.szczeliniak.kitchenassistant.recipe.commands

import pl.szczeliniak.kitchenassistant.recipe.db.Author
import pl.szczeliniak.kitchenassistant.recipe.db.Recipe
import pl.szczeliniak.kitchenassistant.recipe.db.RecipeDao
import pl.szczeliniak.kitchenassistant.recipe.db.Step
import pl.szczeliniak.kitchenassistant.shared.dtos.SuccessResponse
import pl.szczeliniak.kitchenassistant.user.db.User
import spock.lang.Specification
import spock.lang.Subject

import java.time.ZonedDateTime

class DeleteStepCommandSpec extends Specification {

    def recipeDao = Mock(RecipeDao)

    @Subject
    def deleteStepCommand = new DeleteStepCommand(recipeDao)

    def 'should delete step'() {
        given:
        def recipe = recipe()
        recipeDao.findById(1) >> recipe

        when:
        def result = deleteStepCommand.execute(1, 3)

        then:
        result == new SuccessResponse(3)
        recipe.steps.isEmpty()
        1 * recipeDao.save(recipe)
    }

    private static Recipe recipe() {
        return new Recipe(1, '', new User(2, "", "", "", ZonedDateTime.now(), ZonedDateTime.now()), '', new Author(2, "", new User(1, "", "", "", ZonedDateTime.now(), ZonedDateTime.now()), ZonedDateTime.now(), ZonedDateTime.now()), '', false, null, Collections.emptySet(), new HashSet<Step>(Collections.singleton(step())), null, Collections.emptySet(), ZonedDateTime.now(), ZonedDateTime.now())
    }

    private static Step step() {
        return new Step(3, '', '', 0, ZonedDateTime.now(), ZonedDateTime.now())
    }

}
