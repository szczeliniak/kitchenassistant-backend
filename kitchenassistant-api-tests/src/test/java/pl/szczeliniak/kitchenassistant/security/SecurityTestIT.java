package pl.szczeliniak.kitchenassistant.security;

import org.junit.jupiter.api.Test;
import pl.szczeliniak.kitchenassistant.BaseTest;

public class SecurityTestIT extends BaseTest {

    @Test
    public void shouldLogin() {
        addUser(addUserDto());

        LoginResponse loginResponse = login(loginDto());

        assertThat(loginResponse.getToken()).isNotNull();
        assertThat(loginResponse.getId()).isNotNull();
    }

    @Test
    public void shouldRegister() {
        LoginResponse loginResponse = register(registerDto());

        assertThat(loginResponse.getToken()).isNotNull();
        assertThat(loginResponse.getId()).isNotNull();
    }

    private LoginDto loginDto() {
        return LoginDto.builder()
                .email("user@gmail.com")
                .password("Password")
                .build();
    }

    private LoginResponse login(LoginDto loginDto) {
        return spec().body(loginDto)
                .post("/login")
                .then()
                .statusCode(200)
                .extract()
                .as(LoginResponse.class);
    }

    private LoginResponse register(RegisterDto registerDto) {
        return spec().body(registerDto)
                .post("/register")
                .then()
                .statusCode(200)
                .extract()
                .as(LoginResponse.class);
    }

    private RegisterDto registerDto() {
        return RegisterDto.builder()
                .email("user@gmail.com")
                .name("name")
                .password("Password")
                .passwordRepeated("Password")
                .build();
    }

}