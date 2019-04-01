public class Pila {
    int N    = 1000000;
    int M[]  = new int[N];
    int TOPE = -1;
    
    public Pila() {}
    
    public void meter(int v) {
        TOPE++;
        M[TOPE]=v;
    }
    
    public int sacar() {
        int v = M[TOPE];
        TOPE--;
        return v;
    }
    
    public boolean vacia() { return TOPE<0; }
}