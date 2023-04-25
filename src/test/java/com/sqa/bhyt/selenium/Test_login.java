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

public class Test_login {
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

    @Test
    public void testLogin1() {
        driver.get("http://localhost:4200/login");

        // Nhập thông tin đăng nhập
        WebElement usernameField = driver.findElement(By.id("username"));
        usernameField.sendKeys("");

        WebElement passwordField = driver.findElement(By.id("password"));
        passwordField.sendKeys("Tt@123456");

        // Submit form
        WebElement submitButton = driver.findElement(By.className("p-button-danger"));
        submitButton.click();
        try {
            sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

//         Kiểm tra kết quả
        WebElement Message = driver.findElement(By.className("p-error"));
        Assert.assertEquals(Message.getText(), "Tên đăng nhập không được bỏ trống");

    }


//    Trang đăng nhập
    @Test
    public void testLogin2() {
//         Mở trang web cần test
        driver.get("http://localhost:4200");
        Actions action = new Actions(driver);

        // Nhập thông tin đăng nhập
        WebElement usernameField = driver.findElement(By.id("username"));
        usernameField.sendKeys("00120101012182");

        WebElement passwordField = driver.findElement(By.id("password"));
        passwordField.sendKeys("");

        // Submit form
        WebElement submitButton = driver.findElement(By.className("p-button-danger"));
        submitButton.click();
        try {
            sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

//         Kiểm tra kết quả
        WebElement Message = driver.findElement(By.className("p-error"));
        Assert.assertEquals(Message.getText(), "Mật khẩu không được bỏ trống");

    }

    @Test
    public void testLogin3() {
//         Mở trang web cần test
        driver.get("http://localhost:4200");
        Actions action = new Actions(driver);

        // Nhập thông tin đăng nhập
        WebElement usernameField = driver.findElement(By.id("username"));
        usernameField.sendKeys("00120101012182");

        WebElement passwordField = driver.findElement(By.id("password"));
        passwordField.sendKeys("Tt@123456");

        // Submit form
        WebElement submitButton = driver.findElement(By.className("p-button-danger"));
        submitButton.click();
        try {
            sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

//         Kiểm tra kết quả
        WebElement Message = driver.findElement(By.className("p-error"));
        Assert.assertEquals(Message.getText(), "Tài khoản hoặc mật khẩu không chính xác!");

    }

    @Test
    public void testLogin4() {
//         Mở trang web cần test
        driver.get("http://localhost:4200");
        Actions action = new Actions(driver);

        // Nhập thông tin đăng nhập
        WebElement usernameField = driver.findElement(By.id("username"));
        usernameField.sendKeys("00120101012181");

        WebElement passwordField = driver.findElement(By.id("password"));
        passwordField.sendKeys("Tt@123456");

        // Submit form
        WebElement submitButton = driver.findElement(By.className("p-button-danger"));
        submitButton.click();
        try {
            sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

//         Kiểm tra kết quả
        WebElement Message = driver.findElement(By.className("p-my-5"));
        Assert.assertEquals(Message.getText(), "BÁO CÁO THỐNG KÊ");

    }

}
