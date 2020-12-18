package Parser;

import Lexer.*;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser {
    private Lexer lex = new Lexer();
    private static double digit = 0;
    private Map<String, Double> vars = new HashMap<>();
    private boolean minus = false;
    private static ArrayList<String> sourceTokens = new ArrayList<>();
    private Stack stack = new Stack();
    private String negativeBefore = "unary";
    private boolean hasVariable = false;
    private static Token lookAhead;
    private static Token forAhead;

    public Parser(String source) {
        if (this.hasVariable(source)) {
            this.hasVariable = true;
        }
        char[] chars = source.toCharArray();
        lookAhead = this.lex.forward(chars);
    }

    private void sumSub() {
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

    private void mulDiv() {
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

    private void pow() {
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

    private void function() {
        while (true) {
            if (lookAhead.token == Flag.SIN) {
                match(new Token(Flag.SIN));
                match(new Token('('));
                sumSub();
                match(new Token(')'));
                sourceTokens.add("sin ");
                this.stack.push(new Num(Math.sin(((Num) this.stack.pop()).value)));
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
                    throw new Error("Invalid variable name on line: " + Lexer.line);
                } else {
                    sourceTokens.add(digit + " ");
                    this.stack.push(forAhead);
                }
                return;
            } else if (lookAhead.token == Flag.ID) {
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

    private void digitOrLetter() {
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
            for (Map.Entry<String, Double> entry : this.vars.entrySet()) {
                this.stack.push(new Num(entry.getValue()));
                sourceTokens.add(entry.getValue() + " ");
                match(lookAhead);
            }
        } else if (lookAhead.token == '(') {
            match(new Token('('));
            sumSub();
            match(new Token(')'));
        } else
            throw new Error("Syntax Error on line: " + Lexer.line + ", expected token " + Character.getName(lookAhead.token));
    }

    private void match(Token token) {
        if (lookAhead.token == token.token) {
            lookAhead = this.lex.forward();
        } else {
            throw new Error(" Syntax Error on line: " + Lexer.line + ", expected token " + Character.getName(token.token));
        }
    }

    private boolean hasVariable(String source) {
        ArrayList<String> varsList = new ArrayList<>();
        Matcher matcher = Pattern.compile("[a-zA-Z_][a-zA-Z0-9_]*").matcher(source);
        while (matcher.find()) {
            varsList.add(matcher.group());
        }
        for (int i = 0; i < 1000000; i++) {
            varsList.remove("sin");
            varsList.remove("cos");
            varsList.remove("tan");
            varsList.remove("cot");
            varsList.remove("log");
            varsList.remove("exp");
            varsList.remove("e");
            varsList.remove("pi");
            varsList.remove("div");
            varsList.remove("mod");
            varsList.remove("sqrt");
            varsList.remove("sqr");
            varsList.remove("arcsin");
            varsList.remove("arccos");
            varsList.remove("arctan");
            varsList.remove("arccot");
        }
        Set<String> uniqueVar = new HashSet<>(varsList);
        if (uniqueVar.size() > 0) {
            for (String var : uniqueVar) {
                this.vars.put(var, 0.0);
            }
            return true;
        }
        return false;
    }

    private boolean isVariableValid(String content) {
        return Pattern.compile("^[a-zA-Z_$][a-zA-Z_$0-9]*$").matcher(content).find();
    }

    public void run() {
        if (this.hasVariable) {
            Scanner scn = new Scanner(System.in);
            for (Map.Entry<String, Double> entry : this.vars.entrySet()) {
                if (!this.isVariableValid(entry.getKey())) {
                    throw new Error("Invalid variable name: " + entry.getKey() + "on line " + Lexer.line);
                }
                System.out.println("Enter value of " + entry.getKey() + " :");
                this.vars.put(entry.getKey(), scn.nextDouble());
            }
        }
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
