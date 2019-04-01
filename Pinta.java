import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
public class Pinta extends JFrame implements ActionListener {
    int     n     = 30;                // cantidad filas y columnas
    int     a     = 20;                // ancho de cada boton
    JButton c[][] = new JButton[n][n]; // matriz de botones
    Pila    pila  = new Pila(); 

    public Pinta() {
        setLayout(null);
        setSize(10+(n+1)*a,50+n*a);
        
        // crea y posiciona los n*n botones
        int x = 5, y=5;
        for(int i=0;i<n;i++) {
            for(int j=0;j<n;j++) {
                c[i][j] = new JButton();
                c[i][j].setBounds(x, y, a, a);
                c[i][j].setActionCommand("x");  // asinga 'x' a los botones de color fijo
                // aleatoriamente pinta algunos botones
                if(Math.random()<0.5) {
                    c[i][j].setBackground(Color.gray);
                    c[i][j].addActionListener(this); // este botón reaccionará cuando se le haga Clic
                    c[i][j].setActionCommand(String.valueOf(n*i+j)); // a los botones que deben pintarse
                                                                     // les asigna un valor desde el cual
                                                                     // se puede recuperar su fila y columna
                    
                }
                add(c[i][j]);
                x = x + a;
                if(x == (5 + n*a) ) {
                    x=5;
                    y=y+a;
                }
            }
        }
        
        setVisible(true);
    }
    
    // método que se ejecuta cuando se hace Clic en un botón
    public void pintarboton(int f, int c){
        //c[f][c].setBackground(Color.gray);
    }
    public void actionPerformed(ActionEvent e) {
        Color cl, color_a_pintar;
        int v, i,j, cr, cv, ca;
        
        // genera al azar valores R, G, B
        cr = (int)(Math.random()*256);
        cv = (int)(Math.random()*256);
        ca = (int)(Math.random()*256);
        cl = new Color(cr, cv, ca); // con esos valores crea un color aleatorio
        color_a_pintar = c[Integer.parseInt(e.getActionCommand())/n][Integer.parseInt(e.getActionCommand())%n].getBackground();
        
        pila.meter(Integer.parseInt(e.getActionCommand())); // mete en la pila el valor de este boton
        
        int valor = Integer.parseInt(e.getActionCommand());

        boolean meter = false;//Decimos en un comienzo que no ha metido ningun valor a la pila
        int fila;
        int columna;
        while(!pila.vacia()) {//Mientras la pila no contenga ningun valor
            fila = pila.M[pila.TOPE]/n;//Obtenemos la fila del valor que este en el tope de la pila
            columna = pila.M[pila.TOPE]%n;//Obtenemos la columna del valor que este en el tope de la pila
            c[fila][columna].setBackground(cl);//pintamos el boton del array de botones que este en la misma posicion (fila, columna) que obtubimos del tope de la pila
            
            if(fila>0){//Si puedo verificar un boton que este arriba del actual
                if(c[fila-1][columna].getBackground() == color_a_pintar && !meter){//Si ese boton es del color base (botones que se pueden pintar, en un inicio gray) y aun no se ha metido ningun valor a la pila
                    fila = fila - 1;//me desplazo hasta ese boton
                    meter = true;//decimos que ya estamos listos para meter a la pila y no necesitamos pasar por las otras comprobaciones
                }
            }
            //Lo mismo que el caso anterior pero con el boton que esta a la derecha del actual
            if(columna < n-1){
                if(c[fila][columna+1].getBackground() == color_a_pintar && !meter){
                    columna = columna + 1;
                    meter = true;
                }
            }
            //Lo mismo que el caso anterior pero con el boton que esta abajo del actual
            if(fila < n-1){
                if(c[fila+1][columna].getBackground() == color_a_pintar && !meter){
                    fila = fila + 1;
                    meter = true;
                }
            }
            //Lo mismo que el caso anterior pero con el boton que esta a la izquierda del actual
            if(columna > 0){
                if(c[fila][columna-1].getBackground() == color_a_pintar && !meter){
                    columna = columna - 1;
                    meter = true;
                }
            }
            
            if(!meter){//Si no pudo moverse a ningun boton porque no pudo pasar las restricciones
                pila.sacar();//Sacamos ese valor de la pila
            }
            else{//Si se pudo transladar al siguiente boton
                valor = n * fila + columna;//calculamos su valor para meterlo en la pila
                pila.meter(valor);//lo metemos en la pila y asi repetiremos la misma secuencia pero con el siguiente boton
                meter = false;//seteamos meter, para que todo vuelva a funcionar como en un inicio
            }
            
            
        }
        
        
    }
        
    public static void main() {
        new Pinta();
    }
}