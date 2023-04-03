package stepDefs;

import io.cucumber.java.After;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeDriverService;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;


public class userStepDefs {

    Actions actions;
    WebDriverWait wait;
    WebDriver driver;
    List<String> errorList = new ArrayList<>();

    @After
    public void tearUp() {
        driver.close();
        driver.quit();
    }

    @Given("I have opened chrome")
    public void iHaveOpenedChrome() {
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--remote-allow-origins=*", "ignore-certificate-errors");
        driver = new ChromeDriver(chromeOptions);
        System.setProperty("webdriver.chrome.driver", "D:\\!EC MVT22\\program\\chromedriver\\chromedriver.exe");
    }

    @Given("I have opened edge")
    public void iHaveOpenedEdge() {
        System.setProperty("webdriver.edge.verboselogging", "true");
        EdgeDriverService service = EdgeDriverService.createDefaultService();

        driver = new EdgeDriver(service);
    }

    @Given("I have entered MailChimp website")
    public void iHaveEnteredWebsite() {
        driver.get("https://login.mailchimp.com/signup/");
        driver.manage().window().maximize();
    }

    @Given("I have accepted cookies")
    public void iHaveAcceptedCookies() {
        waitClick(By.cssSelector("#onetrust-accept-btn-handler"));
    }

    @Given("I input email {string}")
    public void iHaveEmailEmail(String email) {
        waitSendKeys(By.id("email"), email);
    }

    @Given("I input username {string}")
    public void iHaveUsernameUsername(String username) {
        WebElement userBox = driver.findElement(By.id("new_username"));
        userBox.click();
        userBox.clear();

        if (username.equals("random")) {
            userBox.sendKeys(RandomStringUtils.randomAlphabetic(14));
        } else {
            userBox.sendKeys(username);
        }
    }

    @Given("I input password {string}")
    public void iHavePasswordPassword(String password) {
        WebElement passBox = driver.findElement(By.id("new_password"));
        passBox.sendKeys(password);
    }

    @When("I press sign up")
    public void iPressSignUp() {
        WebElement signUp = driver.findElement(By.xpath("//*[@id=\"create-account-enabled\"]"));
        actions = new Actions(driver);
        actions.moveToElement(signUp).perform();

        waitClick(By.xpath("//*[@id=\"create-account-enabled\"]"));
    }

    @When("there isnt a captcha")
    public void thereIsntACaptcha() {
        waitInvisElement(By.cssSelector("div[class=\"rc-imageselect-payload\"]"), 60);
    }

    @Then("I have made an account {string}")
    public void iHaveMadeAnAccount(String state) {
        if (state.equals("success")) {
            waitInvisElement(By.linkText("Please Wait..."), 30);
            wait.until(ExpectedConditions.titleIs("Success | Mailchimp"));

            String actual = driver.getTitle();
            String expected = "Success | Mailchimp";

            assertEquals(expected, actual);
        } else if (state.equals("fail")) {
            String actual = waitForText(By.cssSelector("[class='invalid-error']"));
            String expected = errorMessages(actual);

            assertEquals(actual, expected);
        }
    }

    private String errorMessages(String actual) {
        errorList.add("Great minds think alike - someone already has this username. If it's you, log in.");
        errorList.add("An email address must contain a single @.");
        errorList.add("Enter a value less than 100 characters long");

        if (errorList.contains(actual)) {
            System.out.println(actual);
            return actual;
        } else {
            return "Error doesnt exist";
        }
    }

    private void waitClick(By by) {
        wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        wait.until(ExpectedConditions.elementToBeClickable(by)).click();
    }

    private void waitSendKeys(By by, String text) {
        wait.until(ExpectedConditions.presenceOfElementLocated(by)).sendKeys(text);
    }

    private void waitInvisElement(By by, int secs) {
        wait = new WebDriverWait(driver, Duration.ofSeconds(secs));
        wait.until(ExpectedConditions.invisibilityOfElementLocated(by));
    }

    private String waitForText(By by) {
        wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(by));
        return element.getText();
    }
}