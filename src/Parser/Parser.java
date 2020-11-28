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
    private static ArrayList<String> sourceTokens = new ArrayList<>();
    private Stack stack = new Stack();
    private String negativeBefore = "unary";
    private static Token lookAhead;
    private static Token forAhead;

    public Parser(String source) {
        Scanner scn = new Scanner(System.in);
        if (val(source)) {
            if (sizeM == 1) {
                initial_x = scn.nextDouble();
            } else if (sizeM == 2) {
                initial_x = scn.nextDouble();
                initial_y = scn.nextDouble();
            }
        }
        char[] chars = new char[source.length() + 1];
        for (int i = 0; i < source.length(); i++) {
            chars[i] = source.charAt(i);
        }
        lookAhead = this.lex.forward(chars);
    }

    public void sumSub() throws Exception {
        mulDiv();
        while (true) {
            if (lookAhead.token == '+') {
                match(new Token('+'));
                mulDiv();
                sourceTokens.add("+ ");
                double b = ((Num) this.stack.pop()).value;
                double a = ((Num) this.stack.pop()).value;
                this.stack.push(new Num(a + b));
            } else if (lookAhead.token == '-' && this.negativeBefore.equals("digitLetter")) {
                match(new Token('-'));
                mulDiv();
                sourceTokens.add("- ");
                double b = ((Num) this.stack.pop()).value;
                double a = ((Num) this.stack.pop()).value;
                this.stack.push(new Num(a - b));
            } else
                return;
        }
    }

    private void mulDiv() throws Exception {
        pow();
        while (true) {
            if (lookAhead.token == '*') {
                match(new Token('*'));
                pow();
                sourceTokens.add("* ");
                double b = ((Num) this.stack.pop()).value;
                double a = ((Num) this.stack.pop()).value;
                this.stack.push(new Num(a * b));
            } else if (lookAhead.token == '/') {
                match(new Token('/'));
                pow();
                sourceTokens.add("/ ");
                double b = ((Num) this.stack.pop()).value;
                double a = ((Num) this.stack.pop()).value;
                this.stack.push(new Num(a / b));
            } else if (lookAhead.token == Flag.DIV) {
                match(new Token(Flag.DIV));
                pow();

                sourceTokens.add("div ");
                double b = ((Num) this.stack.pop()).value;
                double a = ((Num) this.stack.pop()).value;
                this.stack.push(new Num((int) (a / b)));
            } else if (lookAhead.token == Flag.MOD) {
                match(new Token(Flag.MOD));
                pow();

                sourceTokens.add("mod ");
                double b = ((Num) this.stack.pop()).value;
                double a = ((Num) this.stack.pop()).value;
                this.stack.push(new Num(a % b));
            } else
                return;
        }
    }

    private void pow() throws Exception {
        function();
        while (true) {
            if (lookAhead.token == '-' && this.negativeBefore.equals("unary")) {
                this.minus = true;
                match((new Token('-')));
                function();
                double a = 0;
                double b = ((Num) this.stack.pop()).value;
                this.stack.push(new Num(a - b));
            }
            if (lookAhead.token == '^') {
                match(new Token('^'));
                function();
                sourceTokens.add("^ ");
                double b = ((Num) this.stack.pop()).value;
                double a = ((Num) this.stack.pop()).value;
                this.stack.push(new Num(Math.pow(a, b)));
            } else if (lookAhead.token == Flag.E) {
                match((new Token(Flag.E)));
                this.stack.push(new Num(Math.E));
                sourceTokens.add("e ");
            } else if (lookAhead.token == Flag.PI) {
                match((new Token(Flag.PI)));
                this.stack.push(new Num(Math.PI));
                sourceTokens.add("pi ");
            } else
                return;
        }
    }

    private void function() throws Exception {
        while (true) {
            if (lookAhead.token == Flag.SIN) {
                match(new Token(Flag.SIN));
                match(new Token('('));
                sumSub();
                match(new Token(')'));
                sourceTokens.add("sin ");
                this.stack.push(new Num(Math.sin(((Num) this.stack.pop()).value)));
            } else if (lookAhead.token == Flag.SINH) {
                match(new Token(Flag.SINH));
                match(new Token('('));
                sumSub();
                match(new Token(')'));
                sourceTokens.add("sinh ");
                this.stack.push(new Num(Math.sinh((((Num) this.stack.pop()).value))));
            } else if (lookAhead.token == Flag.ARC_SIN) {
                match(new Token(Flag.ARC_SIN));
                match(new Token('('));
                sumSub();
                match(new Token(')'));
                sourceTokens.add("arcsin ");
                this.stack.push(new Num(Math.asin(((Num) this.stack.pop()).value)));
            } else if (lookAhead.token == Flag.COS) {
                match(new Token(Flag.COS));
                match(new Token('('));
                sumSub();
                match(new Token(')'));
                sourceTokens.add("cos ");
                this.stack.push(new Num(Math.cos((((Num) this.stack.pop()).value))));
            } else if (lookAhead.token == Flag.COSH) {
                match(new Token(Flag.COSH));
                match(new Token('('));
                sumSub();
                match(new Token(')'));
                sourceTokens.add("cosh ");
                this.stack.push(new Num(Math.cosh((((Num) this.stack.pop()).value))));
            } else if (lookAhead.token == Flag.ARC_COS) {
                match(new Token(Flag.ARC_COS));
                match(new Token('('));
                sumSub();
                match(new Token(')'));
                sourceTokens.add("arccos ");
                this.stack.push(new Num(Math.acos(((Num) this.stack.pop()).value)));
            } else if (lookAhead.token == Flag.TAN) {
                match(new Token(Flag.TAN));
                match(new Token('('));
                sumSub();
                match(new Token(')'));
                sourceTokens.add("tan ");
                this.stack.push(new Num(Math.tan((((Num) this.stack.pop()).value))));
            } else if (lookAhead.token == Flag.TANH) {
                match(new Token(Flag.TANH));
                match(new Token('('));
                sumSub();
                match(new Token(')'));
                sourceTokens.add("tanh ");
                this.stack.push(new Num(Math.tanh((((Num) this.stack.pop()).value))));
            } else if (lookAhead.token == Flag.ARC_TAN) {
                match(new Token(Flag.ARC_TAN));
                match(new Token('('));
                sumSub();
                match(new Token(')'));
                sourceTokens.add("arctan ");
                this.stack.push(new Num(Math.atan(((Num) this.stack.pop()).value)));
            } else if (lookAhead.token == Flag.COT) {
                match(new Token(Flag.COT));
                match(new Token('('));
                sumSub();
                match(new Token(')'));
                sourceTokens.add("cot ");
                this.stack.push(new Num(1.0 / Math.tan((((Num) this.stack.pop()).value))));
            } else if (lookAhead.token == Flag.COTH) {
                match(new Token(Flag.COTH));
                match(new Token('('));
                sumSub();
                match(new Token(')'));
                sourceTokens.add("coth");
                this.stack.push(new Num(1.0 / Math.tanh((((Num) this.stack.pop()).value))));
            } else if (lookAhead.token == Flag.ARC_COT) {
                match(new Token(Flag.ARC_TAN));
                match(new Token('('));
                sumSub();
                match(new Token(')'));
                sourceTokens.add("arccot ");
                this.stack.push(new Num(1.0 / Math.atan((((Num) this.stack.pop()).value))));
            } else if (lookAhead.token == Flag.SQRT) {
                match(new Token(Flag.SQRT));
                match(new Token('('));
                sumSub();
                match(new Token(')'));
                sourceTokens.add("sqrt ");
                this.stack.push(new Num(Math.sqrt(((Num) this.stack.pop()).value)));
            } else if (lookAhead.token == Flag.SQR) {
                match(new Token(Flag.SQR));
                match(new Token('('));
                sumSub();
                match(new Token(')'));
                sourceTokens.add("sqr ");
                this.stack.push(new Num(Math.pow(((Num) this.stack.pop()).value, 2)));
            } else if (lookAhead.token == Flag.LOG) {
                match(new Token(Flag.LOG));
                match(new Token('('));
                sumSub();
                match(new Token(')'));
                sourceTokens.add("log ");
                this.stack.push(new Num(Math.log(((Num) this.stack.pop()).value) / Math.log(10)));
            } else if (lookAhead.token == Flag.EXP) {
                match(new Token(Flag.EXP));
                match(new Token('('));
                sumSub();
                match(new Token(')'));
                sourceTokens.add("exp ");
                this.stack.push(new Num(Math.exp(((Num) this.stack.pop()).value)));
            } else if (lookAhead.token == Flag.NUM) {
                digitOrLetter();
                if (lookAhead.token == 257) {
                    throw new Error(" you can't create a valuable with a Integer and String in a row" + "in Line " + Lexer.line);
                } else {
                    sourceTokens.add(digit + " ");
                    this.stack.push(forAhead);
                }
                return;
            } else if (lookAhead.token == Flag.ID) {
                digitOrLetter();
                return;
            } else if (lookAhead.token == Flag.ID1) {
                digitOrLetter();
                return;
            } else if (lookAhead.token == '(') {
                digitOrLetter();
                return;
            } else {
                this.negativeBefore = "unary";
                return;
            }
        }
    }

    private void digitOrLetter() throws Exception {
        if (lookAhead.token == Flag.NUM) {
            this.negativeBefore = "digitLetter";
            forAhead = lookAhead;
            if (!this.minus) {
                digit = ((Num) lookAhead).value;
            } else {
                digit = -1 * ((Num) lookAhead).value;
            }
            this.minus = false;
            match(lookAhead);
        } else if (lookAhead.token == Flag.ID) {
            this.negativeBefore = "digitLetter";
            this.stack.push(new Num(initial_x));
            sourceTokens.add(initial_x + " ");
            match(lookAhead);
        } else if (lookAhead.token == Flag.ID1) {
            this.negativeBefore = "digitLetter";
            this.stack.push(new Num(initial_y));
            sourceTokens.add(initial_y + " ");
            match(lookAhead);
        } else if (lookAhead.token == '(') {
            match(new Token('('));
            sumSub();
            match(new Token(')'));
        } else
            throw new Error("Syntax Error on line: " + Lexer.line + ", expected token " + Character.getName(lookAhead.token));
    }

    private void match(Token token) throws Exception {
        if (lookAhead.token == token.token) {
            lookAhead = this.lex.forward();
        } else {
            throw new Exception(" Syntax Error on line: " + Lexer.line + ", expected token " + Character.getName(token.token));
        }
    }

    private boolean val(String s) {
        for (int i = 0; i < s.length(); i++) {
            StringBuilder sb = new StringBuilder();
            if (Character.isLetter(s.charAt(i))) {
                sb.delete(0, sb.length());
                for (int j = 0; j < s.length(); j++) {
                    if (s.charAt(j) == 'x') {
                        this.set.add(s.charAt(j));
                    }
                    if (s.charAt(j) == 'y') {
                        this.set.add(s.charAt(j));
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
                    this.valuable = true;
                }
            }
        }
        sizeM = this.set.size();
        return this.valuable;
    }

    public void run() throws Exception {
        this.sumSub();
        for (String token : Parser.sourceTokens) {
            System.out.print(token + " ");
        }
        System.out.println();
        System.out.println(">>> " + ((Num) this.stack.pop()).value + "\n");
        this.stack.clear();
        sourceTokens.clear();
    }
}
