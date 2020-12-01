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

    /**
     * Read the source code from program/program.txt
     *
     * @return String
     * @Exception IOException
     */
    private static String getSource() throws IOException {
        return new String(Files.readAllBytes(Paths.get(System.getProperty("user.dir") + "\\program\\program.txt")));
    }

    /**
     * Get the source code and read it line by line and prune the comments from it then return the pruned source code.
     *
     * @return String
     */
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
