package kr.ohmymoon.study.spring6study.hellospring.exrate;

import com.fasterxml.jackson.core.JsonProcessingException;
import kr.ohmymoon.study.spring6study.hellospring.api.SimpleApiExecutor;
import kr.ohmymoon.study.spring6study.hellospring.payment.ExRateProvider;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.stream.Collectors;
import com.fasterxml.jackson.databind.ObjectMapper;

public class WebApiExRateProvider implements ExRateProvider {

    @Override
    public BigDecimal getExRate(String currency) {
        String url = "https://open.er-api.com/v6/latest/" + currency;

        return runApiForExRate(url);
    }

    private static BigDecimal runApiForExRate(String url) {
        URI uri;
        try {
            uri = new URI(url);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }

        String response;
        try {
            response = new SimpleApiExecutor().execute(uri);
        }catch(IOException e) {
            throw new RuntimeException(e);
        }

        try {
            return extractExRate(response);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private static BigDecimal extractExRate(String response) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Map data = mapper.readValue(response, Map.class);

        if (data.get("result") == null || !data.get("result").toString().equals("success")) {
            throw new RuntimeException("API 요청이 실패했습니다: " + data);
        }

        @SuppressWarnings("unchecked")
        Map<String, Object> rates = (Map<String, Object>) data.get("rates");
        if (rates == null || !rates.containsKey("KRW")) {
            throw new RuntimeException("USD 환율을 찾을 수 없습니다");
        }

        return new BigDecimal(rates.get("USD").toString());
    }

}
