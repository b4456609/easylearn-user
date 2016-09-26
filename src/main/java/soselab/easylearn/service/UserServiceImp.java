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

import java.util.ArrayList;
import java.util.List;

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
        if (user == null) new UserNotFoundException();

        List<Folder> folders = user.getFolder();
        List<String> packIds = new ArrayList<String>();
        for (Folder folder : folders) {
            packIds.addAll(folder.getPack());
        }
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
}