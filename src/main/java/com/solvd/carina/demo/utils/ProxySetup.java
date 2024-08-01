package com.solvd.carina.demo.utils;

import com.google.gson.JsonParser;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import net.lightbody.bmp.BrowserMobProxy;
import net.lightbody.bmp.BrowserMobProxyServer;
import net.lightbody.bmp.client.ClientUtil;
import net.lightbody.bmp.core.har.Har;
import net.lightbody.bmp.core.har.HarEntry;
import net.lightbody.bmp.proxy.CaptureType;
import org.openqa.selenium.Proxy;
import org.testng.Assert;

import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ProxySetup {
    public BrowserMobProxy getProxyServer() {
        BrowserMobProxy proxy = new BrowserMobProxyServer();
        proxy.setTrustAllServers(true);
        proxy.start();
        return proxy;
    }

    //In getSeleniumProxy() method we wrap the browserMob proxy server into to a selenium proxy object which can later be passed as browser capability.
    public Proxy getSeleniumProxy(BrowserMobProxy proxyServer) {
        Proxy seleniumProxy = ClientUtil.createSeleniumProxy(proxyServer);
        try {
            String hostIp = Inet4Address.getLocalHost().getHostAddress();
            seleniumProxy.setHttpProxy(hostIp + ":" + proxyServer.getPort());
            seleniumProxy.setSslProxy(hostIp + ":" + proxyServer.getPort());
        } catch (UnknownHostException e) {
            e.printStackTrace();
            Assert.fail("Invalid Host Address");
        }
        return seleniumProxy;
    }

    public Har createHar(BrowserMobProxy proxy) {
        // enable more detailed HAR capture
        proxy.enableHarCaptureTypes(CaptureType.REQUEST_CONTENT, CaptureType.RESPONSE_CONTENT);
        return proxy.newHar();
    }

    public List<HarEntry> getHarEntries(BrowserMobProxy proxy, String trackingRequestUrl) {
        for(HarEntry entry :proxy.getHar().getLog().getEntries()){
            System.out.println("HarEntry: "+entry.getRequest().getUrl());
        }
        return proxy.getHar().getLog().getEntries().stream()
                .filter(entry -> entry.getRequest().getUrl().contains(trackingRequestUrl))
                .collect(Collectors.toList());
    }

    public Optional<String> getJsonPropertyValue(String property, String json) {
        return Optional.ofNullable(JsonParser.parseString(json))
                .filter(JsonElement::isJsonObject)
                .map(JsonElement::getAsJsonObject)
                .filter(jsonObject -> jsonObject.has(property) && jsonObject.get(property).isJsonPrimitive())
                .map(jsonObject -> jsonObject.get(property).getAsString());
    }

    public void stopProxyServer(BrowserMobProxy proxy){
        proxy.stop();
    }
}
