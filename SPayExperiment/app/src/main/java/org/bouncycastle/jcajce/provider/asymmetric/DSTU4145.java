/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Object
 *  java.lang.String
 *  org.bouncycastle.jcajce.provider.config.ConfigurableProvider
 */
package org.bouncycastle.jcajce.provider.asymmetric;

import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.ua.UAObjectIdentifiers;
import org.bouncycastle.jcajce.provider.asymmetric.dstu.KeyFactorySpi;
import org.bouncycastle.jcajce.provider.config.ConfigurableProvider;
import org.bouncycastle.jcajce.provider.util.AsymmetricAlgorithmProvider;
import org.bouncycastle.jcajce.provider.util.AsymmetricKeyInfoConverter;

public class DSTU4145 {
    private static final String PREFIX = "org.bouncycastle.jcajce.provider.asymmetric.dstu.";

    public static class Mappings
    extends AsymmetricAlgorithmProvider {
        @Override
        public void configure(ConfigurableProvider configurableProvider) {
            configurableProvider.addAlgorithm("KeyFactory.DSTU4145", "org.bouncycastle.jcajce.provider.asymmetric.dstu.KeyFactorySpi");
            configurableProvider.addAlgorithm("Alg.Alias.KeyFactory.DSTU-4145-2002", "DSTU4145");
            configurableProvider.addAlgorithm("Alg.Alias.KeyFactory.DSTU4145-3410", "DSTU4145");
            this.registerOid(configurableProvider, UAObjectIdentifiers.dstu4145le, "DSTU4145", new KeyFactorySpi());
            this.registerOidAlgorithmParameters(configurableProvider, UAObjectIdentifiers.dstu4145le, "DSTU4145");
            this.registerOid(configurableProvider, UAObjectIdentifiers.dstu4145be, "DSTU4145", new KeyFactorySpi());
            this.registerOidAlgorithmParameters(configurableProvider, UAObjectIdentifiers.dstu4145be, "DSTU4145");
            configurableProvider.addAlgorithm("KeyPairGenerator.DSTU4145", "org.bouncycastle.jcajce.provider.asymmetric.dstu.KeyPairGeneratorSpi");
            configurableProvider.addAlgorithm("Alg.Alias.KeyPairGenerator.DSTU-4145", "DSTU4145");
            configurableProvider.addAlgorithm("Alg.Alias.KeyPairGenerator.DSTU-4145-2002", "DSTU4145");
            configurableProvider.addAlgorithm("Signature.DSTU4145", "org.bouncycastle.jcajce.provider.asymmetric.dstu.SignatureSpi");
            configurableProvider.addAlgorithm("Alg.Alias.Signature.DSTU-4145", "DSTU4145");
            configurableProvider.addAlgorithm("Alg.Alias.Signature.DSTU-4145-2002", "DSTU4145");
            this.addSignatureAlgorithm(configurableProvider, "GOST3411", "DSTU4145LE", "org.bouncycastle.jcajce.provider.asymmetric.dstu.SignatureSpiLe", UAObjectIdentifiers.dstu4145le);
            this.addSignatureAlgorithm(configurableProvider, "GOST3411", "DSTU4145", "org.bouncycastle.jcajce.provider.asymmetric.dstu.SignatureSpi", UAObjectIdentifiers.dstu4145be);
        }
    }

}
