package com.mslarning.hotelms.repository;

import com.mslarning.hotelms.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HotelRepository extends JpaRepository<Order, Long> {
}
