/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.IllegalArgumentException
 *  java.lang.Object
 *  java.lang.String
 */
package org.bouncycastle.asn1.cmp;

import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1Integer;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1OctetString;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.DERSequence;
import org.bouncycastle.asn1.cmp.CertifiedKeyPair;
import org.bouncycastle.asn1.cmp.PKIStatusInfo;

public class CertResponse
extends ASN1Object {
    private ASN1Integer certReqId;
    private CertifiedKeyPair certifiedKeyPair;
    private ASN1OctetString rspInfo;
    private PKIStatusInfo status;

    public CertResponse(ASN1Integer aSN1Integer, PKIStatusInfo pKIStatusInfo) {
        this(aSN1Integer, pKIStatusInfo, null, null);
    }

    public CertResponse(ASN1Integer aSN1Integer, PKIStatusInfo pKIStatusInfo, CertifiedKeyPair certifiedKeyPair, ASN1OctetString aSN1OctetString) {
        if (aSN1Integer == null) {
            throw new IllegalArgumentException("'certReqId' cannot be null");
        }
        if (pKIStatusInfo == null) {
            throw new IllegalArgumentException("'status' cannot be null");
        }
        this.certReqId = aSN1Integer;
        this.status = pKIStatusInfo;
        this.certifiedKeyPair = certifiedKeyPair;
        this.rspInfo = aSN1OctetString;
    }

    private CertResponse(ASN1Sequence aSN1Sequence) {
        block3 : {
            ASN1Encodable aSN1Encodable;
            block4 : {
                block2 : {
                    this.certReqId = ASN1Integer.getInstance(aSN1Sequence.getObjectAt(0));
                    this.status = PKIStatusInfo.getInstance(aSN1Sequence.getObjectAt(1));
                    if (aSN1Sequence.size() < 3) break block2;
                    if (aSN1Sequence.size() != 3) break block3;
                    aSN1Encodable = aSN1Sequence.getObjectAt(2);
                    if (!(aSN1Encodable instanceof ASN1OctetString)) break block4;
                    this.rspInfo = ASN1OctetString.getInstance(aSN1Encodable);
                }
                return;
            }
            this.certifiedKeyPair = CertifiedKeyPair.getInstance(aSN1Encodable);
            return;
        }
        this.certifiedKeyPair = CertifiedKeyPair.getInstance(aSN1Sequence.getObjectAt(2));
        this.rspInfo = ASN1OctetString.getInstance(aSN1Sequence.getObjectAt(3));
    }

    public static CertResponse getInstance(Object object) {
        if (object instanceof CertResponse) {
            return (CertResponse)object;
        }
        if (object != null) {
            return new CertResponse(ASN1Sequence.getInstance(object));
        }
        return null;
    }

    public ASN1Integer getCertReqId() {
        return this.certReqId;
    }

    public CertifiedKeyPair getCertifiedKeyPair() {
        return this.certifiedKeyPair;
    }

    public PKIStatusInfo getStatus() {
        return this.status;
    }

    @Override
    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        aSN1EncodableVector.add(this.certReqId);
        aSN1EncodableVector.add(this.status);
        if (this.certifiedKeyPair != null) {
            aSN1EncodableVector.add(this.certifiedKeyPair);
        }
        if (this.rspInfo != null) {
            aSN1EncodableVector.add(this.rspInfo);
        }
        return new DERSequence(aSN1EncodableVector);
    }
}

