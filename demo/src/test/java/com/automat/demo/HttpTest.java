// package com.automat.demo;



// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.beans.factory.annotation.Value;
// import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

// import org.springframework.boot.test.mock.mockito.MockBean;
// import org.springframework.jdbc.core.JdbcTemplate;
// import org.springframework.test.web.servlet.MockMvc;
// import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
// import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
// import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
// import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// import org.junit.jupiter.api.Test;
// import org.junit.runner.RunWith;
// import org.mockito.InjectMocks;
// import org.mockito.Mock;
// import org.mockito.junit.MockitoJUnitRunner;

// import static org.hamcrest.Matchers.comparesEqualTo;
// import static org.mockito.Mockito.verify;
// import static org.mockito.Mockito.when;

// import com.automat.demo.controller.AutofillJob;
// import com.automat.demo.service.TableService;

// @WebMvcTest(AutofillJob.class)

// public class HttpTest {
//     @Value("${custom.tableName}")
//     private String tableName;

//     @Value("${custom.columnName}") // column on which the percentage distribution calculation is applied.
//     private String columnName;
//     @MockBean
//     private TableService tableService;

//     @Autowired
//     private MockMvc mockMvc;

//     @Test
//     void shouldReturnValue() throws Exception{
//         when(tableService.getSumConsumption(tableName, columnName, "Germany", "oil")).thenReturn(1.0);

//         this.mockMvc.perform(get("/sum"))
//         .andDo(print())
//         .andExpect(status().isOk())
//         .andExpect(content().string(comparesEqualTo("1.0")));
//         verify(tableService).getSumConsumption(tableName, columnName, "Germany", "oil");
//     }
//  @Test
    // public void testGetSumConsumption() throws Exception{
    // this.mockMvc.perform(get("/str")).andDo(print()).andExpect(status().isOk())
	// 			.andExpect(content().string(containsString("Skalamotors")));
    // }

// }
