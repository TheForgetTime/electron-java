package io.gitee.nn.electron.api.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OpenExternalOptions {
    /**
     * <see langword="true"/> to bring the opened application to the foreground. The default is <see langword="true"/>.
     */
    @Builder.Default
    private Boolean activate = true;

    /**
     * The working directory.
     */
    private String workingDirectory;
}
