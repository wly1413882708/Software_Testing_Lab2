package com.example.tests;

/*
 * ͨ��WebDriver������վ����ȡurl��Excel�ļ��е�url��Ƚ�
 */

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import org.junit.*;
import static org.junit.Assert.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;


public class TestCase {
  private WebDriver driver;
  private String baseUrl;
  private boolean acceptNextAlert = true;
  private StringBuffer verificationErrors = new StringBuffer();
  Func func = new Func();
  
  HashMap<String, String> map = new HashMap<String, String>();

  @Before
  public void setUp() throws Exception {
	//�ȶ���Excel�ļ�
	readExcel();
	
	System.setProperty("webdriver.chrome.driver", "webdriver\\chromedriver.exe");
    driver = new ChromeDriver();
    baseUrl = "https://www.katalon.com/";
    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
  }

  @Test
  public void testTestCase() throws Exception {
	Set set = map.entrySet();
	Iterator iterator = set.iterator();     
    while(iterator.hasNext()){     
    	Map.Entry mapentry = (Map.Entry)iterator.next();
    	//ѧ��
       	String id = mapentry.getKey().toString();
       	
       	//����
       	String pass = id.substring(id.length() - 6);
       	
       	//url
       	String url = mapentry.getValue().toString();
       	
       	//System.out.println(id + "-" + pass); 
       	//System.out.println(url);
       	driver.get("https://psych.liebes.top/st");
       	driver.findElement(By.id("username")).click();
       	driver.findElement(By.id("username")).clear();
       	driver.findElement(By.id("username")).sendKeys(id);
       	driver.findElement(By.id("password")).clear();
       	driver.findElement(By.id("password")).sendKeys(pass);
       	driver.findElement(By.name("loginForm")).submit();
       	String url0 = driver.findElement(By.xpath("//p")).getText();
       	
       	int len = url0.length();
    	
    	//��վ�л�ȡ��url�н�ĩβ�����б��ȥ��
    	if(url0.charAt(len - 1) == '/')
    		url0 = url0.substring(0, len - 1);
    	
       	assertEquals(url, url0);//�ж�
    }
  }

  @After
  public void tearDown() throws Exception {
    driver.quit();
    String verificationErrorString = verificationErrors.toString();
    if (!"".equals(verificationErrorString)) {
      fail(verificationErrorString);
    }
  }
  
  private void readExcel(){
	  try {
		map = func.testReadExcel();
	} catch (Exception e) {
		// TODO Auto-generated catch block
		System.out.println("��ȡExcel�ļ�����");
		e.printStackTrace();
	}
  }

  private boolean isElementPresent(By by) {
    try {
      driver.findElement(by);
      return true;
    } catch (NoSuchElementException e) {
      return false;
    }
  }

  private boolean isAlertPresent() {
    try {
      driver.switchTo().alert();
      return true;
    } catch (NoAlertPresentException e) {
      return false;
    }
  }

  private String closeAlertAndGetItsText() {
    try {
      Alert alert = driver.switchTo().alert();
      String alertText = alert.getText();
      if (acceptNextAlert) {
        alert.accept();
      } else {
        alert.dismiss();
      }
      return alertText;
    } finally {
      acceptNextAlert = true;
    }
  }
}
