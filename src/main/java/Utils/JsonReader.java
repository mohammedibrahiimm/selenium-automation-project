package Utils;

import com.jayway.jsonpath.JsonPath;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class JsonReader {

    private final String jsonContent;
    private final String jsonFileName;

    public JsonReader(String jsonFileName) {
        this.jsonFileName = jsonFileName;
        String resource = jsonFileName.endsWith(".json")
                ? jsonFileName
                : jsonFileName + ".json";

        try (InputStream is = getClass().getClassLoader().getResourceAsStream(resource)) {
            if (is == null) {
                throw new IllegalStateException("JSON not found on classpath: " + resource);
            }
            JSONObject data = (JSONObject) new JSONParser()
                    .parse(new InputStreamReader(is, StandardCharsets.UTF_8));
            this.jsonContent = data.toJSONString();
        } catch (Exception e) {
            throw new RuntimeException("Failed to load JSON: " + resource, e);
        }
    }

    public String getJsonData(String jsonPath) {
        try {
            Object value = JsonPath.read(jsonContent, jsonPath);
            return value == null ? "" : value.toString();
        } catch (Exception e) {
            throw new RuntimeException(
                    "Failed to read path '" + jsonPath + "' in " + jsonFileName, e);
        }
    }
}