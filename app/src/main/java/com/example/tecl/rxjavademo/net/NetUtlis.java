package com.example.tecl.rxjavademo.net;

import android.content.Context;
import android.util.Log;

import com.example.tecl.rxjavademo.utlis.SignUtils;
import com.example.tecl.rxjavademo.utlis.TestFun;

import java.io.InputStream;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.security.cert.CertificateFactory;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


public class NetUtlis {

    private static NetUtlis mNetUtlis;

    public NetUtlis () {

    }
    public static NetUtlis getInstance() {
        if (mNetUtlis == null) {
            synchronized (NetUtlis.class) {
                if (mNetUtlis == null) {
                    mNetUtlis = new NetUtlis();
                }
            }
        }
        return mNetUtlis;
    }

    private  Retrofit Config(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://test-api.yimifudao.com/v2.4/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(new OkHttpClient()).build();

        return retrofit;
    }

    public void getUrlAndParams(String _url,HashMap<String,String> params){
        String url = "https://test-api.yimifudao.com/v2.4/"+_url;

        SimpleDateFormat dt= new SimpleDateFormat("yyyyMMddHHmmss");
//        params.put("token", (null==UserInfo.getUser() || null == UserInfo.getUser().getToken() )?"":UserInfo.getUser().getToken());
        params.put("timestamp", dt.format(System.currentTimeMillis()));
        params.put("apiVersion", "2.7");
        try{
            params.put("sign", SignUtils.createSign(params,true));
        }catch (Exception e){}

        String sParams = "?";
        if (params != null & params.size() > 0) {
            int i = 0;
            int len = params.size();
            for (Map.Entry<String, String> entry : params.entrySet()) {
                sParams += entry.getKey() + "=" + entry.getValue();
                i++;
                if (i != len) {
                    sParams += "&";
                }
            }
        }

        Log.e("RequestUrl","网络请求URL："+url+sParams);
//        return url;
    }

    //设置https证书
    protected static SSLSocketFactory  getSSLSocketFactory(Context context, int[] certificates) {

        if (context == null) {
            throw new NullPointerException("context == null");
        }

        //CertificateFactory用来证书生成
        CertificateFactory certificateFactory;
        try {
            certificateFactory = CertificateFactory.getInstance("X.509");
            //Create a KeyStore containing our trusted CAs
            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            keyStore.load(null, null);

            for (int i = 0; i < certificates.length; i++) {
                //读取本地证书
                InputStream is = context.getResources().openRawResource(certificates[i]);
                keyStore.setCertificateEntry(String.valueOf(i), certificateFactory
                        .generateCertificate(is));

                if (is != null) {
                    is.close();
                }
            }

            //Create a TrustManager that trusts the CAs in our keyStore
            TrustManagerFactory trustManagerFactory = TrustManagerFactory
                    .getInstance(TrustManagerFactory.getDefaultAlgorithm());
            trustManagerFactory.init(keyStore);

            //Create an SSLContext that uses our TrustManager
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, trustManagerFactory.getTrustManagers(), new SecureRandom());
            return sslContext.getSocketFactory();

        } catch (Exception e) {

        }
        return null;
    }

}
