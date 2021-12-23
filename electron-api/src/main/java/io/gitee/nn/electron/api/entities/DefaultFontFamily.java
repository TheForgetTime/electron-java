package io.gitee.nn.electron.api.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DefaultFontFamily {
    @Builder.Default
    private String standard = "Times New Roman";
    @Builder.Default
    private String serif = "Times New Roman";
    @Builder.Default
    private String sansSerif = "Arial";
    @Builder.Default
    private String monospace = "Courier New";
    @Builder.Default
    private String cursive = "Script";
    @Builder.Default
    private String fantasy = "Impact";
}
