package persistence;

import java.math.BigInteger;
import java.util.List;

import org.sql2o.Connection;
import org.sql2o.Sql2o;

import domain.Sensor;

public class Sql2oSensorDAO implements SensorDAO {

	private Sql2o sql2o;

	public Sql2oSensorDAO(Sql2o sql2o) {
		this.sql2o = sql2o;
	}

	@Override
	public void create(Sensor sensor) {
		// TODO Auto-generated method stub
		try (Connection conn = sql2o.open()) {
			String sql = "insert into sensor(sensorKey, label, description, userId) VALUES (:key, :label, :desc, :userId)";
			BigInteger id = (BigInteger) conn.createQuery(sql, true).addParameter("key", sensor.getKey())
					.addParameter("label", sensor.getLabel()).addParameter("desc", sensor.getDescription())
					.addParameter("userId", sensor.getUserId()).executeUpdate().getKey();
			sensor.setId(id.longValue());
		}
	}

	@Override
	public List<Sensor> getById(long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Sensor getByKey(String key) {
		// TODO Auto-generated method stub
		try (Connection conn = sql2o.open()) {
			String sql = "select * from sensor where sensorKey=:key";
			return conn.createQuery(sql, true).addParameter("key", key).addColumnMapping("sensorKey", "key")
					.executeAndFetchFirst(Sensor.class);
		}
	}

	@Override
	public List<Sensor> getByUserId(long userId) {
		// TODO Auto-generated method stub
		try (Connection conn = sql2o.open()) {
			String sql = "select * from sensor where userId=:userId";
			return conn.createQuery(sql, true).addParameter("userId", userId).addColumnMapping("sensorKey", "key")
					.executeAndFetch(Sensor.class);
		}
	}
	
	@Override
	public List<Sensor> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

}
