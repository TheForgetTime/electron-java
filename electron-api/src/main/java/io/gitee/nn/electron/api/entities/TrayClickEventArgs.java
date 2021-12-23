package io.gitee.nn.electron.api.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TrayClickEventArgs {
    /**
     * Gets or sets a value indicating whether [alt key].
     */
    private Boolean altKey;

    /**
     * Gets or sets a value indicating whether [shift key].
     */
    private Boolean shiftKey;

    /**
     * Gets or sets a value indicating whether [control key].
     */
    private Boolean ctrlKey;

    /**
     * Gets or sets a value indicating whether [meta key].
     */
    private Boolean metaKey;
}
