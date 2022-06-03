package pl.szczeliniak.kitchenassistant.receipt.commands

import pl.szczeliniak.kitchenassistant.receipt.*
import pl.szczeliniak.kitchenassistant.receipt.commands.dto.UpdateStepDto
import pl.szczeliniak.kitchenassistant.shared.dtos.SuccessResponse
import spock.lang.Specification
import spock.lang.Subject

import java.time.ZonedDateTime

class UpdateStepCommandSpec extends Specification {

    def receiptDao = Mock(ReceiptDao)
    def stepDao = Mock(StepDao)

    @Subject
    def updateStepCommand = new UpdateStepCommand(receiptDao, stepDao)

    def 'should update step'() {
        given:
        def step = step()
        receiptDao.findById(1) >> receipt(Set.of(step))
        stepDao.save(step) >> step

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

    private static Receipt receipt(Set<Step> steps) {
        return new Receipt(1, 1, "", "", new Author(2, "", 1, ZonedDateTime.now(), ZonedDateTime.now()), "", false, null, Collections.emptySet(),
                steps, Collections.emptySet(), Collections.emptySet(), false, ZonedDateTime.now(), ZonedDateTime.now())
    }

    private static Step step() {
        return new Step(2, "", "", 0, false, ZonedDateTime.now(), ZonedDateTime.now())
    }

}