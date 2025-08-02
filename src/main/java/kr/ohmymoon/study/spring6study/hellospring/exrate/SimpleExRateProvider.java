package kr.ohmymoon.study.spring6study.hellospring.exrate;

import kr.ohmymoon.study.spring6study.hellospring.payment.ExRateProvider;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.math.BigDecimal;

@Component
public class SimpleExRateProvider implements ExRateProvider {
    @Override
    public BigDecimal getExRate(String currency) {
        if (currency.equals("USD")) return BigDecimal.valueOf(1000);

        throw new IllegalArgumentException("지원되지 않는 통화입니다");
    }
}
