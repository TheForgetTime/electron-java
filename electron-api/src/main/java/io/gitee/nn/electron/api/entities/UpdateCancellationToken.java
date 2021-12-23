package io.gitee.nn.electron.api.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateCancellationToken {
    private Boolean cancelled;

    public void Cancel() {

    }

    public void Dispose() {

    }
}
