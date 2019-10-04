/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Object
 *  java.math.BigInteger
 */
package org.bouncycastle.pqc.asn1;

import java.math.BigInteger;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1Integer;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.ASN1OctetString;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.DEROctetString;
import org.bouncycastle.asn1.DERSequence;
import org.bouncycastle.pqc.math.linearalgebra.GF2Matrix;
import org.bouncycastle.pqc.math.linearalgebra.GF2mField;
import org.bouncycastle.pqc.math.linearalgebra.Permutation;
import org.bouncycastle.pqc.math.linearalgebra.PolynomialGF2mSmallM;

public class McEliecePrivateKey
extends ASN1Object {
    private byte[] encField;
    private byte[] encGp;
    private byte[] encH;
    private byte[] encP1;
    private byte[] encP2;
    private byte[] encSInv;
    private byte[][] encqInv;
    private int k;
    private int n;
    private ASN1ObjectIdentifier oid;

    public McEliecePrivateKey(ASN1ObjectIdentifier aSN1ObjectIdentifier, int n, int n2, GF2mField gF2mField, PolynomialGF2mSmallM polynomialGF2mSmallM, GF2Matrix gF2Matrix, Permutation permutation, Permutation permutation2, GF2Matrix gF2Matrix2, PolynomialGF2mSmallM[] arrpolynomialGF2mSmallM) {
        this.oid = aSN1ObjectIdentifier;
        this.n = n;
        this.k = n2;
        this.encField = gF2mField.getEncoded();
        this.encGp = polynomialGF2mSmallM.getEncoded();
        this.encSInv = gF2Matrix.getEncoded();
        this.encP1 = permutation.getEncoded();
        this.encP2 = permutation2.getEncoded();
        this.encH = gF2Matrix2.getEncoded();
        this.encqInv = new byte[arrpolynomialGF2mSmallM.length][];
        for (int i = 0; i != arrpolynomialGF2mSmallM.length; ++i) {
            this.encqInv[i] = arrpolynomialGF2mSmallM[i].getEncoded();
        }
    }

    private McEliecePrivateKey(ASN1Sequence aSN1Sequence) {
        this.oid = (ASN1ObjectIdentifier)aSN1Sequence.getObjectAt(0);
        this.n = ((ASN1Integer)aSN1Sequence.getObjectAt(1)).getValue().intValue();
        this.k = ((ASN1Integer)aSN1Sequence.getObjectAt(2)).getValue().intValue();
        this.encField = ((ASN1OctetString)aSN1Sequence.getObjectAt(3)).getOctets();
        this.encGp = ((ASN1OctetString)aSN1Sequence.getObjectAt(4)).getOctets();
        this.encSInv = ((ASN1OctetString)aSN1Sequence.getObjectAt(5)).getOctets();
        this.encP1 = ((ASN1OctetString)aSN1Sequence.getObjectAt(6)).getOctets();
        this.encP2 = ((ASN1OctetString)aSN1Sequence.getObjectAt(7)).getOctets();
        this.encH = ((ASN1OctetString)aSN1Sequence.getObjectAt(8)).getOctets();
        ASN1Sequence aSN1Sequence2 = (ASN1Sequence)aSN1Sequence.getObjectAt(9);
        this.encqInv = new byte[aSN1Sequence2.size()][];
        for (int i = 0; i < aSN1Sequence2.size(); ++i) {
            this.encqInv[i] = ((ASN1OctetString)aSN1Sequence2.getObjectAt(i)).getOctets();
        }
    }

    public static McEliecePrivateKey getInstance(Object object) {
        if (object instanceof McEliecePrivateKey) {
            return (McEliecePrivateKey)object;
        }
        if (object != null) {
            return new McEliecePrivateKey(ASN1Sequence.getInstance(object));
        }
        return null;
    }

    public GF2mField getField() {
        return new GF2mField(this.encField);
    }

    public PolynomialGF2mSmallM getGoppaPoly() {
        return new PolynomialGF2mSmallM(this.getField(), this.encGp);
    }

    public GF2Matrix getH() {
        return new GF2Matrix(this.encH);
    }

    public int getK() {
        return this.k;
    }

    public int getN() {
        return this.n;
    }

    public ASN1ObjectIdentifier getOID() {
        return this.oid;
    }

    public Permutation getP1() {
        return new Permutation(this.encP1);
    }

    public Permutation getP2() {
        return new Permutation(this.encP2);
    }

    public PolynomialGF2mSmallM[] getQInv() {
        PolynomialGF2mSmallM[] arrpolynomialGF2mSmallM = new PolynomialGF2mSmallM[this.encqInv.length];
        GF2mField gF2mField = this.getField();
        for (int i = 0; i < this.encqInv.length; ++i) {
            arrpolynomialGF2mSmallM[i] = new PolynomialGF2mSmallM(gF2mField, this.encqInv[i]);
        }
        return arrpolynomialGF2mSmallM;
    }

    public GF2Matrix getSInv() {
        return new GF2Matrix(this.encSInv);
    }

    @Override
    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        aSN1EncodableVector.add(this.oid);
        aSN1EncodableVector.add(new ASN1Integer(this.n));
        aSN1EncodableVector.add(new ASN1Integer(this.k));
        aSN1EncodableVector.add(new DEROctetString(this.encField));
        aSN1EncodableVector.add(new DEROctetString(this.encGp));
        aSN1EncodableVector.add(new DEROctetString(this.encSInv));
        aSN1EncodableVector.add(new DEROctetString(this.encP1));
        aSN1EncodableVector.add(new DEROctetString(this.encP2));
        aSN1EncodableVector.add(new DEROctetString(this.encH));
        ASN1EncodableVector aSN1EncodableVector2 = new ASN1EncodableVector();
        for (int i = 0; i < this.encqInv.length; ++i) {
            aSN1EncodableVector2.add(new DEROctetString(this.encqInv[i]));
        }
        aSN1EncodableVector.add(new DERSequence(aSN1EncodableVector2));
        return new DERSequence(aSN1EncodableVector);
    }
}

