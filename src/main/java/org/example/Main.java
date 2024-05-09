package org.example;


import com.squareup.okhttp.*;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {

        OkHttpClient client = new OkHttpClient();
        MediaType mediaType = MediaType.parse("application/soap+xml; charset=utf-8");
        RequestBody body = RequestBody.create(mediaType, "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                "<soap12:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap12=\"http://www.w3.org/2003/05/soap-envelope\">\n" +
                "  <soap12:Body>\n" +
                "    <TCKimlikNoDogrula xmlns=\"http://tckimlik.nvi.gov.tr/WS\">\n" +
                "      <TCKimlikNo>12345678911</TCKimlikNo>\n" +
                "      <Ad>Ali</Ad>\n" +
                "      <Soyad>yılmaz</Soyad>\n" +
                "      <DogumYili>1999</DogumYili>\n" +
                "    </TCKimlikNoDogrula>\n" +
                "  </soap12:Body>\n" +
                "</soap12:Envelope>");
        Request request = new Request.Builder()
                .url("https://tckimlik.nvi.gov.tr/Service/KPSPublic.asmx")
                .method("POST", body)
                .addHeader("Content-Type", "application/soap+xml; charset=utf-8")
                .build();
        Response response = client.newCall(request).execute();

        if (response.isSuccessful()) {
            String responseBody = response.body().string();
            if (responseBody.contains("<TCKimlikNoDogrulaResult>true</TCKimlikNoDogrulaResult>")) {
                System.out.println("Kimlik doğrulama başarılı!");
            } else if (responseBody.contains("<TCKimlikNoDogrulaResult>false</TCKimlikNoDogrulaResult>")) {
                System.out.println("Kimlik doğrulama başarısız!");
            }
        }
    }
}