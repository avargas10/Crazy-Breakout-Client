package Data_Handler;

import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;



public class Json_Code_Decode<T> {
	private static Json_Code_Decode _instance;

	public Json_Code_Decode() {
	}

	public static Json_Code_Decode getInstance() {
		if (_instance == null) {
			_instance = new Json_Code_Decode();
			return _instance;
		} else {
			return _instance;
		}
	}

	/**
	 * 
	 * @param key
	 *            clave de acceso
	 * @param json
	 *            elemento json al cual queremos accesar
	 * @param data
	 *            informacion que queremos introducir al json
	 */
	public void addJsonKey(String pKey, JsonObject pJson, T pData, String pType) {
		switch (pType) {
		case DataType_References.stringFile:
			(pJson).addProperty(pKey, (String) pData);
			break;
		case DataType_References.intFile:
			(pJson).addProperty(pKey, (int) pData);
			break;
		case DataType_References.doubleFile:
			(pJson).addProperty(pKey, (double) pData);
			break;
		case DataType_References.bool:
			(pJson).addProperty(pKey, (boolean) pData);
			break;
		case DataType_References.bytesArray:
			byte[] bytes = (byte[]) pData;
			pData = (T) bytes.toString();
			(pJson).addProperty(pKey, (String) (pData));
			break;
		case DataType_References.objectArray:
			Object[] array = (Object[]) pData;
			pData = (T) array.toString();
			(pJson).addProperty(pKey, (String) (pData));
			break;
		case DataType_References.jsonElement:
			(pJson).add(pKey, (JsonElement) pData);
			break;
		}
	}


	public void addJsonKeyBoolean(String key, JsonObject json, Boolean data) {
		(json).addProperty(key, data);
	}

	// Agrega una key de tipo entero a un json especifico
	public void addJsonKeyInt(String key, JsonObject json, int data) {
		(json).addProperty(key, data);
	}

	// Agrega una key de tipo double a un json especifico
	public void addJsonKeyDouble(String key, JsonObject json, double data) {
		(json).addProperty(key, data);
	}

	// se encarga de generar un json apartir de un valuestring
	public JsonElement get_Json(String data) {
		JsonParser parser = new JsonParser();
		JsonElement element = parser.parse(data);
		return element;
	}

	// Se encarga de obtener una dato de tipo string almacenado dentro de un key
	// de json
	public String get_Json_StringKey(String key, JsonElement element) {
		String StringKey = element.getAsJsonObject().get(key).getAsString();
		return StringKey;
	}

	// Se encarga de obtener una dato de tipo boolean almacenado dentro de un
	// key de json
	public boolean get_Json_BooleanKey(String key, JsonElement element) {
		boolean BooleanKey = element.getAsJsonObject().get(key).getAsBoolean();
		return BooleanKey;
	}

	// Se encarga de obtener una dato de tipo double almacenado dentro de un key
	// de json
	public double get_Json_DoubleKey(String key, JsonElement element) {
		double doubleKey = element.getAsJsonObject().get(key).getAsDouble();
		return doubleKey;
	}

	// Se encarga de obtener una dato de tipo entero almacenado dentro de un key
	// de json
	public int get_Json_IntKey(String key, JsonElement element) {
		int intKey = element.getAsJsonObject().get(key).getAsInt();
		return intKey;
	}

	public byte[] get_Json_Byte_Key(String key, JsonElement element) {
		byte[] byteArray = element.getAsJsonObject().get(key).getAsString().getBytes();
		return byteArray;
	}

	public boolean isJson(String pData) {
		try {
			JsonParser parser = new JsonParser();
			JsonElement element = parser.parse(pData);
			return true;
		} catch (JsonIOException e) {
			return false;
		}
	}
}
