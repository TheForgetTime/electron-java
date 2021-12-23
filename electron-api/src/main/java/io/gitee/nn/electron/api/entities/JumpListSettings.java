package io.gitee.nn.electron.api.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JumpListSettings {
    @Builder.Default
    private Integer minItems = 0;
    @Builder.Default
    private JumpListItem[] RemovedItems = new JumpListItem[0];
}
