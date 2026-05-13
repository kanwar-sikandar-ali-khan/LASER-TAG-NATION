package com.lasertagnation.carmodule.config;

import com.lasertagnation.carmodule.entity.Booking;
import com.lasertagnation.carmodule.entity.Car;
import com.lasertagnation.carmodule.entity.Food;
import com.lasertagnation.carmodule.repository.CarBookingRepository;
import com.lasertagnation.carmodule.repository.CarRepository;
import com.lasertagnation.carmodule.repository.CarFoodRepository;
import com.lasertagnation.model.Role;
import com.lasertagnation.model.User;
import com.lasertagnation.repository.RoleRepository;
import com.lasertagnation.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

/**
 * Seeds CarModule tables ({@code car_foods}, {@code cars}, {@code car_food}, {@code car_bookings}) plus links on
 * {@link User} ({@code car_id}, {@code carBookings}). Runs before {@link com.lasertagnation.yachtmodule.config.YachtModuleSeedRunner}.
 */
@Component
@Order(Ordered.LOWEST_PRECEDENCE - 20)
public class CarModuleSeedRunner implements CommandLineRunner {

    private final CarFoodRepository carFoodRepository;
    private final CarRepository carRepository;
    private final CarBookingRepository carBookingRepository;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public CarModuleSeedRunner(
            CarFoodRepository carFoodRepository,
            CarRepository carRepository,
            CarBookingRepository carBookingRepository,
            UserRepository userRepository,
            RoleRepository roleRepository,
            BCryptPasswordEncoder passwordEncoder) {
        this.carFoodRepository = carFoodRepository;
        this.carRepository = carRepository;
        this.carBookingRepository = carBookingRepository;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public void run(String... args) {
        if (carRepository.count() > 0) {
            return;
        }

        List<Food> foods = new ArrayList<>();
        for (int i = 0; i < 14; i++) {
            foods.add(carFoodRepository.save(Food.builder()
                    .name("CarModule snack " + i)
                    .category(i % 3 == 0 ? "Snack" : i % 3 == 1 ? "Meal" : "Drink")
                    .build()));
        }

        List<Car> cars = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            Car car = Car.builder()
                    .licensePlate("CM-" + (1000 + i))
                    .model("Sedan-" + (i % 4))
                    .build();
            car.getFoods().add(foods.get(i % foods.size()));
            car.getFoods().add(foods.get((i + 3) % foods.size()));
            car.getFoods().add(foods.get((i + 7) % foods.size()));
            cars.add(carRepository.save(car));
        }

        User admin = userRepository.findById(1L).orElseThrow(
                () -> new IllegalStateException("CarModule seed expects admin user id=1 from data.sql"));
        Car adminCar = cars.get(0);
        admin.setCar(adminCar);
        adminCar.setUser(admin);
        for (int i = 0; i < 12; i++) {
            Booking b = Booking.builder()
                    .user(admin)
                    .description("Admin car booking " + i)
                    .startDate(LocalDate.now().minusDays(30 - i))
                    .endDate(LocalDate.now().minusDays(29 - i))
                    .build();
            admin.getCarBookings().add(b);
            carBookingRepository.save(b);
        }
        userRepository.save(admin);
        carRepository.save(adminCar);

        Role userRole = roleRepository.findById(2L).orElseThrow(
                () -> new IllegalStateException("CarModule seed expects ROLE_USER id=2 from data.sql"));

        for (int u = 0; u < 6; u++) {
            User driver = User.builder()
                    .name("CarModule Driver " + u)
                    .email("car-driver-" + u + "@example.com")
                    .password(passwordEncoder.encode("driver"))
                    .phone("010" + u)
                    .cnic("CNIC-" + u)
                    .status(true)
                    .roles(new HashSet<>(Collections.singletonList(userRole)))
                    .build();
            userRepository.save(driver);

            Car assigned = cars.get(1 + u);
            driver.setCar(assigned);
            assigned.setUser(driver);

            for (int b = 0; b < 8; b++) {
                Booking booking = Booking.builder()
                        .user(driver)
                        .description("Driver " + u + " trip " + b)
                        .startDate(LocalDate.now().minusWeeks(b + 1))
                        .endDate(LocalDate.now().minusWeeks(b).plusDays(2))
                        .build();
                driver.getCarBookings().add(booking);
                carBookingRepository.save(booking);
            }
            userRepository.save(driver);
            carRepository.save(assigned);
        }
    }
}
