import java.util.Scanner;
import java.io.*;

public class matrix{
	double[][] mat= new double[1000][1000];
	int n; //matrix is n*m
	int m; //m should be n+1 (spek tubes)
	Scanner in= new Scanner(System.in);
	
	//constructor
	matrix(){
		for(int i=0;i<1000;i++)for(int j=0;j<1000;j++)mat[i][j]=0;
	}
	
	//* READING MATRIX *//
	public void read(){
		System.out.printf("Masukan Matriks:\n1. Keyboard\n2. File\nPilihan:");
		int choice=this.in.nextInt();
		in.nextLine(); //eats newline
		if(choice==1)this.readKey();
		else if(choice==2)this.readFile(); //temporary
		else{
			System.out.printf("Masukan tidak valid\n");
			this.read();
		}
	}
	
	void readKey(){
		System.out.printf("Masukkan banyak baris: ");
		this.n=this.in.nextInt();
		in.nextLine(); //eats newline
		System.out.printf("Masukkan banyak kolom: ");
		this.m=this.in.nextInt(); //m should be n+1
		in.nextLine(); //same
		System.out.printf("Masukkan matriks:\n");
		for(int i=0;i<this.n;i++)for(int j=0;j<this.m;j++){
			this.mat[i][j]=this.in.nextFloat();
			in.nextLine(); //same
		}
	}
	
	void readFile(){
		System.out.printf("Masukkan nama atau path file:\n");
		String file=this.in.nextLine();
		try{
			Scanner filein = new Scanner(new FileReader(file));
			int i=0;
			while(filein.hasNextLine()){
				String s=filein.nextLine();
				String[] temp=s.split(" ",0);
				for(int j=0;j<temp.length;j++)this.mat[i][j]=Double.parseDouble(temp[j]);
				i++;
			}
			this.n=i;
			this.m=i+1;
			filein.close();
		}catch(FileNotFoundException ex){
			System.out.printf("File not found\n"); //loops back to drvier
		}
	}
	
	//testing only
	public static void display(){
		for(int i=0;i<n;i++){
			for(int j=0;j<m;j++){
				System.out.printf("%f ",this.mat[i][j]);
			}
			System.out.println();
		}
	}
	
	//*OPERATIONS*//
	//swaps i=baris1 and i=baris2 in this.mat[i][]
	void tukarBaris(int baris1, int baris2){ //using 0 based index
		double temp;
		for(int i=0;i<this.m;i++){
			temp=this.mat[baris1][i];
			this.mat[baris1][i]=this.mat[baris2][i];
			this.mat[baris2][i]=temp;
		}
	}
	
	//swaps j=kolom1 and j=kolom2 in this.[][j], for cramer
	void tukarKolom(int kolom1, int kolom2){
		double temp;
		for(int i=0;i<this.n;i++){
			temp=this.mat[i][kolom1];
			this.mat[i][kolom1]=this.mat[i][kolom2];
			this.mat[i][kolom2]=temp;
		}
	}
	
	//mat[baris][]*=x
	void kaliX(int baris, double x){
		for(int i=0;i<this.m;i++){
			this.mat[baris][i]*=x;
		}
	}
	
	//baris1+=baris2*x
	void tambahBaris(int baris1,int baris2, double x){
		for(int i=0;i<this.m;i++){
			this.mat[baris1][i]+=this.mat[baris2][i]*x;
		}
	}
	
}