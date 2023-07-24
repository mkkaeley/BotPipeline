// import okhttp3.*;
// import java.io.File;
// import java.io.IOException;
// import java.util.Base64;
// import org.apache.commons.io.FileUtils;

// public class Test {
//     public static void main(String[] args) throws IOException {
//         String accessToken = "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"; // Replace with your GitHub personal access token
//         String repoOwner = "bingivarun"; // Replace with the GitHub username or organization
//         String repoName = "tt"; // Replace with the GitHub repository name
//         String branchName = "main"; // Replace with the target branch (e.g., 'main', 'master')

//         // "Hello, World!" code
//         System.out.println("Hello, World!");

//         // Code to add existing file to GitHub
//         File fileToAdd = new File("C:\\Users\\gg\\Documents\\Darshana-infy\\Botkit-Master-Demo"); // Replace with the path to the file you want to add

//         OkHttpClient client = new OkHttpClient();
//         String apiUrl = String.format("https://api.github.com/repos/%s/%s/contents/%s", repoOwner, repoName, fileToAdd.getName());

//         RequestBody requestBody = new MultipartBody.Builder()
//                 .setType(MultipartBody.FORM)
//                 .addFormDataPart("message", "Add existing code")
//                 .addFormDataPart("content", Base64.getEncoder().encodeToString(FileUtils.readFileToByteArray(fileToAdd)))
//                 .addFormDataPart("branch", branchName)
//                 .build();

//         Request request = new Request.Builder()
//                 .url(apiUrl)
//                 .header("Authorization", "token " + accessToken)
//                 .post(requestBody)
//                 .build();

//         Response response = client.newCall(request).execute();
//         System.out.println("Response code: " + response.code());
//         System.out.println("Response body: " + response.body().string());
//     }
// }

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;

import java.io.File;

public class Test {

    public static void main(String[] args) {
        // Replace these variables with your GitHub username, password, and repository name
        String username = "bingivarun";
        String password = "Nurav_30";
        String repositoryName = "tt";
        
        // Replace with your project directory path
        String projectDirectoryPath = "C:\\Users\\gg\\Documents\\Darshana-infy\\Botkit-Master-Demo";

        try {
            // Initialize a new Git repository in your project directory
            Git git = Git.init().setDirectory(new File(projectDirectoryPath)).call();

            // Add all the files in your project directory to the staging area
            git.add().addFilepattern(".").call();

            // Create a commit with your added code
            git.commit().setMessage("Initial commit").call();

            // Create a remote configuration for the GitHub repository
            String remoteUrl = "https://" + username + ":" + password + "@github.com/" + username + "/" + repositoryName
                    + ".git";
            git.remoteAdd().setName("origin").setUri(remoteUrl).call();

            // Push your code to the GitHub repository
            git.push().setCredentialsProvider(new UsernamePasswordCredentialsProvider(username, password)).call();

            System.out.println("Code uploaded to GitHub successfully!");
        } catch (GitAPIException e) {
            e.printStackTrace();
            System.out.println("Failed to upload code to GitHub.");
        }
    }
}
