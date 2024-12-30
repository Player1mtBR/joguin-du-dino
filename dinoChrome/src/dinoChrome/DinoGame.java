//obs talvez o jogo rode mais rapido em maquinas mais potentes

package dinoChrome;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;

public class DinoGame extends JPanel implements KeyListener {
    private int tRexX = 150, tRexY = 525, tRexHeight = 40, tRexWidth = 40;
    private int groundY = 600;
    private int bgX = 0;
    private int groundX = 0;
    
    private boolean jumping = false;
    private int jumpHeight = 0;
    private ArrayList<Rectangle> obstacle;
    private ArrayList<Rectangle> obstacleBig;//teste pra outros obstaculos / funcionando
    private float gameSpeed = 10;
    private int score = 0;
    private double seed = 0; //seed alocada aqui em cima pra poder printar
    private Timer gameTimer;
    
    //imagens
    private Image tRexImg;
    private Image obstacleImg;
    private Image groundImg;
    private Image BGImg;
    
    //dino anim frames
    private int currentFrame = 0; // num do frame atual
    private int totalFrames = 8; // qtd de frames
    private Timer animationTimer; // timer pra updt os frames

    
    
    public DinoGame() {
        setPreferredSize(new Dimension(1280, 720));
        setBackground(Color.WHITE);
        addKeyListener(this);
        setFocusable(true);
        
        //carregar imagens
        tRexImg = new ImageIcon("imgs/dino.png").getImage();
        obstacleImg = new ImageIcon("imgs/rock.png").getImage();
        groundImg = new ImageIcon("imgs/ground03.png").getImage();
        BGImg = new ImageIcon("imgs/bkgnd.png").getImage();

        
        
        // cria um array pros obstaculos
        obstacle = new ArrayList<>();
        obstacleBig = new ArrayList<>();

        // timer pra funcionar o jogo
        gameTimer = new Timer(1000 / 60, e -> gameLoop());
        gameTimer.start();
        
        // anim timer / updt 100ms
        animationTimer = new Timer(100, e -> updateAnimFrame());
        animationTimer.start();
    }
    
    //updt dino anim
    private void updateAnimFrame() {
        currentFrame = (currentFrame + 1) % totalFrames; // loop pelos frames
        if (jumping) {
        	currentFrame = 2;
        }
    }

    private void gameLoop() {//mecanicas 
    	
    	//mover background / se bgX <= negativo(largura): bgX reseta pra 0
    	bgX -= gameSpeed/4;
    	if (bgX <= -8000) {//8000px da imagem
    		bgX = 0;
    	}
    	//mesma coisa mas agora pro chao
    	groundX -= gameSpeed;
    	if (groundX <= -1280) {
    		groundX = 0;
    	}
    	
    	// condicao do pulo / se esta pulando: coordenada Y do dino é decrementada pela altura do pulo, altura do pulo vai decrementando de 1 em 1
    	// depois se o Y do dino for o Y inicial (esta no chao), o dino nao esta mais pulando
        if (jumping) {
            tRexY -= jumpHeight;
            jumpHeight--;
            if (tRexY >= 525) {
                tRexY = 525;
                jumping = false;
            }
        }
        //smol boi
        
        // i = qtd de obstatulos / .size() é o tamanho de elementos da array, ñ confuindir com a altura dos obstaculos  
        for (int i = 0; i < obstacle.size(); i++) {
            Rectangle newObstacle = obstacle.get(i);
            newObstacle.x -= gameSpeed;//move de acordo com a velocidade dojogo
            
            if (newObstacle.x < 0) {//o que acontece depois que o obstaculo passa da borda
                obstacle.remove(i);
                score++;
                gameSpeed++;
                i--;
            }
            
            // checa a colisao e game over
            if (newObstacle.intersects(new Rectangle(tRexX, tRexY, tRexWidth, tRexHeight))) {
            	
                gameTimer.stop();
                JOptionPane.showMessageDialog(this, "Game Over! Sua pontuação foi: " + score);
                System.exit(0);
            }
        }
        //big boi
        for (int i = 0; i < obstacleBig.size(); i++) {
            Rectangle newObstacleBig = obstacleBig.get(i);
            newObstacleBig.x -= gameSpeed;//move de acordo com a velocidade dojogo
            
            if (newObstacleBig.x < 0) {//o que acontece depois que o obstaculo passa da borda
                obstacleBig.remove(i);
                score++;
                gameSpeed++;
                i--;
            }
            
            // checa a colisao e game over
            if (newObstacleBig.intersects(new Rectangle(tRexX, tRexY, tRexWidth, tRexHeight))) {
            	
                gameTimer.stop();
                JOptionPane.showMessageDialog(this, "Game Over! Sua pontuação foi: " + score);
                System.exit(0);
            }
        }

        // aleatoriedade do spawn
        seed = Math.random();
        int obstacleHeight = 45;
        
        if (seed < 0.01) {
            obstacle.add(new Rectangle(1280, groundY - obstacleHeight, 25, obstacleHeight));
        }
        if (seed < 0.003) {
            obstacleBig.add(new Rectangle(1280, groundY - (obstacleHeight + 30), 50, (obstacleHeight + 30)));
        }

        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        
        //desenhar imgs / parte das operacoes sao valores pra ajustar o tamanho que ñ ficava legal so com o tamanho da hitbox
        
        //background movel usando 2 instancias
        g.drawImage(BGImg, bgX, 0, (bgX + 8000), 1170, 0, 0, 8000, 1161, null);
        g.drawImage(BGImg, (bgX + 8000), 0, 8000, 1170, 0, 0, 8000, 1161, null);
        

        // desenhar chao
        g.drawImage(groundImg, groundX, (groundY - 240), 1280, 360, null);
        g.drawImage(groundImg, (groundX + 1280), (groundY - 240), 1280, 360, null);//flip horizontal da imagem
        
        // desenhar o dino
        int frameX = currentFrame * 680; //frameX = var que guarda o total de frames * 680px da imagem
        g.drawImage(tRexImg, (tRexX - 50), (tRexY - 50), (tRexX + tRexWidth) + 125, (tRexY + tRexHeight) + 50, frameX, 0, frameX + 680, 472, null); //numeros adds pros parametros pra compensar a diferenca da caixa de colisao

        // desenhar os obstaculos
        for (Rectangle rect : obstacle) {
            g.drawImage(obstacleImg, (rect.x - 20), (rect.y - 20), (rect.width + 40), (rect.height + 40), null);
        }
        for (Rectangle rect : obstacleBig) {
            g.drawImage(obstacleImg, (rect.x - 20), (rect.y - 20), (rect.width + 40), (rect.height + 40), null);
        }
        
        //remover bloco de comentario para ver colisoes
        
        /*
        // haha cube go brrrrrrrrr
        // desenhar o dino
        g.setColor(Color.YELLOW);
        g.fillRect(tRexX, tRexY, tRexWidth, tRexHeight);
        

        // obstaculos
        g.setColor(Color.RED);
        for (Rectangle obstacle : obstacle) {
            g.fillRect(obstacle.x, obstacle.y, obstacle.width, obstacle.height);
        }
        g.setColor(Color.ORANGE);
        for (Rectangle obstacleBig : obstacleBig) {
            g.fillRect(obstacleBig.x, obstacleBig.y, obstacleBig.width, obstacleBig.height);
        }
        
        // chao
        g.setColor(Color.DARK_GRAY);
        g.fillRect(0, groundY, getWidth(), getHeight() - groundY);
        */
        
        // escrever pontuacao na tela
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, 150, 50);
        g.setColor(Color.BLACK);
        g.drawString("Pontuação: " + score, 10, 20);
        //debug seed de spawn
        g.drawString(String.valueOf(seed), 10, 40);
    }

    // input da barra de espaco
    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE && !jumping) {
            jumping = true;
            jumpHeight = 15;
        }
    }
    
    //ignore
    @Override
    public void keyReleased(KeyEvent e) {}
    @Override
    public void keyTyped(KeyEvent e) {}
}