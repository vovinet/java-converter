package ru.netology.graphics;

import ru.netology.graphics.image.TextGraphicsConverter;
import ru.netology.graphics.image.TextGraphicsConverterInterface;
import ru.netology.graphics.server.GServer;

import java.io.File;
import java.io.PrintWriter;

public class Main {
    public static void main(String[] args) throws Exception {
        TextGraphicsConverterInterface converter = new TextGraphicsConverter(); // Создайте тут объект вашего класса конвертера

        GServer server = new GServer(converter); // Создаём объект сервера
        server.start(); // Запускаем

        // Или то же, но с выводом на экран:
        //converter.setMaxHeight(300);
        converter.setMaxWidth(240);
        //converter.setMaxRatio(4);
        //String url = "https://intrigue.dating/wp-content/uploads/2020/09/4346343-105-819x1024.jpg";
        // String url = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQjR3ApxSMA7Pdews06yEYgd36jx-tppIvRpQ&usqp=CAU";
        // String imgTxt = converter.convert(url);
        // System.out.println(imgTxt);
    }
}
