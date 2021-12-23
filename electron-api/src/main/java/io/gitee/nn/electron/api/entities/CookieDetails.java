package io.gitee.nn.electron.api.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CookieDetails {
    /**
     * The URL to associate the cookie with. The callback will be rejected if the URL is invalid.
     */
    private String url;

    /**
     * (optional) - The name of the cookie. Empty by default if omitted.
     */
    @Builder.Default
    private String name = "";

    /**
     * (optional) - The value of the cookie. Empty by default if omitted.
     */
    @Builder.Default
    private String value = "";

    /**
     * (optional) - The domain of the cookie; this will be normalized with a preceding dot so that it's also valid for subdomains. Empty by default if omitted.
     */
    @Builder.Default
    private String domain = "";

    /**
     * (optional) - The path of the cookie. Empty by default if omitted.
     */
    @Builder.Default
    private String path = "";

    /**
     * (optional) - Whether the cookie is marked as secure. Defaults to false.
     */
    @Builder.Default
    private Boolean secure = false;

    /**
     * (optional) - Whether the cookie is marked as HTTP only. Defaults to false.
     */
    @Builder.Default
    private Boolean httpOnly = false;

    /**
     * (optional) - The expiration date of the cookie as the number of seconds since the UNIX epoch.
     * If omitted then the cookie becomes a session cookie and will not be retained between sessions.
     */
    @Builder.Default
    private Long expirationDate = 0L;
}
