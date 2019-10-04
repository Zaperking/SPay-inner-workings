/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Class
 *  java.lang.IllegalArgumentException
 *  java.lang.Object
 *  java.lang.String
 *  java.math.BigInteger
 *  java.util.Enumeration
 */
package org.bouncycastle.asn1.x509.sigi;

import java.math.BigInteger;
import java.util.Enumeration;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1GeneralizedTime;
import org.bouncycastle.asn1.ASN1Integer;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.ASN1TaggedObject;
import org.bouncycastle.asn1.DERPrintableString;
import org.bouncycastle.asn1.DERSequence;
import org.bouncycastle.asn1.DERTaggedObject;
import org.bouncycastle.asn1.x500.DirectoryString;
import org.bouncycastle.asn1.x509.sigi.NameOrPseudonym;

public class PersonalData
extends ASN1Object {
    private ASN1GeneralizedTime dateOfBirth;
    private String gender;
    private BigInteger nameDistinguisher;
    private NameOrPseudonym nameOrPseudonym;
    private DirectoryString placeOfBirth;
    private DirectoryString postalAddress;

    private PersonalData(ASN1Sequence aSN1Sequence) {
        if (aSN1Sequence.size() < 1) {
            throw new IllegalArgumentException("Bad sequence size: " + aSN1Sequence.size());
        }
        Enumeration enumeration = aSN1Sequence.getObjects();
        this.nameOrPseudonym = NameOrPseudonym.getInstance(enumeration.nextElement());
        block7 : while (enumeration.hasMoreElements()) {
            ASN1TaggedObject aSN1TaggedObject = ASN1TaggedObject.getInstance(enumeration.nextElement());
            switch (aSN1TaggedObject.getTagNo()) {
                default: {
                    throw new IllegalArgumentException("Bad tag number: " + aSN1TaggedObject.getTagNo());
                }
                case 0: {
                    this.nameDistinguisher = ASN1Integer.getInstance(aSN1TaggedObject, false).getValue();
                    continue block7;
                }
                case 1: {
                    this.dateOfBirth = ASN1GeneralizedTime.getInstance(aSN1TaggedObject, false);
                    continue block7;
                }
                case 2: {
                    this.placeOfBirth = DirectoryString.getInstance(aSN1TaggedObject, true);
                    continue block7;
                }
                case 3: {
                    this.gender = DERPrintableString.getInstance(aSN1TaggedObject, false).getString();
                    continue block7;
                }
                case 4: 
            }
            this.postalAddress = DirectoryString.getInstance(aSN1TaggedObject, true);
        }
    }

    public PersonalData(NameOrPseudonym nameOrPseudonym, BigInteger bigInteger, ASN1GeneralizedTime aSN1GeneralizedTime, DirectoryString directoryString, String string, DirectoryString directoryString2) {
        this.nameOrPseudonym = nameOrPseudonym;
        this.dateOfBirth = aSN1GeneralizedTime;
        this.gender = string;
        this.nameDistinguisher = bigInteger;
        this.postalAddress = directoryString2;
        this.placeOfBirth = directoryString;
    }

    public static PersonalData getInstance(Object object) {
        if (object == null || object instanceof PersonalData) {
            return (PersonalData)object;
        }
        if (object instanceof ASN1Sequence) {
            return new PersonalData((ASN1Sequence)object);
        }
        throw new IllegalArgumentException("illegal object in getInstance: " + object.getClass().getName());
    }

    public ASN1GeneralizedTime getDateOfBirth() {
        return this.dateOfBirth;
    }

    public String getGender() {
        return this.gender;
    }

    public BigInteger getNameDistinguisher() {
        return this.nameDistinguisher;
    }

    public NameOrPseudonym getNameOrPseudonym() {
        return this.nameOrPseudonym;
    }

    public DirectoryString getPlaceOfBirth() {
        return this.placeOfBirth;
    }

    public DirectoryString getPostalAddress() {
        return this.postalAddress;
    }

    @Override
    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        aSN1EncodableVector.add(this.nameOrPseudonym);
        if (this.nameDistinguisher != null) {
            aSN1EncodableVector.add(new DERTaggedObject(false, 0, new ASN1Integer(this.nameDistinguisher)));
        }
        if (this.dateOfBirth != null) {
            aSN1EncodableVector.add(new DERTaggedObject(false, 1, this.dateOfBirth));
        }
        if (this.placeOfBirth != null) {
            aSN1EncodableVector.add(new DERTaggedObject(true, 2, this.placeOfBirth));
        }
        if (this.gender != null) {
            aSN1EncodableVector.add(new DERTaggedObject(false, 3, new DERPrintableString(this.gender, true)));
        }
        if (this.postalAddress != null) {
            aSN1EncodableVector.add(new DERTaggedObject(true, 4, this.postalAddress));
        }
        return new DERSequence(aSN1EncodableVector);
    }
}

