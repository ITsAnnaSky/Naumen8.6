import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import java.time.Duration;
import java.util.List;


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
    public void addFavorite() throws InterruptedException
    {
        //залогиниться
        login();
        Thread.sleep(3000);

        //Нажать на кнопку "Добавить в избранное"
        driver.findElement(By.id("favorite")).click();
        Thread.sleep(1000);

        //Нажать "Принять"
        driver.findElement(By.id("gwt-debug-apply")).click();
        Thread.sleep(1000);

        //Нажать на "Избранное" слева
        driver.findElement(By.id("gwt-debug-12c338ac-168c-348b-a88c-b9594aed4be9")).click();
        Thread.sleep(1000);

        //Проверка
        WebElement element = driver.findElement(By.id("gwt-debug-menuItem"));
        String textElement = element.getText();
        Assertions.assertEquals("employee1 \"Чупшева Анна\"/Карточка сотрудника", textElement);

        //Нажать на "Редактировать избранное"
        driver.findElement(By.id("gwt-debug-editFavorites")).click();
        Thread.sleep(1000);

        //Удалить карточку из списка
        driver.findElement(By.cssSelector(".del:nth-child(1)")).click();
        Thread.sleep(1000);

        //Нажать "Да"
        driver.findElement(By.id("gwt-debug-YES")).click();
        Thread.sleep(1000);

        //Нажать "Сохранить"
        driver.findElement(By.id("gwt-debug-apply")).click();
        Thread.sleep(1000);

        //Нажать на "Избранное" слева
        driver.findElement(By.id("gwt-debug-12c338ac-168c-348b-a88c-b9594aed4be9")).click();

        //Разлогиниться
        driver.findElement(By.xpath("//a[contains(text(),'Выйти')]")).click();

    }

    /**
     * Тестирование на удаление карточки пользователя в избранное
     * @throws InterruptedException
     */
    @Test
    public void deleteFavorite() throws InterruptedException
    {
        //Залогиниться
        login();
        Thread.sleep(3000);

        //Нажать на кнопку "Добавить в избранное"
        driver.findElement(By.id("favorite")).click();
        Thread.sleep(1000);

        //Нажать "Принять"
        driver.findElement(By.id("gwt-debug-apply")).click();
        Thread.sleep(1000);

        //Нажать на "Избранное" слева
        driver.findElement(By.id("gwt-debug-12c338ac-168c-348b-a88c-b9594aed4be9")).click();
        Thread.sleep(1000);

        //Нажать на "Редактировать избранное"
        driver.findElement(By.id("gwt-debug-editFavorites")).click();
        Thread.sleep(1000);

        //Удалить карточку из списка
        driver.findElement(By.cssSelector(".del:nth-child(1)")).click();
        Thread.sleep(1000);

        //Нажать "Да"
        driver.findElement(By.id("gwt-debug-YES")).click();
        Thread.sleep(1000);

        //Нажать "Сохранить"
        driver.findElement(By.id("gwt-debug-apply")).click();
        Thread.sleep(1000);

        //Проверка
        List<WebElement> element = driver.findElements(By.xpath("//*[@id='gwt-debug-menuItem']"));
        Assertions.assertFalse(element.isEmpty(), "Карточка пользователя не была удалена из меню");

        //Нажать на "Избранное" слева
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