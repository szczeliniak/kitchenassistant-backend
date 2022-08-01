package pl.szczeliniak.kitchenassistant.recipe.commands.factories

import org.assertj.core.api.Assertions
import pl.szczeliniak.kitchenassistant.recipe.Photo
import spock.lang.Specification
import spock.lang.Subject

import java.time.ZonedDateTime

class PhotoFactoryTest extends Specification {

    @Subject
    def photoFactory = new PhotoFactory()

    def 'should create photo'() {

        when:
        def result = photoFactory.create("name", 1)

        then:
        Assertions.assertThat(result).usingRecursiveComparison()
                .ignoringFields("createdAt", "modifiedAt")
                .isEqualTo(photo())
    }

    private static Photo photo() {
        return new Photo(0, "name", 1, false, ZonedDateTime.now(), ZonedDateTime.now())
    }

}
