/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.io.IOException
 *  java.lang.Exception
 *  java.lang.IllegalArgumentException
 *  java.lang.Object
 *  java.lang.SecurityException
 *  java.lang.String
 *  java.lang.Throwable
 *  java.math.BigInteger
 *  java.security.GeneralSecurityException
 *  java.security.InvalidKeyException
 *  java.security.NoSuchProviderException
 *  java.security.PrivateKey
 *  java.security.PublicKey
 *  java.security.SecureRandom
 *  java.security.SignatureException
 *  java.security.cert.CertificateParsingException
 *  java.security.cert.X509Certificate
 *  java.util.Date
 *  java.util.Iterator
 *  javax.security.auth.x500.X500Principal
 */
package org.bouncycastle.x509;

import java.io.IOException;
import java.math.BigInteger;
import java.security.GeneralSecurityException;
import java.security.InvalidKeyException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.SignatureException;
import java.security.cert.CertificateParsingException;
import java.security.cert.X509Certificate;
import java.util.Date;
import java.util.Iterator;
import javax.security.auth.x500.X500Principal;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1InputStream;
import org.bouncycastle.asn1.ASN1Integer;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.DERBitString;
import org.bouncycastle.asn1.DERSequence;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.bouncycastle.asn1.x509.Certificate;
import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.bouncycastle.asn1.x509.TBSCertificate;
import org.bouncycastle.asn1.x509.Time;
import org.bouncycastle.asn1.x509.V3TBSCertificateGenerator;
import org.bouncycastle.asn1.x509.X509Extensions;
import org.bouncycastle.asn1.x509.X509ExtensionsGenerator;
import org.bouncycastle.asn1.x509.X509Name;
import org.bouncycastle.jce.X509Principal;
import org.bouncycastle.jce.provider.X509CertificateObject;
import org.bouncycastle.x509.ExtCertificateEncodingException;
import org.bouncycastle.x509.X509Util;
import org.bouncycastle.x509.extension.X509ExtensionUtil;

public class X509V3CertificateGenerator {
    private X509ExtensionsGenerator extGenerator = new X509ExtensionsGenerator();
    private AlgorithmIdentifier sigAlgId;
    private ASN1ObjectIdentifier sigOID;
    private String signatureAlgorithm;
    private V3TBSCertificateGenerator tbsGen = new V3TBSCertificateGenerator();

    /*
     * Enabled aggressive block sorting
     */
    private DERBitString booleanToBitString(boolean[] arrbl) {
        byte[] arrby = new byte[(7 + arrbl.length) / 8];
        for (int i = 0; i != arrbl.length; ++i) {
            int n = i / 8;
            byte by = arrby[n];
            int n2 = arrbl[i] ? 1 << 7 - i % 8 : 0;
            arrby[n] = (byte)(n2 | by);
        }
        int n = arrbl.length % 8;
        if (n == 0) {
            return new DERBitString(arrby);
        }
        return new DERBitString(arrby, 8 - n);
    }

    private X509Certificate generateJcaObject(TBSCertificate tBSCertificate, byte[] arrby) {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        aSN1EncodableVector.add(tBSCertificate);
        aSN1EncodableVector.add(this.sigAlgId);
        aSN1EncodableVector.add(new DERBitString(arrby));
        return new X509CertificateObject(Certificate.getInstance(new DERSequence(aSN1EncodableVector)));
    }

    private TBSCertificate generateTbsCert() {
        if (!this.extGenerator.isEmpty()) {
            this.tbsGen.setExtensions(this.extGenerator.generate());
        }
        return this.tbsGen.generateTBSCertificate();
    }

    public void addExtension(String string, boolean bl, ASN1Encodable aSN1Encodable) {
        this.addExtension(new ASN1ObjectIdentifier(string), bl, aSN1Encodable);
    }

    public void addExtension(String string, boolean bl, byte[] arrby) {
        this.addExtension(new ASN1ObjectIdentifier(string), bl, arrby);
    }

    public void addExtension(ASN1ObjectIdentifier aSN1ObjectIdentifier, boolean bl, ASN1Encodable aSN1Encodable) {
        this.extGenerator.addExtension(new ASN1ObjectIdentifier(aSN1ObjectIdentifier.getId()), bl, aSN1Encodable);
    }

    public void addExtension(ASN1ObjectIdentifier aSN1ObjectIdentifier, boolean bl, byte[] arrby) {
        this.extGenerator.addExtension(new ASN1ObjectIdentifier(aSN1ObjectIdentifier.getId()), bl, arrby);
    }

    public void copyAndAddExtension(String string, boolean bl, X509Certificate x509Certificate) {
        byte[] arrby = x509Certificate.getExtensionValue(string);
        if (arrby == null) {
            throw new CertificateParsingException("extension " + string + " not present");
        }
        try {
            this.addExtension(string, bl, (ASN1Encodable)X509ExtensionUtil.fromExtensionValue(arrby));
            return;
        }
        catch (IOException iOException) {
            throw new CertificateParsingException(iOException.toString());
        }
    }

    public void copyAndAddExtension(ASN1ObjectIdentifier aSN1ObjectIdentifier, boolean bl, X509Certificate x509Certificate) {
        this.copyAndAddExtension(aSN1ObjectIdentifier.getId(), bl, x509Certificate);
    }

    public X509Certificate generate(PrivateKey privateKey) {
        return this.generate(privateKey, (SecureRandom)null);
    }

    public X509Certificate generate(PrivateKey privateKey, String string) {
        return this.generate(privateKey, string, null);
    }

    public X509Certificate generate(PrivateKey privateKey, String string, SecureRandom secureRandom) {
        byte[] arrby;
        TBSCertificate tBSCertificate = this.generateTbsCert();
        try {
            arrby = X509Util.calculateSignature(this.sigOID, this.signatureAlgorithm, string, privateKey, secureRandom, tBSCertificate);
        }
        catch (IOException iOException) {
            throw new ExtCertificateEncodingException("exception encoding TBS cert", iOException);
        }
        try {
            X509Certificate x509Certificate = this.generateJcaObject(tBSCertificate, arrby);
            return x509Certificate;
        }
        catch (CertificateParsingException certificateParsingException) {
            throw new ExtCertificateEncodingException("exception producing certificate object", certificateParsingException);
        }
    }

    public X509Certificate generate(PrivateKey privateKey, SecureRandom secureRandom) {
        byte[] arrby;
        TBSCertificate tBSCertificate = this.generateTbsCert();
        try {
            arrby = X509Util.calculateSignature(this.sigOID, this.signatureAlgorithm, privateKey, secureRandom, tBSCertificate);
        }
        catch (IOException iOException) {
            throw new ExtCertificateEncodingException("exception encoding TBS cert", iOException);
        }
        try {
            X509Certificate x509Certificate = this.generateJcaObject(tBSCertificate, arrby);
            return x509Certificate;
        }
        catch (CertificateParsingException certificateParsingException) {
            throw new ExtCertificateEncodingException("exception producing certificate object", certificateParsingException);
        }
    }

    public X509Certificate generateX509Certificate(PrivateKey privateKey) {
        try {
            X509Certificate x509Certificate = this.generateX509Certificate(privateKey, "BC", null);
            return x509Certificate;
        }
        catch (NoSuchProviderException noSuchProviderException) {
            throw new SecurityException("BC provider not installed!");
        }
    }

    public X509Certificate generateX509Certificate(PrivateKey privateKey, String string) {
        return this.generateX509Certificate(privateKey, string, null);
    }

    public X509Certificate generateX509Certificate(PrivateKey privateKey, String string, SecureRandom secureRandom) {
        try {
            X509Certificate x509Certificate = this.generate(privateKey, string, secureRandom);
            return x509Certificate;
        }
        catch (NoSuchProviderException noSuchProviderException) {
            throw noSuchProviderException;
        }
        catch (SignatureException signatureException) {
            throw signatureException;
        }
        catch (InvalidKeyException invalidKeyException) {
            throw invalidKeyException;
        }
        catch (GeneralSecurityException generalSecurityException) {
            throw new SecurityException("exception: " + (Object)((Object)generalSecurityException));
        }
    }

    public X509Certificate generateX509Certificate(PrivateKey privateKey, SecureRandom secureRandom) {
        try {
            X509Certificate x509Certificate = this.generateX509Certificate(privateKey, "BC", secureRandom);
            return x509Certificate;
        }
        catch (NoSuchProviderException noSuchProviderException) {
            throw new SecurityException("BC provider not installed!");
        }
    }

    public Iterator getSignatureAlgNames() {
        return X509Util.getAlgNames();
    }

    public void reset() {
        this.tbsGen = new V3TBSCertificateGenerator();
        this.extGenerator.reset();
    }

    public void setIssuerDN(X500Principal x500Principal) {
        try {
            this.tbsGen.setIssuer(new X509Principal(x500Principal.getEncoded()));
            return;
        }
        catch (IOException iOException) {
            throw new IllegalArgumentException("can't process principal: " + (Object)((Object)iOException));
        }
    }

    public void setIssuerDN(X509Name x509Name) {
        this.tbsGen.setIssuer(x509Name);
    }

    public void setIssuerUniqueID(boolean[] arrbl) {
        this.tbsGen.setIssuerUniqueID(this.booleanToBitString(arrbl));
    }

    public void setNotAfter(Date date) {
        this.tbsGen.setEndDate(new Time(date));
    }

    public void setNotBefore(Date date) {
        this.tbsGen.setStartDate(new Time(date));
    }

    public void setPublicKey(PublicKey publicKey) {
        try {
            this.tbsGen.setSubjectPublicKeyInfo(SubjectPublicKeyInfo.getInstance(new ASN1InputStream(publicKey.getEncoded()).readObject()));
            return;
        }
        catch (Exception exception) {
            throw new IllegalArgumentException("unable to process key - " + exception.toString());
        }
    }

    public void setSerialNumber(BigInteger bigInteger) {
        if (bigInteger.compareTo(BigInteger.ZERO) <= 0) {
            throw new IllegalArgumentException("serial number must be a positive integer");
        }
        this.tbsGen.setSerialNumber(new ASN1Integer(bigInteger));
    }

    public void setSignatureAlgorithm(String string) {
        this.signatureAlgorithm = string;
        try {
            this.sigOID = X509Util.getAlgorithmOID(string);
        }
        catch (Exception exception) {
            throw new IllegalArgumentException("Unknown signature type requested: " + string);
        }
        this.sigAlgId = X509Util.getSigAlgID(this.sigOID, string);
        this.tbsGen.setSignature(this.sigAlgId);
    }

    public void setSubjectDN(X500Principal x500Principal) {
        try {
            this.tbsGen.setSubject(new X509Principal(x500Principal.getEncoded()));
            return;
        }
        catch (IOException iOException) {
            throw new IllegalArgumentException("can't process principal: " + (Object)((Object)iOException));
        }
    }

    public void setSubjectDN(X509Name x509Name) {
        this.tbsGen.setSubject(x509Name);
    }

    public void setSubjectUniqueID(boolean[] arrbl) {
        this.tbsGen.setSubjectUniqueID(this.booleanToBitString(arrbl));
    }
}

