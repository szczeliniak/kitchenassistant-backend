package pl.szczeliniak.kitchenassistant.recipe.commands.factories

import org.assertj.core.api.Assertions
import pl.szczeliniak.kitchenassistant.recipe.FtpClient
import pl.szczeliniak.kitchenassistant.recipe.commands.dto.NewIngredientGroupRequest
import pl.szczeliniak.kitchenassistant.recipe.commands.dto.NewRecipeRequest
import pl.szczeliniak.kitchenassistant.recipe.commands.dto.NewStepRequest
import pl.szczeliniak.kitchenassistant.recipe.db.*
import pl.szczeliniak.kitchenassistant.user.db.User
import pl.szczeliniak.kitchenassistant.user.db.UserDao
import spock.lang.Specification
import spock.lang.Subject

import java.time.ZonedDateTime

class RecipeFactorySpec extends Specification {

    def stepFactory = Mock(StepFactory)
    def categoryDao = Mock(CategoryDao)
    def tagDao = Mock(TagDao)
    def tagFactory = Mock(TagFactory)
    def authorDao = Mock(AuthorDao)
    def authorFactory = Mock(AuthorFactory)
    def ingredientGroupFactory = Mock(IngredientGroupFactory)
    def ftpClient = Mock(FtpClient)
    def userDao = Mock(UserDao)

    @Subject
    def recipeFactory = new RecipeFactory(stepFactory, categoryDao, tagDao, tagFactory, authorDao, authorFactory, ingredientGroupFactory, ftpClient, userDao)

    def 'should create recipe'() {
        given:
        def newIngredientGroupRequest = newIngredientGroupRequest()
        def newStepRequest = newStepRequest()
        def user = user()
        def category = category(user)
        def newTag = tag(30, "NEW_TAG", user)
        def existingTag = tag(35, "EXISTING_TAG", user)
        def author = author(user)

        ingredientGroupFactory.create(newIngredientGroupRequest) >> ingredientGroup()
        stepFactory.create(newStepRequest) >> step()
        categoryDao.findById(2) >> category
        tagDao.findByName("EXISTING_TAG", 4) >> tag(35, "EXISTING_TAG", user)
        tagDao.findByName("NEW_TAG", 4) >> null
        tagFactory.create("NEW_TAG", 4) >> newTag
        authorDao.findByName("RECIPE_AUTHOR", 4) >> null
        authorFactory.create("RECIPE_AUTHOR", 4) >> author
        authorDao.save(author) >> author
        ftpClient.exists("PHOTO_NAME") >> true
        userDao.findById(4) >> user

        when:
        def result = recipeFactory.create(newRecipeRequest(newIngredientGroupRequest, newStepRequest))

        then:
        Assertions.assertThat(result).usingRecursiveComparison()
                .ignoringFields("createdAt", "modifiedAt", "ingredientGroups.createdAt",
                        "ingredientGroups.modifiedAt", "steps.createdAt", "steps.modifiedAt", "photo.createdAt", "photo.modifiedAt",
                        "tags.createdAt", "tags.modifiedAt", "author.createdAt", "author.modifiedAt")
                .isEqualTo(recipe(category, Set.of(existingTag, newTag), user))
    }

    private static NewRecipeRequest newRecipeRequest(NewIngredientGroupRequest newIngredientGroupDto, NewStepRequest newStepDto) {
        return new NewRecipeRequest(4, "RECIPE_NAME", 2, "RECIPE_DESCRIPTION", "RECIPE_AUTHOR",
                "RECIPE_SOURCE", "PHOTO_NAME", Set.of(newIngredientGroupDto), Set.of(newStepDto), Set.of("EXISTING_TAG", "NEW_TAG"))
    }

    private static NewStepRequest newStepRequest() {
        return new NewStepRequest("", 0)
    }

    private static Recipe recipe(Category category, Set<Tag> tags, User user) {
        return new Recipe(0, "RECIPE_NAME", user, "RECIPE_DESCRIPTION", new Author(2, "RECIPE_AUTHOR", user, ZonedDateTime.now(), ZonedDateTime.now()),
                "RECIPE_SOURCE", false, category, new HashSet(Arrays.asList(ingredientGroup())), new HashSet(Arrays.asList(step())), "PHOTO_NAME", tags, ZonedDateTime.now(), ZonedDateTime.now())
    }

    private static Tag tag(Integer id, String name, User user) {
        return new Tag(id, name, user, ZonedDateTime.now(), ZonedDateTime.now())
    }

    private static IngredientGroup ingredientGroup() {
        return new IngredientGroup(3, "INGREDIENT_GROUP_NAME", Collections.emptySet(), ZonedDateTime.now(), ZonedDateTime.now())
    }

    private static Step step() {
        return new Step(4, "STEP_DESCRIPTION", 1, ZonedDateTime.now(), ZonedDateTime.now())
    }

    private static Category category(User user) {
        return new Category(0, "", user, 3, ZonedDateTime.now(), ZonedDateTime.now())
    }

    private static Author author(User user) {
        return new Author(2, "RECIPE_AUTHOR", user, ZonedDateTime.now(), ZonedDateTime.now())
    }

    private static NewIngredientGroupRequest newIngredientGroupRequest() {
        return new NewIngredientGroupRequest("GROUP_NAME", Collections.emptySet())
    }

    private static User user() {
        return new User(1, "", "", "", ZonedDateTime.now(), ZonedDateTime.now())
    }

}
