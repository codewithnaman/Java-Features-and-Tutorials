package java9.feature3;

import java.io.*;
import java.util.Objects;

public class Feature3ARMFunctionPassing {

    public static void main(String[] args) throws IOException {
        File file = new File(Feature3ARMFunctionPassing.class.getClassLoader().getResource("testFile.txt").getFile());
        if(file.exists()){
            BufferedReader fileReader = new BufferedReader(new FileReader(file));
            readLines(fileReader);
           // fileReader.readLine();
        }
    }

    private static void readLines(BufferedReader reader) {
        try (reader){
            String line = null;
            while(!Objects.isNull(line = reader.readLine())){
                System.out.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
