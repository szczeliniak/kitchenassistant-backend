package pl.szczeliniak.kitchenassistant.receipt.commands

import pl.szczeliniak.kitchenassistant.common.dto.SuccessResponse
import pl.szczeliniak.kitchenassistant.exceptions.NotAllowedOperationException
import pl.szczeliniak.kitchenassistant.exceptions.NotFoundException
import pl.szczeliniak.kitchenassistant.receipt.*
import spock.lang.Specification
import spock.lang.Subject

import java.time.LocalDateTime

class DivestFileFromReceiptStepCommandSpec extends Specification {

    def receiptDao = Mock(ReceiptDao)
    def stepDao = Mock(StepDao)

    @Subject
    def divestPhotoFromReceiptStepCommand = new DivestPhotoFromReceiptStepCommand(receiptDao, stepDao)

    def 'should delete photo'() {
        given:
        def photo = photo(false)
        def step = step(Collections.singletonList(photo))
        def receipt = receipt(Collections.singletonList(step))
        receiptDao.findById(1) >> receipt
        stepDao.save(step) >> step

        when:
        def result = divestPhotoFromReceiptStepCommand.execute(1, 2, "NAME")

        then:
        photo.deleted
        result == new SuccessResponse(2)
    }

    def 'should throw exception when step not found'() {
        given:
        def receipt = receipt(Collections.emptyList())
        receiptDao.findById(1) >> receipt

        when:
        divestPhotoFromReceiptStepCommand.execute(1, 2, "NAME")

        then:
        def e = thrown(NotFoundException)
        e.message == "Step not found"
    }

    def 'should throw exception when photo not found'() {
        given:
        def step = step(Collections.emptyList())
        def receipt = receipt(Collections.singletonList(step))
        receiptDao.findById(1) >> receipt

        when:
        divestPhotoFromReceiptStepCommand.execute(1, 2, "NAME")

        then:
        def e = thrown(NotFoundException)
        e.message == "Photo not found"
    }

    def 'should throw exception when photo is already marked as deleted'() {
        given:
        def photo = photo(true)
        def step = step(Collections.singletonList(photo))
        def receipt = receipt(Collections.singletonList(step))
        receiptDao.findById(1) >> receipt

        when:
        divestPhotoFromReceiptStepCommand.execute(1, 2, "NAME")

        then:
        def e = thrown(NotAllowedOperationException)
        e.message == "File is already marked as deleted!"
    }

    private static Receipt receipt(List<Step> steps) {
        return new Receipt(1, 2, '', '', '', '', null, Collections.emptyList(), steps, Collections.emptyList(), false, LocalDateTime.now(), LocalDateTime.now())
    }

    private static Step step(List<File> photos) {
        return new Step(2, "", "", 0, photos, false, LocalDateTime.now(), LocalDateTime.now())
    }

    private static File photo(boolean deleted) {
        return new File(0, "NAME", deleted, LocalDateTime.now(), LocalDateTime.now())
    }

}
