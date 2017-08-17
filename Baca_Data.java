/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package skripsi;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
/**
 *
 * @author Bayu
 */
public class Baca_Data {
    double data_maks;
    double data_min;
    double data_pengangguran[];
    String label_dt[];
    String label_data_latih[];
    double dt_latih[];
    double data_latih[][];
    double target_data_latih[];
    String lokasi_data_uji;
    String label_data_uji[];
    String lbl_dt_uji[];
    double dt_uji[];
    double data_uji[][];
    double target_data_uji[];
    
    
   //Baca Data Pengangguran 
   public void BacaDataPengangguran () throws FileNotFoundException{
       File file = new File ("C:\\Users\\Bayu\\Documents\\NetBeansProjects\\Skripsi\\Data_Pengangguran.txt");
       ArrayList<Double> list_data = new ArrayList<>(); 
       ArrayList<String> label_data = new ArrayList<>();
       Scanner baca_data = new Scanner(file);
       
       //Baca data pengangguran keseluruhan dan masukkan ke dalam array list
       while (baca_data.hasNext()){
           String label = baca_data.next();
           double data = baca_data.nextDouble();
           list_data.add(data);
           label_data.add(label);
       }
       baca_data.close();
       
       //Memindahkan data pengangguran yang ada pada array list ke dalam array 1 dimensi
       this.data_pengangguran = new double [list_data.size()];
       this.label_dt        = new String [label_data.size()];
       for (int i=0; i < list_data.size(); i++){
           data_pengangguran[i] = list_data.get(i);
           label_dt[i]  = label_data.get(i).replace("_","");    
       }
       
   }

    public double[] getData_pengangguran() {
        return data_pengangguran;
    }

    public String[] getLabel_dt() {
        return label_dt;
    }
   
    
   public void getMaksMinData(double dt_pengangguran[]){
       double data[] = dt_pengangguran;
       this.data_min = data[0];
       this.data_maks = data[0];
       for (int i=0; i < data.length; i++){
           if (data_maks < data[i]){
               data_maks = data[i];
           }
           if (data_min > data[i]){
                data_min = data[i];
           }  
       }  
   }

    public double getData_maks() {
        return data_maks;
    }

    public double getData_min() {
        return data_min;
    }

    //Baca Data Latih
   public void BacaDataLatih (String Lokasi_Data) throws FileNotFoundException{
       File file = new File (Lokasi_Data);
       ArrayList<Double> list_data_latih = new ArrayList<>(); 
       ArrayList<String> label_dt_latih = new ArrayList<>();
       Scanner baca_data = new Scanner(file);
       
       //Baca data pengangguran keseluruhan dan masukkan ke dalam array list
       while (baca_data.hasNext()){
           double data = baca_data.nextDouble();
           list_data_latih.add(data);
       }
       baca_data.close();
       
       this.dt_latih = new double [list_data_latih.size()];
       this.label_data_latih = new String [label_dt_latih.size()];
       for (int i=0; i < list_data_latih.size(); i++){
           dt_latih[i] = list_data_latih.get(i);
       }
   }

    public double[] getDt_latih() {
        return dt_latih;
    }

   public void setDataLatih(double data_latih[], int jumlah_data_latih, int pola_dt_latih){
       double dt_latih[] = data_latih;
       int jlh_dt = jumlah_data_latih;
       int pola_dt = pola_dt_latih+1;
       this.data_latih = new double [jlh_dt][pola_dt];
       this.target_data_latih = new double [jlh_dt];
       
       // Ambil data latih sesuai pola
       for (int i=0; i < jlh_dt;i++){
           int x = i;
           for (int j=0; j < pola_dt; j++){
               this.data_latih[i][j] = dt_latih[x];
               x++;
           }
       }
       
       // Ambil target data latih
       for (int i=0; i < jlh_dt; i++){
           target_data_latih[i] = this.data_latih[i][pola_dt_latih];
       }    
   }

    public double[][] getData_latih() {
        return data_latih;
    }
    
    public double[] getTarget_data_latih() {
        return target_data_latih;
    }
 
   //Baca Data Uji
    public void BacaDataUji (String Lokasi_Data) throws FileNotFoundException{
       File file = new File (Lokasi_Data);
       ArrayList<Double> list_data_uji = new ArrayList<>(); 
       ArrayList<String> label_dt_uji = new ArrayList<>();
       Scanner baca_data = new Scanner(file);
       
       //Baca data pengangguran keseluruhan dan masukkan ke dalam array list
       while (baca_data.hasNext()){
           String label = baca_data.next();
           double data = baca_data.nextDouble();
           list_data_uji.add(data);
           label_dt_uji.add(label);
       }
       baca_data.close();
       
       this.dt_uji = new double [list_data_uji.size()];
       this.label_data_uji = new String [label_dt_uji.size()];
       for (int i=0; i < list_data_uji.size(); i++){
           dt_uji[i] = list_data_uji.get(i);
           label_data_uji[i] = label_dt_uji.get(i).replace("_","");
       }
    }

    public double[] getDt_uji() {
        return dt_uji;
    }

    public void setDataUji (double data_uji[], int jumlah_dt_uji, int pola_dt_uji){
       double dt_uji[] = data_uji;
       int jlh_dt = jumlah_dt_uji;
       int pola_dt = pola_dt_uji+1;
       this.data_uji = new double [jlh_dt][pola_dt];
       this.target_data_uji = new double [jlh_dt];
       //Ambil data uji
       for (int i=0; i < jlh_dt;i++){
           int x = i;
           for (int j=0; j < pola_dt; j++){
               this.data_uji[i][j] = dt_uji[x];
               x++;
           }
       }  
       // Ambil Target Data Uji
       for (int i=0; i < jlh_dt; i++){
           this.target_data_uji[i] = this.data_uji[i][pola_dt_uji];
       }
    }
    
    public double[][] getData_uji() {
        return data_uji;
    }   
   
    public double[] getTarget_data_uji() {
        return target_data_uji;
    }

    public String[] getLbl_dt_uji(int pola_data) {
        this.lbl_dt_uji = new String [target_data_uji.length];
        int x = pola_data;
        for (int i=0; i < target_data_uji.length; i++){
            lbl_dt_uji[i] = label_data_uji[x];
            x++;
        }
        return lbl_dt_uji;
    }

    
   
}
