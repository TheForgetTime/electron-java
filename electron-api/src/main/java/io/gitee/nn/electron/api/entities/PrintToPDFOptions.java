package io.gitee.nn.electron.api.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PrintToPDFOptions {
    private Integer marginsType;
    private String pageSize;
    private Boolean printBackground;
    private Boolean printSelectionOnly;
    private Boolean landscape;
}
