package tests;

import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.Dimension;

public class TestLogin {
    private WebDriver driver;
    private LoginPage loginPage;

    @Before
    public void setUp() {
        driver = new ChromeDriver();
        loginPage = new LoginPage(driver);  // إنشاء كائن LoginPage
        driver.manage().window().setSize(new Dimension(1552, 840));
    }

    @After
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void testLogin() throws InterruptedException {
        driver.get("https://cp-staging.gettuba.com/login");

        // إجراء تسجيل الدخول باستخدام LoginPage
        loginPage.login("Company1_Account_Manager@tara.com.sa", "PW@202511");
        WebElement loginButton = driver.findElement(By.id("kt_sign_in_submit"));
        loginButton.click();
        Thread.sleep(3000);
        // هنا يمكن إضافة التحقق من نجاح عملية تسجيل الدخول مثل التحقق من وجود عنصر مميز بعد الدخول.
    }
}
