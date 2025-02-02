package Menu;

import clasificacion.*;
import clasificacion.distancia.*;
import clasificacion.pesadocasos.*;
import clasificacion.votacion.*;
import datos.*;
import datos.atributo.*;
import utilidades.matematicas;
import utilidades.modificadores;
import utilidades.preprocesado.*;
import experimentation.confusionMatrix;

import java.util.ArrayList;
import java.util.Arrays;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import algorithm_statics.algorithmspeedtester;

import java.io.File;
import java.io.FileWriter;   // Import the FileWriter class
import java.io.IOException;  // Import the IOException class to handle errors
import java.time.Duration;
import java.time.Instant;


public class MyMain {

	private static void optionMenuFormat(int n, String text) {
		System.out.println("["+n+"] "+text);
	}
	
	private static void exitPrint(int n) {
		System.out.println("["+n+"] Salir");
	}
	
	private static void atributeInformationMenu(Dataset paramDataset) {
		int menuOption = -1;
		final int menuStart = 0;
		final int menuEnd = paramDataset.getNumCol() + 1;
		do {
			optionMenuFormat(menuStart,"Mostrar informacion de todos los atributos");
			for(int i=0;i<paramDataset.getNumCol();i++) {
				String text = "Mostrar informacion del atributo '" + paramDataset.at(i).getNombre()+"'";
				optionMenuFormat(i+1,text);
			}
			exitPrint(menuEnd);
			menuOption=utilidades.IO.Opcion.getOpcionInt();
			// Se hace al principio para que no siga avanzando en el codigo
			if (!((menuOption >= menuStart) && (menuOption <= menuEnd))) {
				utilidades.IO.Opcion.optNoValid();
			}
			if(menuOption == menuStart) {
				for(int i=0;i<paramDataset.getNumCol();i++) {
					paramDataset.at(i).getInfo();
				}
			}
			if((menuOption>menuStart)&&(menuOption<menuEnd)) {
				paramDataset.at(menuOption-1).getInfo();
			}	
		}while(menuOption != menuEnd);//Opcion salir
	}
	
	private static void dataPreprocessingMenu(Dataset paramDataSet) {
		int menuOpt=0;
		do {
			System.out.println("Seleccione métrica de preprocesado de datos:");
			optionMenuFormat(1, "Sin preprocesado (datos crudos)");
			optionMenuFormat(2, "Rango 0-1");
			optionMenuFormat(3,"Estandarizacion");
			exitPrint(4);

			menuOpt=utilidades.IO.Opcion.getOpcionInt();

			switch(menuOpt) {
				// Cambiar para que se muestren las opciones desde el dataset
				case 1:
					//paramClasif.getEntorno().getKnn().setPreprocesadoDatos(new DatosCrudos());
					break;
				case 2:
					//paramClasif.getEntorno().getKnn().setPreprocesadoDatos(new RangoZeroOne());
					break;
				case 3:
					//paramClasif.getEntorno().getKnn().setPreprocesadoDatos(new Estandarizacion());
					break;
				case 4:
					break;
				default:
					utilidades.IO.Opcion.optNoValid();
					break;
			}	
		}while(menuOpt!=4);
	}
	
	private static void atributeWeightMenu(Dataset paramDataset, 
																				Clasificacion paramClasif) {
		final int menuStart = 1;
		final int menuEnd = paramDataset.getNumCol() + 1;
		int menuOpt = 0;
		if (((KNN)paramClasif).getEntorno().GetPesoAtrib().size() != 
				(paramDataset.getNumCol() - 1)) {
			((KNN)paramClasif).getEntorno().BuildPesoAtrib(paramDataset.getNumCol() - 1);
		}
		do {
			System.out.print("\nMostrando pesos: "+"\n");
			for(int i=0;i<paramDataset.getNumCol()-1;i++) {
				System.out.print("Peso de "+paramDataset.at(i).getNombre()+": "+
										((KNN)paramClasif).getEntorno().GetPesoAtrib().get(i)+"\t");
			}
			System.out.print("\n");

			System.out.println("\nModificar peso atributo:");
			for(int i=0;i<paramDataset.getNumCol()-1;i++) {
				optionMenuFormat(i+1, paramDataset.at(i).getNombre());
			}
			optionMenuFormat(paramDataset.getNumCol(), 
			"Imprimir dataset con pesos actualizados");
			exitPrint(menuEnd);

			menuOpt = utilidades.IO.Opcion.getOpcionInt();
			// No se trabaja con switch porque 'Error: case expressions must be constant expressions'
			if((menuOpt<menuStart) || (menuOpt>menuEnd)) {
				utilidades.IO.Opcion.optNoValid();
			}
			if((menuOpt>=menuStart) && (menuOpt<(menuEnd-1))) {
				Double peso = -1.0;
				while(peso<0.0) {
					peso=utilidades.IO.Opcion.getOpcionDouble("\nIntroduzca peso: ");
				}
				((KNN)paramClasif).getEntorno().ModifyPesoAtrib(menuOpt-1, peso);
			}
			if(menuOpt == (menuEnd - 1)){
				System.out.println("Comentado");
//				System.out.println("Imprimiendo dataset modificado: ");
//				ArrayList<Atributo> ald = paramDataset.copia();
//				for(int i=0; i<paramDataset.getNumFil(); i++) {
//					Instancia.getInstancia(ald, i).print();
//				}
			}
		}while(menuOpt!=menuEnd);
	}

	private static void kValueMenu(Dataset paramDataset, 
																Clasificacion paramClasif) {
		int menuOption=0;
		final int menuStart = 1;
		final int menuEnd = 2;
		do {
			System.out.println("Valor actual de k: "+((KNN)paramClasif).valork);
			optionMenuFormat(menuStart,"Modificar el valor actual de k (numero de vecinos)");
			exitPrint(menuEnd);
			menuOption=utilidades.IO.Opcion.getOpcionInt();
			switch(menuOption) {
				case 1:
					int kOption=-1;
					while((kOption<menuStart)||(kOption>paramDataset.getNumFil())) {
						kOption=utilidades.IO.Opcion.getOpcionInt("Introduzca el nuevo valor de k: ");
					}
					((KNN)paramClasif).setKNeighbours(kOption);
					break;
				case 2:
					break;
				default:
					utilidades.IO.Opcion.optNoValid();
					break;
			}	
		}while(menuOption!=menuEnd);
	}

	private static void distanceMetricMenu(Clasificacion paramClasif) {
		int menuOption = 0;
		final int menuStart = 1;
		final int menuEnd = 4;
		do {
			System.out.println("Seleccione métrica para el cálculo de distancias:");
			optionMenuFormat(menuStart,"Euclidea");
			optionMenuFormat(menuStart+1,"Manhattan");
			optionMenuFormat(menuStart+2,"Chebychef");
			exitPrint(menuEnd);
			menuOption = utilidades.IO.Opcion.getOpcionInt();
			switch(menuOption) {
				case 1:
					((KNN)paramClasif).getEntorno().SetDistanceMetric(new Euclidea());
					break;
				case 2:
					((KNN)paramClasif).getEntorno().SetDistanceMetric(new Manhattan());
					break;
				case 3:
					((KNN)paramClasif).getEntorno().SetDistanceMetric(new Chebychef());
					break;
				case 4:
					break;
				default:
					utilidades.IO.Opcion.optNoValid();
					break;
			}
			// Se ha seleccionado tal métrica
		}while(menuOption!=menuEnd);
	}

	private static void caseMetricMenu(Clasificacion paramClasif) {
		int menuOption = 0;
		final int menuStart = 1;
		final int menuEnd = 5;
		do {
			System.out.println("Seleccione métrica para el pesado de casos:");
			optionMenuFormat(menuStart, "Igualdad de votos");
			optionMenuFormat((menuStart + 1), "Cercanía");
			optionMenuFormat((menuStart + 2), "Voto fijo");
			System.out.println("[4]Voto fijo x cercania");
			exitPrint(menuEnd);
			menuOption = utilidades.IO.Opcion.getOpcionInt();
			switch(menuOption) {
				case menuStart:
					((KNN)paramClasif).setPesadoCasos(new IgualdadVotos());
					break;
				case (menuStart + 1):
					((KNN)paramClasif).setPesadoCasos(new Cercania());
					break;
				case (menuStart + 2):
					((KNN)paramClasif).setPesadoCasos(new VotoFijo(((KNN)paramClasif).valork));
					break;
				// case (menuStart + 3):
				// 	paramClasif.getEntorno().setVotacion(new VotoXDistancia(paramClasif.getEntorno().getKnn().getValorK()));
				// 	break;
				case menuEnd:
					break;
				default:
					utilidades.IO.Opcion.optNoValid();
					break;
			}	
		}while(menuOption!=menuEnd);
	}

	private static void clasificationRuleMenu(Clasificacion paramClasif) {
		int menuOption = 0;
		final int menuStart = 1;
		final int menuEnd = 3;
		do {
			System.out.println("Especificar la regla de clasificación: ");
			optionMenuFormat(menuStart, "Mayoría simple");
			optionMenuFormat((menuStart + 1), "Umbral de aceptación");
			exitPrint(menuEnd);
			menuOption = utilidades.IO.Opcion.getOpcionInt();
			switch(menuOption) {
				case menuStart:
					((KNN)paramClasif).setVotacion(new MayoriaSimple());
					break;
				case (menuStart + 1): {
					double umbralOption = -1.0;
					while((umbralOption<0.0)||(umbralOption>1.0)) {
					String enterkey = utilidades.IO.Opcion.getOpcionString("Introduzca umbral de aceptación (0-1.Dos decimales(Punto)): ");
					if(enterkey.isEmpty()) {
						umbralOption = Umbral.UMBRAL;
					} else
						umbralOption = Double.valueOf(enterkey);
					}
					((KNN)paramClasif).setVotacion(new Umbral(umbralOption));
					break;
				}
				case menuEnd:
					break;
				default:
					utilidades.IO.Opcion.optNoValid();
					break;
			}	
		}while(menuOption!=menuEnd);
	}
	
	private static void checkInstanceMenu(Dataset paramDataset, 
										Clasificacion paramClasif) {
		int menuOption=-1;
		final int menuStart=0;
		final int menuEnd=2;
		int auxVariable=0;
		//Meter instancia por fichero
		if(!(paramDataset.getDataPreprocessingMetric().getNombre().equals(DatosCrudos.getNombreStatic()))) {
			do {
				System.out.println("¿Desea procesar los datos antes o después de introducir la instancia?");
				optionMenuFormat(menuStart,"Antes");
				optionMenuFormat((menuStart + 1),"Despues");
				System.out.println("*Tenga en cuenta que dependiendo de la opcion que coja los resultados podrían verse alterados*");
				exitPrint(menuEnd);
				menuOption=utilidades.IO.Opcion.getOpcionInt();
				if((menuOption==menuStart)||(menuOption==(menuStart+1))) {
					auxVariable = menuOption;
					menuOption=menuEnd;
				}
			}while(menuOption!=menuEnd);
		}
		int secondMenuOption=0;
		final int secondMenuStart=1;
		final int secondMenuEnd=3;
		do {
			optionMenuFormat(secondMenuStart,"Leer instancia por fichero");
			optionMenuFormat((secondMenuStart + 1),"Introducir instancia por teclado");
			exitPrint(secondMenuEnd);
			secondMenuOption=utilidades.IO.Opcion.getOpcionInt();
			Instancia inst;
			if((secondMenuOption==secondMenuStart)||(secondMenuOption==(secondMenuStart + 1))) {
				if(secondMenuOption==secondMenuStart) {
					String nombre_archivo = utilidades.IO.Fichero.nombreFich();
					inst = new Instancia(Instancia.leerInstancia(paramDataset.getNumCol()-1, nombre_archivo));//Las instancias a evaluar irán sin leer su tipo
				} else {
					System.out.println("Introduzca la instancia a evaluar");
					inst = new Instancia(Instancia.leerInstancia(paramDataset.getNumCol()));
				}
				((KNN)paramClasif).setMomento(auxVariable);
				System.out.println("¿Instancia?");
				inst.print();
				System.out.println("[MyMain]");
				System.out.println("Instancia clasificada como: "+paramClasif.clasificar(paramDataset.copia(), inst));
				menuOption=menuEnd;
			}
		}while(menuOption!=menuEnd);
	}
	
	private static void clasifConfig(ArrayList<Clasificacion> vectorClasifConfig, int k) {

		ArrayList<Integer> vectorKValues = new ArrayList<>();
		int ksr = (int)Math.sqrt(k);
		int halfRange = (k / 20) / 2;
		for(int i=(ksr-halfRange);i<=(ksr+halfRange);i++) {
			vectorKValues.add(i);
		}
		System.out.println(vectorKValues.size());
		System.out.println(vectorKValues);

		ArrayList<Distancia> vectorDistance = new ArrayList<>(
			Arrays.asList(new Euclidea(), new Manhattan(), new Chebychef())
		);
		System.out.println(vectorDistance.size());

		ArrayList<PesadoCasos> vectorPesado = new ArrayList<>(
			Arrays.asList(new Cercania(), new IgualdadVotos())// Añadir voto fijo
		);
		System.out.println(vectorPesado.size());

		ArrayList<Double> vectorUmbral = new ArrayList<>(
			Arrays.asList(0.5,0.55,0.6,0.65,(2.0/3.0),0.7,0.75,
			0.8,0.85,0.9,0.91,0.92,0.93,0.94,0.95,0.96,0.97,0.98,0.99,1.0)
			);
		System.out.println(vectorUmbral.size());

		for(int iteratorK = 0;iteratorK<vectorKValues.size();iteratorK++) {
			for(int iteratorDistance = 0;iteratorDistance<vectorDistance.size();iteratorDistance++) {
				for(int iteratorPesadoCasos = 0;iteratorPesadoCasos<vectorPesado.size();iteratorPesadoCasos++) {
					for(int iteratorUmbral = 0;iteratorUmbral<vectorUmbral.size();iteratorUmbral++) {
						Clasificacion nClasif = new KNN();
						((KNN)nClasif).setKNeighbours(vectorKValues.get(iteratorK));
						((KNN)nClasif).getEntorno().SetDistanceMetric(vectorDistance.get(iteratorDistance));
						((KNN)nClasif).setPesadoCasos(vectorPesado.get(iteratorPesadoCasos));
						((KNN)nClasif).setVotacion(new Umbral(vectorUmbral.get(iteratorUmbral)));
						vectorClasifConfig.add(nClasif);
					}
				}
				for(int iteratorUmbral = 0;iteratorUmbral<vectorUmbral.size();iteratorUmbral++) {
					Clasificacion nClasif = new KNN();
					((KNN)nClasif).setKNeighbours(vectorKValues.get(iteratorK));
					((KNN)nClasif).getEntorno().SetDistanceMetric(vectorDistance.get(iteratorDistance));
				 	((KNN)nClasif).setPesadoCasos(new VotoFijo(((KNN)nClasif).valork));
					((KNN)nClasif).setVotacion(new Umbral(vectorUmbral.get(iteratorUmbral)));
					vectorClasifConfig.add(nClasif);
				}
			}	
		}
	}
	
	private static void playClip(String clipName) {
	    try {
	        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(clipName).getAbsoluteFile());
	        Clip clip = AudioSystem.getClip();
	        clip.open(audioInputStream);
	        clip.start();
	        // If you want the sound to loop infinitely, then put: clip.loop(Clip.LOOP_CONTINUOUSLY); 
	        // If you want to stop the sound, then use clip.stop();
	    } catch (Exception ex) {
	        ex.printStackTrace();
	    }
	}
	/*
	 * Falta apartado 7 de la práctica 2 módulo de experimentación
	 */
	public static void main(String[] args) {
		final int OPCIONES_MENU = 17;
		String dataset_file_name = utilidades.IO.Fichero.nombreFich();
		Dataset ds = new Dataset(dataset_file_name);
		Clasificacion clasif = new KNN();
		ArrayList<Clasificacion> vectorClasifConfig = new ArrayList<Clasificacion>();

		int option = 0;
		do{
			System.out.println("");
			optionMenuFormat(1,"Cargar dataset con otro fichero");
			optionMenuFormat(2,"Imprimir dataset original");
			optionMenuFormat(3,"Mostrar información dataset");
			optionMenuFormat(4,"Mostrar información atributo de dataset");
			optionMenuFormat(5,"Seleccionar métrica preprocesado de datos");
			optionMenuFormat(6,"Modificar peso de atributos");
			optionMenuFormat(7,"Modificar el valor de k");
			optionMenuFormat(8,"Seleccionar métricas cálculo de distancias");
			optionMenuFormat(9,"Seleccionar métrica pesado de casos (vecinos más cercanos)");
			optionMenuFormat(10,"Especificar la regla de clasificación");
			optionMenuFormat(11,"Mostrar información parámetros de configuración del algoritmo de clasificación");
			optionMenuFormat(12,"Generar todas las configuraciones posibles");
			optionMenuFormat(13,"Comprobar tipo de instancia");
			optionMenuFormat(14,"Comparador algoritmos KNN");
			optionMenuFormat(15,"Macroexperimentacion(opcion 12 requerida previamente)");
			exitPrint(OPCIONES_MENU);
			System.out.println("");

			option = utilidades.IO.Opcion.getOpcionInt();

			switch(option) {
				case 1:
					dataset_file_name = utilidades.IO.Fichero.nombreFich();
					ds = new Dataset(dataset_file_name);
					break;
				case 2:
					for(int i=0; i<ds.getNumFil(); i++) {
						ds.getInstancia(i).print();
						System.out.print("\n");
					}
					break;
				case 3:
					System.out.print("-----Información Dataset-----"+"\n");
					System.out.print("Nombre de atributos: ");
					for(int i=0; i<ds.getNumCol(); i++) {
						System.out.print(ds.at(i).getNombre()+" | ");
					}
					System.out.print("\nNumero de atributos: "+ds.getNumCol()+"\n");
					System.out.print("Numero de casos: "+ds.getNumFil()+"\n\n");
					break;
				case 4:
					atributeInformationMenu(ds);
					break;
				case 5:
					// Cambiar
					dataPreprocessingMenu(ds);
					break;
				case 6:
					atributeWeightMenu(ds,clasif);
					break;
				
				case 7:
					kValueMenu(ds, clasif);
					break;
					
				case 8:
					distanceMetricMenu(clasif);
					break;
					
				case 9:
					caseMetricMenu(clasif);
					break;
					
				case 10:
					clasificationRuleMenu(clasif);
					break;
					
				case 11:
					((KNN)clasif).getInfo();
					break;
					
				case 12:
					clasifConfig(vectorClasifConfig, ds.getNumFil());
					System.out.println(vectorClasifConfig.size());
					break;
					
				case 13:
					//checkInstanceMenu(ds, clasif);
					Instancia inst = new Instancia(Instancia.leerInstancia(ds.getNumCol()-1, "ins2.csv"));
					if (vectorClasifConfig.size() > 0) {
					    try {
					        FileWriter myWriter = new FileWriter("test.txt");
							Instant startFor = Instant.now();
							myWriter.write(KNN.getLabels()+"\n");
							for(int i=0;i<vectorClasifConfig.size();i++) {
								String typeResult = vectorClasifConfig.get(i).clasificar(ds.copia(), inst);
								if (typeResult.equals(inst.getAtCat().at(0))) {
									myWriter.write(vectorClasifConfig.get(i).getCsvValues()+"\n");
								}
							    //myWriter.write("\nInstancia clasificada como: "+
												//vectorClasifConfig.get(i).clasificar(ds.copia(), inst)+"\n\n");
							}
							Instant endFor = Instant.now();
					        myWriter.close();
							Duration timeFor = Duration.between(startFor, endFor);
							System.out.println(timeFor.toSeconds()+" seconds.");
				      } catch (IOException e) {
				        System.out.println("An error occurred.");
				        e.printStackTrace();
				      }
					} else {
						System.out.println("Instancia clasificada como: "+clasif.clasificar(ds.copia(), inst));
					}
					//System.out.println("Instancia clasificada como: "+clasif.clasificar(ds.copia(), inst));
					break;
				case 14 :
					if (dataset_file_name.equals("glass.csv")) {
						Instancia aux = new Instancia(Instancia.leerInstancia(ds.getNumCol()-1, "ins2.csv"));
						int iterator = 0;
						while (iterator < 5) {
							algorithmspeedtester.knnTimeNreps(ds.copia(), (int)Math.sqrt(ds.getNumFil()), aux, 10000);
							System.out.println();
							iterator++;
						}
					} else {
						Instancia aux = new Instancia(Instancia.leerInstancia(ds.getNumCol()-1, "ins1.csv"));
						int iterator = 0;
						while (iterator < 5) {
							algorithmspeedtester.knnTimeNreps(ds.copia(), (int)Math.sqrt(ds.getNumFil()), aux, 1000);
							System.out.println();
							iterator++;
						}
					}
					playClip("morse_end.wav");
					break;
				case 15 :
					ArrayList<String> cat = new ArrayList<String>(ds.getUniqueTypes());
					cat.add(modificadores.labelUnclasified);
					double acceptPredict = 0.75;
					System.out.println(cat);
				    try {
				        FileWriter myWriter = new FileWriter("total_elements_2_"+(int)(acceptPredict*100)+"_"+dataset_file_name.replaceFirst(".csv", "")+".csv");
						Instant startFor = Instant.now();
						myWriter.write(KNN.getLabels()+",Prec_pred"+"\n");
						int sum = 0;
						for(int i=0;i<vectorClasifConfig.size();i++) {
							confusionMatrix myMatrix = new confusionMatrix(cat);
							for(int j=0;j<ds.copia().get(0).getSize();j++) {
								myMatrix.addValue(ds.getInstancia(j).getAtCat().at(0), vectorClasifConfig.get(i).clasificar(ds.copia(), ds.getInstancia(j)));
							}
							double prec_predict = (double)myMatrix.getSumMainDiagonal()/(double)myMatrix.getTotalElements();
							if (prec_predict >= acceptPredict) {
								sum++;
								System.out.println(i+": "+sum);
								myWriter.write(vectorClasifConfig.get(i).getCsvValues()+","+prec_predict+"\n");
							} else {
								System.out.println(i);
							}
						}
						Instant endFor = Instant.now();
				        myWriter.close();
						Duration timeFor = Duration.between(startFor, endFor);
						System.out.println(timeFor.toSeconds()+" seconds.");
				    } catch (IOException e) {
				    	System.out.println("An error occurred.");
				    	e.printStackTrace();
				    }
				    playClip("morse_end.wav");
					break;
					
				case OPCIONES_MENU: 
					break;
					
				default:
					utilidades.IO.Opcion.optNoValid();
					break;
			}
		}while(option!=OPCIONES_MENU);
	}
}
