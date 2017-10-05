package app;

import static spark.Spark.*;

import java.util.List;

import org.sql2o.Sql2o;

import com.eclipsesource.json.Json;
import com.eclipsesource.json.JsonArray;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;

import domain.*;
import persistence.*;

public class Application {

	public static void main(String[] args) {
		Sql2o sql2o = new Sql2o(
				"jdbc:mysql://localhost:3306/restfulapi?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC",
				"restfulapi", "restfulapi");

		DataDAO dataDAO = new Sql2oDataDAO(sql2o);
		UserDAO userDAO = new Sql2oUserDAO(sql2o);
		UnitDAO unitDAO = new Sql2oUnitDAO(sql2o);
		SensorDAO sensorDAO = new Sql2oSensorDAO(sql2o);
		StreamDAO streamDAO = new Sql2oStreamDAO(sql2o);

		int dataLimit = 5;

		port(8080); // pode ser configurável

		/*
		 * Consultar unidades de grandeza
		 */

		get("/units", (request, response) -> {
			List<Unit> units = unitDAO.getAll();

			JsonValue jsonUnits = Json.array();
			for (Unit u : units) {
				JsonObject jsonUnit = Json.object()
						.add("id", u.getId())
						.add("label", u.getLabel());
				jsonUnits.asArray().add(jsonUnit);
			}
			response.type("application/json");
			return jsonUnits.toString();
		});

		/*
		 * Consultar sensores de um usuário
		 */

		get("/users/:user", (request, response) -> {
			String username = request.params("user");
			User user = userDAO.getByUsername(username);
			List<Sensor> sensors = sensorDAO.getByUserId(user.getId());

			JsonArray jsonSensors = Json.array().asArray();
			for (Sensor s : sensors) {
				JsonArray jsonStreams = Json.array().asArray();
				List<Stream> streams = streamDAO.getBySensorId(s.getId());
				for (Stream str : streams) {
					int totalSize = dataDAO.countByStreamId(str.getId());
					JsonObject jsonStream = Json.object()
							.add("id", str.getId())
							.add("key", str.getKey())
							.add("label", str.getLabel())
							.add("unit", str.getUnitId())
							.add("sensor", str.getSensorId())
							.add("totalSize", totalSize);
					jsonStreams.add(jsonStream);
				}
				JsonObject jsonSensor = Json.object()
						.add("id", s.getId())
						.add("key", s.getKey())
						.add("label", s.getLabel())
						.add("description", s.getDescription())
						.add("streams", jsonStreams);

				jsonSensors.add(jsonSensor);
			}
			response.type("application/json");
			return jsonSensors.toString();
		});

		/*
		 * Consultar um sensor específico
		 */

		get("/sensors/:sensor", (request, response) -> {
			String sensorKey = request.params("sensor");
			Sensor sensor = sensorDAO.getByKey(sensorKey);
			List<Stream> streams = streamDAO.getBySensorId(sensor.getId());

			JsonArray jsonStreams = Json.array().asArray();
			for (Stream str : streams) {
				int totalSize = dataDAO.countByStreamId(str.getId());
				List<Data> data = dataDAO.getLatestByStreamId(str.getId(), dataLimit);
				JsonArray jsonDataArray = Json.array().asArray();
				for (Data d : data) {
					JsonObject jsonData = Json.object()
							.add("timestamp", d.getTimestamp())
							.add("data", d.getValue());
					jsonDataArray.add(jsonData);
				}
				JsonObject jsonStream = Json.object()
						.add("id", str.getId())
						.add("key", str.getKey())
						.add("label", str.getLabel())
						.add("unit", str.getUnitId())
						.add("sensor", str.getSensorId())
						.add("totalSize", totalSize)
						.add("data", jsonDataArray);
				jsonStreams.add(jsonStream);
			}
			JsonObject jsonSensor = Json.object()
					.add("id", sensor.getId())
					.add("key", sensor.getKey())
					.add("label", sensor.getLabel())
					.add("description", sensor.getDescription())
					.add("streams", jsonStreams);
			
			response.type("application/json");
			return jsonSensor.toString();
		});

		/*
		 * Consultar dados de um stream específico
		 */

		get("/data/:stream", (request, response) -> {
			String streamKey = request.params("stream");
			Stream stream = streamDAO.getByKey(streamKey);
			List<Data> data = dataDAO.getByStreamId(stream.getId());

			JsonArray jsonDataArray = Json.array().asArray();
			int totalSize = data.size();
			for (Data d : data) {
				JsonObject jsonData = Json.object()
						.add("timestamp", d.getTimestamp())
						.add("data", d.getValue());
				jsonDataArray.add(jsonData);
			}
			JsonObject jsonStream = Json.object()
					.add("id", stream.getId())
					.add("key", stream.getKey())
					.add("label", stream.getLabel())
					.add("unit", stream.getUnitId())
					.add("sensor", stream.getSensorId())
					.add("totalSize", totalSize).add("data", jsonDataArray);
			response.type("application/json");
			return jsonStream.toString();
		});

		/*
		 * Registrar sensor para um usuário
		 */

		post("/sensors/:user", (request, response) -> {
			JsonObject parsedJson = Json.parse(request.body()).asObject();
			String username = request.params("user");
			User user = userDAO.getByUsername(username);
			Sensor sensor = new Sensor(parsedJson.get("label").asString(), parsedJson.get("description").asString(),
					user.getId());
			sensorDAO.create(sensor);
			JsonObject responseJson = Json.object()
					.add("id", sensor.getId())
					.add("label", sensor.getLabel())
					.add("key", sensor.getKey())
					.add("description", sensor.getDescription());
			response.type("application/json");
			return responseJson.toString();
		});

		// Registrar stream para um sensor
		post("/streams/:sensor", (request, response) -> {
			JsonObject parsedJson = Json.parse(request.body()).asObject();
			String sensorKey = request.params("sensor");
			Sensor sensor = sensorDAO.getByKey(sensorKey);
			Stream stream = new Stream(parsedJson.get("label").asString(), parsedJson.get("unit").asLong(),
					sensor.getId());
			streamDAO.create(stream);
			int totalSize = dataDAO.countByStreamId(stream.getId());

			JsonObject responseJson = Json.object()
					.add("id", stream.getId())
					.add("key", stream.getKey())
					.add("label", stream.getLabel())
					.add("unit", stream.getUnitId())
					.add("sensor", stream.getSensorId())
					.add("totalSize", totalSize);
			response.type("application/json");
			return responseJson.toString();
		});

		/*
		 * Publica dado em um stream
		 */

		post("/data/:stream", (request, response) -> {
			JsonObject parsedJson = Json.parse(request.body()).asObject();
			String streamKey = request.params("stream");
			Stream stream = streamDAO.getByKey(streamKey);
			Data data = new Data(parsedJson.get("timestamp").asLong(), parsedJson.get("value").asDouble(),
					stream.getId());
			dataDAO.create(data);

			JsonObject responseJson = Json.object().add("id", data.getId()).add("timestamp", data.getTimestamp())
					.add("value", data.getValue());
			response.type("application/json");
			return responseJson.toString();
		});

		/*
		 * Criar um usuário
		 */

		post("/users", (request, response) -> {
			JsonObject parsedJson = Json.parse(request.body()).asObject();
			User user = new User(parsedJson.get("username").asString(), parsedJson.get("email").asString());
			userDAO.create(user);

			JsonObject responseJson = Json.object()
					.add("id", user.getId())
					.add("username", user.getUsername())
					.add("email", user.getEmail());
			response.type("application/json");
			return responseJson.toString();
		});

		/*
		 * Criar uma nova unidade
		 */

		post("/units", (request, response) -> {
			JsonObject parsedJson = Json.parse(request.body()).asObject();
			Unit unit = new Unit(parsedJson.get("label").asString());
			unitDAO.create(unit);

			JsonObject responseJson = Json.object()
					.add("id", unit.getId())
					.add("label", unit.getLabel());
			response.type("application/json");
			return responseJson.toString();
		});

	}

}
