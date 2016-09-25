package soselab.easylearn.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import soselab.easylearn.model.User;

import java.util.List;

/**
 * Created by bernie on 2016/9/10.
 */
public interface UserRepository extends MongoRepository<User, String> {

    @Override
    public List<User> findAll();

    @Override
    boolean exists(String id);

    @Override
    User save(User entity);

    @Override
    User findOne(String s);


}
