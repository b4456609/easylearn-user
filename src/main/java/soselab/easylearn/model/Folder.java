package soselab.easylearn.model;

import java.util.List;

/**
 * Created by bernie on 2016/9/10.
 */
public class Folder {

    private String id;
    private String name;
    private List<String> pack;

    public Folder(String id, String name, List<String> pack) {
        this.id = id;
        this.name = name;
        this.pack = pack;
    }

    public Folder() {
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

    public List<String> getPack() {
        return pack;
    }

    public void setPack(List<String> pack) {
        this.pack = pack;
    }

    @Override
    public String toString() {
        return "Folder{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", pack=" + pack +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Folder)) return false;

        Folder folder = (Folder) o;

        if (id != null ? !id.equals(folder.id) : folder.id != null) return false;
        if (name != null ? !name.equals(folder.name) : folder.name != null) return false;
        return pack != null ? pack.equals(folder.pack) : folder.pack == null;

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (pack != null ? pack.hashCode() : 0);
        return result;
    }
}
