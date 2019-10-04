/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Class
 *  java.lang.IllegalArgumentException
 *  java.lang.Object
 *  java.lang.String
 *  java.math.BigInteger
 *  java.util.Enumeration
 */
package org.bouncycastle.asn1.cryptopro;

import java.math.BigInteger;
import java.util.Enumeration;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1Integer;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.ASN1TaggedObject;
import org.bouncycastle.asn1.DERSequence;

public class GOST3410ParamSetParameters
extends ASN1Object {
    ASN1Integer a;
    int keySize;
    ASN1Integer p;
    ASN1Integer q;

    public GOST3410ParamSetParameters(int n2, BigInteger bigInteger, BigInteger bigInteger2, BigInteger bigInteger3) {
        this.keySize = n2;
        this.p = new ASN1Integer(bigInteger);
        this.q = new ASN1Integer(bigInteger2);
        this.a = new ASN1Integer(bigInteger3);
    }

    public GOST3410ParamSetParameters(ASN1Sequence aSN1Sequence) {
        Enumeration enumeration = aSN1Sequence.getObjects();
        this.keySize = ((ASN1Integer)enumeration.nextElement()).getValue().intValue();
        this.p = (ASN1Integer)enumeration.nextElement();
        this.q = (ASN1Integer)enumeration.nextElement();
        this.a = (ASN1Integer)enumeration.nextElement();
    }

    public static GOST3410ParamSetParameters getInstance(Object object) {
        if (object == null || object instanceof GOST3410ParamSetParameters) {
            return (GOST3410ParamSetParameters)object;
        }
        if (object instanceof ASN1Sequence) {
            return new GOST3410ParamSetParameters((ASN1Sequence)object);
        }
        throw new IllegalArgumentException("Invalid GOST3410Parameter: " + object.getClass().getName());
    }

    public static GOST3410ParamSetParameters getInstance(ASN1TaggedObject aSN1TaggedObject, boolean bl) {
        return GOST3410ParamSetParameters.getInstance(ASN1Sequence.getInstance(aSN1TaggedObject, bl));
    }

    public BigInteger getA() {
        return this.a.getPositiveValue();
    }

    public int getKeySize() {
        return this.keySize;
    }

    public int getLKeySize() {
        return this.keySize;
    }

    public BigInteger getP() {
        return this.p.getPositiveValue();
    }

    public BigInteger getQ() {
        return this.q.getPositiveValue();
    }

    @Override
    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        aSN1EncodableVector.add(new ASN1Integer(this.keySize));
        aSN1EncodableVector.add(this.p);
        aSN1EncodableVector.add(this.q);
        aSN1EncodableVector.add(this.a);
        return new DERSequence(aSN1EncodableVector);
    }
}

