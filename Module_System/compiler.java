import java.util.*;
import java.io.*;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

class compiler {
    public static void main(String[] args) throws IOException {
        //Expects Target path at command line
        String path = args[0];
        
        //Include the depencencies from internet
        Configure config = new Configure();
        try {
          config.Config();
        }catch (Throwable e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }
       

        //check for the files
        //List<String> files = getFileNames(path);
        List<String> files = new ArrayList<>();
        findModules(path+"/src/", files);
        printFileNames(files);
        
        //Segregation of files into respective modeule
        Map<String, List<String>> modules = segregate(files);
        printModules(modules);

        //generate files
        generateFiles(path+"/build/", modules);

        //Run shell commands
      

        //Object file generation commands
        List<String> linkerCmds = new ArrayList<>();
        linkerCmds.add("gcc");
        linkerCmds.add("main.o");
        //makeObjectCommands("/abhishek/Module_System_Test/build/main.c");
        for(Map.Entry<String, List<String>> entry : modules.entrySet()){
          makeObjectCommands("/abhishek/Module_System_Test/build/"+entry.getKey()+".c");
          linkerCmds.add(entry.getKey()+".o");
        }

        makeObjectCommands("/abhishek/Module_System_Test/build/main.c");

        linkerCommand(linkerCmds);
        
    }

    // < Deprecated !!>
    //Get the path of each file inside the src folder
    private static List<String> getFileNames(String path) {
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

    //print file paths
    private static void printFileNames(List<String> files) {
      System.out.println("Files : ");
      for(String p : files){
          System.out.println(p);
      }
      System.out.println();
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
            if(line == null) {
              continue;
            }
            String res[] = line.split(" ");

            if(!res[0].equals("##module")){
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

    private static void printModules(Map<String, List<String>> modules) {
      for(Map.Entry<String, List<String>> entry : modules.entrySet()) {
        System.out.print(entry.getKey()+" -> ");
        for(String val : entry.getValue()) {
          System.out.print(val+", ");
        }
        System.out.println();
      }
    }

    //Generate header files
    private static void generateFiles(String root, Map<String, List<String>> modules) throws IOException {
      FileReader reader = null;
      BufferedReader bufferedReader = null;
      FileWriter writerh = null, writerc = null;
      for(Map.Entry<String, List<String>> entry : modules.entrySet()) {

        writerh = new FileWriter(root + entry.getKey() + ".h");
        writerc = new FileWriter(root + entry.getKey() + ".c");
        writerc.append("#include \""+entry.getKey()+".h\"\n");
        writerh.append("#pragma once\n");

        for(String file : entry.getValue()) {
          try {
            reader = new FileReader(file);
            bufferedReader = new BufferedReader(reader);
            
            boolean isPublic = false;

            String line = null;

            while((line = bufferedReader.readLine()) != null){
              String words[] = line.split(" ");
              if(isPublic) {
                if(words[0].equals("##end_public")){
                  isPublic = false;
                  continue;
                }
              }
              if(words[0].equals("##public")){
                isPublic = true;
                continue;
              }
              
              if(words[0].indexOf("##") >= 0){
                continue;
              }
              
              writerc.append(line+"\n");
              if(isPublic) {
                for (int i=0; i<line.length(); i++){
                  if(line.charAt(i) == '{'){
                    writerh.append(";");
                  }else if(line.charAt(i) == '}'){
                    continue;
                  }else{
                    writerh.append(line.charAt(i));
                  }
                }
                isPublic = false;
                writerh.append("\n");
              }
            }
            reader.close();
            bufferedReader.close();
          } catch (Exception e) {
            e.printStackTrace();
          }

        }
        writerh.close();
        writerc.close();
      }
    }



    //Recursively find the files
    private static void findModules(String path, List<String> fileNames) {
      if(path.indexOf(".")>=0) {
        return;
      }

      try {
          DirectoryStream<Path> directoryStream = Files.newDirectoryStream(Paths.get(path));
          for (Path p : directoryStream) {
            if(p.toString().indexOf(".") >= 0){
              fileNames.add(p.toString());
            }else{
              findModules(p.toString(), fileNames);
            }
          }
        } catch (IOException ex) {
          ex.printStackTrace();
        }
    }

    private static void makeObjectCommands(String cmd){
      ProcessBuilder processBuilder = new ProcessBuilder();
      System.out.println("commands : "+cmd);
      processBuilder.command("gcc", "-O", "-c",cmd);
      try {
        Process process = processBuilder.start();
        int exitVal = process.waitFor();
        
        if (exitVal == 0) {
          System.out.println("Success!");
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

    private static void linkerCommand(List<String> cmd) {
      ProcessBuilder processBuilder = new ProcessBuilder(cmd);
      try {
        Process process = processBuilder.start();
        int exitVal = process.waitFor();
        
        if (exitVal == 0) {
          System.out.println("Success!");
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
