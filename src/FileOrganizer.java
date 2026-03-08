import java.io.IOException;
import java.nio.file.*;
import java.util.Scanner;

public class FileOrganizer {
   static void main(){
        System.out.println("Enter the path to organize");

        Scanner scanner = new Scanner(System.in);

        try{
            String folderPath = scanner.nextLine().trim();
            Path sourcePath = Paths.get(folderPath);

            if(!Files.isDirectory(sourcePath)){
                System.out.println("Invalid folder path");
                return;
            }

            try(DirectoryStream<Path> stream = Files.newDirectoryStream(sourcePath)) {
                for (Path file : stream) {
                    if (Files.isDirectory(file)) continue;

                    String fileName = file.getFileName().toString().toLowerCase();
                    String category = getCategory(fileName);
                    Path categoryPath = sourcePath.resolve(category);

                    if (!Files.exists(categoryPath)) {
                        Files.createDirectory(categoryPath);
                    }

                    Path destinationPath = categoryPath.resolve(file.getFileName());
                    Files.move(file, destinationPath,
                            StandardCopyOption.REPLACE_EXISTING);

                    System.out.println("Moved: " + file.getFileName() + " -> " + destinationPath.toAbsolutePath());
                }
            }

        }catch(IOException e){
            System.err.println("File error" + e.getMessage());
        }finally{
            scanner.close();}
    }

    private static String getCategory(String fileName) {
        if (fileName.endsWith(".jpg") || fileName.endsWith(".png")||fileName.endsWith(".bmp")) {
            return "Images";
        } else if (fileName.endsWith(".pdf")) {
            return "PDFs";
        } else if (fileName.endsWith(".txt") || fileName.endsWith(".docx")) {
            return "Documents";
        }else if(fileName.endsWith(".java") || fileName.endsWith(".py")){
            return "Code";
        }else{
            return "Other";
        }


    }


}
