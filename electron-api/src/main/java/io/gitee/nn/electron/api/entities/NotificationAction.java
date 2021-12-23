package io.gitee.nn.electron.api.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotificationAction {
    /**
     * The label for the given action.
     */
    private String text;

    /**
     * The type of action, can be button.
     */
    private String type;
}
