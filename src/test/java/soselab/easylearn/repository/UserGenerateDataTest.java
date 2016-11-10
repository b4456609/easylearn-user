package soselab.easylearn.repository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import soselab.easylearn.factroy.UserFactory;
import soselab.easylearn.model.Folder;
import soselab.easylearn.model.User;

import java.util.Arrays;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("scratch")
public class UserGenerateDataTest {

    @Autowired
    private UserRepository repository;

    @Autowired
    private UserFactory userFactory;

    @Before
    public void executedBeforeEach() {
        repository.deleteAll();
    }

    @Test
    public void testDate() throws Exception {
        User user = userFactory.createUser("Tom", "Tom's id");
        user.getFolder().add(new Folder("i", "id", Arrays.asList("packid", "testPackId")));
        this.repository.save(user);
    }

}