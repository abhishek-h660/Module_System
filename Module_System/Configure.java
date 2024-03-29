import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.*;


public class Configure {
    
    public static List<String> Config() throws Throwable{
        ArrayList<String> list = GetDependencies();
        List<String> list1=new ArrayList<>();
        for(String link : list){
            //System.out.println(link);
            String name = link.substring(link.lastIndexOf('/')+1);
            String fileName = "./Module_System_Test/build/"+name;
            URL url  = new URL( link );
            HttpURLConnection http = (HttpURLConnection)url.openConnection();
            Map< String, List< String >> header = http.getHeaderFields();
            while( isRedirected( header )) {
                link = header.get( "Location" ).get( 0 );
                url    = new URL( link );
                http   = (HttpURLConnection)url.openConnection();
                header = http.getHeaderFields();
            }

            InputStream  input  = http.getInputStream();
            StringBuilder response = new StringBuilder();
            BufferedReader br = new BufferedReader(new InputStreamReader(input, Charset.forName("UTF-8")));
            String line;
            while ((line = br.readLine()) != null) {
                response.append(line).append("\n");
            }
            StringBuilder encoded = new StringBuilder();
            for(int i=response.indexOf("\"content\"")+11; ;i++){
                if(response.charAt(i) == '='||response.charAt(i) == '"'){
                    break;
                }
                if(response.charAt(i) == '\\'&&i!=response.length()-1&&response.charAt(i+1)=='n'){i++;continue;}
                encoded.append(response.charAt(i));
            }
            // mutable encodedContent;
            // for(int i=0;i<encoded.length()-1;i++)
            // {
            //     if(encoded.charAt(i) == '/' && encoded.charAt(i+1)=='n')continue;
            //     encodedContent.
            // }
            
            System.out.println(encoded);
            byte[] content = Base64.getDecoder().decode(encoded.toString().getBytes());
            FileWriter output = new FileWriter( fileName );
            output.append(new String(content));
            output.close();
            
            System.out.println("Internet file "+name+" is available");
            
            list1.add(name);
            
        }
        return list1;
    }
    
    public static ArrayList<String> GetDependencies() throws IOException {
        ArrayList<String> list = new ArrayList<>();
        FileReader reader = null;
        BufferedReader br = null;
        try{
            reader = new FileReader("./Module_System_Test/.config");
            br = new BufferedReader(reader);
            String line = null;
            do{
                line = br.readLine();
                if(line != null){
                    list.add(line);
                }
            }while (line != null);
        }catch(FileNotFoundException e){
            System.out.println(e.getMessage());
        }finally{
            br.close();
            reader.close();
        }
        return list;
    }

    private static boolean isRedirected( Map<String, List<String>> header ) {
        for( String hv : header.get( null )) {
           if(   hv.contains( " 301 " )
              || hv.contains( " 302 " )) return true;
        }
        return false;
    }


}
