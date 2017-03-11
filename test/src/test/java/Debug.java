package test.java;

import com.qa.framework.bean.TestData;
import com.qa.framework.core.DataManager;
import com.qa.framework.core.TestBase;
import com.qa.framework.mock.IMockServer;
import com.qa.framework.testnglistener.PowerEmailableReporter;
import com.qa.framework.testnglistener.TestResultListener;
import org.testng.annotations.*;

@Listeners({TestResultListener.class, PowerEmailableReporter.class})
public class Debug extends TestBase {

    private IMockServer mockServer = null;

    @BeforeSuite(alwaysRun = true)
    public void beforeClass() throws IllegalAccessException, InstantiationException {
        mockServer = new MockServer();
        mockServer.startServer();
        mockServer.settingRules();
    }

    @AfterSuite(alwaysRun = true)
    public void afterClass() {
        if (mockServer != null) {
            mockServer.stopServer();
        }
    }

    @Test(dataProviderClass = DataManager.class, dataProvider = "data")
    public void debug(TestData testData, String url, String httpMethod) {
        processSetupResultParam(testData);
        String content = request(url, testData.getHeaders(), testData.getParams(), httpMethod, testData.isStoreCookie(), testData.isUseCookie());
        verifyResult(testData, content);
    }

    /*xmlName:需要运行的xml文件名
    * xmlDataName:需要运行的testdata的名称
    * 不写Testdata的名称运行整个xml接口文件*/
    @BeforeTest(alwaysRun = true)
    public void before() {
        String xmlName = "demo";
        String xmlDataName = "bookSuccess";
        DataManager.setXmlName(xmlName);
        if (!xmlDataName.equalsIgnoreCase("")) {
            DataManager.setXmlDataName(xmlDataName);
        }
    }
}




