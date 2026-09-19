package com.lasertagnation.yachtmodule.config;

import com.lasertagnation.model.Role;
import com.lasertagnation.model.User;
import com.lasertagnation.repository.RoleRepository;
import com.lasertagnation.repository.UserRepository;
import com.lasertagnation.yachtmodule.entity.Booking;
import com.lasertagnation.yachtmodule.entity.Food;
import com.lasertagnation.yachtmodule.entity.Yacht;
import com.lasertagnation.yachtmodule.repository.BookingRepository;
import com.lasertagnation.yachtmodule.repository.FoodRepository;
import com.lasertagnation.yachtmodule.repository.YachtRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.Collections;
import java.util.HashSet;

/**
 * Seeds {@link Food}, {@link Yacht}, {@link Booking}, and extra {@link User} rows for the YachtModule Hibernate lab.
 * Skips when {@code foods} is non-empty so dev restarts do not duplicate rows.
 */
@Component
@Order(Ordered.LOWEST_PRECEDENCE)
public class YachtModuleSeedRunner implements CommandLineRunner {

    private final FoodRepository foodRepository;
    private final YachtRepository yachtRepository;
    private final UserRepository userRepository;
    private final BookingRepository bookingRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public YachtModuleSeedRunner(
            FoodRepository foodRepository,
            YachtRepository yachtRepository,
            UserRepository userRepository,
            BookingRepository bookingRepository,
            RoleRepository roleRepository,
            BCryptPasswordEncoder passwordEncoder) {
        this.foodRepository = foodRepository;
        this.yachtRepository = yachtRepository;
        this.userRepository = userRepository;
        this.bookingRepository = bookingRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public void run(String... args) {
        if (foodRepository.count() > 0) {
            return;
        }

        //// 1st Phase
//food create
        Food fish = foodRepository.save(Food.builder().name("Grilled fish").category("Main").build());
        Food salad = foodRepository.save(Food.builder().name("House salad").category("Starter").build());
        Food dessert = foodRepository.save(Food.builder().name("Lemon tart").category("Dessert").build());

// yacht create
        Yacht alpha = yachtRepository.save(Yacht.builder().name("Yacht Alpha (lab)").build());
        //use in second phase
        Yacht beta = yachtRepository.save(Yacht.builder().name("Yacht Beta (lab)").build());

//add Above foods in alpha yacht
        alpha.getFoods().add(fish);
        alpha.getFoods().add(salad);
        alpha.getFoods().add(dessert);
        yachtRepository.save(alpha);

//  assosiate Above alpha yacht to user
        User admin = userRepository.findById(1L).orElseThrow(
                () -> new IllegalStateException("YachtModule seed expects admin user id=1 from data.sql"));
        admin.setYacht(alpha);
        //? this line
//         Jo tumne padha — haan, inverse side setUser tab useful hai jab usi flow mein yacht.getUser() use karna ho.
// Lekin uske baad wala yachtRepository.save(...) us rule ka hissa nahi — woh redundant hai.

        alpha.setUser(admin);
        userRepository.save(admin);
        //? this line= redundant hai
        yachtRepository.save(alpha);

//    create booking and associate above user
        bookingRepository.save(
                Booking.builder()
                        .user(admin)
                        .description("Admin demo charter")
                        .startDate(LocalDate.now())
                        .endDate(LocalDate.now().plusDays(1))
                        .build());







                   //// 2nd Phase

//        create new user
        Role userRole = roleRepository.findById(2L).orElseThrow(
                () -> new IllegalStateException("YachtModule seed expects ROLE_USER id=2 from data.sql"));
        User crew = User.builder()
                .name("Yacht Crew Lab")
                .email("yacht-crew-lab@example.com")
                .password(passwordEncoder.encode("crew"))
                .phone("000")
                .cnic("000000")
                .status(true)
                .roles(new HashSet<>(Collections.singletonList(userRole)))
                .build();
        userRepository.save(crew);

//        associate yacht to user
        crew.setYacht(beta);
        //? this line
//         Jo tumne padha — haan, inverse side setUser tab useful hai jab usi flow mein yacht.getUser() use karna ho.
// Lekin uske baad wala yachtRepository.save(...) us rule ka hissa nahi — woh redundant hai.
        beta.setUser(crew);
        userRepository.save(crew);
        //? this line= redundant hai
        yachtRepository.save(beta);

        //    create booking and associate above user

        bookingRepository.save(
                Booking.builder()
                        .user(crew)
                        .description("Crew provisioning run")
                        .startDate(LocalDate.now())
                        .endDate(LocalDate.now().plusDays(3))
                        .build());
    }
}


// Question:
// abhi mene user k andr yachtid save rakh kr 1to1
//  relation bnaya hua ye kaam bhi aise bhi to krskta tha
//   k yacht k andr user id save krta or yebhi 1to1 relation
//    hota konc approch standard h?

// Short answer
// Dono 1:1 hain.
// Standard preference: FK us table pe jahan relation “belong” karti ho (aksar child: Yacht → user_id).
// Decide is se: kaun pehle exist kare, kaun optional ho, kaun delete pe cascade le.