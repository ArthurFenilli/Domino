import java.io.BufferedReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.Stack;

public class JogoDomino{

    private int num_pecas;
    private ArrayList<Domino> pecas_disp;
    private Stack<Domino> pecas_usd;
    private int num_usadas;

    public JogoDomino(String arq){
        pecas_disp = new ArrayList<>();
        pecas_usd = new Stack<>();
        leitura(arq);
        jogo4();
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
            num_usadas = 0;
            
            while ((line = reader.readLine()) != null) {
                try{
                    String[] aux = line.split(" ");
                    int esq = Integer.parseInt(aux[0]);
                    int dir = Integer.parseInt(aux[1]);
                    Domino peca = new Domino(esq, dir);
                    pecas_disp.add(peca);
                }
                catch (Exception ex) {
                    System.err.println("Ocorreu um erro ao ler o arquivo: " + ex.getMessage());
    
                    //Imprime o rastreamento do erro
                    ex.printStackTrace();
                }
            }
        } 
        catch (Exception ex) {
            System.err.println("Ocorreu um erro ao ler o arquivo: " + ex.getMessage());
    
            //Imprime o rastreamento do erro
            ex.printStackTrace();
        }
    }

    public void jogo(){
        int i = 0;
        while (i <pecas_disp.size()) {
            Domino d = pecas_disp.get(i);
            if(pecas_usd.size() == 0){
                pecas_usd.add(d);
                pecas_disp.remove(d);
                jogo();
                if(pecas_usd.size()!= num_pecas){
                    d.inverte();
                    jogo();

                }
                if(pecas_usd.size()!= num_pecas){
                    pecas_disp.add(d);
                    pecas_usd.remove(d);
                }
            }
            else{
                if (d.esq == pecas_usd.get(pecas_usd.size()-1).dir) {
                    pecas_usd.add(d);
                    pecas_disp.remove(d);
                    jogo();
                    if(pecas_usd.size()!= num_pecas){
                        pecas_disp.add(d);
                        pecas_usd.remove(d);
                    }
                }
                if(pecas_usd.size()!= num_pecas){
                    d.inverte();
                }
                if (d.esq == pecas_usd.get(pecas_usd.size()-1).dir) {
                    pecas_usd.add(d);
                    pecas_disp.remove(d);
                    jogo();
                    if(pecas_usd.size()!= num_pecas){
                        pecas_disp.add(d);
                        pecas_usd.remove(d);
                    }
                }
            }
            i++;
        }
    }

    public void jogo2() {
        int i = 0;
        if (pecas_usd.size() == num_pecas) {
            return;
        }
    
        while ( i < pecas_disp.size()) {
            Domino d = pecas_disp.get(i);
    
            if (pecas_usd.isEmpty() || d.esq == pecas_usd.get(pecas_usd.size() - 1).dir) {
                pecas_usd.add(d);
                pecas_disp.remove(d);
                jogo2();
    
                if (pecas_usd.size() == num_pecas) {
                    return;
                }
                pecas_usd.remove(pecas_usd.size() - 1);
                pecas_disp.add(d);
            }
            if(d.dir!=d.esq){
                d.inverte();
    
                if (pecas_usd.isEmpty() || d.esq == pecas_usd.get(pecas_usd.size() - 1).dir) {
                    pecas_usd.add(d);
                    pecas_disp.remove(d);
                    jogo2(); 
    
                    if (pecas_usd.size() == num_pecas) {
                        return;
                    }
    
                    pecas_usd.remove(pecas_usd.size() - 1);
                    pecas_disp.add(d);
                }
                d.inverte();
            }
            i++;
        }
    }


    public void jogo3() {
        int i = 0;
    
        while ( i < pecas_disp.size()) {
            Domino d = pecas_disp.get(i);
            if(d.usada == true){
                i++;
                continue;
            }
    
            if (pecas_usd.isEmpty() || d.esq == pecas_usd.peek().dir) {
                pecas_usd.push(d);
                d.inverteUsada();
                jogo3();
    
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
                    jogo3();
    
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



   
    




    public static void main(String args[]){
        JogoDomino jd = new JogoDomino("caso28.txt");
        for (Domino d : jd.pecas_usd) {
            System.out.print(d.esq + "-" + d.dir + " ");
        }
    }
}