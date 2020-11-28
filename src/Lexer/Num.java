package Lexer;

public class Num extends Token {
    public final double value;

    public Num(double value) {
        super(Flag.NUM);
        this.value = value;
    }
}
