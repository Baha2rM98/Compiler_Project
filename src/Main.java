import Parser.Parser;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) throws Exception {
        String sourceCode = pruneComments(getSource());
        String[] lines = sourceCode.split("\r\n|\r|\n");
        if (lines.length > 1) {
            throw new Exception("Your source code must contains just one main line.");
        }
        new Parser(sourceCode).run();
    }

    private static String getSource() throws IOException {
        return new String(Files.readAllBytes(Paths.get(System.getProperty("user.dir") + "\\program\\program.txt")));
    }

    private static String pruneComments(String sourceCode) {
        StringBuilder stringBuilder = new StringBuilder(sourceCode);
        String temp;
        do {
            for (int i = 0; i < stringBuilder.length(); i++) {
                if (stringBuilder.charAt(i) == '{') {
                    stringBuilder.delete(stringBuilder.indexOf("{"), stringBuilder.indexOf("}") + 3);
                } else if (stringBuilder.charAt(i) == '/' && stringBuilder.charAt(i + 1) == '/') {
                    stringBuilder.delete(i, stringBuilder.indexOf("\n", i) + 1);
                }
            }
            temp = stringBuilder.toString();
        } while ((temp.contains("{") && temp.contains("}")) || temp.contains("\\\\"));
        return temp;
    }
}
