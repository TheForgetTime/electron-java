package io.gitee.nn.electron.api.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProgressInfo {
    private String progress;
    private String bytesPerSecond;
    private String percent;
    private String total;
    private String transferred;
}
