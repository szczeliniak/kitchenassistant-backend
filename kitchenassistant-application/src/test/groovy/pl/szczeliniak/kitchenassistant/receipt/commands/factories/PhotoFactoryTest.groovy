package pl.szczeliniak.kitchenassistant.receipt.commands.factories

import org.assertj.core.api.Assertions
import pl.szczeliniak.kitchenassistant.receipt.Photo
import spock.lang.Specification
import spock.lang.Subject

import java.time.ZonedDateTime

class PhotoFactoryTest extends Specification {

    @Subject
    def photoFactory = new PhotoFactory()

    def 'should create photo'() {

        when:
        def result = photoFactory.create(4)

        then:
        Assertions.assertThat(result).usingRecursiveComparison()
                .ignoringFields("createdAt_", "modifiedAt_")
                .isEqualTo(photo())
    }

    private static Photo photo() {
        return new Photo(0, 4, ZonedDateTime.now(), ZonedDateTime.now())
    }

}
