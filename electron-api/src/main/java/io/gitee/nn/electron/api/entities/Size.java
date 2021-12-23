package io.gitee.nn.electron.api.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Size {
    /**
     * Gets or sets the width.
     */
    private Integer width;

    /**
     * Gets or sets the height.
     */
    private Integer height;
}
