package persistence;

import java.math.BigInteger;
import java.util.List;

import org.sql2o.Connection;
import org.sql2o.Sql2o;

import domain.User;

public class Sql2oUserDAO implements UserDAO {
	
	private Sql2o sql2o;

	public Sql2oUserDAO(Sql2o sql2o) {
		this.sql2o = sql2o;
	}

	@Override
	public void create(User user) {
		// TODO Auto-generated method stub
		try (Connection conn = sql2o.open()) {
			String sql = "insert into user(username, email) VALUES (:username, :email)";
			BigInteger id = (BigInteger) conn.createQuery(sql, true).addParameter("username", user.getUsername())
					.addParameter("email", user.getEmail()).executeUpdate().getKey();
			user.setId(id.longValue());
		}
	}

	@Override
	public User getById(long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User getByUsername(String username) {
		// TODO Auto-generated method stub
		try (Connection conn = sql2o.open()) {
			String sql = "select * from user where username=:user";
			return conn.createQuery(sql, true).addParameter("user", username).executeAndFetchFirst(User.class);
		}
	}

	@Override
	public List<User> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

}
