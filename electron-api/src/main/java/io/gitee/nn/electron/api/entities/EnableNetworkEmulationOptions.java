package io.gitee.nn.electron.api.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EnableNetworkEmulationOptions {
    @Builder.Default
    private Boolean offline = false;
    private Integer latency;
    private Integer downloadThroughput;
    private Integer uploadThroughput;
}
