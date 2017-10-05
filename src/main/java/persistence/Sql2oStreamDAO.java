package persistence;

import java.math.BigInteger;
import java.util.List;

import org.sql2o.Connection;
import org.sql2o.Sql2o;

import domain.Stream;

public class Sql2oStreamDAO implements StreamDAO {

	private Sql2o sql2o;

	public Sql2oStreamDAO(Sql2o sql2o) {
		this.sql2o = sql2o;
	}

	@Override
	public void create(Stream stream) {
		// TODO Auto-generated method stub
		try (Connection conn = sql2o.open()) {
			String sql = "insert into stream(streamKey, label, sensorId, unitId) VALUES (:key, :label, :sensorId, :unitId)";
			BigInteger id = (BigInteger) conn.createQuery(sql, true).addParameter("key", stream.getKey())
					.addParameter("label", stream.getLabel())
					.addParameter("sensorId", stream.getSensorId())
					.addParameter("unitId", stream.getUnitId())
					.executeUpdate().getKey();
			stream.setId(id.longValue());
		}
	}

	@Override
	public Stream getById(long id) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public List<Stream> getBySensorId(long sensorId) {
		// TODO Auto-generated method stub
		try (Connection conn = sql2o.open()) {
			String sql = "select * from stream where sensorId=:sensorId";
			return conn.createQuery(sql, true).addParameter("sensorId", sensorId).addColumnMapping("streamKey", "key")
					.executeAndFetch(Stream.class);
		}
	}

	@Override
	public Stream getByKey(String key) {
		// TODO Auto-generated method stub
		try (Connection conn = sql2o.open()) {
			String sql = "select * from stream where streamKey=:key";
			return conn.createQuery(sql, true).addParameter("key", key).addColumnMapping("streamKey", "key")
					.executeAndFetchFirst(Stream.class);
		}
	}

	@Override
	public List<Stream> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

}
