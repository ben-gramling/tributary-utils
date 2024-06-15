package com.arbriver.tributaryutils.lib.service;

import org.apache.hc.client5.http.fluent.Executor;
import org.apache.hc.client5.http.fluent.Request;
import org.apache.hc.client5.http.impl.async.CloseableHttpAsyncClient;
import org.apache.hc.client5.http.impl.async.HttpAsyncClientBuilder;
import org.apache.hc.client5.http.impl.async.HttpAsyncClients;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.HttpHost;
import org.springframework.http.client.reactive.ClientHttpConnector;
import org.springframework.http.client.reactive.HttpComponentsClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.cert.X509Certificate;
import java.util.Base64;

public class Test {
    public static void main(String[] args) throws URISyntaxException, IOException {
        String apiUrl = "https://api.zenrows.com/v1/?apikey=9568ac41a6d8c1805d5ee7801f446674c8e87e17&url=https%3A%2F%2Fsbapi.il.sportsbook.fanduel.com%2Fapi%2Fcontent-managed-page%3Fpage%3DSPORT%26eventTypeId%3D1%26_ak%3DFhMFpcPWXMeyZxOx%26timezone%3DAmerica%252FChicago&js_render=true&js_instructions=%255B%257B%2522click%2522%253A%2522.selector%2522%257D%252C%257B%2522wait%2522%253A500%257D%252C%257B%2522fill%2522%253A%255B%2522.input%2522%252C%2522value%2522%255D%257D%252C%257B%2522wait_for%2522%253A%2522.slow_selector%2522%257D%255D&premium_proxy=true&proxy_country=us";
//        String response = Request.get(apiUrl)
//                .execute().returnContent().asString();

        HttpAsyncClientBuilder clientBuilder = HttpAsyncClients.custom();
        CloseableHttpAsyncClient client = clientBuilder.build();
        ClientHttpConnector connector = new HttpComponentsClientHttpConnector(client);
        WebClient webClient = WebClient.builder().clientConnector(connector).build();

        String resp = webClient.get().uri(URI.create(apiUrl)).retrieve().bodyToMono(String.class).block();

        System.out.println(resp);
    }

    private static void ignoreCertWarning() {
        SSLContext ctx = null;
        TrustManager[] trustAllCerts = new X509TrustManager[] { new X509TrustManager() {
            public X509Certificate[] getAcceptedIssuers() {return null;}
            public void checkClientTrusted(X509Certificate[] certs, String authType) {}
            public void checkServerTrusted(X509Certificate[] certs, String authType) {}
        } };

        try {
            ctx = SSLContext.getInstance("SSL");
            ctx.init(null, trustAllCerts, null);
            SSLContext.setDefault(ctx);
        } catch (Exception e) {}
    }
}
