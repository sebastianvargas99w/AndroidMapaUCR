package cr.ac.ucr.ecci.eseg.guiaucr;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class MarcadorFavorito implements Parcelable {

    public static final List<MarcadorFavorito> favoritos_public = new ArrayList<MarcadorFavorito>(){
        {
            add(new MarcadorFavorito("Favorito X", 9.938310, -84.051562, 1, "Descripcion X", "id-icono-1", "test1"));
            add(new MarcadorFavorito("Favorito Y", 9.938638, -84.051358, 1, "Descripcion Y", "id-icono-2", "test2"));
            add(new MarcadorFavorito("Favorito Z", 9.935853, -84.048872, 1, "Descripcion Y", "id-icono-3", "test3"));
            add(new MarcadorFavorito("Favorito W", 9.936003, -84.051924, 1, "Descripcion W", "id-icono-4", "test4"));
        }
    };

    private int id;
    private String nombre;
    private Double longitud;
    private Double latitud;
    private int usuario;
    private String descripcion;
    private String tipoIcono;
    private String nombrePunto;

    public MarcadorFavorito(String nombre, Double longitud, Double latitud, int usuario,
                            String descripcion, String tipoIcono, String nombrePunto) {
        this.nombre = nombre;
        this.longitud = longitud;
        this.latitud = latitud;
        this.usuario = usuario;
        this.descripcion = descripcion;
        this.tipoIcono = tipoIcono;
        this.nombrePunto = nombrePunto;
    }

    public MarcadorFavorito(int id, String nombre, Double longitud, Double latitud, int usuario,
                            String descripcion, String tipoIcono, String nombrePunto) {
        this.id = id;
        this.nombre = nombre;
        this.longitud = longitud;
        this.latitud = latitud;
        this.usuario = usuario;
        this.descripcion = descripcion;
        this.tipoIcono = tipoIcono;
        this.nombrePunto = nombrePunto;
    }

    protected MarcadorFavorito(Parcel in) {
        id = in.readInt();
        nombre = in.readString();
        if (in.readByte() == 0) {
            longitud = null;
        } else {
            longitud = in.readDouble();
        }
        if (in.readByte() == 0) {
            latitud = null;
        } else {
            latitud = in.readDouble();
        }
        usuario = in.readInt();
        descripcion = in.readString();
        tipoIcono = in.readString();
        nombrePunto = in.readString();
    }

    public static final Creator<MarcadorFavorito> CREATOR = new Creator<MarcadorFavorito>() {
        @Override
        public MarcadorFavorito createFromParcel(Parcel in) {
            return new MarcadorFavorito(in);
        }

        @Override
        public MarcadorFavorito[] newArray(int size) {
            return new MarcadorFavorito[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Double getLongitud() {
        return longitud;
    }

    public void setLongitud(Double longitud) {
        this.longitud = longitud;
    }

    public Double getLatitud() {
        return latitud;
    }

    public void setLatitud(Double latitud) {
        this.latitud = latitud;
    }

    public int getUsuario() {
        return usuario;
    }

    public void setUsuario(int usuario) {
        this.usuario = usuario;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getTipoIcono() {
        return tipoIcono;
    }

    public void setTipoIcono(String tipoIcono) {
        this.tipoIcono = tipoIcono;
    }

    public String getNombrePunto() {
        return nombrePunto;
    }

    public void setNombrePunto(String nombrePunto) {
        this.nombrePunto = nombrePunto;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(nombre);
        if (longitud == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeDouble(longitud);
        }
        if (latitud == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeDouble(latitud);
        }
        parcel.writeInt(usuario);
        parcel.writeString(descripcion);
        parcel.writeString(tipoIcono);
        parcel.writeString(nombrePunto);
    }

    public static long insertar(Context context, MarcadorFavorito marcadorFavorito) {

        DataBaseHelper dataBaseHelper = new DataBaseHelper(context);

        SQLiteDatabase db = dataBaseHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DataBaseContract.DataBaseEntry.COLUMN_NAME_MARCADORES_FAVORITOS_NOMBRE, marcadorFavorito.getNombre());
        values.put(DataBaseContract.DataBaseEntry.COLUMN_NAME_MARCADORES_FAVORITOS_COORDENADAX, marcadorFavorito.getLatitud());
        values.put(DataBaseContract.DataBaseEntry.COLUMN_NAME_MARCADORES_FAVORITOS_COORDENADAY, marcadorFavorito.getLongitud());
        values.put(DataBaseContract.DataBaseEntry.COLUMN_NAME_MARCADORES_FAVORITOS_USUARIO, marcadorFavorito.getUsuario());
        values.put(DataBaseContract.DataBaseEntry.COLUMN_NAME_MARCADORES_FAVORITOS_DESCRIPCION, marcadorFavorito.getDescripcion());
        values.put(DataBaseContract.DataBaseEntry.COLUMN_NAME_MARCADORES_FAVORITOS_TIPO_DE_ICONO, marcadorFavorito.getTipoIcono());

        return db.insert(DataBaseContract.DataBaseEntry.TABLE_NAME_MARCADORES_FAVORITOS, null,
                values);
    }

    public static List<MarcadorFavorito> leer (Context context){
        List<MarcadorFavorito> resultados = new ArrayList<MarcadorFavorito>();

        DataBaseHelper dataBaseHelper = new DataBaseHelper(context);
        SQLiteDatabase db = dataBaseHelper.getReadableDatabase();

        String[] projection = {
                DataBaseContract.DataBaseEntry._ID,
                DataBaseContract.DataBaseEntry.COLUMN_NAME_MARCADORES_FAVORITOS_NOMBRE,
                DataBaseContract.DataBaseEntry.COLUMN_NAME_MARCADORES_FAVORITOS_COORDENADAX,
                DataBaseContract.DataBaseEntry.COLUMN_NAME_MARCADORES_FAVORITOS_COORDENADAY,
                DataBaseContract.DataBaseEntry.COLUMN_NAME_MARCADORES_FAVORITOS_USUARIO,
                DataBaseContract.DataBaseEntry.COLUMN_NAME_MARCADORES_FAVORITOS_DESCRIPCION,
                DataBaseContract.DataBaseEntry.COLUMN_NAME_MARCADORES_FAVORITOS_TIPO_DE_ICONO,
                DataBaseContract.DataBaseEntry.COLUMN_NAME_MARCADORES_FAVORITOS_NOMBRE_DEL_PUNTO
        };

        Cursor cursor = db.query(
                DataBaseContract.DataBaseEntry.TABLE_NAME_MARCADORES_FAVORITOS,
                projection,
                null,
                null,
                null,
                null,
                null
        );

        while (cursor.moveToNext() && cursor.getCount() > 0) {
            MarcadorFavorito persona = new MarcadorFavorito(
                    cursor.getInt(cursor.getColumnIndexOrThrow(DataBaseContract.DataBaseEntry._ID)),
                    cursor.getString(cursor.getColumnIndexOrThrow(DataBaseContract.DataBaseEntry.COLUMN_NAME_MARCADORES_FAVORITOS_NOMBRE)),
                    cursor.getDouble(cursor.getColumnIndexOrThrow(DataBaseContract.DataBaseEntry.COLUMN_NAME_MARCADORES_FAVORITOS_COORDENADAX)),
                    cursor.getDouble(cursor.getColumnIndexOrThrow(DataBaseContract.DataBaseEntry.COLUMN_NAME_MARCADORES_FAVORITOS_COORDENADAY)),
                    cursor.getInt(cursor.getColumnIndexOrThrow(DataBaseContract.DataBaseEntry.COLUMN_NAME_MARCADORES_FAVORITOS_USUARIO)),
                    cursor.getString(cursor.getColumnIndexOrThrow(DataBaseContract.DataBaseEntry.COLUMN_NAME_MARCADORES_FAVORITOS_DESCRIPCION)),
                    cursor.getString(cursor.getColumnIndexOrThrow(DataBaseContract.DataBaseEntry.COLUMN_NAME_MARCADORES_FAVORITOS_TIPO_DE_ICONO)),
                    cursor.getString(cursor.getColumnIndexOrThrow(DataBaseContract.DataBaseEntry.COLUMN_NAME_MARCADORES_FAVORITOS_NOMBRE_DEL_PUNTO))
            );
            resultados.add(persona);
        }
        try {
            cursor.close();
            db.close();
        }
        catch(Exception e) {}
        return resultados;
    }

    public static MarcadorFavorito leerPorId (Context context, String id){

        DataBaseHelper dataBaseHelper = new DataBaseHelper(context);
        SQLiteDatabase db = dataBaseHelper.getReadableDatabase();

        String[] projection = {
                DataBaseContract.DataBaseEntry._ID,
                DataBaseContract.DataBaseEntry.COLUMN_NAME_MARCADORES_FAVORITOS_NOMBRE,
                DataBaseContract.DataBaseEntry.COLUMN_NAME_MARCADORES_FAVORITOS_COORDENADAX,
                DataBaseContract.DataBaseEntry.COLUMN_NAME_MARCADORES_FAVORITOS_COORDENADAY,
                DataBaseContract.DataBaseEntry.COLUMN_NAME_MARCADORES_FAVORITOS_USUARIO,
                DataBaseContract.DataBaseEntry.COLUMN_NAME_MARCADORES_FAVORITOS_DESCRIPCION,
                DataBaseContract.DataBaseEntry.COLUMN_NAME_MARCADORES_FAVORITOS_TIPO_DE_ICONO,
                DataBaseContract.DataBaseEntry.COLUMN_NAME_MARCADORES_FAVORITOS_NOMBRE_DEL_PUNTO
        };

        String selection = DataBaseContract.DataBaseEntry._ID + " = ?";
        String[] selectionArgs = { id };

        Cursor cursor = db.query(
                DataBaseContract.DataBaseEntry.TABLE_NAME_MARCADORES_FAVORITOS,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        if(cursor.moveToFirst() && cursor.getCount() > 0) {
            return new MarcadorFavorito(
                    cursor.getInt(cursor.getColumnIndexOrThrow(DataBaseContract.DataBaseEntry._ID)),
                    cursor.getString(cursor.getColumnIndexOrThrow(DataBaseContract.DataBaseEntry.COLUMN_NAME_MARCADORES_FAVORITOS_NOMBRE)),
                    cursor.getDouble(cursor.getColumnIndexOrThrow(DataBaseContract.DataBaseEntry.COLUMN_NAME_MARCADORES_FAVORITOS_COORDENADAX)),
                    cursor.getDouble(cursor.getColumnIndexOrThrow(DataBaseContract.DataBaseEntry.COLUMN_NAME_MARCADORES_FAVORITOS_COORDENADAY)),
                    cursor.getInt(cursor.getColumnIndexOrThrow(DataBaseContract.DataBaseEntry.COLUMN_NAME_MARCADORES_FAVORITOS_USUARIO)),
                    cursor.getString(cursor.getColumnIndexOrThrow(DataBaseContract.DataBaseEntry.COLUMN_NAME_MARCADORES_FAVORITOS_DESCRIPCION)),
                    cursor.getString(cursor.getColumnIndexOrThrow(DataBaseContract.DataBaseEntry.COLUMN_NAME_MARCADORES_FAVORITOS_TIPO_DE_ICONO)),
                    cursor.getString(cursor.getColumnIndexOrThrow(DataBaseContract.DataBaseEntry.COLUMN_NAME_MARCADORES_FAVORITOS_NOMBRE_DEL_PUNTO))
            );
        } else {
            try {
                cursor.close();
                db.close();
            }
            catch(Exception e) {}
            return null;
        }
    }

    public static MarcadorFavorito leerMarcadorPorNombre(Context context, String nombre){
        DataBaseHelper dataBaseHelper = new DataBaseHelper(context);
        SQLiteDatabase db = dataBaseHelper.getReadableDatabase();

        String[] projection = {
                DataBaseContract.DataBaseEntry._ID,
                DataBaseContract.DataBaseEntry.COLUMN_NAME_MARCADORES_FAVORITOS_NOMBRE,
                DataBaseContract.DataBaseEntry.COLUMN_NAME_MARCADORES_FAVORITOS_COORDENADAX,
                DataBaseContract.DataBaseEntry.COLUMN_NAME_MARCADORES_FAVORITOS_COORDENADAY,
                DataBaseContract.DataBaseEntry.COLUMN_NAME_MARCADORES_FAVORITOS_USUARIO,
                DataBaseContract.DataBaseEntry.COLUMN_NAME_MARCADORES_FAVORITOS_DESCRIPCION,
                DataBaseContract.DataBaseEntry.COLUMN_NAME_MARCADORES_FAVORITOS_TIPO_DE_ICONO,
                DataBaseContract.DataBaseEntry.COLUMN_NAME_MARCADORES_FAVORITOS_NOMBRE_DEL_PUNTO
        };

        String selection = DataBaseContract.DataBaseEntry.COLUMN_NAME_MARCADORES_FAVORITOS_NOMBRE + " = ?";
        String[] selectionArgs = { nombre };

        Cursor cursor = db.query(
                DataBaseContract.DataBaseEntry.TABLE_NAME_MARCADORES_FAVORITOS,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        if(cursor.moveToFirst() && cursor.getCount() > 0) {
            return new MarcadorFavorito(
                    cursor.getInt(cursor.getColumnIndexOrThrow(DataBaseContract.DataBaseEntry._ID)),
                    cursor.getString(cursor.getColumnIndexOrThrow(DataBaseContract.DataBaseEntry.COLUMN_NAME_MARCADORES_FAVORITOS_NOMBRE)),
                    cursor.getDouble(cursor.getColumnIndexOrThrow(DataBaseContract.DataBaseEntry.COLUMN_NAME_MARCADORES_FAVORITOS_COORDENADAX)),
                    cursor.getDouble(cursor.getColumnIndexOrThrow(DataBaseContract.DataBaseEntry.COLUMN_NAME_MARCADORES_FAVORITOS_COORDENADAY)),
                    cursor.getInt(cursor.getColumnIndexOrThrow(DataBaseContract.DataBaseEntry.COLUMN_NAME_MARCADORES_FAVORITOS_USUARIO)),
                    cursor.getString(cursor.getColumnIndexOrThrow(DataBaseContract.DataBaseEntry.COLUMN_NAME_MARCADORES_FAVORITOS_DESCRIPCION)),
                    cursor.getString(cursor.getColumnIndexOrThrow(DataBaseContract.DataBaseEntry.COLUMN_NAME_MARCADORES_FAVORITOS_TIPO_DE_ICONO)),
                    cursor.getString(cursor.getColumnIndexOrThrow(DataBaseContract.DataBaseEntry.COLUMN_NAME_MARCADORES_FAVORITOS_NOMBRE_DEL_PUNTO))
            );
        } else {
            return null;
        }
    }

    public static void eliminarPorID (Context context, Integer id){
        DataBaseHelper dataBaseHelper = new DataBaseHelper(context);
        SQLiteDatabase db = dataBaseHelper.getWritableDatabase();
        String selection = DataBaseContract.DataBaseEntry._ID + " LIKE ?";
        String[] selectionArgs = {id.toString()};
        db.delete(DataBaseContract.DataBaseEntry.TABLE_NAME_MARCADORES_FAVORITOS, selection, selectionArgs);
        try {
            db.close();
        }
        catch(Exception e) {}
    }

    public static void eliminar (Context context){
        DataBaseHelper dataBaseHelper = new DataBaseHelper(context);
        SQLiteDatabase db = dataBaseHelper.getWritableDatabase();
        db.delete(DataBaseContract.DataBaseEntry.TABLE_NAME_MARCADORES_FAVORITOS, null, null);
        try {
            db.close();
        }
        catch(Exception e) {}
    }

}
