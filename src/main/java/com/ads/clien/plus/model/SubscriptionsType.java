package com.ads.clien.plus.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "subscriptions_type")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SubscriptionsType implements java.io.Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "subscriptions_type_id")
    private Long id;
    private String name;
    @Column(name = "access_months")
    private Long accessMonth;
    private BigDecimal price;
    @Column(name = "product_key",unique = true)
    private String productKey;
}
