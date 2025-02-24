package com.codewithyash.orderservice.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//
//@SpringBootTest
//@AutoConfigureMockMvc
//public class OrderControllerTest {
//
//        @Autowired
//        private MockMvc mockMvc;
//
//        @Test
//        void contextLoads() throws Exception {
//
//            MvcResult result = mockMvc.perform(get("/api/v1/orders"))
//                    .andExpect(status().isOk())
//                    .andReturn();
//
//            assertEquals(200, result.getModelAndView().getModel().size());
//        }
//}
