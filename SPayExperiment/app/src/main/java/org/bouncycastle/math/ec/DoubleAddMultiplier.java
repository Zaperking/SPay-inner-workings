/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.math.BigInteger
 */
package org.bouncycastle.math.ec;

import java.math.BigInteger;
import org.bouncycastle.math.ec.AbstractECMultiplier;
import org.bouncycastle.math.ec.ECCurve;
import org.bouncycastle.math.ec.ECPoint;

public class DoubleAddMultiplier
extends AbstractECMultiplier {
    /*
     * Enabled aggressive block sorting
     */
    @Override
    protected ECPoint multiplyPositive(ECPoint eCPoint, BigInteger bigInteger) {
        ECPoint[] arreCPoint = new ECPoint[]{eCPoint.getCurve().getInfinity(), eCPoint};
        int n = bigInteger.bitLength();
        int n2 = 0;
        while (n2 < n) {
            int n3 = bigInteger.testBit(n2) ? 1 : 0;
            int n4 = 1 - n3;
            arreCPoint[n4] = arreCPoint[n4].twicePlus(arreCPoint[n3]);
            ++n2;
        }
        return arreCPoint[0];
    }
}

