/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Object
 */
package org.bouncycastle.asn1.cms;

import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.ASN1TaggedObject;
import org.bouncycastle.asn1.DERSequence;

public class OtherRevocationInfoFormat
extends ASN1Object {
    private ASN1Encodable otherRevInfo;
    private ASN1ObjectIdentifier otherRevInfoFormat;

    public OtherRevocationInfoFormat(ASN1ObjectIdentifier aSN1ObjectIdentifier, ASN1Encodable aSN1Encodable) {
        this.otherRevInfoFormat = aSN1ObjectIdentifier;
        this.otherRevInfo = aSN1Encodable;
    }

    private OtherRevocationInfoFormat(ASN1Sequence aSN1Sequence) {
        this.otherRevInfoFormat = ASN1ObjectIdentifier.getInstance(aSN1Sequence.getObjectAt(0));
        this.otherRevInfo = aSN1Sequence.getObjectAt(1);
    }

    public static OtherRevocationInfoFormat getInstance(Object object) {
        if (object instanceof OtherRevocationInfoFormat) {
            return (OtherRevocationInfoFormat)object;
        }
        if (object != null) {
            return new OtherRevocationInfoFormat(ASN1Sequence.getInstance(object));
        }
        return null;
    }

    public static OtherRevocationInfoFormat getInstance(ASN1TaggedObject aSN1TaggedObject, boolean bl) {
        return OtherRevocationInfoFormat.getInstance(ASN1Sequence.getInstance(aSN1TaggedObject, bl));
    }

    public ASN1Encodable getInfo() {
        return this.otherRevInfo;
    }

    public ASN1ObjectIdentifier getInfoFormat() {
        return this.otherRevInfoFormat;
    }

    @Override
    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        aSN1EncodableVector.add(this.otherRevInfoFormat);
        aSN1EncodableVector.add(this.otherRevInfo);
        return new DERSequence(aSN1EncodableVector);
    }
}

