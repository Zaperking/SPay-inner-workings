/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.IllegalArgumentException
 *  java.lang.String
 */
package org.bouncycastle.math.ec.custom.sec;

import org.bouncycastle.math.ec.ECCurve;
import org.bouncycastle.math.ec.ECFieldElement;
import org.bouncycastle.math.ec.ECPoint;
import org.bouncycastle.math.ec.custom.sec.SecP224R1Field;
import org.bouncycastle.math.ec.custom.sec.SecP224R1FieldElement;
import org.bouncycastle.math.raw.Nat;
import org.bouncycastle.math.raw.Nat224;

public class SecP224R1Point
extends ECPoint.AbstractFp {
    public SecP224R1Point(ECCurve eCCurve, ECFieldElement eCFieldElement, ECFieldElement eCFieldElement2) {
        this(eCCurve, eCFieldElement, eCFieldElement2, false);
    }

    /*
     * Enabled aggressive block sorting
     */
    public SecP224R1Point(ECCurve eCCurve, ECFieldElement eCFieldElement, ECFieldElement eCFieldElement2, boolean bl) {
        boolean bl2 = true;
        super(eCCurve, eCFieldElement, eCFieldElement2);
        boolean bl3 = eCFieldElement == null ? bl2 : false;
        if (eCFieldElement2 != null) {
            bl2 = false;
        }
        if (bl3 != bl2) {
            throw new IllegalArgumentException("Exactly one of the field elements is null");
        }
        this.withCompression = bl;
    }

    SecP224R1Point(ECCurve eCCurve, ECFieldElement eCFieldElement, ECFieldElement eCFieldElement2, ECFieldElement[] arreCFieldElement, boolean bl) {
        super(eCCurve, eCFieldElement, eCFieldElement2, arreCFieldElement);
        this.withCompression = bl;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public ECPoint add(ECPoint eCPoint) {
        int[] arrn;
        int[] arrn2;
        boolean bl;
        int[] arrn3;
        int[] arrn4;
        if (this.isInfinity()) {
            return eCPoint;
        }
        if (eCPoint.isInfinity()) {
            return this;
        }
        if (this == eCPoint) {
            return this.twice();
        }
        ECCurve eCCurve = this.getCurve();
        SecP224R1FieldElement secP224R1FieldElement = (SecP224R1FieldElement)this.x;
        SecP224R1FieldElement secP224R1FieldElement2 = (SecP224R1FieldElement)this.y;
        SecP224R1FieldElement secP224R1FieldElement3 = (SecP224R1FieldElement)eCPoint.getXCoord();
        SecP224R1FieldElement secP224R1FieldElement4 = (SecP224R1FieldElement)eCPoint.getYCoord();
        SecP224R1FieldElement secP224R1FieldElement5 = (SecP224R1FieldElement)this.zs[0];
        SecP224R1FieldElement secP224R1FieldElement6 = (SecP224R1FieldElement)eCPoint.getZCoord(0);
        int[] arrn5 = Nat224.createExt();
        int[] arrn6 = Nat224.create();
        int[] arrn7 = Nat224.create();
        int[] arrn8 = Nat224.create();
        boolean bl2 = secP224R1FieldElement5.isOne();
        if (bl2) {
            int[] arrn9 = secP224R1FieldElement3.x;
            arrn2 = secP224R1FieldElement4.x;
            arrn = arrn9;
        } else {
            SecP224R1Field.square(secP224R1FieldElement5.x, arrn7);
            SecP224R1Field.multiply(arrn7, secP224R1FieldElement3.x, arrn6);
            SecP224R1Field.multiply(arrn7, secP224R1FieldElement5.x, arrn7);
            SecP224R1Field.multiply(arrn7, secP224R1FieldElement4.x, arrn7);
            arrn2 = arrn7;
            arrn = arrn6;
        }
        if (bl = secP224R1FieldElement6.isOne()) {
            int[] arrn10 = secP224R1FieldElement.x;
            arrn4 = secP224R1FieldElement2.x;
            arrn3 = arrn10;
        } else {
            SecP224R1Field.square(secP224R1FieldElement6.x, arrn8);
            SecP224R1Field.multiply(arrn8, secP224R1FieldElement.x, arrn5);
            SecP224R1Field.multiply(arrn8, secP224R1FieldElement6.x, arrn8);
            SecP224R1Field.multiply(arrn8, secP224R1FieldElement2.x, arrn8);
            arrn4 = arrn8;
            arrn3 = arrn5;
        }
        int[] arrn11 = Nat224.create();
        SecP224R1Field.subtract(arrn3, arrn, arrn11);
        SecP224R1Field.subtract(arrn4, arrn2, arrn6);
        if (Nat224.isZero(arrn11)) {
            if (Nat224.isZero(arrn6)) {
                return this.twice();
            }
            return eCCurve.getInfinity();
        }
        SecP224R1Field.square(arrn11, arrn7);
        int[] arrn12 = Nat224.create();
        SecP224R1Field.multiply(arrn7, arrn11, arrn12);
        SecP224R1Field.multiply(arrn7, arrn3, arrn7);
        SecP224R1Field.negate(arrn12, arrn12);
        Nat224.mul(arrn4, arrn12, arrn5);
        SecP224R1Field.reduce32(Nat224.addBothTo(arrn7, arrn7, arrn12), arrn12);
        SecP224R1FieldElement secP224R1FieldElement7 = new SecP224R1FieldElement(arrn8);
        SecP224R1Field.square(arrn6, secP224R1FieldElement7.x);
        SecP224R1Field.subtract(secP224R1FieldElement7.x, arrn12, secP224R1FieldElement7.x);
        SecP224R1FieldElement secP224R1FieldElement8 = new SecP224R1FieldElement(arrn12);
        SecP224R1Field.subtract(arrn7, secP224R1FieldElement7.x, secP224R1FieldElement8.x);
        SecP224R1Field.multiplyAddToExt(secP224R1FieldElement8.x, arrn6, arrn5);
        SecP224R1Field.reduce(arrn5, secP224R1FieldElement8.x);
        SecP224R1FieldElement secP224R1FieldElement9 = new SecP224R1FieldElement(arrn11);
        if (!bl2) {
            SecP224R1Field.multiply(secP224R1FieldElement9.x, secP224R1FieldElement5.x, secP224R1FieldElement9.x);
        }
        if (!bl) {
            SecP224R1Field.multiply(secP224R1FieldElement9.x, secP224R1FieldElement6.x, secP224R1FieldElement9.x);
        }
        return new SecP224R1Point(eCCurve, secP224R1FieldElement7, secP224R1FieldElement8, new ECFieldElement[]{secP224R1FieldElement9}, this.withCompression);
    }

    @Override
    protected ECPoint detach() {
        return new SecP224R1Point(null, this.getAffineXCoord(), this.getAffineYCoord());
    }

    @Override
    public ECPoint negate() {
        if (this.isInfinity()) {
            return this;
        }
        return new SecP224R1Point(this.curve, this.x, this.y.negate(), this.zs, this.withCompression);
    }

    @Override
    public ECPoint threeTimes() {
        if (this.isInfinity() || this.y.isZero()) {
            return this;
        }
        return this.twice().add(this);
    }

    @Override
    public ECPoint twice() {
        if (this.isInfinity()) {
            return this;
        }
        ECCurve eCCurve = this.getCurve();
        SecP224R1FieldElement secP224R1FieldElement = (SecP224R1FieldElement)this.y;
        if (secP224R1FieldElement.isZero()) {
            return eCCurve.getInfinity();
        }
        SecP224R1FieldElement secP224R1FieldElement2 = (SecP224R1FieldElement)this.x;
        SecP224R1FieldElement secP224R1FieldElement3 = (SecP224R1FieldElement)this.zs[0];
        int[] arrn = Nat224.create();
        int[] arrn2 = Nat224.create();
        int[] arrn3 = Nat224.create();
        SecP224R1Field.square(secP224R1FieldElement.x, arrn3);
        int[] arrn4 = Nat224.create();
        SecP224R1Field.square(arrn3, arrn4);
        boolean bl = secP224R1FieldElement3.isOne();
        int[] arrn5 = secP224R1FieldElement3.x;
        if (!bl) {
            SecP224R1Field.square(secP224R1FieldElement3.x, arrn2);
            arrn5 = arrn2;
        }
        SecP224R1Field.subtract(secP224R1FieldElement2.x, arrn5, arrn);
        SecP224R1Field.add(secP224R1FieldElement2.x, arrn5, arrn2);
        SecP224R1Field.multiply(arrn2, arrn, arrn2);
        SecP224R1Field.reduce32(Nat224.addBothTo(arrn2, arrn2, arrn2), arrn2);
        SecP224R1Field.multiply(arrn3, secP224R1FieldElement2.x, arrn3);
        SecP224R1Field.reduce32(Nat.shiftUpBits(7, arrn3, 2, 0), arrn3);
        SecP224R1Field.reduce32(Nat.shiftUpBits(7, arrn4, 3, 0, arrn), arrn);
        SecP224R1FieldElement secP224R1FieldElement4 = new SecP224R1FieldElement(arrn4);
        SecP224R1Field.square(arrn2, secP224R1FieldElement4.x);
        SecP224R1Field.subtract(secP224R1FieldElement4.x, arrn3, secP224R1FieldElement4.x);
        SecP224R1Field.subtract(secP224R1FieldElement4.x, arrn3, secP224R1FieldElement4.x);
        SecP224R1FieldElement secP224R1FieldElement5 = new SecP224R1FieldElement(arrn3);
        SecP224R1Field.subtract(arrn3, secP224R1FieldElement4.x, secP224R1FieldElement5.x);
        SecP224R1Field.multiply(secP224R1FieldElement5.x, arrn2, secP224R1FieldElement5.x);
        SecP224R1Field.subtract(secP224R1FieldElement5.x, arrn, secP224R1FieldElement5.x);
        SecP224R1FieldElement secP224R1FieldElement6 = new SecP224R1FieldElement(arrn2);
        SecP224R1Field.twice(secP224R1FieldElement.x, secP224R1FieldElement6.x);
        if (!bl) {
            SecP224R1Field.multiply(secP224R1FieldElement6.x, secP224R1FieldElement3.x, secP224R1FieldElement6.x);
        }
        return new SecP224R1Point(eCCurve, secP224R1FieldElement4, secP224R1FieldElement5, new ECFieldElement[]{secP224R1FieldElement6}, this.withCompression);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public ECPoint twicePlus(ECPoint eCPoint) {
        if (this == eCPoint) {
            return this.threeTimes();
        }
        if (this.isInfinity()) return eCPoint;
        if (eCPoint.isInfinity()) {
            return this.twice();
        }
        if (this.y.isZero()) return eCPoint;
        return this.twice().add(eCPoint);
    }
}

