package io.gitee.nn.electron.api.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReadBookmark {
    /**
     * Gets or sets the title.
     */
    private String title;

    /**
     * Gets or sets the URL.
     */
    private String url;
}
