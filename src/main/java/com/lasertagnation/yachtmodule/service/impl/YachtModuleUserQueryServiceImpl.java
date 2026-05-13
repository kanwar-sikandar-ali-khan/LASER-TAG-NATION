package com.lasertagnation.yachtmodule.service.impl;

import com.lasertagnation.model.User;
import com.lasertagnation.repository.UserRepository;
import com.lasertagnation.yachtmodule.service.YachtModuleUserQueryService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class YachtModuleUserQueryServiceImpl implements YachtModuleUserQueryService {

    private final UserRepository userRepository;

    public YachtModuleUserQueryServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * No {@code @Transactional}: the returned {@link User} instances are detached for lazy fields; however, roles are
     * {@code EAGER} on {@link User} so the first level may still load a lot of data — then {@code bookings}/{@code yacht}
     * remain lazy → {@code LazyInitializationException} if the web layer touches them without a session.
     */
    @Override
    public List<User> findAllUsersAsEntities() {
        return userRepository.findAll();
    }

    /**
     * Same anti-patterns as {@link #findAllUsersAsEntities()} but with {@link Page} — observe duplicate count queries
     * when Spring Data builds {@code Page} metadata and the content triggers lazy loads per row.
     */
    @Override
    public Page<User> findAllUsersPaged(Pageable pageable) {
        return userRepository.findAll(pageable);
    }
}
