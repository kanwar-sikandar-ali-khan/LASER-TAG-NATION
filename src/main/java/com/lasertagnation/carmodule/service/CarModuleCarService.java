package com.lasertagnation.carmodule.service;

import com.lasertagnation.carmodule.dto.CarBatchLoadRowDto;
import com.lasertagnation.carmodule.dto.CarDetailDto;
import com.lasertagnation.carmodule.dto.CarSummaryDto;
import com.lasertagnation.carmodule.entity.Car;
import com.lasertagnation.carmodule.mapper.CarMapper;
import com.lasertagnation.carmodule.repository.CarRepository;
import com.lasertagnation.exception.RecordNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Transaction boundaries: all lazy access to {@link Car#getFoods()} happens inside {@code @Transactional(readOnly=true)}
 * methods so the persistence context stays open — avoiding {@code LazyInitializationException} after the service returns.
 */
@Service
public class CarModuleCarService {

    private final CarRepository carRepository;

    public CarModuleCarService(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    /**
     * JOIN FETCH: one query with join — good when you need every car row’s foods; watch payload size vs. wide joins.
     */
    @Transactional(readOnly = true)
    public CarDetailDto getByIdWithFoodsJoinFetch(Long id) {
        Car car = carRepository.findWithFoodsJoinFetchById(id)
                .orElseThrow(() -> new RecordNotFoundException("Car not found: " + id));
        return CarMapper.toDetailDto(car);
    }

    /**
     * EntityGraph: declarative fetch plan; similar SQL outcome to join fetch for this graph, still not safe with
     * {@code Pageable} on multiplying collections (see pagination notes class).
     */
    @Transactional(readOnly = true)
    public CarDetailDto getByIdWithFoodsEntityGraph(Long id) {
        Car car = carRepository.findWithFoodsEntityGraphById(id)
                .orElseThrow(() -> new RecordNotFoundException("Car not found: " + id));
        return CarMapper.toDetailDto(car);
    }

    /**
     * DTO projection pagination (preferred for list screens): no entity graph, no collection join — correct totals.
     */
    @Transactional(readOnly = true)
    public Page<CarSummaryDto> findCarsSummaryPage(Pageable pageable) {
        return carRepository.findAllProjectedBy(pageable).map(CarMapper::toSummaryDto);
    }

    /**
     * Two-step pagination: page ids (no duplication), then {@code IN} fetch relations — stable pages under load.
     */
    @Transactional(readOnly = true)
    public Page<CarDetailDto> findCarsDetailPageTwoStep(Pageable pageable) {
        Page<Long> idPage = carRepository.findAllIds(pageable);
        List<Long> ids = idPage.getContent();
        if (ids.isEmpty()) {
            return new PageImpl<>(new ArrayList<>(), pageable, idPage.getTotalElements());
        }
        List<Car> loaded = carRepository.findAllWithFoodsByIdIn(ids);
        Map<Long, Car> byId = loaded.stream().collect(Collectors.toMap(Car::getId, Function.identity()));
        List<CarDetailDto> ordered = ids.stream()
                .map(byId::get)
                .filter(Objects::nonNull)
                .map(CarMapper::toDetailDto)
                .collect(Collectors.toList());
        return new PageImpl<>(ordered, pageable, idPage.getTotalElements());
    }

    /**
     * Demonstrates batch fetching: initial page query loads cars only; touching {@code foods} issues batched selects
     * when {@code @BatchSize} / default batch size is configured — fewer round-trips than naive per-car selects.
     */
    @Transactional(readOnly = true)
    public Page<CarBatchLoadRowDto> findCarsBatchDemoPage(Pageable pageable) {
        Page<Car> page = carRepository.findAll(pageable);
        List<CarBatchLoadRowDto> rows = page.getContent().stream()
                .map(c -> CarMapper.toBatchRowDto(c, c.getFoods().size()))
                .collect(Collectors.toList());
        return new PageImpl<>(rows, pageable, page.getTotalElements());
    }
}
