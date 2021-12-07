package Backend.Interfaces.DatabaseAccess;

import Backend.Classes.Vehicle;

import java.util.List;

public interface IVehicleDAL
{
    void addVehicleinDB(Vehicle vehicle);
    Vehicle getVehiclefromDBbyVehicleID(String vehicleID);
    Vehicle getVehiclefromDBbyLicensePlate(String licensePlate);
    List<Vehicle> getAllVehiclesfromDB();

}
