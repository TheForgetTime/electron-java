package io.gitee.nn.electron.api.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MessageBoxResult {
    /**
     * Gets or sets the response.
     */
    private Integer response;

    /**
     * Gets or sets a value indicating whether [checkbox checked].
     * <br/>
     * <c>true</c> if [checkbox checked]; otherwise, <c>false</c>.
     */
    private Boolean checkboxChecked;
}
