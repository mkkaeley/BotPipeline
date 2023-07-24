// package NewCode;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

// import org.codehaus.jackson.JsonNode;
// import org.codehaus.jackson.JsonParser;
// import org.json.JSONML;

// import com.github.lbovolini.mapper.ObjectMapper;

public class ImportJava {
	public static void main(String[] args) {
		try {
			String auth = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhcHBJZCI6ImNzLTUzYmZjNmEzLThkMTAtNTFiYS05NzZjLTVhOTMxYzg0Mzc4YSJ9.XKs7o1es4pSUIzgc9z7lZQAuZHVhif6Aq12zni8FPAw";

			// Create the JSON payload
			String botDefinitionId = "64ba901fae2da550a05b1809";
			String configInfoId = "64ba9029a5297b05ffc994ba";
			// String botDefinitionId = "64baa483c24ebd8cc67a32a6";
			// String configInfoId = "64baa482843d5b9bd9be0ee6";


					String importBody = "{\n" +
                    "    \"botDefinition\": \"" + botDefinitionId + "\",\n" +
                    "    \"configInfo\": \"" + configInfoId  + "\",\n" +
                    "    \"importOptions\": {\n" +
                    "        \"nlpData\": [\n" +
                    "            \"training_data\",\n" +
                    "            \"bot_synonyms\",\n" +
                    "            \"nlpSettings\",\n" +
                    "            \"defaultDialog\",\n" +
                    "            \"standardResponses\",\n" +
                    "            \"utterances\",\n" +
                    "            \"patterns\"\n" +
                    "        ],\n" +
                    "        \"settings\": [\n" +
                    "            \"botSettings\",\n" +
                    "            \"ivrSettings\",\n" +
                    "            \"botVariables\"\n" +
                    "        ],\n" +
                    "        \"tasks\": [\n" +
                    "            \"botTask\",\n" +
                    "            \"knowledgeGraph\",\n" +
                    "            \"smallTalk\"\n" +
                    "        ],\n" +
                    "        \"options\": {\n" +
                    "            \"utterances\": {\n" +
                    "                \"append\": true,\n" +
                    "                \"replace\": true\n" +
                    "            }\n" +
                    "        },\n" +
                    "        \"botComponents\": [\n" +
                    "            \"linkedBots\",\n" +
                    "            \"smallTalk\"\n" +
                    "        ],\n" +
                    "        \"customDashboard\": true\n" +
                    "    }\n" +
                    "}";

			// Create the HttpURLConnection
			URL url = new URL("https://bots.kore.ai/api/public/bot/st-c99808ed-b936-5b7d-a49f-a0fad24a1a00/import");
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("POST");
			connection.setRequestProperty("auth", auth);
			connection.setRequestProperty("content-type", "application/json");
			connection.setDoOutput(true);

			// Write the payload to the request body
			try (OutputStream outputStream = connection.getOutputStream()) {
				outputStream.write(importBody.getBytes());
			}

			// Read the response
			BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			StringBuilder responseBuilder = new StringBuilder();
			String line;
			while ((line = reader.readLine()) != null) {
				responseBuilder.append(line);
			}
			reader.close();
			
			

			// Print the response
			System.out.println("Import Sucess : : " + connection.getResponseCode());
			System.out.println("Response Body: " + responseBuilder.toString());

			// Close the connection
			connection.disconnect();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
