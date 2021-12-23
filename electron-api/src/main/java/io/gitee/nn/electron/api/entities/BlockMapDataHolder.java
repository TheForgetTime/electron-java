package io.gitee.nn.electron.api.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class BlockMapDataHolder {
    /**
     * The file size. Used to verify downloaded size (save one HTTP request to get length).
     * Also used when block map data is embedded into the file(appimage, windows web installer package).
     */
    private Double size;

    /**
     * The block map file size. Used when block map data is embedded into the file (appimage, windows web installer package).
     * This information can be obtained from the file itself, but it requires additional HTTP request,
     * so, to reduce request count, block map size is specified in the update metadata too.
     */
    private Double blockMapSize;

    /**
     * The file checksum.
     */
    private String sha512;

    /**
     * ///
     */
    private Boolean isAdminRightsRequired;
}
