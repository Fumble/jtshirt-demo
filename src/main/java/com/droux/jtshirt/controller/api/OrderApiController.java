package com.droux.jtshirt.controller.api;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.droux.jtshirt.controller.exception.OrderNotFoundException;
import com.droux.jtshirt.data.bean.OrderItem;
import com.droux.jtshirt.data.bean.OrderRequest;
import com.droux.jtshirt.data.bean.OrderRequestItem;
import com.droux.jtshirt.data.bean.OrderResponse;
import com.droux.jtshirt.data.bean.OrderResponseItem;
import com.droux.jtshirt.data.bean.Orders;
import com.droux.jtshirt.data.bean.Tshirt;
import com.droux.jtshirt.data.repository.OrderRepository;
import com.droux.jtshirt.data.repository.TshirtRepository;

@Controller
@RequestMapping(path="/api/orders")
public class OrderApiController {
    private OrderRepository orderRepository;
    private TshirtRepository tshirtRepository;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private static final String TSHIRT_NOT_FOUND = "TSHIRT_NOT_FOUND";
    private static final String TSHIRT_NO_STOCK = "TSHIRT_NO_STOCK";
    private static final String OK = "OK";
    private static final String KO = "KO";

    @GetMapping(value="/{id}")
    public @ResponseBody Orders getOrder(@PathVariable Long id) {
        Orders order = orderRepository.findOne(id);
        if(order == null) {
            throw new OrderNotFoundException("Order " + id + " not found");
        }
        return order;
    }

    @PostMapping(path = "/place", consumes = "application/json")
    public @ResponseBody
    OrderResponse placeOrder(@RequestBody OrderRequest request) {
        logger.info("Placing an order by {} on {}", request.getClientName()
                , request.getOrderDate());
        return performOrder(request);
    }

    private OrderResponse performOrder(OrderRequest request) {
        OrderResponse response = new OrderResponse();
        List<OrderResponseItem> responseItems = new ArrayList<>();
        String orderStatus = OK;

        Orders order = new Orders();
        order.setTotalAmount(BigDecimal.ZERO);
        order.setClientName(request.getClientName());
        order.setOrderDate(request.getOrderDate());
        List<OrderItem> items = new ArrayList<>();

        List<Tshirt> tshirts = new ArrayList<>();

        for(OrderRequestItem ori: request.getItems()) {
            Tshirt tshirt = tshirtRepository.findOne(ori.getId());
            OrderResponseItem responseItem = new OrderResponseItem(ori);
            if(tshirt == null) {
                orderStatus = KO;
                responseItem.setStatus(TSHIRT_NOT_FOUND);
                responseItem.setPrice(BigDecimal.ZERO);
                responseItem.setQuantity(-1);
                responseItems.add(responseItem);
                continue;
            } else if(tshirt.getQuantity().compareTo(ori.getQuantity()) < 0) {
                orderStatus = KO;
                responseItem.setStatus(TSHIRT_NO_STOCK);
            }
            OrderItem orderItem = new OrderItem(tshirt);
            orderItem.setQuantity(ori.getQuantity());
            responseItem.setPrice(tshirt.getPrice());
            BigDecimal price = tshirt.getPrice().multiply(new BigDecimal(orderItem.getQuantity()));
            order.setTotalAmount(order.getTotalAmount().add(price));
            items.add(orderItem);
            order.addItem(orderItem);
            responseItems.add(responseItem);
            // The new stock
            tshirt.setQuantity(tshirt.getQuantity() - ori.getQuantity());
            tshirts.add(tshirt);
        }
        // We save the order and update the t-shirts stock only if everything is OK
        if(OK.equals(orderStatus)) {
            orderRepository.save(order);
            tshirtRepository.save(tshirts);
        }
        response.setTotalAmount(order.getTotalAmount());
        response.setStatus(orderStatus);
        response.setItems(responseItems);
        return response;
    }

    public OrderApiController(OrderRepository orderRepository, TshirtRepository tshirtRepository) {
        this.orderRepository = orderRepository;
        this.tshirtRepository = tshirtRepository;
    }
}
