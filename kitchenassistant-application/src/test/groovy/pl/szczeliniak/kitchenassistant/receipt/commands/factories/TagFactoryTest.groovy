package pl.szczeliniak.kitchenassistant.receipt.commands.factories

import org.assertj.core.api.Assertions
import pl.szczeliniak.kitchenassistant.receipt.Tag
import spock.lang.Specification
import spock.lang.Subject

import java.time.ZonedDateTime

class TagFactoryTest extends Specification {

    @Subject
    def tagFactory = new TagFactory()

    def 'should create tag'() {

        when:
        def result = tagFactory.create("TAG_NAME", 4)

        then:
        Assertions.assertThat(result).usingRecursiveComparison()
                .ignoringFields("createdAt", "modifiedAt")
                .isEqualTo(tag())
    }

    private static Tag tag() {
        return new Tag(0, "TAG_NAME", 4, ZonedDateTime.now(), ZonedDateTime.now())
    }

}
