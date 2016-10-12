package soselab.easylearn.service;

import soselab.easylearn.model.Folder;

import java.util.List;

/**
 * Created by bernie on 2016/9/10.
 */
public interface UserService {
    List<String> getUserPack(String userId);


    void login(String id, String name);

    void addFolder(String userId, Folder folder);

    void updateFolder(String userId, Folder folder);

    void deleteFolder(String userId, String folderId);

    List<Folder> getFolder(String userId);
}
