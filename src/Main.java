import java.io.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("\nEnter the path of the html file or the directory containing html files\n");
        String input = scanner.nextLine();
        scanner.close();
        try {
            File inputFile = new File(input);
            if (!inputFile.exists()) {
                System.out.println("Couldn't find " + inputFile.getName());
            }
            else if (inputFile.isFile()) {
                if (!inputFile.getName().endsWith("html")) {
                    System.out.println("The file isn't a html file");
                    return;
                }
                htmlToMd(inputFile);
            }
            else if (inputFile.isDirectory()){
                File[] files = inputFile.listFiles();
                if (files == null) {
                    System.out.println("The folder is empty");
                    return;
                }
                for (File dirFile : files) {
                    if (dirFile.isFile() && dirFile.getName().endsWith(".html")){
                        htmlToMd(dirFile);
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void htmlToMd(File file) throws IOException {
//        System.out.println("start");
//        System.out.println(file);
//        FileInputStream fileInputStream = new FileInputStream(file);
//        System.out.println(fileInputStream.toString());
//        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
//        System.out.println("1a");
//        String inputString = objectInputStream.readUTF();
        String inputString = new Scanner(file).useDelimiter("\\Z").next();
        System.out.println("2nd");
        System.out.println(inputString);
        HtmlParser htmlParser = new HtmlParser(inputString);
        System.out.println("3rd");
        String outputString = htmlParser.GetMd();
        System.out.println("hahaha");
        if (outputString == null) {
            System.out.println("Failed to convert " + file.getName() + " to .md file");
            return;
        }

//        FileOutputStream fileOutputStream = new FileOutputStream(outputFile);
//        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
//        objectOutputStream.writeUTF(outputString);
//        objectOutputStream.flush();

        File outputFile = new File(file.getAbsoluteFile().toString().replace(".html", ".md"));
        PrintWriter out = new PrintWriter(outputFile);
        out.println(outputString);
        out.close();
        SymbolTable.clear();

        System.out.println("Successfully converted " + file.getName() + " to " + outputFile.getName());
    }
}