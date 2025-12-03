package com.lasertagnation.repository;

import com.lasertagnation.model.PressMachineSize;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface PressMachineSizeRepository extends JpaRepository<PressMachineSize,Long> {
    Optional<PressMachineSize> findByPressMachineIdAndPaperSizeIdAndValue(Long id, Long paperSizeId, Integer Value);
}
