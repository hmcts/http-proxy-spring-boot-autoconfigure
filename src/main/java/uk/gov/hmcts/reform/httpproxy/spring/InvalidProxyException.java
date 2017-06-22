package uk.gov.hmcts.reform.httpproxy.spring;

public class InvalidProxyException extends RuntimeException {

    public InvalidProxyException(String message, Throwable cause) {
        super(message, cause);
    }
}
