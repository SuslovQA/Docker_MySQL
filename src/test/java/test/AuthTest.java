package test;

import data.DataHelper;
import data.SQLHelper;
import org.junit.jupiter.api.*;
import page.LoginPage;

import static com.codeborne.selenide.Selenide.*;
import static data.SQLHelper.deleteDbData;

public class AuthTest {
    LoginPage loginPage;

    @BeforeEach
    void setUp() {
        loginPage = open("http://localhost:9999", LoginPage.class);
    }

    @AfterEach
    void tearDown() {
        closeWebDriver();
    }

    @AfterAll
    static void clear() {
        deleteDbData();
    }

    @Test
    void shouldSuccessAuth() {
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        verificationPage.verifyVisibility();
        var verificationCode = SQLHelper.getVerificationCode();
        verificationPage.validVerify(verificationCode.getCode());
    }

    @Test
    void shouldTrowError() {
        var invalidPass = DataHelper.getInvalidPass();
        var verificationPage = loginPage.validLogin(invalidPass);
        verificationPage.verifyErrorNotificationVisibility();
    }
}