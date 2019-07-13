package Lexer;

public class Word extends Token {
    final String lexeme;
    public static double value;
Word(int t, String s){
        super(t);
        this.lexeme = s;
    }
}
