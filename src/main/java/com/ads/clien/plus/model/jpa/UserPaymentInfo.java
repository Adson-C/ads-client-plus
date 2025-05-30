package com.ads.clien.plus.model.jpa;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "user_payment_info")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserPaymentInfo implements java.io.Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_payment_info_id")
    private Long id;
    private String cardNumber;
    @Column(name = "card_expiration_month")
    private Long cardExpirationMonth;
    @Column(name = "card_expiration_year")
    private Long cardExpirationYear;
    @Column(name = "card_security_code")
    private String cardSecurityCode;

    @Column(name = "installments")
    private Long installments;
    private BigDecimal price;
    @Column(name = "dt_payment")
    private LocalDate dtPayment;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

}
