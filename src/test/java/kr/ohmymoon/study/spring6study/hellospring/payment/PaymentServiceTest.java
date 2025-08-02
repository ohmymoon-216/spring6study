package kr.ohmymoon.study.spring6study.hellospring.payment;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.*;

import static java.math.BigDecimal.valueOf;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class PaymentServiceTest {

    Clock clock;

    @BeforeEach
    void beforeEach() {
        this.clock = Clock.fixed(Instant.now(), ZoneId.systemDefault());
    }

    @Test
    void validUntil() throws IOException {
        PaymentService paymentService = new PaymentService(new ExRateProviderStub(valueOf(1_000)), clock);

        Payment payment = paymentService.prepare(1L, "USD", BigDecimal.TEN);

        LocalDateTime now = LocalDateTime.now(this.clock);
        LocalDateTime expectedValidUntil = now.plusMinutes(30);
        
        assertThat(payment.getValidUntil()).isEqualTo(expectedValidUntil);
    }

    @Test
    @DisplayName("Prepare 메소드가 요구사항 3가지를 충족했는지 검증")
    void prepare() throws IOException {

        PaymentService paymentService = new PaymentService(new ExRateProviderStub(valueOf(1000)), this.clock);

        Payment payment = paymentService.prepare(1L, "USD", BigDecimal.TEN);

        // 환육정보를 가져온다.
        assertThat(payment.getExRate()).isEqualByComparingTo(valueOf(1000));

        // 원화 환산금액 계산
        assertThat(payment.getConvertedAmount())
                .isEqualByComparingTo(BigDecimal.TEN.multiply(valueOf(1000)));

        // 유효시간이 30분 이내인지 확인
        assertThat(payment.getValidUntil()).isBetween(LocalDateTime.now(), LocalDateTime.now().plusMinutes(30));
    }
}