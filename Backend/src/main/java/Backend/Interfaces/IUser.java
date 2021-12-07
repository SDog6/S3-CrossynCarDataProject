package Backend.Interfaces;

import Backend.Classes.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.lang.reflect.Member;

public interface IUser extends MongoRepository<User, String> {
    User getUserByUsername(String usesrname);

}
