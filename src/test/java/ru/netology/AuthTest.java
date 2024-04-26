package ru.netology;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static DataGenerator.DataGenerator.Registration.getRegisteredUser;
import static DataGenerator.DataGenerator.Registration.getUser;
import static DataGenerator.DataGenerator.getRandomLogin;
import static DataGenerator.DataGenerator.getRandomPassword;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class AuthTest {

    @BeforeEach
    void setup() {
        open("http://localhost:9999");
    }

    @Test
    @DisplayName("Should successfully login with active registered user")
    void shouldSuccessfulLoginIfRegisteredActiveUser() {
        var registeredUser = getRegisteredUser("active");
        $("[data-test-id='login'] input").setValue(registeredUser.getLogin());
        $("[data-test-id='password'] input").setValue(registeredUser.getPassword());
        $(".button").click();
        $(".heading").shouldBe(visible).shouldHave(text("Личный кабинет"));
    }

    @Test
    @DisplayName("Should get error message if login with not registered user")
    void shouldGetErrorIfNotRegisteredUser() {
        var notRegisteredUser = getUser("active");
        $("[data-test-id='login'] input").setValue(notRegisteredUser.getLogin());
        $("[data-test-id='password'] input").setValue(notRegisteredUser.getPassword());
        $(".button").click();
        $(".notification .notification__content").shouldBe(visible).shouldHave(text("Ошибка! " + "Неверно указан логин или пароль"));
    }

    @Test
    @DisplayName("Should get error message if login with blocked registered user")
    void shouldGetErrorIfBlockedUser() {
        var blockedUser = getRegisteredUser("blocked");
        $("[data-test-id='login'] input").setValue(blockedUser.getLogin());
        $("[data-test-id='password'] input").setValue(blockedUser.getPassword());
        $(".button").click();
        $(".notification .notification__content").shouldBe(visible).shouldHave(text("Ошибка! Пользователь заблокирован"));
    }

    @Test
    @DisplayName("Should get error message if login with wrong login")
    void shouldGetErrorIfWrongLogin() {
        var registeredUser = getRegisteredUser("active");
        var wrongLogin = getRandomLogin();
        $("[data-test-id='login'] input").setValue(wrongLogin);
        $("[data-test-id='password'] input").setValue(registeredUser.getPassword());
        $(".button").click();
        $(".notification .notification__content").shouldBe(visible).shouldHave(text("Ошибка! " + "Неверно указан логин или пароль"));
    }

    @Test
    @DisplayName("Should get error message if login with wrong password")
    void shouldGetErrorIfWrongPassword() {
        var registeredUser = getRegisteredUser("active");
        var wrongPassword = getRandomPassword();
        $("[data-test-id='login'] input").setValue(registeredUser.getLogin());
        $("[data-test-id='password'] input").setValue(wrongPassword);
        $(".button").click();
        $(".notification .notification__content").shouldBe(visible).shouldHave(text("Ошибка! " + "Неверно указан логин или пароль"));
    }

    @Test
    @DisplayName("Should get error message if login with not registered and blocked user")
    void shouldGetErrorIfNotRegisteredBlockedUser() {
        var notRegisteredUser = getUser("blocked");
        $("[data-test-id='login'] input").setValue(notRegisteredUser.getLogin());
        $("[data-test-id='password'] input").setValue(notRegisteredUser.getPassword());
        $(".button").click();
        $(".notification .notification__content").shouldBe(visible).shouldHave(text("Ошибка! " + "Неверно указан логин или пароль"));
    }

    @Test
    @DisplayName("Should get error message if login with wrong password and user blocked")
    void shouldGetErrorIfWrongPasswordAndUserBlocked() {
        var registeredUser = getRegisteredUser("blocked");
        var wrongPassword = getRandomPassword();
        $("[data-test-id='login'] input").setValue(registeredUser.getLogin());
        $("[data-test-id='password'] input").setValue(wrongPassword);
        $(".button").click();
        $(".notification .notification__content").shouldBe(visible).shouldHave(text("Ошибка! " + "Неверно указан логин или пароль"));
    }

    @Test
    @DisplayName("Should get error message if login with wrong login and blocked user")
    void shouldGetErrorIfWrongLoginAndBlockedUser() {
        var registeredUser = getRegisteredUser("blocked");
        var wrongLogin = getRandomLogin();
        $("[data-test-id='login'] input").setValue(wrongLogin);
        $("[data-test-id='password'] input").setValue(registeredUser.getPassword());
        $(".button").click();
        $(".notification .notification__content").shouldBe(visible).shouldHave(text("Ошибка! " + "Неверно указан логин или пароль"));
    }
}
