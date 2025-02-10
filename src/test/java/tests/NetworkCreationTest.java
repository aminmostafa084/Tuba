package tests;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class NetworkCreationTest {
    private WebDriver driver;
    private LoginPage loginPage;
    private WebDriverWait wait;
    private final String networkName = "صلاح ااسواني واحد وسيبها ع الله";

    @Before
    public void setUp() {
        driver = new ChromeDriver();
        loginPage = new LoginPage(driver);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.manage().window().maximize();
    }

    @After
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void testNetworkCreation() {
        driver.get("https://cp-staging.gettuba.com/login");
        loginPage.login("Company1_Account_Manager@tara.com.sa", "PW@202511");
        driver.findElement(By.id("kt_sign_in_submit")).click();

        // الانتقال إلى صفحة إدارة الشبكات
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".menu-item:nth-child(3) .menu-title"))).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".py-0"))).click();

        // التحقق مما إذا كانت الشبكة موجودة
        if (isNetworkAlreadyCreated(networkName)) {
            System.out.println("⚠ الشبكة موجودة بالفعل، سيتم تخطي عملية الإنشاء.");
        } else {
            System.out.println("🔄 لم يتم العثور على الشبكة، سيتم إنشاؤها...");

            // إدخال اسم الشبكة
            WebElement networkNameField = wait.until(ExpectedConditions.elementToBeClickable(By.id("networkLevelName")));
            networkNameField.sendKeys(networkName);

            // تحديد القيم من القوائم المنسدلة
            selectDropdownOption(By.id("baseNetwork"), "3");
            selectDropdownOption(By.id("cityFilter"), "Riyadh");

            // الضغط على زر إضافة الشبكة
            wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".btn-add-member"))).click();
            System.out.println("✅ تم إنشاء الشبكة بنجاح.");

            // التحقق من ظهور رسالة النجاح
            WebElement successMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".success-message")));
            String messageText = successMessage.getText();
            assert messageText.contains("تم إنشاء الشبكة بنجاح") : "رسالة النجاح غير صحيحة.";
        }

        // المتابعة إلى الخطوة التالية بعد التحقق من الشبكة
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".cursor-pointer:nth-child(1) .details-badge"))).click();
    }

    private boolean isNetworkAlreadyCreated(String networkName) {
        try {
            // التأكد من أن الجدول قد تم تحميله بالكامل
            wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//table")));

            // جلب جميع الصفوف في الجدول
            List<WebElement> rows = driver.findElements(By.xpath("//table//tr"));

            // الانتظار حتى تظهر جميع الصفوف بشكل كامل
            wait.until(ExpectedConditions.visibilityOfAllElements(rows));

            // التحقق من وجود الشبكة في الجدول
            for (WebElement row : rows) {
                try {
                    WebElement cell = row.findElement(By.xpath(".//td[contains(normalize-space(), '" + networkName + "')]"));
                    if (cell != null) {
                        System.out.println("✅ الشبكة موجودة بالفعل: " + networkName);
                        return true;
                    }
                } catch (NoSuchElementException ignored) {
                    // إذا لم يتم العثور على العنصر في الصف الحالي، استمر في البحث
                }
            }
            System.out.println("❌ لم يتم العثور على الشبكة.");
            return false;
        } catch (Exception e) {
            System.out.println("⚠ خطأ أثناء التحقق من وجود الشبكة: " + e.getMessage());
            return false;
        }
    }

    private void selectDropdownOption(By locator, String value) {
        WebElement dropdown = wait.until(ExpectedConditions.elementToBeClickable(locator));
        Select select = new Select(dropdown);
        select.selectByVisibleText(value);
    }
}
