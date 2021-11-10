package Backend.Interfaces;

import java.util.ArrayList;

public interface IManager {

boolean AddObject(Object a);

boolean RemoveObject(Object a);

ArrayList<Object> GetAllObjects();
}
