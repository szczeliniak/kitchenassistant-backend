package pl.szczeliniak.kitchenassistant.receipt.commands.factories

import org.assertj.core.api.Assertions
import pl.szczeliniak.kitchenassistant.receipt.Step
import pl.szczeliniak.kitchenassistant.receipt.commands.dto.NewStepDto
import spock.lang.Specification
import spock.lang.Subject

import java.time.LocalDateTime

class StepFactorySpec extends Specification {

    @Subject
    def stepFactory = new StepFactory()

    def 'should create step'() {
        when:
        def result = stepFactory.create(newStepDto())

        then:
        Assertions.assertThat(result).usingRecursiveComparison()
                .ignoringFields("createdAt_", "modifiedAt_")
                .isEqualTo(step())
    }

    private static NewStepDto newStepDto() {
        return new NewStepDto("TITLE", "DESCRIPTION", 1)
    }

    private static Step step() {
        return new Step(0, "TITLE", "DESCRIPTION", 1, false, LocalDateTime.now(), LocalDateTime.now())
    }

}