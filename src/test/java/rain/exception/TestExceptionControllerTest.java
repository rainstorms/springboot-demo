package rain.exception;

import junit.framework.TestCase;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
public class TestExceptionControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Test
    public void testResourceException() throws Exception {
        mockMvc.perform(get("/test-resource-exception")).andReturn();
//                .andExpect(status().is(400))
//                .andExpect(jsonPath("$.message").value("参数错误!"));
    }
}