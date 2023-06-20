package pl.szczeliniak.kitchenassistant.recipe.commands.factories

import org.assertj.core.api.Assertions
import pl.szczeliniak.kitchenassistant.recipe.commands.dto.NewStepRequest
import pl.szczeliniak.kitchenassistant.recipe.db.Step
import spock.lang.Specification
import spock.lang.Subject

import java.time.ZonedDateTime

class StepFactorySpec extends Specification {

    @Subject
    def stepFactory = new StepFactory()

    def 'should create step'() {
        when:
        def result = stepFactory.create(newStepRequest())

        then:
        Assertions.assertThat(result).usingRecursiveComparison()
                .ignoringFields("createdAt", "modifiedAt")
                .isEqualTo(step())
    }

    private static NewStepRequest newStepRequest() {
        return new NewStepRequest("NAME", "DESCRIPTION", 1)
    }

    private static Step step() {
        return new Step(0, "NAME", "DESCRIPTION", 1, ZonedDateTime.now(), ZonedDateTime.now())
    }

}