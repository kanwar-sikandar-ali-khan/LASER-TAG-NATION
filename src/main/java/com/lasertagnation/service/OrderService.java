package com.lasertagnation.service;

import com.lasertagnation.dto.OrderDto;
import com.lasertagnation.dto.PressMachineDto;
import com.lasertagnation.model.Order;
import com.lasertagnation.model.User;

import java.util.List;

public interface OrderService
{
    OrderDto save(OrderDto orderDto);
    List<OrderDto> getAll();
    List<OrderDto> searchByProduct(String product);
    OrderDto findById(Long id);
    String deleteById(Long id);
    OrderDto updateOrder(Long id, OrderDto orderDto);
    OrderDto assignOrderToUser(Long orderId, Long userId, String role);
    List<Order> getAssignedOrdersForLoggedInUser();
}


