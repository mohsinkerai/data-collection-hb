package com.mohsinkerai.adminlte.repository;

import com.mohsinkerai.adminlte.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

}
