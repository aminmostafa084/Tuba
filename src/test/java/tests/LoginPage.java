package tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration; // تأكد من استيراد Duration

public class LoginPage {
    private WebDriver driver;

    // عناصر الصفحة
    private By usernameField = By.id("usr");
    private By passwordField = By.id("pwd");
    private By loginButton = By.cssSelector(".material-icons");

    // Constructor
    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    // إجراء إدخال اسم المستخدم
    public void enterUsername(String username) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));  // استخدام Duration بدلاً من int
        WebElement usernameElement = wait.until(ExpectedConditions.elementToBeClickable(usernameField));
        usernameElement.clear();
        usernameElement.sendKeys(username);
    }

    // إجراء إدخال كلمة المرور
    public void enterPassword(String password) {
        WebElement passwordElement = driver.findElement(passwordField);
        passwordElement.clear();
        passwordElement.sendKeys(password);
    }

    // إجراء النقر على زر تسجيل الدخول
    public void clickLoginButton() {
        WebElement loginButtonElement = driver.findElement(loginButton);
        loginButtonElement.click();
    }

    // إجراء تسجيل الدخول بالكامل
    public void login(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        clickLoginButton();
    }
}
