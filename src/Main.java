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
    }

}