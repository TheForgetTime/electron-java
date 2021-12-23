package io.gitee.nn.electron.api.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CertificatePrincipal {
    /**
     * Common Name
     */
    private String commonName;

    /**
     * Country or region
     */
    private String country;

    /**
     * Locality
     */
    private String locality;

    /**
     * Organization names
     */
    private String[] organizations;

    /**
     * Organization Unit names
     */
    private String[] organizationUnits;

    /**
     * State or province
     */
    private String state;
}
