/*
 * Decompiled with CFR 0.0.
 */
package com.mastercard.mobile_api.utils.apdu.emv;

import com.mastercard.mobile_api.bytes.ByteArray;
import com.mastercard.mobile_api.bytes.ByteArrayFactory;
import com.mastercard.mobile_api.utils.apdu.Apdu;

public class ComputeCCApdu
extends Apdu {
    public static final byte CLA = -128;
    public static final byte INS = 42;
    private ByteArray UDOL;
    private ByteArray authorizedAmount;
    private ByteArray merchantCategoryCode;
    private ByteArray mobileSupportIndicator;
    private ByteArray terminalCountryCode;
    private byte terminalType;
    private ByteArray transactionCurrencyCode;
    private ByteArray transactionDate;
    private ByteArray transactionType;
    private ByteArray unpredictableNumber;

    public ComputeCCApdu(ByteArray byteArray) {
        super(byteArray);
        this.parse(byteArray);
    }

    private void parse(ByteArray byteArray) {
        this.UDOL = byteArray.copyOfRange(5, 4 + byteArray.getByte(4));
        this.unpredictableNumber = byteArray.copyOfRange(5, 9);
        this.mobileSupportIndicator = byteArray.copyOfRange(9, 10);
        this.authorizedAmount = byteArray.copyOfRange(10, 16);
        this.transactionCurrencyCode = byteArray.copyOfRange(16, 18);
        this.terminalCountryCode = byteArray.copyOfRange(18, 20);
        if (byteArray.getLength() >= 25) {
            this.transactionType = byteArray.copyOfRange(20, 21);
            this.transactionDate = byteArray.copyOfRange(21, 24);
            this.terminalType = byteArray.getByte(24);
            return;
        }
        this.transactionType = ByteArrayFactory.getInstance().getByteArray(1);
        this.transactionDate = ByteArrayFactory.getInstance().getByteArray(3);
        this.merchantCategoryCode = ByteArrayFactory.getInstance().getByteArray(2);
        this.terminalType = byteArray.getByte(20);
    }

    public ByteArray getAuthorizedAmount() {
        return this.authorizedAmount;
    }

    public ByteArray getMerchantCategoryCode() {
        return this.merchantCategoryCode;
    }

    public ByteArray getMobileSupportIndicator() {
        return this.mobileSupportIndicator;
    }

    public ByteArray getTerminalCountryCode() {
        return this.terminalCountryCode;
    }

    public byte getTerminalType() {
        return this.terminalType;
    }

    public ByteArray getTransactionCurrencyCode() {
        return this.transactionCurrencyCode;
    }

    public ByteArray getTransactionDate() {
        return this.transactionDate;
    }

    public ByteArray getTransactionType() {
        return this.transactionType;
    }

    public ByteArray getUDOL() {
        return this.UDOL;
    }

    public ByteArray getUnpredictableNumber() {
        return this.unpredictableNumber;
    }

    public void setMerchantCategoryCode(ByteArray byteArray) {
        this.merchantCategoryCode = byteArray;
    }

    public void setTransactionDate(ByteArray byteArray) {
        this.transactionDate = byteArray;
    }

    public void setTransactionType(ByteArray byteArray) {
        this.transactionType = byteArray;
    }
}

