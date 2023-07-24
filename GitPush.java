import java.io.IOException;

public class GitPush {
    public static void main(String[] args) {
        String gitRepoPath = "."; // Replace this with the actual path to your Git repository
        String commitMessage = "Your commit message goes here";

        // Git commands
        String gitAdd = "git add .";
        String gitCommit = "git commit -m \"" + commitMessage + "\"";
        String gitPush = "git push";

        // Execute Git commands
        try {
            executeCommand(gitRepoPath, gitAdd);
            executeCommand(gitRepoPath, gitCommit);
            executeCommand(gitRepoPath, gitPush);
            System.out.println("Changes added, committed, and pushed successfully.");
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            System.err.println("Failed to add, commit, and push changes.");
        }
    }

    private static void executeCommand(String workingDir, String command) throws IOException, InterruptedException {
        ProcessBuilder processBuilder = new ProcessBuilder(command.split(" "));
        processBuilder.directory(new java.io.File(workingDir));
        Process process = processBuilder.start();
        int exitCode = process.waitFor();
        if (exitCode != 0) {
            throw new RuntimeException("Failed to execute command: " + command);
        }
    }
}
