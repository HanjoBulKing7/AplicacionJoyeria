package com.jewelry.managementsystem.repositories;

import com.jewelry.managementsystem.models.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
