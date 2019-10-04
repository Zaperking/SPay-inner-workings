/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Object
 */
package org.bouncycastle.asn1.ocsp;

import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.ASN1TaggedObject;
import org.bouncycastle.asn1.DERSequence;
import org.bouncycastle.asn1.DERTaggedObject;
import org.bouncycastle.asn1.ocsp.Signature;
import org.bouncycastle.asn1.ocsp.TBSRequest;

public class OCSPRequest
extends ASN1Object {
    Signature optionalSignature;
    TBSRequest tbsRequest;

    private OCSPRequest(ASN1Sequence aSN1Sequence) {
        this.tbsRequest = TBSRequest.getInstance(aSN1Sequence.getObjectAt(0));
        if (aSN1Sequence.size() == 2) {
            this.optionalSignature = Signature.getInstance((ASN1TaggedObject)aSN1Sequence.getObjectAt(1), true);
        }
    }

    public OCSPRequest(TBSRequest tBSRequest, Signature signature) {
        this.tbsRequest = tBSRequest;
        this.optionalSignature = signature;
    }

    public static OCSPRequest getInstance(Object object) {
        if (object instanceof OCSPRequest) {
            return (OCSPRequest)object;
        }
        if (object != null) {
            return new OCSPRequest(ASN1Sequence.getInstance(object));
        }
        return null;
    }

    public static OCSPRequest getInstance(ASN1TaggedObject aSN1TaggedObject, boolean bl) {
        return OCSPRequest.getInstance(ASN1Sequence.getInstance(aSN1TaggedObject, bl));
    }

    public Signature getOptionalSignature() {
        return this.optionalSignature;
    }

    public TBSRequest getTbsRequest() {
        return this.tbsRequest;
    }

    @Override
    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        aSN1EncodableVector.add(this.tbsRequest);
        if (this.optionalSignature != null) {
            aSN1EncodableVector.add(new DERTaggedObject(true, 0, this.optionalSignature));
        }
        return new DERSequence(aSN1EncodableVector);
    }
}

