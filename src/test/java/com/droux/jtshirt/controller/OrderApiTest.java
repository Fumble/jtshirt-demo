package com.droux.jtshirt.controller;

import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.droux.jtshirt.data.bean.Orders;
import com.droux.jtshirt.data.bean.Tshirt;
import com.droux.jtshirt.data.repository.OrderRepository;
import com.droux.jtshirt.data.repository.TshirtRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = JtshirtDemoApplication.class)
@AutoConfigureMockMvc
@Ignore
public class OrderApiTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private TshirtRepository tshirtRepository;

    private List<Orders> ordersList = new ArrayList<>();
    private List<Tshirt> tshirtsList = new ArrayList<>();
    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));

    @Before
    public void setup() throws Exception {
        orderRepository.deleteAll();

        tshirtRepository.deleteAll();
        this.tshirtsList.add(tshirtRepository.save(new Tshirt(0L,"T1", "S", "Black",
                new BigDecimal(10.98), "default.jpg", 1)));
        this.tshirtsList.add(tshirtRepository.save(new Tshirt(0L,"T2", "M", "Blue",
                new BigDecimal(10), "default.jpg", 2)));
        this.tshirtsList.add(tshirtRepository.save(new Tshirt(0L,"T3", "L", "Pink",
                new BigDecimal(105.99), "default.jpg", 3)));

    }
}
