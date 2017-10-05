package persistence;

import java.util.List;

import domain.Sensor;

public interface SensorDAO {
	void create(Sensor sensor);
	List<Sensor> getById(long id);
	Sensor getByKey(String key);
	List<Sensor> getAll();
	List<Sensor> getByUserId(long userId);
}
