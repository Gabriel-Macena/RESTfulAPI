package persistence;

import java.math.BigInteger;
import java.util.List;

import org.sql2o.Connection;
import org.sql2o.Sql2o;

import domain.Unit;

public class Sql2oUnitDAO implements UnitDAO {

	private Sql2o sql2o;

	public Sql2oUnitDAO(Sql2o sql2o) {
		this.sql2o = sql2o;
	}
	
	@Override
	public void create(Unit unit) {
		// TODO Auto-generated method stub
		try (Connection conn = sql2o.open()) {
			String sql = "insert into unit(label) VALUES (:label)";
			BigInteger id = (BigInteger) conn.createQuery(sql, true).addParameter("label", unit.getLabel())
					.executeUpdate().getKey();
			unit.setId(id.longValue());
		}
	}

	@Override
	public Unit getById(long id) {
		// TODO Auto-generated method stub
		try (Connection conn = sql2o.open()) {
			String sql = "select * from unit where id=:id";
			return conn.createQuery(sql, true).addParameter("id", id).executeAndFetchFirst(Unit.class);
		}
	}

	@Override
	public List<Unit> getAll() {
		// TODO Auto-generated method stub
		try (Connection conn = sql2o.open()) {
			String sql = "select * from unit";
			return conn.createQuery(sql, true).executeAndFetch(Unit.class);
		}
	}

}
