/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.math.BigInteger
 *  java.util.Vector
 */
package org.bouncycastle.crypto.params;

import java.math.BigInteger;
import java.util.Vector;
import org.bouncycastle.crypto.params.NaccacheSternKeyParameters;

public class NaccacheSternPrivateKeyParameters
extends NaccacheSternKeyParameters {
    private BigInteger phi_n;
    private Vector smallPrimes;

    public NaccacheSternPrivateKeyParameters(BigInteger bigInteger, BigInteger bigInteger2, int n2, Vector vector, BigInteger bigInteger3) {
        super(true, bigInteger, bigInteger2, n2);
        this.smallPrimes = vector;
        this.phi_n = bigInteger3;
    }

    public BigInteger getPhi_n() {
        return this.phi_n;
    }

    public Vector getSmallPrimes() {
        return this.smallPrimes;
    }
}

