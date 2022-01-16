package cr.ac.ucr.ecci.eseg.guiaucr;
import android.provider.BaseColumns;
import android.provider.ContactsContract;

public class DataBaseContract {

    private DataBaseContract(){}
    //Definimos la clase interna que define las tablas y las columnas.
    public static class DataBaseEntry implements BaseColumns{
        //Clase PuntoDeInteres
        public static final String TABLE_NAME_PUNTO_DE_INTERES = "PuntoDeInteres";
        //Atributos
        public static final String COLUMN_NAME_NOMBRE = "nombre";
        public static final String COLUMN_NAME_COORDENADAX = "coordenadaX";
        public static final String COLUMN_NAME_COORDENADAY = "coordenadaY";
        public static final String COLUMN_NAME_DESCRIPCION = "descripcion";
        public static final String COLUMN_NAME_TELEFONO = "telefono";
        public static final String COLUMN_NAME_PAGINA = "pagina";
        public static final String COLUMN_NAME_TIPO = "tipo";
        public static final String COLUMN_NAME_FOTO = "foto";

        // Tabla de marcadores favoritos
        public static final String TABLE_NAME_MARCADORES_FAVORITOS = "MarcadoresFavoritos";

        public static final String COLUMN_NAME_MARCADORES_FAVORITOS_NOMBRE = "marcadores_favoritos_nombre";
        public static final String COLUMN_NAME_MARCADORES_FAVORITOS_COORDENADAX = "marcadores_favoritos_coordenadaX";
        public static final String COLUMN_NAME_MARCADORES_FAVORITOS_COORDENADAY = "marcadores_favoritos_coordenadaY";
        public static final String COLUMN_NAME_MARCADORES_FAVORITOS_USUARIO = "marcadores_favoritos_usuario";
        public static final String COLUMN_NAME_MARCADORES_FAVORITOS_DESCRIPCION = "marcadores_favoritos_descripcion";
        public static final String COLUMN_NAME_MARCADORES_FAVORITOS_TIPO_DE_ICONO = "marcadores_favoritos_tipo_de_icono";
        public static final String COLUMN_NAME_MARCADORES_FAVORITOS_NOMBRE_DEL_PUNTO = "marcadores_favoritos_nombre_del_punto";
    }
    //Mapeos de tipos
    // Tipos de dato para mapeo entre SQL y Java
    private static final String TEXT_TYPE = " TEXT ";
    private static final String INTEGER_TYPE = " INTEGER ";
    private static final String REAL_TYPE = " REAL ";
    private static final String BLOB_TYPE = " BLOB ";
    private static final String COMMA_SEP = ",";

    //Creacion de los comandos sql
    public static final String SQL_CREATE_PUNTO_DE_INTERES =
            "CREATE TABLE " + DataBaseEntry.TABLE_NAME_PUNTO_DE_INTERES + " (" +
                    DataBaseEntry.COLUMN_NAME_NOMBRE + TEXT_TYPE + "PRIMARY_KEY," +
                    DataBaseEntry.COLUMN_NAME_COORDENADAX + REAL_TYPE + COMMA_SEP +
                    DataBaseEntry.COLUMN_NAME_COORDENADAY + REAL_TYPE + COMMA_SEP +
                    DataBaseEntry.COLUMN_NAME_DESCRIPCION + TEXT_TYPE + COMMA_SEP +
                    DataBaseEntry.COLUMN_NAME_TIPO + TEXT_TYPE + COMMA_SEP +
                    DataBaseEntry.COLUMN_NAME_TELEFONO + TEXT_TYPE + COMMA_SEP +
                    DataBaseEntry.COLUMN_NAME_PAGINA + TEXT_TYPE + COMMA_SEP +
                    DataBaseEntry.COLUMN_NAME_FOTO + BLOB_TYPE + " )";
    public static final String SQL_DELETE_PUNTO_DE_INTERES =
            "DROP TABLE IF EXIST " + DataBaseEntry.TABLE_NAME_PUNTO_DE_INTERES;

    public static final String SQL_CREATE_MARCADORES_FAVORITOS =
            "CREATE TABLE " + DataBaseEntry.TABLE_NAME_MARCADORES_FAVORITOS + " (" +
                    DataBaseEntry._ID + INTEGER_TYPE + "PRIMARY KEY AUTOINCREMENT NOT NULL," +
                    DataBaseEntry.COLUMN_NAME_MARCADORES_FAVORITOS_NOMBRE + TEXT_TYPE + COMMA_SEP +
                    DataBaseEntry.COLUMN_NAME_MARCADORES_FAVORITOS_COORDENADAX + REAL_TYPE + COMMA_SEP +
                    DataBaseEntry.COLUMN_NAME_MARCADORES_FAVORITOS_COORDENADAY + REAL_TYPE + COMMA_SEP +
                    DataBaseEntry.COLUMN_NAME_MARCADORES_FAVORITOS_USUARIO + INTEGER_TYPE + COMMA_SEP +
                    DataBaseEntry.COLUMN_NAME_MARCADORES_FAVORITOS_DESCRIPCION + TEXT_TYPE + COMMA_SEP +
                    DataBaseEntry.COLUMN_NAME_MARCADORES_FAVORITOS_TIPO_DE_ICONO + TEXT_TYPE + COMMA_SEP +
                    DataBaseEntry.COLUMN_NAME_MARCADORES_FAVORITOS_NOMBRE_DEL_PUNTO + TEXT_TYPE + " )";
    public static final String SQL_DELETE_MARCADORES_FAVORITOS =
            "DROP TABLE IF EXIST " + DataBaseEntry.TABLE_NAME_MARCADORES_FAVORITOS;
}