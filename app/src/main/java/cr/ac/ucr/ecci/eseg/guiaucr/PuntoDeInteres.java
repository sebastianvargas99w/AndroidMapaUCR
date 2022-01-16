package cr.ac.ucr.ecci.eseg.guiaucr;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class PuntoDeInteres {
    public static final String FACULTAD = "Facultad";
    public static final String FOTOCOPIADORA = "Fotocopiadora";
    public static final String SODA = "Soda";
    public static final String PARADA = "Parada";
    public static final String PERSONALIZADO = "Personalizado";
    private String nombre = "";
    private double coordenadaX = 0.0;
    private double coordenadaY = 0.0;
    private String descripcion = "";
    private String tipo;
    private String telefono;
    private String pagina;
    //cómo agregar una imagen a SQLite
    private byte[] foto;

    public  PuntoDeInteres() {};

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getCoordenadaX() {
        return coordenadaX;
    }

    public void setCoordenadaX(double coordenadaX) {
        this.coordenadaX = coordenadaX;
    }

    public double getCoordenadaY() {
        return coordenadaY;
    }

    public void setCoordenadaY(double coordenadaY) {
        this.coordenadaY = coordenadaY;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public byte[] getFoto() { return foto; }

    public void setFoto(byte[] foto) {
        this.foto = foto;
    }

    public String getTelefono() { return telefono; }

    public void setTelefono(String telefono) { this.telefono = telefono; }

    public String getPagina() { return pagina; }

    public void setPagina(String pagina) { this.pagina = pagina; }

    public PuntoDeInteres(String nombre, double coordenadaX, double coordenadaY, String descripcion , String tipo, String telefono, String pagina, byte[] foto) {
        this.nombre = nombre;
        this.coordenadaX = coordenadaX;
        this.coordenadaY = coordenadaY;
        this.descripcion = descripcion;
        this.tipo = tipo;
        this.telefono = telefono;
        this.pagina = pagina;
        this.foto = foto;
    }

    public static long insertar(Context context, PuntoDeInteres puntoDeInteres){
        DataBaseHelper dataBaseHelper = new DataBaseHelper(context);
        SQLiteDatabase db = dataBaseHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DataBaseContract.DataBaseEntry.COLUMN_NAME_NOMBRE , puntoDeInteres.getNombre());
        values.put(DataBaseContract.DataBaseEntry.COLUMN_NAME_COORDENADAX , puntoDeInteres.getCoordenadaX());
        values.put(DataBaseContract.DataBaseEntry.COLUMN_NAME_COORDENADAY , puntoDeInteres.getCoordenadaY());
        values.put(DataBaseContract.DataBaseEntry.COLUMN_NAME_DESCRIPCION , puntoDeInteres.getDescripcion());
        values.put(DataBaseContract.DataBaseEntry.COLUMN_NAME_TIPO , puntoDeInteres.getTipo());
        values.put(DataBaseContract.DataBaseEntry.COLUMN_NAME_TELEFONO , puntoDeInteres.getTelefono());
        values.put(DataBaseContract.DataBaseEntry.COLUMN_NAME_PAGINA , puntoDeInteres.getPagina());
        values.put(DataBaseContract.DataBaseEntry.COLUMN_NAME_FOTO , puntoDeInteres.getFoto());
        return db.insert(DataBaseContract.DataBaseEntry.TABLE_NAME_PUNTO_DE_INTERES, null, values);
    }

    public static List<PuntoDeInteres>  leer (Context context){
        List<PuntoDeInteres> resultados = new ArrayList<PuntoDeInteres>();
        DataBaseHelper dataBaseHelper = new DataBaseHelper(context);
        SQLiteDatabase db = dataBaseHelper.getReadableDatabase();
        //Definimos las columnas que deseamos solicitar.
        String[] projection = {
                DataBaseContract.DataBaseEntry.COLUMN_NAME_NOMBRE,
                DataBaseContract.DataBaseEntry.COLUMN_NAME_COORDENADAX,
                DataBaseContract.DataBaseEntry.COLUMN_NAME_COORDENADAY,
                DataBaseContract.DataBaseEntry.COLUMN_NAME_DESCRIPCION,
                DataBaseContract.DataBaseEntry.COLUMN_NAME_TIPO,
                DataBaseContract.DataBaseEntry.COLUMN_NAME_TELEFONO,
                DataBaseContract.DataBaseEntry.COLUMN_NAME_PAGINA,
                DataBaseContract.DataBaseEntry.COLUMN_NAME_FOTO
        };
        Cursor cursor = db.query(
                DataBaseContract.DataBaseEntry.TABLE_NAME_PUNTO_DE_INTERES,
                projection,
                null,
                null,
                null,
                null,
                null
                );
        while (cursor.moveToNext() && cursor.getCount() > 0) {
                PuntoDeInteres punto = new PuntoDeInteres(
                        cursor.getString(cursor.getColumnIndexOrThrow(DataBaseContract.DataBaseEntry.COLUMN_NAME_NOMBRE)),
                        cursor.getDouble(cursor.getColumnIndexOrThrow(DataBaseContract.DataBaseEntry.COLUMN_NAME_COORDENADAX)),
                        cursor.getDouble(cursor.getColumnIndexOrThrow(DataBaseContract.DataBaseEntry.COLUMN_NAME_COORDENADAY)),
                        cursor.getString(cursor.getColumnIndexOrThrow(DataBaseContract.DataBaseEntry.COLUMN_NAME_DESCRIPCION)),
                        cursor.getString(cursor.getColumnIndexOrThrow(DataBaseContract.DataBaseEntry.COLUMN_NAME_TIPO)),
                        cursor.getString(cursor.getColumnIndexOrThrow(DataBaseContract.DataBaseEntry.COLUMN_NAME_TELEFONO)),
                        cursor.getString(cursor.getColumnIndexOrThrow(DataBaseContract.DataBaseEntry.COLUMN_NAME_PAGINA)),
                        cursor.getBlob(cursor.getColumnIndexOrThrow(DataBaseContract.DataBaseEntry.COLUMN_NAME_FOTO))
                );
                resultados.add(punto);
        }
        try {
            cursor.close();
            db.close();
        }
        catch(Exception e) {}
        return resultados;
    }
    public static PuntoDeInteres leerPuntoDeInteresPorNombre(Context context, String nombre){
        List<PuntoDeInteres> resultados = new ArrayList<PuntoDeInteres>();
        DataBaseHelper dataBaseHelper = new DataBaseHelper(context);
        SQLiteDatabase db = dataBaseHelper.getReadableDatabase();
        //Definimos las columnas que deseamos solicitar.
        String[] projection = {
                DataBaseContract.DataBaseEntry.COLUMN_NAME_NOMBRE,
                DataBaseContract.DataBaseEntry.COLUMN_NAME_COORDENADAX,
                DataBaseContract.DataBaseEntry.COLUMN_NAME_COORDENADAY,
                DataBaseContract.DataBaseEntry.COLUMN_NAME_DESCRIPCION,
                DataBaseContract.DataBaseEntry.COLUMN_NAME_TIPO,
                DataBaseContract.DataBaseEntry.COLUMN_NAME_TELEFONO,
                DataBaseContract.DataBaseEntry.COLUMN_NAME_PAGINA,
                DataBaseContract.DataBaseEntry.COLUMN_NAME_FOTO
        };
        String selection = DataBaseContract.DataBaseEntry.COLUMN_NAME_NOMBRE + " = ?";
        String[] selectionArgs = {nombre};
        Cursor cursor = db.query(
                DataBaseContract.DataBaseEntry.TABLE_NAME_PUNTO_DE_INTERES,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );
        if(cursor.moveToFirst() && cursor.getCount() > 0){
            return new PuntoDeInteres(
            cursor.getString(cursor.getColumnIndexOrThrow(DataBaseContract.DataBaseEntry.COLUMN_NAME_NOMBRE)),
            cursor.getDouble(cursor.getColumnIndexOrThrow(DataBaseContract.DataBaseEntry.COLUMN_NAME_COORDENADAX)),
            cursor.getDouble(cursor.getColumnIndexOrThrow(DataBaseContract.DataBaseEntry.COLUMN_NAME_COORDENADAY)),
            cursor.getString(cursor.getColumnIndexOrThrow(DataBaseContract.DataBaseEntry.COLUMN_NAME_DESCRIPCION)),
            cursor.getString(cursor.getColumnIndexOrThrow(DataBaseContract.DataBaseEntry.COLUMN_NAME_TIPO)),
            cursor.getString(cursor.getColumnIndexOrThrow(DataBaseContract.DataBaseEntry.COLUMN_NAME_TELEFONO)),
            cursor.getString(cursor.getColumnIndexOrThrow(DataBaseContract.DataBaseEntry.COLUMN_NAME_PAGINA)),
            cursor.getBlob(cursor.getColumnIndexOrThrow(DataBaseContract.DataBaseEntry.COLUMN_NAME_FOTO))
            );
        }else{
            try{
                cursor.close();
            }
            catch(Exception e){}
            return null;
        }
    }
    public void eliminar(Context context, String nombre){
        DataBaseHelper dataBaseHelper = new DataBaseHelper(context);
        //Obtenemos una instancia en modo escritura de la base de datos.
        SQLiteDatabase db = dataBaseHelper.getWritableDatabase();
        //
        String selection = DataBaseContract.DataBaseEntry.COLUMN_NAME_NOMBRE + "LIKE ?";
        String[] selectionArgs = {nombre};
        //
        db.delete(DataBaseContract.DataBaseEntry.TABLE_NAME_PUNTO_DE_INTERES, selection, selectionArgs);
    }
    public static void  eliminarDatos(Context context){
        DataBaseHelper dataBaseHelper = new DataBaseHelper(context);
        //Obtenemos una instancia en modo escritura de la base de datos.
        SQLiteDatabase db = dataBaseHelper.getWritableDatabase();
        db.delete(DataBaseContract.DataBaseEntry.TABLE_NAME_PUNTO_DE_INTERES,null ,null );
    }

    public static String leerDescripcion(Context context, String nombre) {
        DataBaseHelper dataBaseHelper = new DataBaseHelper(context);
        SQLiteDatabase db = dataBaseHelper.getReadableDatabase();
        String[] projection = {
                DataBaseContract.DataBaseEntry.COLUMN_NAME_NOMBRE,
                DataBaseContract.DataBaseEntry.COLUMN_NAME_DESCRIPCION,
        };
        Cursor cursor = db.query(
                DataBaseContract.DataBaseEntry.TABLE_NAME_PUNTO_DE_INTERES,
                projection,
                null,
                null,
                null,
                null,
                null
        );
        while (cursor.moveToNext() && cursor.getCount() > 0) {
            if (cursor.getString(cursor.getColumnIndexOrThrow(DataBaseContract.DataBaseEntry.COLUMN_NAME_NOMBRE)).equals(nombre)) {
                return cursor.getString(cursor.getColumnIndexOrThrow(DataBaseContract.DataBaseEntry.COLUMN_NAME_DESCRIPCION));
            }
        }
        return "";
    }
}
