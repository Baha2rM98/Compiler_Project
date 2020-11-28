package Lexer;

import java.util.*;

public class Lexer {
    public static int line = 1;
    private char[] charList;
    public static Hashtable<String, Word> hashtable = new Hashtable<>();
    private static char lookAhead;
    private static int counter = 0;

    public Lexer() {
        initialHashTable(new Word(Flag.SIN, "sin"));
        initialHashTable(new Word(Flag.COS, "cos"));
        initialHashTable(new Word(Flag.TAN, "tan"));
        initialHashTable(new Word(Flag.COT, "cot"));
        initialHashTable(new Word(Flag.LOG, "log"));
        initialHashTable(new Word(Flag.EXP, "exp"));
        initialHashTable(new Word(Flag.E, "e"));
        initialHashTable(new Word(Flag.PI, "pi"));
        initialHashTable(new Word(Flag.DIV, "div"));
        initialHashTable(new Word(Flag.MOD, "mod"));
        initialHashTable(new Word(Flag.SQRT, "sqrt"));
        initialHashTable(new Word(Flag.SQR, "sqr"));
        initialHashTable(new Word(Flag.SINH, "sinh"));
        initialHashTable(new Word(Flag.COSH, "cosh"));
        initialHashTable(new Word(Flag.TANH, "tanh"));
        initialHashTable(new Word(Flag.ARC_SIN, "arcsin"));
        initialHashTable(new Word(Flag.ARC_COS, "arccos"));
        initialHashTable(new Word(Flag.ARC_TAN, "arctan"));
        initialHashTable(new Word(Flag.ARC_COT, "arccot"));
    }

    public Token forward() {
        for (; counter < this.charList.length; counter++) {
            lookAhead = this.charList[counter];
            if (lookAhead == ' ') {
                continue;
            }
            if (lookAhead == '\t') {
                continue;
            }
            if (lookAhead == '\n') {
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
                lookAhead = this.charList[counter];
            }
            int decimalPoint = 0;
            if (lookAhead == ',' || lookAhead == '.') {
                counter++;
                while (Character.isDigit(this.charList[counter])) {
                    lookAhead = this.charList[counter];
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
                lookAhead = this.charList[++counter];
            } while (Character.isLetterOrDigit(lookAhead));
            String str = stringBuilder.toString();
            if (str.equals("x")) {
                Word word = hashtable.get(str);
                if (word != null)
                    return word;
                word = new Word(Flag.ID, str);
                hashtable.put(str, word);
                return word;
            } else if (str.equals("y")) {
                Word word = hashtable.get(str);
                if (word != null)
                    return word;
                word = new Word(Flag.ID1, str);
                hashtable.put(str, word);
                return word;
            }
            Word word = hashtable.get(str);
            if (word != null)
                return word;
            word = new Word(Flag.ID, str);
            hashtable.put(str, word);
            return word;
        }
        Token token = new Token(lookAhead);
        counter++;
        return token;
    }

    private void initialHashTable(Word word) {
        hashtable.put(word.lexeme, word);
    }

    public Token forward(char[] chars) {
        this.charList = new char[chars.length];
        System.arraycopy(chars, 0, this.charList, 0, chars.length);
        for (; counter < chars.length; counter++) {
            lookAhead = chars[counter];
            if (lookAhead == ' ' || lookAhead == '\t')
                continue;
            if (lookAhead == '\n')
                line++;
            else
                break;
        }
        if (Character.isDigit(lookAhead)) {
            double integer = 0.0, floated = 0.0;
            while (Character.isDigit(lookAhead)) {
                integer = 10 * integer + Character.getNumericValue(lookAhead);
                counter++;
                lookAhead = this.charList[counter];
            }
            int decimalPoint = 0;
            if (lookAhead == ',' || lookAhead == '.') {
                counter++;
                while (Character.isDigit(this.charList[counter])) {
                    lookAhead = this.charList[counter];
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
                lookAhead = this.charList[++counter];
            } while (Character.isLetterOrDigit(lookAhead));
            String string = sb.toString();
            if (string.equals("x")) {
                Word word = hashtable.get(string);
                if (word != null) {
                    return word;
                }
                word = new Word(Flag.ID, string);
                hashtable.put(string, word);
                return word;
            }
            if (string.equals("y")) {
                Word word = hashtable.get(string);
                if (word != null) {
                    return word;
                }
                word = new Word(Flag.ID1, string);
                hashtable.put(string, word);
                return word;
            }
            Word word = hashtable.get(string);
            if (word != null) {
                return word;
            }
            word = new Word(Flag.ID, string);
            hashtable.put(string, word);
            return word;
        }
        Token token = new Token(lookAhead);
        counter++;
        return token;
    }
}
