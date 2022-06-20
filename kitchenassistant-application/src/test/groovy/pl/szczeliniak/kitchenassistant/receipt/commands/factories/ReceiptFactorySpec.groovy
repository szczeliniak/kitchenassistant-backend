package pl.szczeliniak.kitchenassistant.receipt.commands.factories

import org.assertj.core.api.Assertions
import pl.szczeliniak.kitchenassistant.receipt.*
import pl.szczeliniak.kitchenassistant.receipt.commands.dto.NewIngredientGroupDto
import pl.szczeliniak.kitchenassistant.receipt.commands.dto.NewReceiptDto
import pl.szczeliniak.kitchenassistant.receipt.commands.dto.NewStepDto
import pl.szczeliniak.kitchenassistant.user.queries.GetUserByIdQuery
import pl.szczeliniak.kitchenassistant.user.queries.dto.UserDto
import pl.szczeliniak.kitchenassistant.user.queries.dto.UserResponse
import spock.lang.Specification
import spock.lang.Subject

import java.time.ZonedDateTime

class ReceiptFactorySpec extends Specification {

    def getUserByIdQuery = Mock(GetUserByIdQuery)
    def stepFactory = Mock(StepFactory)
    def categoryDao = Mock(CategoryDao)
    def tagDao = Mock(TagDao)
    def tagFactory = Mock(TagFactory)
    def authorDao = Mock(AuthorDao)
    def authorFactory = Mock(AuthorFactory)
    def photoDao = Mock(PhotoDao)
    def ingredientGroupFactory = Mock(IngredientGroupFactory)

    @Subject
    def receiptFactory = new ReceiptFactory(getUserByIdQuery, stepFactory, categoryDao, tagDao, tagFactory, authorDao, authorFactory, photoDao, ingredientGroupFactory)

    def 'should create receipt'() {
        given:
        def newIngredientGroupDto = newIngredientGroupDto()
        def newStepDto = newStepDto()
        def category = category()
        def newTag = tag(30, "NEW_TAG")
        def existingTag = tag(35, "EXISTING_TAG")
        def author = author()

        getUserByIdQuery.execute(1) >> userResponse()
        photoDao.findById(99) >> photo()
        ingredientGroupFactory.create(newIngredientGroupDto) >> ingredientGroup()
        stepFactory.create(newStepDto) >> step()
        categoryDao.findById(2) >> category
        tagDao.findByName("EXISTING_TAG", 4) >> tag(35, "EXISTING_TAG")
        tagDao.findByName("NEW_TAG", 4) >> null
        tagFactory.create("NEW_TAG", 4) >> newTag
        authorDao.findByName("RECEIPT_AUTHOR", 4) >> null
        authorFactory.create("RECEIPT_AUTHOR", 4) >> author
        authorDao.save(author) >> author

        when:
        def result = receiptFactory.create(newReceiptDto(newIngredientGroupDto, newStepDto))

        then:
        Assertions.assertThat(result).usingRecursiveComparison()
                .ignoringFields("createdAt_", "modifiedAt_", "ingredientGroups_.createdAt_",
                        "ingredientGroups_.modifiedAt_", "steps_.createdAt_", "steps_.modifiedAt_", "photos_.createdAt_", "photos_.modifiedAt_",
                        "tags_.createdAt_", "tags_.modifiedAt_", "author_.createdAt_", "author_.modifiedAt_")
                .isEqualTo(receipt(category, Set.of(existingTag, newTag)))
    }

    private static NewReceiptDto newReceiptDto(NewIngredientGroupDto newIngredientGroupDto, NewStepDto newStepDto) {
        return new NewReceiptDto(4, "RECEIPT_NAME", 2, "RECEIPT_DESCRIPTION", "RECEIPT_AUTHOR",
                "RECEIPT_SOURCE", Set.of(newIngredientGroupDto), Set.of(newStepDto), Set.of(99), Set.of("EXISTING_TAG", "NEW_TAG"))
    }

    private static NewStepDto newStepDto() {
        return new NewStepDto("", "", 0)
    }

    private static UserResponse userResponse() {
        return new UserResponse(new UserDto(1, "", ""))
    }

    private static Receipt receipt(Category category, Set<Tag> tags) {
        return new Receipt(0, 4, "RECEIPT_NAME", "RECEIPT_DESCRIPTION", new Author(2, "RECEIPT_AUTHOR", 1, ZonedDateTime.now(), ZonedDateTime.now()),
                "RECEIPT_SOURCE", false, category, new HashSet(Arrays.asList(ingredientGroup())), new HashSet(Arrays.asList(step())), new HashSet(Arrays.asList(photo())), tags, false, ZonedDateTime.now(), ZonedDateTime.now())
    }

    private static Photo photo() {
        return new Photo(99, "", 1, false, ZonedDateTime.now(), ZonedDateTime.now())
    }

    private static Tag tag(Integer id, String name) {
        return new Tag(id, name, 4, ZonedDateTime.now(), ZonedDateTime.now())
    }

    private static IngredientGroup ingredientGroup() {
        return new IngredientGroup(3, "INGREDIENT_GROUP_NAME", Collections.emptySet(), false, ZonedDateTime.now(), ZonedDateTime.now())
    }

    private static Step step() {
        return new Step(4, "STEP_NAME", "STEP_DESCRIPTION", 1, false, ZonedDateTime.now(), ZonedDateTime.now())
    }

    private static Category category() {
        return new Category(0, "", 0, 3, false, ZonedDateTime.now(), ZonedDateTime.now())
    }

    private static Author author() {
        return new Author(2, "RECEIPT_AUTHOR", 1, ZonedDateTime.now(), ZonedDateTime.now())
    }

    private static NewIngredientGroupDto newIngredientGroupDto() {
        return new NewIngredientGroupDto("GROUP_NAME", Collections.emptySet())
    }

}
