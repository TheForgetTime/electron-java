package io.gitee.nn.electron.api.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ImportCertificateOptions {
    /**
     * Path for the pkcs12 file.
     */
    private String certificate;

    /**
     * Passphrase for the certificate.
     */
    private String password;
}
