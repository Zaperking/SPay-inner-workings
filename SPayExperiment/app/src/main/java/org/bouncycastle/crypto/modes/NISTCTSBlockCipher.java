/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.IllegalArgumentException
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.System
 */
package org.bouncycastle.crypto.modes;

import org.bouncycastle.crypto.BlockCipher;
import org.bouncycastle.crypto.BufferedBlockCipher;
import org.bouncycastle.crypto.DataLengthException;
import org.bouncycastle.crypto.modes.CBCBlockCipher;

public class NISTCTSBlockCipher
extends BufferedBlockCipher {
    public static final int CS1 = 1;
    public static final int CS2 = 2;
    public static final int CS3 = 3;
    private final int blockSize;
    private final int type;

    public NISTCTSBlockCipher(int n2, BlockCipher blockCipher) {
        this.type = n2;
        this.cipher = new CBCBlockCipher(blockCipher);
        this.blockSize = blockCipher.getBlockSize();
        this.buf = new byte[2 * this.blockSize];
        this.bufOff = 0;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public int doFinal(byte[] arrby, int n2) {
        if (n2 + this.bufOff > arrby.length) {
            throw new DataLengthException("output buffer to small in doFinal");
        }
        int n3 = this.cipher.getBlockSize();
        int n4 = this.bufOff - n3;
        byte[] arrby2 = new byte[n3];
        if (this.forEncryption) {
            if (this.bufOff < n3) {
                throw new DataLengthException("need at least one block of input for NISTCTS");
            }
            if (this.bufOff > n3) {
                byte[] arrby3 = new byte[n3];
                if (this.type == 2 || this.type == 3) {
                    this.cipher.processBlock(this.buf, 0, arrby2, 0);
                    System.arraycopy((Object)this.buf, (int)n3, (Object)arrby3, (int)0, (int)n4);
                    this.cipher.processBlock(arrby3, 0, arrby3, 0);
                    if (this.type == 2 && n4 == n3) {
                        System.arraycopy((Object)arrby2, (int)0, (Object)arrby, (int)n2, (int)n3);
                        System.arraycopy((Object)arrby3, (int)0, (Object)arrby, (int)(n3 + n2), (int)n4);
                    } else {
                        System.arraycopy((Object)arrby3, (int)0, (Object)arrby, (int)n2, (int)n3);
                        System.arraycopy((Object)arrby2, (int)0, (Object)arrby, (int)(n2 + n3), (int)n4);
                    }
                } else {
                    System.arraycopy((Object)this.buf, (int)0, (Object)arrby2, (int)0, (int)n3);
                    this.cipher.processBlock(arrby2, 0, arrby2, 0);
                    System.arraycopy((Object)arrby2, (int)0, (Object)arrby, (int)n2, (int)n4);
                    System.arraycopy((Object)this.buf, (int)(this.bufOff - n4), (Object)arrby3, (int)0, (int)n4);
                    this.cipher.processBlock(arrby3, 0, arrby3, 0);
                    System.arraycopy((Object)arrby3, (int)0, (Object)arrby, (int)(n4 + n2), (int)n3);
                }
            } else {
                this.cipher.processBlock(this.buf, 0, arrby2, 0);
                System.arraycopy((Object)arrby2, (int)0, (Object)arrby, (int)n2, (int)n3);
            }
        } else {
            if (this.bufOff < n3) {
                throw new DataLengthException("need at least one block of input for CTS");
            }
            byte[] arrby4 = new byte[n3];
            if (this.bufOff > n3) {
                if (this.type == 3 || this.type == 2 && (this.buf.length - this.bufOff) % n3 != 0) {
                    if (this.cipher instanceof CBCBlockCipher) {
                        ((CBCBlockCipher)this.cipher).getUnderlyingCipher().processBlock(this.buf, 0, arrby2, 0);
                    } else {
                        this.cipher.processBlock(this.buf, 0, arrby2, 0);
                    }
                    for (int i2 = n3; i2 != this.bufOff; ++i2) {
                        arrby4[i2 - n3] = (byte)(arrby2[i2 - n3] ^ this.buf[i2]);
                    }
                    System.arraycopy((Object)this.buf, (int)n3, (Object)arrby2, (int)0, (int)n4);
                    this.cipher.processBlock(arrby2, 0, arrby, n2);
                    System.arraycopy((Object)arrby4, (int)0, (Object)arrby, (int)(n2 + n3), (int)n4);
                } else {
                    ((CBCBlockCipher)this.cipher).getUnderlyingCipher().processBlock(this.buf, this.bufOff - n3, arrby4, 0);
                    System.arraycopy((Object)this.buf, (int)0, (Object)arrby2, (int)0, (int)n3);
                    if (n4 != n3) {
                        System.arraycopy((Object)arrby4, (int)n4, (Object)arrby2, (int)n4, (int)(n3 - n4));
                    }
                    this.cipher.processBlock(arrby2, 0, arrby2, 0);
                    System.arraycopy((Object)arrby2, (int)0, (Object)arrby, (int)n2, (int)n3);
                    for (int i3 = 0; i3 != n4; ++i3) {
                        arrby4[i3] = (byte)(arrby4[i3] ^ this.buf[i3]);
                    }
                    System.arraycopy((Object)arrby4, (int)0, (Object)arrby, (int)(n2 + n3), (int)n4);
                }
            } else {
                this.cipher.processBlock(this.buf, 0, arrby2, 0);
                System.arraycopy((Object)arrby2, (int)0, (Object)arrby, (int)n2, (int)n3);
            }
        }
        int n5 = this.bufOff;
        this.reset();
        return n5;
    }

    @Override
    public int getOutputSize(int n2) {
        return n2 + this.bufOff;
    }

    @Override
    public int getUpdateOutputSize(int n2) {
        int n3 = n2 + this.bufOff;
        int n4 = n3 % this.buf.length;
        if (n4 == 0) {
            return n3 - this.buf.length;
        }
        return n3 - n4;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public int processByte(byte by, byte[] arrby, int n2) {
        int n3;
        if (this.bufOff == this.buf.length) {
            n3 = this.cipher.processBlock(this.buf, 0, arrby, n2);
            System.arraycopy((Object)this.buf, (int)this.blockSize, (Object)this.buf, (int)0, (int)this.blockSize);
            this.bufOff = this.blockSize;
        } else {
            n3 = 0;
        }
        byte[] arrby2 = this.buf;
        int n4 = this.bufOff;
        this.bufOff = n4 + 1;
        arrby2[n4] = by;
        return n3;
    }

    @Override
    public int processBytes(byte[] arrby, int n2, int n3, byte[] arrby2, int n4) {
        int n5;
        if (n3 < 0) {
            throw new IllegalArgumentException("Can't have a negative input length!");
        }
        int n6 = this.getBlockSize();
        int n7 = this.getUpdateOutputSize(n3);
        if (n7 > 0 && n7 + n4 > arrby2.length) {
            throw new DataLengthException("output buffer too short");
        }
        int n8 = this.buf.length - this.bufOff;
        if (n3 > n8) {
            System.arraycopy((Object)arrby, (int)n2, (Object)this.buf, (int)this.bufOff, (int)n8);
            n5 = 0 + this.cipher.processBlock(this.buf, 0, arrby2, n4);
            System.arraycopy((Object)this.buf, (int)n6, (Object)this.buf, (int)0, (int)n6);
            this.bufOff = n6;
            n3 -= n8;
            n2 += n8;
            while (n3 > n6) {
                System.arraycopy((Object)arrby, (int)n2, (Object)this.buf, (int)this.bufOff, (int)n6);
                n5 += this.cipher.processBlock(this.buf, 0, arrby2, n4 + n5);
                System.arraycopy((Object)this.buf, (int)n6, (Object)this.buf, (int)0, (int)n6);
                n3 -= n6;
                n2 += n6;
            }
        } else {
            n5 = 0;
        }
        System.arraycopy((Object)arrby, (int)n2, (Object)this.buf, (int)this.bufOff, (int)n3);
        this.bufOff = n3 + this.bufOff;
        return n5;
    }
}

