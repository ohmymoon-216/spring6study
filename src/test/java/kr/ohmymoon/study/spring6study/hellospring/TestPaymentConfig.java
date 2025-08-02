package kr.ohmymoon.study.spring6study.hellospring;

import kr.ohmymoon.study.spring6study.hellospring.exrate.CachedExRateProvider;
import kr.ohmymoon.study.spring6study.hellospring.payment.ExRateProvider;
import kr.ohmymoon.study.spring6study.hellospring.payment.ExRateProviderStub;
import kr.ohmymoon.study.spring6study.hellospring.payment.PaymentService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;

@Configuration
public class TestPaymentConfig {
    @Bean
    public PaymentService paymentService() {
        return new PaymentService(cachedExRateProvider(), clock());
    }

    @Bean
    public ExRateProvider cachedExRateProvider() {
        return new CachedExRateProvider(exRateProvider());
    }

    @Bean
    public ExRateProvider exRateProvider() {
        return new ExRateProviderStub(BigDecimal.valueOf(1000));
    }

    @Bean
    public Clock clock() {
        return Clock.fixed(Instant.now(), ZoneId.systemDefault());
    }
}

