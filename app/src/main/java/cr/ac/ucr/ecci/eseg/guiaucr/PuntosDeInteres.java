package cr.ac.ucr.ecci.eseg.guiaucr;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public final class PuntosDeInteres {
    private List<PuntoDeInteres> puntosDeInteres;

    public static final List<PuntoDeInteres> puntosDeInteresPublic = new ArrayList<PuntoDeInteres>() {
        {
            /*
            Context context = App.getContext();
            add(new PuntoDeInteres("Facultad de Ciencias Económicas", 9.937067, -84.051742,
                    "Ejemplo de una descripcion",
                     PuntoDeInteres.FACULTAD, "2511-6484", "https://www.fce.ucr.ac.cr/",
                    getBitmapAsByteArray(context,R.drawable.firma_digital_ucr)));
            add(new PuntoDeInteres("Facultad de Educación", 9.936068, -84.048814,
                    "Ejemplo de una descripcion",
                     PuntoDeInteres.FACULTAD, "2511-8867", "https://facultadeducacion.ucr.ac.cr/index.php/es/",
                    getBitmapAsByteArray(context,R.mipmap.placeholder)));
            add(new PuntoDeInteres("Escuela de computacion", 9.937926, -84.051970,
                    "Ejemplo de una descripcion",
                     PuntoDeInteres.FACULTAD, "2511-6484", "https://www.fce.ucr.ac.cr/",
                    getBitmapAsByteArray(context,R.mipmap.placeholder)));
            add(new PuntoDeInteres("Parada alajuela", 9.935556, -84.050156,
                    "Ejemplo de una descripcion",
                     PuntoDeInteres.PARADA, "2511-6484", "https://www.fce.ucr.ac.cr/",
                    getBitmapAsByteArray(context,R.mipmap.placeholder)));
            add(new PuntoDeInteres("Parada Cartago", 9.935539, -84.050219,
                    "Ejemplo de una descripcion",
                     PuntoDeInteres.PARADA, "2511-6484", "https://www.fce.ucr.ac.cr/",
                    getBitmapAsByteArray(context,R.mipmap.placeholder)));
            add(new PuntoDeInteres("Parada Heredia",  9.935555, -84.050136,
                    "Ejemplo de una descripcion",
                    PuntoDeInteres.PARADA, "2511-6484", "https://www.fce.ucr.ac.cr/",
                    getBitmapAsByteArray(context,R.mipmap.placeholder)));
            add(new PuntoDeInteres("Soda generales", 9.936241, -84.050440,
                    "Ejemplo de una descripcion",
                     PuntoDeInteres.SODA, "2511-6484", "https://www.fce.ucr.ac.cr/",
                    getBitmapAsByteArray(context,R.mipmap.placeholder)));
            add(new PuntoDeInteres("Soda Aulas", 9.936650, -84.050272,
                    "Ejemplo de una descripcion",
                     PuntoDeInteres.SODA, "2511-6484", "https://www.fce.ucr.ac.cr/",
                    getBitmapAsByteArray(context,R.mipmap.placeholder)));
            add(new PuntoDeInteres("Soda Farmacia", 9.938797, -84.049518,
                    "Ejemplo de una descripcion",
                     PuntoDeInteres.SODA, "2511-6484", "https://www.fce.ucr.ac.cr/",
                    getBitmapAsByteArray(context,R.mipmap.placeholder)));
            add(new PuntoDeInteres("Fotocopiadora Aulas", 9.936706, -84.050444,
                    "Ejemplo de una descripcion",
                     PuntoDeInteres.FOTOCOPIADORA, "2511-6484", "https://www.fce.ucr.ac.cr/",
                    getBitmapAsByteArray(context,R.mipmap.placeholder)));
            add(new PuntoDeInteres("Facultad de Derecho", 9.936463, -84.053852,
                    "Días de atención: Lunes a Viernes\nHorario: 7:00 AM - 8:00 PM",
                     PuntoDeInteres.FACULTAD, "2511-6484", "https://www.fce.ucr.ac.cr/",
                    getBitmapAsByteArray(context,R.mipmap.placeholder)));
            add(new PuntoDeInteres("Escuela de Arquitectura", 9.9351374, -84.0528298,
                    "Días de atención: Lunes a Viernes\nHorario: 8:00 AM - 5:00 PM",
                     PuntoDeInteres.FACULTAD, "2511-6484", "https://www.fce.ucr.ac.cr/",
                    getBitmapAsByteArray(context,R.mipmap.placeholder)));
            add(new PuntoDeInteres("Comedor Estudiantil", 9.9372532, -84.0541903,
                    "Días de atención: Lunes a Viernes\nHorario: 7:00 AM - 8:00 PM",
                     PuntoDeInteres.SODA, "2511-6484", "https://www.fce.ucr.ac.cr/",
                    getBitmapAsByteArray(context,R.mipmap.placeholder)));
            add(new PuntoDeInteres("Escuela de Química", 9.9373497, -84.0503486,
                    "Días de atención: Lunes a Viernes\nHorario: 7:00 AM - 8:00 PM",
                     PuntoDeInteres.FACULTAD, "2511-6484", "https://www.fce.ucr.ac.cr/",
                    getBitmapAsByteArray(context,R.mipmap.placeholder)));
            add(new PuntoDeInteres("Escuela de Física y Escuela de Matemáticas", 9.9367071, -84.0512781,
                    "Días de atención: Lunes a Viernes\nHorario: 7:00 AM - 8:00 PM",
                     PuntoDeInteres.FACULTAD, "2511-6484", "https://www.fce.ucr.ac.cr/",
                    getBitmapAsByteArray(context,R.mipmap.placeholder)));
            add(new PuntoDeInteres("Facultad de Artes", 9.9362759, -84.0491663,
                    "Días de atención: Lunes a Viernes\nHorario: 8:00 AM - 5:00 PM",
                     PuntoDeInteres.FACULTAD, "2511-6484", "https://www.fce.ucr.ac.cr/",
                    getBitmapAsByteArray(context,R.mipmap.placeholder)));
            add(new PuntoDeInteres("Escuela de Biología", 9.937585, -84.048891,
                    "Días de atención: Lunes a Viernes\nHorario: 8:00 AM - 5:00 PM",
                     PuntoDeInteres.FACULTAD, "2511-6484", "https://www.fce.ucr.ac.cr/",
                    getBitmapAsByteArray(context,R.mipmap.placeholder)));
            add(new PuntoDeInteres("Escuela de Biología", 9.937585, -84.048891,
                    "Días de atención: Lunes a Viernes\nHorario: 8:00 AM - 5:00 PM",
                     PuntoDeInteres.FACULTAD, "2511-6484", "https://www.fce.ucr.ac.cr/",
                    getBitmapAsByteArray(context,R.mipmap.placeholder)));
            add(new PuntoDeInteres("Escuela de Ciencias Agroalimentarias", 9.9385219, -84.0491698,
                    "Días de atención: Lunes a Viernes\nHorario: 8:00 AM - 5:00 PM",
                     PuntoDeInteres.FACULTAD, "2511-6484", "https://www.fce.ucr.ac.cr/",
                    getBitmapAsByteArray(context,R.mipmap.placeholder)));
            add(new PuntoDeInteres("Soda de Agronomía", 9.9385219, -84.0491698,
                    "Días de atención: Lunes a Viernes\nHorario: 7:00 AM - 7:00 PM",
                     PuntoDeInteres.SODA, "2511-6484", "https://www.fce.ucr.ac.cr/",
                    getBitmapAsByteArray(context,R.mipmap.placeholder)));
            add(new PuntoDeInteres("Escuela de Geología", 9.9381221, -84.0546879,
                    "Días de atención: Lunes a Viernes\nHorario: 8:00 AM - 5:00 PM",
                     PuntoDeInteres.FACULTAD, "2511-6484", "https://www.fce.ucr.ac.cr/",
                    getBitmapAsByteArray(context,R.mipmap.placeholder)));
            add(new PuntoDeInteres("Escuela de Nutrición", 9.9384147, -84.0462347,
                    "Días de atención: Lunes a Viernes\nHorario: 8:00 AM - 5:00 PM",
                     PuntoDeInteres.FACULTAD, "2511-6484", "https://www.fce.ucr.ac.cr/",
                    getBitmapAsByteArray(context,R.mipmap.placeholder)));
            add(new PuntoDeInteres("Escuela de Microbiología", 9.937973, -84.0485198,
                    "Días de atención: Lunes a Viernes\nHorario: 8:00 AM - 4:00 PM",
                    PuntoDeInteres.FACULTAD, "2511-6484", "https://www.fce.ucr.ac.cr/",
                    getBitmapAsByteArray(context,R.mipmap.placeholder)));
            add(new PuntoDeInteres("Facultad de Ciencias Sociales", 9.9382264, -84.04616,
                    "Días de atención: Lunes a Viernes\nHorario: 7:00 AM - 5:00 PM",
                    PuntoDeInteres.FACULTAD, "2511-6484", "https://www.fce.ucr.ac.cr/",
                    getBitmapAsByteArray(context,R.mipmap.placeholder)));
            add(new PuntoDeInteres("Escuela de Educación Física", 9.9438575, -84.0455848,
                    "Días de atención: Lunes a Viernes\nHorario: 7:00 AM - 5:00 PM",
                     PuntoDeInteres.FACULTAD, "2511-6484", "https://www.fce.ucr.ac.cr/",
                    getBitmapAsByteArray(context,R.mipmap.placeholder)));
            */
        }
    };


    public void add(PuntoDeInteres puntoDeInteres) {
        puntosDeInteres.add(puntoDeInteres);
    }

    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
    public List<PuntoDeInteres> getPuntosInteres() {
        return puntosDeInteres;
    }

    public static byte[] getBitmapAsByteArray(Context context,int id) {
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), id);
        if(bitmap != null) {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 0, outputStream);
            return outputStream.toByteArray();
        }
        return null;
    }
}
