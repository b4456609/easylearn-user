package soselab.easylearn.service;

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

import java.util.ArrayList;
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


    @Test(expected = UserNotFoundException.class)
    public void addFolder_noSuchUser_UserNotFoundException() throws Exception {
        given(this.userRepository.exists("id"))
                .willReturn(false);
        userService.addFolder("id", null);
    }

    @Test
    public void addFolder_addFolderToUser_addFolderToUser() throws Exception {
        Folder folder = new Folder("id", "name", Arrays.asList("asdfasdf", "2", "3"));
        Folder folder1 = new Folder("id1", "name1", Arrays.asList("asdfasdf1", "21", "31"));
        User user = new User("id", "name", new ArrayList<Folder>(Arrays.asList(folder)), null);

        given(this.userRepository.exists("id"))
                .willReturn(true);
        given(this.userRepository.findOne("id"))
                .willReturn(user);

        userService.addFolder("id", folder1);
        user.getFolder().add(folder1);
        verify(userRepository, times(1)).save(user);
    }

    @Test
    public void updateFolder_updateUserFolder_saveNewUser() throws Exception {
        Folder folder = new Folder("id", "name", Arrays.asList("asdfasdf", "2", "3"));
        Folder folder1 = new Folder("id", "name1", Arrays.asList("asdfasdf1", "21", "31"));
        User user = new User("id", "name", new ArrayList<Folder>(Arrays.asList(folder)), null);
        User expect = new User("id", "name", new ArrayList<Folder>(Arrays.asList(folder1)), null);

        given(this.userRepository.exists("id"))
                .willReturn(true);
        given(this.userRepository.findOne("id"))
                .willReturn(user);

        userService.updateFolder("id", folder1);
        verify(userRepository).save(expect);
    }

    @Test
    public void deleteFolder_deleteUserFolder_saveDeleteFolderUser() throws Exception {
        Folder folder = new Folder("id", "name", Arrays.asList("asdfasdf", "2", "3"));
        Folder folder1 = new Folder("id1", "name1", Arrays.asList("asdfasdf1", "21", "31"));
        User user = new User("id", "name", new ArrayList<Folder>(Arrays.asList(folder, folder1)), null);
        User expect = new User("id", "name", new ArrayList<Folder>(Arrays.asList(folder1)), null);

        given(this.userRepository.exists("id"))
                .willReturn(true);
        given(this.userRepository.findOne("id"))
                .willReturn(user);

        userService.deleteFolder("id", "id");
        verify(userRepository).save(expect);

    }

    @Test
    public void getFolder_getUserFolder_returnListOfFolder() throws Exception {
        Folder folder = new Folder("id", "name", Arrays.asList("asdfasdf", "2", "3"));
        Folder folder1 = new Folder("id1", "name1", Arrays.asList("asdfasdf1", "21", "31"));
        User user = new User("id", "name", new ArrayList<Folder>(Arrays.asList(folder, folder1)), null);

        given(this.userRepository.exists("id"))
                .willReturn(true);
        given(this.userRepository.findOne("id"))
                .willReturn(user);

        assertThat(userService.getFolder("id")).contains(folder, folder1);
    }

}