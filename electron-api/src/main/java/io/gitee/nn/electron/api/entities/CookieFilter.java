package io.gitee.nn.electron.api.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CookieFilter {
    /**
     * (optional) - Retrieves cookies which are associated with url.Empty implies retrieving cookies of all URLs.
     */
    private String url;

    /**
     * (optional) - Filters cookies by name.
     */
    private String name;

    /**
     * (optional) - Retrieves cookies whose domains match or are subdomains of domains.
     */
    private String domain;

    /**
     * (optional) - Retrieves cookies whose path matches path.
     */
    private String path;

    /**
     * (optional) - Filters cookies by their Secure property.
     */
    private Boolean secure;

    /**
     * (optional) - Filters out session or persistent cookies.
     */
    private Boolean session;
}
