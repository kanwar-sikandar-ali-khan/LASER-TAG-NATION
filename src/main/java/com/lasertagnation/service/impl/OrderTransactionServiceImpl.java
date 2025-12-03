package com.lasertagnation.service.impl;

import com.lasertagnation.dto.OrderTransactionDto;
import com.lasertagnation.exception.RecordNotFoundException;
import com.lasertagnation.model.Ctp;
import com.lasertagnation.model.OrderTransaction;
import com.lasertagnation.repository.CtpRepository;
import com.lasertagnation.repository.OrderRepository;
import com.lasertagnation.repository.OrderTransactionRepository;
import com.lasertagnation.service.OrderTransactionService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderTransactionServiceImpl implements OrderTransactionService {

    private final OrderTransactionRepository orderTransactionRepository;
    private final OrderRepository orderRepository;
    private final CtpRepository ctpRepository;

    public OrderTransactionServiceImpl(OrderTransactionRepository orderTransactionRepository, OrderRepository orderRepository, CtpRepository ctpRepository) {
        this.orderTransactionRepository = orderTransactionRepository;
        this.orderRepository = orderRepository;
        this.ctpRepository = ctpRepository;
    }


    @Override
    @Transactional
    public OrderTransactionDto save(OrderTransactionDto orderTransactionDto) {
        OrderTransaction orderTransaction = toEntity(orderTransactionDto);
        orderTransaction.setStatus(true);

//        Ctp ctp = ctpRepository.fin

        orderTransaction.setAmount(orderTransaction.getQuantity() * orderTransaction.getUnitPrice());

        OrderTransaction savedOrderTransaction = orderTransactionRepository.save(orderTransaction);
        return toDto(savedOrderTransaction);
    }

    @Override
    public List<OrderTransactionDto> getAll() {
        List<OrderTransaction> orderTransactionList = orderTransactionRepository.findAll();
        List<OrderTransactionDto> orderTransactionDtoList = new ArrayList<>();

        for (OrderTransaction orderTransaction : orderTransactionList) {
            OrderTransactionDto orderTransactionDto = toDto(orderTransaction);
            orderTransactionDtoList.add(orderTransactionDto);
        }
        return orderTransactionDtoList;
    }

    @Override
    public List<OrderTransactionDto> searchByPlateDimension(String plateDimension) {
        List<OrderTransaction> orderTransactionList = orderTransactionRepository.findByPlateDimension(plateDimension);
        List<OrderTransactionDto> orderTransactionDtoList = new ArrayList<>();

        for (OrderTransaction orderTransaction : orderTransactionList) {
            OrderTransactionDto orderTransactionDto = toDto(orderTransaction);
            orderTransactionDtoList.add(orderTransactionDto);
        }
        return orderTransactionDtoList;
    }

    @Override
    public OrderTransactionDto findById(Long id) {
        OrderTransaction orderTransaction = orderTransactionRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(String.format("OrderTransaction not found for id => %d", id)));
        return toDto(orderTransaction);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        OrderTransaction orderTransaction = orderTransactionRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(String.format("OrderTransaction not found for id => %d", id)));
        orderTransactionRepository.setStatusInactive(orderTransaction.getId());
    }

    @Override
    @Transactional
    public OrderTransactionDto update(Long id, OrderTransactionDto orderTransactionDto) {
        OrderTransaction existingOrderTransaction = orderTransactionRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(String.format("OrderTransaction not found for id => %d", id)));

        existingOrderTransaction.setPlateDimension(orderTransactionDto.getPlateDimension());
        existingOrderTransaction.setVendor(orderTransactionDto.getVendor());
        existingOrderTransaction.setQuantity(orderTransactionDto.getQuantity());
        existingOrderTransaction.setUnitPrice(orderTransactionDto.getUnitPrice());
        existingOrderTransaction.setAmount(orderTransactionDto.getAmount());
        existingOrderTransaction.setPaymentMode(orderTransactionDto.getPaymentMode());


        OrderTransaction updatedOrderTransaction = orderTransactionRepository.save(existingOrderTransaction);
        return toDto(updatedOrderTransaction);
    }


    public OrderTransactionDto toDto(OrderTransaction orderTransaction) {
        return OrderTransactionDto.builder()
                .id(orderTransaction.getId())
                .plateDimension(orderTransaction.getPlateDimension())
                .vendor(orderTransaction.getVendor())
                .quantity(orderTransaction.getQuantity())
                .unitPrice(orderTransaction.getUnitPrice())
                .amount(orderTransaction.getAmount())
                .paymentMode(orderTransaction.getPaymentMode())
                .status(orderTransaction.getStatus())
                .build();
    }

    public OrderTransaction toEntity(OrderTransactionDto orderTransactionDto) {
        return OrderTransaction.builder()
                .id(orderTransactionDto.getId())
                .plateDimension(orderTransactionDto.getPlateDimension())
                .vendor(orderTransactionDto.getVendor())
                .quantity(orderTransactionDto.getQuantity())
                .unitPrice(orderTransactionDto.getUnitPrice())
                .amount(orderTransactionDto.getAmount())
                .paymentMode(orderTransactionDto.getPaymentMode())
                .status(orderTransactionDto.getStatus())
                .build();
    }

}
