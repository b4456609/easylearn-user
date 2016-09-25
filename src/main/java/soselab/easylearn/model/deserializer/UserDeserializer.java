package soselab.easylearn.model.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import soselab.easylearn.model.Folder;
import soselab.easylearn.model.User;
import soselab.easylearn.model.dto.UserDTO;

import java.io.IOException;
import java.util.List;

/**
 * Created by bernie on 2016/9/10.
 */
public class UserDeserializer extends JsonDeserializer<User> {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserDeserializer.class);

    @Override
    public User deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false).setPropertyNamingStrategy(
                        PropertyNamingStrategy.SNAKE_CASE);
        ;

        LOGGER.info(objectMapper.toString());
        JsonNode jsonNode = p.readValueAsTree();

        LOGGER.info(jsonNode.toString());

        //user json
        JsonNode userJsonNode = jsonNode.get("user");
        User user = new User();
        UserDTO userDTO = objectMapper.readValue(userJsonNode.toString(), UserDTO.class);

        user.setName(userDTO.getName());
        user.setId(userDTO.getId());

        //folder json
        JsonNode folderJsonNode = jsonNode.get("folder");
        List<Folder> folder = objectMapper.readValue(folderJsonNode.toString(), new TypeReference<List<Folder>>() {
        });
        user.setFolder(folder);
        return user;
    }
}
