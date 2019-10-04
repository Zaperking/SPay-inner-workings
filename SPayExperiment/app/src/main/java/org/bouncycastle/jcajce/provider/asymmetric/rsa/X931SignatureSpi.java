/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Exception
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.UnsupportedOperationException
 *  java.security.PrivateKey
 *  java.security.PublicKey
 *  java.security.SignatureException
 *  java.security.SignatureSpi
 *  java.security.interfaces.RSAPrivateKey
 *  java.security.interfaces.RSAPublicKey
 *  java.security.spec.AlgorithmParameterSpec
 */
package org.bouncycastle.jcajce.provider.asymmetric.rsa;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SignatureException;
import java.security.SignatureSpi;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.AlgorithmParameterSpec;
import org.bouncycastle.crypto.AsymmetricBlockCipher;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.Digest;
import org.bouncycastle.crypto.digests.RIPEMD128Digest;
import org.bouncycastle.crypto.digests.RIPEMD160Digest;
import org.bouncycastle.crypto.digests.SHA1Digest;
import org.bouncycastle.crypto.digests.SHA224Digest;
import org.bouncycastle.crypto.digests.SHA256Digest;
import org.bouncycastle.crypto.digests.SHA384Digest;
import org.bouncycastle.crypto.digests.SHA512Digest;
import org.bouncycastle.crypto.digests.WhirlpoolDigest;
import org.bouncycastle.crypto.engines.RSABlindedEngine;
import org.bouncycastle.crypto.params.RSAKeyParameters;
import org.bouncycastle.crypto.signers.X931Signer;
import org.bouncycastle.jcajce.provider.asymmetric.rsa.RSAUtil;

public class X931SignatureSpi
extends SignatureSpi {
    private X931Signer signer;

    protected X931SignatureSpi(Digest digest, AsymmetricBlockCipher asymmetricBlockCipher) {
        this.signer = new X931Signer(asymmetricBlockCipher, digest);
    }

    protected Object engineGetParameter(String string) {
        throw new UnsupportedOperationException("engineSetParameter unsupported");
    }

    protected void engineInitSign(PrivateKey privateKey) {
        RSAKeyParameters rSAKeyParameters = RSAUtil.generatePrivateKeyParameter((RSAPrivateKey)privateKey);
        this.signer.init(true, rSAKeyParameters);
    }

    protected void engineInitVerify(PublicKey publicKey) {
        RSAKeyParameters rSAKeyParameters = RSAUtil.generatePublicKeyParameter((RSAPublicKey)publicKey);
        this.signer.init(false, rSAKeyParameters);
    }

    protected void engineSetParameter(String string, Object object) {
        throw new UnsupportedOperationException("engineSetParameter unsupported");
    }

    protected void engineSetParameter(AlgorithmParameterSpec algorithmParameterSpec) {
        throw new UnsupportedOperationException("engineSetParameter unsupported");
    }

    protected byte[] engineSign() {
        try {
            byte[] arrby = this.signer.generateSignature();
            return arrby;
        }
        catch (Exception exception) {
            throw new SignatureException(exception.toString());
        }
    }

    protected void engineUpdate(byte by) {
        this.signer.update(by);
    }

    protected void engineUpdate(byte[] arrby, int n, int n2) {
        this.signer.update(arrby, n, n2);
    }

    protected boolean engineVerify(byte[] arrby) {
        return this.signer.verifySignature(arrby);
    }

    public static class RIPEMD128WithRSAEncryption
    extends X931SignatureSpi {
        public RIPEMD128WithRSAEncryption() {
            super(new RIPEMD128Digest(), new RSABlindedEngine());
        }
    }

    public static class RIPEMD160WithRSAEncryption
    extends X931SignatureSpi {
        public RIPEMD160WithRSAEncryption() {
            super(new RIPEMD160Digest(), new RSABlindedEngine());
        }
    }

    public static class SHA1WithRSAEncryption
    extends X931SignatureSpi {
        public SHA1WithRSAEncryption() {
            super(new SHA1Digest(), new RSABlindedEngine());
        }
    }

    public static class SHA224WithRSAEncryption
    extends X931SignatureSpi {
        public SHA224WithRSAEncryption() {
            super(new SHA224Digest(), new RSABlindedEngine());
        }
    }

    public static class SHA256WithRSAEncryption
    extends X931SignatureSpi {
        public SHA256WithRSAEncryption() {
            super(new SHA256Digest(), new RSABlindedEngine());
        }
    }

    public static class SHA384WithRSAEncryption
    extends X931SignatureSpi {
        public SHA384WithRSAEncryption() {
            super(new SHA384Digest(), new RSABlindedEngine());
        }
    }

    public static class SHA512WithRSAEncryption
    extends X931SignatureSpi {
        public SHA512WithRSAEncryption() {
            super(new SHA512Digest(), new RSABlindedEngine());
        }
    }

    public static class WhirlpoolWithRSAEncryption
    extends X931SignatureSpi {
        public WhirlpoolWithRSAEncryption() {
            super(new WhirlpoolDigest(), new RSABlindedEngine());
        }
    }

}

