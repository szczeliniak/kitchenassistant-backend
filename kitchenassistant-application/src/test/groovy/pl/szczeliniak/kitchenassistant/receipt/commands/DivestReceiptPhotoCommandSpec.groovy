package pl.szczeliniak.kitchenassistant.receipt.commands

import pl.szczeliniak.kitchenassistant.receipt.Photo
import pl.szczeliniak.kitchenassistant.receipt.Receipt
import pl.szczeliniak.kitchenassistant.receipt.ReceiptDao
import pl.szczeliniak.kitchenassistant.shared.dtos.SuccessResponse
import pl.szczeliniak.kitchenassistant.shared.exceptions.NotFoundException
import spock.lang.Specification
import spock.lang.Subject

import java.time.LocalDateTime

class DivestReceiptPhotoCommandSpec extends Specification {

    def receiptDao = Mock(ReceiptDao)

    @Subject
    def deletePhotoCommand = new DivestReceiptPhotoCommand(receiptDao)

    def 'should delete photo'() {
        given:
        def receipt = receipt(new HashSet<Photo>(List.of(photo())))
        receiptDao.findById(1) >> receipt
        receiptDao.save(receipt) >> receipt

        when:
        def result = deletePhotoCommand.execute(1, 2)

        then:
        receipt.photos == Collections.emptySet()
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

    private static Receipt receipt(Set<Photo> photos) {
        return new Receipt(1, 4, '', '', '', '', null, Collections.emptySet(), Collections.emptySet(), photos, Collections.emptySet(), false, LocalDateTime.now(), LocalDateTime.now())
    }

    private static Photo photo() {
        return new Photo(2, 3, LocalDateTime.now(), LocalDateTime.now())
    }

}
