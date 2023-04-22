import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class AuthTest {


    @BeforeEach
    void setUp() {
        open("http://localhost:9999");
    }

    @Test
    @DisplayName("Happy Path")
    void shouldLogin() {
        var registeredUser = DataGenerator.Registration.getRegisteredUser("active");
        $("[data-test-id='login'] input").setValue(registeredUser.getLogin());
        $("[data-test-id='password'] input").setValue(registeredUser.getPassword());
        $("[data-test-id='action-login'] ").click();
        $("h2").shouldBe(Condition.visible).shouldHave(Condition.text("Личный кабинет"));
    }

    @Test
    @DisplayName("Not registered User")
    void notRegistered() {
        var user = DataGenerator.Registration.getUser("active");
        $("[data-test-id='login'] input").setValue(user.getLogin());
        $("[data-test-id='password'] input").setValue(user.getPassword());
        $("[data-test-id='action-login'] ").click();
        $("[data-test-id='error-notification']").shouldBe(Condition.visible).shouldHave(Condition.text("Ошибка! " + "Неверно указан логин или пароль"));
    }

    @Test
    @DisplayName("Wrong password")
    void shouldNotLogin() {
        var registeredUser = DataGenerator.Registration.getRegisteredUser("active");
        var user = DataGenerator.Registration.getUser("active");

        $("[data-test-id='login'] input").setValue(registeredUser.getLogin());
        $("[data-test-id='password'] input").setValue(user.getPassword());
        $("[data-test-id='action-login'] ").click();
        $("[data-test-id='error-notification']").shouldBe(Condition.visible).shouldHave(Condition.text("Ошибка! " + "Неверно указан логин или пароль"));
    }

    @Test
    @DisplayName("Wrong login")
    void shouldNotLogin2() {
        var registeredUser = DataGenerator.Registration.getRegisteredUser("active");
        var user = DataGenerator.Registration.getUser("active");

        $("[data-test-id='login'] input").setValue(user.getLogin());
        $("[data-test-id='password'] input").setValue(registeredUser.getPassword());
        $("[data-test-id='action-login'] ").click();
        $("[data-test-id='error-notification']").shouldBe(Condition.visible).shouldHave(Condition.text("Ошибка! " + "Неверно указан логин или пароль"));
    }

    @Test
    @DisplayName("blocked user")
    void shouldNoticeBlocked() {
        var registeredUser = DataGenerator.Registration.getRegisteredUser("blocked");
        $("[data-test-id='login'] input").setValue(registeredUser.getLogin());
        $("[data-test-id='password'] input").setValue(registeredUser.getPassword());
        $("[data-test-id='action-login'] ").click();
        $("[data-test-id='error-notification']").shouldBe(Condition.visible).shouldHave(Condition.text("Ошибка! " + "Пользователь заблокирован"));
    }

    @Test
    @DisplayName("Empty String login")
    void shouldNoticeLoginEmpty() {
        var registeredUser = DataGenerator.Registration.getRegisteredUser("active");
        $("[data-test-id='password'] input").setValue(registeredUser.getPassword());
        $("[data-test-id='action-login'] ").click();
        $("[data-test-id='login']").shouldBe(Condition.visible).shouldHave(Condition.text("Поле обязательно для заполнения"));
    }

    @Test
    @DisplayName("Empty String password")
    void shouldNoticePasswordEmpty() {
        var registeredUser = DataGenerator.Registration.getRegisteredUser("active");
        $("[data-test-id='login'] input").setValue(registeredUser.getLogin());
        $("[data-test-id='action-login'] ").click();
        $("[data-test-id='password']").shouldBe(Condition.visible).shouldHave(Condition.text("Поле обязательно для заполнения"));
    }
}