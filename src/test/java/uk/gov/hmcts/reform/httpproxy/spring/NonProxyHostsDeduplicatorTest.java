package uk.gov.hmcts.reform.httpproxy.spring;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


public class NonProxyHostsDeduplicatorTest {

    @Test
    public void uniqueHostsShouldBeLeftUntouched() {
        List<String> duplicatedHosts = Arrays.asList("127.0.0.1", "127.0.0.2", "127.0.0.3");
        List<String> deduplicated = new NonProxyHostsDeduplicator().deduplicate(duplicatedHosts);
        assertThat(deduplicated).containsExactly("127.0.0.1", "127.0.0.2", "127.0.0.3");
    }

    @Test
    public void duplicatedHostsShouldBeRemoved() {
        List<String> duplicatedHosts = Arrays.asList("127.0.0.2", "127.0.0.1", "127.0.0.1");
        List<String> deduplicated = new NonProxyHostsDeduplicator().deduplicate(duplicatedHosts);
        assertThat(deduplicated).containsExactly("127.0.0.1", "127.0.0.2");
    }

    @Test
    public void hostWithWilcardShouldHaveHigherPrecedence() {
        List<String> duplicatedHosts = Arrays.asList("127.0.0.1", "127.0.0.2", "127.0.0.1*");
        List<String> deduplicated = new NonProxyHostsDeduplicator().deduplicate(duplicatedHosts);
        assertThat(deduplicated).containsExactly("127.0.0.1*", "127.0.0.2");
    }

}
