package Backend.Repo;

import Backend.Classes.Vehicle;
import Backend.Interfaces.DatabaseAccess.IVehicleDAL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public class VehicleRepo implements IVehicleDAL
{
    @Autowired
    MongoTemplate mt;

    @Override
    public void addVehicleinDB(Vehicle vehicle) {
        mt.save(vehicle);
    }

    @Override
    public Vehicle getVehiclefromDBbyVehicleID(String vehicleID) {
    Query q = new Query();
    q.addCriteria(Criteria.where("vehicleID").is(vehicleID));
        return mt.findOne(q, Vehicle.class);
    }

    @Override
    public Vehicle getVehiclefromDBbyLicensePlate(String licensePlate) {
        Query q = new Query();
        q.addCriteria(Criteria.where("licensePlate").is(licensePlate));
        return mt.findOne(q, Vehicle.class);
    }

    @Override
    public List<Vehicle> getAllVehiclesfromDB() {
        return mt.findAll(Vehicle.class);
    }
}
