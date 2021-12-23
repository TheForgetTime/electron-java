package io.gitee.nn.electron.api.entities;

import com.fasterxml.jackson.annotation.JsonValue;

public enum CookieChangedCause {
    /**
     * The cookie was changed directly by a consumer's action.
     */
    EXPLICIT("explicit"),

    /**
     * The cookie was automatically removed due to an insert operation that overwrote it.
     */
    OVERWRITE("overwrite"),

    /**
     * The cookie was automatically removed as it expired.
     */
    EXPIRED("expired"),

    /**
     * The cookie was automatically evicted during garbage collection.
     */
    EVICTED("evicted"),

    /**
     * The cookie was overwritten with an already-expired expiration date.
     */
    EXPIRED_OVERWRITE("expired_overwrite");

    private final String value;

    CookieChangedCause(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }
}
