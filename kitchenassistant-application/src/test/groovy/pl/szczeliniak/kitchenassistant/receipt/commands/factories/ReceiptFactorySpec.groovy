package pl.szczeliniak.kitchenassistant.receipt.commands.factories

import org.assertj.core.api.Assertions
import pl.szczeliniak.kitchenassistant.receipt.*
import pl.szczeliniak.kitchenassistant.receipt.commands.dto.NewIngredientDto
import pl.szczeliniak.kitchenassistant.receipt.commands.dto.NewReceiptDto
import pl.szczeliniak.kitchenassistant.receipt.commands.dto.NewStepDto
import pl.szczeliniak.kitchenassistant.user.queries.GetUserByIdQuery
import pl.szczeliniak.kitchenassistant.user.queries.dto.UserDto
import pl.szczeliniak.kitchenassistant.user.queries.dto.UserResponse
import spock.lang.Specification
import spock.lang.Subject

import java.time.LocalDateTime

class ReceiptFactorySpec extends Specification {

    def getUserByIdQuery = Mock(GetUserByIdQuery)
    def ingredientFactory = Mock(IngredientFactory)
    def stepFactory = Mock(StepFactory)
    def categoryDao = Mock(CategoryDao)
    def fileDao = Mock(FileDao)
    def tagDao = Mock(TagDao)
    def tagFactory = Mock(TagFactory)

    @Subject
    def receiptFactory = new ReceiptFactory(getUserByIdQuery, ingredientFactory, stepFactory, categoryDao, fileDao, tagDao, tagFactory)

    def 'should create receipt'() {
        given:
        def newIngredientDto = newIngredientDto()
        def newStepDto = newStepDto()
        def category = category()
        def newTag = tag(30, "NEW_TAG")
        def existingTag = tag(35, "EXISTING_TAG")

        getUserByIdQuery.execute(1) >> userResponse()
        ingredientFactory.create(newIngredientDto) >> ingredient()
        stepFactory.create(newStepDto) >> step()
        fileDao.findById(99) >> photo()
        categoryDao.findById(2) >> category
        tagDao.findByName("EXISTING_TAG", 4) >> tag(35, "EXISTING_TAG")
        tagDao.findByName("NEW_TAG", 4) >> null
        tagFactory.create("NEW_TAG", 4) >> newTag
        tagDao.save(newTag) >> newTag

        when:
        def result = receiptFactory.create(newReceiptDto(newIngredientDto, newStepDto))

        then:
        Assertions.assertThat(result).usingRecursiveComparison()
                .ignoringFields("createdAt_", "modifiedAt_", "ingredients_.createdAt_",
                        "ingredients_.modifiedAt_", "steps_.createdAt_", "steps_.modifiedAt_", "photos_.createdAt_", "photos_.modifiedAt_",
                        "tags_.createdAt_", "tags_.modifiedAt_")
                .isEqualTo(receipt(category, List.of(existingTag, newTag)))
    }

    private static NewReceiptDto newReceiptDto(NewIngredientDto newIngredientDto, NewStepDto newStepDto) {
        return new NewReceiptDto(4, "RECEIPT_NAME", 2, "RECEIPT_DESCRIPTION", "RECEIPT_AUTHOR",
                "RECEIPT_SOURCE", Collections.singletonList(newIngredientDto), Collections.singletonList(newStepDto), Collections.singletonList(99), List.of("EXISTING_TAG", "NEW_TAG"))
    }

    private static NewIngredientDto newIngredientDto() {
        return new NewIngredientDto("", "")
    }

    private static NewStepDto newStepDto() {
        return new NewStepDto("", "", 0, Collections.emptyList())
    }

    private static UserResponse userResponse() {
        return new UserResponse(new UserDto(1, "", ""))
    }

    private static Receipt receipt(Category category, List<Tag> tags) {
        return new Receipt(0, 4, "RECEIPT_NAME", "RECEIPT_DESCRIPTION", "RECEIPT_AUTHOR",
                "RECEIPT_SOURCE", category, Collections.singletonList(ingredient()), Collections.singletonList(step()), Collections.singletonList(photo()),
                tags, false, LocalDateTime.now(), LocalDateTime.now())
    }

    private static File photo() {
        return new File(99, "NAME", 4, false, LocalDateTime.now(), LocalDateTime.now())
    }

    private static Tag tag(Integer id, String name) {
        return new Tag(id, name, 4, false, LocalDateTime.now(), LocalDateTime.now())
    }

    private static Ingredient ingredient() {
        return new Ingredient(3, "INGREDIENT_NAME", "INGREDIENT_QUANTITY", false, LocalDateTime.now(), LocalDateTime.now())
    }

    private static Step step() {
        return new Step(4, "STEP_NAME", "STEP_DESCRIPTION", 1, Collections.emptyList(), false, LocalDateTime.now(), LocalDateTime.now())
    }

    private static Category category() {
        return new Category(0, "", 0, false, LocalDateTime.now(), LocalDateTime.now())
    }
}
