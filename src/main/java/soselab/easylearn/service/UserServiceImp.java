package soselab.easylearn.service;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import soselab.easylearn.factroy.UserFactory;
import soselab.easylearn.model.Folder;
import soselab.easylearn.model.User;
import soselab.easylearn.repository.UserRepository;
import soselab.easylearn.service.exception.UserNotFoundException;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by bernie on 2016/9/10.
 */
@Service
public class UserServiceImp implements UserService {


    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImp.class);

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserFactory userFactory;

    @Override
    public List<String> getUserPack(String userId) {
        User user = userRepository.findOne(userId);
        if (user == null) {
            throw new UserNotFoundException();
        }

        List<String> packIds = user.getFolder().stream()
                .map(Folder::getPack)
                .flatMap(List::stream)
                .distinct()
                .sorted()
                .collect(Collectors.toList());

        return packIds;
    }

//    @Override
//    public User sync(User user) {
//        // check if new user
//        if (!userRepository.exists(user.getId())) {
//            LOGGER.info("user not exist");
//            user.setCreateTime(new DateTime().getMillis());
//            user.setLastUpTime(new DateTime().getMillis());
//            user.getSetting().setVersion(1);
//            userRepository.save(user);
//        } else if (user.getSetting().getVersion() == 0) {
//            //old user new login or new device
//            // no need to do anything
//            // direct to build response
//            LOGGER.info("old user new login or new device");
//
//        } else if (userRepository.findOne(user.getId()).isConflict(user)) {
//            new UserConflictException();
//        } else {
//            LOGGER.info("sync to db");
//            User dbuser = userRepository.findOne(user.getId());
//            dbuser.setLastUpTime(new DateTime().getMillis());
//            dbuser.setSetting(user.getSetting());
//            dbuser.setFolder(user.getFolder());
//            userRepository.save(dbuser);
//        }
//        return userRepository.findOne(user.getId());
//    }

    @Override
    public void login(String id, String name) {
        long nowTime = new DateTime().getMillis();
        // check if new user
        if (!userRepository.exists(id)) {
            LOGGER.info(id + " user not exist");
            User user = userFactory.createUser(name, id);
            user.setCreateTime(nowTime);
            user.setLastUpTime(nowTime);
            userRepository.save(user);
        } else {
            LOGGER.info(id + " login");
            User dbuser = userRepository.findOne(id);
            dbuser.setName(name);
            dbuser.setLastUpTime(nowTime);
            userRepository.save(dbuser);
        }
    }

    @Override
    public void addFolder(String userId, Folder folder) {
        if (userRepository.exists(userId)) {
            User user = userRepository.findOne(userId);
            user.getFolder().add(folder);
            userRepository.save(user);
        } else {
            throw new UserNotFoundException();
        }
    }

    @Override
    public void updateFolder(String userId, Folder folder) {
        if (userRepository.exists(userId)) {
            User user = userRepository.findOne(userId);
            List<Folder> folders = user.getFolder().stream()
                    .filter(folder1 -> !folder1.getId().equals(folder.getId()))
                    .collect(Collectors.toList());
            folders.add(folder);
            user.setFolder(folders);
            userRepository.save(user);
        } else {
            throw new UserNotFoundException();
        }

    }

    @Override
    public void deleteFolder(String userId, String folderId) {
        if (userRepository.exists(userId)) {
            User user = userRepository.findOne(userId);
            List<Folder> folders = user.getFolder().stream()
                    .filter(folder1 -> !folder1.getId().equals(folderId))
                    .collect(Collectors.toList());
            user.setFolder(folders);
            userRepository.save(user);
        } else {
            throw new UserNotFoundException();
        }
    }

    @Override
    public List<Folder> getFolder(String userId) {
        if (userRepository.exists(userId)) {
            User user = userRepository.findOne(userId);
            return user.getFolder();
        } else {
            throw new UserNotFoundException();
        }
    }

    @Override
    public void deletePackInFolder(String userId, String id) {
        if (userRepository.exists(userId)) {
            User user = userRepository.findOne(userId);
            List<Folder> folders = user
                    .getFolder()
                    .stream()
                    .map(f -> {
                        f.getPack().remove(id);
                        return f;
                    })
                    .collect(Collectors.toList());
            user.setFolder(folders);
            userRepository.save(user);
        } else {
            throw new UserNotFoundException();
        }
    }
}
