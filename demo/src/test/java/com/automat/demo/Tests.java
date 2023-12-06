package com.automat.demo;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.hamcrest.Matchers.comparesEqualTo;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;


import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class Tests {
    
    @Autowired
    private MockMvc mockMvc;
   
    @Test
    public void testPercentage() throws Exception{
    this.mockMvc.perform(get("/percent")).andDo(print()).andExpect(status().isOk())
				.andExpect(content().string(comparesEqualTo("1.0")));             
    }

}
