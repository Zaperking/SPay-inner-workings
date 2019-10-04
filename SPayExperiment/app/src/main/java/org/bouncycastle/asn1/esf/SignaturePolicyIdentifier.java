/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Object
 */
package org.bouncycastle.asn1.esf;

import org.bouncycastle.asn1.ASN1Null;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.DERNull;
import org.bouncycastle.asn1.esf.SignaturePolicyId;

public class SignaturePolicyIdentifier
extends ASN1Object {
    private boolean isSignaturePolicyImplied;
    private SignaturePolicyId signaturePolicyId;

    public SignaturePolicyIdentifier() {
        this.isSignaturePolicyImplied = true;
    }

    public SignaturePolicyIdentifier(SignaturePolicyId signaturePolicyId) {
        this.signaturePolicyId = signaturePolicyId;
        this.isSignaturePolicyImplied = false;
    }

    public static SignaturePolicyIdentifier getInstance(Object object) {
        if (object instanceof SignaturePolicyIdentifier) {
            return (SignaturePolicyIdentifier)object;
        }
        if (object instanceof ASN1Null || SignaturePolicyIdentifier.hasEncodedTagValue(object, 5)) {
            return new SignaturePolicyIdentifier();
        }
        if (object != null) {
            return new SignaturePolicyIdentifier(SignaturePolicyId.getInstance(object));
        }
        return null;
    }

    public SignaturePolicyId getSignaturePolicyId() {
        return this.signaturePolicyId;
    }

    public boolean isSignaturePolicyImplied() {
        return this.isSignaturePolicyImplied;
    }

    @Override
    public ASN1Primitive toASN1Primitive() {
        if (this.isSignaturePolicyImplied) {
            return DERNull.INSTANCE;
        }
        return this.signaturePolicyId.toASN1Primitive();
    }
}

