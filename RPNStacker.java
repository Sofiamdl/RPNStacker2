import stacker.rpn.lexer.Token;
import stacker.rpn.lexer.TokenType;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;

public class RPNStacker {

    public static void main(String[] args) throws Exception {

        List<Token> TokenList = readFile("Calc1.stk");
        Stack<String> stackStr = calculate(TokenList);

        System.out.println("O resultado é: " + stackStr.pop());
    }

    static List<Token> readFile(String fileName) {
        List<Token> TokenList = new ArrayList<>();
        File text = new File(fileName);

        try (Scanner scanner = new Scanner(text)) {
            while(scanner.hasNextLine()){

                String digit = scanner.nextLine();

                if (isValid(digit)) {
                    Token token = new Token(TokenType.NUM, digit);
                    TokenList.add(token);

                } else if (digit.equals("+")) {
                    Token token = new Token(TokenType.PLUS, digit);
                    TokenList.add(token);

                } else if (digit.equals("-")) {
                    Token token = new Token(TokenType.MINUS, digit);
                    TokenList.add(token);
                } else if (digit.equals("/")) {
                    Token token = new Token(TokenType.SLASH, digit);
                    TokenList.add(token);
                } else if (digit.equals("*")) {
                    Token token = new Token(TokenType.STAR, digit);
                    TokenList.add(token);

                } else {
                    throw new Exception();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return TokenList;
    }

    private static Stack<String> calculate(List<Token> TokenList) {
        Stack<String> stackStr = new Stack<String>();
        while(!TokenList.isEmpty()){

            String digit = TokenList.remove(0).lexeme;

            switch(digit){
                case "+":
                case "-":
                case "/":
                case "*":

                    int x, y;
                    x = Integer.parseInt(stackStr.pop());
                    y = Integer.parseInt(stackStr.pop());
                    switch(digit){
                        case "+":
                            stackStr.push(Integer.toString(y + x));
                            break;
                        case "-":
                            stackStr.push(Integer.toString(y - x));
                            break;
                        case "/":
                            stackStr.push(Integer.toString(y / x));
                            break;
                        case "*":
                            stackStr.push(Integer.toString(y * x));
                            break;
                    }
                    break;
                default:
                    stackStr.push(digit);
                    break;
            }
        }
        return stackStr;
    }

    private static boolean isValid(String string) {
        return string != null && string.matches("[0-9]*");
    }
}