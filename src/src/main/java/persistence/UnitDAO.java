package persistence;

import java.util.List;

import domain.Unit;

public interface UnitDAO {
	void create(Unit unit);
	Unit getById(long id);
	List<Unit> getAll();
}
