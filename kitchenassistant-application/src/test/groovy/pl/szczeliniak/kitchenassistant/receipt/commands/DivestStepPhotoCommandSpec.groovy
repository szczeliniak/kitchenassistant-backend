package pl.szczeliniak.kitchenassistant.receipt.commands

import pl.szczeliniak.kitchenassistant.common.dto.SuccessResponse
import pl.szczeliniak.kitchenassistant.exceptions.NotAllowedOperationException
import pl.szczeliniak.kitchenassistant.exceptions.NotFoundException
import pl.szczeliniak.kitchenassistant.receipt.*
import spock.lang.Specification
import spock.lang.Subject

import java.time.LocalDateTime

class DivestStepPhotoCommandSpec extends Specification {

    def receiptDao = Mock(ReceiptDao)
    def photoDao = Mock(PhotoDao)

    @Subject
    def divestPhotoFromReceiptStepCommand = new DivestStepPhotoCommand(receiptDao, photoDao)

    def 'should delete photo'() {
        given:
        def photo = photo(false)
        def step = step(Set.of(photo))
        def receipt = receipt(Set.of(step))
        receiptDao.findById(1) >> receipt
        photoDao.save(photo) >> photo

        when:
        def result = divestPhotoFromReceiptStepCommand.execute(1, 2, 4)

        then:
        photo.deleted
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

    def 'should throw exception when photo is already marked as deleted'() {
        given:
        def photo = photo(true)
        def step = step(Set.of(photo))
        def receipt = receipt(Set.of(step))
        receiptDao.findById(1) >> receipt

        when:
        divestPhotoFromReceiptStepCommand.execute(1, 2, 4)

        then:
        def e = thrown(NotAllowedOperationException)
        e.message == "File is already marked as deleted!"
    }

    private static Receipt receipt(Set<Step> steps) {
        return new Receipt(1, 2, '', '', '', '', null, Collections.emptySet(), steps, Collections.emptySet(), Collections.emptySet(), false, LocalDateTime.now(), LocalDateTime.now())
    }

    private static Step step(Set<Photo> photos) {
        return new Step(2, "", "", 0, photos, false, LocalDateTime.now(), LocalDateTime.now())
    }

    private static Photo photo(boolean deleted) {
        return new Photo(4, 55, deleted, LocalDateTime.now(), LocalDateTime.now())
    }

}
