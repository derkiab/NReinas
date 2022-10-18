/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.Random; 
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 *
 * @author ADMIN
 */
//CLASE PARA CONTABILIZAR LA CANTIDAD DE CRUZAS Y QUE PADRES PARTICIPARON.

class cruza{
    
    public int padre1;
    public int padre2;
    
    public cruza(int p1,int p2){
        
        this.padre1=p1;
        this.padre2=p2;
        
    }
}

//ORDENAR POR VALOR DE FITNESS, UTILIZA INTERFAZ COMPARATOR

class FitnessSorter implements Comparator<Individuo> {

    @Override
    public int compare(Individuo o1, Individuo o2) {
        return Integer.compare(o1.getFitness(), o2.getFitness());
    }

}

//CLASE INDIVIDUO DONDE SE GUARDA TODA LA INFORMACION DE ESTE

class Individuo  {
    
    public static int nroIndividuo=0;
    public HashSet<Integer> arrayIndividuo;
    public ArrayList<Integer> arrayxd;
    private final int size;
    private int fitness;
    
    public Individuo(int n){
        
        this.size = n;
        nroIndividuo++;
        arrayIndividuo=new HashSet();
        arrayxd=new ArrayList();
        
    }
    
    
    public Individuo(int n,ArrayList<Integer> x){
        
        this.size=n;
        nroIndividuo++;
        arrayxd=x;
        
    }
    
    public void llenarIndividuo(int n){
        
        Random rand = new Random();
        
        
        while(arrayIndividuo.size()!=n){
            
            int numero = 1+rand.nextInt(n);
            System.out.println(arrayIndividuo.add(numero));
            
        }  
    }
    
    public void setFitness(int fit){
        
        this.fitness=fit;
    }
    
    public int getFitness(){
        return this.fitness;
    }
    
}

public class JavaApplication2 {

    /**
     * @param tPoblacion
     * @param tTablero
     * @return 
     */
    
    //CREA POBLACION INICIAL
    
    public static ArrayList crearPoblacion(int tPoblacion, int tTablero){
        
        ArrayList<Individuo> array = new ArrayList();
       
        for(int i=0;i<tPoblacion;i++){
            
            array.add(new Individuo(tTablero));
            
            array.get(i).llenarIndividuo(tTablero);
            
            Iterator value = array.get(i).arrayIndividuo.iterator();
            
            
            while (value.hasNext()) { 

                array.get(i).arrayxd.add((int)value.next());
                
            } 
            
            Collections.shuffle(array.get(i).arrayxd);
        }
        
        return array;
    }
    // METODO CREADOR DE MASCARA ALEATORIA
    
    public static ArrayList crearMascara(int tTablero){
        
        ArrayList<Integer> mascara = new ArrayList();
        Random num = new Random();
        
        for(int i=0;i<tTablero;i++){
            
            int a = num.nextInt(2);
            mascara.add(a);
                
        }
        
        return mascara;
    }
    
    // CALCULA PEOR CASO DE FITNESS OSEA COLISIONES MAXIMAS.
    
    public static int calcularColisionesMaximas(int n) {
        
        int colisionesMax = 0;
        
        for (int i=n-1;i>0;i--) {
            
            colisionesMax+= i;
        }
    
        return colisionesMax*2;
    }
    
    //ENCUENTRA NUMERO DE COLISIONES, FITNESS.
    
    public static int contarColisiones(ArrayList<Integer> individuo,int n) {
        
        int numColisiones = 0;
        final int colisionesMax = calcularColisionesMaximas(n);
        
        for (int i=0;i<n-1;i++) {
            
            for (int j=i+1;j<n;j++) {
                
                //FORMULA DEL FITNESS
                
                if (j-i==Math.abs(individuo.get(i)-individuo.get(j)))
               
                    numColisiones++;
            }
        }
        return numColisiones*2;
    }
    
    //METODO CREADOR DE HIJOS
    
    public static ArrayList crearHijo(Individuo padre1, Individuo padre2, ArrayList<Integer> mask){
        
        ArrayList<Integer> mascara = new ArrayList<>();
        ArrayList<Integer> p1 = new ArrayList<>();
        ArrayList<Integer> p2 = new ArrayList<>();
        ArrayList<Integer> Hijo = new ArrayList<>();
        mascara=mask;
        p1= padre1.arrayxd;
        p2= padre2.arrayxd;
        
        for(int i = 0; i < mascara.size();i++){
            
            if(mascara.get(i)==0){
                
                Hijo.add(p1.get(i));
                
            }else if(mascara.get(i)==1){
                
                Hijo.add(p2.get(i));
                
            }
        }
        return Hijo;
    }
   
    public static void main(String[] args) {
        // TODO code application logic here
        
        Scanner sc = new Scanner(System.in);
        int tamanioTablero;
        int tamanioPoblacion;
        int generacion=0;
        int numeroColisionesMaximas;
        
        //ESTRUCTURAS DE DATOS PARA GUARDAR LA POBLACION INICIAL Y MASCARA
        
        ArrayList<Individuo> poblacionInicial = new ArrayList();
        ArrayList<Integer> mascara = new ArrayList<>();
        
        //SE PIDE AL USUARIO TAMAÑO TABLERO Y TAMAÑO DE POBLACION INICIAL
        
        System.out.println("Ingresar Tamanio Tablero");
        tamanioTablero= sc.nextInt();
        
        System.out.println("Ingresar Tamanio Poblacion");
        tamanioPoblacion=sc.nextInt();
        
        //SE CREA LA POBLACION INICIAL Y LA MASCARA DE FORMA ALEATORIA
        
        poblacionInicial = crearPoblacion(tamanioPoblacion, tamanioTablero);
        mascara = crearMascara(tamanioTablero);
        numeroColisionesMaximas = calcularColisionesMaximas(tamanioTablero);
        
        for(int i=0;i<poblacionInicial.size();i++){
            
            System.out.println(poblacionInicial.get(i).arrayxd);
            
        }
        
        System.out.println("Mascara");
        System.out.println(mascara);
        System.out.println("Colisiones Maximas");
        System.out.println(numeroColisionesMaximas);
        
        System.out.println("Colisiones por individuo");
        
        for(int i=0;i<poblacionInicial.size();i++){
            
            System.out.println(contarColisiones(poblacionInicial.get(i).arrayxd, tamanioTablero));
            poblacionInicial.get(i).setFitness(contarColisiones(poblacionInicial.get(i).arrayxd, tamanioTablero));
        }
        
        /*poblacionInicial.sort(new FitnessSorter());
        
        System.out.println("ORDENADO POR FITNESS??");
        
        for(int i=0;i<poblacionInicial.size();i++){
            
            
            System.out.println(poblacionInicial.get(i).getFitness());
        }
        */
        
        //ARRAYS AUXILIARES PARA MANTENER GUARDADA LA NUEVA POBLACION QUE SE GENERE
        
        ArrayList<Individuo> poblacion = new ArrayList();
        ArrayList<Individuo> nuevaPoblacion = new ArrayList();
       
        //CICLO DE GENERACIONES
        
        while(generacion<4000){
            
            int contador=0;
            Random rand = new Random();
            
            if(generacion==0){
                poblacion=poblacionInicial;
            }
            if(generacion>0){
                poblacion=nuevaPoblacion;
            }
            
            
            ArrayList<Individuo> hijos = new ArrayList<>();
            ArrayList<cruza> cruzas = new ArrayList<>();
            
            int auxiliar=0;
            
            //CRUZA OCURRE EN ESTE CICLO WHILE
 
            while(contador<poblacion.size()){   
                
                
                int randomP1 = rand.nextInt(tamanioTablero);
                int randomP2 = rand.nextInt(tamanioTablero);
                //System.out.println("RANDOM1"+randomP1);
                //System.out.println("RANDOM2" + randomP2);
                if(randomP1!=randomP2){
                    
                    if(cruzas.isEmpty()){
                        
                        cruzas.add(new cruza(randomP1, randomP2));
                        System.out.println("CRUZA HA OCURRIDO");
                        Individuo hijo = new Individuo(tamanioTablero,crearHijo(poblacion.get(randomP1), poblacion.get(randomP2), mascara));
                        hijos.add(hijo);
                        contador++;
                        
                    }else{
                        
                        for(int i=0;i<cruzas.size();i++){
                            
                            if((randomP1==cruzas.get(i).padre1 && randomP2==cruzas.get(i).padre2)||(randomP2==cruzas.get(i).padre1 && randomP1==cruzas.get(i).padre2)){
                                
                                auxiliar++;
                            }
                        }
                        
                        if(auxiliar==0){
                        
                        System.out.println("CRUZA HA OCURRIDO");
                        cruzas.add(new cruza(randomP1, randomP2));
                        Individuo hijo = new Individuo(tamanioTablero,crearHijo(poblacion.get(randomP1), poblacion.get(randomP2), mascara));
                        hijos.add(hijo);
                        contador++;
                        
                        } 
                    }
                    auxiliar=0;
                }
            }

            // ARREGLAR HIJOS UTILIZANDO EXPRESION LAMBDA
                
            for(int i=0;i<hijos.size();i++){
                    
                final ArrayList<Integer> arr = hijos.get(i).arrayxd;
                    
                    
                System.out.println(hijos.get(i).arrayxd);
                List<Integer> faltan = IntStream.rangeClosed(1,tamanioTablero)
                    .filter(k -> !arr.contains(k))
                    .boxed().collect(Collectors.toList());
                System.out.println("faltan: " + faltan);
                    
                    
                Set<Integer> unique = new HashSet<>();
                Iterator<Integer> faltanIterator = faltan.iterator();
                    for(int j = 0; j < tamanioTablero; j++){
                        if(!unique.add(arr.get(j))){
                            arr.set(j, faltanIterator.next());
                        }
                    }
                System.out.println("modificado : " + arr);
                hijos.get(i).arrayxd=arr;
                    
                System.out.println("NUEVO HIJO"+ hijos.get(i).arrayxd);
                    
            }
            
            //MUTACION GENETICA
            
            for(int i=0;i<hijos.size();i++){
                
                if(new Random().nextDouble() <= 0.10 ){
                    
                    Random randomize = new Random();
                    int r1= randomize.nextInt(tamanioTablero);
                    int r2= randomize.nextInt(tamanioTablero);
                    System.out.println("ran1 "+r1 + "ran2 " + r2);
                    if(r1!=r2){
                        ArrayList<Integer> auxi = new ArrayList<>();
                        auxi=hijos.get(i).arrayxd;
                        
                        Collections.swap(auxi, r1, r2);
                        
                        hijos.get(i).arrayxd=auxi;
                        
                    }
                }
            }
            
            //CALCULO DE FITNESS
            
            for(int i=0;i<poblacion.size();i++){
                
                poblacion.get(i).setFitness(contarColisiones(poblacion.get(i).arrayxd, poblacion.get(i).arrayxd.size()));
                
            }
            for(int i=0;i<hijos.size();i++){
                
                hijos.get(i).setFitness(contarColisiones(hijos.get(i).arrayxd, hijos.get(i).arrayxd.size()));
                
            }
            
            //ORDENAR POR FITNESS
            
            poblacion.sort(new FitnessSorter());
            hijos.sort(new FitnessSorter());

            //CREAR NUEVA POBLACION Y CHEQUEAR SI EXISTE SOLUCION
            
            ArrayList<Individuo> nuevaPobla = new ArrayList<>();
            
            for(int i=0;i<poblacion.size();i++){
                
                nuevaPobla.add(poblacion.get(i));
                
            }
            for(int i=0;i<hijos.size();i++){
                
                nuevaPobla.add(hijos.get(i));
                
            }
            nuevaPobla.sort(new FitnessSorter());
            ArrayList<Individuo> nuevaPoblaxd = new ArrayList<>();

            for (int i = 0; i < nuevaPobla.size()/2; i++) {
                nuevaPoblaxd.add(nuevaPobla.get(i));
            }
            for(int i=0;i<nuevaPoblaxd.size();i++){
                
                System.out.println("CURRENT FITNESS"+nuevaPoblaxd.get(i).getFitness());
                
                if(nuevaPoblaxd.get(i).getFitness()==0){
                    
                    System.out.println("SOLUCION ENCONTRADA");
                    System.out.println(nuevaPoblaxd.get(i).arrayxd);
                    System.exit(0);
                }
            }
            generacion++;
            nuevaPoblacion=nuevaPoblaxd;
        }
    }  
}
