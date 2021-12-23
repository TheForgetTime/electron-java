package io.gitee.nn.electron.api.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReleaseNoteInfo {
    /**
     * The version.
     */
    private String version;

    /**
     * The note.
     */
    private String note;
}