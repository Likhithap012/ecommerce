package com.learning.payment.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "payment")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Payment {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  private BigDecimal amount;

  @Enumerated(EnumType.STRING)
  @Column(length = 50, nullable = false)
  private PaymentMethod paymentMethod;

  @Column(unique = true, nullable = false)
  private Integer orderId;

  @Column(nullable = false)
  private Integer customerId;

  @CreatedDate
  @Column(updatable = false)
  private LocalDateTime createdDate;

  @LastModifiedDate
  @Column(insertable = false)
  private LocalDateTime lastModifiedDate;
}
