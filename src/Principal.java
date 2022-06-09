import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Principal {
    public static void main (String []args) throws IOException {
        File file = new File("src\\teste.jpg");
        BufferedImage image = ImageIO.read(file);

        double[] horizontal = {-1, -2, -1, 0, 0, 0, 1, 2, 1};
        double[] vertical = {-1, 0, 1, -2, 0, 2, -1, 0, 1};
        double[] linhasOeste = {1, 1, -1, 1, -2, -1, 1, 1, -1};
        double[] laplaciano = {0, -1, 0, -1, 4, -1, 0, -1, 0};


        Filtragem f = new Filtragem();
        double[][][] imageYiq = f.rgb2YIQ(image);

        BufferedImage imageOutput = f.convolution(image, laplaciano);
//        averageNeighborhoodSize
//        medianNeighborhoodSize
//        convolution
        JLabel mylabel = new JLabel(new ImageIcon(imageOutput));
        JPanel mypanel = new JPanel();
        mypanel.add(mylabel);
        JFrame myframe = new JFrame("Imagem com Filtro");
        myframe.setSize(new Dimension(imageOutput.getWidth(),imageOutput.getHeight()));
        myframe.add(mypanel);
        myframe.setVisible(true);
    }
}