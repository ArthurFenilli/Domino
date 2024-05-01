import java.io.BufferedReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

public class JogoDomino{

    private int num_pecas;
    private ArrayList<Domino> pecas_disp;
    private ArrayList<Integer> impares;
    private Stack<Domino> pecas_usd;

    public JogoDomino(String arq){
        impares = new ArrayList<>();
        pecas_disp = new ArrayList<>();
        pecas_usd = new Stack<>();
        leitura(arq);
        if(impares.size()<3){
            jogo5();
        }
        else{
            System.out.println("Impossivel formar cadeia");
        }
    }

    class Domino{
        private int esq;
        private int dir;
        private boolean usada;

        private Domino(int esq, int dir){
            this.esq = esq;
            this.dir = dir;
            this.usada = false;
        }

        private void inverte(){
            int temp = this.esq;
            this.esq = this.dir;
            this.dir = temp;
            //invert= !invert;
        }

        private void inverteUsada(){
            this.usada = !this.usada;
        }
    }

    public void leitura(String lc){
        Path path = Paths.get(lc);
        try (BufferedReader reader = Files.newBufferedReader(path, Charset.forName("UTF-8"))) {
            String line = reader.readLine();
            this.num_pecas = Integer.parseInt(line);
            int[] collect = new int[11];
            
            while ((line = reader.readLine()) != null) {
                try{
                    String[] aux = line.split(" ");
                    int esq = Integer.parseInt(aux[0]);
                    int dir = Integer.parseInt(aux[1]);
                    collect[dir]++;
                    collect[esq]++;
                    Domino peca = new Domino(esq, dir);
                    pecas_disp.add(peca);
                }
                catch (Exception ex) {
                    System.err.println("Ocorreu um erro ao ler o arquivo: " + ex.getMessage());
    
                    //Imprime o rastreamento do erro
                    ex.printStackTrace();
                }
            }

            for(int i = 0; i<collect.length;i++){
                if(collect[i] % 2 != 0){
                    impares.add(i);
                }
            }

        } 
        catch (Exception ex) {
            System.err.println("Ocorreu um erro ao ler o arquivo: " + ex.getMessage());
    
            //Imprime o rastreamento do erro
            ex.printStackTrace();
        }
    }


    public void jogo4() {
        HashMap<String,Boolean> passou = new HashMap<>();
        int i = 0;
    
        while ( i < pecas_disp.size()) {
            Domino d = pecas_disp.get(i);
            if(d.usada == true){
                i++;
                continue;
            }
            if(passou.get(d.esq + "-" + d.dir ) == null){
                passou.put(d.esq + "-" + d.dir , true);
                passou.put(d.dir + "-" + d.esq , true);
            }
            else{
                
                i++;
                continue;
            }
    
            if (pecas_usd.isEmpty() || d.esq == pecas_usd.peek().dir) {
                pecas_usd.push(d);
                d.inverteUsada();
                jogo4();
    
                if (pecas_usd.size() == num_pecas) {
                    return;
                }
                pecas_usd.pop();
                d.inverteUsada();
            }
            if(d.dir!=d.esq){
                d.inverte();
    
                if (pecas_usd.isEmpty()|| d.esq == pecas_usd.peek().dir) {
                    pecas_usd.push(d);
                    d.inverteUsada();
                    jogo4();
    
                    if (pecas_usd.size() == num_pecas) {
                        return;
                    }
    
                    pecas_usd.pop();
                    d.inverteUsada();
                }
                d.inverte();
            }
            i++;
        }
    }


    public void jogo5() {
        HashMap<String,Boolean> passou = new HashMap<>();
        int i = 0;
        boolean pass = true;
    
        while ( i < pecas_disp.size()) {
            Domino d = pecas_disp.get(i);
            if(d.usada == true){
                i++;
                continue;
            }
            if(passou.get(d.esq + "-" + d.dir ) == null){
                passou.put(d.esq + "-" + d.dir , true);
                passou.put(d.dir + "-" + d.esq , true);
            }
            else{
                i++;
                continue;
            }

            if(impares.size()>0 && pecas_usd.empty()){
                if(d.esq != impares.get(0)){
                    pass = false;
                }
                else{
                    pass = true;
                }
                
            }
            else if(impares.size()>1 && pecas_usd.size() == num_pecas -1){
                if(d.dir != impares.get(1)){
                    pass = false;
                }
                else{
                    pass = true;
                }
            
            }
            
            if(pass == true){
                if ( pecas_usd.isEmpty() || d.esq == pecas_usd.peek().dir ) {
                    pecas_usd.push(d);
                    d.inverteUsada();
                    jogo5();
        
                    if (pecas_usd.size() == num_pecas) {
                        return;
                    }
                    pecas_usd.pop();
                    d.inverteUsada();
                }
            }
            
            if(d.dir!=d.esq){
                d.inverte();

                if(impares.size()>0 && pecas_usd.empty()){
                    if(d.esq != impares.get(0)){
                        pass = false;
                    }
                    else{
                        pass = true;
                    }
                    
                }
                else if(impares.size()>1 && pecas_usd.size() == num_pecas -1){
                    if(d.dir != impares.get(1)){
                        pass = false;
                    }
                    else{
                        pass = true;
                    }
                }

                if(pass == true){
                    if (  pecas_usd.isEmpty()|| d.esq == pecas_usd.peek().dir ) {
                        pecas_usd.push(d);
                        d.inverteUsada();
                        jogo5();
        
                        if (pecas_usd.size() == num_pecas) {
                            return;
                        }
        
                        pecas_usd.pop();
                        d.inverteUsada();
                    }
                    d.inverte();
                }
    
               
            }
            i++;
        }
    }


    public static void main(String args[]){
        JogoDomino jd = new JogoDomino("caso20.txt");
        for (Domino d : jd.pecas_usd) {
            System.out.print(d.esq + "-" + d.dir + " ");
        }
        
    }
}