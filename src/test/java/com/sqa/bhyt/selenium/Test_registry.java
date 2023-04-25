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


public class Test_registry {
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
    public void testRegistry1() {
//         Mở trang web cần test
        driver.get("http://localhost:4200/register");
        Actions action = new Actions(driver);

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
        Assert.assertEquals(Message.getText(), "Tên đăng nhập không được để trống");

    }


    //    Trang đăng nhập
    @Test
    public void testRegistry2() {
//         Mở trang web cần test
        driver.get("http://localhost:4200/register");
        Actions action = new Actions(driver);

        // Nhập thông tin đăng nhập
        WebElement usernameField = driver.findElement(By.id("username"));
        usernameField.sendKeys("1234");

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
        Assert.assertEquals(Message.getText(), "Tài khoản không hợp lệ vui lòng nhập lại!");

    }

    @Test
    public void testRegistry3() {
//         Mở trang web cần test
        driver.get("http://localhost:4200/register");
        Actions action = new Actions(driver);

        // Nhập thông tin đăng nhập
        WebElement usernameField = driver.findElement(By.id("username"));
        usernameField.sendKeys("sonnk@123456");

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
        Assert.assertEquals(Message.getText(), "Tài khoản không hợp lệ vui lòng nhập lại!");

    }

    @Test
    public void testRegistry4() {
//         Mở trang web cần test
        driver.get("http://localhost:4200/register");
        Actions action = new Actions(driver);

        // Nhập thông tin đăng nhập
        WebElement usernameField = driver.findElement(By.id("username"));
        usernameField.sendKeys("00120101012181");

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
    public void testRegistry5() {
//         Mở trang web cần test
        driver.get("http://localhost:4200/register");
        Actions action = new Actions(driver);

        // Nhập thông tin đăng nhập
        WebElement usernameField = driver.findElement(By.id("username"));
        usernameField.sendKeys("00120101012181");

        WebElement passwordField = driver.findElement(By.id("password"));
        passwordField.sendKeys("Tt1234");

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
        Assert.assertEquals(Message.getText(), "Mật khẩu không hợp lệ. Vui lòng nhập lại");

    }

    @Test
    public void testRegistry6() {
//         Mở trang web cần test
        driver.get("http://localhost:4200/register");
        Actions action = new Actions(driver);

        // Nhập thông tin đăng nhập
        WebElement usernameField = driver.findElement(By.id("username"));
        usernameField.sendKeys("00120101012181");

        WebElement passwordField = driver.findElement(By.id("password"));
        passwordField.sendKeys("Tt@1234");

        WebElement rePasswordField = driver.findElement(By.id("repassword"));
        rePasswordField.sendKeys("Tt@1234567");

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
        Assert.assertEquals(Message.getText(), "Nhập lại mã bảo mật không khớp");

    }

    @Test
    public void testRegistry7() {
//         Mở trang web cần test
        driver.get("http://localhost:4200/register");
        Actions action = new Actions(driver);

        // Nhập thông tin đăng nhập
        WebElement usernameField = driver.findElement(By.id("username"));
        usernameField.sendKeys("00120101012181");

        WebElement passwordField = driver.findElement(By.id("password"));
        passwordField.sendKeys("Tt@1234");

        WebElement rePasswordField = driver.findElement(By.id("repassword"));
        rePasswordField.sendKeys("Tt@1234");

        WebElement emailField = driver.findElement(By.id("email-address"));
        emailField.sendKeys("son@");

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
        Assert.assertEquals(Message.getText(), "Email không đúng định dạng");

    }

    @Test
    public void testRegistry8() {
//         Mở trang web cần test
        driver.get("http://localhost:4200/register");
        Actions action = new Actions(driver);

        // Nhập thông tin đăng nhập
        WebElement usernameField = driver.findElement(By.id("username"));
        usernameField.sendKeys("00120101012181");

        WebElement passwordField = driver.findElement(By.id("password"));
        passwordField.sendKeys("Tt@1234");

        WebElement rePasswordField = driver.findElement(By.id("repassword"));
        rePasswordField.sendKeys("Tt@1234");

        WebElement emailField = driver.findElement(By.id("email-address"));
        emailField.sendKeys("son@gmail.com");

        WebElement phoneField = driver.findElement(By.id("phone-number"));
        phoneField.sendKeys("0354678xx");

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
        Assert.assertEquals(Message.getText(), "Số điện thoại không đúng định dạng");

    }

    @Test
    public void testRegistry9() {
//         Mở trang web cần test
        driver.get("http://localhost:4200/register");
        Actions action = new Actions(driver);

        // Nhập thông tin đăng nhập
        WebElement usernameField = driver.findElement(By.id("username"));
        usernameField.sendKeys("00120101012181");

        WebElement passwordField = driver.findElement(By.id("password"));
        passwordField.sendKeys("Tt@1234");

        WebElement rePasswordField = driver.findElement(By.id("repassword"));
        rePasswordField.sendKeys("Tt@1234");

        WebElement emailField = driver.findElement(By.id("email-address"));
        emailField.sendKeys("son@gmail.com");

        WebElement phoneField = driver.findElement(By.id("phone-number"));
        phoneField.sendKeys("0354678123");

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
        Assert.assertEquals(Message.getText(), "Tài khoản đã tồn tại");

    }

}
