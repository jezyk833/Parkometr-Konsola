package test;



import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class ParkometrTest {

	public static void main(String[] args) throws IOException {
		
	Scanner sc = new Scanner(System.in);
	String filePath = "C:\\Users\\Jerzyk\\workspace\\PARKOMETR\\src\\test\\klienci.txt";			
	FileWriter fileWriter = null;		
	
 	String data= LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
	
    System.out.println("Dzień Dobry, dziś mamy:  " + data);
    System.out.println();
	System.out.println("Parkowanie bezpłatne do 15minut\n30 minut-2zł\n1 godzina-4zł\n2 godziny- 8zł\n4 i więcej godzin-15zł");
	System.out.println();
    System.out.println("Na ile czasu chcesz zaparkować?");        	
	int czasparkowania =sc.nextInt();
	String czasaktualny =LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
	LocalTime parseczas = LocalTime.parse(czasaktualny);
	
		if(czasparkowania <=24) {
			 LocalTime czas = parseczas.plusHours(czasparkowania);
			 System.out.println(" Czas parkowania do: " + czas);
		} else if (czasparkowania > 12) {
			LocalTime czas= parseczas.plusMinutes(czasparkowania);
			 System.out.println(" Czas parkowania do: " + czas);
		}
	System.out.println("Podaj numer samochodu(bez spacji): ");    	
	String numer = sc.next();
	
	double kwotaMinuty =2.0;
	double kwotaGodziny =4.0;
	double kwotaWielegodzin =15.0;
	double suma = 0;
	
	if (czasparkowania == 15 ) {
		System.out.println("Parkowanie bezpłatne");
	}else if(czasparkowania ==30) {
		System.out.println("Do zapłaty:"+ kwotaMinuty +"zł");
		suma=kwotaMinuty;
	}else if(czasparkowania ==1 || czasparkowania== 2 || czasparkowania ==3) {
			suma=kwotaGodziny * czasparkowania;
		System.out.println("Do zapłaty: "+ (kwotaGodziny * czasparkowania )+ "zł");		
	} else if(czasparkowania >=4) {
		System.out.println("Do zapłaty: " + kwotaWielegodzin +"zł");	
	  	 suma=kwotaWielegodzin;
	}
	
	Parkometr parkometr = new Parkometr(suma);		
	  double ile=0;
		
	while (ile < parkometr.getSaldo()) {
		System.out.println("Do zapłaty pozostało " + (parkometr.getSaldo() - ile)+"zł");
		System.out.println("Wrzuć kolejną monetę:");
		double moneta = sc.nextDouble();
		ile += moneta;
	 }
	if(ile > parkometr.getSaldo()) {
		double reszta = ile - parkometr.getSaldo();
		System.out.println("Wydaję resztę " + reszta+"zł");
	}
	
	System.out.println("Dziękujemy, miłego dnia");
	try {
	    fileWriter = new FileWriter(filePath);
	    fileWriter.write(czasaktualny);
	    fileWriter.write(" ");
	    if (czasparkowania == 15 ) {
	    	fileWriter.write("bezpłatnie");
		}else if(czasparkowania ==30) {
			fileWriter.write(String.valueOf(czasparkowania)+ "minut");
		}else if(czasparkowania >=1 && czasparkowania <=12) {
			fileWriter.write(String.valueOf(czasparkowania)+ "godziny");
		}	   
	    fileWriter.write(" ");
	    fileWriter.write(numer);
	    fileWriter.write(" ");
	    fileWriter.write(String.valueOf(parkometr.getSaldo())+ "zł");
	} finally {
	    if (fileWriter != null) {
	        fileWriter.close();
	   }
	}   	
  }
}		

class Parkometr {
	
	public Parkometr(double suma) {
		saldo=suma;
		
	}
	private double saldo;
	
	public double getSaldo() {
		return saldo;
	}
	public void setSaldo(double saldo) {
		this.saldo = saldo;
	}
	
	public void wpłata(double ile) {
	   setSaldo(saldo-=ile); 	
	}
	
}



