package com.droux.jtshirt.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.util.ArrayList;
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

import com.droux.jtshirt.data.bean.Tshirt;
import com.droux.jtshirt.data.repository.TshirtRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = JtshirtDemoApplication.class)
@AutoConfigureMockMvc
public class TshirtApiTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TshirtRepository tshirtRepository;

    private List<Tshirt> tshirtsList = new ArrayList<>();
    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));

    @Test
    public void listAll() throws Exception {
        this.mockMvc.perform(get("/api/tshirts"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].id", is(this.tshirtsList.get(0).getId().intValue())))
                .andExpect(jsonPath("$[0].name", is(this.tshirtsList.get(0).getName())))
                .andExpect(jsonPath("$[0].size", is(this.tshirtsList.get(0).getSize())))
                .andExpect(jsonPath("$[0].color", is(this.tshirtsList.get(0).getColor())))
                .andExpect(jsonPath("$[0].price", is(this.tshirtsList.get(0).getPrice().doubleValue())))
                .andExpect(jsonPath("$[0].quantity", is(this.tshirtsList.get(0).getQuantity())))
                .andExpect(jsonPath("$[1].id", is(this.tshirtsList.get(1).getId().intValue())))
                .andExpect(jsonPath("$[1].name", is(this.tshirtsList.get(1).getName())))
                .andExpect(jsonPath("$[1].size", is(this.tshirtsList.get(1).getSize())))
                .andExpect(jsonPath("$[1].color", is(this.tshirtsList.get(1).getColor())))
                .andExpect(jsonPath("$[1].price", is(this.tshirtsList.get(1).getPrice().doubleValue())))
                .andExpect(jsonPath("$[1].quantity", is(this.tshirtsList.get(1).getQuantity())))
                .andExpect(jsonPath("$[2].id", is(this.tshirtsList.get(2).getId().intValue())))
                .andExpect(jsonPath("$[2].name", is(this.tshirtsList.get(2).getName())))
                .andExpect(jsonPath("$[2].size", is(this.tshirtsList.get(2).getSize())))
                .andExpect(jsonPath("$[2].color", is(this.tshirtsList.get(2).getColor())))
                .andExpect(jsonPath("$[2].price", is(this.tshirtsList.get(2).getPrice().doubleValue())))
                .andExpect(jsonPath("$[2].quantity", is(this.tshirtsList.get(2).getQuantity())));
    }

    @Test
    public void getOneTshirt() throws Exception {
        this.mockMvc.perform(get("/api/tshirts/" + tshirtsList.get(0).getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.id", is(this.tshirtsList.get(0).getId().intValue())))
                .andExpect(jsonPath("$.name", is(this.tshirtsList.get(0).getName())))
                .andExpect(jsonPath("$.size", is(this.tshirtsList.get(0).getSize())))
                .andExpect(jsonPath("$.color", is(this.tshirtsList.get(0).getColor())))
                .andExpect(jsonPath("$.price", is(this.tshirtsList.get(0).getPrice().doubleValue())))
                .andExpect(jsonPath("$.quantity", is(this.tshirtsList.get(0).getQuantity())));
    }

    @Test
    public void tshirtNotFound() throws Exception {
        this.mockMvc.perform(get("/api/tshirts/-1"))
                .andExpect(status().isNotFound());
    }

    @Before
    public void setup() throws Exception {
        tshirtRepository.deleteAll();
        this.tshirtsList.add(tshirtRepository.save(new Tshirt(0L,"T1", "S", "Black",
                new BigDecimal(10.98), "default.jpg", 1)));
        this.tshirtsList.add(tshirtRepository.save(new Tshirt(0L,"T2", "M", "Blue",
                new BigDecimal(10), "default.jpg", 2)));
        this.tshirtsList.add(tshirtRepository.save(new Tshirt(0L,"T3", "L", "Pink",
                new BigDecimal(105.99), "default.jpg", 3)));
    }

}
