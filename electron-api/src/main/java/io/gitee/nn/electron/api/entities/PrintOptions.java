package io.gitee.nn.electron.api.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PrintOptions {
    public Boolean silent;
    public Boolean printBackground;
    public String deviceName;
    public Boolean color;
    public Integer marginsType;
    public Boolean landscape;
    public Float scaleFactor;
    public Integer pagesPerSheet;
    public Boolean copies;
    public Boolean collate;
    public PrintPageRange pageRanges;
    public String duplexMode;
    public PrintDpi dpi;
}
