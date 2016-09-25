package soselab.easylearn.model;


/**
 * Created by bernie on 2016/9/10.
 */
public class Bookmark {
    private String id;
    private String versionId;
    private String name;

    public Bookmark() {
    }

    public Bookmark(String id, String versionId, String name) {
        this.id = id;
        this.versionId = versionId;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVersionId() {
        return versionId;
    }

    public void setVersionId(String versionId) {
        this.versionId = versionId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Bookmark{" +
                "id='" + id + '\'' +
                ", versionId='" + versionId + '\'' +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Bookmark)) return false;

        Bookmark bookmark = (Bookmark) o;

        if (id != null ? !id.equals(bookmark.id) : bookmark.id != null) return false;
        if (versionId != null ? !versionId.equals(bookmark.versionId) : bookmark.versionId != null) return false;
        return name != null ? name.equals(bookmark.name) : bookmark.name == null;

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (versionId != null ? versionId.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }
}
