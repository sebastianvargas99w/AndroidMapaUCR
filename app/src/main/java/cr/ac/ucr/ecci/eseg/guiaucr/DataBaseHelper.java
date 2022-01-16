package cr.ac.ucr.ecci.eseg.guiaucr;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBaseHelper extends SQLiteOpenHelper{
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "GuiaUCRStorage.db";

    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL(DataBaseContract.SQL_CREATE_PUNTO_DE_INTERES);
        db.execSQL(DataBaseContract.SQL_CREATE_MARCADORES_FAVORITOS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DataBaseContract.SQL_DELETE_PUNTO_DE_INTERES);
        db.execSQL(DataBaseContract.SQL_DELETE_MARCADORES_FAVORITOS);
        onCreate(db);
    }
    // inplementamos el metodo para volver a la version anterior de la base de datos
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

}
