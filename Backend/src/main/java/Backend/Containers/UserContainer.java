package Backend.Containers;

import Backend.Classes.User;
import Backend.Interfaces.DatabaseAccess.IUserDAL;
import Backend.Interfaces.IUserContainer;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@NoArgsConstructor
public class UserContainer implements IUserContainer {

    @Autowired
    IUserDAL dal;

    @Override
    public boolean verifyUserfromDBbyUserNameandPassword(String userName, String password) {
       return  dal.verifyUserfromDBbyUserNameandPassword(userName,password);
    }

    @Override
    public User getUserfromDBbyUserName(String userName) {
        return dal.getUserfromDBbyUserName(userName);
    }

    @Override
    public List<User> getAllUsersfromDB() {
        return dal.getAllUsersfromDB();
    }

    @Override
    public List<User> getAllUsersfromDBbyVehicleID(String vehicleID) {
        return dal.getAllUsersfromDBbyVehicleID(vehicleID);
    }

    @Override
    public void addUserinDB(User user) {
        dal.addUserinDB(user);
    }
}
