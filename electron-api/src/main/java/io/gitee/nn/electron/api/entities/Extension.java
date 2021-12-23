package io.gitee.nn.electron.api.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Extension {
    private String id;
    private Object manifest;
    private String name;
    private String path;
    private String url;
    private String version;
}
