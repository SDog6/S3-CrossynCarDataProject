package API;

import Backend.Classes.User;

import java.util.ArrayList;
import java.util.List;

public class FakeUserData {
   private final List<User> users = new ArrayList<User>();

    public FakeUserData(){

        User user1 = new User("user1","123");
        users.add(user1);

    }

    public List<User> GetAllUsers(){
        return this.users;
    }


}
