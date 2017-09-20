package com.packt.spring5.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.annotation.Resource;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.packt.spring5.dto.Stock;

@SpringJUnitWebConfig
@SpringBootTest
class StockDataAwareControllerWebAppContextTest {

    @Autowired
    private WebApplicationContext wac;

    @Resource
    private ObjectMapper objectMapper;

    private MockMvc mockMvc;

    @Autowired
    private StockDataAwareController stockDataAwareController;

    @BeforeEach
    void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @Test
    void shouldGetStockById() throws Exception {
        String stockId = "NASDAQ:GOOG";
        mockMvc.perform(get("/stock/{id}", stockId)).andExpect(status().isOk());
    }

    @Test
    void shouldCreateStock() throws Exception {
        String stockId = "NASDAQ:GOOG";
        Stock stock = new Stock();
        stock.setId("NASDAQ:FB");
        stock.setName("Facebook Inc.");
        stock.setPrice(135.5);

        String stockAsJson = objectMapper.writeValueAsString(stock);
        mockMvc.perform(put("/stock/{id}", stockId)
                .contentType(APPLICATION_JSON_UTF8)
                .content(stockAsJson))
                .andExpect(status().isOk());
    }
}