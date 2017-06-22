package uk.gov.hmcts.reform.httpproxy.spring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.net.URL;

@Component
class HttpProxyConfigurator {

    private static final Logger LOGGER = LoggerFactory.getLogger(HttpProxyConfigurator.class);
    private static final String PROXY_SETTING_LOG_FORMAT = "Proxy - Setting {}: {}";

    private final EnvNoProxyToJavaNonProxyHostsConverter proxyHostsConverter;

    HttpProxyConfigurator(EnvNoProxyToJavaNonProxyHostsConverter proxyHostsConverter) {
        this.proxyHostsConverter = proxyHostsConverter;
    }

    HttpProxyConfigurator setHttpProxy(final URL proxy) {
        setProxy(proxy, "http");

        return this;
    }

    HttpProxyConfigurator setHttpsProxy(final URL proxy) {
        setProxy(proxy, "https");

        return this;
    }

    HttpProxyConfigurator setNoProxy(final String noProxy) {
        if (!StringUtils.isEmpty(noProxy)) {
            String javaNoProxy = proxyHostsConverter.convert(noProxy);
            String noProxySystemProperty = "http.nonProxyHosts";

            LOGGER.info(PROXY_SETTING_LOG_FORMAT, noProxySystemProperty, javaNoProxy);
            System.setProperty(noProxySystemProperty, javaNoProxy);
        }
        return this;
    }

    private void setProxy(final URL proxy, final String proxyScheme) {
        String proxyHostSystemProperty = proxyScheme + ".proxyHost";
        String proxyPortSystemProperty = proxyScheme + ".proxyPort";

        LOGGER.info(PROXY_SETTING_LOG_FORMAT, proxyHostSystemProperty, proxy.getHost());
        System.setProperty(proxyHostSystemProperty, proxy.getHost());
        LOGGER.info(PROXY_SETTING_LOG_FORMAT, proxyPortSystemProperty, proxy.getPort());
        System.setProperty(proxyPortSystemProperty, Integer.toString(proxy.getPort()));
    }
}
