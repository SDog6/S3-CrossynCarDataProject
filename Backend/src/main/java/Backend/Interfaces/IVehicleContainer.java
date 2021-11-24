package Backend.Interfaces;

import Backend.Classes.Vehicle;

import java.util.List;

public interface IVehicleContainer
{
    List<Vehicle> dbGetAllVehicles();
    Vehicle dbGetVehicleByLicenseplate(String LPlate);
    void dbSaveVehicle(Vehicle vehicle);
}
