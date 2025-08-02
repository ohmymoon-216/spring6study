package kr.ohmymoon.study.spring6study.hellospring.payment;

import kr.ohmymoon.study.spring6study.hellospring.TestPaymentConfig;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.Clock;
import java.time.LocalDateTime;

import static java.math.BigDecimal.valueOf;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestPaymentConfig.class)
class PaymentServiceSpringTest {

    @Autowired
    PaymentService paymentService;

    @Autowired
    Clock clock;

    @Test
    @DisplayName("Prepare 메소드가 요구사항 3가지를 충족했는지 검증")
    void prepare() throws IOException {

        Payment payment = paymentService.prepare(1L, "USD", BigDecimal.TEN);

        // 환육정보를 가져온다.
        Assertions.assertThat(payment.getExRate()).isEqualByComparingTo(BigDecimal.valueOf(1000));

        // 원화 환산금액 계산
        Assertions.assertThat(payment.getConvertedAmount())
                .isEqualByComparingTo(BigDecimal.TEN.multiply(BigDecimal.valueOf(1000)));

        // 유효시간이 30분 이내인지 확인
        Assertions.assertThat(payment.getValidUntil()).isBetween(LocalDateTime.now(), LocalDateTime.now().plusMinutes(30));
    }

    @Test
    void validUntil() throws IOException {

        Payment payment = paymentService.prepare(1L, "USD", BigDecimal.TEN);

        LocalDateTime now = LocalDateTime.now(this.clock);
        LocalDateTime expectedValidUntil = now.plusMinutes(30);

        assertThat(payment.getValidUntil()).isEqualTo(expectedValidUntil);
    }
}
