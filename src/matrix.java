import java.util.Scanner;
import java.util.Formatter;
import java.io.*;
import java.lang.Math;

public class matrix {
	double[][] mat = new double[1000][1000];
	int n; //matrix is n*m
	int m; //m should be n+1 (spek tubes)
	Scanner in = new Scanner(System.in);

	//constructor
	matrix() {
		for (int i = 0; i < 1000; i++) for (int j = 0; j < 1000; j++) mat[i][j] = 0;
	}

	//* I/O MATRIX *//
	//Functions to call: read(), outFloat(), outPers(), outMat();
	//outFloat untuk determinan, outPers untuk SPL, outMat untuk invers;
	
	//if augmented, k=1 (SPL), else if det/inv, k=0;
	public void read(int k){ 
		System.out.printf("Masukan Matriks:\n1. Keyboard\n2. File\nPilihan: ");
		int choice = this.in.nextInt();
		in.nextLine(); //eats newline
		if (choice == 1) this.readKey(k);
		else if (choice == 2) this.readFile(k); 
		else {
			System.out.printf("Masukan tidak valid\n");
			this.read(k);
		}
	}

	void readKey(int k) {
		if(k==1){
			System.out.printf("Masukkan banyak baris: ");
			this.n = this.in.nextInt();
			in.nextLine(); //eats newline
			System.out.printf("Masukkan banyak kolom: ");
			this.m = this.in.nextInt(); //m should be n+1
			in.nextLine(); //same
		}else{
			System.out.printf("Masukkan banyak baris/kolom: ");
			this.n = this.in.nextInt();
			in.nextLine(); //same
			this.m=this.n;
		}
		if (this.m==0 || this.n==0){
			System.out.println("Matrix masukan tidak valid.");
		} else {
			System.out.printf("Masukkan matriks:\n");
			for (int i = 0; i < this.n; i++)
				for (int j = 0; j < this.m; j++) {
					this.mat[i][j] = this.in.nextFloat();
				}
			in.nextLine();//eats newline
		}
	}

	void readFile(int k) {
		System.out.printf("Masukkan nama file:\n");
		String file = this.in.nextLine();
		try {
			File f = new File(file);
			if(!f.exists())file="../test/input/"+file;
			Scanner filein = new Scanner(new FileReader(file));
			int i = 0;
			while (filein.hasNextLine()) {
				String s = filein.nextLine();
				String[] temp = s.split(" ", 0);
				for (int j = 0; j < temp.length; j++) this.mat[i][j] = Double.parseDouble(temp[j]);
				i++;
				this.m=temp.length;
			}
			this.n = i;
			filein.close();
		} catch (FileNotFoundException ex) {
			System.out.printf("File not found\n"); //loops back to drvier
		}
	}
	
	//basically just for determinants
	public void outFloat(double n){
		System.out.printf("Keluaran Matriks:\n1. Keyboard\n2. File\nPilihan: ");
		int choice = this.in.nextInt();
		in.nextLine(); //eats newline
		if (choice == 1) this.outFloatKey(n);
		else if (choice == 2) this.outFloatFile(n); 
		else {
			System.out.printf("Masukan tidak valid\n");
			this.outFloat(n);
		}
	}
		
	
	void outFloatKey(double n){
		System.out.printf("%f\n",n);
	}
	
	void outFloatFile(double n){
		System.out.printf("Masukkan nama file:\n");
		String file = this.in.nextLine();
		file="../test/output/"+file;
		try {
			Formatter fileout = new Formatter(file);
			fileout.format("%f\n",n);
			fileout.close();
		} catch (FileNotFoundException ex) {
			System.out.printf("File not found\n"); //loops back to drvier
		}
	}
	
	public void outPers(){
		System.out.printf("Keluaran Matriks:\n1. Keyboard\n2. File\nPilihan: ");
		int choice = this.in.nextInt();
		in.nextLine(); //eats newline
		if (choice == 1) this.outPersKey();
		else if (choice == 2) this.outPersFile(); 
		else {
			System.out.printf("Masukan tidak valid\n");
			this.outPers();
		}
	}
	
	void outPersKey(){
		gaussJordan();
		int z=this.n-1;
		boolean[] done=new boolean[this.m];
		for(int i=0;i<this.m;i++)done[i]=false;
		for (;z>=0;z--){
			int j=0;
			while(this.mat[z][j]<0.00001&&j<this.m)j++;
			
			//handling baris 0
			if(j==this.m-1&&Math.abs(this.mat[z][j])>0.000001){
				System.out.println("Tidak ada solusi");
				return;
			} else if(j==this.m-1&&Math.abs(this.mat[z][j])<0.000001){
				continue;
			}
			if(j<this.m-1)break;
		}
		if(z==-1)System.out.printf("Tidak ada solusi2\n");
		for(int i=0;i<=z;i++){
			int j=0;
			while(this.mat[i][j]<0.00001&&j<this.m)j++;
			//All other cases
			boolean yes=false;
			System.out.printf("x_%d = ",j+1);
			for(j++;j<this.m-1;j++){
				if(Math.abs(this.mat[i][j])>0.000001)yes=true;
				//FORMATIING 
				if(this.mat[i][j]<-0.000001)System.out.printf("- %f*s_%d ",-1*this.mat[i][j],j+1);
				if(this.mat[i][j]>0.000001)System.out.printf("+ %f*s_%d ",this.mat[i][j],j+1);
			}
			if(yes&&Math.abs(this.mat[i][this.m-1])<0.000001)System.out.println();
			else if(yes&&this.mat[i][this.m-1]>0.000001)System.out.printf("+ %f\n",this.mat[i][j]);
			else if(yes&&this.mat[i][this.m-1]<-0.000001)System.out.printf("- %f\n",-1*this.mat[i][j]);
			else if(Math.abs(this.mat[i][this.m-1])<0.000001)System.out.printf("0\n");
			else System.out.printf("%f\n",this.mat[i][j]);
			
			if(yes){
				j=0;
				while(Math.abs(this.mat[i][j])<0.000001&&j<this.m)j++;
				for(j++;j<this.m-1;j++){
					if(Math.abs(this.mat[i][j])>0.000001&&!done[j])System.out.printf("x_%d = s_%d\n",j+1,j+1);
					done[j]=true;
				}
			}
				
		}
	}
	
	void outPersFile(){
		System.out.printf("Masukkan nama file:\n");
		String file = this.in.nextLine();
		file="../test/output/"+file;
		try{
			Formatter fileout = new Formatter(file);
			gaussJordan();
			int z=this.n-1;
			boolean[] done= new boolean[this.m];
			for(int i=0;i<this.m;i++)done[i]=false;
			for (;z>=0;z--){
				int j=0;
				while(Math.abs(this.mat[z][j])<0.000001&&j<this.m)j++;
				
				//handling baris 0
				if(j==this.m-1&&Math.abs(this.mat[z][j])>0.000001){
					fileout.format("Tidak ada solusi\n");
					fileout.close();
					return;
				} else if(j==this.m-1&&Math.abs(this.mat[z][j])<0.000001){
					continue;
				}
				if(j<this.m-1)break;
			}
			if(z==-1)fileout.format("Tidak ada solusi\n");
			for(int i=0;i<=z;i++){
				int j=0;
				while(Math.abs(this.mat[i][j])<0.000001&&j<this.m)j++;
				//All other cases
				boolean yes=false;
				fileout.format("x_%d = ",j+1);
				for(j++;j<this.m-1;j++){
					if(Math.abs(this.mat[i][j])>0.000001)yes=true;
					//FORMATIING 
					if(this.mat[i][j]<-0.000001)fileout.format("- %f*s_%d ",-1*this.mat[i][j],j+1);
					if(this.mat[i][j]>0.000001)fileout.format("+ %f*s_%d ",this.mat[i][j],j+1);
				}
				if(yes&&Math.abs(this.mat[i][this.m-1])<0.000001)fileout.format("\n");
				else if(yes&&this.mat[i][this.m-1]>0.000001)fileout.format("+ %f\n",this.mat[i][j]);
				else if(yes&&this.mat[i][this.m-1]<-0.000001)fileout.format("- %f\n",-1*this.mat[i][j]);
				else if(Math.abs(this.mat[i][this.m-1])<0.000001)fileout.format("0\n");
				else fileout.format("%f\n",this.mat[i][j]);
				
				if(yes){
					j=0;
					while(Math.abs(this.mat[i][j])<0.00001&&j<this.m)j++;
					for(j++;j<this.m-1;j++){
						if(Math.abs(this.mat[i][j])>0.000001&&!done[j])fileout.format("x_%d = s_%d\n",j+1,j+1);
						done[j]=true;
					}
				}
			}
			fileout.close();
		} catch (FileNotFoundException ex) {
			System.out.printf("File not found\n"); //loops back to drvier
		}
	}
	
	public void outMat(){
		System.out.printf("Keluaran Matriks:\n1. Keyboard\n2. File\nPilihan: ");
		int choice = this.in.nextInt();
		in.nextLine(); //eats newline
		if (choice == 1) this.outMatKey();
		else if (choice == 2) this.outMatFile(); 
		else {
			System.out.printf("Masukan tidak valid\n");
			this.outMat();
		}
	}
	
	void outMatKey() {
		for (int i = 0; i < this.n; i++) {
			for (int j = 0; j < this.m; j++) {
				System.out.printf("%f ", this.mat[i][j]);
			}
			System.out.println();
		}
	}
	
	void outMatFile(){
		System.out.printf("Masukkan nama file:\n");
		String file = this.in.nextLine();
		file="../test/output/"+file;
		try{
			Formatter fileout = new Formatter(file);
			for (int i = 0; i < this.n; i++) {
				for (int j = 0; j < this.m; j++) {
					fileout.format("%f ", this.mat[i][j]);
				}
				fileout.format("\n");
			}
		} catch (FileNotFoundException ex) {
			System.out.printf("File not found\n"); //loops back to drvier
		}
	}
	
	public void outCol(){
		System.out.printf("Keluaran Matriks:\n1. Keyboard\n2. File\nPilihan: ");
		int choice = this.in.nextInt();
		in.nextLine(); //eats newline
		if (choice == 1) this.outColKey();
		else if (choice == 2) this.outColFile(); 
		else {
			System.out.printf("Masukan tidak valid\n");
			this.outFloat(n);
		}
	}
	
	void outColKey(){
		for(int i=0;i<this.n;i++)System.out.printf("x_%d=%f\n",i+1,this.mat[i][0]);
	}
	
	void outColFile(){
		System.out.printf("Masukkan nama file:\n");
		String file = this.in.nextLine();
		file="../test/output/"+file;
		try{
			Formatter fileout = new Formatter(file);
			for(int i=0;i<this.n;i++)fileout.format("x_%d=%f\n",i+1,this.mat[i][0]);
			
		} catch (FileNotFoundException ex) {
			System.out.printf("File not found\n"); //loops back to drvier
		}
	}

	//*OPERATIONS*//
	//swaps i=baris1 and i=baris2 in this.mat[i][]
	void tukarBaris(int baris1, int baris2) { //using 0 based index
		double temp;
		for (int i = 0; i < this.m; i++) {
			temp = this.mat[baris1][i];
			this.mat[baris1][i] = this.mat[baris2][i];
			this.mat[baris2][i] = temp;
		}
	}

	//swaps j=kolom1 and j=kolom2 in this.[][j], for cramer
	void tukarKolom(int kolom1, int kolom2) {
		double temp;
		for (int i = 0; i < this.n; i++) {
			temp = this.mat[i][kolom1];
			this.mat[i][kolom1] = this.mat[i][kolom2];
			this.mat[i][kolom2] = temp;
		}
	}

	//mat[baris][]*=x
	void kaliX(int baris, double x) {
		for (int i = 0; i < this.m; i++) {
			this.mat[baris][i] *= x;
		}
	}

	//baris1+=baris2*x
	void tambahBaris(int baris1, int baris2, double x) {
		for (int i = 0; i < this.m; i++) {
			this.mat[baris1][i] += this.mat[baris2][i] * x;
		}
	}

	int[] notZero(int baris, int kolom) {
		// mencari bukan 0
		int i = baris;
		int j = kolom;
		boolean found = false;
		while (j <= m - 1 && !found) {
			while (i <= n - 1 && !found) {
				if (this.mat[i][j] >= 0.000001 || this.mat[i][j] <=-0.000001) {
					found = true;
				} else {
					i += 1;
				}
			}
			if (!found) {
				i = baris;
			}
			j += 1;
		}
		if (!found) {
			i = -1;
			j = 0;
		}
		int[] arrIDX = {i, j - 1};
		return arrIDX;
	}

	public void gauss() {

		int bar = 0;
		int kol = 0;
		int[] idxNotZero;
		int i, j;

		while (bar <= this.n - 1 && kol <= this.m - 1) {
			idxNotZero = notZero(bar, kol);
			if (idxNotZero[0] == -1) {
				bar = this.n;
			} else {
				tukarBaris(bar, idxNotZero[0]);
				kol = idxNotZero[1]; //next kol
				for (i = bar + 1; i <= this.n - 1; i++) {
					if (this.mat[i][kol] != 0) {
						tambahBaris(i, bar, (-1 * this.mat[i][kol] / this.mat[bar][kol]));
					}
				}
				bar += 1; //next bar
			}
		}
		// Sudah mempunyai bilangan utama, tetapi belum mempunyai 1 utama

		// Menghasilkan 1 utama
		bar = 0;
		kol = 0;
		boolean done;

		while (bar <= this.n - 1) {
			done = false;
			while (kol <= this.m - 1 && !done) {
				if (this.mat[bar][kol] >= 0.000001||this.mat[bar][kol]<=-0.000001) {
					kaliX(bar, 1 / this.mat[bar][kol]);
					done = true;
				} else {
					kol += 1;
				}
			}
			bar += 1;
		}

		// fix -0
		for (i = 0; i <= n - 1; i++) {
			for (j = 0; j <= m - 1; j++) {
				if (this.mat[i][j] < 0.000001 && this.mat[i][j] > -0.000001) {
					this.mat[i][j] = 0;
				}
			}
		}

	}

	public void gaussJordan() {

		int bar = 0;
		int kol = 0;
		boolean done;
		int tempBar;

		gauss();

		while (bar <= this.n - 1) {
			done = false;
			tempBar = 0;
			while (kol <= this.m - 1 && !done) {
				if (this.mat[bar][kol] <= 1.000001 && this.mat[bar][kol] >= 0.999999) {
					while (tempBar <= this.n - 1) {
						if ((this.mat[tempBar][kol] >= 0.000001 || this.mat[tempBar][kol] <=-0.000001) && tempBar != bar) {
							tambahBaris(tempBar, bar, (-1 * this.mat[tempBar][kol] / this.mat[bar][kol]));
						}
						tempBar += 1;
					}
					if (tempBar == this.n) {
						done = true;
					}
				}
				kol += 1;
			}
			bar += 1;
		}

	}

	public void transpose() {
		double[][] temp = new double[this.n][this.m];
		for (int i = 0; i < this.n; i++)
			for (int j = 0; j < this.m; j++) {
				temp[i][j] = this.mat[i][j];
			}
		int simpan = this.n;
		this.n = this.m;
		this.m = simpan;
		for (int i = 0; i < this.n; i++)
			for (int j = 0; j < this.m; j++) {
				this.mat[i][j] = temp[j][i];
			}
	}

	public double determinanOBE() {
		int bar = 0;
		int kol = 0;
		int[] idxNotZero;
		int i, j, k, l;
		int reverse = 0;
		double hasil = 1;

		while (bar <= this.n - 1 && kol <= this.m - 1) {
			idxNotZero = notZero(bar, kol);
			if (idxNotZero[0] == -1) {
				bar = this.n;
			} else {
				if (bar!=idxNotZero[0]){
					reverse += 1;
				}
				tukarBaris(bar, idxNotZero[0]);
				kol = idxNotZero[1]; //next kol
				for (i = bar + 1; i <= this.n - 1; i++) {
					if (this.mat[i][kol] != 0) {
						tambahBaris(i, bar, (-1 * this.mat[i][kol] / this.mat[bar][kol]));
					}
				}
				bar += 1; //next bar
			}
		}
		for (k = 0; k < this.n; k++)
			for (l = 0; l < this.m; l++) {
				if (k == l) {
					hasil = hasil * this.mat[k][l]* Math.pow(-1, reverse);

				}
			}

		if (hasil < 0.000001 && hasil >-0.000001){
			hasil = 0;
		}
		
		return hasil;
	}

	public double determinanKofaktor(){

		int j, itemp, jtemp, ireal, jreal, bar, kol;
		matrix mtemp = new matrix();
		double temp;

		bar = this.n;
		kol = this.m;

		if (bar == 1 && kol == 1){
			temp = this.mat[0][0];
		} else if (bar == 2 && kol == 2){
			temp = (this.mat[0][0] * this.mat[1][1]) - (this.mat[0][1] * this.mat[1][0]);
		} else {
			temp = 0;
			for (j=0; j<=kol-1; j++){
				mtemp = inisialisasiM(bar-1, kol-1);
				mtemp.n = bar-1;
				mtemp.m = kol-1;
				ireal = 1;
				jreal = 0;
				itemp = 0;
				jtemp = 0;
				while (itemp<=bar-2){
					while (jtemp<=kol-2){
						if(jreal == j){
							jreal += 1;
						}
						mtemp.mat[itemp][jtemp] = this.mat[ireal][jreal];
						jtemp += 1;
						jreal += 1;
					}
					jtemp = 0;
					jreal = 0;
					itemp += 1;
					ireal += 1;
				}
				if (j%2==0){
					temp += this.mat[0][j]*mtemp.determinanKofaktor();
				} else {
					temp += (-1)*this.mat[0][j]*mtemp.determinanKofaktor();
				}
			}
		}
		return temp;
	}
	
	public void inversOBE() {

		matrix id = new matrix();
		int barId, kolId;
		int i, j, k, not0, idxNot0;
		double denom, x;
		boolean found;

		barId= this.n;
		kolId = this.m;
		id.n = this.n;
		id.m = this.m;

		// Identitas
		for (i=0; i<=barId-1; i++){
			for (j=0; j<=kolId-1; j++){
				if (i==j){
					id.mat[i][j] = 1;
				} else {
					id.mat[i][j] = 0;
				}
			}
		}

		// Fase Maju
		for (i=0; i<=barId-1; i++){
			if (this.mat[i][i]==0){
				found = false;
				idxNot0 = i;
				while ((idxNot0<=barId-1) && !found){
					if (this.mat[idxNot0][idxNot0] != 0){
						found = true;
					} else {
						idxNot0 += 1;
					}
				}
				tukarBaris(i, idxNot0);
				id.tukarBaris(i, idxNot0);
				id.tukarKolom(i, idxNot0);
			}
			denom = this.mat[i][i];
			for (j=0; j<=kolId; j++){
				this.mat[i][j] /= denom;
				id.mat[i][j] /= denom;
			}
			for (j=i+1; j<=barId-1; j++){
				if (this.mat[j][i] != 0){
					x = (-1*this.mat[j][i]/this.mat[i][i]);
					for (k=0; k<=kolId-1; k++){
						this.mat[j][k] += this.mat[i][k] * x;
						id.mat[j][k] += id.mat[i][k] * x;
					}
				}
			}
		}

		// Fase Mundur
		for (i=0; i<=barId-1; i++){
			for (j=0; j<=barId-1; j++){
				if (this.mat[j][i]!=0 && i!=j){
					x = (-1*this.mat[j][i]/this.mat[i][i]);
					for (k=0; k<=kolId-1; k++){
						this.mat[j][k] += this.mat[i][k] * x;
						id.mat[j][k] += id.mat[i][k] * x;
					}
				}
			}
		}

		// Copy id to this.mat
		for (i=0; i<=barId-1; i++) {
			for (j = 0; j <= kolId; j++) {
				this.mat[i][j] = id.mat[i][j];
				if (this.mat[i][j] == -0) {
					this.mat[i][j] = 0;
				}
			}
		}

	}

	// Inisialisasi Matriks
	matrix inisialisasiM(int nBar, int nKol){
		matrix m = new matrix();
		int i, j;
		for (i=0; i<=nBar-1; i++){
			for (j=0; j<=nKol-1; j++){
				m.mat[i][j] = 0;
			}
		}
		return m;
	}
	
	public void inversKofaktor(){

		matrix kofaktor = new matrix();
		matrix mtemp = new matrix();
		int i, j, ireal, jreal, itemp, jtemp, bar, kol;
		double det;
		int iz, jz;

		bar = this.n;
		kol = this.m;

		// Inisialisasi matriks kofaktor
		kofaktor = inisialisasiM(bar, kol);
		kofaktor.n = bar;
		kofaktor.m = kol;

		for (i=0; i<=bar-1; i++) {
			for (j = 0; j <= kol - 1; j++) {
				mtemp = inisialisasiM(bar - 1, kol - 1);
				mtemp.n = bar-1;
				mtemp.m = kol-1;
				ireal = 0;
				jreal = 0;
				itemp = 0;
				jtemp = 0;
				while (itemp <= bar - 2) {
					while (jtemp <= kol - 2) {
						if (ireal == i) {
							ireal += 1;
						}
						if (jreal == j) {
							jreal += 1;
						}
						mtemp.mat[itemp][jtemp] = this.mat[ireal][jreal];
						jtemp += 1;
						jreal += 1;
					}
					jtemp = 0;
					jreal = 0;
					itemp += 1;
					ireal += 1;
				}
				if ((i + j) % 2 == 0) {
					kofaktor.mat[i][j] = mtemp.determinanOBE();
				} else {
					kofaktor.mat[i][j] = (-1) * mtemp.determinanOBE();
				}
			}
		}
		kofaktor.transpose();
		det = this.determinanOBE();
		for (i=0; i<=bar-1; i++){
			for (j=0; j<=kol-1; j++){
				kofaktor.mat[i][j] /= det;
			}
		}

		for (i=0; i<=bar-1; i++){
			for (j=0; j<=kol-1; j++){
				this.mat[i][j] = kofaktor.mat[i][j];
			}
		}
	}

	public matrix kaliMatrix(matrix m1, matrix m2) {

		matrix m3 = new matrix();
		m3.n = m1.n;
		m3.m = m2.m;
		int i, j, k;
		
		for (i=0; i<=m3.n-1; i++){
			for (j=0; j<=m3.m-1; j++){
				m3.mat[i][j] = 0;
				for (k=0; k<=m1.m-1; k++){
					m3.mat[i][j] += m1.mat[i][k] * m2.mat[k][j];
				}
			}
		}
		return m3;

	}
	
	public matrix splInvers(matrix m1){
		matrix A = new matrix();
		matrix b = new matrix();
		for(int i=0; i<=(m1.n-1); i++){
			for(int j=0; j<(m1.m-1); j++){
				A.mat[i][j] = m1.mat[i][j];
			}
		}
		for(int i=0; i<=(m1.n-1); i++){
			int j = m1.m-1;
			b.mat[i][0] = m1.mat[i][j];
		}
		A.n=m1.n;
		A.m=A.n;
		b.n=m1.n;
		b.m=1;
		matrix hasil = new matrix();
		A.inversOBE();
		hasil = kaliMatrix(A, b);
		return hasil;
	}

	public matrix splCramer(matrix m1) {
		matrix A = new matrix();
		matrix b = new matrix();
		for (int i = 0; i <= (m1.n - 1); i++) {
			for (int j = 0; j < (m1.m - 1); j++) {
				A.mat[i][j] = m1.mat[i][j];
			}
		}
		A.n=m1.n;
		A.m=A.n;
		for (int i = 0; i <= (m1.n - 1); i++) {
			int j = (m1.m) - 1;
			b.mat[i][0] = m1.mat[i][j];
		}
		b.n=m1.n;
		b.m=1;
		double det = A.determinanKofaktor();

		matrix hasil = new matrix();
		

		for (int i=0; i<=(A.m-1); i++){
			for(int j=0; j<=(A.n-1); j++) {
				A.mat[j][i] = b.mat[j][0];
			}
			double det_x = A.determinanKofaktor();
			
			for(int j=0;j<A.n;j++){
				A.mat[j][i]=m1.mat[j][i];
			}
			
			hasil.mat[i][0] = det_x/det;
			
		}
		hasil.n=m1.n;
		hasil.m=1;
		return hasil;
	}

}


