package com.lasertagnation.yachtmodule.service;

import com.lasertagnation.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Intentionally “wrong” read APIs over {@link User} for Hibernate/Jackson labs (raw entities, no DTOs).
 */
public interface YachtModuleUserQueryService {

    /**
     * Returns persistent {@link User} entities with lazy associations untouched in the service layer.
     * <p>
     * N+1: if the consumer touches {@code yacht} or {@code bookings} per user, you get one query per association
     * touch pattern.
     * <p>
     * JSON recursion: {@code user → bookings → user → …} can blow the stack when serialized as-is.
     */
    List<User> findAllUsersAsEntities();

    /**
     * Bad pagination: {@code Page<User>} without fetch joins or DTOs — typical total-count query plus page query, then
     * per-row lazy loads during serialization → very chatty SQL and poor scaling.
     */
    Page<User> findAllUsersPaged(Pageable pageable);
}
