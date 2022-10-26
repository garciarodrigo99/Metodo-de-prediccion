package algorithm_statics;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;

import utilidades.matematicas;
import utilidades.modificadores;

//https://www.geeksforgeeks.org/java-program-to-handle-divide-by-zero-and-multiple-exceptions/

public class algorithmspeedtester {

	public static final String PRUEBAS = " pruebas";
	public static final String T_NANO = "Tiempo en nanosegundos: ";
	public static final String T_MILLI = "Tiempo en milisegundos: ";
	public static final String T_SEC = "Tiempo en segundos: ";
	public static final String T_MIN = "Tiempo en minutos: ";
	
	
//	public static void variance_comparator(ArrayList<Double> lista) {
//		variance_comparator(lista,1);
//	}
	
	public static void variance_comparator(ArrayList<Double> lista, int opt) {
		switch (opt) {
			case 1 : 
				var_comp_nreps_time(lista,1);
				break;
			case 2 :
				var_comp_nreps_time(lista,2);
				break;
			case 3 :
				var_comp_ntimeslowest(lista);
				break;
			default:
					break;
		}
	}
	
	// En una iteración se hacen las llamadas a los respectivos algoritmos y se
	// cada vez que un algoritmo tarde menos que el otro se acumula en su
	// respectivo contador
	public static void var_comp_ntimeslowest(ArrayList<Double> lista) {

		int n_pruebas = 1000000;
		int stop_pruebas_at = 10;
		Instant start_test = Instant.now();
		while (n_pruebas>=stop_pruebas_at) {
			int best_alg1_times = 0;
			int best_alg2_times = 0;
			Instant start_n_pruebas = Instant.now();
			for (int i=0; i < n_pruebas; i++) {
				Instant start_alg_1 = Instant.now();
				matematicas.varianza(lista);
				Instant end_alg_1 = Instant.now();
				Duration timeElapsed_alg1 = Duration.between(start_alg_1, end_alg_1);
				
				Instant start_alg_2 = Instant.now();
				matematicas.oldvarianza(lista);
				Instant end_alg_2 = Instant.now();
				Duration timeElapsed_alg_2 = Duration.between(start_alg_2,
															end_alg_2);
				if (timeElapsed_alg1.toNanos() > timeElapsed_alg_2.toNanos()) {
					best_alg2_times++;
				}
				if (timeElapsed_alg1.toNanos() < timeElapsed_alg_2.toNanos()) {
					best_alg1_times++;
				}
			}
			Instant end_n_pruebas = Instant.now();
			Duration timeElapsed_n_pruebas = Duration.between(start_n_pruebas, end_n_pruebas);
			double per1 = (double)best_alg1_times/(double)n_pruebas;
			double per2 = (double)best_alg2_times/(double)n_pruebas;

			// Muestra segundos
			switch (n_pruebas) {
				case 10:
					
				case 100: 
					System.out.println(n_pruebas+PRUEBAS);
					System.out.println(T_MILLI+
							modificadores.decform.format(
									timeElapsed_n_pruebas.toNanos()/
									(double)1000000)+"\n");
					break;
				case 1000:
					System.out.println("1k"+PRUEBAS);
					System.out.println(T_MILLI+
							modificadores.decform.format(
									timeElapsed_n_pruebas.toNanos()/
									(double)1000000)+"\n");
					break;
				case 10000: 
					System.out.println("10k"+PRUEBAS);
					System.out.println(T_MILLI+
							timeElapsed_n_pruebas.toMillis()+"\n");
					break;
				case 100000: 
					System.out.println("100k"+PRUEBAS);
					System.out.println(T_MILLI+
							timeElapsed_n_pruebas.toMillis()+"\n");
					break;
				case 1000000: 
					System.out.println("1M"+PRUEBAS);
					System.out.println(T_MILLI+
							timeElapsed_n_pruebas.toMillis()+"\n");
					break;
				case 10000000: 
					System.out.println("10M"+PRUEBAS);
					System.out.println(T_SEC+
							timeElapsed_n_pruebas.toSeconds()+"\n");
					break;
				case 100000000: 
					System.out.println("100M"+PRUEBAS);
					System.out.println(T_SEC+
							timeElapsed_n_pruebas.toSeconds()+"\n");
					break;
				default:
					break;
			}
			System.out.println("Algoritmo propuesto: "+
							modificadores.decform.format(per1*100)+
							"%, algoritmo implementado: "+
							modificadores.decform.format(per2*100)+"%");
			
			n_pruebas /= 10;
	//			try {
	//				  Thread.sleep(1000);
	//			} catch (InterruptedException e) {
	//				  Thread.currentThread().interrupt();
	//			}
		}
		Instant end_test = Instant.now();
		Duration timeElapsed_test = Duration.between(start_test, end_test);
		System.out.println(T_SEC+timeElapsed_test.toSeconds()+"\n\n");
	}	
	
	// Ejecuta la llamada n veces, se mide el tiempo tardado y se compara con
	// el otro algoritmo
	public static void var_comp_nreps_time(ArrayList<Double> lista, int opt) {
		switch (opt) {
			case 1 : {
				int n_pruebas = 1000000;
				int iterator = 0;
				Instant start_alg_1 = Instant.now();
				while (iterator<n_pruebas) {
					matematicas.varianza(lista);
					iterator++;
				}
				Instant end_alg_1 = Instant.now();
				Duration timeElapsed_alg_1 = Duration.between(start_alg_1, end_alg_1);
				
				iterator = 0;
				Instant start_alg_2 = Instant.now();
				while (iterator<n_pruebas) {
					matematicas.oldvarianza(lista);
					iterator++;
				}
				Instant end_alg_2 = Instant.now();
				Duration timeElapsed_alg_2 = Duration.between(start_alg_2,
															end_alg_2);
	
				switch (n_pruebas) {
					case 10: {
						System.out.println(n_pruebas+PRUEBAS);
						displayTime(timeElapsed_alg_1,timeElapsed_alg_2,0);
						calculateTime(timeElapsed_alg_1,timeElapsed_alg_2,0);
						break;
					}
					case 100: {
						System.out.println(n_pruebas+PRUEBAS);
						displayTime(timeElapsed_alg_1,timeElapsed_alg_2,0);
						calculateTime(timeElapsed_alg_1,timeElapsed_alg_2,0);
						break;
					}
					case 1000: {
						System.out.println("1k pruebas"+PRUEBAS);
						displayTime(timeElapsed_alg_1,timeElapsed_alg_2,0);
						calculateTime(timeElapsed_alg_1,timeElapsed_alg_2,0);
						break;
					}
					case 10000: {
						System.out.println("10k pruebas"+PRUEBAS);
						displayTime(timeElapsed_alg_1,timeElapsed_alg_2,0);
						calculateTime(timeElapsed_alg_1,timeElapsed_alg_2,0);
						break;
					}
					case 100000: {
						System.out.println("100k pruebas"+PRUEBAS);
						displayTime(timeElapsed_alg_1,timeElapsed_alg_2,0);
						calculateTime(timeElapsed_alg_1,timeElapsed_alg_2,0);
						break;
					}
					case 1000000: {
						System.out.println("1M pruebas"+PRUEBAS);
						displayTime(timeElapsed_alg_1,timeElapsed_alg_2,1);
						calculateTime(timeElapsed_alg_1,timeElapsed_alg_2,1);
						break;
					}
					// Tiempo 2-3 seg
					// 2000-3000 mseg
					case 10000000: {
						System.out.println("10M pruebas"+PRUEBAS);
						displayTime(timeElapsed_alg_1,timeElapsed_alg_2,2);
						calculateTime(timeElapsed_alg_1,timeElapsed_alg_2,1);
						break;
					}
					case 100000000: {
						System.out.println("100M pruebas"+PRUEBAS);
						displayTime(timeElapsed_alg_1,timeElapsed_alg_2,2);
						calculateTime(timeElapsed_alg_1,timeElapsed_alg_2,1);
						break;
					}
					case 1000000000: {
						System.out.println("1kM pruebas"+PRUEBAS);
						displayTime(timeElapsed_alg_1,timeElapsed_alg_2,2);
						calculateTime(timeElapsed_alg_1,timeElapsed_alg_2,1);
						break;
					}
					default:
						break;
				}
				break;
			}
			case 2 : {
				long n_pruebas = 10000000000L;
				long iterator = 0;
				Instant start_alg_1 = Instant.now();
				while (iterator<n_pruebas) {
					matematicas.varianza(lista);
					iterator++;
				}
				Instant end_alg_1 = Instant.now();
				Duration timeElapsed_alg_1 = Duration.between(start_alg_1, end_alg_1);
				
				iterator = 0;
				Instant start_alg_2 = Instant.now();
				while (iterator<n_pruebas) {
					matematicas.oldvarianza(lista);
					iterator++;
				}
				Instant end_alg_2 = Instant.now();
				Duration timeElapsed_alg_2 = Duration.between(start_alg_2,
															end_alg_2);
				System.out.println("10kM pruebas");
				displayTime(timeElapsed_alg_1,timeElapsed_alg_2,3);
				calculateTime(timeElapsed_alg_1,timeElapsed_alg_2,1);
				break;
			}
			default:
				break;
		}
	}
	
	private static void displayTime(Duration alg_1, Duration alg_2, int option) {
		final String CADENA_1 = "Algoritmo 1: ";
		final String CADENA_2 = "\\nAlgoritmo 2: ";
		switch (option) {
			case 0: {
				System.out.println(T_NANO+"\n"+
						CADENA_1+alg_1.toNanos()+"\n"+
						CADENA_2+alg_2.toNanos());
				break;
			}
			case 1: {
				System.out.println(T_MILLI+"\n"+
						CADENA_1+alg_1.toMillis()+"\n"+
						CADENA_2+alg_2.toMillis());
				break;
			}
			case 2: {
				System.out.println(T_SEC+"\n"+
						CADENA_1+alg_1.toSeconds()+"\n"+
						CADENA_2+alg_2.toSeconds());
				break;
			}
			case 3: {
				System.out.println(T_MIN+"\n"+
						CADENA_1+alg_1.toMinutes()+"\n"+
						CADENA_2+alg_2.toMinutes());
				break;
			}
			default:
				break;
		}
	}
	
	private static void calculateTime(Duration alg_1, Duration alg_2, int option) {
		int t_alg_1 = 1;
		int t_alg_2 = 1;
		switch (option) {
			case 0: {
				t_alg_1 = (int)alg_1.toNanos();
				t_alg_2 = (int)alg_2.toNanos();
				break;
			}
			case 1: {
				t_alg_1 = (int)alg_1.toMillis();
				t_alg_2 = (int)alg_2.toMillis();
				break;
			}
			case 2: {
				t_alg_1 = (int)alg_1.toSeconds();
				t_alg_2 = (int)alg_2.toSeconds();
				break;
			}
			default:
				break;
		}
		if (t_alg_1 > t_alg_2) {
			System.out.println("Algoritmo 2 "+modificadores.decform.format((((double)t_alg_1/(double)t_alg_2)-1.0)*100)+"% + rápido");
		} else {
			System.out.println("Algoritmo 1 "+modificadores.decform.format((((double)t_alg_2/(double)t_alg_1)-1.0)*100)+"% + rápido");
		}
	}

}
