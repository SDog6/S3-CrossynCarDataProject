package Backend.Repo;

import Backend.Classes.Trip;
import Backend.Classes.User;
import Backend.Interfaces.DatabaseAccess.IUserDAL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserRepo implements IUserDAL
{
    @Autowired
    MongoTemplate mt;

    @Override
    public boolean verifyUserfromDBbyUserNameandPassword(String userName, String password)
    {
        Query q = new Query();
        q.addCriteria(Criteria.where("userName").is(userName));
        q.addCriteria(Criteria.where("password").is(password));
        return mt.exists(q, User.class);
    }

    @Override
    public User getUserfromDBbyUserName(String userName) {
        Query q = new Query();
        q.addCriteria(Criteria.where("userName").is(userName));
        return mt.findOne(q, User.class);
    }

    @Override
    public List<User> getAllUsersfromDB() {
        return mt.findAll(User.class);
    }

    @Override
    public List<User> getAllUsersfromDBbyVehicleID(String vehicleID) {
        Query q = new Query();
        q.addCriteria(Criteria.where("vehicleId").is(vehicleID));
        return mt.find(q, User.class);
    }

    @Override
    public void addUserinDB(User user)
    {
        mt.save(user);
    }
}
