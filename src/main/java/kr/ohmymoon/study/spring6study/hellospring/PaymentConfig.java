package kr.ohmymoon.study.spring6study.hellospring;

import kr.ohmymoon.study.spring6study.hellospring.api.ApiTemplate;
import kr.ohmymoon.study.spring6study.hellospring.api.ErApiExRateExtractor;
import kr.ohmymoon.study.spring6study.hellospring.api.HttpClientApiExecutor;
import kr.ohmymoon.study.spring6study.hellospring.exrate.RestTemplateExRateProvider;
import kr.ohmymoon.study.spring6study.hellospring.exrate.SimpleExRateProvider;
import kr.ohmymoon.study.spring6study.hellospring.exrate.WebApiExRateProvider;
import kr.ohmymoon.study.spring6study.hellospring.payment.ExRateProvider;
import kr.ohmymoon.study.spring6study.hellospring.payment.PaymentService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.time.Clock;

@Configuration
public class PaymentConfig {

    @Bean
    public PaymentService paymentService() {
        return new PaymentService(exRateProvider(), clock());
    }


    @Bean
    public Clock clock() {
        return Clock.systemDefaultZone();
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    ApiTemplate apiTemplate() {
        return new ApiTemplate(new HttpClientApiExecutor(), new ErApiExRateExtractor());
    }

    @Bean
    public ExRateProvider exRateProvider() {
        return new RestTemplateExRateProvider(restTemplate());
    }

}
