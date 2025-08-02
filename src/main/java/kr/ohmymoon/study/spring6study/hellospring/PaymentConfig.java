package kr.ohmymoon.study.spring6study.hellospring;

import kr.ohmymoon.study.spring6study.hellospring.exrate.SimpleExRateProvider;
import kr.ohmymoon.study.spring6study.hellospring.exrate.WebApiExRateProvider;
import kr.ohmymoon.study.spring6study.hellospring.payment.ExRateProvider;
import kr.ohmymoon.study.spring6study.hellospring.payment.PaymentService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Clock;

@Configuration
public class PaymentConfig {

    @Bean
    public PaymentService paymentService() {
        return new PaymentService(exRateProvider(), clock());
    }

    @Bean
    public ExRateProvider exRateProvider() {
        return new WebApiExRateProvider();
    }

    @Bean
    public Clock clock() {
        return Clock.systemDefaultZone();
    }
}
