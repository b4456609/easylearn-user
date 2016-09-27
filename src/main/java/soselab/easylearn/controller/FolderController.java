package soselab.easylearn.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import soselab.easylearn.model.Folder;
import soselab.easylearn.model.dto.DeleteFolderDTO;
import soselab.easylearn.service.UserService;

import java.util.List;

/**
 * Created by bernie on 2016/9/27.
 */
@RestController
public class FolderController {
    private static final Logger LOGGER = LoggerFactory.getLogger(FolderController.class);

    @Autowired
    UserService userService;

    @RequestMapping(path = "/folder", method = RequestMethod.POST)
    public void addFolder(@RequestBody Folder folder, @RequestHeader("user-id") String userId) {

        LOGGER.info(userId);
        LOGGER.info(folder.toString());

        userService.addFolder(userId, folder);
    }

    @RequestMapping(path = "/folder", method = RequestMethod.PUT)
    public void updateFolder(@RequestBody Folder folder, @RequestHeader("user-id") String userId) {

        LOGGER.info(userId);
        LOGGER.info(folder.toString());

        userService.updateFolder(userId, folder);
    }

    @RequestMapping(path = "/folder", method = RequestMethod.DELETE)
    public void deleteFolder(@RequestBody DeleteFolderDTO deleteFolderDTO, @RequestHeader("user-id") String userId) {

        LOGGER.info(userId);
        LOGGER.info(deleteFolderDTO.toString());

        userService.deleteFolder(userId, deleteFolderDTO);
    }

    @RequestMapping(path = "/folder", method = RequestMethod.GET)
    public List<Folder> getFolder(@RequestHeader("user-id") String userId) {

        LOGGER.info(userId);

        return userService.getFolder(userId);
    }
}
