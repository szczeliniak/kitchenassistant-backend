package pl.szczeliniak.kitchenassistant.receipt.commands

import pl.szczeliniak.kitchenassistant.receipt.Photo
import pl.szczeliniak.kitchenassistant.receipt.PhotoDao
import pl.szczeliniak.kitchenassistant.receipt.Receipt
import pl.szczeliniak.kitchenassistant.receipt.ReceiptDao
import pl.szczeliniak.kitchenassistant.shared.dtos.SuccessResponse
import pl.szczeliniak.kitchenassistant.shared.exceptions.NotAllowedOperationException
import pl.szczeliniak.kitchenassistant.shared.exceptions.NotFoundException
import spock.lang.Specification
import spock.lang.Subject

import java.time.LocalDateTime

class DivestReceiptPhotoCommandSpec extends Specification {

    def receiptDao = Mock(ReceiptDao)
    def photoDao = Mock(PhotoDao)

    @Subject
    def deletePhotoCommand = new DivestReceiptPhotoCommand(receiptDao, photoDao)

    def 'should delete photo'() {
        given:
        def photo = photo(false)
        def receipt = receipt(Set.of(photo))
        receiptDao.findById(1) >> receipt
        receiptDao.save(receipt) >> receipt

        when:
        def result = deletePhotoCommand.execute(1, 2)

        then:
        photo.deleted
        result == new SuccessResponse(1)
    }

    def 'should throw exception when photo not found'() {
        given:
        def receipt = receipt(Collections.emptySet())
        receiptDao.findById(1) >> receipt

        when:
        deletePhotoCommand.execute(1, 2)

        then:
        def e = thrown(NotFoundException)
        e.message == "Photo not found"
    }

    def 'should throw exception when photo is already marked as deleted'() {
        given:
        def photo = photo(true)
        def receipt = receipt(Set.of(photo))
        receiptDao.findById(1) >> receipt

        when:
        deletePhotoCommand.execute(1, 2)

        then:
        def e = thrown(NotAllowedOperationException)
        e.message == "File is already marked as deleted!"
    }

    private static Receipt receipt(Set<Photo> photos) {
        return new Receipt(1, 4, '', '', '', '', null, Collections.emptySet(), Collections.emptySet(), photos, Collections.emptySet(), false, LocalDateTime.now(), LocalDateTime.now())
    }

    private static Photo photo(boolean deleted) {
        return new Photo(2, 3, deleted, LocalDateTime.now(), LocalDateTime.now())
    }

}
