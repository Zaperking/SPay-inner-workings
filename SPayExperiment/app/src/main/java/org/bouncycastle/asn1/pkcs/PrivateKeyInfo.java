/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.io.IOException
 *  java.lang.IllegalArgumentException
 *  java.lang.IllegalStateException
 *  java.lang.Object
 *  java.lang.String
 *  java.math.BigInteger
 *  java.util.Enumeration
 */
package org.bouncycastle.asn1.pkcs;

import java.io.IOException;
import java.math.BigInteger;
import java.util.Enumeration;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1Integer;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1OctetString;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.ASN1Set;
import org.bouncycastle.asn1.ASN1TaggedObject;
import org.bouncycastle.asn1.DEROctetString;
import org.bouncycastle.asn1.DERSequence;
import org.bouncycastle.asn1.DERTaggedObject;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;

public class PrivateKeyInfo
extends ASN1Object {
    private AlgorithmIdentifier algId;
    private ASN1Set attributes;
    private ASN1OctetString privKey;

    public PrivateKeyInfo(ASN1Sequence aSN1Sequence) {
        Enumeration enumeration = aSN1Sequence.getObjects();
        if (((ASN1Integer)enumeration.nextElement()).getValue().intValue() != 0) {
            throw new IllegalArgumentException("wrong version for private key info");
        }
        this.algId = AlgorithmIdentifier.getInstance(enumeration.nextElement());
        this.privKey = ASN1OctetString.getInstance(enumeration.nextElement());
        if (enumeration.hasMoreElements()) {
            this.attributes = ASN1Set.getInstance((ASN1TaggedObject)enumeration.nextElement(), false);
        }
    }

    public PrivateKeyInfo(AlgorithmIdentifier algorithmIdentifier, ASN1Encodable aSN1Encodable) {
        this(algorithmIdentifier, aSN1Encodable, null);
    }

    public PrivateKeyInfo(AlgorithmIdentifier algorithmIdentifier, ASN1Encodable aSN1Encodable, ASN1Set aSN1Set) {
        this.privKey = new DEROctetString(aSN1Encodable.toASN1Primitive().getEncoded("DER"));
        this.algId = algorithmIdentifier;
        this.attributes = aSN1Set;
    }

    public static PrivateKeyInfo getInstance(Object object) {
        if (object instanceof PrivateKeyInfo) {
            return (PrivateKeyInfo)object;
        }
        if (object != null) {
            return new PrivateKeyInfo(ASN1Sequence.getInstance(object));
        }
        return null;
    }

    public static PrivateKeyInfo getInstance(ASN1TaggedObject aSN1TaggedObject, boolean bl) {
        return PrivateKeyInfo.getInstance(ASN1Sequence.getInstance(aSN1TaggedObject, bl));
    }

    public AlgorithmIdentifier getAlgorithmId() {
        return this.algId;
    }

    public ASN1Set getAttributes() {
        return this.attributes;
    }

    public ASN1Primitive getPrivateKey() {
        try {
            ASN1Primitive aSN1Primitive = this.parsePrivateKey().toASN1Primitive();
            return aSN1Primitive;
        }
        catch (IOException iOException) {
            throw new IllegalStateException("unable to parse private key");
        }
    }

    public AlgorithmIdentifier getPrivateKeyAlgorithm() {
        return this.algId;
    }

    public ASN1Encodable parsePrivateKey() {
        return ASN1Primitive.fromByteArray(this.privKey.getOctets());
    }

    @Override
    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        aSN1EncodableVector.add(new ASN1Integer(0L));
        aSN1EncodableVector.add(this.algId);
        aSN1EncodableVector.add(this.privKey);
        if (this.attributes != null) {
            aSN1EncodableVector.add(new DERTaggedObject(false, 0, this.attributes));
        }
        return new DERSequence(aSN1EncodableVector);
    }
}

