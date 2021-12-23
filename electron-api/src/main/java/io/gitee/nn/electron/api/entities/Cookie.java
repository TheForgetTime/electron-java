package io.gitee.nn.electron.api.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Cookie {
    /**
     * (optional) - The expiration date of the cookie as the number of seconds since the UNIX epoch. Not provided for session cookies.
     */
    public long expirationDate;
    /**
     * The name of the cookie.
     */
    private String name;
    /**
     * The value of the cookie.
     */
    private String value;
    /**
     * (optional) - The domain of the cookie; this will be normalized with a preceding dot so that it's also valid for subdomains.
     */
    private String domain;
    /**
     * (optional) - Whether the cookie is a host-only cookie; this will only be true if no domain was passed.
     */
    private Boolean hostOnly;
    /**
     * (optional) - The path of the cookie.
     */
    private String path;
    /**
     * (optional) - Whether the cookie is marked as secure.
     */
    private Boolean secure;
    /**
     * (optional) - Whether the cookie is marked as HTTP only.
     */
    private Boolean httpOnly;
    /**
     * (optional) - Whether the cookie is a session cookie or a persistent cookie with an expiration date.
     */
    private Boolean session;
}
