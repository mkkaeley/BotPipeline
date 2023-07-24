import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

public class UploadFile {

    public static void main(String[] args) throws IOException {
        String apiUrl = "https://bots.kore.ai/api/public/uploadfile";
        String authToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhcHBJZCI6ImNzLWVmYTk1Yjc4LTc4NDYtNTQ5MC1iNzc1LTg1ODJkYTgxY2RhNSJ9.9gODvgXbG2DOtOvVfLyM2Jea-G2Tvx69C0JnbANpX7A";
        String[] fileNames = { "C:/Users/gg/Documents/Darshana-infy/Botkit-Master-Demo/ExportBot/botDefinition.json",
                "C:/Users/gg/Documents/Darshana-infy/Botkit-Master-Demo/ExportBot/config.json" };
        String fileContext = "bulkImport";
        String fileExtension = "json";
        String boundary = "------------------------abcdef1234567890";

        for (String filePath : fileNames) {
            String postData = "--" + boundary + "\r\n" + "Content-Disposition: form-data; name=\"fileContext\"\r\n\r\n"
                    + fileContext + "\r\n" + "--" + boundary + "\r\n"
                    + "Content-Disposition: form-data; name=\"fileExtension\"\r\n\r\n" + fileExtension + "\r\n" + "--"
                    + boundary + "\r\n" + "Content-Disposition: form-data; name=\"file\"; filename=\""
                    + Paths.get(filePath).getFileName() + "\"\r\n" + "Content-Type: application/octet-stream\r\n\r\n";

            String endBoundary = "\r\n--" + boundary + "--\r\n";

            byte[] fileBytes = Files.readAllBytes(Paths.get(filePath));

            URL url = new URL(apiUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("auth", authToken);
            connection.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);

            connection.setDoOutput(true);
            OutputStream outputStream = connection.getOutputStream();
            outputStream.write(postData.getBytes());
            outputStream.write(fileBytes);
            outputStream.write(endBoundary.getBytes());
            outputStream.flush();
            outputStream.close();

            int responseCode = connection.getResponseCode();
            System.out.println("Upload Success : : " + responseCode);

            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            // Print the response from the server
            System.out.println("Response from Server:");
            System.out.println(response.toString());

        }
    }
}
    

