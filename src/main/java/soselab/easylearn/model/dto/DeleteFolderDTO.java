package soselab.easylearn.model.dto;

/**
 * Created by bernie on 2016/9/27.
 */
public class DeleteFolderDTO {
    private String id;

    public DeleteFolderDTO(String id) {
        this.id = id;
    }

    public DeleteFolderDTO() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
