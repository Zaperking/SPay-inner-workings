/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Object
 */
package org.bouncycastle.crypto.params;

import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.params.ECPublicKeyParameters;

public class MQVPublicParameters
implements CipherParameters {
    private ECPublicKeyParameters ephemeralPublicKey;
    private ECPublicKeyParameters staticPublicKey;

    public MQVPublicParameters(ECPublicKeyParameters eCPublicKeyParameters, ECPublicKeyParameters eCPublicKeyParameters2) {
        this.staticPublicKey = eCPublicKeyParameters;
        this.ephemeralPublicKey = eCPublicKeyParameters2;
    }

    public ECPublicKeyParameters getEphemeralPublicKey() {
        return this.ephemeralPublicKey;
    }

    public ECPublicKeyParameters getStaticPublicKey() {
        return this.staticPublicKey;
    }
}

