package Compiler;

import Parser.Parser;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        Scanner scn = new Scanner(System.in);
        System.out.println("Please enter the equation:");
        String str = scn.nextLine();
        Parser parser = new Parser(str);
        parser.execute();
    }
}
