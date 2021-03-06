/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.String
 */
package com.samsung.android.spayfw.remoteservice.tokenrequester.models;

import com.samsung.android.spayfw.remoteservice.models.Art;
import com.samsung.android.spayfw.remoteservice.tokenrequester.models.CardColor;
import com.samsung.android.spayfw.remoteservice.tokenrequester.models.CardIssuerInfo;
import com.samsung.android.spayfw.remoteservice.tokenrequester.models.Expiry;
import com.samsung.android.spayfw.remoteservice.tokenrequester.models.Type;

public class CardInfo
extends Type {
    private Art[] arts;
    private String brand;
    private CardColor[] colors;
    private String description;
    private Expiry expiry;
    private CardIssuerInfo issuer;
    private String name;
    private String reference;
    private String suffix;

    public CardInfo(String string) {
        super(string);
    }

    public Art[] getArts() {
        return this.arts;
    }

    public String getBrand() {
        return this.brand;
    }

    public CardColor[] getColors() {
        return this.colors;
    }

    public String getDescription() {
        return this.description;
    }

    public Expiry getExpiry() {
        return this.expiry;
    }

    public CardIssuerInfo getIssuer() {
        return this.issuer;
    }

    public String getName() {
        return this.name;
    }

    public String getReference() {
        return this.reference;
    }

    public String getSuffix() {
        return this.suffix;
    }
}

