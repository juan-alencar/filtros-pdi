import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Arrays;

public class Filtragem {
    public static BufferedImage increasePitchOneBand(BufferedImage image, int band, int increase) {
        int width = image.getWidth();
        int height = image.getHeight();
        BufferedImage imageOutput = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int line = 0; line < height; line++) {
            for (int column = 0; column < width; column++) {

                int rgb = image.getRGB(column, line);

                Color color = new Color(rgb);

                int red = color.getRed();
                int green = color.getGreen();
                int blue = color.getBlue();

                if (band == 0) {
                    red += increase;
                    if (red > 255) red = 255;
                }
                if (band == 1) {
                    green += increase;
                    if (green > 255) green = 255;
                }
                if (band == 2) {
                    blue += increase;
                    if (blue > 255) blue = 255;
                }

                Color newColor = new Color(red, green, blue);
                imageOutput.setRGB(column, line, newColor.getRGB());
            }
        }
        return imageOutput;
    }
    public static BufferedImage grayBand(BufferedImage image, int band) {
        int width = image.getWidth();
        int height = image.getHeight();
        BufferedImage imageOutput = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int line = 0; line < height; line++) {
            for (int column = 0; column < width; column++) {
                int rgb = image.getRGB(column, line);
                Color color = new Color(rgb);
                int red = color.getRed();
                int green = color.getGreen();
                int blue = color.getBlue();
                Color newColor = null;
                if (band == 0) {
                    newColor = new Color(red, red, red);
                }
                if (band == 1) {
                    newColor = new Color(green, green, green);
                }
                if (band == 2) {
                    newColor = new Color(blue, blue, blue);
                }
                imageOutput.setRGB(column, line, newColor.getRGB());
            }
        }
        return imageOutput;
    }
    public static BufferedImage grayMedium(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();
        BufferedImage imageOutput = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int line = 0; line < height; line++) {
            for (int column = 0; column < width; column++) {
                int rgb = image.getRGB(column, line);
                Color color = new Color(rgb);
                int red = color.getRed();
                int green = color.getGreen();
                int blue = color.getBlue();
                int media = (int) (red + green + blue) / 3;
                Color newColor = new Color(media, media, media);
                imageOutput.setRGB(column, line, newColor.getRGB());
            }
        }
        return imageOutput;
    }
    public static BufferedImage negativeRGB(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();
        BufferedImage imageOutput = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int line = 0; line < height; line++) {
            for (int column = 0; column < width; column++) {
                int rgb = image.getRGB(column, line);
                Color color = new Color(rgb);
                int red = color.getRed();
                int green = color.getGreen();
                int blue = color.getBlue();
                Color newColor = new Color(255 - red, 255 - green, 255 - blue);
                imageOutput.setRGB(column, line, newColor.getRGB());

            }
        }
        return imageOutput;
    }
    public static BufferedImage binarization(BufferedImage image, int limiar) {
        int width = image.getWidth();
        int height = image.getHeight();
        BufferedImage imageOutput = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int line = 0; line < height; line++) {
            for (int column = 0; column < width; column++) {
                int rgb = image.getRGB(column, line);
                Color color = new Color(rgb);
                int red = color.getRed();
                int green = color.getGreen();
                int blue = color.getBlue();
                int media = (red + green + blue) / 3;
                Color branco = new Color(255, 255, 255);
                Color preto = new Color(0, 0, 0);
                if (media <= limiar) imageOutput.setRGB(column, line, preto.getRGB());
                else imageOutput.setRGB(column, line, branco.getRGB());
            }
        }
        return imageOutput;
    }
    public double[][][] rgb2YIQ(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();
        double[][][] imageYIQ = new double[height][width][3];
        for (int line = 0; line < height; line++) {
            for (int column = 0; column < width; column++) {
                int rgb = image.getRGB(column, line);
                Color color = new Color(rgb);
                int red = color.getRed();
                int green = color.getGreen();
                int blue = color.getBlue();
                double y = (double) (0.299 * red + 0.587 * green + 0.114 * blue); //Y
                double i = (double) (0.596 * red - 0.274 * green - 0.322 * blue); //I
                double q = (double) (0.211 * red - 0.523 * green + 0.312 * blue); //Q
                imageYIQ[line][column][0] = y;
                imageYIQ[line][column][1] = i;
                imageYIQ[line][column][2] = q;
            }
        }
        return imageYIQ;
    }
    public BufferedImage yiq2RGB(double[][][] imageYIQ) {
        int height = imageYIQ.length;
        int width = imageYIQ[0].length;
        BufferedImage imageOutput = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int line = 0; line < height; line++) {
            for (int column = 0; column < width; column++) {
                int red = (int) (imageYIQ[line][column][0] + 0.956 * imageYIQ[line][column][1] + 0.621 * imageYIQ[line][column][2]);
                if (red < 0) {
                    red = 0;
                } else if (red > 255) {
                    red = 255;
                }
                int green = (int) (imageYIQ[line][column][0] - 0.272 * imageYIQ[line][column][1] - 0.647 * imageYIQ[line][column][2]);
                if (green < 0) {
                    green = 0;
                } else if (green > 255) {
                    green = 255;
                }
                int blue = (int) (imageYIQ[line][column][0] - 1.106 * imageYIQ[line][column][1] + 1.703 * imageYIQ[line][column][2]);
                if (blue < 0) {
                    blue = 0;
                } else if (blue > 255) {
                    blue = 255;
                }
                Color newColor = new Color(red, green, blue);
                imageOutput.setRGB(column, line, newColor.getRGB());
            }
        }
        return imageOutput;
    }
    public static BufferedImage additiveBrightnessRGB(BufferedImage image, double brightnessAdd) {
        int width = image.getWidth();
        int height = image.getHeight();
        BufferedImage imageOutput = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int line = 0; line < height; line++) {
            for (int column = 0; column < width; column++) {
                int rgb = image.getRGB(column, line);
                Color color = new Color(rgb);
                int red = color.getRed();
                int green = color.getGreen();
                int blue = color.getBlue();
                red += brightnessAdd;
                if (red > 255) red = 255;
                green += brightnessAdd;
                if (green > 255) green = 255;
                blue += brightnessAdd;
                if (blue > 255) blue = 255;
                Color newColor = new Color(red, green, blue);
                imageOutput.setRGB(column, line, newColor.getRGB());
            }
        }
        return imageOutput;
    }
    public static BufferedImage multiplicativeBrightnessRGB(BufferedImage image, double brightnessMult) {
        int width = image.getWidth();
        int height = image.getHeight();
        BufferedImage imageOutput = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int line = 0; line < height; line++) {
            for (int column = 0; column < width; column++) {
                int rgb = image.getRGB(column, line);
                Color color = new Color(rgb);
                int red = color.getRed();
                int green = color.getGreen();
                int blue = color.getBlue();
                red *= brightnessMult;
                if (red > 255) red = 255;
                green *= brightnessMult;
                if (green > 255) green = 255;
                blue *= brightnessMult;
                if (blue > 255) blue = 255;
                Color newColor = new Color(red, green, blue);
                imageOutput.setRGB(column, line, newColor.getRGB());
            }
        }
        return imageOutput;
    }
    public BufferedImage additiveBrightnessY(BufferedImage image, double brightnessAdd) {
        int width = image.getWidth();
        int height = image.getHeight();
        double[][][] imageYIQ = new double[height][width][3];
        for (int line = 0; line < height; line++) {
            for (int column = 0; column < width; column++) {
                int rgb = image.getRGB(column, line);
                Color color = new Color(rgb);
                int red = color.getRed();
                int green = color.getGreen();
                int blue = color.getBlue();
                double y = brightnessAdd + (0.299 * red + 0.587 * green + 0.114 * blue); //Y
                double i = (0.596 * red - 0.274 * green - 0.322 * blue); //I
                double q = (0.211 * red - 0.523 * green + 0.312 * blue); //Q
                imageYIQ[line][column][0] = y;
                imageYIQ[line][column][1] = i;
                imageYIQ[line][column][2] = q;
            }
        }
        return yiq2RGB(imageYIQ);
    }
    public BufferedImage brightnessMultY(BufferedImage image, double brightnessMult) {
        int width = image.getWidth();
        int height = image.getHeight();
        double[][][] imageYIQ = new double[height][width][3];
        for (int line = 0; line < height; line++) {
            for (int column = 0; column < width; column++) {
                int rgb = image.getRGB(column, line);
                Color color = new Color(rgb);
                int red = color.getRed();
                int green = color.getGreen();
                int blue = color.getBlue();
                double y = (double) brightnessMult * (0.299 * red + 0.587 * green + 0.114 * blue); //Y
                double i = (double) (0.596 * red - 0.274 * green - 0.322 * blue); //I
                double q = (double) (0.211 * red - 0.523 * green + 0.312 * blue); //Q
                imageYIQ[line][column][0] = y;
                imageYIQ[line][column][1] = i;
                imageYIQ[line][column][2] = q;
            }
        }
        return yiq2RGB(imageYIQ);
    }
    public static double[][][] negativeY(double[][][] imageYIQ) {
        int height = imageYIQ.length;
        int width = imageYIQ[0].length;
        for (int line = 0; line < height; line++) {
            for (int column = 0; column < width; column++) {
                imageYIQ[line][column][0] = 255 - imageYIQ[line][column][0];
            }
        }
        return imageYIQ;
    }

    public BufferedImage average3x3 (BufferedImage imgOriginal) {
        int width = imgOriginal.getWidth();
        int height = imgOriginal.getHeight();
        BufferedImage imageOutput = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        
        for (int i = 1; i < width -1; i++) {
            for (int j= 1; j < height -1; j++) {

                Color color1 = new Color(imgOriginal.getRGB(i-1, j-1));
                Color color2 = new Color(imgOriginal.getRGB(i-1, j));
                Color color3 = new Color(imgOriginal.getRGB(i-1, j+1));
                Color color4 = new Color(imgOriginal.getRGB(i, j-1));
                Color color5 = new Color(imgOriginal.getRGB(i, j));
                Color color6 = new Color(imgOriginal.getRGB(i, j+1));
                Color color7 = new Color(imgOriginal.getRGB(i+1, j-1));
                Color color8 = new Color(imgOriginal.getRGB(i+1, j));
                Color color9 = new Color(imgOriginal.getRGB(i+1, j+1));

                int r1 = color1.getRed();
                int r2 = color2.getRed();
                int r3 = color3.getRed();
                int r4 = color4.getRed();
                int r5 = color5.getRed();
                int r6 = color6.getRed();
                int r7 = color7.getRed();
                int r8 = color8.getRed();
                int r9 = color9.getRed();

                int media = (r1 + r2 + r3 + r4 + r5 + r6 + r7 + r8 + r9)/ 9;

                Color color = new Color(media, media, media);

                imageOutput.setRGB(i, j, color.getRGB());
            }
        }
        return imageOutput;
    }

    public BufferedImage median3x3 (BufferedImage imgOriginal) {
        int width = imgOriginal.getWidth();
        int height = imgOriginal.getHeight();
        BufferedImage imageOutput = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        for (int i = 1; i < width -1; i++) {
            for (int j= 1; j < height -1; j++) {

                Color color1 = new Color(imgOriginal.getRGB(i-1, j-1));
                Color color2 = new Color(imgOriginal.getRGB(i-1, j));
                Color color3 = new Color(imgOriginal.getRGB(i-1, j+1));
                Color color4 = new Color(imgOriginal.getRGB(i, j-1));
                Color color5 = new Color(imgOriginal.getRGB(i, j));
                Color color6 = new Color(imgOriginal.getRGB(i, j+1));
                Color color7 = new Color(imgOriginal.getRGB(i+1, j-1));
                Color color8 = new Color(imgOriginal.getRGB(i+1, j));
                Color color9 = new Color(imgOriginal.getRGB(i+1, j+1));

                int r1 = color1.getRed();
                int r2 = color2.getRed();
                int r3 = color3.getRed();
                int r4 = color4.getRed();
                int r5 = color5.getRed();
                int r6 = color6.getRed();
                int r7 = color7.getRed();
                int r8 = color8.getRed();
                int r9 = color9.getRed();

                int[] vector = {r1, r2, r3, r4, r5, r6, r7, r8, r9};
                Arrays.sort(vector);
                int media = vector[(vector.length-1)/2];

                Color color = new Color(media, media, media);

                imageOutput.setRGB(i, j, color.getRGB());
            }
        }
        return imageOutput;
    }

    public BufferedImage averageNeighborhoodSize (BufferedImage imgOriginal, int neighborhoodSize) {
        int width = imgOriginal.getWidth();
        int height = imgOriginal.getHeight();
        BufferedImage imageOutput = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        int referenceValue = neighborhoodSize/2;

        for (int y = referenceValue; y < height - referenceValue; y++) {
            for (int x = referenceValue; x < width - referenceValue; x++) {
                int soma = 0;

                for (int i = - referenceValue; i <= referenceValue; i++) {
                    for (int j = - referenceValue; j <= referenceValue; j++) {
                        Color color = new Color(imgOriginal.getRGB(x + j, y + i));
                        int red = color.getRed();
                        soma += red;
                    }
                }

                int media = soma/(neighborhoodSize*neighborhoodSize);
                Color newColor = new Color(media, media, media);
                imageOutput.setRGB(x, y, newColor.getRGB());
            }
        }
        return imageOutput;
    }

    public BufferedImage medianNeighborhoodSize (BufferedImage imgOriginal, int neighborhoodSize) {
        int width = imgOriginal.getWidth();
        int height = imgOriginal.getHeight();

        BufferedImage imageOutput = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        int referenceValue = neighborhoodSize/2;

        for (int y = referenceValue; referenceValue < height - y; y++) {
            for (int x = referenceValue; x < width - referenceValue; x++) {
                int[] values = new int [neighborhoodSize * neighborhoodSize];
                int position = 0;

                for (int i = -referenceValue; i <= referenceValue; i++) {
                    for (int j = -referenceValue; j <= referenceValue; j++) {
                        Color color = new Color(imgOriginal.getRGB(x + j, y + i));
                        int red = color.getRed();
                        values[position] += red;
                        position++;
                    }

                }

                Arrays.sort(values);
                int mediana = values[(values.length-1)/2];

                Color newColor = new Color(mediana, mediana, mediana);
                imageOutput.setRGB(x, y, newColor.getRGB());
            }
        }
        return imageOutput;
    }

    public BufferedImage convolution(BufferedImage imgOriginal, double[] kernel) {

        int width = imgOriginal.getWidth();
        int height = imgOriginal.getHeight();
        BufferedImage imageOutput = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);


        int neighborhoodSize = (int)Math.sqrt(kernel.length);
        int referenceValue = neighborhoodSize/2;

        if (neighborhoodSize == 3) {

            for (int y = referenceValue; y < height - referenceValue; y++) {
                for (int x = referenceValue; x < width - referenceValue; x++) {
                    double soma = 0;
                    int position = 0;

                    for (int i = -referenceValue; i <= referenceValue; i++) {
                        for (int j = -referenceValue; j <= referenceValue; j++) {
                            Color color = new Color(imgOriginal.getRGB(x + j, y + i));
                            int red = color.getRed();
                            soma += (double)red * kernel[position];
                            position++;
                        }
                    }
                    int result = (int)soma;
                    if (result < 0) result = 0;
                    if (result > 255) result = 255;
                    Color newColor = new Color(result, result, result);
                    imageOutput.setRGB(x, y, newColor.getRGB());
                }
            }
        }

        else {

            for (int y = referenceValue; y < height - referenceValue; y++) {
                for (int x = referenceValue; x < width - referenceValue; x++) {
                    int soma = 0;
                    int position = 0;

                    for (int i = -referenceValue; i <= referenceValue; i++) {
                        for (int j = -referenceValue; j <= referenceValue; j++) {
                            Color color = new Color(imgOriginal.getRGB(x + j, y + i));
                            int red = color.getRed();
                            soma += (double)red * (kernel[position]/256);
                            position++;
                        }
                    }
                    int result = (int)soma;
                    if (result < 0) result = 0;
                    if (result > 255) result = 255;
                    Color newColor = new Color(result, result, result);
                    imageOutput.setRGB(x, y, newColor.getRGB());
                }
            }
        }
        return imageOutput;
    }
}