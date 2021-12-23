package io.gitee.nn.electron.api.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CPUUsage {
    /**
     * Percentage of CPU used since the last call to getCPUUsage. First call returns 0.
     */
    private Integer percentCPUUsage;

    /**
     * The number of average idle cpu wakeups per second since the last call to
     * getCPUUsage.First call returns 0.
     */
    private Integer idleWakeupsPerSecond;
}
