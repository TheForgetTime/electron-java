package io.gitee.nn.electron.api.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RemovePassword {
    private String origin;
    private String password;
    private String realm;
    private Scheme scheme;
    private String type;
    private String username;

    public RemovePassword(String type) {
        this.type = type;
    }
}
