package com.api.orderms.controller;

import com.api.orderms.controller.dto.ApiResonse;
import com.api.orderms.controller.dto.PaginationResponse;
import com.api.orderms.service.OrderService;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {

    private final OrderService orderService;


    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/customers/{customerId}/orders")
    public ResponseEntity<ApiResonse<OrderResponse>> listOrders(@PathVariable("customerId") Long customerId,
                                                    @RequestParam(name = "page", defaultValue = "0") Integer page,
                                                    @RequestParam(name = "size", defaultValue = "10") Integer pageSize){

        var pageResponse = orderService.findAllByCustomerId(customerId, PageRequest.of(page, pageSize));

        return ResponseEntity.ok(new ApiResonse<>(
                pageResponse.getContent(),
                PaginationResponse.fromPage(pageResponse)
        ));

    }
}
