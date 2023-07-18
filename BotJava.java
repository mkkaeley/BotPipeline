import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONObject;

public class BotJava {
    public static void main(String[] args) {
        try {
            String auth = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhcHBJZCI6ImNzLTUzYmZjNmEzLThkMTAtNTFiYS05NzZjLTVhOTMxYzg0Mzc4YSJ9.XKs7o1es4pSUIzgc9z7lZQAuZHVhif6Aq12zni8FPAw";
            String fileUploadURL = "https://bots.kore.ai/api/public/uploadfile";
            String importURL = "https://bots.kore.ai/api/public/bot/st-c99808ed-b936-5b7d-a49f-a0fad24a1a00/import";

            String botDefinitionFileID = uploadFile(fileUploadURL, auth, "botDefinition.json", "bulkImport", "json");
            String configInfoFileID = uploadFile(fileUploadURL, auth, "config.json", "bulkImport", "json");

            String importOptions = "{ \"nlpData\": [\"training_data\",\"bot_synonyms\",\"nlpSettings\",\"defaultDialog\",\"standardResponses\",\"utterances\",\"patterns\"],\"settings\": [\"botSettings\",\"ivrSettings\",\"botVariables\"],\"tasks\": [\"botTask\",\"knowledgeGraph\",\"smallTalk\"],\"options\": {\"utterances\": {\"append\": true,\"replace\": true}},\"botComponents\": [\"linkedBots\",\"smallTalk\"],\"customDashboard\": true }";
            JSONObject importBody = new JSONObject();
            importBody.put("botDefinition", botDefinitionFileID);
            importBody.put("configInfo", configInfoFileID);
            importBody.put("importOptions", new JSONObject(importOptions));

            Thread.sleep(1500);
            String importID = invokeRestMethod(importURL, "POST", importBody.toString(), auth, "application/json");

            Thread.sleep(1500);
            String statusURL = "https://bots.kore.ai/api/public/bot/import/status/" + importID;
            JSONObject status = new JSONObject(invokeRestMethod(statusURL, "GET", null, auth, null));

            System.out.println("Import status: " + status.getString("status"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String uploadFile(String url, String auth, String fileName, String fileContext, String fileExtension) throws IOException {
        URL uploadURL = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) uploadURL.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("auth", auth);
        connection.setRequestProperty("Content-Type", "multipart/form-data");
        connection.setDoOutput(true);

        File file = new File(fileName);
        String boundary = Long.toHexString(System.currentTimeMillis());
        String CRLF = "\r\n";

        connection.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);

        try (OutputStream output = connection.getOutputStream();
             PrintWriter writer = new PrintWriter(new OutputStreamWriter(output, "UTF-8"), true);
             BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)))) {

            // Send form data
            writer.append("--").append(boundary).append(CRLF);
            writer.append("Content-Disposition: form-data; name=\"file\"; filename=\"" + fileName + "\"").append(CRLF);
            writer.append("Content-Type: " + HttpURLConnection.guessContentTypeFromName(fileName)).append(CRLF);
            writer.append("Content-Transfer-Encoding: binary").append(CRLF);
            writer.append(CRLF).flush();

            // Send file data
            String line;
            while ((line = reader.readLine()) != null) {
                output.write(line.getBytes());
            }
            output.flush(); // Important before continuing with writer!

            // CRLF must be appended at the end to signal end of boundary
            writer.append(CRLF).flush();
            writer.append("--").append(boundary).append("--").append(CRLF);
            writer.close();
        }

        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                StringBuilder response = new StringBuilder();
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                return response.toString();
            }
        } else {
            throw new IOException("File upload failed with response code: " + responseCode);
        }
    }

    private static String invokeRestMethod(String url, String method, String body, String auth, String contentType) throws IOException {
        URL restURL = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) restURL.openConnection();
        connection.setRequestMethod(method);
        connection.setRequestProperty("auth", auth);

        if (contentType != null) {
            connection.setRequestProperty("Content-Type", contentType);
        }

        if (body != null) {
            connection.setDoOutput(true);
            try (OutputStream outputStream = connection.getOutputStream()) {
                outputStream.write(body.getBytes());
            }
        }

        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                StringBuilder response = new StringBuilder();
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                return response.toString();
            }
        } else {
            throw new IOException("HTTP request failed with response code: " + responseCode);
        }
    }
}
