package pl.szczeliniak.kitchenassistant.receipt.commands

import pl.szczeliniak.kitchenassistant.file.queries.CheckIfFileExistsQuery
import pl.szczeliniak.kitchenassistant.file.queries.dtos.CheckIfFileExistsResponse
import pl.szczeliniak.kitchenassistant.receipt.*
import pl.szczeliniak.kitchenassistant.receipt.commands.dto.AssignFilesAsReceiptPhotosDto
import pl.szczeliniak.kitchenassistant.receipt.commands.factories.PhotoFactory
import pl.szczeliniak.kitchenassistant.shared.dtos.SuccessResponse
import spock.lang.Specification
import spock.lang.Subject

import java.time.ZonedDateTime

class AssignReceiptPhotosCommandSpec extends Specification {

    def receiptDao = Mock(ReceiptDao)
    def photoFactory = Mock(PhotoFactory)
    def photoDao = Mock(PhotoDao)
    def checkIfFileExistsQuery = Mock(CheckIfFileExistsQuery)

    @Subject
    def addReceiptPhotosCommand = new AssignReceiptPhotosCommand(receiptDao, checkIfFileExistsQuery, photoFactory, photoDao)

    def 'should add photo to receipt'() {
        given:
        def photo = photo()
        def receipt = receipt(new HashSet<Photo>())
        receiptDao.findById(1) >> receipt
        receiptDao.save(receipt) >> receipt
        checkIfFileExistsQuery.execute(99) >> checkIfFileExistsResponse()
        photoFactory.create(99) >> photo
        photoDao.save(photo) >> photo

        when:
        def result = addReceiptPhotosCommand.execute(1, addReceiptPhotosDto())

        then:
        result == new SuccessResponse(1)
        receipt.photos.size() == 1
        receipt.photos.contains(photo)
    }

    def 'should not add photo to receipt when photo with file id already added'() {
        given:
        def photo = photo()
        def receipt = receipt(Set.of(photo))
        receiptDao.findById(1) >> receipt
        receiptDao.save(receipt) >> receipt
        checkIfFileExistsQuery.execute(99) >> checkIfFileExistsResponse()

        when:
        def result = addReceiptPhotosCommand.execute(1, addReceiptPhotosDto())

        then:
        result == new SuccessResponse(1)
        receipt.photos.size() == 1
        receipt.photos.contains(photo)
    }

    private static AssignFilesAsReceiptPhotosDto addReceiptPhotosDto() {
        return new AssignFilesAsReceiptPhotosDto(Set.of(99))
    }

    private static Receipt receipt(Set<Photo> photos) {
        return new Receipt(1, 0, "", "", "", "", false,
                new Category(0, "", 0, false, ZonedDateTime.now(), ZonedDateTime.now()),
                Collections.emptySet(), Collections.emptySet(), photos, Collections.emptySet(), false, ZonedDateTime.now(), ZonedDateTime.now())
    }

    private static Photo photo() {
        return new Photo(0, 99, ZonedDateTime.now(), ZonedDateTime.now())
    }

    private static CheckIfFileExistsResponse checkIfFileExistsResponse() {
        return new CheckIfFileExistsResponse(true)
    }

}
