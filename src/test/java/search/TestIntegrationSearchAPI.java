package search;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import java.net.URL;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TestIntegrationSearchAPI {

    @LocalServerPort
    private int port;
    private URL base;

    @Autowired
    private TestRestTemplate template;

    @Before
    public void setUp() throws Exception {
        this.base = new URL("http://localhost:" + port + "/");
    }

    @Test
    public void integrationTest_api() throws Exception {
        ResponseEntity<String> response = template.getForEntity("/api/", String.class);

        String expected = "{\"term\":\"welcome\",\"count\":1,\"input\":\"hi! welcome to the api :)\"}";
        assertThat(response.getBody(), equalTo(expected));
    }

    @Test
    public void integrationTest_string() throws Exception {
        MultiValueMap<String, String> params= new LinkedMultiValueMap<String, String>();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        headers.set("Accept", "application/json");

        params.add("string", "In the 24th century, Spock became an adviser to the leadership of the Federation");
        params.add("term", "spock");

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(params, headers);

        ResponseEntity<String> response = template.postForEntity("/api/string", request, String.class);
        String expected = "{\"term\":\"spock\",\"count\":1,\"input\":\"In the 24th century, "
                + "Spock became an adviser to the leadership of the Federation\"}";
        assertThat(response.getBody(), equalTo(expected));
    }

    @Test
    public void integrationTest_file() throws Exception {
        MultiValueMap<String, Object> params= new LinkedMultiValueMap<String, Object>();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        headers.set("Content-Type", "multipart/form-data");
        headers.set("Accept", "application/json");

        params.add("file", new FileSystemResource("test-assets/test-file2.txt"));
        params.add("term", "spock");

        HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<MultiValueMap<String, Object>>(params, headers);

        ResponseEntity<String> response = template.postForEntity("/api/file", request, String.class);
        String expected = "{\"term\":\"spock\",\"count\":2,\"input\":\"test-file2.txt\"}";
        assertThat(response.getBody(), equalTo(expected));
    }
}