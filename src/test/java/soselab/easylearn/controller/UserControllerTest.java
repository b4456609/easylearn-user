package soselab.easylearn.controller;

import org.codehaus.jettison.json.JSONObject;
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

import static org.assertj.core.api.Java6Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("scratch")
public class UserControllerTest {
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
        user.getFolder().add(new Folder("folder", "foldern", Arrays.asList("packadfa", "padsfkwerowierpa")));
        userRepository.save(user);
    }

    @Test
    public void getUserPack_getUserPack_packList() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("user-id", "id");
        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<String> response = this.restTemplate.exchange("/pack", HttpMethod.GET, entity, String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        System.out.println(response.getBody());
        assertThat(response.getBody()).contains("packadf", "padsfkwerowierp", "packadfa", "padsfkwerowierpa");

    }

    @Test
    public void login_loginUser_udpateDatabaseUserLastUpdateTime() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        String json = new JSONObject()
                .put("id", "id")
                .put("name", "name")
                .toString();
        HttpEntity<String> entity = new HttpEntity<>(json, headers);
        ResponseEntity<String> response = this.restTemplate.exchange("/login", HttpMethod.POST, entity, String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

}