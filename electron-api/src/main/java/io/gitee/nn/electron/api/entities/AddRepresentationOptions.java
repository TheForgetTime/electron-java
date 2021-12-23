package io.gitee.nn.electron.api.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddRepresentationOptions {
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

    /**
     * Gets or sets the buffer
     */
    private byte[] buffer;

    /**
     * Gets or sets the dataURL
     */
    private String dataUrl;
}
