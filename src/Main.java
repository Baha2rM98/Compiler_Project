import Parser.Parser;

import java.nio.file.Files;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) throws Exception {
        if (args.length < 1) {
            throw new Exception("Specific a file path.");
        }
        if (args.length > 1) {
            throw new Exception("Specific a file path.");
        }
        String sourceCode = pruneComments(new String(Files.readAllBytes(Paths.get(System.getProperty("user.dir") + "\\" + args[0].replace("/", "\\")))));
        String[] lines = sourceCode.split("\r\n|\r|\n");
        if (lines.length > 1) {
            throw new Exception("Your source code must contains just one main line.");
        }
        new Parser(sourceCode).run();
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
