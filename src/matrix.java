import java.util.Scanner;

public class matrix{
	double[][] mat= new double[1000][1000];
	int n; //matrix is n*m
	int m;
	Scanner in= new Scanner(System.in);
	
	//constructor
	matrix(){
		for(int i=0;i<1000;i++)for(int j=0;j<1000;j++)mat[i][j]=0;
	}
	
	//* READING MATRIX *//
	public void read(){
		System.out.printf("Masukan Matriks:\n1. Keyboard\n2. File\nPilihan:");
		int choice=this.in.nextInt();
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
		this.m=this.in.nextInt();
		in.nextLine(); //same
		System.out.printf("Masukkan matriks:\n");
		for(int i=0;i<this.n;i++)for(int j=0;j<this.m;j++){
			this.mat[i][j]=this.in.nextFloat();
			in.nextLine(); //same
		}
		//this.display();	
	}
	
	//not done
	void readFile(){
		System.out.printf("Masukkan nama atau path file:\n");
		String file=this.in.nextLine();
		try{
			Scanner filein = new Scanner(new FileReader(file));
			int i=0;
			while(filein.hasNextLine()){
				// i have no idea how to read this, what the fuck!!!!
				String s=filein.nextLine();
			}
			filein.close();
			this.display();
		}catch(FileNotFoundException ex){
			System.out.printf("File not found\n"); //loops back to drvier
		}
	}
}
