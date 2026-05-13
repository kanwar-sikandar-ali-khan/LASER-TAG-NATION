package com.lasertagnation.yachtmodule.service;

import com.lasertagnation.yachtmodule.entity.Yacht;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface YachtService {

    /**
     * Intentionally not wrapped in a service-level transaction: returning entities that later have lazy fields
     * touched in the web layer is a common source of {@code LazyInitializationException}.
     */
    List<Yacht> findAll();

    /**
     * Bad pagination lab: {@link Page} of {@link Yacht} with lazy {@code user} / {@code foods} — serialization or
     * logging can fan out into many extra queries (N+1 per row) with no DTO projection.
     */
    Page<Yacht> findAll(Pageable pageable);

    Yacht findById(Long id);

    Yacht save(Yacht yacht);

    void deleteById(Long id);

    /**
     * Returns a managed/detached {@link Yacht} without forcing initialization — {@code getFoods()} in the controller
     * demonstrates {@code LazyInitializationException} when no session is open.
     */
    Yacht getYachtBare(Long id);

    /** Runs two identical {@code findById} calls so SQL logs show duplicate round-trips. */
    Yacht findByIdTwiceForDemo(Long id);
}
