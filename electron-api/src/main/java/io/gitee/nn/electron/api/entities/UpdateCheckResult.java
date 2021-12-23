package io.gitee.nn.electron.api.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateCheckResult {
    @Builder.Default
    private UpdateInfo updateInfo = new UpdateInfo();
    private String[] download;
    private UpdateCancellationToken cancellationToken;
}
