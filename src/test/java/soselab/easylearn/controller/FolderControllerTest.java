package soselab.easylearn.controller;

import com.jayway.jsonpath.JsonPath;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import soselab.easylearn.factroy.UserFactory;
import soselab.easylearn.model.Folder;
import soselab.easylearn.model.User;
import soselab.easylearn.repository.UserRepository;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Java6Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("scratch")
public class FolderControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserFactory userFactory;

    @Before
    public void setUp() {
        userRepository.deleteAll();
        User user = userFactory.createUser("name", "id");
        user.getFolder().add(new Folder("folderid", "foldername", Arrays.asList("packadf", "padsfkwerowierp")));
        userRepository.save(user);
    }

    @Test
    public void addFolder() throws Exception {
        String input = "{\"id\":\"folderid\",\"name\":\"foldername\",\"pack\":[\"pack\"]}";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("user-id", "id");
        HttpEntity<String> entity = new HttpEntity<>(input, headers);
        ResponseEntity<String> response = this.restTemplate.exchange("/folder/", HttpMethod.POST, entity, String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        // validate
        response = this.restTemplate.exchange("/folder", HttpMethod.GET, entity, String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        System.out.println(response.getBody());
        JSONArray jsonArray = new JSONArray(response.getBody());
        assertThat(jsonArray.length()).isEqualTo(3);

        List<String> authors = JsonPath.read(response.getBody(), "$[*][?(@.id=='folderid')]");
        jsonArray = new JSONArray(authors);
        JSONObject jsonObject = jsonArray.getJSONObject(0);
        assertThat(jsonObject.getString("id")).isEqualTo("folderid");
        assertThat(jsonObject.getString("name")).isEqualTo("foldername");
        assertThat(jsonObject.getString("name")).isEqualTo("foldername");

    }

    @Test
    public void updateFolder_updateUserFolder_Ok() throws Exception {
        String input = "{\"id\":\"folderid\",\"name\":\"foldername\",\"pack\":[\"pack\"]}";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("user-id", "id");
        HttpEntity<String> entity = new HttpEntity<>(input, headers);
        ResponseEntity<String> response = this.restTemplate.exchange("/folder/", HttpMethod.PUT, entity, String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        // validate
        response = this.restTemplate.exchange("/folder", HttpMethod.GET, entity, String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        System.out.println(response.getBody());

        List<String> authors = JsonPath.read(response.getBody(), "$[*][?(@.id=='folderid')]");
        JSONArray jsonArray = new JSONArray(authors);
        JSONObject jsonObject = jsonArray.getJSONObject(0);
        assertThat(jsonObject.getString("id")).isEqualTo("folderid");
        assertThat(jsonObject.getString("name")).isEqualTo("foldername");
    }

    @Test
    public void deleteFolder_deleteUserFolder_OK() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("user-id", "id");
        HttpEntity entity = new HttpEntity(headers);
        ResponseEntity<String> response = this.restTemplate.exchange("/folder/folderid", HttpMethod.DELETE, entity, String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        // validate
        response = this.restTemplate.exchange("/folder", HttpMethod.GET, entity, String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        JSONArray jsonArray = new JSONArray(response.getBody());
        assertThat(jsonArray.length()).isEqualTo(1);
    }

    @Test
    public void getFolder_getUserFolder_UserFolderArray() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("user-id", "id");
        HttpEntity entity = new HttpEntity(headers);
        ResponseEntity<String> response = this.restTemplate.exchange("/folder", HttpMethod.GET, entity, String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        JSONArray jsonArray = new JSONArray(response.getBody());
        assertThat(jsonArray.length()).isEqualTo(2);
    }

    @Test
    public void deletePackInFolder_RemovePack_remove() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("user-id", "id");
        HttpEntity entity = new HttpEntity(headers);
        ResponseEntity<String> response = this.restTemplate.exchange("/folder/pack/packadf", HttpMethod.DELETE, entity, String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        System.out.println(userRepository.findOne("id").getFolder());
        assertThat(userRepository.findOne("id").getFolder()
                .stream()
                .filter(i -> i.getId().equals("folderid"))
                .findFirst()
                .get()
                .getPack())
                .doesNotContain("packadf")
                .contains("padsfkwerowierp");
    }
}