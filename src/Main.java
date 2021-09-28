import java.util.Scanner;

public class Main{

    public static void main (String[] args){

        matrix m1 = new matrix();
        matrix mh = new matrix();
        interpolation i1 = new interpolation();
        int pilihan = 0;
        int metode;

        Scanner in = new Scanner(System.in);

        while (pilihan!=6) {

            System.out.println("MENU");
            System.out.println("1. Sistem Persamaan Linier");
            System.out.println("2. Determinan");
            System.out.println("3. Matriks Balikan");
            System.out.println("4. Interpolasi Polinom");
            System.out.println("5. Regresi Linier Berganda");
            System.out.println("6. Keluar");
            System.out.print("Silahkan pilih permasalahan: ");
            pilihan = in.nextInt();
            while (pilihan < 1 || pilihan > 6) {
                System.out.print("Pilihan tidak tersedia. Masukkan angka antara 1-6: ");
                pilihan = in.nextInt();
            }
//========================SPL=========================================
            if (pilihan == 1) {
                System.out.println("Daftar Metode");
                System.out.println("1. Metode eliminasi Gauss");
                System.out.println("2. Metode eliminasi Gauss-Jordan");
                System.out.println("3. Metode matriks balikan");
                System.out.println("4. Metode Cramer");
                System.out.print("Silahkan pilih metode penyelesaian: ");
                metode = in.nextInt();
                while (metode < 1 || metode > 4) {
                    System.out.print("Pilihan tidak tersedia. Masukkan angka antara 1-2: ");
                    metode = in.nextInt();
                }
                m1.read(1);
                if (metode == 1) {
                    m1.gauss();
                    if (!(m1.n == 0 && m1.m == 0)){
                        m1.outPers();
                    }
                    m1.outPers();
                } else if (metode == 2) {
                    m1.gaussJordan();
                    if (!(m1.n == 0 && m1.m == 0)){
                        m1.outPers();
                    }
                } else if (metode == 3) {
                    // processing
                } else if (metode == 4) {
                    // processing
                }
//========================Determinan=========================================
            } else if (pilihan == 2) {
                System.out.println("Daftar Metode");
                System.out.println("1. Metode Operasi Baris Elementer");
                System.out.println("2. Metode Kofaktor");
                System.out.print("Silahkan pilih metode penyelesaian: ");
                metode = in.nextInt();
                while (metode < 1 || metode > 2) {
                    System.out.print("Pilihan tidak tersedia. Masukkan angka antara 1-2: ");
                    metode = in.nextInt();
                }
                m1.read(0);
                if ((metode == 1) && (!(m1.n == 0 && m1.m == 0))){
                    m1.outFloat(m1.determinanOBE());
                } else if ((metode == 2) && (!(m1.n == 0 && m1.m == 0))){
                    m1.outFloat(m1.determinanKofaktor());
                }
//========================INVERS=========================================
            } else if (pilihan == 3) {
                System.out.println("Daftar Metode");
                System.out.println("1. Metode Gauss-Jordan");
                System.out.println("2. Metode Kofaktor");
                System.out.print("Silahkan pilih metode penyelesaian: ");
                metode = in.nextInt();
                while (metode < 1 || metode > 2) {
                    System.out.print("Pilihan tidak tersedia. Masukkan angka antara 1-2: ");
                    metode = in.nextInt();
                }
                m1.read(0);
                if (metode == 1){
                    m1.inversOBE();
                    if ((!(m1.n == 0 && m1.m == 0))) {
                        m1.outMat();
                    }
                } else if (metode == 2){
                    m1.inversKofaktor();
                    if (!(m1.n == 0 && m1.m == 0)) {
                        m1.outMat();
                    }
                }
//========================INTERPOLASI=========================================
            } else if (pilihan == 4){
                i1.read();
                i1.count();
                i1.out();
//========================REGRESI=========================================
            } else if (pilihan == 5){
                // process
            }
        }
    }

}