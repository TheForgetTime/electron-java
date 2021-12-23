package io.gitee.nn.electron.api.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JumpListCategory {
    private String name;

    /**
     * Array of objects if type is tasks or custom, otherwise it should be omitted.
     */
    private JumpListItem[] items;

    /**
     * One of the following: "tasks" | "frequent" | "recent" | "custom"
     */
    private JumpListCategoryType type;
}
