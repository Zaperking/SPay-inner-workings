/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.io.IOException
 *  java.lang.Class
 *  java.lang.Object
 *  java.lang.String
 */
package org.bouncycastle.asn1.cms;

import java.io.IOException;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1Integer;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.ASN1SequenceParser;
import org.bouncycastle.asn1.ASN1Set;
import org.bouncycastle.asn1.ASN1SetParser;
import org.bouncycastle.asn1.ASN1TaggedObjectParser;
import org.bouncycastle.asn1.cms.ContentInfoParser;

public class SignedDataParser {
    private boolean _certsCalled;
    private boolean _crlsCalled;
    private Object _nextObject;
    private ASN1SequenceParser _seq;
    private ASN1Integer _version;

    private SignedDataParser(ASN1SequenceParser aSN1SequenceParser) {
        this._seq = aSN1SequenceParser;
        this._version = (ASN1Integer)aSN1SequenceParser.readObject();
    }

    public static SignedDataParser getInstance(Object object) {
        if (object instanceof ASN1Sequence) {
            return new SignedDataParser(((ASN1Sequence)object).parser());
        }
        if (object instanceof ASN1SequenceParser) {
            return new SignedDataParser((ASN1SequenceParser)object);
        }
        throw new IOException("unknown object encountered: " + object.getClass().getName());
    }

    public ASN1SetParser getCertificates() {
        this._certsCalled = true;
        this._nextObject = this._seq.readObject();
        if (this._nextObject instanceof ASN1TaggedObjectParser && ((ASN1TaggedObjectParser)this._nextObject).getTagNo() == 0) {
            ASN1SetParser aSN1SetParser = (ASN1SetParser)((ASN1TaggedObjectParser)this._nextObject).getObjectParser(17, false);
            this._nextObject = null;
            return aSN1SetParser;
        }
        return null;
    }

    public ASN1SetParser getCrls() {
        if (!this._certsCalled) {
            throw new IOException("getCerts() has not been called.");
        }
        this._crlsCalled = true;
        if (this._nextObject == null) {
            this._nextObject = this._seq.readObject();
        }
        if (this._nextObject instanceof ASN1TaggedObjectParser && ((ASN1TaggedObjectParser)this._nextObject).getTagNo() == 1) {
            ASN1SetParser aSN1SetParser = (ASN1SetParser)((ASN1TaggedObjectParser)this._nextObject).getObjectParser(17, false);
            this._nextObject = null;
            return aSN1SetParser;
        }
        return null;
    }

    public ASN1SetParser getDigestAlgorithms() {
        ASN1Encodable aSN1Encodable = this._seq.readObject();
        if (aSN1Encodable instanceof ASN1Set) {
            return ((ASN1Set)aSN1Encodable).parser();
        }
        return (ASN1SetParser)aSN1Encodable;
    }

    public ContentInfoParser getEncapContentInfo() {
        return new ContentInfoParser((ASN1SequenceParser)this._seq.readObject());
    }

    public ASN1SetParser getSignerInfos() {
        if (!this._certsCalled || !this._crlsCalled) {
            throw new IOException("getCerts() and/or getCrls() has not been called.");
        }
        if (this._nextObject == null) {
            this._nextObject = this._seq.readObject();
        }
        return (ASN1SetParser)this._nextObject;
    }

    public ASN1Integer getVersion() {
        return this._version;
    }
}

