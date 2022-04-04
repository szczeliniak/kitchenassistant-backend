package pl.szczeliniak.kitchenassistant.user;

import org.junit.jupiter.api.Test;
import pl.szczeliniak.kitchenassistant.BaseTest;

import java.util.List;

public class UserTestIT extends BaseTest {

    @Test
    public void shouldAddUser() {
        SuccessResponse response = addUser(addUserDto());

        assertThat(response.getId()).isNotNull();
    }

    @Test
    public void shouldReturnUsers() {
        Integer userId = addUser(addUserDto()).getId();
        Integer userId2 = addUser(addUserDto2()).getId();

        UsersResponse response = getUsers();

        assertThat(response.getUsers())
                .isEqualTo(List.of(user(userId), user2(userId2)));
    }

    @Test
    public void shouldReturnUserById() {
        Integer userId = addUser(addUserDto()).getId();

        UserResponse response = getUser(userId);

        assertThat(response.getUser()).isEqualTo(user(userId));
    }

    protected AddUserDto addUserDto2() {
        return AddUserDto.builder()
                .name("User2")
                .password("Password")
                .email("user2@gmail.com")
                .build();
    }

    protected User user(Integer id) {
        return User.builder()
                .id(id)
                .email("user@gmail.com")
                .name("User")
                .build();
    }

    protected User user2(Integer id) {
        return User.builder()
                .id(id)
                .email("user2@gmail.com")
                .name("User2")
                .build();
    }

    private UsersResponse getUsers() {
        return spec()
                .get("/users")
                .then()
                .statusCode(200)
                .extract()
                .as(UsersResponse.class);
    }

    private UserResponse getUser(Integer id) {
        return spec()
                .get("/users/" + id)
                .then()
                .statusCode(200)
                .extract()
                .as(UserResponse.class);
    }

}