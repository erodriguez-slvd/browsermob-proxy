package com.solvd.carina.demo;

import com.solvd.carina.demo.utils.ProxySetup;
import com.zebrunner.carina.core.IAbstractTest;
import com.zebrunner.carina.utils.R;
import net.lightbody.bmp.BrowserMobProxy;
import net.lightbody.bmp.BrowserMobProxyServer;
import net.lightbody.bmp.client.ClientUtil;
import net.lightbody.bmp.core.har.HarEntry;
import net.lightbody.bmp.proxy.CaptureType;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.lang.invoke.MethodHandles;
import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.util.List;

public class BrowserMobProxyTest implements IAbstractTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @BeforeClass
    public void setUp() {
        R.CONFIG.put("browserup_proxy", "true", true);
        R.CONFIG.put("proxy_type", "MANUAL", true);
        R.CONFIG.put("proxy_port", "0", true);
    }

    @Test
    public void testProxy() {
        ProxySetup server = new ProxySetup();
        DesiredCapabilities capabilities = new DesiredCapabilities();
        BrowserMobProxy proxy = server.getProxyServer(); //getting browsermob proxy
        Proxy seleniumProxy = server.getSeleniumProxy(proxy);
        capabilities.setCapability(CapabilityType.PROXY, seleniumProxy);
        WebDriver driver = getDriver();
        server.createHar(proxy);
        driver.get(R.CONFIG.get("url"));
        List<HarEntry> filteredEntries = server.getHarEntries(proxy, R.TESTDATA.get("track_request_url"));
        Assert.assertFalse(filteredEntries.isEmpty(), "No requests to the specified URL were captured.");
        server.stopProxyServer(proxy);
    }

    @Test
    public void test() throws Exception {
        ProxySetup server = new ProxySetup();
        DesiredCapabilities capabilities = new DesiredCapabilities();
        BrowserMobProxy proxy = server.getProxyServer(); //getting browsermob proxy
        Proxy seleniumProxy = server.getSeleniumProxy(proxy);
        capabilities.setCapability(CapabilityType.PROXY, seleniumProxy);
        capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-web-security");
        options.addArguments("--allow-insecure-localhost");
        options.addArguments("--ignore-urlfetcher-cert-requests");
        WebDriver driver = getDriver();
        proxy.newHar(); // creating new HAR
        driver.get("http://www.google.com");
        List<HarEntry> entries = proxy.getHar().getLog().getEntries();
        for (HarEntry entry : entries) {
            System.out.println(entry.getRequest().getUrl());
        }
        Assert.assertFalse(entries.isEmpty(), "No requests to the specified URL were captured.");
        proxy.stop();
    }

    @Test
    public void trafficTest() throws InterruptedException {
        System.setProperty("webdriver.chrome.driver", "/Users/solvd/Documents/Projects/proxy/chromedriver");
        ProxySetup server = new ProxySetup();

        BrowserMobProxy proxy = new BrowserMobProxyServer();
        proxy.setTrustAllServers(true);
        proxy.start(0);

        Proxy seleniumProxy = ClientUtil.createSeleniumProxy(proxy);
        try {
            String hostIp = Inet4Address.getLocalHost().getHostAddress();
            seleniumProxy.setHttpProxy(hostIp + ":" + proxy.getPort());
            seleniumProxy.setSslProxy(hostIp + ":" + proxy.getPort());
        } catch (UnknownHostException e) {
            e.printStackTrace();
            Assert.fail("Invalid Host Address");
        }

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(CapabilityType.PROXY, seleniumProxy);
        capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-web-security");
        options.addArguments("--allow-insecure-localhost");
        options.addArguments("--ignore-urlfetcher-cert-requests");
        options.addArguments("--remote-allow-origins=*");
        options.merge(capabilities);

        WebDriver driver = new ChromeDriver(options);
        //WebDriver driver=getDriver();

        proxy.enableHarCaptureTypes(CaptureType.REQUEST_CONTENT, CaptureType.RESPONSE_CONTENT);
        proxy.newHar("solvd");

        driver.get(R.CONFIG.get("url"));
        Thread.sleep(3000);

        List<HarEntry> entries = proxy.getHar().getLog().getEntries();

        for (HarEntry entry : entries) {
            System.out.println("HarEntry: " + entry.getRequest().getUrl());
        }
        Assert.assertFalse(proxy.getHar().getLog().getEntries().isEmpty(), "No requests to the specified URL were captured.");
        proxy.stop();
    }

}
