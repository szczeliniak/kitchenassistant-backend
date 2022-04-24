package pl.szczeliniak.kitchenassistant.receipt.commands.factories

import org.assertj.core.api.Assertions
import pl.szczeliniak.kitchenassistant.file.queries.CheckIfFileExistsQuery
import pl.szczeliniak.kitchenassistant.file.queries.dtos.CheckIfFileExistsResponse
import pl.szczeliniak.kitchenassistant.receipt.Photo
import pl.szczeliniak.kitchenassistant.receipt.Step
import pl.szczeliniak.kitchenassistant.receipt.commands.dto.NewStepDto
import spock.lang.Specification
import spock.lang.Subject

import java.time.LocalDateTime

class StepFactorySpec extends Specification {

    def photoFactory = Mock(PhotoFactory)
    def checkIfFileExistsQuery = Mock(CheckIfFileExistsQuery)

    @Subject
    def stepFactory = new StepFactory(photoFactory, checkIfFileExistsQuery)

    def 'should create step'() {
        given:
        def photo = photo()
        photoFactory.create(99) >> photo
        checkIfFileExistsQuery.execute(99) >> checkIfFileExistsResponse()

        when:
        def result = stepFactory.create(newStepDto())

        then:
        Assertions.assertThat(result).usingRecursiveComparison()
                .ignoringFields("createdAt_", "modifiedAt_")
                .isEqualTo(step(Set.of(photo)))
    }

    private static NewStepDto newStepDto() {
        return new NewStepDto("NAME", "DESCRIPTION", 1, Set.of(99))
    }

    private static Step step(Set<Photo> photos) {
        return new Step(0, "NAME", "DESCRIPTION", 1, photos, false, LocalDateTime.now(), LocalDateTime.now())
    }

    private static Photo photo() {
        return new Photo(0, 4, false, LocalDateTime.now(), LocalDateTime.now())
    }

    private static CheckIfFileExistsResponse checkIfFileExistsResponse() {
        return new CheckIfFileExistsResponse(true)
    }

}