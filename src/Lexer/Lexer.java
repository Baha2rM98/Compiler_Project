package Lexer;

import java.util.Hashtable;

public class Lexer {
    public int line = 1;
    private char peek = ' ';
    private Hashtable<String, Word> words = new Hashtable<String, Word>();
    private int i = 0;
    private char[] sCopy;

    public Lexer() {
        reserve(new Word(Tag.TRUE, "true"));
        reserve(new Word(Tag.FALSE, "false"));
        reserve(new Word(Tag.SIN, "sin"));
        reserve(new Word(Tag.COS, "cos"));
        reserve(new Word(Tag.TAN, "tan"));
        reserve(new Word(Tag.COT, "cot"));
        reserve(new Word(Tag.log, "log"));
        reserve(new Word(Tag.exp, "exp"));
        reserve(new Word(Tag.E, "E"));
        reserve(new Word(Tag.PI, "Pi"));
        reserve(new Word(Tag.DIV, "div"));
        reserve(new Word(Tag.MOD, "mod"));
    }

    private void reserve(Word t) {
        words.put(t.lexeme, t);
    }

    public Token scan() {
        for (; i < sCopy.length; i++) {
            peek = sCopy[i];
            if (peek == ' ' || peek == '\t')
                continue;
            else if (peek == '\n') {
                line++;
                return new Token('\n');
            } else
                break;
        }

        if (Character.isDigit(peek)) {
            double v = 0;
            double w = 0.0;
            do {
                v = 10 * v + Character.digit(peek, 10);
                peek = sCopy[++i];
            } while (Character.isDigit(peek));
            if (peek == '.') {
                int x = 1;
                while (Character.isDigit(peek = sCopy[++i])) {
                    w = w + Character.digit(peek, 10) / Math.pow(10, x++);
                }
            }
            v += w;
            return new Num(v);
        }
        if (Character.isLetter(peek)) {
            StringBuilder b = new StringBuilder();
            do {
                b.append(peek);
                peek = sCopy[++i];
            } while (Character.isLetterOrDigit(peek));
            String str = b.toString();
            Word w = words.get(str);
            if (w != null)
                return w;
            w = new Word(Tag.ID, str);
            words.put(str, w);
            return w;
        }
        Token t = new Token(peek);
        i++;
        return t;
    }

    public Token scan(char[] s) {
        sCopy = new char[s.length];
        System.arraycopy(s, 0, sCopy, 0, s.length);

        for (; i < s.length; i++) {
            peek = s[i];
            if (peek == ' ' || peek == '\t')
                continue;
            else if (peek == '\n')
                line++;
            else
                break;
        }

        if (Character.isDigit(peek)) {
            double v = 0;
            double w = 0.0;
            do {
                v = 10 * v + Character.digit(peek, 10);
                peek = s[++i];
            } while (Character.isDigit(peek));
            if (peek == '.') {
                int x = 1;
                while (Character.isDigit(peek = s[++i])) {
                    w = w + Character.digit(peek, 10) / Math.pow(10, x++);
                }
            }
            v += w;
            return new Num(v);
        }
        if (Character.isLetter(peek)) {
            StringBuilder b = new StringBuilder();
            do {
                b.append(peek);
                peek = sCopy[++i];
            } while (Character.isLetterOrDigit(peek));
            String str = b.toString();
            Word w = words.get(str);
            if (w != null)
                return w;
            w = new Word(Tag.ID, str);
            words.put(str, w);
            return w;
        }
        Token t = new Token(peek);
        i++;
        return t;

    }
}
