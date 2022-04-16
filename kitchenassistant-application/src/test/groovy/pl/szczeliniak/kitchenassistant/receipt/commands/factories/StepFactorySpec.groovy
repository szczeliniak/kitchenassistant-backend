package pl.szczeliniak.kitchenassistant.receipt.commands.factories


import org.assertj.core.api.Assertions
import pl.szczeliniak.kitchenassistant.receipt.Photo
import pl.szczeliniak.kitchenassistant.receipt.Step
import pl.szczeliniak.kitchenassistant.receipt.commands.dto.NewStepDto
import spock.lang.Specification
import spock.lang.Subject

import java.time.LocalDateTime

class StepFactorySpec extends Specification {

    def photoFactory = Mock(PhotoFactory)

    @Subject
    def stepFactory = new StepFactory(photoFactory)

    def 'should create step'() {
        given:
        def photo = photo()
        photoFactory.create("PHOTO_NAME") >> photo
        when:
        def result = stepFactory.create(newStepDto())

        then:
        Assertions.assertThat(result).usingRecursiveComparison()
                .ignoringFields("createdAt_", "modifiedAt_")
                .isEqualTo(step(Collections.singletonList(photo)))
    }

    private static NewStepDto newStepDto() {
        return new NewStepDto("NAME", "DESCRIPTION", 1, Collections.singletonList("PHOTO_NAME"))
    }

    private static Step step(List<Photo> photos) {
        return new Step(0, "NAME", "DESCRIPTION", 1, photos, false, LocalDateTime.now(), LocalDateTime.now())
    }

    private static Photo photo() {
        return new Photo(0, "", false, LocalDateTime.now(), LocalDateTime.now())
    }

}