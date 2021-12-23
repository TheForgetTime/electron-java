package io.gitee.nn.electron.api.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@lombok.Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Data {
    /**
     * Gets or sets the text.
     */
    private String text;

    /**
     * Gets or sets the HTML.
     */
    private String html;


    /**
     * Gets or sets the RTF.
     */
    private String rtf;

    /**
     * The title of the url at text.
     */
    private String bookmark;
}
