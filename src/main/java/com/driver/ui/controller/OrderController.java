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
import org.modelmapper.ModelMapper;
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

		OrderDetailsResponse returnValue = new OrderDetailsResponse();
		ModelMapper modelMapper = new ModelMapper();

		OrderDto orderDto = orderService.getOrderById(id);
		returnValue = modelMapper.map(orderDto, OrderDetailsResponse.class);

		return returnValue;
	}
	
	@PostMapping()
	public OrderDetailsResponse createOrder(@RequestBody OrderDetailsRequestModel order) {

		OrderDetailsResponse returnValue = new OrderDetailsResponse();
		ModelMapper modelMapper = new ModelMapper();

		OrderDto orderDto = new OrderDto();
		BeanUtils.copyProperties(order, orderDto);

		OrderDto createdOrder = orderService.createOrder(orderDto);
		returnValue = modelMapper.map(createdOrder, OrderDetailsResponse.class);

		return returnValue;
	}
		
	@PutMapping(path="/{id}")
	public OrderDetailsResponse updateOrder(@PathVariable String id, @RequestBody OrderDetailsRequestModel order) throws Exception{

		OrderDetailsResponse returnValue = new OrderDetailsResponse();
		ModelMapper modelMapper = new ModelMapper();

		OrderDto orderDto = new OrderDto();
		orderDto = modelMapper.map(order, OrderDto.class);

		OrderDto updatedOrder = orderService.updateOrderDetails(id, orderDto);
		returnValue = modelMapper.map(updatedOrder, OrderDetailsResponse.class);

		return returnValue;
	}
	
	@DeleteMapping(path = "/{id}")
	public OperationStatusModel deleteOrder(@PathVariable String id) throws Exception {

		OperationStatusModel returnValue = new OperationStatusModel();
		returnValue.setOperationName(RequestOperationName.DELETE.name());

		orderService.deleteOrder(id);

		returnValue.setOperationResult(RequestOperationStatus.SUCCESS.name());

		return returnValue;
	}
	
	@GetMapping()
	public List<OrderDetailsResponse> getOrders() {

		List<OrderDetailsResponse> returnValue = new ArrayList<>();

		List<OrderDto> orders = orderService.getOrders();

		for(OrderDto orderDto : orders) {
			OrderDetailsResponse response = new OrderDetailsResponse();
			BeanUtils.copyProperties(orderDto, response);
			returnValue.add(response);
		}

		return returnValue;
	}
}
