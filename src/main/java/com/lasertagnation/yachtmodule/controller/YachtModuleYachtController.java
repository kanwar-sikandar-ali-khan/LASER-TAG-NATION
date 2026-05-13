package com.lasertagnation.yachtmodule.controller;

import com.lasertagnation.yachtmodule.entity.Food;
import com.lasertagnation.yachtmodule.entity.Yacht;
import com.lasertagnation.yachtmodule.service.YachtService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@CrossOrigin
@RequestMapping("/api/yacht-module/yachts")
public class YachtModuleYachtController {

    private final YachtService yachtService;

    public YachtModuleYachtController(YachtService yachtService) {
        this.yachtService = yachtService;
    }

    @GetMapping
    public List<Yacht> getAllYachts() {
        return yachtService.findAll();
    }

    @GetMapping("/paged")
    public Page<Yacht> getYachtsPaged(Pageable pageable) {
        return yachtService.findAll(pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Yacht> getById(@PathVariable Long id) {
        Yacht y = yachtService.findById(id);
        return y == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(y);
    }

    /**
     * {@code LazyInitializationException} lab: {@link YachtService#getYachtBare(Long)} leaves no open session, but
     * this handler calls {@code getFoods()} — Hibernate cannot initialize the bag.
     */
    @GetMapping("/{id}/foods")
    public ResponseEntity<Set<Food>> getYachtFoods(@PathVariable Long id) {
        Yacht y = yachtService.getYachtBare(id);
        if (y == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(y.getFoods());
    }

    /**
     * Shows two identical {@code SELECT} patterns for the same id when SQL logging is enabled.
     */
    @GetMapping("/{id}/duplicate-sql-demo")
    public ResponseEntity<Yacht> duplicateSqlDemo(@PathVariable Long id) {
        Yacht y = yachtService.findByIdTwiceForDemo(id);
        return y == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(y);
    }

    @PostMapping
    public Yacht create(@RequestBody Yacht yacht) {
        return yachtService.save(yacht);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Yacht> update(@PathVariable Long id, @RequestBody Yacht yacht) {
        if (yachtService.findById(id) == null) {
            return ResponseEntity.notFound().build();
        }
        yacht.setId(id);
        return ResponseEntity.ok(yachtService.save(yacht));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        yachtService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
