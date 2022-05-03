package pl.szczeliniak.kitchenassistant.receipt.commands

import pl.szczeliniak.kitchenassistant.receipt.*
import pl.szczeliniak.kitchenassistant.shared.dtos.SuccessResponse
import pl.szczeliniak.kitchenassistant.shared.exceptions.NotFoundException
import spock.lang.Specification
import spock.lang.Subject

import java.time.LocalDateTime

class DivestStepPhotoCommandSpec extends Specification {

    def receiptDao = Mock(ReceiptDao)
    def stepDao = Mock(StepDao)

    @Subject
    def divestPhotoFromReceiptStepCommand = new DivestStepPhotoCommand(receiptDao, stepDao)

    def 'should delete photo'() {
        given:
        def step = step(new HashSet<Photo>(List.of(photo())))
        def receipt = receipt(Set.of(step))
        receiptDao.findById(1) >> receipt
        stepDao.save(step) >> step

        when:
        def result = divestPhotoFromReceiptStepCommand.execute(1, 2, 4)

        then:
        step.photos == Collections.emptySet()
        result == new SuccessResponse(1)
    }

    def 'should throw exception when step not found'() {
        given:
        def receipt = receipt(Collections.emptySet())
        receiptDao.findById(1) >> receipt

        when:
        divestPhotoFromReceiptStepCommand.execute(1, 2, 4)

        then:
        def e = thrown(NotFoundException)
        e.message == "Step not found"
    }

    def 'should throw exception when photo not found'() {
        given:
        def step = step(Collections.emptySet())
        def receipt = receipt(Set.of(step))
        receiptDao.findById(1) >> receipt

        when:
        divestPhotoFromReceiptStepCommand.execute(1, 2, 4)

        then:
        def e = thrown(NotFoundException)
        e.message == "Photo not found"
    }

    private static Receipt receipt(Set<Step> steps) {
        return new Receipt(1, 2, '', '', '', '', null, Collections.emptySet(), steps, Collections.emptySet(), Collections.emptySet(), false, LocalDateTime.now(), LocalDateTime.now())
    }

    private static Step step(Set<Photo> photos) {
        return new Step(2, "", "", 0, photos, false, LocalDateTime.now(), LocalDateTime.now())
    }

    private static Photo photo() {
        return new Photo(4, 55, LocalDateTime.now(), LocalDateTime.now())
    }

}
