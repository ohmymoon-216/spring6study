package kr.ohmymoon.study.spring6study.hellospring.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.math.BigDecimal;
import java.util.Map;

public class ErApiExRateExtractor implements ExRateExtractor {
    @Override
    public BigDecimal extract(String response) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Map data = mapper.readValue(response, Map.class);

        if (data.get("result") == null || !data.get("result").toString().equals("success")) {
            throw new RuntimeException("API 요청이 실패했습니다: " + data);
        }

        @SuppressWarnings("unchecked")
        Map<String, Object> rates = (Map<String, Object>) data.get("rates");
        if (rates == null || !rates.containsKey("KRW")) {
            throw new RuntimeException("KRW 환율을 찾을 수 없습니다");
        }

        return new BigDecimal(rates.get("KRW").toString());
    }
}
