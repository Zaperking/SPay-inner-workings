/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.IllegalArgumentException
 *  java.lang.Object
 *  java.lang.String
 */
package org.bouncycastle.asn1.crmf;

import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.ASN1TaggedObject;
import org.bouncycastle.asn1.DERSequence;
import org.bouncycastle.asn1.DERTaggedObject;
import org.bouncycastle.asn1.crmf.PKMACValue;
import org.bouncycastle.asn1.x509.GeneralName;
import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;

public class POPOSigningKeyInput
extends ASN1Object {
    private SubjectPublicKeyInfo publicKey;
    private PKMACValue publicKeyMAC;
    private GeneralName sender;

    /*
     * Enabled aggressive block sorting
     */
    private POPOSigningKeyInput(ASN1Sequence aSN1Sequence) {
        ASN1Encodable aSN1Encodable = aSN1Sequence.getObjectAt(0);
        if (aSN1Encodable instanceof ASN1TaggedObject) {
            ASN1TaggedObject aSN1TaggedObject = (ASN1TaggedObject)aSN1Encodable;
            if (aSN1TaggedObject.getTagNo() != 0) {
                throw new IllegalArgumentException("Unknown authInfo tag: " + aSN1TaggedObject.getTagNo());
            }
            this.sender = GeneralName.getInstance(aSN1TaggedObject.getObject());
        } else {
            this.publicKeyMAC = PKMACValue.getInstance(aSN1Encodable);
        }
        this.publicKey = SubjectPublicKeyInfo.getInstance(aSN1Sequence.getObjectAt(1));
    }

    public POPOSigningKeyInput(PKMACValue pKMACValue, SubjectPublicKeyInfo subjectPublicKeyInfo) {
        this.publicKeyMAC = pKMACValue;
        this.publicKey = subjectPublicKeyInfo;
    }

    public POPOSigningKeyInput(GeneralName generalName, SubjectPublicKeyInfo subjectPublicKeyInfo) {
        this.sender = generalName;
        this.publicKey = subjectPublicKeyInfo;
    }

    public static POPOSigningKeyInput getInstance(Object object) {
        if (object instanceof POPOSigningKeyInput) {
            return (POPOSigningKeyInput)object;
        }
        if (object != null) {
            return new POPOSigningKeyInput(ASN1Sequence.getInstance(object));
        }
        return null;
    }

    public SubjectPublicKeyInfo getPublicKey() {
        return this.publicKey;
    }

    public PKMACValue getPublicKeyMAC() {
        return this.publicKeyMAC;
    }

    public GeneralName getSender() {
        return this.sender;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        if (this.sender != null) {
            aSN1EncodableVector.add(new DERTaggedObject(false, 0, this.sender));
        } else {
            aSN1EncodableVector.add(this.publicKeyMAC);
        }
        aSN1EncodableVector.add(this.publicKey);
        return new DERSequence(aSN1EncodableVector);
    }
}

