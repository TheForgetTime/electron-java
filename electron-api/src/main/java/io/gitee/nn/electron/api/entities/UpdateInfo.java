package io.gitee.nn.electron.api.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateInfo {
    /**
     * The version.
     */
    private String version;

    /**
     *
     */
    @Builder.Default
    private UpdateFileInfo[] files = new UpdateFileInfo[0];

    /**
     * The release name.
     */
    private String releaseName;

    /**
     * The release notes.
     */
    @Builder.Default
    private ReleaseNoteInfo[] releaseNotes = new ReleaseNoteInfo[0];

    /**
     * ///
     */
    private String releaseDate;

    /**
     * The staged rollout percentage, 0-100.
     */
    private Integer stagingPercentage;
}
