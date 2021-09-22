import java.util.Scanner;


public class Main{

    public static void main(String[] args){
        MenuUtama();
    }

    public static void MenuUtama(){
        Scanner in = new Scanner(System.in);
        System.out.println("MENU");
        System.out.println("1. Sistem Persamaan Linier");
        System.out.println("2. Determinan");
        System.out.println("3. Matriks Balikan");
        System.out.println("4. Interpolasi Polinom");
        System.out.println("5. Regresi Linier Berganda");
        System.out.println("6. Keluar");
        System.out.print("Silahkan pilih permasalahan: ");
        int pilihan = in.nextInt();
        while (pilihan<1 || pilihan>6){
            System.out.print("Pilihan tidak tersedia. Masukkan angka antara 1-6: ");
            pilihan = in.nextInt();
        }
        int metode;
        if (pilihan==1) {
            System.out.println("Daftar Metode");
            System.out.println("1. Metode Eliminasi Gauss");
            System.out.println("2. Metode Eliminasi Gauss-Jordan");
            System.out.print("Silahkan pilih metode penyelesaian: ");
            metode = in.nextInt();
            while (metode < 1 || metode > 2) {
                System.out.print("Pilihan tidak tersedia. Masukkan angka antara 1-2: ");
                metode = in.nextInt();
            }
        }else if(pilihan==2) {
            System.out.println("Daftar Metode");
            System.out.println("1. Metode Operasi Baris Elementer");
            System.out.println("2. Metode Kofaktor");
            System.out.print("Silahkan pilih metode penyelesaian: ");
            metode = in.nextInt();
            while (metode < 1 || metode > 2) {
                System.out.print("Pilihan tidak tersedia. Masukkan angka antara 1-2: ");
                metode = in.nextInt();
            }
        }else if(pilihan==3) {
            System.out.println("Daftar Metode");
            System.out.println("1. Metode Gauss-Jordan");
            System.out.println("2. Metode Kofaktor");
            System.out.print("Silahkan pilih metode penyelesaian: ");
            metode = in.nextInt();
            while (metode < 1 || metode > 2) {
                System.out.print("Pilihan tidak tersedia. Masukkan angka antara 1-2: ");
                metode = in.nextInt();
            }
        }
    }

}