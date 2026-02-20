package org.sudal.responsecore.model.exception;

public interface ErrorObject {
    int getStatus();
    String getCode();
    String getDescription();
}
