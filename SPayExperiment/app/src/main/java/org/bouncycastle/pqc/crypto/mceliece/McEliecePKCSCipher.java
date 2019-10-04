/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Exception
 *  java.lang.IllegalArgumentException
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.System
 *  java.security.SecureRandom
 */
package org.bouncycastle.pqc.crypto.mceliece;

import java.security.SecureRandom;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.params.ParametersWithRandom;
import org.bouncycastle.pqc.crypto.MessageEncryptor;
import org.bouncycastle.pqc.crypto.mceliece.McElieceKeyParameters;
import org.bouncycastle.pqc.crypto.mceliece.McEliecePrivateKeyParameters;
import org.bouncycastle.pqc.crypto.mceliece.McEliecePublicKeyParameters;
import org.bouncycastle.pqc.math.linearalgebra.GF2Matrix;
import org.bouncycastle.pqc.math.linearalgebra.GF2Vector;
import org.bouncycastle.pqc.math.linearalgebra.GF2mField;
import org.bouncycastle.pqc.math.linearalgebra.GoppaCode;
import org.bouncycastle.pqc.math.linearalgebra.Permutation;
import org.bouncycastle.pqc.math.linearalgebra.PolynomialGF2mSmallM;
import org.bouncycastle.pqc.math.linearalgebra.Vector;

public class McEliecePKCSCipher
implements MessageEncryptor {
    public static final String OID = "1.3.6.1.4.1.8301.3.1.3.4.1";
    public int cipherTextSize;
    private int k;
    McElieceKeyParameters key;
    public int maxPlainTextSize;
    private int n;
    private SecureRandom sr;
    private int t;

    private byte[] computeMessage(GF2Vector gF2Vector) {
        int n;
        byte[] arrby = gF2Vector.getEncoded();
        for (n = -1 + arrby.length; n >= 0 && arrby[n] == 0; --n) {
        }
        if (n < 0 || arrby[n] != 1) {
            throw new Exception("Bad Padding: invalid ciphertext");
        }
        byte[] arrby2 = new byte[n];
        System.arraycopy((Object)arrby, (int)0, (Object)arrby2, (int)0, (int)n);
        return arrby2;
    }

    /*
     * Enabled aggressive block sorting
     */
    private GF2Vector computeMessageRepresentative(byte[] arrby) {
        int n = this.maxPlainTextSize;
        int n2 = (7 & this.k) != 0 ? 1 : 0;
        byte[] arrby2 = new byte[n2 + n];
        System.arraycopy((Object)arrby, (int)0, (Object)arrby2, (int)0, (int)arrby.length);
        arrby2[arrby.length] = 1;
        return GF2Vector.OS2VP(this.k, arrby2);
    }

    public int getKeySize(McElieceKeyParameters mcElieceKeyParameters) {
        if (mcElieceKeyParameters instanceof McEliecePublicKeyParameters) {
            return ((McEliecePublicKeyParameters)mcElieceKeyParameters).getN();
        }
        if (mcElieceKeyParameters instanceof McEliecePrivateKeyParameters) {
            return ((McEliecePrivateKeyParameters)mcElieceKeyParameters).getN();
        }
        throw new IllegalArgumentException("unsupported type");
    }

    @Override
    public void init(boolean bl, CipherParameters cipherParameters) {
        if (bl) {
            if (cipherParameters instanceof ParametersWithRandom) {
                ParametersWithRandom parametersWithRandom = (ParametersWithRandom)cipherParameters;
                this.sr = parametersWithRandom.getRandom();
                this.key = (McEliecePublicKeyParameters)parametersWithRandom.getParameters();
                this.initCipherEncrypt((McEliecePublicKeyParameters)this.key);
                return;
            }
            this.sr = new SecureRandom();
            this.key = (McEliecePublicKeyParameters)cipherParameters;
            this.initCipherEncrypt((McEliecePublicKeyParameters)this.key);
            return;
        }
        this.key = (McEliecePrivateKeyParameters)cipherParameters;
        this.initCipherDecrypt((McEliecePrivateKeyParameters)this.key);
    }

    public void initCipherDecrypt(McEliecePrivateKeyParameters mcEliecePrivateKeyParameters) {
        this.n = mcEliecePrivateKeyParameters.getN();
        this.k = mcEliecePrivateKeyParameters.getK();
        this.maxPlainTextSize = this.k >> 3;
        this.cipherTextSize = this.n >> 3;
    }

    /*
     * Enabled aggressive block sorting
     */
    public void initCipherEncrypt(McEliecePublicKeyParameters mcEliecePublicKeyParameters) {
        SecureRandom secureRandom = this.sr != null ? this.sr : new SecureRandom();
        this.sr = secureRandom;
        this.n = mcEliecePublicKeyParameters.getN();
        this.k = mcEliecePublicKeyParameters.getK();
        this.t = mcEliecePublicKeyParameters.getT();
        this.cipherTextSize = this.n >> 3;
        this.maxPlainTextSize = this.k >> 3;
    }

    @Override
    public byte[] messageDecrypt(byte[] arrby) {
        GF2Vector gF2Vector = GF2Vector.OS2VP(this.n, arrby);
        McEliecePrivateKeyParameters mcEliecePrivateKeyParameters = (McEliecePrivateKeyParameters)this.key;
        GF2mField gF2mField = mcEliecePrivateKeyParameters.getField();
        PolynomialGF2mSmallM polynomialGF2mSmallM = mcEliecePrivateKeyParameters.getGoppaPoly();
        GF2Matrix gF2Matrix = mcEliecePrivateKeyParameters.getSInv();
        Permutation permutation = mcEliecePrivateKeyParameters.getP1();
        Permutation permutation2 = mcEliecePrivateKeyParameters.getP2();
        GF2Matrix gF2Matrix2 = mcEliecePrivateKeyParameters.getH();
        PolynomialGF2mSmallM[] arrpolynomialGF2mSmallM = mcEliecePrivateKeyParameters.getQInv();
        Permutation permutation3 = permutation.rightMultiply(permutation2);
        GF2Vector gF2Vector2 = (GF2Vector)gF2Vector.multiply(permutation3.computeInverse());
        GF2Vector gF2Vector3 = GoppaCode.syndromeDecode((GF2Vector)gF2Matrix2.rightMultiply(gF2Vector2), gF2mField, polynomialGF2mSmallM, arrpolynomialGF2mSmallM);
        GF2Vector gF2Vector4 = (GF2Vector)((GF2Vector)gF2Vector2.add(gF2Vector3)).multiply(permutation);
        (GF2Vector)gF2Vector3.multiply(permutation3);
        return this.computeMessage((GF2Vector)gF2Matrix.leftMultiply(gF2Vector4.extractRightVector(this.k)));
    }

    @Override
    public byte[] messageEncrypt(byte[] arrby) {
        GF2Vector gF2Vector = this.computeMessageRepresentative(arrby);
        GF2Vector gF2Vector2 = new GF2Vector(this.n, this.t, this.sr);
        return ((GF2Vector)((McEliecePublicKeyParameters)this.key).getG().leftMultiply(gF2Vector).add(gF2Vector2)).getEncoded();
    }
}

