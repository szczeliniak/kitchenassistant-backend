package pl.szczeliniak.kitchenassistant.receipt.commands

import pl.szczeliniak.kitchenassistant.common.dto.SuccessResponse
import pl.szczeliniak.kitchenassistant.receipt.Receipt
import pl.szczeliniak.kitchenassistant.receipt.ReceiptDao
import pl.szczeliniak.kitchenassistant.receipt.Step
import pl.szczeliniak.kitchenassistant.receipt.StepDao
import pl.szczeliniak.kitchenassistant.receipt.commands.dto.UpdateStepDto
import spock.lang.Specification
import spock.lang.Subject

import java.time.LocalDateTime

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
        return new Receipt(1, 1, "", "", "", "", null, Collections.emptySet(),
                steps, Collections.emptySet(), Collections.emptySet(), false, LocalDateTime.now(), LocalDateTime.now())
    }

    private static Step step() {
        return new Step(2, "", "", 0, Collections.emptySet(), false, LocalDateTime.now(), LocalDateTime.now())
    }

}