package pl.szczeliniak.kitchenassistant.recipe.commands.factories

import org.assertj.core.api.Assertions
import pl.szczeliniak.kitchenassistant.recipe.commands.dto.NewIngredientGroupDto
import pl.szczeliniak.kitchenassistant.recipe.commands.dto.NewRecipeDto
import pl.szczeliniak.kitchenassistant.recipe.commands.dto.NewStepDto
import pl.szczeliniak.kitchenassistant.recipe.db.*
import pl.szczeliniak.kitchenassistant.user.queries.GetUserByIdQuery
import pl.szczeliniak.kitchenassistant.user.queries.dto.UserDto
import pl.szczeliniak.kitchenassistant.user.queries.dto.UserResponse
import spock.lang.Specification
import spock.lang.Subject

import java.time.ZonedDateTime

class RecipeFactorySpec extends Specification {

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
    def recipeFactory = new RecipeFactory(getUserByIdQuery, stepFactory, categoryDao, tagDao, tagFactory, authorDao, authorFactory, photoDao, ingredientGroupFactory)

    def 'should create recipe'() {
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
        authorDao.findByName("RECIPE_AUTHOR", 4) >> null
        authorFactory.create("RECIPE_AUTHOR", 4) >> author
        authorDao.save(author) >> author

        when:
        def result = recipeFactory.create(newRecipeDto(newIngredientGroupDto, newStepDto))

        then:
        Assertions.assertThat(result).usingRecursiveComparison()
                .ignoringFields("createdAt", "modifiedAt", "ingredientGroups.createdAt",
                        "ingredientGroups.modifiedAt", "steps.createdAt", "steps.modifiedAt", "photo.createdAt", "photo.modifiedAt",
                        "tags.createdAt", "tags.modifiedAt", "author.createdAt", "author.modifiedAt")
                .isEqualTo(recipe(category, Set.of(existingTag, newTag)))
    }

    private static NewRecipeDto newRecipeDto(NewIngredientGroupDto newIngredientGroupDto, NewStepDto newStepDto) {
        return new NewRecipeDto(4, "RECIPE_NAME", 2, "RECIPE_DESCRIPTION", "RECIPE_AUTHOR",
                "RECIPE_SOURCE", 99, Set.of(newIngredientGroupDto), Set.of(newStepDto), Set.of("EXISTING_TAG", "NEW_TAG"))
    }

    private static NewStepDto newStepDto() {
        return new NewStepDto("", "", 0)
    }

    private static UserResponse userResponse() {
        return new UserResponse(new UserDto(1, "", ""))
    }

    private static Recipe recipe(Category category, Set<Tag> tags) {
        return new Recipe(0, "RECIPE_NAME", 4, "RECIPE_DESCRIPTION", new Author(2, "RECIPE_AUTHOR", 1, ZonedDateTime.now(), ZonedDateTime.now()),
                "RECIPE_SOURCE", false, category, new HashSet(Arrays.asList(ingredientGroup())), new HashSet(Arrays.asList(step())), photo(), tags, false, ZonedDateTime.now(), ZonedDateTime.now())
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
        return new Author(2, "RECIPE_AUTHOR", 1, ZonedDateTime.now(), ZonedDateTime.now())
    }

    private static NewIngredientGroupDto newIngredientGroupDto() {
        return new NewIngredientGroupDto("GROUP_NAME", Collections.emptySet())
    }

}
