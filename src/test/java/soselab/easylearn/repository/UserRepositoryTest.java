package soselab.easylearn.repository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import soselab.easylearn.factroy.UserFactory;
import soselab.easylearn.model.User;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("scratch")
public class UserRepositoryTest {

    @Autowired
    private UserRepository repository;

    @Autowired
    private UserFactory userFactory;

    @Before
    public void executedBeforeEach() {
        repository.deleteAll();
    }

    @Test
    public void userExistsAndSave() throws Exception {
        assertFalse(repository.exists("Tom's id"));

        User user = userFactory.createUser("Tom", "Tom's id");
        this.repository.save(user);

        assertTrue(repository.exists("Tom's id"));
    }

    @Test
    public void userFindOne() throws Exception {
        User user = userFactory.createUser("Blue", "Blue's id");
        this.repository.save(user);
        user = repository.findOne("Blue's id");

        assertTrue(repository.exists("Blue's id"));
        user = repository.findOne("Blue's idaaa");

        assertThat(user).isNull();
    }

}