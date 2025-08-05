package kr.ohmymoon.study.spring6study.hellospring.exrate;

import kr.ohmymoon.study.spring6study.hellospring.api.*;
import kr.ohmymoon.study.spring6study.hellospring.payment.ExRateProvider;

import java.math.BigDecimal;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class WebApiExRateProvider implements ExRateProvider {

    private final ApiTemplate apiTemplate;

    public WebApiExRateProvider(ApiTemplate apiTemplate) {
        this.apiTemplate = apiTemplate;
    }

    @Override
    public BigDecimal getExRate(String currency) {

        String url = "https://open.er-api.com/v6/latest/" + currency;

        return apiTemplate.getExRate(url);
    }


}
