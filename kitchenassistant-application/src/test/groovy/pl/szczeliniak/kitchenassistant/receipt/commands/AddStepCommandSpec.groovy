package pl.szczeliniak.kitchenassistant.receipt.commands

import pl.szczeliniak.kitchenassistant.dto.SuccessResponse
import pl.szczeliniak.kitchenassistant.receipt.Receipt
import pl.szczeliniak.kitchenassistant.receipt.ReceiptDao
import pl.szczeliniak.kitchenassistant.receipt.Step
import pl.szczeliniak.kitchenassistant.receipt.commands.dto.NewStepDto
import pl.szczeliniak.kitchenassistant.receipt.commands.factories.StepFactory
import spock.lang.Specification
import spock.lang.Subject

import java.time.LocalDateTime

class AddStepCommandSpec extends Specification {
    def receiptDao = Mock(ReceiptDao)
    def stepFactory = Mock(StepFactory)
    @Subject
    def addStepCommand = new AddStepCommand(receiptDao, stepFactory)

    def 'should save step'() {
        given:
        def receipt = receipt()
        def dto = newStepDto()
        def step = step()
        receiptDao.findById(1) >> receipt
        stepFactory.create(dto) >> step
        receiptDao.save(receipt) >> receipt

        when:
        def result = addStepCommand.execute(1, dto)

        then:
        result == new SuccessResponse()
        receipt.steps == Collections.singletonList(step)
    }

    private static NewStepDto newStepDto() {
        return new NewStepDto("", "", 0)
    }

    private static Step step() {
        return new Step(0, "", "", 0, false, LocalDateTime.now(), LocalDateTime.now())
    }

    private static Receipt receipt() {
        return new Receipt(0, 0, "", "", "", "", Collections.emptyList(), new ArrayList<Step>(), false, LocalDateTime.now(), LocalDateTime.now())
    }

}
