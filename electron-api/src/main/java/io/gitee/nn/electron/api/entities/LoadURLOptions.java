package io.gitee.nn.electron.api.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoadURLOptions {
    private String httpReferrer;
    private String userAgent;
    private String baseURLForDataURL;
    private String extraHeaders;
    private IPostData[] postData;
}
