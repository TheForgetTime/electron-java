package io.gitee.nn.electron.api.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SemVer {
    public SemVerOptions options;
    private String raw;
    private Boolean loose;
    private Integer major;
    private Integer minor;
    private Integer patch;
    private String version;
    private String[] build;
    private String[] prerelease;
}
