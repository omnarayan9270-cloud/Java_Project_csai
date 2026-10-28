package com.pinkstock.inventory.repository;
import com.pinkstock.inventory.model.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
public interface SupplierRepository extends JpaRepository<Supplier,Long> {}
