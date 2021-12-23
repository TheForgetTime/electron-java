package io.gitee.nn.electron.api.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateInterruptedDownloadOptions {
    private String path;
    private String[] urlChain;
    private String mimeType;
    private Integer offset;
    private Integer length;
    private String lastModified;
    private String eTag;
    private Integer startTime;
}
