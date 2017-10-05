package domain;

public class Data {
	private long id;
	private long timestamp;
	private double value;
	private long streamId;

	public Data(long timestamp, double value, long streamId) {
		this.timestamp = timestamp;
		this.value = value;
		this.streamId = streamId;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	public long getStreamId() {
		return streamId;
	}

	public void setStreamId(long streamId) {
		this.streamId = streamId;
	}

}
