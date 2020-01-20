package Compiler;

import Lexer.Num;
import Parser.Parser;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        Scanner scn = new Scanner(System.in);
        System.out.println("Please enter the equation:");
        String str = scn.nextLine();
        Parser parse = new Parser(str);
        parse.sumSub();
        for (String s : Parser.arrayList) {
            System.out.print(s + " ");
        }
        System.out.println();
        System.out.println(((Num) parse.stack.pop()).value);
    }
}