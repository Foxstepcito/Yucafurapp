import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class GeneradorDeCodigosComplejos {
    private static final String CARACTERES_PERMITIDOS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%";

    public static void main(String[] args) {
        // Conectar a la base de datos
        Connection conexion = null;

        try{
            String url = "jdbc:sqlite:basededatos/database.sqlite";
            conexion = DriverManager.getConnection(url);
        } catch (SQLException e){
            System.err.println("Error al conectar a la base de datos");
        } finally {
            try {
                if (conexion != null) {
                    conexion.close();
                }
            } catch (SQLException e) {
                System.err.println("Error al cerrar la conexión");
            }
        };

        // Crear un HashMap para almacenar los usuarios y sus códigos
        HashMap<String, String> codigosUsuarios = new HashMap<>();

        // Datos de ejemplo de usuarios con nombres y usuarios de Telegram
        String[][] datosUsuarios = {
            {"gabriela padron", "@gabyusagui"},
            {"david mauricio", "@yeikancient"},
            {"julian ivan", "@foxstepcito"}
        };

        // Generar un código aleatorio para cada usuario y almacenarlo en el HashMap
        for (String[] usuarioInfo : datosUsuarios) {
            String nombreCompleto = usuarioInfo[0];
            String usuarioTelegram = usuarioInfo[1];
            String codigoVinculacion = generarCodigo(nombreCompleto);
            codigosUsuarios.put(usuarioTelegram, codigoVinculacion);
        }

        // Imprimir los usuarios y sus códigos
        for (Map.Entry<String, String> entrada : codigosUsuarios.entrySet()) {
            System.out.println("Usuario de Telegram: " + entrada.getKey() + " - Codigo de vinculacion: " + entrada.getValue());
        }
    }

    // Método para generar un código de vinculación basado en el nombre del usuario
    public static String generarCodigo(String nombreCompleto) {
        // Separar el nombre en palabras y obtener las iniciales
        String[] palabras = nombreCompleto.split(" ");
        String iniciales = "";
        for (String palabra : palabras) {
            if (!palabra.isEmpty()) {
                iniciales += palabra.toUpperCase().charAt(0);
            }
        }

        // Generar un código aleatorio de 8 caracteres que incluya letras y caracteres especiales
        Random random = new Random();
        StringBuilder codigoAleatorio = new StringBuilder(iniciales);
        for (int i = 0; i < 8; i++) {
            int indiceAleatorio = random.nextInt(CARACTERES_PERMITIDOS.length());
            codigoAleatorio.append(CARACTERES_PERMITIDOS.charAt(indiceAleatorio));
        }

        return codigoAleatorio.toString();
    }
}
