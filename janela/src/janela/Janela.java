package janela;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
public class Janela extends JFrame {

	

		    private TilePanel painel;

		    public Janela() {
		        setTitle("Joguin du Dino");
		        //setSize(1920, 1080);
		        setExtendedState(JFrame.MAXIMIZED_BOTH);
		        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		        setResizable(false);
		        setLocationRelativeTo(null);

		        painel = new TilePanel();
		        add(painel);
		        
		    }
	public static void main(String[] args) {	
		SwingUtilities.invokeLater(new Runnable() {
		            @Override
		            public void run() {
		                new Janela().setVisible(true);
		            }
		        });
		    
		}
	}

 class TilePanel extends JPanel implements ActionListener, KeyListener {

    private static final int[][] MAPA = {
        {-1, -1, -1, -1, -1, -1, -1, -1, -1},
        {-1, -1, 1, -1, -1, -1, -1, -1, -1}, 
        {-1, -1, -1, 1, -1, 1008, 1008, 1008, -1},
        {-1, -1, -1, 1, -1, 1008, 1005, 1008, -1},
        {-1, -901, -1, 1, -1, 1008, 1008, 1008, -1},
        {4000, 4100, 4200, -1, -1, 1, -1, -1, -1}, 
        {4001, 4101, 4201, -1, -801, -1, -1, -1, -1},
        {4002, 4102, 4202, -1, -1, -1, -1, -1, -1},
        {2114, 2214, 2214, 2214, 2214, 2214, 2214, 2214, 2314},
        {2115, 2215, 2215, 2215, 2215, 2215, 2215, 2215, 2315}
    };
    //img width & height
    private int LARGURA = 680;
    private int ALTURA = 472;
    
    private int framex = 0;
    private int shift = 0;
    private int indiceImagemAtual = 0;
    private int posX = 300;
    private int posY = 700;
    private int deltaX = 2;
    private int deltaY = 2;
    private Image imagemDino = new ImageIcon("imgs/dino.png").getImage();
    private Image imagemFundo = new ImageIcon("imgs/BG.png").getImage();
    private Timer timer;

    public TilePanel() {
        setFocusable(true);
        addKeyListener(this);
       // carregarImagens();
        timer = new Timer(50, this);
        timer.start();
    }

   

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        desenhar(g);
    }

    private void desenhar(Graphics g) {
        g.drawImage(imagemFundo, 0, 0, getWidth(), getHeight(), null);

        
        
        //Ñ SEI A LOGICA PRA MUDAR OS FRAMES AAAAAAAAAAAAAAAAAAAAAAAAAAAA 
        //vou a mimir, desculpa Igão
        
        
        
  /*      for (int i = 0; i < MAPA.length; i++) {
          
                if (MAPA[i][j] != -1) {
                    g.drawRect(j * SIZE, i * SIZE, SIZE, SIZE); 
                }
            
        }*/

        /*g.drawImage(imagemDino, posX, posY, posX + 340, posY + 236, 
        		0,0,680,472,null);
    }*/
    	g.drawImage(imagemDino, posX, posY, posX + (LARGURA/2), posY + (ALTURA/2), 
    			shift,0,LARGURA,ALTURA,null);
    	while (LARGURA < 5440) {
    		LARGURA += LARGURA;
    		shift += 680;
    	}
    	
	}

    private void atualizarPosicao() {
        posX += (deltaX * 4);

    }
    
    private void alterarFrame() {
    	
    }
       /* if (posX < 0 || posX + imagensAnimacao[indiceImagemAtual].getWidth() > getWidth()) {
            deltaX = -deltaX;
        }
        if (posY < 0 || posY + imagensAnimacao[indiceImagemAtual].getHeight() > getHeight()) {
            deltaY = -deltaY;
        }

        indiceImagemAtual = (indiceImagemAtual + 1) % imagensAnimacao.length;
    }
*/

    @Override
    public void actionPerformed(ActionEvent e) {
       //atualizarPosicao();
        //repaint();
    }

    @Override
    public void keyPressed(KeyEvent e) {
    	atualizarPosicao();
        repaint();
    }

    /*@Override
    public void keyReleased(KeyEvent e) {
        // Implementar se necessário
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // Implementar se necessário
    }*/
}