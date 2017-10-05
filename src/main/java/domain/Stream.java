package domain;

import java.util.UUID;

public class Stream {
	private long id;
	private String key;
	private String label;
	private boolean enabled;
	private long unitId;
	private long sensorId;

	public Stream(String label, long unitId, long sensorId) {
		super();
		this.key = UUID.randomUUID().toString().replaceAll("-", "");
		this.label = label;
		this.enabled = true;
		this.unitId = unitId;
		this.sensorId = sensorId;
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

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public long getUnitId() {
		return unitId;
	}

	public void setUnitId(long unitId) {
		this.unitId = unitId;
	}

	public long getSensorId() {
		return sensorId;
	}

	public void setSensorId(long sensorId) {
		this.sensorId = sensorId;
	}

}