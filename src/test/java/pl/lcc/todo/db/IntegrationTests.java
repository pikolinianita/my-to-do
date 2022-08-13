/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package pl.lcc.todo.db;

import org.assertj.core.api.SoftAssertions;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 *
 * @author piko
 */
@SpringBootTest
@AutoConfigureMockMvc
public class IntegrationTests {
   
  @Autowired
  private MockMvc mockMvc;

 // @Autowired
 // private ObjectMapper objectMapper;

 // @MockBean
 // private RegisterUseCase registerUseCase;
    
    public IntegrationTests() {
    }
    
    @Test
    public void name() throws Exception {
      mockMvc.perform(post("/forums/42/register")
    .contentType("application/json"))
    .andExpect(status().isOk());
    }
    
    @Test
    public void name2() throws Exception {
      mockMvc.perform(post("/forums/42/register")
    .contentType("application/json"))
    .andExpect(status().isOk());
    }
    
     @Test
    public void name3() throws Exception {      
        
    mockMvc.perform(delete("/api/user/1")
    .contentType("application/json"))
    .andExpect(status().isOk());
    }
}
