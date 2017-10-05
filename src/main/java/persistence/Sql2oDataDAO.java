package persistence;

import java.math.BigInteger;
import java.util.List;

import org.sql2o.Connection;
import org.sql2o.Sql2o;

import domain.Data;

public class Sql2oDataDAO implements DataDAO {

	private Sql2o sql2o;

	public Sql2oDataDAO(Sql2o sql2o) {
		this.sql2o = sql2o;
	}

	@Override
	public void create(Data data) {
		// TODO Auto-generated method stub
		try (Connection conn = sql2o.open()) {
			String sql = "insert into data(timestamp, value, streamId) VALUES (:timestamp, :value, :streamId)";
			BigInteger id = (BigInteger) conn.createQuery(sql, true)
					.addParameter("timestamp", data.getTimestamp())
					.addParameter("value", data.getValue())
					.addParameter("streamId", data.getStreamId())
					.executeUpdate().getKey();
			data.setId(id.longValue());
		}
	}

	@Override
	public List<Data> getByStreamId(long streamId) {
		// TODO Auto-generated method stub
		try (Connection conn = sql2o.open()) {
			String sql = "select * from data where streamId=:streamId";
			return conn.createQuery(sql, true).addParameter("streamId", streamId)
					.executeAndFetch(Data.class);
		}
	}

	@Override
	public List<Data> getById(long id) {
		// TODO Auto-generated method stub
		try (Connection conn = sql2o.open()) {
			String sql = "select * from data where id=:id";
			return conn.createQuery(sql, true).addParameter("id", id)
					.executeAndFetch(Data.class);
		}
	}

	@Override
	public List<Data> getAll() {
		// TODO Auto-generated method stub
		try (Connection conn = sql2o.open()) {
			String sql = "select * from data";
			return conn.createQuery(sql, true)
					.executeAndFetch(Data.class);
		}
	}

	@Override
	public int countByStreamId(long streamId) {
		try (Connection conn = sql2o.open()) {
			String sql = "select count(*) from data where streamId=:streamId";
			return conn.createQuery(sql, true).addParameter("streamId", streamId).executeScalar(Integer.class);
		}
	}

	@Override
	public List<Data> getLatestByStreamId(long streamId, int limit) {
		// TODO Auto-generated method stub
		try (Connection conn = sql2o.open()) {
			String sql = "select * from data where streamId=:streamId order by timestamp desc";
			List<Data> ldata = conn.createQuery(sql, true).addParameter("streamId", streamId)
					.executeAndFetch(Data.class);
			if (ldata.isEmpty() || ldata.size() <= limit)
				return ldata;
			else
				return ldata.subList(0, limit);
		}
	}

}
