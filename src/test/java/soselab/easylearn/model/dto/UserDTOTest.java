package soselab.easylearn.model.dto;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.*;
import org.junit.runner.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.autoconfigure.json.*;
import org.springframework.boot.test.context.*;
import org.springframework.boot.test.json.*;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.*;

import static org.assertj.core.api.Assertions.*;

public class UserDTOTest {

    @Autowired
    private JacksonTester<UserDTO> json;

    @Before
    public void setup() {
        ObjectMapper objectMapper = new ObjectMapper();
        // Possibly configure the mapper
        JacksonTester.initFields(this, objectMapper);
    }


    @Test
    public void testDeserialize() throws Exception {
        String content = "{\"id\":\"Ford\",\"name\":\"Focus\"}";
        assertThat(this.json.parseObject(content).getId()).isEqualTo("Ford");
        assertThat(this.json.parseObject(content).getName()).isEqualTo("Focus");
    }

}