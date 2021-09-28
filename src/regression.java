import java.util.Scanner;
import java.util.Formatter;
import java.io.*;

public class regression{
	double[][] x = new double[1000][1000];
	double[] xtar = new double[1000];
	double[] y = new double[1000];
	int n=0; //variables
	int s=0; //samples
	Scanner in = new Scanner(System.in);
	
	//constructor
	regression(){
		for(int i=0;i<1000;i++){
			y[i]=0;
			xtar[i]=0;
			for(int j=0;j<1000;j++){
				x[i][j]=1; //multiplication identity
			}
		}
	}
	
	//Functions to call: read(), out()
	public void read(){
		System.out.printf("Masukan Regresi:\n1. Keyboard\n2. File\nPilihan: ");
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
		System.out.printf("Masukkan n (jumlah peubah x): ");
		this.n=this.in.nextInt();
		in.nextLine(); //eats newline
		System.out.printf("Masukkan banyak sampel: ");
		this.s=this.in.nextInt();
		in.nextLine();
		System.out.printf("Masukkan x1i,...,xni,yi:\n");
		for(int i=1;i<=this.s;i++){
			for(int j=1;j<=this.n;j++){
				this.x[j][i]=this.in.nextFloat();
			}
			this.y[i]=this.in.nextFloat();
		}
		System.out.printf("Masukkan xk (n buah):\n");
		for(int i=1;i<=this.n;i++)this.xtar[i]=this.in.nextFloat();
		in.nextLine(); //same
	}
	
	void readFile(){
		System.out.printf("Masukkan nama atau path file:\n");
		String file=this.in.nextLine();
		try{
			Scanner filein = new Scanner(new FileReader(file));
			int i=1;
			while (filein.hasNextLine()) {
				String s = filein.nextLine();
				String[] temp = s.split(" ", 0);
				if(i==1)this.n=temp.length-1;
				for (int j=1; j<=this.n; j++) this.x[j][i] = Double.parseDouble(temp[j-1]);
				if(this.n==temp.length){
					for(int j=1;j<=this.n;j++)this.xtar[j]=this.x[j][i];
					break;
				}
				this.y[i]=Double.parseDouble(temp[this.n]);
				i++;
			}
			this.s=i-1;
			filein.close();
		}catch(FileNotFoundException ex){
			System.out.printf("File not found\n"); //loops back to drvier
		}
	}
	
	matrix count(){
		matrix m=new matrix();
		m.n=this.n+1;
		m.m=this.n+2;
		for(int i=0;i<m.n;i++){
			for(int j=0;j<m.m-1;j++){
				m.mat[i][j]=0;
				for(int k=1;k<=this.s;k++){
					m.mat[i][j]+=(this.x[i][k]*this.x[j][k]);
				}
			}
			m.mat[i][m.m-1]=0;
			for(int k=1;k<=this.s;k++)m.mat[i][m.m-1]+=(this.x[i][k]*this.y[k]);
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
		m.gaussJordan();
		int z=m.n-1;
		for (;z>=0;z--){
			int j=0;
			while(m.mat[z][j]<0.00001&&j<m.m)j++;
			
			//handling baris 0
			if(j==m.m-1&&m.mat[z][j]!=0){
				System.out.println("Tidak ada solusi");
				return;
			} else if(j==m.m-1&&m.mat[z][j]==0){
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
			while(m.mat[i][j]<0.00001&&j<m.m)j++;
			//All other cases
			for(j++;j<m.m-1;j++){
				if(m.mat[i][j]!=0)yes=true;
			}
		}
		if(yes){
			System.out.println("Tidak ada solusi\n");
		}else{
			System.out.printf("f(x) = ");
			for(int i=0;i<=z;i++){
				int j=0;
				while(m.mat[i][j]<0.00001&&j<m.m){
					j++;
				}
				if(i==0&&j==0)System.out.printf("%f ",m.mat[i][m.m-1]);
				else if(i==0)System.out.printf("%fx_%d ",m.mat[i][m.m-1],j);
				else if(m.mat[i][m.m-1]>0)System.out.printf("+ %fx_%d ",m.mat[i][m.m-1],j);
				else if(m.mat[i][m.m-1]<0)System.out.printf("- %fx_%d ",-1*m.mat[i][m.m-1],j);
				ans+=(this.xtar[j]*m.mat[i][m.m-1]);
			}
			System.out.printf("\nNilai taksiran adalah: %f\n",ans);
		}
	}
	
	void outFile(){
		matrix m=count();
		m.gaussJordan();
		int z=m.n-1;
		System.out.printf("Masukkan nama atau path file:\n");
		String file = this.in.nextLine();
		try{
			Formatter fileout = new Formatter(file);
				for (;z>=0;z--){
				int j=0;
				while(m.mat[z][j]<0.00001&&j<m.m)j++;
				
				//handling baris 0
				if(j==m.m-1&&m.mat[z][j]!=0){
					System.out.printf("Tidak ada solusi\n");
					return;
				} else if(j==m.m-1&&m.mat[z][j]==0){
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
				while(m.mat[i][j]<0.00001&&j<m.m)j++;
				//All other cases
				for(j++;j<m.m-1;j++){
					if(m.mat[i][j]!=0)yes=true;
				}
			}
			if(yes){
				System.out.printf("Tidak ada solusi\n");
			}else{
				fileout.format("f(x) = ");
				for(int i=0;i<=z;i++){
					int j=0;
					while(m.mat[i][j]<0.00001&&j<m.m){
						j++;
					}
					if(i==0&&j==0)fileout.format("%f ",m.mat[i][m.m-1]);
					else if(i==0)fileout.format("%fx_%d ",m.mat[i][m.m-1],j);
					else if(m.mat[i][m.m-1]>0)fileout.format("+ %fx_%d ",m.mat[i][m.m-1],j);
					else if(m.mat[i][m.m-1]<0)fileout.format("- %fx_%d ",-1*m.mat[i][m.m-1],j);
					ans+=(this.xtar[j]*m.mat[i][m.m-1]);
				}
				fileout.format("\nNilai taksiran adalah: %f\n",ans);
			}
			fileout.close();
		} catch (FileNotFoundException ex) {
			System.out.printf("File not found\n"); //loops back to drvier
		}
	}
}