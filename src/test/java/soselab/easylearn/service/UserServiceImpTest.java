package soselab.easylearn.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import soselab.easylearn.model.Folder;
import soselab.easylearn.model.User;
import soselab.easylearn.repository.UserRepository;
import soselab.easylearn.service.exception.UserNotFoundException;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("scratch")
public class UserServiceImpTest {

    @Autowired
    UserService userService;

    @MockBean
    UserRepository userRepository;

    @Before
    public void setUp() throws Exception {

    }

    @Test(expected = UserNotFoundException.class)
    public void getUserPack_noSuchUser_UserNotFoundException() throws Exception {
        given(this.userRepository.findOne("id"))
                .willReturn(null);
        userService.getUserPack("id");
    }

    @Test
    public void getUserPack_requestPack_PackList() throws Exception {
        Folder folder = new Folder("id", "name", Arrays.asList("asdfasdf", "2", "3"));
        Folder folder1 = new Folder("id1", "name1", Arrays.asList("asdfasdf1", "21", "31"));
        User user = new User("id", "name", Arrays.asList(folder, folder1), null);
        given(this.userRepository.findOne("id"))
                .willReturn(user);
        List<String> packs = userService.getUserPack("id");
        assertThat(packs).contains("asdfasdf", "2", "3", "asdfasdf1", "21", "31");
    }

    @Test
    public void login_newUser_callSaveUser() throws Exception {
        given(this.userRepository.exists("id"))
                .willReturn(false);
        userService.login("id", "name");
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    public void login_existUser_callSaveUser() throws Exception {
        given(this.userRepository.exists("id"))
                .willReturn(true);
        given(this.userRepository.findOne("id"))
                .willReturn(new User("id", "name"));
        userService.login("id", "name");
        verify(userRepository, times(1)).save(any(User.class));
    }


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

    }

}