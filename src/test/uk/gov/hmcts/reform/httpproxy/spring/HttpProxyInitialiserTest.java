package uk.gov.hmcts.reform.httpproxy.spring;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.EnvironmentVariables;

public class HttpProxyInitialiserTest {

    @Rule
    public final EnvironmentVariables environmentVariables = new EnvironmentVariables();

    private HttpProxyInitialiser httpProxyInitialiser;

    @Before
    public void setup() {
        httpProxyInitialiser = new HttpProxyInitialiser(new HttpProxyConfigurator());
    }

    @Test(expected = InvalidProxyException.class)
    public void testExceptionThrownIfInvalidProxy() {
        environmentVariables.set("http_proxy", "asdsada");
        httpProxyInitialiser.onApplicationEvent(null);
    }
}
