package com.lasertagnation.carmodule.repository;

import com.lasertagnation.carmodule.entity.Car;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * Car persistence.
 * <p>
 * {@code JOIN FETCH} queries eagerly load associations in the same round-trip (good for eliminating N+1 when you
 * always need the association), but see {@link com.lasertagnation.carmodule.pagination.CarModulePaginationAntiPatterns}
 * before combining fetch joins on collections with {@link Pageable}.
 */
@Repository
public interface CarRepository extends JpaRepository<Car, Long> {

    @Query("select c from CarRoot c join fetch c.foods where c.id = :id")
    Optional<Car> findWithFoodsJoinFetchById(@Param("id") Long id);

    @EntityGraph(attributePaths = {"foods"})
    @Query("select c from CarRoot c where c.id = :id")
    Optional<Car> findWithFoodsEntityGraphById(@Param("id") Long id);

    /**
     * Step 1 (two-step pagination): stable, duplicate-free page of ids — no collection join, so the SQL row count
     * matches the page size.
     */
    @Query("select c.id from CarRoot c")
    Page<Long> findAllIds(Pageable pageable);

    /**
     * Step 2: hydrate cars and foods for a bounded {@code IN (:ids)} list (batch-friendly; order restored in service).
     * <p>
     * {@code distinct} avoids duplicate roots when the join multiplies rows in memory (here mostly defensive).
     */
    @Query("select distinct c from CarRoot c left join fetch c.foods where c.id in :ids")
    List<Car> findAllWithFoodsByIdIn(@Param("ids") Collection<Long> ids);

    /**
     * DTO projection pagination: Spring Data resolves the projection interface without returning managed entities,
     * so there is no lazy-proxy serialization risk and no collection join — correct totals for simple aggregates.
     */
    Page<CarSummaryProjection> findAllProjectedBy(Pageable pageable);

    interface CarSummaryProjection {
        Long getId();

        String getLicensePlate();

        String getModel();
    }
}
