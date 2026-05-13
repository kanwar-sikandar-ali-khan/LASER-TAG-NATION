package com.lasertagnation.yachtmodule.service.impl;

import com.lasertagnation.yachtmodule.entity.Yacht;
import com.lasertagnation.yachtmodule.repository.YachtRepository;
import com.lasertagnation.yachtmodule.service.YachtService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class YachtServiceImpl implements YachtService {

    private final YachtRepository yachtRepository;

    public YachtServiceImpl(YachtRepository yachtRepository) {
        this.yachtRepository = yachtRepository;
    }

    /**
     * N+1 lab: a controller that loops yachts and calls {@code getUser()} / {@code getFoods()} issues extra SQL per
     * iteration — no join fetch by design.
     */
    @Override
    public List<Yacht> findAll() {
        return yachtRepository.findAll();
    }

    /**
     * Pagination inefficiency: Spring Data executes a {@code COUNT(*)} query plus the page query; with LAZY
     * collections and associations, rendering this page as JSON often multiplies queries further (per element).
     */
    @Override
    public Page<Yacht> findAll(Pageable pageable) {
        return yachtRepository.findAll(pageable);
    }

    /**
     * Duplicate-SQL smell: naive controllers often call {@code findById} multiple times for the same id across
     * layers — each call is a round-trip (see {@link #findByIdTwiceForDemo(Long)}).
     */
    @Override
    public Yacht findById(Long id) {
        return yachtRepository.findById(id).orElse(null);
    }

    @Transactional
    @Override
    public Yacht save(Yacht yacht) {
        return yachtRepository.save(yacht);
    }

    /**
     * Cascade lab: deleting a yacht that is still referenced from {@code users.yacht_id} may throw constraint
     * violations, or if you delete from the user side with {@code CascadeType.ALL}, you may delete the yacht
     * unintentionally — depends on operation order and ownership.
     */
    @Transactional
    @Override
    public void deleteById(Long id) {
        yachtRepository.deleteById(id);
    }

    @Override
    public Yacht getYachtBare(Long id) {
        return yachtRepository.findById(id).orElse(null);
    }

    /** Explicit duplicate-select demo for logging exercises. */
    @Transactional
    @Override
    public Yacht findByIdTwiceForDemo(Long id) {
        yachtRepository.findById(id);
        return yachtRepository.findById(id).orElse(null);
    }
}
