package uk.gov.hmcts.reform.httpproxy.spring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.net.MalformedURLException;
import java.net.URL;

@Component
public class HttpProxyInitialiser implements ApplicationListener<ApplicationReadyEvent> {

    private static final Logger LOGGER = LoggerFactory.getLogger(HttpProxyInitialiser.class);
    private final HttpProxyConfigurator httpProxyConfigurator;

    @Autowired
    public HttpProxyInitialiser(final HttpProxyConfigurator httpProxyConfigurator) {
        this.httpProxyConfigurator = httpProxyConfigurator;
    }

    @Override
    public void onApplicationEvent(final ApplicationReadyEvent event) {
        try {
            String httpProxyEnvVar = System.getenv("http_proxy");

            if (!StringUtils.isEmpty(httpProxyEnvVar)) {
                LOGGER.info("Proxy - Configuring: {}", httpProxyEnvVar);
                URL proxy = new URL(httpProxyEnvVar);

                httpProxyConfigurator
                    .setHttpProxy(proxy)
                    .setHttpsProxy(proxy)
                    .setNoProxy(System.getenv("no_proxy"));
            } else {
                LOGGER.info("No proxy found to configure");
            }
        } catch (MalformedURLException e) {
            throw new InvalidProxyException("Error parsing proxy URL", e);
        }
    }

}
