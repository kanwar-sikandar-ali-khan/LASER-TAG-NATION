package com.lasertagnation.repository;

import com.lasertagnation.model.ProductRulePaperStock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRulePaperStockRepository extends JpaRepository<ProductRulePaperStock, Long> {
}
