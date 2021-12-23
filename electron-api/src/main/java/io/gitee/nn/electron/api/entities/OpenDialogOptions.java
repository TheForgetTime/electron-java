package io.gitee.nn.electron.api.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OpenDialogOptions {
    /**
     * Gets or sets the title.
     */

    private String title;

    /**
     * Gets or sets the default path.
     */
    private String defaultPath;

    /**
     * Custom label for the confirmation button, when left empty the default label will be used.
     */
    private String buttonLabel;

    /**
     * Contains which features the dialog should use. The following values are supported:
     * <br/>
     * 'openFile' | 'openDirectory' | 'multiSelections' | 'showHiddenFiles' | 'createDirectory' | 'promptToCreate' | 'noResolveAliases' | 'treatPackageAsDirectory'
     */
    private OpenDialogProperty[] properties;

    /**
     * Message to display above input boxes.
     */
    private String message;

    /**
     * The filters specifies an array of file types that can be displayed or
     * selected when you want to limit the user to a specific type. For example:
     * <pre>
     * <code>new FileFilter[]{
     * new FileFilter("Image",new String[]{"jpg","png","gif"}),
     * new FileFilter("Movies", new String[]{"mkv", "avi", "mp4"}),
     * new FileFilter("Custom File Type", new String[]{"as"}),
     * new FileFilter("All Files", new String[]{"*"})
     * };
     * </code>
     * </pre>
     */
    private FileFilter[] filters;
}
