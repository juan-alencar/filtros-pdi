import java.awt.*;
import java.awt.image.BufferedImage;

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
        BufferedImage imageOutput = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
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
        imageOutput = yiq2RGB(imageYIQ);
        return imageOutput;
    }

    public BufferedImage brightnessMultY(BufferedImage image, double brightnessMult) {
        int width = image.getWidth();
        int height = image.getHeight();
        double[][][] imageYIQ = new double[height][width][3];
        BufferedImage imageOutput = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
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
        imageOutput = yiq2RGB(imageYIQ);
        return imageOutput;
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
}
