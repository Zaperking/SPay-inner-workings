package org.bouncycastle.asn1.cms;

import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1Integer;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1OctetString;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.DEROctetString;
import org.bouncycastle.asn1.DERSequence;
import org.bouncycastle.util.Arrays;

public class GCMParameters extends ASN1Object {
    private int icvLen;
    private byte[] nonce;

    private GCMParameters(ASN1Sequence aSN1Sequence) {
        this.nonce = ASN1OctetString.getInstance(aSN1Sequence.getObjectAt(0)).getOctets();
        if (aSN1Sequence.size() == 2) {
            this.icvLen = ASN1Integer.getInstance(aSN1Sequence.getObjectAt(1)).getValue().intValue();
        } else {
            this.icvLen = 12;
        }
    }

    public GCMParameters(byte[] bArr, int i) {
        this.nonce = Arrays.clone(bArr);
        this.icvLen = i;
    }

    public static GCMParameters getInstance(Object obj) {
        return obj instanceof GCMParameters ? (GCMParameters) obj : obj != null ? new GCMParameters(ASN1Sequence.getInstance(obj)) : null;
    }

    public int getIcvLen() {
        return this.icvLen;
    }

    public byte[] getNonce() {
        return Arrays.clone(this.nonce);
    }

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        aSN1EncodableVector.add(new DEROctetString(this.nonce));
        if (this.icvLen != 12) {
            aSN1EncodableVector.add(new ASN1Integer((long) this.icvLen));
        }
        return new DERSequence(aSN1EncodableVector);
    }
}