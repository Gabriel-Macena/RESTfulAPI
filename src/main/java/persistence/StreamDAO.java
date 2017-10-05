package persistence;

import java.util.List;

import domain.Stream;

public interface StreamDAO {
	void create(Stream stream);
	Stream getById(long id);
	Stream getByKey(String key);
	List<Stream> getAll();
	List<Stream> getBySensorId(long sensorId);
}
