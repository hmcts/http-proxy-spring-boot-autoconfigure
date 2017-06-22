package uk.gov.hmcts.reform.httpproxy.spring;

import org.junit.Test;

import static java.util.Arrays.asList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class EnvNoProxyToJavaNonProxyHostsConverterTest {

    @Test
    public void createDeployment() {
        NonProxyHostsDeduplicator nonProxyHostsDeduplicator = mock(NonProxyHostsDeduplicator.class);
        when(nonProxyHostsDeduplicator.deduplicate(asList("a", "a", "c"))).thenReturn(asList("a", "c"));

        EnvNoProxyToJavaNonProxyHostsConverter converter =
            new EnvNoProxyToJavaNonProxyHostsConverter(nonProxyHostsDeduplicator);
        String converted = converter.convert("a,a,c");

        assertThat(converted).isEqualTo("a|c");
    }

}
