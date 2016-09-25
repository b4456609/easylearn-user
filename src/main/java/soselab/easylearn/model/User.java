package soselab.easylearn.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.springframework.data.annotation.Id;
import soselab.easylearn.model.deserializer.UserDeserializer;

import java.util.List;

/**
 * Created by bernie on 2016/9/10.
 */
@JsonDeserialize(using = UserDeserializer.class)
public class User {
    @Id
    private String id;
    private String name;
    private long createTime;
    private long lastUpTime;
    private List<Folder> folder;
    private List<Bookmark> bookmark;

    public User() {
    }

    public User(String id, String name, List<Folder> folder,
                List<Bookmark> bookmark) {
        this.id = id;
        this.name = name;
        this.folder = folder;
        this.bookmark = bookmark;
    }

    public User(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public long getLastUpTime() {
        return lastUpTime;
    }

    public void setLastUpTime(long lastUpTime) {
        this.lastUpTime = lastUpTime;
    }

    public List<Folder> getFolder() {
        return folder;
    }

    public void setFolder(List<Folder> folder) {
        this.folder = folder;
    }

    public List<Bookmark> getBookmark() {
        return bookmark;
    }

    public void setBookmark(List<Bookmark> bookmark) {
        this.bookmark = bookmark;
    }
}
