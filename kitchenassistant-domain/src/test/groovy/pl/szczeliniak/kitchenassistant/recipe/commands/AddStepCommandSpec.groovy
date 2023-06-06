package pl.szczeliniak.kitchenassistant.recipe.commands

import pl.szczeliniak.kitchenassistant.recipe.commands.dto.NewStepDto
import pl.szczeliniak.kitchenassistant.recipe.commands.factories.StepFactory
import pl.szczeliniak.kitchenassistant.recipe.db.*
import pl.szczeliniak.kitchenassistant.shared.dtos.SuccessResponse
import pl.szczeliniak.kitchenassistant.user.db.User
import spock.lang.Specification
import spock.lang.Subject

import java.time.ZonedDateTime

class AddStepCommandSpec extends Specification {
    def recipeDao = Mock(RecipeDao)
    def stepDao = Mock(StepDao)
    def stepFactory = Mock(StepFactory)
    @Subject
    def addStepCommand = new AddStepCommand(recipeDao, stepDao, stepFactory)

    def 'should save step'() {
        given:
        def recipe = recipe()
        def dto = newStepDto()
        def step = step()
        recipeDao.findById(1) >> recipe
        stepFactory.create(dto) >> step
        stepDao.save(step) >> step
        recipeDao.save(recipe) >> recipe

        when:
        def result = addStepCommand.execute(1, dto)

        then:
        result == new SuccessResponse(2)
        recipe.steps == Set.of(step)
    }

    private static NewStepDto newStepDto() {
        return new NewStepDto("", "", 0)
    }

    private static Step step() {
        return new Step(2, "", "", 0, ZonedDateTime.now(), ZonedDateTime.now())
    }

    private static Recipe recipe() {
        return new Recipe(1, "", new User(0, "", "", "", ZonedDateTime.now(), ZonedDateTime.now()), "", new Author(2, "", new User(1, "", "", "", ZonedDateTime.now(), ZonedDateTime.now()), ZonedDateTime.now(), ZonedDateTime.now()), "", false, null, Collections.emptySet(), new HashSet<Step>(), null, Collections.emptySet(), ZonedDateTime.now(), ZonedDateTime.now())
    }

}
