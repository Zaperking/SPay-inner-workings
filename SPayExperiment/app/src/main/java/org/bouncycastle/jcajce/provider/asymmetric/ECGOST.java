/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Object
 *  java.lang.String
 *  org.bouncycastle.jcajce.provider.asymmetric.ecgost.KeyFactorySpi
 *  org.bouncycastle.jcajce.provider.config.ConfigurableProvider
 */
package org.bouncycastle.jcajce.provider.asymmetric;

import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.cryptopro.CryptoProObjectIdentifiers;
import org.bouncycastle.jcajce.provider.asymmetric.ecgost.KeyFactorySpi;
import org.bouncycastle.jcajce.provider.config.ConfigurableProvider;
import org.bouncycastle.jcajce.provider.util.AsymmetricAlgorithmProvider;
import org.bouncycastle.jcajce.provider.util.AsymmetricKeyInfoConverter;

public class ECGOST {
    private static final String PREFIX = "org.bouncycastle.jcajce.provider.asymmetric.ecgost.";

    public static class Mappings
    extends AsymmetricAlgorithmProvider {
        @Override
        public void configure(ConfigurableProvider configurableProvider) {
            configurableProvider.addAlgorithm("KeyFactory.ECGOST3410", "org.bouncycastle.jcajce.provider.asymmetric.ecgost.KeyFactorySpi");
            configurableProvider.addAlgorithm("Alg.Alias.KeyFactory.GOST-3410-2001", "ECGOST3410");
            configurableProvider.addAlgorithm("Alg.Alias.KeyFactory.ECGOST-3410", "ECGOST3410");
            this.registerOid(configurableProvider, CryptoProObjectIdentifiers.gostR3410_2001, "ECGOST3410", (AsymmetricKeyInfoConverter)new KeyFactorySpi());
            this.registerOidAlgorithmParameters(configurableProvider, CryptoProObjectIdentifiers.gostR3410_2001, "ECGOST3410");
            configurableProvider.addAlgorithm("KeyPairGenerator.ECGOST3410", "org.bouncycastle.jcajce.provider.asymmetric.ecgost.KeyPairGeneratorSpi");
            configurableProvider.addAlgorithm("Alg.Alias.KeyPairGenerator.ECGOST-3410", "ECGOST3410");
            configurableProvider.addAlgorithm("Alg.Alias.KeyPairGenerator.GOST-3410-2001", "ECGOST3410");
            configurableProvider.addAlgorithm("Signature.ECGOST3410", "org.bouncycastle.jcajce.provider.asymmetric.ecgost.SignatureSpi");
            configurableProvider.addAlgorithm("Alg.Alias.Signature.ECGOST-3410", "ECGOST3410");
            configurableProvider.addAlgorithm("Alg.Alias.Signature.GOST-3410-2001", "ECGOST3410");
            this.addSignatureAlgorithm(configurableProvider, "GOST3411", "ECGOST3410", "org.bouncycastle.jcajce.provider.asymmetric.ecgost.SignatureSpi", CryptoProObjectIdentifiers.gostR3411_94_with_gostR3410_2001);
        }
    }

}

