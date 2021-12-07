package Backend.Interfaces.DatabaseAccess;

import Backend.Classes.User;

import java.util.List;

public interface IUserDAL
{
    boolean verifyUserfromDBbyUserNameandPassword(String userName, String password);
    User getUserfromDBbyUserName(String userName);
    List<User> getAllUsersfromDB();
    List<User> getAllUsersfromDBbyVehicleID(String vehicleID);
    void addUserinDB(User user);

}
