import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class BotJava {

    public static void main(String[] args) {

        String url = "https://bots.kore.ai/api/public/uploadfile";

        String botDefinitionAuth = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhcHBJZCI6ImNzLWVmYTk1Yjc4LTc4NDYtNTQ5MC1iNzc1LTg1ODJkYTgxY2RhNSJ9.9gODvgXbG2DOtOvVfLyM2Jea-G2Tvx69C0JnbANpX7A";
        String botDefinitionFilePath = "botDefinition.json";
        String botDefinitionFileContext = "bulkImport";
        String botDefinitionFileExtension = "json";

        String filePath = "botDefinition.json";

        try {

            File file = new File(filePath);
            byte[] fileContent = readFileToByteArray(file);

            URL requestUrl = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) requestUrl.openConnection();

            String authString = botDefinitionAuth + ":" + botDefinitionFilePath + ":" + botDefinitionFileContext + ":"
                    + botDefinitionFileExtension;
            String encodedAuth = Base64.getEncoder().encodeToString(authString.getBytes(StandardCharsets.UTF_8));
            String authHeader = "Basic " + encodedAuth;
            connection.setRequestProperty("Authorization", authHeader);

            connection.setDoInput(true);
            connection.setDoOutput(true);

            connection.setRequestMethod("POST");

            connection.setRequestProperty("Content-Type", "multipart/form-data");

            try (OutputStream outputStream = connection.getOutputStream()) {
                outputStream.write(fileContent);
                outputStream.flush();
            }

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {

                try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                    String line;
                    StringBuilder response = new StringBuilder();
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }
                    System.out.println("Response: " + response.toString());
                }
            } else {

                System.out.println("Error: " + responseCode );
                System.out.println(connection.getErrorStream());
            }

            connection.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static byte[] readFileToByteArray(File file) throws IOException {
        try (InputStream inputStream = new FileInputStream(file)) {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
            return outputStream.toByteArray();
        }
    }
}