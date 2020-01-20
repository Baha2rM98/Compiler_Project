package Lexer;

public class Word extends Token {
    final String lexeme;

    Word(int t, String s) {
        super(t);
        lexeme = s;
    }
}