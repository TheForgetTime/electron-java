package io.gitee.nn.electron.api.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppDetailsOptions {
    private String appId;
    private String appIconPath;
    private Integer appIconIndex;
    private String relaunchCommand;
    private String relaunchDisplayName;
}
