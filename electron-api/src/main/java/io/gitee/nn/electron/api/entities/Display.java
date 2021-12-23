package io.gitee.nn.electron.api.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Display {
    /**
     * Gets or sets the bounds.
     */
    private Rectangle bounds;

    /**
     * Unique identifier associated with the display.
     */
    private String id;

    /**
     * Can be 0, 90, 180, 270, represents screen rotation in clock-wise degrees.
     */
    private Integer rotation;

    /**
     * Output device's pixel scale factor.
     */
    private Integer scaleFactor;

    /**
     * Gets or sets the size.
     */
    private Size size;

    /**
     * Can be available, unavailable, unknown.
     */
    private String touchSupport;

    /**
     * Gets or sets the work area.
     */
    private Rectangle workArea;

    /**
     * Gets or sets the size of the work area.
     */
    private Size workAreaSize;
}
