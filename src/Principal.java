import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Principal {
    public static void main (String []args) throws IOException {
        File file = new File("src\\testee.jpg");
        BufferedImage image = ImageIO.read(file);

        Filtragem f = new Filtragem();
        double[][][] imageYiq = f.rgb2YIQ(image);
        BufferedImage imageOutput = f.yiq2RGB(f.negativeY(imageYiq));


        JLabel mylabel = new JLabel(new ImageIcon(imageOutput));
        JPanel mypanel = new JPanel();
        mypanel.add(mylabel);
        JFrame myframe = new JFrame("Imagem com Filtro");
        myframe.setSize(new Dimension(imageOutput.getWidth(),imageOutput.getHeight()));
        myframe.add(mypanel);
        myframe.setVisible(true);
    }




}