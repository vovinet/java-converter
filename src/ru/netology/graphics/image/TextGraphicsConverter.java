package ru.netology.graphics.image;

import java.io.IOException;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import javax.imageio.ImageIO;
import java.net.URL;

public class TextGraphicsConverter implements TextGraphicsConverterInterface {
    private int maxWidth;
    private int maxHeight;
    private double maxRatio;
    private TextColorSchemaInteface schema;

    public TextGraphicsConverter() {
        this.setMaxWidth(0);
        this.setMaxHeight(0);
        this.setMaxRatio(0.0);

        TextColorSchemaInteface schema = new TextColorSchema();
        this.setTextColorSchema(schema);
    }

    @Override
    public String convert(String url) throws IOException, BadImageSizeException {
        BufferedImage img = ImageIO.read(new URL(url));

        int originalWidth = img.getWidth();
        int originalHeight = img.getHeight();
        int newWidth = 0;
        int newHeight = 0;

        // Сначала самое простое - проверка соотношения сторон
        if (this.maxRatio > 0) {   
            double originalRatio = (originalWidth * 1.0 / originalHeight);
            if (originalRatio > this.maxRatio) {
                throw new BadImageSizeException(originalRatio, this.maxRatio);
            }
        }

        // Если задано и то и другое - проверяем оба
        if ((this.maxWidth > 0) && (this.maxHeight > 0)) {
            // Если хоть один из параметров превышает лимит - расчитываем масштабирование
            if ((originalWidth > this.maxWidth) || (originalHeight > this.maxHeight)) {
                double horisontalRatio = originalWidth * 1.0 / maxWidth;
                double verticalRatio = originalHeight * 1.0 / maxWidth;
                double ratio = 0.0;

                ratio = horisontalRatio > verticalRatio ?  horisontalRatio : verticalRatio;

                newWidth = (int) Math.ceil(originalWidth / ratio);
                newHeight = (int) Math.ceil(originalHeight / ratio);
            }

        // Масштабируем только по ширине
        } else if ((this.maxWidth > 0) && (originalWidth > this.maxWidth)) {
            double ratio = originalWidth / maxWidth;
            newWidth = (int)(originalWidth / ratio);
            newHeight = (int)(originalHeight / ratio);
            
        // Масштабируемся только по высоте
        } else if ((this.maxHeight > 0) && (originalHeight > this.maxHeight)) {
            double ratio = originalHeight / maxWidth;
            newWidth = (int)(originalWidth / ratio);
            newHeight = (int)(originalHeight / ratio);
        
        // В остальных случаях - рендерим как есть
        } else {
            newWidth = originalWidth;
            newHeight = originalHeight;

        }

        // Resize
        Image scaledImage = img.getScaledInstance(newWidth, newHeight, BufferedImage.SCALE_SMOOTH);

        // Convert to BW
        BufferedImage bwImg = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_BYTE_GRAY);

        Graphics2D graphics = bwImg.createGraphics();
        graphics.drawImage(scaledImage, 0, 0, null);
        WritableRaster bwRaster = bwImg.getRaster();


        int[] pixelRGB = new int[3];
        String textImage = "";

        for (int verticalPos = 0; verticalPos < newHeight; verticalPos++)  {
            for (int horizontalPos = 0; horizontalPos < newWidth; horizontalPos++) {
              int color = bwRaster.getPixel(horizontalPos, verticalPos, pixelRGB)[0];
              char c = this.schema.convert(color);
              textImage = textImage + c + c;
            }
            textImage += "\n";
          }
        return textImage;
    }

    @Override
    public void setMaxWidth(int width) {
        this.maxWidth = width;
    }

    @Override
    public void setMaxHeight(int height) {
        this.maxHeight = height;
    }

    @Override
    public void setMaxRatio(double maxRatio) {
        this.maxRatio = maxRatio;        
    }
    @Override
    public void setTextColorSchema(TextColorSchemaInteface schema) {
        this.schema = schema;
        
    }
}
