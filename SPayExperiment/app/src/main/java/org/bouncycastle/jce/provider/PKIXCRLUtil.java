/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.Throwable
 *  java.security.cert.CertStore
 *  java.security.cert.CertStoreException
 *  java.security.cert.X509CRL
 *  java.security.cert.X509Certificate
 *  java.util.Collection
 *  java.util.Date
 *  java.util.HashSet
 *  java.util.Iterator
 *  java.util.List
 *  java.util.Set
 */
package org.bouncycastle.jce.provider;

import java.security.cert.CertStore;
import java.security.cert.CertStoreException;
import java.security.cert.X509CRL;
import java.security.cert.X509Certificate;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.bouncycastle.jcajce.PKIXCRLStoreSelector;
import org.bouncycastle.jce.provider.AnnotatedException;
import org.bouncycastle.util.Selector;
import org.bouncycastle.util.Store;
import org.bouncycastle.util.StoreException;

class PKIXCRLUtil {
    PKIXCRLUtil() {
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private final Collection findCRLs(PKIXCRLStoreSelector pKIXCRLStoreSelector, List list) {
        HashSet hashSet = new HashSet();
        Iterator iterator = list.iterator();
        AnnotatedException annotatedException = null;
        boolean bl = false;
        while (iterator.hasNext()) {
            AnnotatedException annotatedException2;
            boolean bl2;
            Object object = iterator.next();
            if (object instanceof Store) {
                Store store = (Store)object;
                try {
                    hashSet.addAll(store.getMatches(pKIXCRLStoreSelector));
                    bl2 = true;
                    annotatedException2 = annotatedException;
                }
                catch (StoreException storeException) {
                    AnnotatedException annotatedException3 = new AnnotatedException("Exception searching in X.509 CRL store.", (Throwable)storeException);
                    boolean bl3 = bl;
                    annotatedException2 = annotatedException3;
                    bl2 = bl3;
                }
            } else {
                CertStore certStore = (CertStore)object;
                try {
                    hashSet.addAll(PKIXCRLStoreSelector.getCRLs(pKIXCRLStoreSelector, certStore));
                    bl2 = true;
                    annotatedException2 = annotatedException;
                }
                catch (CertStoreException certStoreException) {
                    AnnotatedException annotatedException4 = new AnnotatedException("Exception searching in X.509 CRL store.", certStoreException);
                    bl2 = bl;
                    annotatedException2 = annotatedException4;
                }
            }
            annotatedException = annotatedException2;
            bl = bl2;
        }
        if (!bl && annotatedException != null) {
            throw annotatedException;
        }
        return hashSet;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public Set findCRLs(PKIXCRLStoreSelector pKIXCRLStoreSelector, Date date, List list, List list2) {
        HashSet hashSet = new HashSet();
        try {}
        catch (AnnotatedException annotatedException) {
            throw new AnnotatedException("Exception obtaining complete CRLs.", (Throwable)annotatedException);
        }
        hashSet.addAll(this.findCRLs(pKIXCRLStoreSelector, list2));
        hashSet.addAll(this.findCRLs(pKIXCRLStoreSelector, list));
        HashSet hashSet2 = new HashSet();
        Iterator iterator = hashSet.iterator();
        while (iterator.hasNext()) {
            X509CRL x509CRL = (X509CRL)iterator.next();
            if (!x509CRL.getNextUpdate().after(date)) continue;
            X509Certificate x509Certificate = pKIXCRLStoreSelector.getCertificateChecking();
            if (x509Certificate != null) {
                if (!x509CRL.getThisUpdate().before(x509Certificate.getNotAfter())) continue;
                hashSet2.add((Object)x509CRL);
                continue;
            }
            hashSet2.add((Object)x509CRL);
        }
        return hashSet2;
    }
}

