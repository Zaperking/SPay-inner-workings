/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Class
 *  java.lang.Object
 *  java.lang.String
 *  java.security.InvalidKeyException
 *  java.security.PrivateKey
 *  java.security.PublicKey
 */
package org.bouncycastle.pqc.jcajce.provider.mceliece;

import java.security.InvalidKeyException;
import java.security.PrivateKey;
import java.security.PublicKey;
import org.bouncycastle.crypto.params.AsymmetricKeyParameter;
import org.bouncycastle.pqc.crypto.mceliece.McElieceCCA2Parameters;
import org.bouncycastle.pqc.crypto.mceliece.McElieceCCA2PrivateKeyParameters;
import org.bouncycastle.pqc.crypto.mceliece.McElieceCCA2PublicKeyParameters;
import org.bouncycastle.pqc.jcajce.provider.mceliece.BCMcElieceCCA2PrivateKey;
import org.bouncycastle.pqc.jcajce.provider.mceliece.BCMcElieceCCA2PublicKey;
import org.bouncycastle.pqc.math.linearalgebra.GF2Matrix;
import org.bouncycastle.pqc.math.linearalgebra.GF2mField;
import org.bouncycastle.pqc.math.linearalgebra.Permutation;
import org.bouncycastle.pqc.math.linearalgebra.PolynomialGF2mSmallM;

public class McElieceCCA2KeysToParams {
    public static AsymmetricKeyParameter generatePrivateKeyParameter(PrivateKey privateKey) {
        if (privateKey instanceof BCMcElieceCCA2PrivateKey) {
            BCMcElieceCCA2PrivateKey bCMcElieceCCA2PrivateKey = (BCMcElieceCCA2PrivateKey)privateKey;
            return new McElieceCCA2PrivateKeyParameters(bCMcElieceCCA2PrivateKey.getOIDString(), bCMcElieceCCA2PrivateKey.getN(), bCMcElieceCCA2PrivateKey.getK(), bCMcElieceCCA2PrivateKey.getField(), bCMcElieceCCA2PrivateKey.getGoppaPoly(), bCMcElieceCCA2PrivateKey.getP(), bCMcElieceCCA2PrivateKey.getH(), bCMcElieceCCA2PrivateKey.getQInv(), bCMcElieceCCA2PrivateKey.getMcElieceCCA2Parameters());
        }
        throw new InvalidKeyException("can't identify McElieceCCA2 private key.");
    }

    public static AsymmetricKeyParameter generatePublicKeyParameter(PublicKey publicKey) {
        if (publicKey instanceof BCMcElieceCCA2PublicKey) {
            BCMcElieceCCA2PublicKey bCMcElieceCCA2PublicKey = (BCMcElieceCCA2PublicKey)publicKey;
            return new McElieceCCA2PublicKeyParameters(bCMcElieceCCA2PublicKey.getOIDString(), bCMcElieceCCA2PublicKey.getN(), bCMcElieceCCA2PublicKey.getT(), bCMcElieceCCA2PublicKey.getG(), bCMcElieceCCA2PublicKey.getMcElieceCCA2Parameters());
        }
        throw new InvalidKeyException("can't identify McElieceCCA2 public key: " + publicKey.getClass().getName());
    }
}

