package com.example.demo;

import com.example.demo.utils.HttpUtil;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;




public class AC_ACTR_GET {

/*    private WebDriver driver;
    @BeforeEach
    public void setUp(){
        //Chrome	89.0.4389.114 (공식 빌드) (64비트) (cohort: Stable)
        driver = new FirefoxDriver(); // Driver 생성
        
    }
    @AfterEach
    public void tearDown(){
        //driver.quit();  // Driver 종료
    }
*/	
    /*
    @Test
    
    public void test_title(){ //타이틀 확인하는 테스트 코드
//        System.setProperty("webdriver.chrome.driver", "C:/Users/jijsx/Downloads/chromedriver_win32/chromedriver.exe"); // 다운받은 ChromeDriver 위치를 넣어줍니다.
        System.setProperty("webdriver.gecko.driver", "C:/Users/jijsx/geckodriver-v0.29.1-win64/geckodriver.exe"); // 다운받은 ChromeDriver 위치를 넣어줍니다.
        driver.get("https://www.avdbs.com/menu/actor.php?actor_idx=2675"); // URL로 접속하기
        System.out.println("aaaaaa0");
        String tmp =driver.getPageSource();
        String url = driver.getCurrentUrl();

        
        System.out.println("aaaaaa1");
        System.out.println(url);
        System.out.println("aaaaaa2");
        System.out.println(tmp);
        

        //Assert.assertThat(driver.getTitle(),is("URL의 Title")); // Title 확인 작업
    }
    */
    @Test
    void download() {
        //String tmp =httpGet("https://www.avdbs.com/");
        HttpUtil h = new HttpUtil();
        //String tmp =h.httpGet("http://www.avdbs.com/menu/actor.php?actor_idx=6826");
        String tmp =h.httpGetAvdbs("https://www.avdbs.com/menu/dvd.php?dvd_idx=790097");
        //System.out.println(tmp);
        Document doc = Jsoup.parseBodyFragment(tmp);
        String mv_nm =  doc.getElementsByClass("profile_view_top").select(".tomato").text(); //작품번호
        String dvd_img_src  = doc.getElementById("dvd_img_src").attr("src");  

        String title_kr  = doc.getElementById("title_kr").text();
        String main_actr_idx  = doc.getElementsByClass("inner_name_cn").select("a").attr("href");
        Elements profile_detail  = doc.getElementsByClass("profile_detail").select("p");
        
        System.out.println(profile_detail.text());
    }

  

     

}
