import java.util.Scanner;
import java.util.Formatter;
import java.io.*;

public class interpolation{
	double[] x= new double[1000];
	double[] y= new double[1000];
	double xtar;
	int n = 0; //interpolation degree
	Scanner in = new Scanner(System.in);
	
	//constructor
	interpolation(){
		for(int i=0;i<1000;i++){
			x[i]=0;
			y[i]=0;
		}
	}
	
	//Functions to call: read(), out()
	public void read(){
		System.out.printf("Masukan Interpolasi:\n1. Keyboard\n2. File\nPilihan: ");
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
		in.nextLine(); //eats newline
		for(int i=0;i<=this.n;i++){
			this.x[i]=in.nextFloat();
			this.y[i]=in.nextFloat();
		}
		System.out.printf("Masukkan x yang ingin ditaksir: ");
		this.xtar=in.nextFloat();
		in.nextLine(); //same
		//this.display();
	}
	
	void readFile(){
		System.out.printf("Masukkan nama file:\n");
		String file=this.in.nextLine();
		file="../test/input/"+file;
		try{
			Scanner filein = new Scanner(new FileReader(file));
			int i=0;
			while(filein.hasNextFloat()){
				this.x[i]=filein.nextFloat();
				if(!filein.hasNextFloat()){
					this.xtar=this.x[i];
					break;
				}
				this.y[i]=filein.nextFloat();
				i++;
			}
			this.n=i-1; //i overflow
			filein.close();
			//this.display();
		}catch(FileNotFoundException ex){
			System.out.printf("File not found\n"); //loops back to drvier
		}
	}
	
	matrix count(){
		matrix m= new matrix();
		m.n=this.n+1;
		m.m=this.n+2;
		for(int i=0;i<=this.n;i++){
			double var=1;
			for(int j=0;j<=this.n;j++){
				m.mat[i][j]=var;
				var*=this.x[i];
			}
			m.mat[i][this.n+1]=y[i];
		}
		m.gaussJordan();
		return m;
	}
	
	public void out(){
		System.out.printf("Keluaran Matriks:\n1. Keyboard\n2. File\nPilihan: ");
		int choice = this.in.nextInt();
		in.nextLine(); //eats newline
		if (choice == 1) this.outKey();
		else if (choice == 2) this.outFile(); 
		else {
			System.out.printf("Masukan tidak valid\n");
			this.out();
		}
	}
	
	void outKey(){
		matrix m=count();
		int z=m.n-1;
		for (;z>=0;z--){
			int j=0;
			while(abs(m.mat[z][j])<0.000001&&j<m.m)j++;
			
			//handling baris 0
			if(j==m.m-1&&abs(m.mat[z][j])>0.000001){
				System.out.println("Tidak ada solusi");
				return;
			} else if(j==m.m-1&&abs(m.mat[z][j])<0.000001){
				continue;
			}
			if(j<m.m-1)break;
		}
		if(z==-1){
			System.out.printf("Tidak ada solusi\n");
			return;
		}
		double ans=0;
		boolean yes=false;
		for(int i=0;i<=z;i++){
			int j=0;
			while(abs(m.mat[i][j])<0.000001&&j<m.m)j++;
			//All other cases
			for(j++;j<m.m-1;j++){
				if(abs(m.mat[i][j])<0.000001)yes=true;
			}
		}
		if(yes){
			System.out.println("Tidak ada solusi\n");
		}else{
			System.out.printf("p_%d(x) = ",this.n);
			for(int i=0;i<=z;i++){
				int j=0;
				double var=1;
				while(abs(m.mat[i][j])<0.00001&&j<m.m){
					j++;
					var*=this.xtar;
				}
				if(i==0&&j==0)System.out.printf("%f ",m.mat[i][m.m-1]);
				else if(i==0&&j==1)System.out.printf("%fx ",m.mat[i][m.m-1]);
				else if(i==0)System.out.printf("%fx^%d ",m.mat[i][m.m-1],j);
				else if(m.mat[i][m.m-1]>0.000001&&j==1)System.out.printf("+ %fx ",m.mat[i][m.m-1]);
				else if(m.mat[i][m.m-1]<-0.000001&&j==1)System.out.printf("- %fx ",-1*m.mat[i][m.m-1]);
				else if(m.mat[i][m.m-1]>0.000001)System.out.printf("+ %fx^%d ",m.mat[i][m.m-1],j);
				else if(m.mat[i][m.m-1]<-0.000001)System.out.printf("- %fx^%d ",-1*m.mat[i][m.m-1],j);
				ans+=(var*m.mat[i][m.m-1]);
			}
			System.out.printf("\nNilai taksiran %f adalah: %f\n",this.xtar,ans);
		}
	}
	
	void outFile(){
		matrix m=count();
		int z=m.n-1;
		System.out.printf("Masukkan nama file:\n");
		String file = this.in.nextLine();
		file="../test/output/"+file;
		try{
			Formatter fileout = new Formatter(file);
				for (;z>=0;z--){
				int j=0;
				while(abs(m.mat[z][j]<0.00001)&&j<m.m)j++;
				
				//handling baris 0
				if(j==m.m-1&&abs(m.mat[z][j])>0.000001){
					System.out.printf("Tidak ada solusi\n");
					return;
				} else if(j==m.m-1&&abs(m.mat[z][j])<0.000001){
					continue;
				}
				if(j<m.m-1)break;
			}
			if(z==-1){
				System.out.printf("Tidak ada solusi\n");
				return;
			}
			double ans=0;
			boolean yes=false;
			for(int i=0;i<=z;i++){
				int j=0;
				while(abs(m.mat[i][j])<0.000001&&j<m.m)j++;
				//All other cases
				for(j++;j<m.m-1;j++){
					if(abs(m.mat[i][j])>0.000001)yes=true;
				}
			}
			if(yes){
				System.out.printf("Tidak ada solusi\n");
			}else{
				fileout.format("p_%d(x) = ",this.n);
				for(int i=0;i<=z;i++){
					int j=0;
					double var=1;
					while(abs(m.mat[i][j])<0.000001&&j<m.m){
						j++;
						var*=this.xtar;
					}
					if(i==0&&j==0)fileout.format("%f ",m.mat[i][m.m-1]);
					else if(i==0&&j==1)fileout.format("%fx ",m.mat[i][m.m-1]);
					else if(i==0)fileout.format("%fx^%d ",m.mat[i][m.m-1],j);
					else if(m.mat[i][m.m-1]>0.000001&&j==1)fileout.format("+ %fx ",m.mat[i][m.m-1]);
					else if(m.mat[i][m.m-1]<-0.000001&&j==1)fileout.format("- %fx ",-1*m.mat[i][m.m-1]);
					else if(m.mat[i][m.m-1]>0.000001)fileout.format("+ %fx^%d ",m.mat[i][m.m-1],j);
					else if(m.mat[i][m.m-1]<-0.000001)fileout.format("- %fx^%d ",-1*m.mat[i][m.m-1],j);
					ans+=(var*m.mat[i][m.m-1]);
				}
				fileout.format("\nNilai taksiran %f adalah: %f\n",this.xtar,ans);
			}
			fileout.close();
		} catch (FileNotFoundException ex) {
			System.out.printf("File not found\n"); //loops back to drvier
		}
	}
}
