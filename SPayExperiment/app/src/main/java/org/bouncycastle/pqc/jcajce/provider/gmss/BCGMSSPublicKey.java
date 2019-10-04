/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Object
 *  java.lang.String
 *  java.security.PublicKey
 */
package org.bouncycastle.pqc.jcajce.provider.gmss;

import java.security.PublicKey;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.pqc.asn1.GMSSPublicKey;
import org.bouncycastle.pqc.asn1.PQCObjectIdentifiers;
import org.bouncycastle.pqc.asn1.ParSet;
import org.bouncycastle.pqc.crypto.gmss.GMSSParameters;
import org.bouncycastle.pqc.crypto.gmss.GMSSPublicKeyParameters;
import org.bouncycastle.pqc.jcajce.provider.util.KeyUtil;
import org.bouncycastle.pqc.jcajce.spec.GMSSPublicKeySpec;
import org.bouncycastle.util.encoders.Hex;

public class BCGMSSPublicKey
implements PublicKey,
CipherParameters {
    private static final long serialVersionUID = 1L;
    private GMSSParameters gmssParameterSet;
    private GMSSParameters gmssParams;
    private byte[] publicKeyBytes;

    public BCGMSSPublicKey(GMSSPublicKeyParameters gMSSPublicKeyParameters) {
        this(gMSSPublicKeyParameters.getPublicKey(), gMSSPublicKeyParameters.getParameters());
    }

    protected BCGMSSPublicKey(GMSSPublicKeySpec gMSSPublicKeySpec) {
        this(gMSSPublicKeySpec.getPublicKey(), gMSSPublicKeySpec.getParameters());
    }

    public BCGMSSPublicKey(byte[] arrby, GMSSParameters gMSSParameters) {
        this.gmssParameterSet = gMSSParameters;
        this.publicKeyBytes = arrby;
    }

    public String getAlgorithm() {
        return "GMSS";
    }

    public byte[] getEncoded() {
        return KeyUtil.getEncodedSubjectPublicKeyInfo(new AlgorithmIdentifier(PQCObjectIdentifiers.gmss, new ParSet(this.gmssParameterSet.getNumOfLayers(), this.gmssParameterSet.getHeightOfTrees(), this.gmssParameterSet.getWinternitzParameter(), this.gmssParameterSet.getK()).toASN1Primitive()), new GMSSPublicKey(this.publicKeyBytes));
    }

    public String getFormat() {
        return "X.509";
    }

    public GMSSParameters getParameterSet() {
        return this.gmssParameterSet;
    }

    public byte[] getPublicKeyBytes() {
        return this.publicKeyBytes;
    }

    public String toString() {
        String string = "GMSS public key : " + new String(Hex.encode(this.publicKeyBytes)) + "\n" + "Height of Trees: \n";
        for (int i = 0; i < this.gmssParameterSet.getHeightOfTrees().length; ++i) {
            string = string + "Layer " + i + " : " + this.gmssParameterSet.getHeightOfTrees()[i] + " WinternitzParameter: " + this.gmssParameterSet.getWinternitzParameter()[i] + " K: " + this.gmssParameterSet.getK()[i] + "\n";
        }
        return string;
    }
}

