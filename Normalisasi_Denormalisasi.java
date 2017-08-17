/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package skripsi;

import java.text.DecimalFormat;
/**
 *
 * @author Bayu
 */
public class Normalisasi_Denormalisasi {
    //atribut normalisasi data
    double dt_latih_normalisasi[][];
    double dt_uji_normalisasi[][];
    double target_latih_normalisasi[];
    double target_uji_normalisasi[];
    //atribut denormalisasi data
    double denormalisasi_data[];
    
    // Method Normalisasi Data Latih
     public void NormalisasiDataLatih(double Maks_Data, double Min_Data, double data_latih[][]){
       this.dt_latih_normalisasi = new double [data_latih.length][data_latih[0].length];
       for (int i=0; i < data_latih.length; i++){
           for (int j=0; j < data_latih[0].length; j++){
               double selisih = data_latih[i][j]-Min_Data;
               this.dt_latih_normalisasi[i][j] = ((0.8*selisih)/(Maks_Data-Min_Data))+0.1;
               dt_latih_normalisasi[i][j] = dt_latih_normalisasi[i][j];
           }
       }
   }
    
     // Method untuk mengambil hasil normalisasi data latih
    public double[][] getDt_latih_normalisasi() {
        return dt_latih_normalisasi;
    }
     
    // Method untuk Normalisasi Target Data Latih
      public void NormalisasiTargetDataLatih (double Maks_Data, double Min_Data, double target_dt_latih[]){
       this.target_latih_normalisasi = new double [target_dt_latih.length];
          for (int i=0; i < target_dt_latih.length; i++){
              target_latih_normalisasi[i] = ((0.8*(target_dt_latih[i]-Min_Data))/(Maks_Data-Min_Data))+0.1;
              target_latih_normalisasi[i] = target_latih_normalisasi[i];
       }
   }
      
      // Method untuk mengambil nilai target data latih yang sudah dinormalisasi
      public double[] getTarget_latih_normalisasi() {
        return target_latih_normalisasi;
    }
   
      
    //Method Normalisasi Data Uji
   public void NormalisasiDataUji(double data_uji[][], double Maks_data, double Min_data){
        this.dt_uji_normalisasi = new double [data_uji.length][data_uji[0].length];
         for (int i=0; i < data_uji.length; i++){
           for (int j=0; j < data_uji[0].length; j++){
               this.dt_uji_normalisasi[i][j] = ((0.8*(data_uji[i][j]-Min_data))/(Maks_data-Min_data))+0.1;
           }
       }
    }

   // Method untuk mengambil data  uji yang sudah dinormalisasi
    public double[][] getDt_uji_normalisasi() {
        return dt_uji_normalisasi;
    }
    
    // Method untuk Normalisasi Target Data Uji
     public void NormalisasiTargetDataUji (double Maks_Data, double Min_Data, double target_dt_uji[]){
       this.target_uji_normalisasi = new double [target_dt_uji.length];
          for (int i=0; i < target_dt_uji.length; i++){
              if (target_dt_uji[i] == 0){
                  target_uji_normalisasi[i] = 0;
              } else {
                 target_uji_normalisasi[i] = ((0.8*(target_dt_uji[i]-Min_Data))/(Maks_Data-Min_Data))+0.1; 
              }   
       }
   }

      // Method untuk mengambil target data uji yang sudah dinormalisasi
    public double[] getTarget_uji_normalisasi() {
        return target_uji_normalisasi;
    }
    
    //Method Denormalisasi Data
    public void setDenormalisasi_data(double[] hasil_prediksi, double Maks_Data, double Min_Data) {
        this.denormalisasi_data = new double [hasil_prediksi.length];    
        for (int i=0; i < hasil_prediksi.length; i++){
            denormalisasi_data[i] = Math.round((((hasil_prediksi[i]-0.1)*(Maks_Data - Min_Data))/0.8)+Min_Data);
        }
        
    }
    
    // Method untuk mengambil hasil denormalisasi data
    public double[] getDenormalisasi_data() {
        return denormalisasi_data;
    }
    
    
}
