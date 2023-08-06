package pl.szczeliniak.kitchenassistant.recipe.commands

import pl.szczeliniak.kitchenassistant.dayplan.db.DayPlan
import pl.szczeliniak.kitchenassistant.dayplan.db.DayPlanDao
import pl.szczeliniak.kitchenassistant.dayplan.dto.DayPlanCriteria
import pl.szczeliniak.kitchenassistant.recipe.db.Author
import pl.szczeliniak.kitchenassistant.recipe.db.Recipe
import pl.szczeliniak.kitchenassistant.recipe.db.RecipeDao
import pl.szczeliniak.kitchenassistant.shared.dtos.SuccessResponse
import pl.szczeliniak.kitchenassistant.shoppinglist.db.ShoppingList
import pl.szczeliniak.kitchenassistant.shoppinglist.db.ShoppingListCriteria
import pl.szczeliniak.kitchenassistant.shoppinglist.db.ShoppingListDao
import pl.szczeliniak.kitchenassistant.shoppinglist.db.ShoppingListItem
import pl.szczeliniak.kitchenassistant.user.db.User
import spock.lang.Specification
import spock.lang.Subject

import java.time.LocalDate
import java.time.ZonedDateTime

class DeleteRecipeCommandSpec extends Specification {

    def recipeDao = Mock(RecipeDao)
    def shoppingListDao = Mock(ShoppingListDao)
    def dayPlanDao = Mock(DayPlanDao)

    @Subject
    def deleteRecipeCommand = new DeleteRecipeCommand(recipeDao, dayPlanDao, shoppingListDao)

    def 'should delete recipe'() {
        given:
        def user = user()
        def recipe = recipe(1, user)
        def dayPlan = dayPlan(user)
        def shoppingListItem = shoppingListItem(recipe)
        def shoppingList = shoppingList(shoppingListItem, user)
        recipeDao.findById(1) >> recipe
        dayPlanDao.findAll(new DayPlanCriteria(1, null, null), null, null, null) >> Set.of(dayPlan)
        shoppingListDao.findAll(new ShoppingListCriteria(null, null, null, null, 1, null, null), null, null) >> Set.of(shoppingList)
        when:
        def result = deleteRecipeCommand.execute(1)

        then:
        result == new SuccessResponse(1)
        1 * recipeDao.delete(recipe)
        1 * dayPlanDao.save(dayPlan)
        1 * shoppingListDao.save(Set.of(shoppingList))
        shoppingListItem.recipe == null
        dayPlan.recipes.size() == 1
    }

    private static Recipe recipe(Integer id, User user) {
        return new Recipe(id, '', user, '', new Author(2, "", user, ZonedDateTime.now(), ZonedDateTime.now()), '', false, null, Collections.emptySet(), Collections.emptySet(), null, Collections.emptySet(), ZonedDateTime.now(), ZonedDateTime.now())
    }

    private static DayPlan dayPlan(User user) {
        return new DayPlan(0, user.id, LocalDate.now(), new HashSet<Recipe>(Arrays.asList(recipe(1, user), recipe(2, user))), ZonedDateTime.now(), ZonedDateTime.now())
    }

    private static ShoppingListItem shoppingListItem(Recipe recipe) {
        return new ShoppingListItem(0, "", "", 1, recipe, false, ZonedDateTime.now(), ZonedDateTime.now())
    }

    private static ShoppingList shoppingList(ShoppingListItem shoppingListItem, User user) {
        return new ShoppingList(0, user, "", "", LocalDate.now(), Set.of(shoppingListItem), false, false, ZonedDateTime.now(), ZonedDateTime.now())
    }


    private static User user() {
        return new User(2, "", "", ZonedDateTime.now(), ZonedDateTime.now())
    }

}
