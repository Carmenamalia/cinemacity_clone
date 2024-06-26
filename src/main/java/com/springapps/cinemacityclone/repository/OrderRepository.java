package com.springapps.cinemacityclone.repository;

import com.springapps.cinemacityclone.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order,Long> {

    Optional<Order> findById(Long id);
    List<Order> findAll();
}
