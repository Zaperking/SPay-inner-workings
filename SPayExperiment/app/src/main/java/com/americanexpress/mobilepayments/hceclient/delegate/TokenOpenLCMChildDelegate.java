/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Object
 *  java.lang.String
 */
package com.americanexpress.mobilepayments.hceclient.delegate;

import com.americanexpress.mobilepayments.hceclient.delegate.TokenOpenLCMDelegate;
import com.americanexpress.mobilepayments.hceclient.securecomponent.SecureComponentImpl;
import com.americanexpress.mobilepayments.hceclient.session.Operation;
import com.americanexpress.mobilepayments.hceclient.utils.common.LLVARUtil;

public class TokenOpenLCMChildDelegate
extends TokenOpenLCMDelegate {
    @Override
    protected void invokeOpen() {
        Object[] arrobject = new Object[]{'1' + Operation.OPERATION.getTokenRefId()};
        byte[] arrby = LLVARUtil.objectsToLLVar(arrobject);
        this.checkSCStatus(new SecureComponentImpl().open(arrby));
    }
}

