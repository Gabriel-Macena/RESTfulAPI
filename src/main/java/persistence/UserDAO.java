package persistence;

import java.util.List;

import domain.User;

public interface UserDAO {
	void create(User user);
	User getById(long id);
	User getByUsername(String username);
	List<User> getAll();
}
