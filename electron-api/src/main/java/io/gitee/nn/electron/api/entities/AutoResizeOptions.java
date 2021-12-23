package io.gitee.nn.electron.api.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AutoResizeOptions {
    /**
     * If `true`, the view's width will grow and shrink together with the window.
     * `false` by default.
     */
    @Builder.Default
    private Boolean width = false;

    /**
     * If `true`, the view's height will grow and shrink together with the window.
     * `false` by default.
     */
    @Builder.Default
    private Boolean height = false;

    /**
     * If `true`, the view's x position and width will grow and shrink proportionally
     * with the window. `false` by default.
     */
    @Builder.Default
    private Boolean horizontal = false;

    /**
     * If `true`, the view's y position and height will grow and shrink proportionally
     * with the window. `false` by default.
     */
    @Builder.Default
    private Boolean vertical = false;
}
