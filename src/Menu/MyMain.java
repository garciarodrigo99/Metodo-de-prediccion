package Menu;

import clasificacion.*;
import clasificacion.distancia.*;
import clasificacion.pesadocasos.*;
import clasificacion.votacion.*;
import datos.*;
import datos.atributo.*;
import utilidades.preprocesado.*;

import java.util.ArrayList;


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
			for(int i=0;i<paramDataset.getNumCol();i++) {
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
				System.out.println("Imprimiendo dataset modificado: ");
				ArrayList<Atributo> ald = paramDataset.copia();
				for(int i=0; i<paramDataset.getNumFil(); i++) {
					Instancia.getInstancia(ald, i).print();
				}
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
					((KNN)paramClasif).valork = kOption;
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
					String enterkey = utilidades.IO.Opcion.getOpcionString("Introduzca umbral de aceptación (0-1,dos decimales): ");
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
		int menuOption=0;
		final int menuStart=1;
		final int menuEnd=3;
		int auxVariable=0;
		//Meter instancia por fichero
		if(!(paramDataset.getDataPreprocessingMetric().equals(DatosCrudos.getNombreStatic()))) {
			do {
				System.out.println("¿Desea procesar los datos antes o después de introducir la instancia?");
				optionMenuFormat(menuStart,"Antes");
				optionMenuFormat((menuStart + 1),"Despues");
				System.out.println("*Tenga en cuenta que dependiendo de la opcion que coja los resultados podrían verse alterados*");
				exitPrint(menuEnd);
				menuOption=utilidades.IO.Opcion.getOpcionInt();
				if((menuOption==menuStart)||(menuOption==(menuStart+1))) {
					auxVariable = menuOption - 1;
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
				System.out.println("Instancia clasificada como: "+paramClasif.clasificar(paramDataset.copia(), inst));
				menuOption=menuEnd;
			}
		}while(menuOption!=menuEnd);
	}
	
	/*
	 * Falta apartado 7 de la práctica 2 módulo de experimentación
	 */
	public static void main(String[] args) {
		final int OPCIONES_MENU = 13;
		Dataset ds = new Dataset(utilidades.IO.Fichero.nombreFich());
		Clasificacion clasif = new KNN();
		

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
			optionMenuFormat(12,"Comprobar tipo de instancia");
			exitPrint(OPCIONES_MENU);
			System.out.println("");

			option = utilidades.IO.Opcion.getOpcionInt();

			switch(option) {
				case 1:
					ds = new Dataset(utilidades.IO.Fichero.nombreFich());
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
					clasif.getInfo();
					break;
					
				case 12:
					checkInstanceMenu(ds, clasif);
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
