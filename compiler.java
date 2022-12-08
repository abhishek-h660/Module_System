import java.util.*;
import java.io.*;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

class compiler {
    public static void main(String[] args) throws IOException {
        String path = args[0];
        path=path+"\\src\\";
        
        //check for the files
        List<String> files = getFileNames(path);
        System.out.println("Files : ");
        for(String p : files){
            System.out.println(p);
        }
        System.out.println();
        //Segregation of files into respective modeule
        Map<String, List<String>> modules = segregate(files);
        
        for(Map.Entry<String, List<String>> entry : modules.entrySet()) {
          System.out.print(entry.getKey()+" -> ");
          for(String val : entry.getValue()) {
            System.out.print(val+", ");
          }
          System.out.println();
        }
    }

    //Get the path of each file inside the src folder
    public static List<String> getFileNames(String path) {
        List<String> fileNames = new ArrayList<>();
        try {
            DirectoryStream<Path> directoryStream = Files.newDirectoryStream(Paths.get(path));
            for (Path p : directoryStream) {
              fileNames.add(p.toString());
            }
          } catch (IOException ex) {
            ex.printStackTrace();
          }
        return fileNames;
    }

    //Categorise files into respective modules
    private static Map<String, List<String>> segregate(List<String> list) throws IOException {
        Map<String, List<String>> modules = new HashMap<>();

        for(String path : list) {
          try{
            FileReader reader = new FileReader(path);
            BufferedReader br = new BufferedReader(reader);
            String line = br.readLine();

            reader.close();
            String res[] = line.split(" ");

            if(!res[0].equals("#module")){
              System.out.println("module is not defined");
            }else{
              List<String> moduleList = modules.get(res[1]);
              if(moduleList == null){
                moduleList = new ArrayList<>();
              }
              moduleList.add(path);
              modules.put(res[1], moduleList);
            }
              
          }catch(FileNotFoundException exception) {
            exception.printStackTrace();
          }

        }

        return modules;
    }

}