import java.util.Scanner;

public class Main{

    public static void main (String[] args){

        matrix m1 = new matrix();
        interpolation i1 = new interpolation();
        regression r1 = new regression();
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
                    if (!(m1.n == 0 || m1.m == 0)){
                        m1.outPers();
                    } else {
                        System.out.println("Matrix tidak dapat diproses.");
                    }
                } else if (metode == 2) {
                    m1.gaussJordan();
                    if (!(m1.n == 0 || m1.m == 0)){
                        m1.outPers();
                    } else {
                        System.out.println("Matrix tidak dapat diproses.");
                    }
                } else if (metode == 3) {
                    if ((!(m1.n == 0 || m1.m == 0)) && (m1.n==m1.m-1)){
                        m1.splInvers(m1).outCol();
                    } else {
                        System.out.println("Matrix tidak dapat diproses.");
                    }
                } else if (metode == 4) {
                    if ((!(m1.n == 0 || m1.m == 0)) && (m1.n==m1.m-1)){
                        m1.splCramer(m1).outCol();
                    } else {
                        System.out.println("Matrix tidak dapat diproses.");
                    }
                }

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
                if ((metode == 1) && (!(m1.n == 0 || m1.m == 0))){
                    m1.outFloat(m1.determinanOBE());
                } else if ((metode == 2) && (!(m1.n == 0 || m1.m == 0))){
                    m1.outFloat(m1.determinanKofaktor());
                } else {
                    System.out.println("Matrix tidak dapat diproses.");
                }

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
                    if ((!(m1.n == 0 || m1.m == 0)) && m1.determinanKofaktor()!=0) {
                        m1.outMat();
                    } else if (m1.determinanKofaktor()==0){
                        System.out.println("Matriks tidak memiliki invers");
                    } else {
                        System.out.println("Matrix tidak dapat diproses.");
                    }
                } else if (metode == 2){
                    m1.inversKofaktor();
                    if (!(m1.n == 0 || m1.m == 0) && m1.determinanKofaktor()!=0) {
                        m1.outMat();
                    } else if (m1.determinanKofaktor()==0){
                        System.out.println("Matriks tidak memiliki invers");
                    } else {
                        System.out.println("Matrix tidak dapat diproses.");
                    }
                }

            } else if (pilihan == 4){
                i1.read();
                i1.out();

            } else if (pilihan == 5){
                r1.read();
                r1.out();
            }
            // Reset Matrix
            m1.n = 0;
            m1.m = 0;
        }
    }

}