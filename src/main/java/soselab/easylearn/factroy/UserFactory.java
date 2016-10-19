package soselab.easylearn.factroy;

import org.springframework.stereotype.Component;
import soselab.easylearn.model.Folder;
import soselab.easylearn.model.User;

import java.util.Arrays;
import java.util.Collections;
import java.util.UUID;

/**
 * Created by bernie on 2016/9/13.
 */
@Component
public class UserFactory {
    public User createUser(String name, String id) {
        User user = new User(id, name);
        Folder folder = new Folder("all", "全部懶人包", Collections.EMPTY_LIST);
        user.setFolder(Arrays.asList(folder));
        return user;
    }
}
