package driver;

import com.thoughtworks.gauge.AfterSuite;
import com.thoughtworks.gauge.BeforeSpec;
import com.thoughtworks.gauge.BeforeSuite;
import com.thoughtworks.gauge.ExecutionContext;
import org.openqa.selenium.remote.RemoteWebDriver;

//Proje ayağa kalkarken ilk driver'a geliyor. Burada öncelik olarak before suit içinden kullanacağı tarayıcıyı veya cihazı seçiyor.
// BeforeSpec ile spec dosyamızın tanımlamasını yapıyor. Driver ayağa kalktıktan sonra spec dosyasına gidiyor. Tüm testler aynı cihaz ile bu sekmede koşuyor.
// BeforeSpec bittikten sonra AfterSuite ile test bittikten sonraki driver'ın sonlandırması yapılıyor.


public class Driver {
    public static RemoteWebDriver webDriver;
    public static String FEATURE_NAME;


    @BeforeSuite
    public void initializeDriver(){
        webDriver = DriverFactory.getDriver();
    }

    @BeforeSpec
    public void beforeSuite(ExecutionContext context){
        var featurePath  = context.getCurrentSpecification().getFileName();
        var fileNameIndex = featurePath.split("\\\\");
        for (String piece: fileNameIndex){
            if (piece.contains(".spec")){
                FEATURE_NAME = piece.replace(".spec","");
            }
        }

    }

    // Close the webDriver instance
    @AfterSuite
    public void closeDriver(){
        webDriver.quit();
    }
}
