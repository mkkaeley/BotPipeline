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
			String botDefinitionId = "64b507fb40e52c9b7a74cb7c";
			String configInfoId = "64b507c00cdd895152703c2e";

			String importBody = "{\n" + "  \"botDefinition\": \"" + botDefinitionId + "\",\n" + "  \"configInfo\": \""
					+ configInfoId + "\",\n" + "  \"importOptions\": {\n" + "    \"tasks\": [\n"
					+ "      \"botTask\",\n" + "      \"knowledgeGraph\",\n" + "      \"smallTalk\"\n" + "    ],\n"
					+ "    \"nlpData\": [\n" + "      \"nlpSettings\",\n" + "      \"utterances\",\n"
					+ "      \"patterns\",\n" + "      \"standardResponses\"\n" + "    ],\n" + "    \"settings\": [\n"
					+ "      \"botSettings\",\n" + "      \"botVariables\",\n" + "      \"ivrSettings\"\n" + "    ]\n"
					+ "  }\n" + "}";

			// Create the HttpURLConnection
			URL url = new URL("https://bots.kore.ai/api/public/bot/st-c72008d0-ce30-571b-ba13-1078b89dbef4/import");
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
