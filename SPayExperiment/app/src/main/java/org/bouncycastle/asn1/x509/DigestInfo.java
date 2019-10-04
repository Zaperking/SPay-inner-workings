/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Object
 *  java.util.Enumeration
 */
package org.bouncycastle.asn1.x509;

import java.util.Enumeration;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1OctetString;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.ASN1TaggedObject;
import org.bouncycastle.asn1.DEROctetString;
import org.bouncycastle.asn1.DERSequence;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;

public class DigestInfo
extends ASN1Object {
    private AlgorithmIdentifier algId;
    private byte[] digest;

    public DigestInfo(ASN1Sequence aSN1Sequence) {
        Enumeration enumeration = aSN1Sequence.getObjects();
        this.algId = AlgorithmIdentifier.getInstance(enumeration.nextElement());
        this.digest = ASN1OctetString.getInstance(enumeration.nextElement()).getOctets();
    }

    public DigestInfo(AlgorithmIdentifier algorithmIdentifier, byte[] arrby) {
        this.digest = arrby;
        this.algId = algorithmIdentifier;
    }

    public static DigestInfo getInstance(Object object) {
        if (object instanceof DigestInfo) {
            return (DigestInfo)object;
        }
        if (object != null) {
            return new DigestInfo(ASN1Sequence.getInstance(object));
        }
        return null;
    }

    public static DigestInfo getInstance(ASN1TaggedObject aSN1TaggedObject, boolean bl) {
        return DigestInfo.getInstance(ASN1Sequence.getInstance(aSN1TaggedObject, bl));
    }

    public AlgorithmIdentifier getAlgorithmId() {
        return this.algId;
    }

    public byte[] getDigest() {
        return this.digest;
    }

    @Override
    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        aSN1EncodableVector.add(this.algId);
        aSN1EncodableVector.add(new DEROctetString(this.digest));
        return new DERSequence(aSN1EncodableVector);
    }
}

