package Practica_9;

import Practica_9.Clases_Importantes.Estudiante;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Practica_8 extends Operaciones {
    public static void main(String[] args) {

        Estudiante Pablo = new Estudiante("Pablo Mateos Palas", 5),
                JuanMaria = new Estudiante("Juan María Mateos Ponce", 0),
                Victor = new Estudiante("Victor Chacón Calle", 0),
                Jorge = new Estudiante("Jorge Coronil Villalba", 9),
                Ricardo = new Estudiante("Ricardo Gabriel Moreno Cantea", 4),
                Adrian = new Estudiante("Adrián Merino Gamaza", 4),
                JuanManuelH = new Estudiante("Juan Manuel Herrera Ramírez", 4),
                DanielAlfonso = new Estudiante("Daniel Alfonso Rodríguez Santos", 5),
                Diego = new Estudiante("Diego González Romero", 2),
                Jonathan = new Estudiante("Jonathan León Canto", 5),
                JuanManuelS = new Estudiante("Juan Manuel Saborido Baena", -1),
                Julian = new Estudiante("Julian García Velázquez", 1),
                JoseAntonio = new Estudiante("Jose Antonio Jaén Gómez", 0),
                AntonioJesus = new Estudiante("Antonio Jesús Téllez Perdigones", 2);

        List<Estudiante> listaEstudiantes = new ArrayList<>();
        listaEstudiantes.add(Pablo);
        listaEstudiantes.add(JuanMaria);
        listaEstudiantes.add(Victor);
        listaEstudiantes.add(Jorge);
        listaEstudiantes.add(Ricardo);
        listaEstudiantes.add(Adrian);
        listaEstudiantes.add(JuanManuelH);
        listaEstudiantes.add(DanielAlfonso);
        listaEstudiantes.add(Diego);
        listaEstudiantes.add(Jonathan);
        listaEstudiantes.add(JuanManuelS);
        listaEstudiantes.add(Julian);
        listaEstudiantes.add(JoseAntonio);
        listaEstudiantes.add(AntonioJesus);

        //Boolean para ejecución
        boolean exit = false;

        // Declaramos variable Scanner, que la que usaremos para introducir parametros por pantalla
        Scanner scan = new Scanner(System.in);

        while (!exit) {
            System.out.println("MENU:");
            System.out.println("1. Importar archivo XML");
            System.out.println("2. Exportar archivo XML");
            System.out.println("3. Seleccionar alumno menos participativo");
            System.out.println("4. Resetear participaciones");
            System.out.println("5. Alumnos más y menos participativos");
            System.out.println("6. Alumnos con participación por debajo de la media");
            System.out.println("7. Mostrar alumnos según su participación");
            System.out.println("8. Mostrar el alumno con la participación más reciente");
            System.out.println("9. Mostrar información de un alumno concreto");
            System.out.println("10. Dar de alta, baja y modificar alumnos");
            System.out.println("11. Salir");
            System.out.print("Introduce una opción (1-11): ");
            int opcion = scan.nextInt();

            switch (opcion) {
                case 1:
                    System.out.println("Introduce la ruta del archivo XML a importar: ");
                    String rutaImportar = scan.next();
                    importarXML(listaEstudiantes, rutaImportar);
                    break;
                case 2:
                    System.out.println("Introduce la ruta del archivo XML a exportar: ");
                    String rutaLectura = scan.next();
                    System.out.println("Introduce la ruta donde quieres guardar el archivo exportado: ");
                    String rutaEscritura = scan.next();
                    exportarXML(rutaLectura, rutaEscritura);
                    break;
                case 3:
                    System.out.println("Introduce la ruta del archivo XML de alumnos: ");
                    String rutaAlumnos = scan.next();
                    System.out.println(seleccionarAlumnoMenosParticipativo(rutaAlumnos));
                    break;
                case 4:
                    System.out.println("Introduce la ruta del archivo XML a resetear: ");
                    String rutaReset = scan.next();
                    resetearParticipaciones(rutaReset);
                    break;
                case 5:
                    System.out.println("Introduce la ruta del archivo XML para mostrar los alumno más y menos participativos: ");
                    String rutaMasMenosParticipativo = scan.next();
                    mostrarAlumnoMasYMenosParticipativo(rutaMasMenosParticipativo);
                    break;
                case 6:
                    System.out.println("Introduce la ruta del archivo XML para mostrar los alumno que estén por debajo de la media de participaciones: ");
                    String rutaAlumnosDebajoMedia = scan.next();
                    mostrarAlumnosPorDebajoDeLaMedia(rutaAlumnosDebajoMedia);
                    break;
                case 7:
                    System.out.println("Introduce la ruta del archivo XML a analizar: ");
                    String rutaAlumnosParticipacion = scan.next();
                    System.out.print("Introduce un valor para filtrar por participación: ");
                    int valor = scan.nextInt();
                    mostrarAlumnosPorParticipacion(rutaAlumnosParticipacion, valor);
                    break;
                case 8:
                    System.out.println("Introduce la ruta del archivo XML a analizar: ");
                    String rutaXML = scan.next();
                    ultimoAlumnoParticipar(rutaXML);
                    break;
                case 9:
                    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

                    System.out.println("Introduce la ruta del archivo XML a consultar: ");
                    String rutaConsulta = null;
                    try {
                        rutaConsulta = reader.readLine();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    System.out.println("Introduzca el nombre del alumno: ");
                    String nombrealumn = null;
                    try {
                        nombrealumn = reader.readLine();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    mostrarInformacionAlumno(rutaConsulta, nombrealumn);
                    break;
                case 10:
                    BufferedReader readerges = new BufferedReader(new InputStreamReader(System.in));

                    System.out.println("Introduce la ruta del archivo XML a modificar: ");
                    String rutaGestion = null;
                    try {
                        rutaGestion = readerges.readLine();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    System.out.println("Introduzca la opción a usar, 'alta', 'baja' o 'modificar'");
                    String opcionGestion = null;
                    try {
                        opcionGestion = readerges.readLine();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    System.out.println("Introduzca el nombre del alumno: ");
                    String nombre = null;
                    try {
                        nombre = readerges.readLine();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    gestionarAlumnos(rutaGestion, opcionGestion, nombre);
                    break;
                case 11:
                    exit = true;
                    System.out.println("Hasta luego!");
                    break;
                default:
                    System.out.println("Opción no válida");
            }
        }
    }
}
