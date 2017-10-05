package persistence;

import java.util.List;

import domain.Data;

public interface DataDAO {
	void create(Data data);
	int countByStreamId(long streamId);
	List<Data> getByStreamId(long streamId);
	List<Data> getById(long id);
	List<Data> getLatestByStreamId(long streamId, int limit);
	List<Data> getAll();
}
