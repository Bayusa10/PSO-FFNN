/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package skripsi;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.util.Random;
import java.text.DecimalFormat;
/**
 *
 * @author Bayu
 */
public class PSO {
    double kecepatan_partikel[][];
    double posisi_partikel[][];
    double fitness[];
    double PBest[][];
    double fitness_PBest[];
    double GBest[];
    double fitness_GBest;
    Skripsi data = new Skripsi();
    Random random = new Random();
    DecimalFormat df = new DecimalFormat ("#.######");
    
    // Method Inisialisasi Kecepatan Awal Partikel
    public void setKecepatanPartikel (int jumlah_partikel, int dimensi_partikel){
        // Inisialisasi banyaknya jumlah partikel dan dimensinya
        this.kecepatan_partikel = new double [jumlah_partikel][dimensi_partikel];
        // Isi kecepatan partikel awal dengan nilai 0
        for (int i=0; i < jumlah_partikel; i++){
            for (int j=0; j < dimensi_partikel; j++){
                kecepatan_partikel[i][j] = 0.0;
            }
        }
    }
    
    // Method untuk mengambil kecepatan partikel
    public double[][] getKecepatan_partikel() {
        return kecepatan_partikel;
    }
    
    // Method untuk inisialisasi posisi awal partikel
    public void setPosisiAwalPartikel (int jumlah_partikel, int dimensi_partikel){
        //Inisialisasi banyaknya jumlah posisi partikel dan dimensinya
       this.posisi_partikel = new double [jumlah_partikel][dimensi_partikel];
       //Inisialisasikan nilai minimum posisi partikel dan maksimum posisi partikel
        double minimum = -1.0;
        double maksimum = 1.0;
        //bangkitkan nilai posisi awal partikel secara acak didalam rentang [minimum,maksimum]
        int pembulat = 1;
        for (int i=0; i < jumlah_partikel; i++){
            for (int j=0; j < dimensi_partikel; j++){
                double acak = minimum + (maksimum - minimum) * random.nextDouble();
                posisi_partikel[i][j] = acak;
                BigDecimal bd = new BigDecimal(posisi_partikel[i][j]);
                bd = bd.setScale(pembulat, BigDecimal.ROUND_UP);
                posisi_partikel[i][j] = bd.doubleValue();
            }
        }
    }
    
    // Method untuk mengambil nilai posisi partikel
    public double[][] getPosisi_partikel() {
        return posisi_partikel;
    }
    
    // Method hitung fitness setiap partikel
    public void HitungFitness(double posisi_partikel[][], double data_latih[][], double target_dt_latih[],  
        int jlh_lapisan_input, int jlh_lapisan_tersembunyi){
       //Inisialisasi jumlah variabel fitness
        this.fitness = new double [posisi_partikel.length];   
        /*Hitung fitness untuk setiap posisi partikel, 
          saat fase inisialisasi maupun tidak, loop dilakukan sebanyak posisi partikel*/
        
        for (int i=0; i < posisi_partikel.length; i++){   
            //inisialisasi semua variabel yang dibutuhkan untuk proses feedforward 
            double Z_net[][] = data.Init_Znet_Z_j(data_latih.length, jlh_lapisan_tersembunyi);
            double Z_j[][]   = data.Init_Znet_Z_j(data_latih.length,jlh_lapisan_tersembunyi);
            double Y_net []  = data.Init_Y_net(data_latih.length);
            double Y_k[]     = data.Init_Y_net(data_latih.length);            
            /*
                Hitung Z_net
                Langkah 1 : Mengambil bobot dari lapisan input yang menuju lapisan tersembunyi
                            Bobot ini berada di dalam dimensi partikel pada indeks ke jlh_lapisan_tersembunyi
                            sampai dengan indeks ke (jlh_lapisan_tersembunyi x jlh_lapisan_input) 
                            + jlh_lapisan_tersembunyi
                Langkah 2 : Hitung Z_net dengan mengalikan setiap data latih dengan bobotnya
                Langkah 3 : Setelah Z_net didapat, tambahkan dengan bias dari lapisan input.
                            Bias lapisan input didapat dari dimensi partikel pada indeks ke - 1 sampai jlh_lapisan_tersembunyi    
            */
            
            for (int j=0; j < data_latih.length; j++){
                //Inisialisasikan indeks bobot pada lapisan input dimana indeksnya terletak pada jlh_lapisan_tersembunyi
                int idx_bobot_input = jlh_lapisan_tersembunyi;
                for (int k=0; k < jlh_lapisan_tersembunyi; k++){
                    for (int l=0; l < jlh_lapisan_input; l++){
                    // Ambil bobot dari lapisan input ke lapisan tersembunyi    
                    double bobot_input = posisi_partikel[i][idx_bobot_input];
                    // Kalikan bobot dari lapisan input yang didapat dengan data latih 
                    double temp = (data_latih[j][l]*bobot_input);
                    /*Jumlahkan nilai Z_net sebelumnya dengan nilai pada 
                        variabel temp
                    */
                    Z_net[j][k] += temp;
                    idx_bobot_input++;   
                }
                    // Ambil bias pada lapisan input dimana nilainya didapat pada dimensi partikel 
                    double bias_input = posisi_partikel[i][k];
                    // tambahkan nilai bias yang sudah didapat dengan Z_net
                    Z_net[j][k] = bias_input+Z_net[j][k];
               }
            }
            
            //Hitung Z_j
            for (int m=0; m < Z_net.length; m++){
                for (int n=0; n < Z_net[0].length; n++){
                    /* Kalikan nilai Z_net dengan -1, nilai ini digunakan sebagai pangkat dari nilai e 
                    untuk menghitung nilai Z_j */
                    double power = (Z_net[m][n]*-1);
                    /* Hitung nilai Z_j dengan fungsi aktivasi binner
                       fungsi aktivasi binner = 1/1+(e^-Z_net) 
                    */
                    Z_j[m][n] = 1.0/(1.0+(Math.exp(power)));
                }
            }
            
            //Inisialisai indeks untuk mengambil bias pada lapisan tersembunyi pada posisi_partikel
            int idx_bias_tersembunyi = jlh_lapisan_tersembunyi +(jlh_lapisan_input*jlh_lapisan_tersembunyi); 
            //Hitung Y_net 
            for (int o=0; o < Z_j.length; o++){
                //Inisialisasi indeks untuk mengambil bobot pada lapisan tersembunyi
                int idx_bobot_tersembunyi = idx_bias_tersembunyi + 1;
                for (int p=0; p < Z_j[0].length; p++){
                    // Ambil bobot pada lapisan tersembunyi dari posisi partikel berdasarkan indeks bobot tersembunyi
                    double bobot_tersembunyi = posisi_partikel[i][idx_bobot_tersembunyi];
                    // Simpan hasil perkalian antara Z_j dengan bobot pada lapisan tersembunyi
                    double temp = (Z_j[o][p] * bobot_tersembunyi);
                    // Tambahkan nilai Y_net sebelumnya dengan hasil perkalian antara Z_j dengan bobot pada lapisan tersembunyi
                    Y_net [o] = Y_net[o]+temp;
                    idx_bobot_tersembunyi++;
                }
                // Ambil bias pada lapisan tersembunyi dari dimensi partikel berdasarkan indeks bias tersembunyi 
                double bias_tersembunyi = posisi_partikel[i][idx_bias_tersembunyi];
                // Tambahkan bias yang sudah didapat dengan nilai Y_net
                Y_net[o] = bias_tersembunyi+Y_net[o];
            }
             
            //Hitung Y_k dan Error Jaringan
            double error = 0.0;
            for (int t=0; t < data_latih.length; t++){
                 /* Kalikan nilai Y_net dengan -1, nilai ini digunakan sebagai pangkat dari nilai e 
                    untuk menghitung nilai Y_k */
                double power = (Y_net[t]*-1);
                /* Hitung nilai Y_k dengan fungsi aktivasi binner
                       fungsi aktivasi binner = 1/1+(e^-Z_net) 
                    */
                Y_k [t] = 1.0/(1.0+(Math.exp(power)));
                // Hitung selisih antara Y_k dengan target data latih
                double selisih = Y_k[t] - target_dt_latih[t];
                // kuadratkan setiap besarnya selisih, kemudian jumlahkan
                double kuadrat_selisih = (Math.pow(selisih,2));
                error += kuadrat_selisih;
            }          
            //Hitung fitness , fitness = MSE
           fitness[i] = 1.0/Double.valueOf(df.format((1.0/data_latih.length) * error));
        }    
    }
    
    // Method untuk mengambil nilai fitness
    public double[] getFitness() {
        return fitness;
    }
    
    // Method untuk inisialisasi PBest awal
    public void InitPBest(double posisi_partikel[][], double fitness[]){
        this.PBest = new double [posisi_partikel.length][posisi_partikel[0].length];
        // Karena masih inisialisasi, maka nilai PBest = posisi awal partikel dan nilai fitness PBest = fitness partikel
        this.fitness_PBest = new double [posisi_partikel.length];
        for (int i=0; i < PBest.length; i++){
            for (int j=0; j < PBest[0].length; j++){
                PBest[i][j] = posisi_partikel[i][j];
            }
            fitness_PBest[i] = Double.valueOf(df.format(fitness[i]));
        }
    }
    
    // Method untuk mengambil nilai PBest
    public double[][] getPBest() {
        return PBest;
    }

    // Method untuk mengambil nilai fitness PBest 
    public double[] getFitness_PBest() {
        return fitness_PBest;
    }
    
    // Method untuk inisialisasi GBest
    public void InitGBest(double PBest[][], double fitness_Pbest[]){
        // Nilai GBest disimpan dalam array 1 D sepanjang dimensi dari PBest
        this.GBest = new double [PBest[0].length];
        // Inisialisasi nilai fitness GBest awal
        this.fitness_GBest = fitness_Pbest[0];
        // Inisialisasi nilai indeks minimum
        int idx_maksimum = 0;
        
        /* Nilai GBest diambil dari fitness PBest yang paling besar
           Hal ini dikarenakan nilai fitness dihitung menggunakan MSE
           semakin besar MSE, maka semakin baik untuk prediksi 
        */
        for (int i=0; i < fitness_Pbest.length; i++){
            if (fitness_Pbest[i] >= fitness_GBest){
                fitness_GBest = Double.valueOf(df.format(fitness_Pbest[i]));
                idx_maksimum = i;
            }
        }
        
        // Isi nilai GBest dengan PBest yang berada pada indeks minimum
        for (int j=0; j < PBest[0].length; j++){
            GBest [j] =PBest[idx_maksimum][j];
        }
    }
    
    // Method untuk mengambil nilai GBest
    public double[] getGBest() {
        return GBest;
    }

    // Method untuk mengambil nilai fitness GBest
    public double getFitness_GBest() {
        return fitness_GBest;
    }
    
    // Method untuk update kecepatan
    public void updateKecepatan (double Wmax, double Wmin, double c1, double c2, double PBest[][],
        double GBest[] ,double posisi_partikel[][], int iterasi, int iterasi_maksimum, double r1, double r2){  
        //Hitung koefisien W
        double n1   = Math.sqrt(iterasi+1);
        double n2   = Wmax-Wmin;
        double n3   = Math.sqrt(iterasi_maksimum+1);
        double W    = Wmax - ((n1*n2)/(n3));       
       //LDIW
       //double W = Wmax - (((double)iterasi*(Wmax-Wmin))/(double)iterasi_maksimum);
        //Update kecepatan partikel     
        for (int i=0; i < PBest.length;i++){
            for (int j=0; j < PBest[0].length; j++){
                double temp = kecepatan_partikel[i][j];
                kecepatan_partikel[i][j] = 0.0;
                kecepatan_partikel[i][j] = 
                (W * temp)+((c1*r1)*(PBest[i][j]-posisi_partikel[i][j]))+((c2*r2)*(GBest[j]-posisi_partikel[i][j])); 
                //batas kecepatan partikel diantara -0.1 sampai dengan 0.1, sehingga harus diset agar tidak melewati batas
                if (kecepatan_partikel[i][j] > 0.1){
                    kecepatan_partikel[i][j] = 0.1;
                } else 
                    if (kecepatan_partikel[i][j] < -0.1){
                        kecepatan_partikel[i][j] = -0.1;
                    } else {
                        kecepatan_partikel[i][j] = kecepatan_partikel[i][j];
                    } 
            }   
        }
        
    }
    
    // Method update posisi partikel
    public void updatePosisi (double kecepatan_partikel[][]){
        //Update posisi
        for (int i=0; i < kecepatan_partikel.length; i++){
            for (int j=0; j < kecepatan_partikel[0].length; j++){
                double temp = posisi_partikel[i][j];
                this.posisi_partikel[i][j] = 0.0;
                this.posisi_partikel[i][j] = temp + kecepatan_partikel[i][j];
                //batas posisi minimum -1 maksimum 1, sehingga harus diset agar tidak melewati batas  
                if (posisi_partikel[i][j] > 1.0 ){
                    posisi_partikel[i][j] = 1.0;
                } else
                    if (posisi_partikel[i][j] < -1.0){
                        posisi_partikel[i][j] = -1.0;
                    } else {
                        posisi_partikel[i][j] = posisi_partikel[i][j];
                    }
            }
        }
    }
    
    // Method untuk update PBest
    public void updatePBest(double posisi_partikel[][], double fitness[], double Pbest[][], double fitness_Pbest[]){
         for (int i=0; i < Pbest.length; i++){
             if (fitness_Pbest[i] >= fitness[i]){
                this.fitness_PBest [i]= Double.valueOf(df.format(fitness_Pbest[i])); 
                 for (int j=0; j < Pbest[0].length;j++){
                  this.PBest[i][j] = Pbest[i][j];
                 }
             } else 
                 if (fitness[i] >= fitness_Pbest[i]){
                 this.fitness_PBest[i] = Double.valueOf(df.format(fitness[i]));
                 for (int j=0; j < Pbest[0].length; j++){
                     this.PBest[i][j] = posisi_partikel[i][j];
                 }
             }
        }
    }
    
    // Method update PBest -> Prosesnya sama seperti inisialisasi PBest
    public void updateGBest(double PBest[][], double fitness_Pbest[]){
        int idx_minimum = 0;  
        for (int i=0; i < fitness_Pbest.length; i++){
            if (fitness_Pbest[i] >= fitness_GBest){
                fitness_GBest = Double.valueOf(df.format(fitness_Pbest[i]));
                idx_minimum = i;
            }
        }
        
        for (int j=0; j < PBest[0].length; j++){
            GBest [j] = PBest[idx_minimum][j];
        }
    }
    
}
