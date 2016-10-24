package soselab.easylearn.model.dto;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.json.JacksonTester;

import static org.assertj.core.api.Assertions.assertThat;

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