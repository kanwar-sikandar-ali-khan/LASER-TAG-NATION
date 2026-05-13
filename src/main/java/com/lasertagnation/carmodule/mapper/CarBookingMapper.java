package com.lasertagnation.carmodule.mapper;

import com.lasertagnation.carmodule.dto.CarBookingDto;
import com.lasertagnation.carmodule.dto.UserSummaryDto;
import com.lasertagnation.carmodule.entity.Booking;
import com.lasertagnation.model.User;

public final class CarBookingMapper {

    private CarBookingMapper() {
    }

    /**
     * Maps a managed {@link Booking}; {@code user} must be initialized before leaving a transaction (service responsibility).
     */
    public static CarBookingDto toDto(Booking booking) {
        if (booking == null) {
            return null;
        }
        User user = booking.getUser();
        UserSummaryDto userDto = UserSummaryDto.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .build();
        return CarBookingDto.builder()
                .id(booking.getId())
                .description(booking.getDescription())
                .startDate(booking.getStartDate())
                .endDate(booking.getEndDate())
                .user(userDto)
                .build();
    }
}
