package com.driver.service.impl;

import com.driver.io.entity.OrderEntity;
import com.driver.io.repository.OrderRepository;
import com.driver.service.OrderService;
import com.driver.shared.dto.OrderDto;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderRepository orderRepository;

    @Override
    public OrderDto createOrder(OrderDto orderDto) {
        OrderEntity orderEntity = new OrderEntity();
        BeanUtils.copyProperties(orderDto, orderEntity);

        OrderEntity savedOrder = orderRepository.save(orderEntity);
        OrderDto savedOrderDto = new OrderDto();
        BeanUtils.copyProperties(savedOrder, savedOrderDto);

        return savedOrderDto;
    }

    @Override
    public OrderDto getOrderById(String orderId) throws Exception {
        OrderEntity orderEntity = orderRepository.findByOrderId(orderId);
        if (orderEntity == null) {
            throw new Exception("Order not found");
        }
        OrderDto orderDto = new OrderDto();
        BeanUtils.copyProperties(orderEntity, orderDto);

        return orderDto;
    }

    @Override
    public OrderDto updateOrderDetails(String orderId, OrderDto order) throws Exception {
        OrderEntity orderEntity = orderRepository.findByOrderId(orderId);
        if (orderEntity == null) {
            throw new Exception("Order not found");
        }

        // Update the properties of orderEntity using order

        OrderEntity updatedOrderEntity = orderRepository.save(orderEntity);
        OrderDto updatedOrderDto = new OrderDto();
        BeanUtils.copyProperties(updatedOrderEntity, updatedOrderDto);

        return updatedOrderDto;
    }

    @Override
    public void deleteOrder(String orderId) throws Exception {
        OrderEntity orderEntity = orderRepository.findByOrderId(orderId);
        if (orderEntity == null) {
            throw new Exception("Order not found");
        }
        orderRepository.delete(orderEntity);
    }

    @Override
    public List<OrderDto> getOrders() {
        Iterable<OrderEntity> orderEntities = orderRepository.findAll();
        List<OrderDto> orderDtos = new ArrayList<>();

        for (OrderEntity entity : orderEntities) {
            OrderDto orderDto = new OrderDto();
            BeanUtils.copyProperties(entity, orderDto);
            orderDtos.add(orderDto);
        }

        return orderDtos;
    }
}
