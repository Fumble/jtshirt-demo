package com.droux.jtshirt.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.droux.jtshirt.data.bean.OrderRequest;
import com.droux.jtshirt.data.bean.OrderRequestItem;
import com.droux.jtshirt.data.bean.Tshirt;
import com.droux.jtshirt.data.repository.OrderRepository;
import com.droux.jtshirt.data.repository.TshirtRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = JtshirtDemoApplication.class)
@AutoConfigureMockMvc
public class OrderApiTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private TshirtRepository tshirtRepository;

    private List<Tshirt> tshirtsList = new ArrayList<>();
    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));

    @Test
    public void getOrderNotFound() throws Exception {
        mockMvc.perform(get("/api/orders/{id}", 0L))
                .andExpect(status().isNotFound());
    }

    @Test
    public void createOneOrder() throws Exception{
        List<OrderRequestItem> items = new ArrayList<>();
        items.add(new OrderRequestItem(tshirtsList.get(0).getId(), 5));
        items.add(new OrderRequestItem(tshirtsList.get(1).getId(), 2));
        OrderRequest request = new OrderRequest(new Date(), "John", items);

        ObjectMapper objectMapper = new ObjectMapper();
        byte[] requestJson =  objectMapper.writeValueAsBytes(request);
        mockMvc.perform(post("/api/orders/place")
                .contentType(contentType)
                .content(requestJson))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.totalAmount", is(74.90)))
                .andExpect(jsonPath("$.status", is("OK")))
                .andExpect(jsonPath("$.items", hasSize(2)))
                .andExpect(jsonPath("$.items[0].id", is(tshirtsList.get(0).getId().intValue())))
                .andExpect(jsonPath("$.items[0].quantity", is(5)))
                .andExpect(jsonPath("$.items[0].price", is(tshirtsList.get(0).getPrice().doubleValue())))
                .andExpect(jsonPath("$.items[0].status", is("OK")))
                .andExpect(jsonPath("$.items[1].id", is(tshirtsList.get(1).getId().intValue())))
                .andExpect(jsonPath("$.items[1].quantity", is(2)))
                .andExpect(jsonPath("$.items[1].price", is(tshirtsList.get(1).getPrice().doubleValue())))
                .andExpect(jsonPath("$.items[1].status", is("OK")));
    }

    @Test
    public void createOneBadOrder() throws Exception{
        List<OrderRequestItem> items = new ArrayList<>();
        items.add(new OrderRequestItem(0L, 5));
        items.add(new OrderRequestItem(tshirtsList.get(1).getId(), 25));
        OrderRequest request = new OrderRequest(new Date(), "John", items);

        ObjectMapper objectMapper = new ObjectMapper();
        byte[] requestJson =  objectMapper.writeValueAsBytes(request);
        mockMvc.perform(post("/api/orders/place")
                .contentType(contentType)
                .content(requestJson))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.status", is("KO")))
                .andExpect(jsonPath("$.items", hasSize(2)))
                .andExpect(jsonPath("$.items[0].id", is(0)))
                .andExpect(jsonPath("$.items[0].quantity", is(-1)))
                .andExpect(jsonPath("$.items[0].status", is("TSHIRT_NOT_FOUND")))
                .andExpect(jsonPath("$.items[1].id", is(tshirtsList.get(1).getId().intValue())))
                .andExpect(jsonPath("$.items[1].quantity", is(25)))
                .andExpect(jsonPath("$.items[1].status", is("TSHIRT_NO_STOCK")));
    }

    @Before
    public void setup() throws Exception {
        orderRepository.deleteAll();

        tshirtRepository.deleteAll();
        this.tshirtsList.add(tshirtRepository.save(new Tshirt(0L,"T1", "S", "Black",
                new BigDecimal(10.98), "default.jpg", 10)));
        this.tshirtsList.add(tshirtRepository.save(new Tshirt(0L,"T2", "M", "Blue",
                new BigDecimal(10), "default.jpg", 5)));
        this.tshirtsList.add(tshirtRepository.save(new Tshirt(0L,"T3", "L", "Pink",
                new BigDecimal(105.99), "default.jpg", 3)));
    }
}
