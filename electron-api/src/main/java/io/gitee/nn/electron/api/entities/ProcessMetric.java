package io.gitee.nn.electron.api.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProcessMetric {
    /**
     * Process id of the process.
     */
    private int pId;

    /**
     * Process type (Browser or Tab or GPU etc).
     */
    private String type;

    /**
     * CPU usage of the process.
     */
    private CPUUsage cpu;

    /**
     * Creation time for this process. The time is represented as number of milliseconds since epoch.
     * Since the <see cref="PId"/> can be reused after a process dies, it is useful to use both the <see cref="PId"/>
     * and the <see cref="CreationTime"/> to uniquely identify a process.
     */
    private int creationTime;

    /**
     * Memory information for the process.
     */
    private MemoryInfo memory;

    /**
     * Whether the process is sandboxed on OS level.
     */
    private Boolean sandboxed;

    /**
     * One of the following values:
     * untrusted | low | medium | high | unknown
     */
    private String integrityLevel;
}
