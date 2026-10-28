package com.pinkstock.inventory.repository;
import com.pinkstock.inventory.model.Sale;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDateTime;
import java.util.List;
public interface SaleRepository extends JpaRepository<Sale,Long> {
 List<Sale> findAllByOrderBySoldAtDesc();
 List<Sale> findBySoldAtAfter(LocalDateTime time);
 List<Sale> findBySoldAtAfterOrderBySoldAtDesc(LocalDateTime time);
}
