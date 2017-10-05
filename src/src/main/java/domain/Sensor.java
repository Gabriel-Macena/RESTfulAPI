package domain;

import java.util.UUID;

public class Sensor {
	private long id;
	private String key;
	private String label;
	private String description;
	private long userId;

	public Sensor(String label, String description, long userId) {
		super();
		this.key = UUID.randomUUID().toString().replaceAll("-", "");
		this.label = label;
		this.description = description;
		this.userId = userId;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

}
