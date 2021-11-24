package Backend.Containers;

import Backend.Classes.Trip;
import Backend.Classes.Vehicle;
import Backend.Interfaces.DatabaseAccess.ITripDAL;
import Backend.Interfaces.DatabaseAccess.IVehicleDAL;
import Backend.Interfaces.IVehicleContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VehicleContainer implements IVehicleContainer
{
    @Autowired
    IVehicleDAL dal;

    private List<Vehicle> vehicles;


    @Override
    public List<Vehicle> dbGetAllVehicles()
    {
        return dal.getAllVehiclesfromDB();
    }

    @Override
    public Vehicle dbGetVehicleByLicenseplate(String LPlate) {
        return dal.getVehiclefromDBbyLicensePlate(LPlate);
    }

    @Override
    public void dbSaveVehicle(Vehicle vehicle) {

    }
}
