package cn.com.broadlink.blelight.util.okhttp;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import javax.net.ssl.X509TrustManager;

/* JADX INFO: loaded from: classes.dex */
public final class ETrustManager implements X509TrustManager {
    private static volatile ETrustManager instance;
    private X509Certificate mServerCert = null;

    @Override // javax.net.ssl.X509TrustManager
    public void checkClientTrusted(X509Certificate[] x509CertificateArr, String str) throws CertificateException {
    }

    public static final ETrustManager getInstance() {
        if (instance == null) {
            synchronized (ETrustManager.class) {
                if (instance == null) {
                    instance = new ETrustManager();
                }
            }
        }
        return instance;
    }

    private ETrustManager() {
    }

    public void setServerCert(X509Certificate x509Certificate) {
        this.mServerCert = x509Certificate;
    }

    public X509Certificate getServerCert() {
        return this.mServerCert;
    }

    @Override // javax.net.ssl.X509TrustManager
    public void checkServerTrusted(X509Certificate[] x509CertificateArr, String str) throws CertificateException {
        if (x509CertificateArr == null || x509CertificateArr.length == 0) {
            throw new IllegalArgumentException("parameter is not used");
        }
        if (str == null || str.length() == 0) {
            throw new IllegalArgumentException("parameter is not used");
        }
        try {
            if (this.mServerCert == null) {
                x509CertificateArr[0].checkValidity();
                return;
            }
            for (X509Certificate x509Certificate : x509CertificateArr) {
                x509Certificate.checkValidity();
                x509Certificate.verify(this.mServerCert.getPublicKey());
            }
        } catch (Exception unused) {
            throw new CertificateException("Certificate not valid or trusted.");
        }
    }

    @Override // javax.net.ssl.X509TrustManager
    public X509Certificate[] getAcceptedIssuers() {
        return new X509Certificate[0];
    }
}
