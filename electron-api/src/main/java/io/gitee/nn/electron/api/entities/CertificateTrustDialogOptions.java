package io.gitee.nn.electron.api.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CertificateTrustDialogOptions {
    /**
     * The certificate to trust/import.
     */
    private Certificate certificate;

    /**
     * The message to display to the user.
     */
    private String message;
}
