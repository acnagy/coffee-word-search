package search;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
public class TestController {

    @Autowired
    private MockMvc mvc;

    @Test
    public void test_index() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo("Hi! 42 :)\n")));
    }

    @Test
    public void test_string() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/string")
                .param("term", "picard")
                .param("string", "Jean-Luc Picard was a celebrated Starfleet officer, archaeologist and diplomat")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo("picard: 1\n")));
    }

    @Test
    public void test_file() throws Exception {
        String filename = "test-assets/test-file3.txt";
        MockMultipartFile mpFile = new MockMultipartFile("file", filename,
                "multipart/form-data", new FileInputStream(filename));

        mvc.perform(MockMvcRequestBuilders.multipart("/file")
                .file(mpFile)
                .param("term", "lucis")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo("lucis: 2\n")));
    }
}
