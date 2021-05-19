package com.mslarning.airlinems.repository;

import com.mslarning.airlinems.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AirlineRepository extends JpaRepository<Order, Long> {
}
