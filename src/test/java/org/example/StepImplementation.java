package org.example;

import com.thoughtworks.gauge.Step;
import com.thoughtworks.gauge.Table;
import com.thoughtworks.gauge.TableRow;
import driver.Driver;
import exception.NoSuchSelector;
import exception.UndefinedEnum;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.util.HashSet;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.fail;
import static util.Mapper.foundActivity;

// Proje içinde kullandığımız veya kullanacağımız metodlar bu class altında tanımlanıyor. Tek sayfa olduğu için  StepImplementation class şuanlık yeterlidir.
// Bu class dosyası proje ilerledilçe OOP mantığına göre çoğaltılabilir.
// Bu classı aslında bir framework gibi düşünebiliriz. Burada yazdığımızı tüm metodları istenilen spec dosyasında çağırabiliyoruz.
// Guage framework'ü yardımı ile metod isimlerini kendimiz oluşturabildiğimizden, spec dosyasında yazılan caselerin yazımı daha kolay ve anlaşılır oluyor.
// Her metod tek bir iş yapıyor. Bu da bulunan hataların bulunması kolaylığı, projenin bakımı ve okunabilirliğini iyileştiriliyor.
// locatorValue map.Json dosyasımıza yazdığımız elementleri String olarak getiriyor. getElement ile birlikte aslında ön yüzde elemente ulaşmış oluyoruz.

public class StepImplementation {
    public RemoteWebDriver driver = Driver.webDriver;

    private WebElement getElement(String locatorValue) throws UndefinedEnum, NoSuchSelector {
        return driver.findElement(foundActivity(locatorValue, Driver.FEATURE_NAME));
    }

    private List<WebElement> getElements(String locatorValue) throws UndefinedEnum, NoSuchSelector {
        return driver.findElements(foundActivity(locatorValue, Driver.FEATURE_NAME));
    }

    @Step("Go to HomePage")
    public void gotoGetStartedPage() throws InterruptedException {
        driver.get(System.getenv("APP_URL"));
        driver.manage().window().maximize();
    }

    @Step("Go to <url> URL")
    public void gotoGetPage(String url) throws Exception {
        driver.navigate().to(url);
    }

    @Step({"<locatorValue> click element."})
    public void click(String locatorValue) throws Exception {
        getElement(locatorValue).click();
    }

    @Step({" delete the data in the <locatorValue> "})
    public void delete(String locatorValue) throws Exception {
        int length = getElement(locatorValue).getAttribute("value").length();
        for (int i = 0; i < length; ++i) {
            getElement(locatorValue).sendKeys(Keys.BACK_SPACE);
        }
    }

    @Step({"Send keys <input> in <locatorValue>."})
    public void sendKeys(String input, String locatorValue) throws Exception {
        getElement(locatorValue).sendKeys(input);
    }

    @Step({"Send keys number <input> in <locatorValue>."})
    public void sendKeysNumber(String input,String locatorValue) throws Exception {
        char[] ch=input.toCharArray();
        for(int i=0;i<ch.length;i++) {
            getElement(locatorValue).sendKeys(String.valueOf(ch[i]));
        }

    }

    @Step("<locatorValue> display element.")
    public void isDisplay(String locatorValue) throws Exception {
        Assertions.assertTrue(getElement(locatorValue).isDisplayed());
    }

    @Step("Stop the test for <second> seconds.")
    public void pauseTest(int second) throws InterruptedException {
        Thread.sleep((second * 1000L));
    }

    @Step({"The text of the <locatorValue> element matches <text>."})
    public void assertText(String locatorValue, String text) throws Exception {
        var locatorText = getElement(locatorValue).getText().replaceAll("\\s","");
        var writeText = text.replaceAll("\\s","");
        Assertions.assertEquals(locatorText, writeText);
    }

    @Step("Error message <message> appears in <element> element.")
    public void validationMessage(String message, String element) throws UndefinedEnum, NoSuchSelector {
        String messageText = getElement(element).getAttribute("validationMessage");
        System.out.println(messageText);
        Assertions.assertEquals(message,messageText);
    }

    @Step("<locatorValue> The dropdown is verified.")
    public void assertDropdown(String locatorValue) throws Exception{
        List<WebElement> liste = getElements(locatorValue);
        int number = liste.size();
        if (number >= 12){
            System.out.println("True");
        }else {
            fail("Fail");
        }
    }

    @Step("<locatorValue> Dropdown random number is selected.")
    public void randomSelectDropdownList(String locatorValue) throws Exception{
        List<WebElement> liste = getElements(locatorValue);
        int number = liste.size();
        int randomNumber = ThreadLocalRandom.current().nextInt(0, number);
        liste.get(randomNumber).click();
    }


}
