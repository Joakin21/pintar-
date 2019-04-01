
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Rellenar extends JPanel implements MouseListener {
    private int     w      = 500;             // ancho y alto de imagen
    private byte    px[][] = new byte[w][w];  // arreglo con misma cantidad de x0s y y0s
                                              // que se usará para representar los pixeles de la imagen
    private boolean llenar = false;
    private Pila    pila   = new Pila();
    private int     x0, y0, x1, y1, f, c, v;

    
    public Rellenar() {
        addMouseListener(this);
        int nr, wr,hr;
        
        // decide al azar una cantidad de rectangulos a dibujar
        nr = 8 + (int)(Math.random()*10);
        
        // decide al azar sus posiciones, ancho y alto
        for(int i=0;i<nr;i++) {
            wr = 60 + (int)(Math.random()*100);
            hr = 60 + (int)(Math.random()*100);
            x0 = (int)(Math.random()*(w-wr));
            x1 = x0 + wr - 1;
            y0 = (int)(Math.random()*(w-hr));
            y1 = y0 + hr - 1;
            for(int x=x0;x<x0+wr;x++) px[x][y0] = 1; // linea superior
            for(int y=y0;y<y0+hr;y++) px[x0][y] = 1; // linea izquierda
            for(int y=y0;y<y0+hr;y++) px[x1][y] = 1; // linea derecha        
            for(int x=x0;x<x0+wr;x++) px[x][y1] = 1; // linea inferior
        }
        repaint();
    }
    
    // dibuja todo
    public void paint(Graphics g) {
        
        // dibuja los rectangulos en color rojo
        g.setColor(Color.red);
        for(int x=0;x<w;x++) {
            for(int y=0;y<w;y++) {
                if(px[x][y]==1) {
                    g.setColor(Color.red);
                    g.drawLine(x,y,x+1,y+1);
                }
                if(px[x][y]==2) {
                    g.setColor(Color.green);
                    g.drawLine(x,y,x+1,y+1);
                }
            }
        }
        
        // si se hizo Clic en un pixel que no es rojo...
        if(llenar) {
            // pinta de verde todo pixel adyacente, excepto al toparse con uno rojo
            // es decir que rellena de verde el area interna de un rectangulo o el
            // area externa a todos ellos, según en donde se haya hecho Clic
            g.setColor(Color.green);
            for(int f=0;f<w;f++) {
                for(int c=0;c<w;c++) {
                    if(px[f][c]==2) g.drawLine(f,c,f,c);//pinta un pixel
                }
            }
            llenar=false;
        }
    }
    
    public void mouseEntered (MouseEvent e) {}
    public void mouseExited  (MouseEvent e) {}
    public void mouseReleased(MouseEvent e) {}
    public void mousePressed (MouseEvent e) {}
    
    // método que se ejcuta al hacer Clic en un pixel
    public void mouseClicked (MouseEvent e) {
        x0 = e.getX();
        y0 = e.getY();
        boolean meter = false;
        // si este pixel NO es rojo
        if(px[x0][y0]==0) {
            llenar = true; // activa indicador
            v = w*x0 + y0; // crea un valor con sus coordenadas
            pila.meter(v); // mete ese valor en la pila

            while(!pila.vacia()) {

                x0 = pila.M[pila.TOPE] / w;//Obtenemos la fila del valor que este en el tope de la pila
                y0 = pila.M[pila.TOPE] % w;//Obtenemos la columna del valor que este en el tope de la pila
                px[x0][y0] = 2;//Al array de pixeles que este en la misma posicion (fila, columna) que obtubimos del tope de la pila, decimos que sea 2 para que lo pinte de verde
                repaint();//llamamos al metodo que pinta en pantalla
                if(x0>0){//Si puedo verificar un pixel que este arriba del actual
                    if(px[x0-1][y0] == 0 && !meter){//Si el pixel que esta arriba del actual no tiene color y aun no se ha metido valor en la pila
                        x0 = x0 - 1;//me desplazo hasta ese pixel
                        meter = true;//decimos que ya estamos listos para meter a la pila y no necesitamos pasar por las otras comprobaciones
                    }
                }
                //Lo mismo que el caso anterior pero con el pixel que esta a la derecha del actual
                if(y0 < w-1){
                    if(px[x0][y0+1] == 0 && !meter){
                        y0 = y0 + 1;
                        meter = true;
                    }
                }
                //Lo mismo que el caso anterior pero con el pixel que esta abajo del actual
                if(x0 < w-1){
                    if(px[x0+1][y0] == 0 && !meter){
                        x0 = x0 + 1;
                        meter = true;
                    }
                }
                //Lo mismo que el caso anterior pero con el pixel que esta a la izquierda del actual
                if(y0 > 0){
                    if(px[x0][y0-1] == 0 && !meter){
                        y0 = y0 - 1;
                        meter = true;
                    }
                }
                if(!meter){//Si no pudo moverse a ningun pixel porque no pudo pasar las restricciones
                    pila.sacar();//Sacamos ese valor de la pila
                }
                else{//Si se pudo transladar al siguiente boton
                    v = w*x0 + y0;//calculamos su valor para meterlo en la pila
                    pila.meter(v);//lo metemos en la pila y asi repetiremos la misma secuencia pero con el siguiente pixel
                    meter = false;//seteamos meter, para que todo vuelva a funcionar como en un inicio
                }
            }
        }        
    }
    
    
    public static  void main() {
        JFrame f = new JFrame();
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.getContentPane().add(new Rellenar());
        f.setSize(500,500);
        f.setVisible(true);
    }
}
