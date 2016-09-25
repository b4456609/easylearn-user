package soselab.easylearn.service;

import java.util.List;

/**
 * Created by bernie on 2016/9/10.
 */
public interface UserService {
    List<String> getUserPack(String userId);


    void login(String id, String name);
}
