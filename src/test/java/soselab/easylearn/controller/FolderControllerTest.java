package soselab.easylearn.controller;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Java6Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("scratch")
@DirtiesContext
public class FolderControllerTest {


    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void addFolder() throws Exception {

    }

    @Test
    public void updateFolder() throws Exception {

    }

    @Test
    public void deleteFolder() throws Exception {

    }

    @Test
    public void getFolder() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("user-id", "id");
        HttpEntity entity = new HttpEntity(headers);
        ResponseEntity<String> response = this.restTemplate.exchange("/folder", HttpMethod.GET, entity, String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        //check if a default all folder is in
        JSONArray jsonArray = new JSONArray(response.getBody());
        JSONObject jsonObject = jsonArray.getJSONObject(0);
        assertThat(jsonObject.getString("id")).isEqualTo("all");
        assertThat(jsonObject.getString("name")).isEqualTo("全部懶人包");
    }

}