package search;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.io.FileInputStream;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class TestSearch {

    @Autowired
    private MockMvc mvc;

    @Test
    public void test_index() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/")
                .accept(MediaType.TEXT_PLAIN))
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo("Hi! Welcome to the app :)")));
    }

    @Test
    public void test_string() throws Exception {
        String ter = "picard";
        String str = "Jean-Luc Picard was a celebrated Starfleet officer, archaeologist and diplomat";
        Integer expected = 1;
        mvc.perform(MockMvcRequestBuilders.post("/string")
                .param("term", ter)
                .param("string", str)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.term").value(ter))
                .andExpect(jsonPath("$.count").value(1))
                .andExpect(jsonPath("$.input").value(str));
    }

    @Test
    public void test_file() throws Exception {
        String ter = "lucis";
        String filename = "test-assets/test-file3.txt";
        MockMultipartFile mpFile = new MockMultipartFile("file", filename,
                "multipart/form-data", new FileInputStream(filename));

        mvc.perform(MockMvcRequestBuilders.multipart("/file")
                .file(mpFile)
                .param("term", ter)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.term").value(ter))
                .andExpect(jsonPath("$.count").value(2))
                .andExpect(jsonPath("$.input").value(filename));
    }
}
