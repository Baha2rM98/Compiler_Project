package compiler;

import Parser.Parser;

import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        String exp = scanner.next();
        Parser parser = new Parser(exp);
        parser.expr();
        System.out.println();
    }
}
