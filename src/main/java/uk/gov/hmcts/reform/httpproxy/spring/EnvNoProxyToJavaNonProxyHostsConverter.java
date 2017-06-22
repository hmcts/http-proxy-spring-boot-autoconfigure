package uk.gov.hmcts.reform.httpproxy.spring;

import org.springframework.stereotype.Component;

import java.util.List;

import static java.util.Arrays.asList;


@Component
public class EnvNoProxyToJavaNonProxyHostsConverter {

    private final NonProxyHostsDeduplicator nonProxyHostsDeduplicator;

    EnvNoProxyToJavaNonProxyHostsConverter() {
        this(new NonProxyHostsDeduplicator());
    }

    EnvNoProxyToJavaNonProxyHostsConverter(NonProxyHostsDeduplicator nonProxyHostsDeduplicator) {
        this.nonProxyHostsDeduplicator = nonProxyHostsDeduplicator;
    }

    String convert(String noProxyEnvVariable) {
        String[] hosts = noProxyEnvVariable.split(",");
        List<String> deduplicated = nonProxyHostsDeduplicator.deduplicate(asList(hosts));
        return String.join("|", deduplicated);
    }
}
