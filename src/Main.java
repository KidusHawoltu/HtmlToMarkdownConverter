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
                    System.out.println("The file isn't an html file");
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
                    System.out.println(dirFile.getName());
                    if (dirFile.isFile() && dirFile.getName().endsWith(".html")){
                        System.out.println(dirFile.getName());
                        htmlToMd(dirFile);
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void htmlToMd(File file) throws IOException {
        String inputString = new Scanner(file).useDelimiter("\\Z").next();
        HtmlParser htmlParser = new HtmlParser(inputString);
        String outputString = htmlParser.GetMd();
        if (outputString == null) {
            System.out.println("Failed to convert " + file.getName() + " to .md file");
            return;
        }

        File outputFile = new File(file.getAbsoluteFile().toString().replace(".html", ".md"));
        PrintWriter out = new PrintWriter(outputFile);
        out.println(outputString);
        out.close();
        SymbolTable.clear();

        System.out.println("Successfully converted " + file.getName() + " to " + outputFile.getName());
    }
}