package uk.gov.hmcts.reform.httpproxy.spring;

import org.springframework.stereotype.Component;
import sun.misc.REException;
import sun.misc.RegexpPool;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Workaround for JDK bug (https://bugs.openjdk.java.net/browse/JDK-8145732).
 * A duplicate host in -Dhttp.nonProxyHosts results in hosts after it being ignored.
 * This class uses the same mechanism as DefaultProxySelector to find & filter
 * duplicate hosts so no bug is encountered. Ideally, this class should be removed
 * ASAP after the JDK bug is fixed.
 */
@Component
public class NonProxyHostsDeduplicator {
    public List<String> deduplicate(Collection<String> hosts) {
        List<String> uniqueHosts = new ArrayList<>();
        RegexpPool regexpPool = new RegexpPool();

        for (String host : sortWithWildcardsFirst(hosts)) {
            try {
                regexpPool.add(host.toLowerCase(), true);
                uniqueHosts.add(host);
            } catch (REException e) {
                // ignore duplicates
            }
        }

        return uniqueHosts;
    }

    /**
     * RegexpPool supports only hosts with '*' at the start or the end of the host.
     * This method bubbles those hosts up so when filtering duplicates we leave the
     * coarse-grained ones and remove fine-grained ones. This is not always 100% correct
     * but does the trick in our case.
     *
     * @param hosts collection of hosts to sort
     * @return list with hosts starting or ending with '*' coming first
     */
    private List<String> sortWithWildcardsFirst(Collection<String> hosts) {
        List<String> sorted = new ArrayList<>(hosts);
        sorted.sort((o1, o2) -> {
            int weight1 = o1.startsWith("*") ? 0 : o1.endsWith("*") ? 1 : 2;
            int weight2 = o2.startsWith("*") ? 0 : o2.endsWith("*") ? 1 : 2;
            return weight1 == weight2 ? o1.compareTo(o2) : weight1 - weight2;
        });
        return sorted;
    }

}
