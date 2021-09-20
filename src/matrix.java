import java.util.Scanner;
import java.io.*;

public class matrix {
	double[][] mat = new double[1000][1000];
	int n; //matrix is n*m
	int m; //m should be n+1 (spek tubes)
	Scanner in = new Scanner(System.in);

	//constructor
	matrix() {
		for (int i = 0; i < 1000; i++) for (int j = 0; j < 1000; j++) mat[i][j] = 0;
	}

	//* READING MATRIX *//
	public void read() {
		System.out.printf("Masukan Matriks:\n1. Keyboard\n2. File\nPilihan:");
		int choice = this.in.nextInt();
		in.nextLine(); //eats newline
		if (choice == 1) this.readKey();
		else if (choice == 2) this.readFile(); //temporary
		else {
			System.out.printf("Masukan tidak valid\n");
			this.read();
		}
	}

	void readKey() {
		System.out.printf("Masukkan banyak baris: ");
		this.n = this.in.nextInt();
		in.nextLine(); //eats newline
		System.out.printf("Masukkan banyak kolom: ");
		this.m = this.in.nextInt(); //m should be n+1
		in.nextLine(); //same
		System.out.printf("Masukkan matriks:\n");
		for (int i = 0; i < this.n; i++)
			for (int j = 0; j < this.m; j++) {
				this.mat[i][j] = this.in.nextFloat();
				in.nextLine(); //same
			}
	}

	void readFile() {
		System.out.printf("Masukkan nama atau path file:\n");
		String file = this.in.nextLine();
		try {
			Scanner filein = new Scanner(new FileReader(file));
			int i = 0;
			while (filein.hasNextLine()) {
				String s = filein.nextLine();
				String[] temp = s.split(" ", 0);
				for (int j = 0; j < temp.length; j++) this.mat[i][j] = Double.parseDouble(temp[j]);
				i++;
			}
			this.n = i;
			this.m = i + 1;
			filein.close();
		} catch (FileNotFoundException ex) {
			System.out.printf("File not found\n"); //loops back to drvier
		}
	}

	//testing only
	public void display() {
		for (int i = 0; i < this.n; i++) {
			for (int j = 0; j < this.m; j++) {
				System.out.printf("%f ", this.mat[i][j]);
			}
			System.out.println();
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
				if (this.mat[i][j] != 0) {
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
				if (this.mat[bar][kol] != 0) {
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
				if (this.mat[i][j] == -0) {
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
				if (this.mat[bar][kol] == 1) {
					while (tempBar <= this.n - 1) {
						if (this.mat[tempBar][kol] != 0 && tempBar != bar) {
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

	double determinanOBE() {
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
				tukarBaris(bar, idxNotZero[0]);
				reverse += 1;
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
		return hasil;
	}

}

