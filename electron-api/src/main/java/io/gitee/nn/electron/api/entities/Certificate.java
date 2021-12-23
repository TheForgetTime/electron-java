package io.gitee.nn.electron.api.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Certificate {
    /**
     * PEM encoded data
     */
    private String data;

    /**
     * Fingerprint of the certificate
     */
    private String fingerprint;

    /**
     * Issuer principal
     */
    private CertificatePrincipal issuer;

    /**
     * Issuer certificate (if not self-signed)
     */
    private Certificate issuerCert;

    /**
     * Issuer's Common Name
     */
    private String issuerName;

    /**
     * Hex value represented String
     */
    private String serialNumber;

    /**
     * Subject principal
     */
    private CertificatePrincipal subject;

    /**
     * Subject's Common Name
     */
    private String subjectName;

    /**
     * End date of the certificate being valid in seconds
     */
    private int validExpiry;

    /**
     * Start date of the certificate being valid in seconds
     */
    private int validStart;
}
