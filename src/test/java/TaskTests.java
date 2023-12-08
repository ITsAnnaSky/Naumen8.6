import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import java.time.Duration;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TaskTests {
    static WebDriver driver;

    @BeforeAll
    public static void setUp()
    {
        WebDriverManager.chromedriver().browserVersion("").setup();
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
    }

    /**
     * Тестирование на добавление карточки пользователя в избранное
     * @throws InterruptedException
     */
    @Test
    @Order(1)
    public void addFavorite() throws InterruptedException
    {
        //залогиниться
        login();
        Thread.sleep(3000);

        //Нажать на иконку звёздочки
        driver.findElement(By.id("favorite")).click();
        Thread.sleep(1000);

        //Нажать "принять"
        driver.findElement(By.id("gwt-debug-apply")).click();
        Thread.sleep(1000);

        //Нажать на иконку звёздочки
        driver.findElement(By.id("gwt-debug-12c338ac-168c-348b-a88c-b9594aed4be9")).click();
        Thread.sleep(1000);

        //Проверка
        WebElement element = driver.findElement(By.id("gwt-debug-menuItem"));
        String textElement = element.getText();
        String msg = "Сообщение. Ожидалось: %s, Получили: %s";
        Assertions.assertEquals("employee1 \"Чупшева Анна\"/Карточка сотрудника", textElement);

        //Нажать на иконку звёздочки слева
        driver.findElement(By.id("gwt-debug-12c338ac-168c-348b-a88c-b9594aed4be9")).click();

        //Разлогиниться
        driver.findElement(By.xpath("//a[contains(text(),'Выйти')]")).click();

    }

    /**
     * Тестирование на удаление карточки пользователя в избранное
     * @throws InterruptedException
     */
    @Test
    @Order(2)
    public void deleteFavorite() throws InterruptedException
    {
        //Залогиниться
        login();
        Thread.sleep(3000);

        //Нажать на иконку звёздочки слева
        driver.findElement(By.id("gwt-debug-12c338ac-168c-348b-a88c-b9594aed4be9")).click();
        Thread.sleep(1000);

        //Редактировать
        driver.findElement(By.id("gwt-debug-editFavorites")).click();
        Thread.sleep(1000);

        //Удалить
        driver.findElement(By.cssSelector(".del:nth-child(1)")).click();
        Thread.sleep(1000);

        //Нажать "Да"
        driver.findElement(By.id("gwt-debug-YES")).click();
        Thread.sleep(1000);

        //Сохранить
        driver.findElement(By.id("gwt-debug-apply")).click();
        Thread.sleep(1000);

        //Проверка
        WebElement element = driver.findElement(By.id("gwt-debug-menuItem"));
        String textElement = element.getText();
        String msg = "Сообщение. Ожидалось: %s, Получили: %s";
        Assertions.assertEquals("", textElement);

        //Нажать на иконку звёздочки слева
        driver.findElement(By.id("gwt-debug-12c338ac-168c-348b-a88c-b9594aed4be9")).click();

        //Разлогиниться
        driver.findElement(By.xpath("//a[contains(text(),'Выйти')]")).click();
    }

    private void login()
    {
        driver.get("https://test-m.sd.nau.run/sd/");

        //залогиниться
        driver.findElement(By.id("username")).sendKeys("ItsAnna21");
        driver.findElement(By.id("password")).sendKeys("123");
        driver.findElement(By.xpath("//input[@id ='submit-button']")).click();
    }

    @AfterAll
    public static void close()
    {
        driver.close();
    }

}
