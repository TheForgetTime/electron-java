package io.gitee.nn.electron.api.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemoryInfo {
    /**
     * The amount of memory currently pinned to actual physical RAM.
     */
    public Integer workingSetSize;

    /**
     * The maximum amount of memory that has ever been pinned to actual physical RAM.
     */
    public Integer peakWorkingSetSize;

    /**
     * The amount of memory not shared by other processes, such as JS heap or HTML
     * content.
     */
    public Integer privateBytes;
}
