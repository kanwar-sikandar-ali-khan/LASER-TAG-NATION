package com.lasertagnation.carmodule.pagination;

/**
 * <h2>Why {@code JOIN FETCH} + {@code Pageable} on collections is unsafe</h2>
 * <p>
 * When you join-fetch a collection (e.g. {@code car.foods}) and apply SQL-level pagination ({@code LIMIT/OFFSET}),
 * the database paginates <strong>joined rows</strong>, not logical parent rows. One {@code Car} with many {@code Food}
 * rows multiplies result tuples: page size 10 can return 3 cars, or the same car repeated, and total counts become
 * meaningless relative to cars. Hibernate may also de-duplicate roots, but the page window is still wrong.
 * <p>
 * <strong>Correct approaches</strong> (implemented elsewhere in CarModule):
 * <ul>
 *     <li>DTO / interface projections without collection join for the page query</li>
 *     <li>Two-step: page stable ids first, then {@code IN (:ids)} with controlled fetch</li>
 *     <li>{@code EntityGraph} only where the graph does not multiply rows (e.g. single {@code ManyToOne})</li>
 * </ul>
 * <p>
 * This type exists only as documentation — no runtime behavior.
 */
public final class CarModulePaginationAntiPatterns {

    private CarModulePaginationAntiPatterns() {
    }
}
