package pl.szczeliniak.kitchenassistant.user;

import org.junit.jupiter.api.Test;
import pl.szczeliniak.kitchenassistant.BaseTest;

public class SecurityTestIT extends BaseTest {

    @Test
    public void shouldLogin() {
        addUser(addUserDto());

        LoginResponse loginResponse = login(LoginDto.builder()
                .email("user@gmail.com")
                .password("Password")
                .build());

        assertThat(loginResponse.getToken()).isNotNull();
    }

    private LoginResponse login(LoginDto loginDto) {
        return spec().body(loginDto)
                .post("/login")
                .then()
                .statusCode(200)
                .extract()
                .as(LoginResponse.class);
    }

}