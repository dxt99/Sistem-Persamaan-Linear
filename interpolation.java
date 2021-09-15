import java.util.Scanner;
import java.io.*;

public class interpolation{
	double[] x= new double[1000];
	double[] y= new double[1000];
	int n = 0; //interpolation degree
	Scanner in = new Scanner(System.in);
	
	//constructor
	interpolation(){
		for(int i=0;i<1000;i++){
			x[i]=0;
			y[i]=0;
		}
	}
	//run this from driver
	public void read(){
		System.out.printf("Masukan Interpolasi:\n1. Keyboard\n2. File\nPilihan:");
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
		System.out.printf("Masukkan n (banyak titik n+1): ");
		this.n=this.in.nextInt();
		in.nextLine() //eats newline
		for(int i=0;i<=this.n;i++){
			this.x[i]=in.nextFloat();
			in.nextLine(); //same
			this.y[i]=in.nextFloat();
			in.nextLine(); //same
		}
		//this.display();
	}
	
	void readFile(){
		System.out.printf("Masukkan nama atau path file:\n");
		String file=this.in.nextLine();
		try{
			Scanner filein = new Scanner(new FileReader(file));
			int i=0;
			while(filein.hasNextFloat()){
				x[i]=filein.nextFloat();
				y[i]=filein.nextFloat();
				i++;
			}
			this.n=i-1; //i overflow
			filein.close();
			this.display();
		}catch(FileNotFoundException ex){
			System.out.printf("File not found\n"); //loops back to drvier
		}
	}
			
			
	
	//for testing only, delete later
	void display(){
		for(int i=0;i<=this.n;i++){
			System.out.printf("(%f,%f)\n",x[i],y[i]);
		}
	}
	
	void count(){ //waiting for matrix class
	}
}