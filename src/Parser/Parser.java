package Parser;

import Lexer.*;

import java.util.*;

public class Parser {
    private Lexer lex = new Lexer();
    private HashSet<Character> set = new HashSet<>();
    private double initial_x = 0;
    private double initial_y = 0;
    private static int sizeM = 0;
    private static double digit = 0;
    private boolean valuable = false;
    private boolean minus = false;
    public static ArrayList<String> arrayList = new ArrayList<>();
    public Stack stack = new Stack();
    private String negativesBefore = "amalgar";
    private static Token lookAhead;
    private static Token forAhead;

    public Parser(String s) {
        Scanner scn = new Scanner(System.in);
        if (val(s)) {
            if (sizeM == 1) {
                initial_x = scn.nextDouble();
            } else if (sizeM == 2) {
                initial_x = scn.nextDouble();
                initial_y = scn.nextDouble();
            }
        }
        char[] chars = new char[s.length() + 1];
        for (int a = 0; a < s.length(); a++) {
            chars[a] = s.charAt(a);
        }
        lookAhead = lex.forward(chars);
    }


    public void sumSub() throws Exception {
        mulDiv();
        while (true) {
            if (lookAhead.tag == '+') {
                match(new Token('+'));
                mulDiv();
                arrayList.add("+ ");
                double b = ((Num) stack.pop()).value;
                double a = ((Num) stack.pop()).value;
                stack.push(new Num(a + b));
            } else if (lookAhead.tag == '-' && negativesBefore.equals("adadletter")) {
                match(new Token('-'));
                mulDiv();
                arrayList.add("- ");
                double b = ((Num) stack.pop()).value;
                double a = ((Num) stack.pop()).value;
                stack.push(new Num(a - b));
            } else
                return;
        }
    }


    private void mulDiv() throws Exception {
        pow();
        while (true) {
            if (lookAhead.tag == '*') {
                match(new Token('*'));
                pow();
                arrayList.add("* ");
                double b = ((Num) stack.pop()).value;
                double a = ((Num) stack.pop()).value;
                stack.push(new Num(a * b));
            } else if (lookAhead.tag == '/') {
                match(new Token('/'));
                pow();
                arrayList.add("/ ");
                double b = ((Num) stack.pop()).value;
                double a = ((Num) stack.pop()).value;
                stack.push(new Num(a / b));
            } else if (lookAhead.tag == Tag.DIV) {
                match(new Token(Tag.DIV));
                pow();

                arrayList.add("div ");
                double b = ((Num) stack.pop()).value;
                double a = ((Num) stack.pop()).value;
                stack.push(new Num((int) (a / b)));
            } else if (lookAhead.tag == Tag.MOD) {
                match(new Token(Tag.MOD));
                pow();

                arrayList.add("mod ");
                double b = ((Num) stack.pop()).value;
                double a = ((Num) stack.pop()).value;
                stack.push(new Num(a % b));
            } else
                return;
        }
    }


    private void pow() throws Exception {
        func();
        while (true) {
            if (lookAhead.tag == '-' && negativesBefore.equals("amalgar")) {
                minus = true;
                match((new Token('-')));
                func();
                double a = 0;
                double b = ((Num) stack.pop()).value;
                stack.push(new Num(a - b));
            }
            if (lookAhead.tag == '^') {
                match(new Token('^'));
                func();
                arrayList.add("^ ");
                double b = ((Num) stack.pop()).value;
                double a = ((Num) stack.pop()).value;
                stack.push(new Num(Math.pow(a, b)));
            } else if (lookAhead.tag == Tag.e) {
                match((new Token(Tag.e)));
                stack.push(new Num(Math.E));
                arrayList.add("e ");
            } else if (lookAhead.tag == Tag.PI) {
                match((new Token(Tag.PI)));
                stack.push(new Num(Math.PI));
                arrayList.add("pi ");
            } else
                return;
        }
    }


    private void func() throws Exception {
        while (true) {
            if (lookAhead.tag == Tag.SIN) {
                match(new Token(Tag.SIN));
                match(new Token('('));
                sumSub();
                match(new Token(')'));
                arrayList.add("sin ");
                stack.push(new Num(Math.sin(((Num) stack.pop()).value)));
            } else if (lookAhead.tag == Tag.SINH) {
                match(new Token(Tag.SINH));
                match(new Token('('));
                sumSub();
                match(new Token(')'));
                arrayList.add("sinh ");
                stack.push(new Num(Math.sinh((((Num) stack.pop()).value))));
            } else if (lookAhead.tag == Tag.ARCsin) {
                match(new Token(Tag.ARCsin));
                match(new Token('('));
                sumSub();
                match(new Token(')'));
                arrayList.add("Arcsin ");
                stack.push(new Num(Math.asin(((Num) stack.pop()).value)));
            } else if (lookAhead.tag == Tag.COS) {
                match(new Token(Tag.COS));
                match(new Token('('));
                sumSub();
                match(new Token(')'));
                arrayList.add("cos ");
                stack.push(new Num(Math.cos((((Num) stack.pop()).value))));
            } else if (lookAhead.tag == Tag.COSH) {
                match(new Token(Tag.COSH));
                match(new Token('('));
                sumSub();
                match(new Token(')'));
                arrayList.add("cosh ");
                stack.push(new Num(Math.cosh((((Num) stack.pop()).value))));
            } else if (lookAhead.tag == Tag.ARCcos) {
                match(new Token(Tag.ARCcos));
                match(new Token('('));
                sumSub();
                match(new Token(')'));
                arrayList.add("Arccos ");
                stack.push(new Num(Math.acos(((Num) stack.pop()).value)));
            } else if (lookAhead.tag == Tag.TAN) {
                match(new Token(Tag.TAN));
                match(new Token('('));
                sumSub();
                match(new Token(')'));
                arrayList.add("tan ");
                stack.push(new Num(Math.tan((((Num) stack.pop()).value))));
            } else if (lookAhead.tag == Tag.TANH) {
                match(new Token(Tag.TANH));
                match(new Token('('));
                sumSub();
                match(new Token(')'));
                arrayList.add("tanh ");
                stack.push(new Num(Math.tanh((((Num) stack.pop()).value))));
            } else if (lookAhead.tag == Tag.Arctan) {
                match(new Token(Tag.Arctan));
                match(new Token('('));
                sumSub();
                match(new Token(')'));
                arrayList.add("Arctan ");
                stack.push(new Num(Math.atan(((Num) stack.pop()).value)));
            } else if (lookAhead.tag == Tag.COT) {
                match(new Token(Tag.COT));
                match(new Token('('));
                sumSub();
                match(new Token(')'));
                arrayList.add("cot ");
                stack.push(new Num(1.0 / Math.tan((((Num) stack.pop()).value))));
            } else if (lookAhead.tag == Tag.COTH) {
                match(new Token(Tag.COTH));
                match(new Token('('));
                sumSub();
                match(new Token(')'));
                arrayList.add("coth");
                stack.push(new Num(1.0 / Math.tanh((((Num) stack.pop()).value))));
            } else if (lookAhead.tag == Tag.Arccot) {
                match(new Token(Tag.Arccot));
                match(new Token('('));
                sumSub();
                match(new Token(')'));
                arrayList.add("Arccot ");
                stack.push(new Num(1.0 / Math.atan((((Num) stack.pop()).value))));
            } else if (lookAhead.tag == Tag.SQRT) {
                match(new Token(Tag.SQRT));
                match(new Token('('));
                sumSub();
                match(new Token(')'));
                arrayList.add("sqrt ");
                stack.push(new Num(Math.sqrt(((Num) stack.pop()).value)));
            } else if (lookAhead.tag == Tag.log) {
                match(new Token(Tag.log));
                match(new Token('('));
                sumSub();
                match(new Token(')'));
                arrayList.add("log ");
                stack.push(new Num(Math.log(((Num) stack.pop()).value)));
            } else if (lookAhead.tag == Tag.exp) {
                match(new Token(Tag.exp));
                match(new Token('('));
                sumSub();
                match(new Token(')'));
                arrayList.add("exp ");
                stack.push(new Num(Math.exp(((Num) stack.pop()).value)));
            } else if (lookAhead.tag == Tag.NUM) {
                digitOrLetter();
                if (lookAhead.tag == 257) {
                    throw new Error(" you can't create a valuable with a Integer and String in a row" +
                            "in Line " + Lexer.line);
                } else {
                    arrayList.add(digit + " ");
                    stack.push(forAhead);
                }
                return;
            } else if (lookAhead.tag == Tag.ID) {
                digitOrLetter();
                return;
            } else if (lookAhead.tag == Tag.ID1) {
                digitOrLetter();
                return;
            } else if (lookAhead.tag == '(') {
                digitOrLetter();
                return;
            } else {
                negativesBefore = "amalgar";
                return;
            }
        }
    }


    private void digitOrLetter() throws Exception {
        if (lookAhead.tag == Tag.NUM) {
            negativesBefore = "adadletter";
            forAhead = lookAhead;
            if (!minus) {
                digit = ((Num) lookAhead).value;
            } else {
                digit = -1 * ((Num) lookAhead).value;
            }
            minus = false;
            match(lookAhead);
        } else if (lookAhead.tag == Tag.ID) {
            negativesBefore = "adadletter";

            stack.push(new Num(initial_x));
            arrayList.add(initial_x + " ");
            match(lookAhead);
        } else if (lookAhead.tag == Tag.ID1) {
            negativesBefore = "adadletter";

            stack.push(new Num(initial_y));
            arrayList.add(initial_y + " ");
            match(lookAhead);
        } else if (lookAhead.tag == '(') {
            match(new Token('('));
            sumSub();
            match(new Token(')'));
        } else
            throw new Error("Syntax Error on line: " + Lexer.line);
    }


    private void match(Token t) throws Exception {
        if (lookAhead.tag == t.tag) {
            lookAhead = lex.forward();
        } else {
            throw new Exception(" Syntax(Match) Error on line: " + Lexer.line);
        }
    }


    private boolean val(String s) {
        for (int i = 0; i < s.length(); i++) {
            StringBuilder sb = new StringBuilder();
            if (Character.isLetter(s.charAt(i))) {
                sb.delete(0, sb.length());
                for (int j = 0; j < s.length(); j++) {
                    if (s.charAt(j) == 'x') {
                        set.add(s.charAt(j));
                    }
                    if (s.charAt(j) == 'y') {
                        set.add(s.charAt(j));
                    }
                }
                for (int j = i; j < s.length(); j++) {
                    if (s.charAt(j) == '(' || s.charAt(j) == ')' || s.charAt(j) == '+' || s.charAt(j) == '-'
                            || s.charAt(j) == '*' || s.charAt(j) == '/' || s.charAt(j) == '^' ||
                            Character.isDigit(s.charAt(j))) {
                        continue;
                    }
                    i = j;
                }
                String ss = sb.toString();
                Word w = Lexer.hashtable.get(ss);
                if (w == null) {
                    valuable = true;
                }
            }
        }
        sizeM = set.size();
        return valuable;
    }

    public void execute() throws Exception {
        this.sumSub();
        for (String s : Parser.arrayList) {
            System.out.print(s + " ");
        }
        System.out.println();
        System.out.println(((Num) this.stack.pop()).value);
    }
}
