import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.Duration;

/*
*   1.  Go to https://www.kurtosys.com/
    2.  Navigate to “RESOURCES”
    3.  From the dropdown, click on “White Papers & eBooks”
    4.  Verify Title reads “White Papers”
    5.  Click on any white paper tile (e.g., Click on “UCITS Whitepaper”)
    6.  Fill in Field for “First Name”
    7.  Fill in Field for “Last Name”
    8.  Fill in Field for “Company”
    9.  Fill in Field for “Industry”
    10. Please Note: DO NOT populate the "Email” field
    11. Click “Send me a copy”
    12. Add screenshot of the error messages
    13. Validate all error messages
* */
public class FunctionUiTest {
    public static void main(String[] args)
    {
        //  1.  Go to https://www.kurtosys.com/

        System.setProperty("webdriver.firefox.driver", "C:\\Selenium jars and Drivers\\drivers\\firefoxdriver\\geckodriver.exe");
        WebDriver driver = new FirefoxDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
        driver.get("https://www.kurtosys.com/");
        driver.manage().window().maximize();
        WebDriverWait aWait = new WebDriverWait(driver,Duration.ofSeconds(20));
        WebElement acceptCookies = aWait.until(ExpectedConditions.presenceOfElementLocated(By.id("onetrust-accept-btn-handler")));
        acceptCookies.click();


        //2.  Navigate to “RESOURCES”

        driver.navigate().to("https://www.kurtosys.com/resources/");


        //3.  From the dropdown, click on “White Papers & eBooks”
        WebElement hover = driver.findElement(By.xpath("//span[@class='menu-image-title' and text()='Insights']"));
        aWait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//span[@class='menu-image-title' and text()='Insights']")));


        Actions actions = new Actions(driver);
        actions.moveToElement(hover).perform();
        WebElement whitePapers = driver.findElement(By.xpath("//*[text()='White Papers & eBooks']"));
        aWait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[text()='White Papers & eBooks']")));
        whitePapers.click();


        //4.    Verify Title reads “White Papers”
        String title = driver.getTitle();
        System.out.println(title);
        if(title.contains("White Papers"))
        {
            System.out.println("PASS");
        }else
        {
            System.out.println("FAIL");
        }


        //5.  Click on any white paper tile (e.g., Click on “UCITS Whitepaper”)
        WebElement ucits = driver.findElement(By.xpath("/html/body/div[2]/div/section[2]/div/div/div/div/div/div/div/div[1]/article[6]/div/div[1]/p/a"));
        ucits.click();
        WebElement frameElement = aWait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("iframe")));
        driver.switchTo().frame(frameElement);


        //6.  Fill in Field for “First Name”
        WebElement name = driver.findElement(By.xpath("//input[@type='text' and @name='18882_231669pi_18882_231669']"));
        name.clear();
        name.sendKeys("Devon");
        System.out.println(name);


        //7.  Fill in Field for “Last Name”
        WebElement lastName = driver.findElement(By.id("18882_231671pi_18882_231671"));
        lastName.clear();
        lastName.sendKeys("McBain");
        System.out.println(lastName);

        //8.  Fill in Field for “Company”
        WebElement company = driver.findElement(By.id("18882_231675pi_18882_231675"));
        company.clear();
        company.sendKeys("TheBusiness");
        System.out.println(company);

        //9.  Fill in Field for “Industry”
        WebElement industry = driver.findElement(By.name("18882_231677pi_18882_231677"));
        industry.clear();
        industry.sendKeys("FineDance");
        System.out.println(industry);

        //10. Please Note: DO NOT populate the "Email” field
        WebElement email = driver.findElement(By.id("18882_231673pi_18882_231673"));
        email.sendKeys("");
        System.out.println(email);


        //11. Click “Send me a copy”
        WebElement submitForm=driver.findElement(By.xpath("//input[@type='submit']"));
        submitForm.submit();


        //12. Add screenshot of the error messages
        WebElement errorMsg = driver.findElement(By.className("errors"));
        String formError = errorMsg.getText();
        System.out.println(formError);

        WebElement errorMsg2 = driver.findElement(By.className("error"));
        String fieldError = errorMsg2.getText();
        System.out.println(fieldError);

        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(20));

        File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        String fileLocation = "C:\\Selenium jars and Drivers\\Screenshots\\screenshot.png";
        try
        {
            Path targetPath = Path.of(fileLocation);
            Files.move(screenshot.toPath(),targetPath,StandardCopyOption.REPLACE_EXISTING);
            System.out.println("Screenshot saved successfully at: " + fileLocation);
        } catch (IOException e)
        {
            e.printStackTrace();
            System.out.println("Failed to save screenshot.");
        }


        //13. Validate all error messages
        WebElement fieldElementName = driver.findElement(By.xpath("//input[@type='text' and @name='18882_231669pi_18882_231669']"));
        WebElement fieldElementLastName = driver.findElement(By.id("18882_231671pi_18882_231671"));
        WebElement fieldElementCompany = driver.findElement(By.id("18882_231675pi_18882_231675"));
        WebElement fieldElementIndustry = driver.findElement(By.name("18882_231677pi_18882_231677"));
        WebElement fieldElementEmail = driver.findElement(By.id("18882_231673pi_18882_231673"));

        // Get the value of the field
        String fieldValueName = fieldElementName.getAttribute("value");
        String fieldValueLastName = fieldElementLastName.getAttribute("value");
        String fieldValueCompany = fieldElementCompany.getAttribute("value");
        String fieldValueIndustry = fieldElementIndustry.getAttribute("value");
        String fieldValueEmail = fieldElementEmail.getAttribute("value");

        if (fieldValueLastName.isEmpty()||fieldValueName.isEmpty()||fieldValueCompany.isEmpty()||fieldValueIndustry.isEmpty()||fieldValueEmail.isEmpty())
        {
            driver.findElement(By.className("errors"));
            System.out.println("The user is required to complete the form data.");
        }
        if (fieldValueEmail.isEmpty())
        {
            driver.findElement(By.className("error"));
            System.out.println("The user is required to complete email address field.");
        }

        driver.quit();

    }

}

