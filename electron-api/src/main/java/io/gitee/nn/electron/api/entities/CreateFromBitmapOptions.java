package io.gitee.nn.electron.api.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateFromBitmapOptions {
    /**
     * Gets or sets the width
     */
    private Integer width;

    /**
     * Gets or sets the height
     */
    private Integer height;

    /**
     * Gets or sets the scalefactor
     */
    @Builder.Default
    private Float scaleFactor = 1.0f;
}
