package Practica_9;

import Practica_9.Clases_Importantes.Estudiante;
import org.w3c.dom.Element;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.*;

public class Operaciones {
    private static final String DB_URL = "jdbc:mariadb://localhost:3306/";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";
    private static final String DRIVER = "org.mariadb.jdbc.Driver";

    static String alumnoMasParticipativo(String nombreBD, String nombreTabla) {

        String alumnoSeleccionado = null;

        try {
            // Enlazar con el driver
            Class.forName(DRIVER);

            Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            Statement stm = con.createStatement();

            // Alumno con más intervenciones
            String auxQuery = "SELECT MAX(intervenciones) AS maximo FROM " + nombreBD + "." + nombreTabla + ";";
            ResultSet auxResul = stm.executeQuery(auxQuery);
            int maxIntervenciones = 0;
            if (auxResul.next()) {
                maxIntervenciones = auxResul.getInt("maximo");
            }
            auxQuery = "SELECT alumno FROM " + nombreBD + "." + nombreTabla + " WHERE intervenciones = " + maxIntervenciones + ";";
            auxResul = stm.executeQuery(auxQuery);
            List<String> alumnos = new ArrayList<>();
            while (auxResul.next()) {
                alumnos.add(auxResul.getString("alumno"));
            }

            // Seleccionar un alumno aleatorio de la lista
            Random random = new Random();
            int index = random.nextInt(alumnos.size());
            alumnoSeleccionado = alumnos.get(index);

            System.out.println("Alumno con más intervenciones: " + alumnoSeleccionado);

            stm.close();
            con.close();

        } catch(SQLException | ClassNotFoundException e){
            System.err.println("Ha fallado la conexión: " + e.getMessage());
        }
        return alumnoSeleccionado;
    }

    static String alumnoMenosParticipativo(String nombreBD, String nombreTabla) {

        String alumnoSeleccionado = null;

        try {
            // Enlazar con el driver
            Class.forName(DRIVER);

            Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            Statement stm = con.createStatement();

            String auxQuery = "SELECT MIN(intervenciones) AS minimo FROM " + nombreBD + "." + nombreTabla + ";";
            ResultSet auxResul = stm.executeQuery(auxQuery);
            int minIntervenciones = 0;
            if (auxResul.next()) {
                minIntervenciones = auxResul.getInt("minimo");
            }
            auxQuery = "SELECT alumno FROM " + nombreBD + "." + nombreTabla + " WHERE intervenciones = " + minIntervenciones + ";";
            auxResul = stm.executeQuery(auxQuery);
            List<String> alumnosMin = new ArrayList<>();
            while (auxResul.next()) {
                alumnosMin.add(auxResul.getString("alumno"));
            }

            // Seleccionar un alumno aleatorio de la lista
            Random random2 = new Random();
            int index2 = random2.nextInt(alumnosMin.size());
            alumnoSeleccionado = alumnosMin.get(index2);

            System.out.println("Alumno con menos intervenciones: " + alumnoSeleccionado);

            stm.close();
            con.close();

        } catch(SQLException | ClassNotFoundException e){
            System.err.println("Ha fallado la conexión: " + e.getMessage());
        }
        return alumnoSeleccionado;
    }

    static List<String> alumnosDebajoMedia(String nombreBD, String nombreTabla) {

        List<String> alumnosSeleccionados = new ArrayList<>();

        try {
            // Enlazar con el driver
            Class.forName(DRIVER);

            Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            Statement stm = con.createStatement();

            // Alumnos que están por debajo de la media de intervenciones por alumno
            String query = "SELECT alumno FROM " + nombreBD + "." + nombreTabla + " WHERE intervenciones<(SELECT AVG(intervenciones) FROM " + nombreBD + "." + nombreTabla + ");";
            ResultSet resultado = stm.executeQuery(query);

            System.out.println("Alumnos con intervenciones por debajo de la media: ");
            while (resultado.next()) {
                String alumno = resultado.getString("alumno");
                alumnosSeleccionados.add(alumno);
                System.out.println("- " + alumno);            }

            stm.close();
            con.close();

        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("Ha fallado la conexión: " + e.getMessage());
        }
        return alumnosSeleccionados;
    }

    static List<String> alumnosIntervencionesValor(String nombreBD, String nombreTabla, String numIntervenciones) {

        List<String> alumnosSeleccionados = new ArrayList<>();

        try {
            // Enlazar con el driver
            Class.forName(DRIVER);

            Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            Statement stm = con.createStatement();

            // Alumnos que tienen un número de participaciones superior, inferior o igual a un valor indicado
            String query1 = "SELECT alumno FROM " + nombreBD + "." + nombreTabla + " WHERE intervenciones=" + numIntervenciones + ";";
            ResultSet resultado1 = stm.executeQuery(query1);

            System.out.println("Alumnos con intervenciones iguales a " + numIntervenciones + ":");
            while (resultado1.next()) {
                String alumno = resultado1.getString("alumno");
                alumnosSeleccionados.add(alumno);
                System.out.println("- " + alumno);
            }

            alumnosSeleccionados.add("] [");

            String query2 = "SELECT alumno FROM " + nombreBD + "." + nombreTabla + " WHERE intervenciones>" + numIntervenciones + ";";
            ResultSet resultado2 = stm.executeQuery(query2);

            System.out.println("Alumnos con intervenciones por encima de " + numIntervenciones + ":");
            while (resultado2.next()) {
                String alumno = resultado2.getString("alumno");
                alumnosSeleccionados.add(alumno);
                System.out.println("- " + alumno);
            }

            alumnosSeleccionados.add("] [");

            String query3 = "SELECT alumno FROM " + nombreBD + "." + nombreTabla + " WHERE intervenciones<" + numIntervenciones + ";";
            ResultSet resultado3 = stm.executeQuery(query3);

            System.out.println("Alumnos con intervenciones por debajo de " + numIntervenciones + ":");
            while (resultado3.next()) {
                String alumno = resultado3.getString("alumno");
                alumnosSeleccionados.add(alumno);
                System.out.println("- " + alumno);
            }

            stm.close();
            con.close();

        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("Ha fallado la conexión: " + e.getMessage());
        }
        return alumnosSeleccionados;
    }

    static List<String> alumnoUltimo(String nombreBD, String nombreTabla) {
        List<String> alumnosSeleccionados = new ArrayList<>();

        try {
            // Enlazar con el driver
            Class.forName(DRIVER);

            Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            Statement stm = con.createStatement();

            ResultSet rs = stm.executeQuery("SELECT ultima_intervencion FROM " + nombreBD + "." + nombreTabla + " ORDER BY ultima_intervencion DESC LIMIT 1");

            if (rs.next()) {
                Date ultimaIntervencion = rs.getDate("ultima_intervencion");
                ArrayList<String> alumnos = new ArrayList<>();

                rs = stm.executeQuery("SELECT alumno FROM " + nombreBD + "." + nombreTabla + " WHERE ultima_intervencion = '" + ultimaIntervencion + "'");

                while (rs.next()) {
                    String alumno = rs.getString("alumno");
                    alumnos.add(alumno);
                }

                if (!alumnos.isEmpty()) {
                    if (alumnos.size() > 1) {
                        Random random = new Random();
                        int indiceAleatorio = random.nextInt(alumnos.size());
                        String alumnoSeleccionado = alumnos.get(indiceAleatorio);
                        alumnosSeleccionados.add(alumnoSeleccionado);
                    } else {
                        String alumnoSeleccionado = alumnos.get(0);
                        alumnosSeleccionados.add(alumnoSeleccionado);
                    }
                } else {
                    alumnosSeleccionados.add("No hay alumnos con la misma fecha de intervención");
                }
            } else {
                alumnosSeleccionados.add("No hay alumnos con intervenciones");
            }

            stm.close();
            con.close();

        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("Ha fallado la conexión: " + e.getMessage());
        }

        return alumnosSeleccionados;
    }

    static String seleccionarAlumnoAleatorio(String nombreBD, String nombreTabla) {

        String resultado = "null";

        try {
            // Enlazar con el driver
            Class.forName(DRIVER);

            Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            Statement stm = con.createStatement();

            String query = "SELECT alumno FROM " + nombreBD + "." + nombreTabla;
            ResultSet rs = stm.executeQuery(query);
            ArrayList<String> alumnos = new ArrayList<>();

            while (rs.next()) {
                String alumno = rs.getString("alumno");
                alumnos.add(alumno);
            }

            if (alumnos.isEmpty()) {
                resultado = "No hay alumnos en la base de datos.";
            } else {
                Random random = new Random();
                int indiceAleatorio = random.nextInt(alumnos.size());
                String alumnoSeleccionado = alumnos.get(indiceAleatorio);
                resultado = "Alumno seleccionado: " + alumnoSeleccionado;
            }

            stm.close();
            con.close();

        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        return resultado;
    }

    static List<String> alumnoInfo(String nombreBD, String nombreTabla, String alumno) {
        List<String> infoAlumno = new ArrayList<>();

        try {
            // Enlazar con el driver
            Class.forName(DRIVER);

            Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            Statement stm = con.createStatement();

            // Mostrar la información de un alumno dado
            String query = "SELECT * FROM " + nombreBD + "." + nombreTabla + " WHERE alumno = '" + alumno + "';";
            ResultSet rs = stm.executeQuery(query);

            while (rs.next()) {
                String nombre = rs.getString("alumno");
                int intervenciones = rs.getInt("intervenciones");
                String fecha=rs.getString("ultima_intervencion");

                // Agregar la información del alumno a la lista
                infoAlumno.add("Nombre: " + nombre);
                infoAlumno.add("Intervenciones actuales: " + intervenciones);
                infoAlumno.add("Ultima Intervención: " + fecha);

            }

            stm.close();
            con.close();
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("Ha fallado la conexión: " + e.getMessage());
        }

        return infoAlumno;
    }

    static String resetearIntervenciones(String nombreBD, String nombreTabla) {

        String reinicioIntervenciones = null;

        try {
            // Enlazar con el driver
            Class.forName(DRIVER);

            Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            Statement stm = con.createStatement();

            String updateQuery = "UPDATE " + nombreBD + "." + nombreTabla + " SET intervenciones = 0, ultima_intervencion = '0000-00-00'";
            int filasActualizadas = stm.executeUpdate(updateQuery);

            reinicioIntervenciones = "Se han reiniciado las intervenciones en " + filasActualizadas + " filas";

            stm.close();
            con.close();

        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return reinicioIntervenciones;
    }

    static String alumnoDarAlta(String nombreBD, String nombreTabla, String alumno) {

        String resultado = null;

        try {
            // Enlazar con el driver
            Class.forName(DRIVER);

            Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            Statement stm = con.createStatement();

            String query = "INSERT INTO " + nombreBD + "." + nombreTabla + " (alumno, ultima_intervencion) VALUES (?, ?)";

            try (PreparedStatement pstmt = con.prepareStatement(query)) {
                pstmt.setString(1, alumno);
                pstmt.setString(2,"0000-00-00");
                int filasInsertadas = pstmt.executeUpdate();
                if (filasInsertadas > 0) {
                    resultado = "El alumno " + alumno + " ha sido dado de alta correctamente";
                } else {
                    resultado = "No se pudo dar de alta al alumno " + alumno;
                }
            }

            stm.close();
            con.close();

        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("Ha fallado la conexión: " + e.getMessage());
        }
        return resultado;
    }

    static String alumnoDarBaja(String nombreBD, String nombreTabla, String alumno) {

        String resultado = null;

        try {
            // Enlazar con el driver
            Class.forName(DRIVER);

            Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            Statement stm = con.createStatement();

            String query = "DELETE FROM " + nombreBD + "." + nombreTabla + " WHERE alumno = ?";

            try (PreparedStatement pstmt = con.prepareStatement(query)) {
                pstmt.setString(1, alumno);
                int filasEliminadas = pstmt.executeUpdate();
                if (filasEliminadas > 0) {
                    resultado = "El alumno " + alumno + " ha sido dado de baja correctamente";
                } else {
                    resultado = "No se encontró ningún alumno con el nombre " + alumno;
                }
            }

            stm.close();
            con.close();

        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("Ha fallado la conexión: " + e.getMessage());
        }

        return resultado;
    }
    static List<String> alumnoModificar(String nombreBD, String nombreTabla, String alumno, String nuevasIntervenciones, String nuevoNombre) {

        List<String> resultados = new ArrayList<>();

        try (Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            con.setAutoCommit(false);
            if (!nuevasIntervenciones.equals("0")) {
                try (PreparedStatement pstmt = con.prepareStatement("UPDATE " + nombreBD + "." + nombreTabla + " SET alumno = ?, intervenciones = ?, ultima_intervencion = NOW() WHERE alumno = ?")) {
                    pstmt.setString(1, nuevoNombre);
                    pstmt.setInt(2, Integer.parseInt(nuevasIntervenciones));
                    pstmt.setString(3, alumno);
                    int filasActualizadas = pstmt.executeUpdate();
                    if (filasActualizadas > 0) {
                        con.commit();
                        System.out.println("El alumno " + alumno + " ha sido modificado correctamente");
                    } else {
                        con.rollback();
                        System.out.println("No se encontró ningún alumno con el nombre " + alumno);
                    }
                }
            } else {
                try (PreparedStatement pstmt = con.prepareStatement("UPDATE " + nombreBD + "." + nombreTabla + " SET alumno = ?, intervenciones = ?, ultima_intervencion = NULL WHERE alumno = ?")) {
                    pstmt.setString(1, nuevoNombre);
                    pstmt.setInt(2, Integer.parseInt(nuevasIntervenciones));
                    pstmt.setString(3, alumno);
                    int filasActualizadas = pstmt.executeUpdate();
                    if (filasActualizadas > 0) {
                        con.commit();
                        resultados.add("El alumno " + alumno + " ha sido modificado correctamente");
                        resultados.add("Nuevo nombre: " + nuevoNombre);
                        resultados.add("Intervenciones: " + nuevasIntervenciones);
                    } else {
                        con.rollback();
                        resultados.add("No se encontró ningún alumno con el nombre " + alumno);
                    }
                }
            }
            con.setAutoCommit(true);
        } catch (SQLException e) {
            System.err.println("Ha fallado la conexión: " + e.getMessage());
        }
        return resultados;
    }

    //Este método exporta un archivo XML de una ruta indicada
    //Como parámetro acepta un String con la ruta desde dónde hará la copia y otro String con dónde escribir esa copia
    public static void exportarXML(String rutaLectura, String rutaEscritura) {
        try {
            //Cargamos fichero que vamos a leer
            File file = new File(rutaLectura);

            //Parseamos el fichero al Document
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = null;
            builder = factory.newDocumentBuilder();
            Document document = builder.parse(file);

            //Clases necesarias para la creación del archivo XML
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(document);
            StreamResult result = new StreamResult(new File(rutaEscritura));

            //Transformación, de Document a Fichero.
            transformer.transform(source, result);

        } catch (ParserConfigurationException e) {
            System.out.println("Ha fallado el parseo " + e.getMessage());
        } catch (TransformerException e) {
            System.out.println("Ha fallado la transformación del documento " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Ha fallado la entrada/salida " + e.getMessage());
        } catch (SAXException e) {
            System.out.println("Ha fallado el parseo a XML " + e.getMessage());
        }
    }


    //Este método busca al alumno menos participativo y lo devuelve, sumándole 1 a su participación
    //Como parámetro recibe la ruta del archivo XML del que queremos sacar el estudiante menos participativo
    public static Estudiante seleccionarAlumnoMenosParticipativo(String rutaXML) {
        //Carga una lista y guarda el primer elemento de esta como el alumno menos participativo
        List<Estudiante> listaEstudiantes = null;
        listaEstudiantes = pasarXML_A_Lista(rutaXML);
        Estudiante estudianteMenosParticipativo = listaEstudiantes.get(0);
        //Recorre la lista con un bucle y va cambiando el valor de la variable "estudianteMenosParticipativo" si encuentra que
        //el siguiente alumno de la lista tiene una participación menor que el alumno que está guardado en la variable
        for (int i = 1; i < listaEstudiantes.size(); i++) {
            if (listaEstudiantes.get(i).getParticipacion() < estudianteMenosParticipativo.getParticipacion()) {
                estudianteMenosParticipativo = listaEstudiantes.get(i);
            }
        }
        //Aumenta la participación del alumno que se ha elegido en uno
        estudianteMenosParticipativo.setParticipacion(estudianteMenosParticipativo.getParticipacion() + 1);
        LocalDateTime fechaActual = LocalDateTime.now();
        estudianteMenosParticipativo.setfechaParticipacion(fechaActual);
        //Para guardar los cambios en el XML se importa el archivo
        importarXML(listaEstudiantes, rutaXML);
        //Devuelve el alumno menos participativo
        return estudianteMenosParticipativo;
    }

    static void importarXML(List<Estudiante> listaEstudiantes, String rutaXML) {
    }


    //Este método es usado por el método "seleccionarAlumnoMenosParticipaticvo" para que pueda cargar el archivo XML con las participaciones actualizadas
    // y "resetearParticipaciones" para poner a 0 las diferentes participaciones de los Alumnos
    //Como parámetro recibe una ruta donde se encuentra un archivo XML que pasará a una lista
    public static List<Estudiante> pasarXML_A_Lista(String rutaXML) {
        try {
            //Crea una Lista y un Archivo que cargará el archivo en la ruta indicada
            List<Estudiante> listaestudiantes = new LinkedList<>();
            File file = new File(rutaXML);
            //Parseamos el fichero al Document
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = null;
            builder = factory.newDocumentBuilder();
            Document document = builder.parse(file);
            //Creamos una NodeList que irá guardando los elementos de la lista según el nombre de la etiqueta, en este caso "estudiante"
            NodeList estudiantes = document.getElementsByTagName("estudiante");

            //Con un bucle vamos iterando sobre los distintos nodos del XML e introduciendo los valores de las etiquetas "nombre",
            //"participacion" y "fechaParticipacion" en su correspondiente etiqueta "estudiante"
            for (int i = 0; i < estudiantes.getLength(); i++) {
                Node node = estudiantes.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {

                    Element eElement = (Element) node;
                    Estudiante estudiante = new Estudiante();
                    estudiante.setNombre(eElement.getElementsByTagName("nombre").item(0).getTextContent());
                    estudiante.setParticipacion(Integer.parseInt(eElement.getElementsByTagName("participacion").item(0).getTextContent()));
                    estudiante.setfechaParticipacion(LocalDateTime.parse(eElement.getElementsByTagName("fechaParticipacion").item(0).getTextContent()));

                    //Se va añadiendo cada estudiante con sus atributos ya recibidos en la lista
                    listaestudiantes.add(estudiante);
                }
            }
            //Se devuelve la lista
            return listaestudiantes;
        } catch (ParserConfigurationException e) {
            System.out.println("Ha fallado el parseo " + e.getMessage());
            throw new RuntimeException(e);
        } catch (IOException e) {
            System.out.println("Ha fallado la entrada/salida " + e.getMessage());
            throw new RuntimeException(e);
        } catch (SAXException e) {
            System.out.println("Ha fallado el parseo a XML " + e.getMessage());
            throw new RuntimeException(e);
        }
    }


    //Este método pone a 0 las participaciones de todos los alumnos en un archivo XML
    //Como parámetro recibe la ruta del archivo XMl del que se desea poner las participaciones a 0
    public static void resetearParticipaciones(String rutaParaResetear) {
        //Crea la lista a partir del XML con el método anterior
        List<Estudiante> listaEstudiantes = null;
        listaEstudiantes = pasarXML_A_Lista(rutaParaResetear);
        //Con un bucle vamos poniendo la participación de cada alumno a 0
        for (int i = 0; i < listaEstudiantes.size(); i++){
            listaEstudiantes.get(i).setParticipacion(0);
        }
        //Y finalmente se importa el archivo, así acabamos con el mismo XML pero con todas las participaciones en 0
        importarXML(listaEstudiantes,rutaParaResetear);
        System.out.println("Se han reseteado las participaciones de los Alumnos");
    }

    public static void mostrarAlumnoMasYMenosParticipativo(String rutaXML){
        List<Estudiante> listaEstudiantes = pasarXML_A_Lista(rutaXML);
        Estudiante estudianteMenosParticipativo = listaEstudiantes.get(0);
        for (int i = 1; i < listaEstudiantes.size(); i++) {
            if (listaEstudiantes.get(i).getParticipacion() < estudianteMenosParticipativo.getParticipacion()) {
                estudianteMenosParticipativo = listaEstudiantes.get(i);
            }
        }
        System.out.println("El Alumno menos participativo es: " + estudianteMenosParticipativo.getNombre() + " con una participación de " + estudianteMenosParticipativo.getParticipacion());

        Estudiante estudianteMasParticipativo = listaEstudiantes.get(0);
        for (int i = 1; i < listaEstudiantes.size(); i++) {
            if (listaEstudiantes.get(i).getParticipacion() > estudianteMasParticipativo.getParticipacion()) {
                estudianteMasParticipativo = listaEstudiantes.get(i);
            }
        }
        System.out.println("El Alumno menos participativo es: " + estudianteMasParticipativo.getNombre() + " con una participación de " + estudianteMasParticipativo.getParticipacion());

    }

    public static void mostrarAlumnosPorDebajoDeLaMedia(String rutaArchivoXML){
        List<Estudiante> listaEstudiantes = pasarXML_A_Lista(rutaArchivoXML);
        int mediaParticipacion = 0;
        for (int i = 0; i < listaEstudiantes.size(); i++){
            mediaParticipacion += listaEstudiantes.get(i).getParticipacion();
        }
        mediaParticipacion = mediaParticipacion  / listaEstudiantes.size();
        System.out.println("La media de partipación es: " + mediaParticipacion);
        for (int i = 0 ; i < listaEstudiantes.size(); i++){
            if ( listaEstudiantes.get(i).getParticipacion() < mediaParticipacion){
                System.out.println(listaEstudiantes.get(i));
            }
        }
    }


    public static void mostrarAlumnosPorParticipacion(String rutaXML, int valorParticipaciones) {

        //Pasa el XML a una lista
        List<Estudiante> listaEstudiantes = pasarXML_A_Lista(rutaXML);
        //Listas para ordenar a los alumnos donde corresponda según el valor dado.
        List<Estudiante> alumnosValorMayor = new ArrayList<>();
        List<Estudiante> alumnosValorMenor = new ArrayList<>();
        List<Estudiante> alumnosValorIgual = new ArrayList<>();

        //Operación que recorre la lista de alumnos y los ordena.
        for (Estudiante estudiante : listaEstudiantes) {
            int participacion = estudiante.getParticipacion();
            if (participacion < valorParticipaciones) {
                alumnosValorMenor.add(estudiante);
            } else if (participacion > valorParticipaciones) {
                alumnosValorMayor.add(estudiante);
            } else {
                alumnosValorIgual.add(estudiante);
            }
        }

        //Se imprimen los resultados con un submenú.
        boolean subMenuActivo = true;
        Scanner scan = new Scanner(System.in);
        while (subMenuActivo) {
            System.out.println("Escoja una opción:");
            System.out.println("1- Alumnos con participaciones mayor a " + valorParticipaciones);
            System.out.println("2- Alumnos con participaciones menor a " + valorParticipaciones);
            System.out.println("3- Alumnos con participaciones igual a " + valorParticipaciones);
            System.out.println("4. Volver al menú principal");
            int opcionSubMenu = scan.nextInt();

            switch (opcionSubMenu) {
                case 1:
                    System.out.println("•Lista de alumnos con participaciones mayor a " + valorParticipaciones + ":");
                    for (Estudiante estudiante : alumnosValorMayor) {
                        System.out.println("- Nombre: " + estudiante.getNombre() + "\n- Participaciones: "
                                + estudiante.getParticipacion());
                    }
                    break;
                case 2:
                    System.out.println("•Lista de alumnos con participaciones menor a " + valorParticipaciones + ":");
                    for (Estudiante estudiante : alumnosValorMenor) {
                        System.out.println("- Nombre: " + estudiante.getNombre() + "\n- Participaciones: "
                                + estudiante.getParticipacion());
                    }
                    break;
                case 3:
                    System.out.println("•Lista de alumnos con participaciones igual a " + valorParticipaciones + ":");
                    for (Estudiante estudiante : alumnosValorIgual) {
                        System.out.println("-Nombre: " + estudiante.getNombre() + "\n- Participaciones: "
                                + estudiante.getParticipacion());
                    }
                    break;
                case 4:
                    subMenuActivo = false;
                    break;
                default:
                    System.out.println("Opción no válida");
                    break;
            }
        }
    }

    public static void ultimoAlumnoParticipar(String rutaXML) {
        //Pasa el XML a una lista
        List<Estudiante> listaEstudiantes = pasarXML_A_Lista(rutaXML);
        //Variables para almacenar la fecha y el nombre del último alumno
        LocalDateTime ultimaFechaParticipacion = LocalDateTime.MIN;
        Estudiante ultimoAlumno = null;

        //Bucle que recorre el XML para buscar un candidato
        //Compara la fecha y hora de los alumnos y guarda la más actual
        for (Estudiante estudiante : listaEstudiantes) {
            LocalDateTime fechaParticipacion = estudiante.getfechaParticipacion();
            if (fechaParticipacion != null && fechaParticipacion.isAfter(ultimaFechaParticipacion)) {
                ultimaFechaParticipacion = fechaParticipacion;
                ultimoAlumno = estudiante;
            }
        }

        //Imprime el resultado
        if (ultimoAlumno != null) {
            System.out.println("·Último alumno en participar:" + '\n' + "-Nombre: " + ultimoAlumno.getNombre() + '\n' +
                    "-Fecha: " + ultimoAlumno.getfechaParticipacion());

        } else {
            System.out.println("No existen alumnos. Registre alguno e inténtelo de nuevo.");
        }
    }


    public static void mostrarInformacionAlumno(String rutaConsulta, String nombreAlumno) {
        List<Estudiante> listaEstudiantes = pasarXML_A_Lista(rutaConsulta);
        for (Estudiante estudiante : listaEstudiantes) {
            if (estudiante.getNombre().equals(nombreAlumno)) {
                System.out.println("Información del alumno:");
                System.out.println("- Nombre: " + estudiante.getNombre());
                System.out.println("- Participaciones: " + estudiante.getParticipacion());
                System.out.println("- Fecha de la última participación: " + estudiante.getfechaParticipacion());
                return;
            }
        }
        System.out.println("No se encontró al alumno " + nombreAlumno + " en la lista.");
    }

    // Método para dar de alta, baja y modificar alumnos
    public static void gestionarAlumnos(String rutaGestion, String opcionGestion, String nombre) {
        List<Estudiante> listaEstudiantes = pasarXML_A_Lista(rutaGestion);

        switch (opcionGestion) {
            case "alta":
                // Crear un nuevo estudiante y agregarlo a la lista de estudiantes
                Estudiante nuevoEstudiante = new Estudiante(nombre, 0, LocalDateTime.now());
                listaEstudiantes.add(nuevoEstudiante);
                importarXML(listaEstudiantes, rutaGestion);
                System.out.println("Alumno dado de alta correctamente.");
                break;
            case "baja":
                // Buscar el estudiante por nombre y eliminarlo de la lista de estudiantes
                Estudiante estudianteEliminar = null;
                for (Estudiante estudiante : listaEstudiantes) {
                    if (estudiante.getNombre().equals(nombre)) {
                        estudianteEliminar = estudiante;
                        break;
                    }
                }
                if (estudianteEliminar != null) {
                    listaEstudiantes.remove(estudianteEliminar);
                    importarXML(listaEstudiantes, rutaGestion);
                    System.out.println("Alumno dado de baja correctamente.");
                } else {
                    System.out.println("No se encontró ningún alumno con ese nombre.");
                }
                break;
            case "modificar":
                // Buscar el estudiante por nombre y modificar su participación
                Estudiante estudianteModificar = null;
                for (Estudiante estudiante : listaEstudiantes) {
                    if (estudiante.getNombre().equals(nombre)) {
                        estudianteModificar = estudiante;
                        break;
                    }
                }
                if (estudianteModificar != null) {
                    Scanner scan = new Scanner(System.in);
                    System.out.print("Introduce la nueva participación del alumno: ");
                    int nuevaParticipacion = scan.nextInt();
                    estudianteModificar.setParticipacion(nuevaParticipacion);
                    importarXML(listaEstudiantes, rutaGestion);
                    System.out.println("Participación del alumno modificada correctamente.");
                } else {
                    System.out.println("No se encontró ningún alumno con ese nombre.");
                }
                break;
            default:
                System.out.println("Opción de gestión no válida.");
        }
    }
}
