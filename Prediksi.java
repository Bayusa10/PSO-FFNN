/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package skripsi;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 *
 * @author Bayu
 */
public class Prediksi {
   double [] hasil_prediksi;
   double error = 0.0;
   DecimalFormat df = new DecimalFormat ("#.######");
   Skripsi data = new Skripsi();
   
   public void Prediksi (double GBest [], double data_uji[][],int jlh_lapisan_input, int jlh_lapisan_tersembunyi){
       
       this.hasil_prediksi = new double [data_uji.length];
       //Hitung prediksi
       for (int i=0; i < data_uji.length; i++){
            //inisialisasi semua variabel yang dibutuhkan untuk feedforward
            double Z_net[] = data.Init_Prediksi_Znet(jlh_lapisan_tersembunyi);
            double Z_j[]   = data.Init_Prediksi_Znet(jlh_lapisan_tersembunyi);
            double Y_net  = 0.0;
            double Y_k    = 0.0;
           
            int idx_bobot_input = jlh_lapisan_tersembunyi;
            
            for (int j=0; j < jlh_lapisan_tersembunyi; j++){
                for (int k=0; k < jlh_lapisan_input; k++){
                    Z_net[j] = Z_net[j]+(data_uji[i][k]*GBest[idx_bobot_input]);
                    idx_bobot_input++;
                }
            }
           
            for (int j=0; j < jlh_lapisan_tersembunyi; j++){
                Z_net[j] = GBest[j]+Z_net[j];
                Z_j [j] = 1.0/(1.0+(Math.exp(Z_net[j]*-1)));
            }
            
            int idx_bias_lapisan_tersembunyi = (jlh_lapisan_input*jlh_lapisan_tersembunyi) + jlh_lapisan_tersembunyi;
            int idx_bobot_lapisan_tersembunyi = idx_bias_lapisan_tersembunyi+1;
            
            for (int j=0; j < jlh_lapisan_tersembunyi; j++){
                Y_net = Y_net+(Z_j[j] * GBest[idx_bobot_lapisan_tersembunyi]);
                idx_bobot_lapisan_tersembunyi++;
            }
            
           Y_net = GBest[idx_bias_lapisan_tersembunyi] + Y_net;
           Y_k  = 1.0/(1.0+(Math.exp(Y_net*-1)));
           
           hasil_prediksi[i] = Double.valueOf(df.format(Y_k));
       }    
   }

    public double[] getHasil_prediksi() {
        return hasil_prediksi;
    }
    
    public void setBobot (double GBest[]){
        for (int i=0; i < GBest.length; i++){
            System.out.print(GBest[i] + "    ");
        }
    }
   
   public void AFER (double hasil_prediksi[], double target_data_uji[]){
      int pembulat = 4; 
      double temp = 0.0;
      double selisih = 0.0;
      int banyak_data = hasil_prediksi.length; 
      for (int i=0; i < hasil_prediksi.length; i++){
          if (target_data_uji[i] == 0.0){
              banyak_data--;
          } else {
               selisih = Math.abs((target_data_uji[i]-hasil_prediksi[i])/target_data_uji[i]);
               temp = temp+selisih;
          }
      } 
      
      error = (temp/banyak_data)*100;  
      BigDecimal bd = new BigDecimal(error);
      bd = bd.setScale(pembulat, BigDecimal.ROUND_UP);
      error = bd.doubleValue();
   }

    public double getError() {
        return error;
    }
   
   
    
}
