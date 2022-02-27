package ru.netology.graphics.image;

public class TextColorSchema implements TextColorSchemaInteface{

    @Override
    public char convert(int color) {
        // int descretisationStep = 26;
        // int charIndex = 0;

        // charIndex = color / descretisationStep;

        // switch (charIndex) {
        //     case (0):
        //         return('▇');
        //     case(1):
        //         return('●');
        //     case(2):
        //         return('◉');
        //     case(3):
        //         return('◍');
        //     case(4):
        //         return('◎');
        //     case(5):
        //         return('○');
        //     case(6):
        //         return('☉');
        //     case(7):
        //         return('◌');
        //     case(8):
        //         return('-');
        //     default:
        //         return(' '); 
        // }

        char[] symbols = { '#', '$', '@', '%', '*', '+', '-', '\'' };
        int step = (int) 256 / symbols.length;

        return symbols[(int) color / step];

    }
    
}
