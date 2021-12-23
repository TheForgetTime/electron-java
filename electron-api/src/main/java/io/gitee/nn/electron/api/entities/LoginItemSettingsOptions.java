package io.gitee.nn.electron.api.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginItemSettingsOptions {
    /**
     * The executable path to compare against. Defaults to process.execPath.
     */
    private String path;

    /**
     * The command-line arguments to compare against. Defaults to an empty array.
     */
    private String[] args;
}
