package tests;

import com.codeborne.selenide.Configuration;
import org.testng.annotations.BeforeMethod;

public class BaseTest {

    @BeforeMethod
    public void setUp(){
        Configuration.baseUrl = "https://log.finalsurge.com/";
        Configuration.timeout = 10000;
//        Configuration.holdBrowserOpen = true;
    }
}
