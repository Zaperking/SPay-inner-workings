/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.io.IOException
 *  java.lang.String
 *  java.lang.Throwable
 */
package org.bouncycastle.crypto.io;

import java.io.IOException;

public class CipherIOException
extends IOException {
    private static final long serialVersionUID = 1L;
    private final Throwable cause;

    public CipherIOException(String string, Throwable throwable) {
        super(string);
        this.cause = throwable;
    }

    public Throwable getCause() {
        return this.cause;
    }
}

