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

public class Test_verify {
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
    public void testVerify1() {
//         Mở trang web cần test
        driver.get("http://localhost:4200");
        Actions action = new Actions(driver);


//         Kiểm tra kết quả
        WebElement Message = driver.findElement(By.className("p-error"));
        Assert.assertEquals(Message.getText(), "Tài khoản không tồn tại");

    }

    @Test
    public void testVerify2() {
        login();

        // Nhập thông tin đăng nhập
        WebElement nameField = driver.findElement(By.id("full-name"));
        nameField.sendKeys("Nguyễn Khắc Sơn");

        WebElement genderField = driver.findElement(By.id("gender"));
        genderField.sendKeys("Male");

        WebElement countryField = driver.findElement(By.id("country"));
        countryField.sendKeys("Việt Nam");

        WebElement residentAddressField = driver.findElement(By.id("resident-address"));
        residentAddressField.sendKeys("Hạ Mỗ, Đan Phượng, Hà Nọi");

        WebElement currentAddressField = driver.findElement(By.id("current-address"));
        currentAddressField.sendKeys("Cụm 7, xã Hạ Mỗ, huyện Đan Phượng, thành phố Hà nội");

        WebElement optField = driver.findElement(By.id("otp"));
        optField.sendKeys("983522");

        try {
            sleep(10000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        // Submit form
        WebElement submitButton = driver.findElement(By.className("p-button-success"));
        submitButton.click();


//         Kiểm tra kết quả
        WebElement Message = driver.findElement(By.className("p-error"));
        Assert.assertEquals(Message.getText(), "Mã xác minh hết hiệu lực.");

    }

    @Test
    public void testVerify3() {
        login();

        // Nhập thông tin đăng nhập
        WebElement nameField = driver.findElement(By.id("full-name"));
        nameField.sendKeys("Nguyễn Khắc Sơn");

        WebElement genderField = driver.findElement(By.id("gender"));
        genderField.sendKeys("Male");

        WebElement countryField = driver.findElement(By.id("country"));
        countryField.sendKeys("Việt Nam");

        WebElement residentAddressField = driver.findElement(By.id("resident-address"));
        residentAddressField.sendKeys("Hạ Mỗ, Đan Phượng, Hà Nọi");

        WebElement currentAddressField = driver.findElement(By.id("current-address"));
        currentAddressField.sendKeys("Cụm 7, xã Hạ Mỗ, huyện Đan Phượng, thành phố Hà nội");

        WebElement optField = driver.findElement(By.id("otp"));
        optField.sendKeys("");

        try {
            sleep(10000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        // Submit form
        WebElement submitButton = driver.findElement(By.className("p-button-success"));
        submitButton.click();


//         Kiểm tra kết quả
        WebElement Message = driver.findElement(By.className("p-error"));
        Assert.assertEquals(Message.getText(), "OTP không được bỏ trống");

    }

    @Test
    public void testVerify4() {
        login();

        // Nhập thông tin đăng nhập
        WebElement nameField = driver.findElement(By.id("full-name"));
        nameField.sendKeys("Nguyễn Khắc Sơn");

        WebElement genderField = driver.findElement(By.id("gender"));
        genderField.sendKeys("Male");

        WebElement countryField = driver.findElement(By.id("country"));
        countryField.sendKeys("Việt Nam");

        WebElement residentAddressField = driver.findElement(By.id("resident-address"));
        residentAddressField.sendKeys("Hạ Mỗ, Đan Phượng, Hà Nọi");

        WebElement currentAddressField = driver.findElement(By.id("current-address"));
        currentAddressField.sendKeys("Cụm 7, xã Hạ Mỗ, huyện Đan Phượng, thành phố Hồ Chí Minh");

        WebElement optField = driver.findElement(By.id("otp"));
        optField.sendKeys("867586");

        try {
            sleep(10000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        // Submit form
        WebElement submitButton = driver.findElement(By.className("p-button-success"));
        submitButton.click();


//         Kiểm tra kết quả
        WebElement Message = driver.findElement(By.className("p-error"));
        Assert.assertEquals(Message.getText(), "Thông tin xác thực không chính xác");

    }

    @Test
    public void testVerify5() {
        login();

        // Nhập thông tin đăng nhập
        WebElement nameField = driver.findElement(By.id("full-name"));
        nameField.sendKeys("Nguyễn Khắc Sơn");

        WebElement genderField = driver.findElement(By.id("gender"));
        genderField.sendKeys("Male");

        WebElement countryField = driver.findElement(By.id("country"));
        countryField.sendKeys("Việt Nam");

        WebElement residentAddressField = driver.findElement(By.id("resident-address"));
        residentAddressField.sendKeys("Hạ Mỗ, Đan Phượng, Hà Nọi");

        WebElement currentAddressField = driver.findElement(By.id("current-address"));
        currentAddressField.sendKeys("Cụm 7, xã Hạ Mỗ, huyện Đan Phượng, thành phố Hà nội");

        WebElement optField = driver.findElement(By.id("otp"));
        optField.sendKeys("867586");

        try {
            sleep(10000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        // Submit form
        WebElement submitButton = driver.findElement(By.className("p-button-success"));
        submitButton.click();


//         Kiểm tra kết quả
        WebElement Message = driver.findElement(By.className("p-error"));
        Assert.assertEquals(Message.getText(), "Cập nhật tài khoản thành công");

    }




}

