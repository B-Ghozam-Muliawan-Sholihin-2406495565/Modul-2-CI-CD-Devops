package id.ac.ui.cs.advprog.eshop.functional;

import io.github.bonigarcia.seljup.SeleniumJupiter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@ExtendWith(SeleniumJupiter.class)

public class CreateProductFunctionalTest {
    /**
     * The port number assigned to the running application during test execution.
     * Set automatically during each test run by Spring Framework's test context.
     */
    @LocalServerPort
    private int serverPort;

    /**
     * The base URL for testing. Default to {@code http://localhost}.
     */
    @Value("${app.baseUrl:http://localhost}")
    private String testBaseUrl;

    private String baseUrl;

    @BeforeEach
    void setupTest() {
        baseUrl = String.format("%s:%d", testBaseUrl, serverPort);
    }

    @Test
    void createProductAndCheck_IsCorrect(ChromeDriver driver) throws Exception {
        driver.get(baseUrl + "/product/list");
        
        WebElement create = driver.findElement(By.linkText("Create Product"));
        create.click();

        WebElement nameInput = driver.findElement(By.id("nameInput"));
        String name = "product test";
        nameInput.sendKeys(name);

        WebElement quantityInput = driver.findElement(By.id("quantityInput"));
        String quantity = "67";
        quantityInput.clear();
        quantityInput.sendKeys("\b\b\b\b\b"); // Extra backspaces to ensure field is empty
        quantityInput.sendKeys(quantity);

        WebElement submit = driver.findElement(By.className("btn-primary"));
        submit.click();

        String nameInList = driver.findElement(By.className("product-name")).getText();
        String quantInList = driver.findElement(By.className("product-quantity")).getText();
        
        assertEquals(name, nameInList);
        assertEquals(quantity, quantInList);
    }
}