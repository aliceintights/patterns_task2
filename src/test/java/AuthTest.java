import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;


public class AuthTest {

    @BeforeEach
    void setUp() {
        open("http://localhost:9999/");
    }

    @Test
    void shouldCheckIfTheUserExists() {
        var registeredUser = LoginDataGenerator.Registration.getRegisteredUser("active");

        $("[data-test-id='login'] input").setValue(registeredUser.getLogin());
        $("[data-test-id='password'] input").setValue(registeredUser.getPassword());
        $("[data-test-id='action-login']").click();
        $("h2").shouldHave(Condition.text("Личный кабинет")).shouldBe(visible, Duration.ofSeconds(15));
    }

    @Test
    void shouldCheckIfUserStatusIsNotRegistered() {
        var notRegisteredUser = LoginDataGenerator.Registration.getUser("active");

        $("[data-test-id='login'] input").setValue(notRegisteredUser.getLogin());
        $("[data-test-id='password'] input").setValue(notRegisteredUser.getPassword());
        $("[data-test-id='action-login']").click();
        $("[data-test-id='error-notification']").shouldHave(text("Неверно указан логин или пароль")).shouldBe(visible, Duration.ofSeconds(15));
    }

    @Test
    void shouldCheckIfUserIsBlocked() {
        var blockedUser = LoginDataGenerator.Registration.getRegisteredUser("blocked");

        $("[data-test-id='login'] input").setValue(blockedUser.getLogin());
        $("[data-test-id='password'] input").setValue(blockedUser.getPassword());
        $("[data-test-id='action-login']").click();
        $("[data-test-id='error-notification']").shouldHave(text("Пользователь заблокирован")).shouldBe(visible, Duration.ofSeconds(15));
    }

    @Test
    void shouldCheckIfLoginIsValid() {
        var registeredUser = LoginDataGenerator.Registration.getRegisteredUser("active");
        var invalidLogin = LoginDataGenerator.getLogin();

        $("[data-test-id='login'] input").setValue(invalidLogin);
        $("[data-test-id='password'] input").setValue(registeredUser.getPassword());
        $("[data-test-id='action-login']").click();
        $("[data-test-id='error-notification']").shouldHave(text("Неверно указан логин или пароль")).shouldBe(visible, Duration.ofSeconds(15));
    }

    @Test
    void shouldCheckIfPasswordIsValid() {
        var registeredUser = LoginDataGenerator.Registration.getRegisteredUser("active");
        var invalidPassword = LoginDataGenerator.getPassword();

        $("[data-test-id='login'] input").setValue(invalidPassword);
        $("[data-test-id='password'] input").setValue(registeredUser.getPassword());
        $("[data-test-id='action-login']").click();
        $("[data-test-id='error-notification']").shouldHave(text("Неверно указан логин или пароль")).shouldBe(visible, Duration.ofSeconds(15));
    }
}
