package com.tomatopay.transactionservice;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
class TransactionServiceIT {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testGetTransaction() throws Exception {
        mockMvc
                .perform(
                        MockMvcRequestBuilders
                                .get("http://localhost:8080/api/v1/transactions/ed635219-63b8-4ead-8554-d41f527458ba")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.*", hasSize(6)))
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn().getResponse();
    }

}
