package uk.gov.hmcts.reform.httpproxy.spring;

import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.RestoreSystemProperties;

import java.net.MalformedURLException;
import java.net.URL;

import static org.junit.Assert.assertEquals;

public class HttpProxyConfiguratorTest {

    @Rule
    public final RestoreSystemProperties restoreSystemProperties = new RestoreSystemProperties();

    @Test
    public void proxyVariablesAreCorrect() throws MalformedURLException {
        String proxy = "http://acompanyproxy:3128";

        HttpProxyConfigurator httpProxyConfigurator = new HttpProxyConfigurator();
        URL proxyUrl = new URL(proxy);
        httpProxyConfigurator.setHttpProxy(proxyUrl)
            .setHttpsProxy(proxyUrl)
            .setNoProxy("localhost,127.0.0.0/8,127.0.1.1,127.0.1.1*,local.home");

        String proxyPort = Integer.toString(proxyUrl.getPort());

        assertEquals(proxyUrl.getHost(), System.getProperty("http.proxyHost"));
        assertEquals(proxyPort, System.getProperty("http.proxyPort"));

        assertEquals(proxyUrl.getHost(), System.getProperty("https.proxyHost"));
        assertEquals(proxyPort, System.getProperty("https.proxyPort"));

        assertEquals("localhost|127.0.0.0/8|127.0.1.1|127.0.1.1*|local.home",
            System.getProperty("http.nonProxyHosts"));
    }

}
