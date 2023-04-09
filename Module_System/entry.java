import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class entry {
    public static void main(String[] args) {
        List<String> cmd = new ArrayList<>();
        cmd.add("javac");
        cmd.add("compiler.java");

        runShellCommands(cmd);
        cmd = new ArrayList<>();
        cmd.add("java");
        cmd.add("compiler");
        cmd.add("/abhishek/Module_System_Test");
        runShellCommands(cmd);

        cmd = new ArrayList<>();
        cmd.add("./a.out");
        runShellCommands(cmd);
    }
    private static void runShellCommands(List<String> cmd){
        ProcessBuilder processBuilder = new ProcessBuilder();
        System.out.println("commands : "+cmd);
        processBuilder.command(cmd);
        try {
          Process process = processBuilder.start();
          int exitVal = process.waitFor();
          
          if (exitVal == 0) {
            System.out.println("Success!");
            BufferedReader reader = new BufferedReader(
                new InputStreamReader(process.getInputStream()));

        String line;
        while ((line = reader.readLine()) != null) {
          System.out.println(line);
        }
              } else {
            System.out.println("Failed !");
            System.out.println(exitVal);
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(process.getErrorStream()));
  
            String line;
            while ((line = reader.readLine()) != null) {
              System.out.println(line);
            }
              }
        } catch (Exception e) {
          System.out.println("Failed ..");
          e.printStackTrace();
        }
      }
}
