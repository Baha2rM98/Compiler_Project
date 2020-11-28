import Parser.Parser;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) throws Exception {
        int counter = 0;
        String sourceCode = pruneComments(getSource());
        String[] lines = sourceCode.split("\r\n|\r|\n");
        while (counter != lines.length) {
            new Parser(sourceCode).run();
            counter++;
        }
    }

    private static String getSource() throws IOException {
        return new String(Files.readAllBytes(Paths.get(System.getProperty("user.dir") + "\\program\\program.txt")));
    }

    private static String pruneComments(String sourceCode) {
        StringBuilder stringBuilder = new StringBuilder(sourceCode);
        String temp;
//        int lastLine = -1;
//        int pos = 0;
        do {
            for (int i = 0; i < stringBuilder.length(); i++) {
                if (stringBuilder.charAt(i) == '{') {
                    stringBuilder.delete(stringBuilder.indexOf("{"), stringBuilder.indexOf("}") + 3);
                } else if (stringBuilder.charAt(i) == '/' && stringBuilder.charAt(i + 1) == '/') {
//                    if (lastLine == pos) {
//                        return "";
//                    }
//                        pos++;
                    stringBuilder.delete(i, stringBuilder.indexOf("\n", i) + 1);

                }
            }
            temp = stringBuilder.toString();
//            lastLine = temp.split("\r\n|\r|\n").length;
        } while ((temp.contains("{") && temp.contains("}")) || temp.contains("\\\\"));
        return temp;
    }
}
