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
import org.bouncycastle.asn1.ASN1OctetString;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.ASN1TaggedObject;
import org.bouncycastle.asn1.DERSequence;
import org.bouncycastle.asn1.cms.KeyAgreeRecipientIdentifier;

public class RecipientEncryptedKey
extends ASN1Object {
    private ASN1OctetString encryptedKey;
    private KeyAgreeRecipientIdentifier identifier;

    private RecipientEncryptedKey(ASN1Sequence aSN1Sequence) {
        this.identifier = KeyAgreeRecipientIdentifier.getInstance(aSN1Sequence.getObjectAt(0));
        this.encryptedKey = (ASN1OctetString)aSN1Sequence.getObjectAt(1);
    }

    public RecipientEncryptedKey(KeyAgreeRecipientIdentifier keyAgreeRecipientIdentifier, ASN1OctetString aSN1OctetString) {
        this.identifier = keyAgreeRecipientIdentifier;
        this.encryptedKey = aSN1OctetString;
    }

    public static RecipientEncryptedKey getInstance(Object object) {
        if (object instanceof RecipientEncryptedKey) {
            return (RecipientEncryptedKey)object;
        }
        if (object != null) {
            return new RecipientEncryptedKey(ASN1Sequence.getInstance(object));
        }
        return null;
    }

    public static RecipientEncryptedKey getInstance(ASN1TaggedObject aSN1TaggedObject, boolean bl) {
        return RecipientEncryptedKey.getInstance(ASN1Sequence.getInstance(aSN1TaggedObject, bl));
    }

    public ASN1OctetString getEncryptedKey() {
        return this.encryptedKey;
    }

    public KeyAgreeRecipientIdentifier getIdentifier() {
        return this.identifier;
    }

    @Override
    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        aSN1EncodableVector.add(this.identifier);
        aSN1EncodableVector.add(this.encryptedKey);
        return new DERSequence(aSN1EncodableVector);
    }
}

