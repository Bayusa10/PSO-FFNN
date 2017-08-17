/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package skripsi;

import java.io.FileNotFoundException;
import java.util.Random;
/**
 *
 * @author Bayu
 */
public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        Baca_Data coba = new Baca_Data();
        Normalisasi_Denormalisasi test = new Normalisasi_Denormalisasi();
        PSO pso = new PSO();
        Prediksi prediksi = new Prediksi ();
        Random random = new Random();
        Setting_Parameter parameter = new Setting_Parameter();
        
        coba.BacaDataPengangguran();
        double dt_pengangguran [] = coba.getData_pengangguran();
        String label[] = coba.getLabel_dt();
        
        coba.getMaksMinData(dt_pengangguran);
        double maks = coba.getData_maks();
        double min = coba.getData_min();
        System.out.println("Maksimum : " + maks);
        System.out.println("Minimum : "+min);
        System.out.println();
        String Lokasi_Dt_Latih = "C:\\Users\\Bayu\\Documents\\NetBeansProjects\\Skripsi\\data\\data_latih\\Data_Latih_7_Input.txt";
        coba.BacaDataLatih(Lokasi_Dt_Latih);
        double dt_latih[] = coba.getDt_latih();
        
        int jumlah_data_latih = 32;
        int pola_data_latih = 7;
        
        coba.setDataLatih(dt_latih, jumlah_data_latih, pola_data_latih);
        double data_latih[][] = coba.getData_latih();
        System.out.println("Data Latih");
         for (int i=0; i < jumlah_data_latih ; i++){
           for (int j=0; j < pola_data_latih; j++){
               System.out.print(data_latih[i][j] + " ");
           } 
           System.out.println();
       } 
       
         System.out.println();
         System.out.println("Target Data Latih");
         double target_dt_latih [] = coba.getTarget_data_latih();
         
           for (int i=0; i < target_dt_latih.length ; i++){
               System.out.println(target_dt_latih[i] + " ");
           }
           
           System.out.println();
           System.out.println("Data Latih Normalisasi");
           test.NormalisasiDataLatih(maks, min, data_latih);
           double data_latih_normalisasi [][] = test.getDt_latih_normalisasi();
           
          for (int i=0; i < jumlah_data_latih ; i++){
           for (int j=0; j < pola_data_latih; j++){
               System.out.print(data_latih_normalisasi[i][j] + " ");
           } 
           System.out.println();
       } 
          System.out.println();
          System.out.println("Target Latih Normalisasi");
          test.NormalisasiTargetDataLatih(maks, min, target_dt_latih);
          double target_dt_latih_normalisasi []= test.getTarget_latih_normalisasi();
          
           for (int i=0; i < target_dt_latih_normalisasi.length ; i++){
               System.out.println(target_dt_latih_normalisasi[i] + " ");
           }
           
           System.out.println();
           String Lokasi_Dt_Uji = "C:\\Users\\Bayu\\Documents\\NetBeansProjects\\Skripsi\\data\\data_uji\\Data_Uji_7_Input.txt";
           coba.BacaDataUji(Lokasi_Dt_Uji);
           double dt_uji[] = coba.getDt_uji();
           int jumlah_dt_uji = 9;
           int pola_dt_uji = 7;
           coba.setDataUji(dt_uji, jumlah_dt_uji, pola_dt_uji);
           double data_uji[][] = coba.getData_uji();
           System.out.println("Data Uji");
           for (int i=0; i < jumlah_dt_uji ;i++){
               for (int j=0; j < pola_dt_uji; j++){
                   System.out.print(data_uji[i][j]+ " ");
               }
               
               System.out.println();
           }
         
           System.out.println();
           System.out.println("Target Data Uji");
           double target_dt_uji [] = coba.getTarget_data_uji();
           
           for (int i=0; i < target_dt_uji.length ; i++){
               System.out.println(target_dt_uji[i] + " ");
           }
           
           System.out.println();
           System.out.println("Normalisasi Data Uji");
           test.NormalisasiDataUji(data_uji, maks, min);
           double data_uji_normalisasi[][] = test.getDt_uji_normalisasi();
           
           for (int i=0; i < jumlah_dt_uji; i++){
               for (int j=0; j < pola_dt_uji; j++){
                   System.out.print(data_uji_normalisasi[i][j]+ " ");
               }
               
               System.out.println();
           }
           
           System.out.println();
           System.out.println("Normalisasi Target Data Uji");
           test.NormalisasiTargetDataUji(maks, min, target_dt_uji);
           double target_uji_normalisasi [] = test.getTarget_uji_normalisasi();
           
           for (int i=0; i < target_uji_normalisasi.length; i++){
               System.out.println(target_uji_normalisasi[i]+" ");
           }
           
          
           parameter.setJumlah_partikel(30);
           int jumlah_partikel = parameter.getJumlah_partikel();
           
           parameter.setJumlah_lapisan_input(7);
           int jumlah_lapisan_input = parameter.getJumlah_lapisan_input();
           
           parameter.setJumlah_lapisan_tersembunyi(7);
           int jumlah_lapisan_tersembunyi = parameter.getJumlah_lapisan_tersembunyi();
           
           parameter.setDimensi_partikel(jumlah_lapisan_input, jumlah_lapisan_tersembunyi);
           int dimensi_partikel = parameter.getDimensi_partikel();
           
           parameter.setWmax(0.7);
           double Wmax = parameter.getWmax();
           
           parameter.setWmin(0.4);
           double Wmin = parameter.getWmin();
           
           parameter.setC1(2);
           double c1   = parameter.getC1();
           
           parameter.setC2(2);
           double c2   = parameter.getC2();
           
           double r1   = random.nextDouble()*1;
           double r2   = random.nextDouble()*1;
           
           int iterasi = 0;
           
           parameter.setMaksimum_iterasi(250);
           int maksimum_iterasi = parameter.getMaksimum_iterasi();
           
           System.out.println();
           System.out.println("---------------------------------------------------");
           System.out.println("| PELATIHAN FEEDFORWARD NEURAL NETWORK DENGAN PSO |");
           System.out.println("---------------------------------------------------");
           System.out.println();
           System.out.println(" -- FASE INISIALISASI AWAL --");
           System.out.println();
           
           System.out.println(" -- INISIALISASI KECEPATAN AWAL PARTIKEL --");
           System.out.println();
           
           pso.setKecepatanPartikel(jumlah_partikel, dimensi_partikel);
           double Kecepatan_Partikel[][] = pso.getKecepatan_partikel();
           
           for (int j=0; j < jumlah_partikel; j++){
               for (int k=0; k < dimensi_partikel; k++){
                   System.out.print("|"+""+Kecepatan_Partikel[j][k]+""+"|"+"   ");
               }
               System.out.println();
           }
           
           System.out.println();
           System.out.println(" -- INISIALISASI POSISI AWAL PARTIKEL --");
           System.out.println();
           
           pso.setPosisiAwalPartikel(jumlah_partikel, dimensi_partikel);
           double Posisi_Partikel[][] = pso.getPosisi_partikel();
           
           for (int j=0; j < jumlah_partikel; j++){
               for (int k=0; k < dimensi_partikel; k++){
                   System.out.print("|"+""+Posisi_Partikel[j][k]+""+"|"+"   ");
               }
               System.out.println();
           }
           
           System.out.println();
           System.out.println(" -- FITNESS AWAL PARTIKEL --");
           System.out.println();
           pso.HitungFitness(Posisi_Partikel, data_latih_normalisasi, target_dt_latih_normalisasi, jumlah_lapisan_input, jumlah_lapisan_tersembunyi);
           double Fitness_Partikel[] = pso.getFitness();
            for (int j=0; j < jumlah_partikel; j++){
                System.out.println("Fitness Partikel Ke-"+""+(j+1)+""+Fitness_Partikel[j]);
           }
           
           System.out.println();
           System.out.println(" -- INISIALISASI PBEST --");
           System.out.println();
           pso.InitPBest(Posisi_Partikel, Fitness_Partikel);
           double PBest[][] = pso.getPBest();
           for (int j=0; j < jumlah_partikel; j++){
               for (int k=0; k < dimensi_partikel; k++){
                   System.out.print("|"+""+PBest[j][k]+""+"|"+"   ");
               }
               System.out.println();
           }
           
           System.out.println();
           System.out.println(" -- FITNESS PBEST --");
           System.out.println();   
           double Fitness_PBest[] = pso.getFitness_PBest();
            for (int j=0; j < jumlah_partikel; j++){
                System.out.println("Fitness PBest Ke-"+""+(j+1)+""+":"+""+Fitness_PBest[j]);
           }
           
           System.out.println();
           System.out.println(" -- INISIALISASI GBEST --");
           System.out.println();
           pso.InitGBest(PBest, Fitness_PBest);
           double GBest[] = pso.getGBest();
               for (int k=0; k < dimensi_partikel; k++){
                   System.out.print("|"+""+GBest[k]+""+"|"+"   ");
               }
           
           System.out.println();
           System.out.println(" -- FITNESS GBEST --");
           System.out.println();   
           double Fitness_GBest = pso.getFitness_GBest();
           System.out.println("Fitness GBest  :"+" "+Fitness_GBest);
           
           System.out.println();
           System.out.println(" -- UPDATE KECEPATAN PARTIKEL --");
           System.out.println();
           
           pso.updateKecepatan(Wmax, Wmin, c1, c2, PBest, GBest, Posisi_Partikel, iterasi, maksimum_iterasi, r1, r2);
           Kecepatan_Partikel = pso.getKecepatan_partikel();
           for (int j=0; j < jumlah_partikel; j++){
               for (int k=0; k < dimensi_partikel; k++){
                   System.out.print("|"+""+Kecepatan_Partikel[j][k]+""+"|"+"   ");
               }
               System.out.println();
           }
           
           System.out.println();
           System.out.println(" -- UPDATE POSISI PARTIKEL --");
           System.out.println();
           
           pso.updatePosisi(Kecepatan_Partikel);
           Posisi_Partikel = pso.getPosisi_partikel();
           
           for (int j=0; j < jumlah_partikel; j++){
               for (int k=0; k < dimensi_partikel; k++){
                   System.out.print("|"+""+Posisi_Partikel[j][k]+""+"|"+"   ");
               }
               System.out.println();
           }
           
           System.out.println();
           System.out.println(" -- FITNESS PARTIKEL --");
           System.out.println();
           pso.HitungFitness(Posisi_Partikel, data_latih_normalisasi, target_dt_latih_normalisasi, jumlah_lapisan_input, jumlah_lapisan_tersembunyi);
           Fitness_Partikel = pso.getFitness();
            for (int j=0; j < jumlah_partikel; j++){
                System.out.println("Fitness Partikel Ke-"+""+(j+1)+""+Fitness_Partikel[j]);
           }
           
           System.out.println();
           System.out.println(" -- UPDATE PBEST --");
           System.out.println();
           pso.updatePBest(Posisi_Partikel, Fitness_Partikel, PBest, Fitness_PBest);
           PBest = pso.getPBest();
           for (int j=0; j < jumlah_partikel; j++){
               for (int k=0; k < dimensi_partikel; k++){
                   System.out.print("|"+""+PBest[j][k]+""+"|"+"   ");
               }
               System.out.println();
           }
           
           System.out.println();
           System.out.println(" -- FITNESS PBEST --");
           System.out.println();   
           Fitness_PBest = pso.getFitness_PBest();
            for (int j=0; j < jumlah_partikel; j++){
                System.out.println("Fitness PBest Ke-"+""+(j+1)+""+":"+""+Fitness_PBest[j]);
           } 
           
           System.out.println();
           System.out.println(" -- UPDATE GBEST --");
           System.out.println();
           pso.updateGBest(PBest, Fitness_PBest);
           GBest = pso.getGBest();
               for (int k=0; k < dimensi_partikel; k++){
                   System.out.print("|"+""+GBest[k]+""+"|"+"   ");
               }
           
           System.out.println();
           System.out.println(" -- FITNESS GBEST --");
           System.out.println();   
           Fitness_GBest = pso.getFitness_GBest();
           System.out.println("Fitness GBest  :"+" "+Fitness_GBest); 
           
           
           for (int i=iterasi+1; i < maksimum_iterasi; i++){
           System.out.println();
           System.out.println("-- ITERASI KE - "+" "+i);
           System.out.println();
           System.out.println(" -- UPDATE KECEPATAN PARTIKEL --");
           System.out.println();
           pso.updateKecepatan(Wmax, Wmin, c1, c2, PBest, GBest, Posisi_Partikel, i, maksimum_iterasi, r1, r2);
           Kecepatan_Partikel = pso.getKecepatan_partikel();
           for (int j=0; j < jumlah_partikel; j++){
               for (int k=0; k < dimensi_partikel; k++){
                   System.out.print("|"+""+Kecepatan_Partikel[j][k]+""+"|"+"   ");
               }
               System.out.println();
           }
           
           System.out.println();
           System.out.println(" -- UPDATE POSISI PARTIKEL --");
           System.out.println();
           
           pso.updatePosisi(Kecepatan_Partikel);
           Posisi_Partikel = pso.getPosisi_partikel();
           
           for (int j=0; j < jumlah_partikel; j++){
               for (int k=0; k < dimensi_partikel; k++){
                   System.out.print("|"+""+Posisi_Partikel[j][k]+""+"|"+"   ");
               }
               System.out.println();
           }
           
           System.out.println();
           System.out.println(" -- FITNESS PARTIKEL --");
           System.out.println();
           pso.HitungFitness(Posisi_Partikel, data_latih_normalisasi, target_dt_latih_normalisasi, jumlah_lapisan_input, jumlah_lapisan_tersembunyi);
           Fitness_Partikel = pso.getFitness();
            for (int j=0; j < jumlah_partikel; j++){
                System.out.println("Fitness Partikel Ke-"+" "+(j+1)+""+Fitness_Partikel[j]);
           }
           
           System.out.println();
           System.out.println(" -- UPDATE PBEST --");
           System.out.println();
           pso.updatePBest(Posisi_Partikel, Fitness_Partikel, PBest, Fitness_PBest);
           PBest = pso.getPBest();
           for (int j=0; j < jumlah_partikel; j++){
               for (int k=0; k < dimensi_partikel; k++){
                   System.out.print("|"+""+PBest[j][k]+""+"|"+"   ");
               }
               System.out.println();
           }
           
           System.out.println();
           System.out.println(" -- FITNESS PBEST --");
           System.out.println();   
           Fitness_PBest = pso.getFitness_PBest();
            for (int j=0; j < jumlah_partikel; j++){
                System.out.println("Fitness PBest Ke-"+""+(j+1)+""+":"+""+Fitness_PBest[j]);
           } 
           
           System.out.println();
           System.out.println(" -- UPDATE GBEST --");
           System.out.println();
           pso.updateGBest(PBest, Fitness_PBest);
           GBest = pso.getGBest();
               for (int k=0; k < dimensi_partikel; k++){
                   System.out.print("|"+""+GBest[k]+""+"|"+"   ");
               }
           
           System.out.println();
           System.out.println(" -- FITNESS GBEST --");
           System.out.println();   
           Fitness_GBest = pso.getFitness_GBest();
           System.out.println("Fitness GBest  :"+" "+Fitness_GBest);     
                  
           }
 
           System.out.println();
           System.out.println(" -- PREDIKSI --");
           System.out.println();
           prediksi.Prediksi(GBest,data_uji_normalisasi,jumlah_lapisan_input, jumlah_lapisan_tersembunyi);
           double hasil_prediksi [] = prediksi.getHasil_prediksi();
           for (int i=0; i < hasil_prediksi.length; i++){
               System.out.println("PREDIKSI DATA UJI KE - " +" "+(i+1)+" "+ hasil_prediksi[i]);
           }
           System.out.println();
           System.out.println(" -- DENORMALISASI DATA --");
           System.out.println();
           test.setDenormalisasi_data(hasil_prediksi, maks, min);
           double denormalisasi_data[] = test.getDenormalisasi_data();
           for (int i=0; i < denormalisasi_data.length; i++){
               System.out.println("PREDIKSI DATA UJI KE - " +" "+(i+1)+" "+ denormalisasi_data[i]);
           }
           
           System.out.println();
           System.out.println(" -- AFER --");
           System.out.println();
           prediksi.AFER(denormalisasi_data,target_dt_uji);
           double AFER = prediksi.getError();
           System.out.println(AFER);
           
    }      
}

        
    
    
