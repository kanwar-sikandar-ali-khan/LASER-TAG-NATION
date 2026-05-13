package com.lasertagnation.repository;

import com.lasertagnation.model.NewProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NewProductRepository extends JpaRepository<NewProduct, Long> {
}
