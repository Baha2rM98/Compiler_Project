package Lexer;

import java.util.*;

public class Lexer {
    public static int line = 1;
    private char[] charList;
    public static Hashtable<String, Word> hashtable = new Hashtable<>();
    private static char lookAhead;
    private static int counter = 0;

    public Lexer() {
        res(new Word(Tag.TRUE, "true"));
        res(new Word(Tag.FALSE, "false"));
        res(new Word(Tag.SIN, "sin"));
        res(new Word(Tag.COS, "cos"));
        res(new Word(Tag.TAN, "tan"));
        res(new Word(Tag.COT, "cot"));
        res(new Word(Tag.log, "log"));
        res(new Word(Tag.exp, "exp"));
        res(new Word(Tag.e, "e"));
        res(new Word(Tag.PI, "pi"));
        res(new Word(Tag.DIV, "div"));
        res(new Word(Tag.MOD, "mod"));
        res(new Word(Tag.SQRT, "sqrt"));
        res(new Word(Tag.SINH, "sinh"));
        res(new Word(Tag.COSH, "cosh"));
        res(new Word(Tag.TANH, "tanh"));
        res(new Word(Tag.ARCsin, "Arcsin"));
        res(new Word(Tag.ARCcos, "Arccos"));
        res(new Word(Tag.Arctan, "Arctan"));
        res(new Word(Tag.Arccot, "Arccot"));
    }


    public Token forward() {
        for (; counter < charList.length; counter++) {
            lookAhead = charList[counter];
            if (lookAhead == ' ') {
            }
            if (lookAhead == '\t') {
            } else if (lookAhead == '\n') {
                line++;
                return new Token('\n');
            } else
                break;
        }

        if (Character.isDigit(lookAhead)) {
            double integer = 0, floated = 0.0;
            while (Character.isDigit(lookAhead)) {
                integer = 10 * integer + Character.getNumericValue(lookAhead);
                counter++;
                lookAhead = charList[counter];
            }
            int decimalPoint = 0;
            if (lookAhead == ',' || lookAhead == '.') {
                counter++;
                while (Character.isDigit(charList[counter])) {
                    lookAhead = charList[counter];
                    counter++;
                    floated = 10 * floated + Character.getNumericValue(lookAhead);
                    decimalPoint++;
                }
            }
            integer += floated / Math.pow(10, decimalPoint);
            return new Num(integer);
        }
        if (Character.isLetter(lookAhead)) {
            StringBuilder stringBuilder = new StringBuilder();
            do {
                stringBuilder.append(lookAhead);
                lookAhead = charList[++counter];
            } while (Character.isLetterOrDigit(lookAhead));
            String str = stringBuilder.toString();
            if (str.equals("x")) {
                Word w = hashtable.get(str);
                if (w != null)
                    return w;
                w = new Word(Tag.ID, str);
                hashtable.put(str, w);
                return w;
            } else if (str.equals("y")) {
                Word w = (Word) hashtable.get(str);
                if (w != null)
                    return w;
                w = new Word(Tag.ID1, str);
                hashtable.put(str, w);
                return w;
            }
            Word w = hashtable.get(str);
            if (w != null)
                return w;
            w = new Word(Tag.ID, str);
            hashtable.put(str, w);
            return w;
        }
        Token t = new Token(lookAhead);
        counter++;
        return t;
    }


    private void res(Word t) {
        hashtable.put(t.lexeme, t);
    }


    public Token forward(char[] chars) {
        charList = new char[chars.length];
        System.arraycopy(chars, 0, charList, 0, chars.length);
        for (; counter < chars.length; counter++) {
            lookAhead = chars[counter];
            if (lookAhead == ' ' || lookAhead == '\t')
                continue;
            else if (lookAhead == '\n')
                line++;
            else
                break;
        }
        if (Character.isDigit(lookAhead)) {
            double integer = 0.0, floated = 0.0;
            while (Character.isDigit(lookAhead)) {
                integer = 10 * integer + Character.getNumericValue(lookAhead);
                counter++;
                lookAhead = charList[counter];
            }
            int decimalPoint = 0;
            if (lookAhead == ',' || lookAhead == '.') {
                counter++;
                while (Character.isDigit(charList[counter])) {
                    lookAhead = charList[counter];
                    counter++;
                    floated = 10 * floated + Character.getNumericValue(lookAhead);
                    decimalPoint++;
                }
            }
            integer += floated / Math.pow(10, decimalPoint);
            return new Num(integer);
        }
        if (Character.isLetter(lookAhead)) {
            StringBuilder sb = new StringBuilder();
            do {
                sb.append(lookAhead);
                lookAhead = charList[++counter];
            } while (Character.isLetterOrDigit(lookAhead));
            String s = sb.toString();
            if (s.equals("x")) {
                Word w = hashtable.get(s);
                if (w != null) {
                    return w;
                }
                w = new Word(Tag.ID, s);
                hashtable.put(s, w);
                return w;
            }
            if (s.equals("y")) {
                Word w = hashtable.get(s);
                if (w != null) {
                    return w;
                }
                w = new Word(Tag.ID1, s);
                hashtable.put(s, w);
                return w;
            }
            Word w = hashtable.get(s);
            if (w != null) {
                return w;
            }
            w = new Word(Tag.ID, s);
            hashtable.put(s, w);
            return w;
        }
        Token t = new Token(lookAhead);
        counter++;
        return t;
    }
}