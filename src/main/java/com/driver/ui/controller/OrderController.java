package com.driver.ui.controller;

import java.util.ArrayList;
import java.util.List;

import com.driver.model.request.OrderDetailsRequestModel;
import com.driver.model.response.OperationStatusModel;
import com.driver.model.response.OrderDetailsResponse;
import com.driver.model.response.RequestOperationName;
import com.driver.model.response.RequestOperationStatus;
import com.driver.service.OrderService;
import com.driver.shared.dto.OrderDto;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
public class OrderController {

	@Autowired
    private OrderService orderService;

	@GetMapping(path="/{id}")
	public OrderDetailsResponse getOrder(@PathVariable String id) throws Exception{

		OrderDto orderDto = orderService.getOrderById(id);
		OrderDetailsResponse returnValue = new OrderDetailsResponse();
		BeanUtils.copyProperties(orderDto, returnValue);
		return returnValue;
	}
	
	@PostMapping()
	public OrderDetailsResponse createOrder(@RequestBody OrderDetailsRequestModel order) {

		OrderDto orderDto = new OrderDto();
		BeanUtils.copyProperties(order, orderDto);
		OrderDto createdOrder = orderService.createOrder(orderDto);
		OrderDetailsResponse returnValue = new OrderDetailsResponse();
		BeanUtils.copyProperties(createdOrder, returnValue);
		return returnValue;
	}
		
	@PutMapping(path="/{id}")
	public OrderDetailsResponse updateOrder(@PathVariable String id, @RequestBody OrderDetailsRequestModel order) throws Exception{

		OrderDto orderDto = new OrderDto();
		BeanUtils.copyProperties(order, orderDto);
		OrderDto updatedOrder = orderService.updateOrderDetails(id, orderDto);
		OrderDetailsResponse returnValue = new OrderDetailsResponse();
		BeanUtils.copyProperties(updatedOrder, returnValue);
		return returnValue;
	}
	
	@DeleteMapping(path = "/{id}")
	public OperationStatusModel deleteOrder(@PathVariable String id) throws Exception {

		orderService.deleteOrder(id);
		OperationStatusModel returnValue = new OperationStatusModel();
		returnValue.setOperationName(RequestOperationName.DELETE.name());
		returnValue.setOperationResult(RequestOperationStatus.SUCCESS.name());
		return returnValue;
	}
	
	@GetMapping()
	public List<OrderDetailsResponse> getOrders() {

		List<OrderDto> orderDtos = orderService.getOrders();
		List<OrderDetailsResponse> returnValue = new ArrayList<>();
		for (OrderDto orderDto : orderDtos) {
			OrderDetailsResponse response = new OrderDetailsResponse();
			BeanUtils.copyProperties(orderDto, response);
			returnValue.add(response);
		}
		return returnValue;
	}
}
