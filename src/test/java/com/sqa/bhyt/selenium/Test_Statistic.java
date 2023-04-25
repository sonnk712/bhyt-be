package com.sqa.bhyt.selenium;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

import static java.lang.Thread.sleep;


public class Test_Statistic {
    private WebDriver driver;

    @Before
    public void setUp() {
        // Cài đặt driver của trình duyệt Chrome
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
    }

    @After
    public void tearDown() {
        // Đóng trình duyệt sau khi thực hiện xong test
        driver.quit();
    }

    public void login(){
        driver.get("http://localhost:4200/login");
        WebElement usernameField = driver.findElement(By.id("username"));
        usernameField.sendKeys("00120101012181");

        WebElement passwordField = driver.findElement(By.id("password"));
        passwordField.sendKeys("Tt@123456");

        // Submit form
        WebElement submitButton = driver.findElement(By.className("p-button-danger"));
        submitButton.click();
    }

    @Test
    public void testStat1() {
        login();

        // Nhập thông tin ngày thống kê
        WebElement time_Search = driver.findElement(By.xpath("//p-calendar"));
        time_Search.sendKeys("05/01/2023");

        // Submit form
        WebElement submitButton = driver.findElement(By.className("p-button-info"));
        submitButton.click();
        try {
            sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

//         Kiểm tra kết quả
        WebElement Message = driver.findElement(By.className("p-error"));
        Assert.assertEquals(Message.getText(), "Khoảng thời gian tìm kiếm không hợp lệ.");

    }

//    @Test
//    public void testStat2() {
//        login();
//
//        // Nhập thông tin ngày thống kê
//        WebElement time_Search = driver.findElement(By.xpath("//p-calendar"));
//        time_Search.sendKeys("05/03/2023 - 05/04/2023");
//
//        // Submit form
//        WebElement submitButton = driver.findElement(By.className("p-button-info"));
//        submitButton.click();
//        try {
//            sleep(1000);
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }
//
////         Kiểm tra kết quả
//        WebElement Message = driver.findElement(By.className("p-error"));
//        Assert.assertTrue(resultText.contains("05/01/2023") && resultText.contains("05/04/2023"));
//
//    }

    @Test
    public void testStat4() {
        login();

        // Nhập thông tin ngày thống kê
        WebElement time_Search = driver.findElement(By.xpath("//p-calendar"));
        time_Search.sendKeys("05/01/2023 - 05/04/2023");

        // Submit form
        WebElement submitButton = driver.findElement(By.className("p-button-info"));
        submitButton.click();
        try {
            sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

//         Kiểm tra kết quả
        WebElement results = (WebElement) driver.findElements(By.className("subject-id-column"));

        if(driver.findElements(By.className("subject-id-column")).isEmpty()) {
            WebElement tong = (WebElement) driver.findElements(By.className("count"));
            Assert.assertEquals(tong.getText(), "0");
        }
    }



}
