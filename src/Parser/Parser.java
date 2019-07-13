package Parser;
import Lexer.*;
import java.util.*;
import java.io.*;
import java.math.*;
public class Parser {
    
    private static Token lookahead;
    private Lexer lex = new Lexer();
    private Stack stack = new Stack();
    
    public Parser(String s) throws IOException{
        char[] sChar = new char[s.length() + 1];
        for (int a = 0 ; a < s.length() ; a++)
            sChar[a] = s.charAt(a);
        sChar[s.length()] = '\n';
        lookahead = lex.scan(sChar);
    }
    
    public void expr()throws IOException{
        term();
        while(true){
            if (lookahead.tag == '+'){
                match(new Token('+'));
                term();
                System.out.print("+ ");
                double b = ((Num)stack.pop()).value;
                double a = ((Num)stack.pop()).value;
                stack.push(new Num(a + b));
            }
            else if (lookahead.tag == '-'){
                match(new Token('-'));
                term();
                System.out.print("- ");
                double b = ((Num)stack.pop()).value;
                double a = ((Num)stack.pop()).value;
                stack.push(new Num(a - b));
            }
            else
                return;
        }
    }

    private void term() throws IOException {
        pow();
        while(true){
            if (lookahead.tag == '*'){
                match(new Token('*'));
                pow();
                System.out.print("* ");
                double b = ((Num)stack.pop()).value;
                double a = ((Num)stack.pop()).value;
                stack.push(new Num(a * b));
            }
            else if (lookahead.tag == '/'){
                match(new Token('/'));
                pow();
                System.out.print("/ ");
                double b = ((Num)stack.pop()).value;
                double a = ((Num)stack.pop()).value;
                stack.push(new Num(a / b));
            }
            else if (lookahead.tag == Tag.DIV){
                match(new Token(Tag.DIV));
                pow();
                System.out.print("div ");
                double b = ((Num)stack.pop()).value;
                double a = ((Num)stack.pop()).value;
                stack.push(new Num((int)(a / b)));             //taghsime sahih
            }
            else if (lookahead.tag == Tag.MOD){
                match(new Token(Tag.MOD));
                pow();
                System.out.print("mod ");
                double b = ((Num)stack.pop()).value;
                double a = ((Num)stack.pop()).value;
                stack.push(new Num(a % b));
            }
            else
                return;
        }
    }
    
    private void pow() throws IOException{
        trigonometrics();
        while(true){
            if (lookahead.tag == '^'){
                match(new Token('^'));
//                trigonometrics();
                pow();
                System.out.print("^ ");
                double b = ((Num)stack.pop()).value;
                double a = ((Num)stack.pop()).value;
                stack.push(new Num(Math.pow(a, b)));
            }
            else
                return;
        }
    }
    
    private void trigonometrics() throws IOException{
//        DigitOrLetter();
        while(true){
            if (lookahead.tag == Tag.SIN){
                match(new Token(Tag.SIN));
                match(new Token('('));
                expr();
                match(new Token(')'));
//                DigitOrLetter();
                System.out.print("sin ");
                stack.push(new Num(Math.sin(((Num)stack.pop()).value)));
            }
            else if (lookahead.tag == Tag.COS){
                match(new Token(Tag.COS));
                match(new Token('('));
                expr();
                match(new Token(')'));
//                DigitOrLetter();
                System.out.print("cos ");
                stack.push(new Num(Math.cos(((Num)stack.pop()).value)));
            }
            else if (lookahead.tag == Tag.TAN){
                match(new Token(Tag.TAN));
                match(new Token('('));
                expr();
                match(new Token(')'));
//                DigitOrLetter();
                System.out.print("tan ");
                stack.push(new Num(Math.tan(((Num)stack.pop()).value)));
            }
            else if (lookahead.tag == Tag.COT){
                match(new Token(Tag.COT));
                match(new Token('('));
                expr();
                match(new Token(')'));
//                DigitOrLetter();
                System.out.print("cot ");
                stack.push(new Num(1.0 / Math.tan(((Num)stack.pop()).value)));
            }
            else if(lookahead.tag == Tag.NUM
                    || lookahead.tag == Tag.ID
                    || lookahead.tag == '('){
                DigitOrLetter();
                return;
            }
            else
                return;

        }
    }
    
    private void DigitOrLetter() throws IOException {
        if (lookahead.tag == Tag.NUM){
            stack.push(lookahead);
            System.out.print(((Num)lookahead).value + " ");
            match(lookahead);
        }
        else if (lookahead.tag == Tag.ID){
//            compiler.Compiler.id_flag = true;
            stack.push(new Num(Word.value));
            System.out.print(Word.value + " ");
            match(lookahead);
        }
        else if (lookahead.tag == '('){
            match(new Token('('));
            expr();
            match(new Token(')'));
        }
        else
            return;
//            System.out.println("test");
//            throw new Error("Syntax Error on line: " + lex.line);
    }
    
    private void match(Token t)throws IOException{
        if (lookahead.tag == t.tag )
            lookahead = lex.scan();
        else
            throw new Error("Syntax Error on line: " + lex.line);
    }
}


