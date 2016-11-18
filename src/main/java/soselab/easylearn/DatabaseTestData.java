package soselab.easylearn;

import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import soselab.easylearn.factroy.UserFactory;
import soselab.easylearn.model.Folder;
import soselab.easylearn.model.User;
import soselab.easylearn.repository.UserRepository;

public class DatabaseTestData implements CommandLineRunner {

    @Value("${easylearn.isTest}")
    private boolean isTest;

    @Autowired
    private UserRepository userRepository;

    @Override
    public void run(String... args) throws Exception {
        if (isTest) {
            userRepository.deleteAll();
            User user = new UserFactory().createUser("范振原", "1009840175700426");
            Folder folder = new Folder("folder1478678221265", "Hhh", Lists.newArrayList("pack1478666090008"));
            Folder folder1 = new Folder("folder1478678221265", "SSD", Lists.newArrayList());
            Folder folder2 = new Folder("all", "全部懶人包", Lists.newArrayList("pack1477403034413", "pack1478666090008", "pack1479221373194"));
            user.setFolder(Lists.newArrayList(folder, folder1, folder2));
            userRepository.save(user);
        }
    }
}
