package pl.szczeliniak.kitchenassistant.receipt.commands

import pl.szczeliniak.kitchenassistant.receipt.*
import pl.szczeliniak.kitchenassistant.shared.KitchenAssistantException
import pl.szczeliniak.kitchenassistant.shared.dtos.SuccessResponse
import spock.lang.Specification
import spock.lang.Subject

import java.time.ZonedDateTime

class DeleteStepCommandSpec extends Specification {

    def receiptDao = Mock(ReceiptDao)
    def stepDao = Mock(StepDao)

    @Subject
    def deleteStepCommand = new DeleteStepCommand(receiptDao, stepDao)

    def 'should delete step'() {
        given:
        def step = step(false)
        def receipt = receipt(Set.of(step))
        receiptDao.findById(1) >> receipt
        stepDao.save(step) >> step

        when:
        def result = deleteStepCommand.execute(1, 3)

        then:
        step.deleted
        result == new SuccessResponse(1)
    }

    def 'should throw exception when step not found'() {
        given:
        def receipt = receipt(Collections.emptySet())
        receiptDao.findById(1) >> receipt

        when:
        deleteStepCommand.execute(1, 3)

        then:
        def e = thrown(KitchenAssistantException)
        e.message == "Step not found"
    }

    def 'should throw exception when step is already marked as deleted'() {
        given:
        def step = step(true)
        def receipt = receipt(Set.of(step))
        receiptDao.findById(1) >> receipt

        when:
        deleteStepCommand.execute(1, 3)

        then:
        def e = thrown(KitchenAssistantException)
        e.message == "Step is already marked as deleted!"
    }

    private static Receipt receipt(Set<Step> steps) {
        return new Receipt(1, 2, '', '', new Author(2, "", 1, ZonedDateTime.now(), ZonedDateTime.now()), '', false, null, Collections.emptySet(), steps, Collections.emptySet(), Collections.emptySet(), false, ZonedDateTime.now(), ZonedDateTime.now())
    }

    private static Step step(boolean deleted) {
        return new Step(3, '', '', 0, deleted, ZonedDateTime.now(), ZonedDateTime.now())
    }

}
