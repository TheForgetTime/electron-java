package io.gitee.nn.electron.api.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SaveDialogOptions {
    /**
     * Gets or sets the title.
     */

    private String title;

    /**
     * Absolute directory path, absolute file path, or file name to use by default.
     */
    private String defaultPath;

    /**
     * Custom label for the confirmation button, when left empty the default label will be used.
     */
    private String buttonLabel;

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

    /**
     * Message to display above text fields.
     */
    private String message;

    /**
     * Custom label for the text displayed in front of the filename text field.
     */
    private String nameFieldLabel;

    /**
     * Show the tags input box, defaults to true.
     */
    private Boolean showsTagField;
}
