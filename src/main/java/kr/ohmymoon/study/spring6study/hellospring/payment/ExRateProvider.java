package kr.ohmymoon.study.spring6study.hellospring.payment;

import java.io.IOException;
import java.math.BigDecimal;

public interface ExRateProvider {
    BigDecimal getExRate(String currency);
}
