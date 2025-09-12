package org.menuservice.api.common.error;

public interface ErrorCodeIfs {

    Integer getHttpStatusCode();
    Integer getErrorCode();
    String getDescription();
}
