package Lexer;

public class Word extends Token {
    final String lexeme;

    Word(int key, String value) {
        super(key);
        lexeme = value;
    }
}
