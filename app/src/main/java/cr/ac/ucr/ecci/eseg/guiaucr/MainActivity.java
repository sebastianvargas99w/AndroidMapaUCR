package cr.ac.ucr.ecci.eseg.guiaucr;

import android.Manifest;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.PointF;
import android.location.Location;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.Blob;
import com.mapbox.android.core.location.LocationEngine;
import com.mapbox.android.core.location.LocationEngineCallback;
import com.mapbox.android.core.location.LocationEngineProvider;
import com.mapbox.android.core.location.LocationEngineRequest;
import com.mapbox.android.core.location.LocationEngineResult;
import com.mapbox.android.core.permissions.PermissionsListener;

import android.graphics.Color;
import android.widget.ToggleButton;

import com.mapbox.android.core.permissions.PermissionsManager;
import com.mapbox.api.directions.v5.DirectionsCriteria;
import com.mapbox.api.directions.v5.MapboxDirections;
import com.mapbox.api.directions.v5.models.DirectionsResponse;
import com.mapbox.api.directions.v5.models.DirectionsRoute;
import com.mapbox.geojson.Feature;
import com.mapbox.geojson.FeatureCollection;
import com.mapbox.geojson.LineString;
import com.mapbox.geojson.Point;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.geometry.LatLngBounds;
import com.mapbox.mapboxsdk.location.LocationComponent;
import com.mapbox.mapboxsdk.location.LocationComponentActivationOptions;
import com.mapbox.mapboxsdk.location.LocationComponentOptions;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;
import com.mapbox.mapboxsdk.plugins.annotation.OnSymbolClickListener;
import com.mapbox.mapboxsdk.plugins.annotation.Symbol;
import com.mapbox.mapboxsdk.plugins.annotation.SymbolManager;
import com.mapbox.mapboxsdk.plugins.annotation.SymbolOptions;
import com.mapbox.mapboxsdk.style.layers.LineLayer;
import com.mapbox.mapboxsdk.style.layers.Property;
import com.mapbox.mapboxsdk.style.layers.SymbolLayer;
import com.mapbox.mapboxsdk.style.sources.GeoJsonSource;
import com.mapbox.mapboxsdk.utils.BitmapUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import static com.mapbox.core.constants.Constants.PRECISION_6;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconAllowOverlap;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconIgnorePlacement;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconImage;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconOffset;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.lineCap;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.lineColor;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.lineJoin;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.lineWidth;
import com.yarolegovich.slidingrootnav.SlidingRootNav;
import com.yarolegovich.slidingrootnav.SlidingRootNavBuilder;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements OnMapReadyCallback, AddPointDialog.AddPointDialogAction {

    private ListView lista_sugerencias;
    public static List<MarcadorFavorito> marcadoresFavoritos;

    private MarcadorFavorito marcador_seleccionado;

    private ToggleButton botonFavorito;

    private CustomListAdapter adapter;

    private MenuItem myMenuSearch;

    Integer[] imgid = {
            R.drawable.ic_facultadad_blue,
            R.drawable.ic_fotocopiadora_green,
            R.drawable.ic_parada_red,
            R.drawable.ic_soda_yellow,
            R.drawable.ic_fav_icon
    };

    private static final LatLng BOUND_CORNER_NW = new LatLng(9.948116, -84.041542);
    private static final LatLng BOUND_CORNER_SE = new LatLng(9.934653, -84.055479);
    private static final LatLngBounds RESTRICTED_BOUNDS_AREA = new LatLngBounds.Builder()
            .include(BOUND_CORNER_NW)
            .include(BOUND_CORNER_SE)
            .build();

    private static final String SOURCE_ID = "SOURCE_ID";
    private static final String ICON_ID = "ICON_ID";
    private static final String LAYER_ID = "LAYER_ID";

    private static final String ICONO_PARADAS = "icon-paradas-id";
    private static final String ICONO_FACULTADES = "icon-facultades-id";
    private static final String ICONO_SODAS = "icon-sodas-id";
    private static final String ICONO_FOTOCOPIADORAS = "icon-fotocopiadoras-id";
    private static final String ICONO_FAVORITO = "icon-favoritos-id";
    private static final String ICONO_MARCADOR = "icon-marcadoress-id";

    private MapView mapView;
    public static MapboxMap map;
    private Style mStyle;
    private SymbolManager symbolLayerManager;
    private SlidingRootNav slidingRootNavBuilder;

    private int height;
    private int width;

    private LocationEngine locationEngine;
    private PermissionsListener permissionsListener;
    private PermissionsManager permissionsManager;
    private LocationComponent locationComponent;

    private static final String ROUTE_LAYER_ID = "route-layer-id";
    private static final String ROUTE_SOURCE_ID = "route-source-id";
    private static final String ICON_LAYER_ID = "icon-layer-id";
    private static final String ICON_SOURCE_ID = "icon-source-id";
    private static final String RED_PIN_ICON_ID = "red-pin-icon-id";
    private static final String PURPLE_PIN_ICON_ID = "purple-pin-icon-id";


    private Location myLastLocation;

    long DEFAULT_INTERVAL_IN_MILLISECONDS = 1000L;
    long DEFAULT_MAX_WAIT_TIME = DEFAULT_INTERVAL_IN_MILLISECONDS * 5;
    private LocationListeningCallback callback = new LocationListeningCallback(this);

    private Bundle savedInstanceState;
    private DirectionsRoute currentRoute;
    private MapboxDirections client;
    private Point origin;
    private Point destination;
    private boolean rutaActiva = false;
    private LatLng posicionUltimaAnotacion;

    private boolean clicked = false;

    private String routeLayerId = null;
    private String routeMarketLayerId = null;

    //Firebase
    private DatabaseReference mDatabaseReference; // Se enlaza la aplicacion con la base de datos.
    FirebaseDatabase firebaseDatabase;

    public void toast(String text) {
        Toast toast = Toast.makeText(getApplicationContext(), text, Toast.LENGTH_LONG);
        toast.show();
    }



    private class LocationListeningCallback implements LocationEngineCallback<LocationEngineResult> {
        private final WeakReference<MainActivity> activityWeakReference;
        LocationListeningCallback(MainActivity activity){
            this.activityWeakReference = new WeakReference<>(activity);
        }

        @Override
        public void onSuccess(LocationEngineResult result) {
            // The LocationEngineCallback interface's method which fires when the device's location has changed.
            Location lastLocation = result.getLastLocation();
            myLastLocation = result.getLastLocation();
        }

        @Override
        public void onFailure(@NonNull Exception exception) {
        }

        /*
            Historia de usuario: NAR-1-5. Mostrar ubicación actual en el mapa
                    Tarea: CNA-13. Solicitar permisos
            Driver: Sebástian Vargas
            Navigators: Pablo Ruiz, Fábian Rojas y Steven Barahona.
            */
    }

    /**
     * Add the route and marker icon layers to the map
     */
    private void initLayers(@NonNull Style loadedMapStyle) {
        LineLayer routeLayer = new LineLayer(ROUTE_LAYER_ID, ROUTE_SOURCE_ID);

// Add the LineLayer to the map. This layer will display the directions route.
        routeLayer.setProperties(
                lineCap(Property.LINE_CAP_ROUND),
                lineJoin(Property.LINE_JOIN_ROUND),
                lineWidth(5f),
                lineColor(Color.parseColor("#41ade7"))
        );
        routeLayerId = routeLayer.getId();
        loadedMapStyle.addLayer(routeLayer);

// Add the red marker icon image to the map
        loadedMapStyle.addImage(PURPLE_PIN_ICON_ID, BitmapUtils.getBitmapFromDrawable(
                getResources().getDrawable(R.drawable.ic_location_purple)));

// Add the red marker icon SymbolLayer to the map
        SymbolLayer marcadorRutaMasCorta = new SymbolLayer(ICON_LAYER_ID, ICON_SOURCE_ID).withProperties(
                iconImage(PURPLE_PIN_ICON_ID),
                iconIgnorePlacement(true),
                iconAllowOverlap(true),
                iconOffset(new Float[] {0f, -9f}));
        routeMarketLayerId = marcadorRutaMasCorta.getId();
        loadedMapStyle.addLayer(marcadorRutaMasCorta);
    }


    private void changeLocation(double latitude, double longitude)
    {
        if(longitude < BOUND_CORNER_NW.getLongitude()
           && longitude > BOUND_CORNER_SE.getLongitude()
           && latitude < BOUND_CORNER_NW.getLatitude()
           && latitude > BOUND_CORNER_SE.getLatitude())
        {
            int millisecondSpeed = 2000;
            CameraPosition position = new CameraPosition.Builder()
                    .target(new LatLng(latitude, longitude))
                    .zoom(18)
                    .build();
            map.animateCamera(CameraUpdateFactory.newCameraPosition(position), millisecondSpeed);
        }
        else
        {
            // toast("Se encuentra afuera del área de la Universidad de Costa Rica" + "latitud:" +latitude+ "longitud" + longitude);
            toast("Se encuentra afuera del área de la Universidad de Costa Rica");
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode) {
            case (1) : {
                if (resultCode == MainActivity.RESULT_OK) {
                    final List<PuntoDeInteres> puntosDeInteres = PuntoDeInteres.leer(getBaseContext());
                    addMarkers(map, puntosDeInteres);
                }
                break;
            }
            case (2) : {
                setMapFocus(resultCode);
                break;
            }
            case (3) : {
                // resultCode en este caso es el ID del marcador, se insertaron ids de 1 en adelante.
                if (resultCode != 0) {
                    setMapFocusOnMarker(resultCode);
                }
                break;
            }
        }
    }

    @SuppressWarnings("MissingPermission")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.savedInstanceState = savedInstanceState;
        firebaseDatabase = FirebaseDatabase.getInstance();
        marcadoresFavoritos = MarcadorFavorito.leer(getApplicationContext());
        loadMap();
    }

    public static List<MarcadorFavorito> getMarcadores(){
        return marcadoresFavoritos;
    }

    public void recargarEstiloMapa () {
        map.setStyle(new Style.Builder().fromUri("mapbox://styles/naranitas/ckfm1n2h0039q19qp0juz6wfn"), new Style.OnStyleLoaded() {
            @Override
            public void onStyleLoaded(@NonNull Style style) {
                // Set the boundary area for the map camera
                map.setLatLngBoundsForCameraTarget(RESTRICTED_BOUNDS_AREA);
                // Set the minimum zoom level of the map camera
                map.setMinZoomPreference(15);
                SymbolManager newSymbolLayerManager = new SymbolManager(mapView, map, style);
                cargarImagenes(style);
                final List<PuntoDeInteres> puntosDeInteres = PuntoDeInteres.leer(getBaseContext());
                addMarkers(map, puntosDeInteres);
            }
        });
    }


    public ImageButton cargarBotonYo() {
        ImageButton buttonYo = (ImageButton) findViewById(R.id.localizacion);

        buttonYo.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                locationEngine.getLastLocation(callback);
                if (myLastLocation != null) {
                    changeLocation(myLastLocation.getLatitude(), myLastLocation.getLongitude());
                }
                else {
                    toast("No se pudo obtener la ubicación actual");
                }
            }
        });
        return buttonYo;
    }

    public ImageButton cargarBotonFiltrar() {
        ImageButton filtrar = (ImageButton) findViewById(R.id.filtrar);
        filtrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(MainActivity.this, FilterActivity.class), 1);
            }
        });
        return filtrar;
    }

    public ImageButton cargarBotonAddPoint() {
        ImageButton buttonAddPoint = (ImageButton) findViewById(R.id.addPoint);

        buttonAddPoint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clicked = true;
                if(estaLogueado()) {
                    pressAddButton();
                }else{
                    Toast.makeText(getApplicationContext(), "Por favor inicie sesion para usar esta funcionalidad.", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return buttonAddPoint;
    }

    public ImageButton cargarBotonCentrar() {
        ImageButton centrar = (ImageButton) findViewById(R.id.centrar);
        centrar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(MainActivity.this, FincaFocusActivity.class).putExtra("codigo",2), 2);
            }
        });
        return centrar;
    }

    public Button cargarBotonAbout(){
        Button about = (Button) findViewById(R.id.aboutButton);
        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                irAbout();
            }
        });
        return about;
    }

    public Button cargarBotonCerraSesion() {
        Button cerrarSesionButton = (Button)findViewById(R.id.cerrarSesionButton);
        cerrarSesionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cerrarSesion();
            }
        });
        return cerrarSesionButton;
    }

    public ImageButton cargarBotonMenu() {
        ImageButton menuButton = (ImageButton) findViewById(R.id.drawer_menu_image);
        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                slidingRootNavBuilder.openMenu();
            }
        });
        return menuButton;
    }

    public TextView cargarTextoMarcadoresFavoritos() {
        TextView marcadoresFavoritos = (TextView) findViewById(R.id.text_favoritos);
        marcadoresFavoritos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(MainActivity.this, MarcadoresFavoritosActivity.class), 3);
            }
        });
        return marcadoresFavoritos;
    }

    public void cargarBotonFavoritos() {
        this.botonFavorito = (ToggleButton) findViewById(R.id.agregar_a_favoritos);
        this.botonFavorito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView titulo = (TextView) findViewById(R.id.titulo);

                // Obtener el punto de interés apartir del nombre
                PuntoDeInteres punto = PuntoDeInteres.leerPuntoDeInteresPorNombre(getApplicationContext(), titulo.getText().toString());

                int usuario = 1; // Para efectos de pruebas.

                // Si no está marcado como favorito
                if (marcador_seleccionado == null) {
                    toast("Se agregó el punto a Favoritos");

                    // Agregar a la tabla de favoritos
                    MarcadorFavorito marcador_nuevo = new MarcadorFavorito(titulo.getText().toString(), punto.getCoordenadaX(), punto.getCoordenadaY(), usuario, punto.getDescripcion(), "", titulo.getText().toString());
                    long id = MarcadorFavorito.insertar(getBaseContext(), marcador_nuevo);
                } else {
                    // Si ya está marcado como favorito

                    toast("Se eliminó el punto de Favoritos");

                    // Borrar de favoritos
                    MarcadorFavorito.eliminarPorID(getApplicationContext(), marcador_seleccionado.getId());
                }
            }
        });
    }

    public ImageButton cargarBotonDetalles() {
        ImageButton botonDetalles = (ImageButton) findViewById(R.id.detalles);
        botonDetalles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView titulo = (TextView) findViewById(R.id.titulo);
                irDetalles(titulo.getText().toString());
            }
        });
        return botonDetalles;
    }

    public ImageButton cargarBotonCaminarA() {
        ImageButton botonCaminarA = (ImageButton) findViewById(R.id.caminar_a);
        botonCaminarA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rutaActiva = true;
                Style currentStyle = map.getStyle();
                try {
                    currentStyle.removeLayer(routeLayerId);
                } catch (NullPointerException e) {
                    Log.w("cargarBotonCaminarA;","NullPointerException in MainAtivity->cargarBotonCaminarA");
                }
                try {
                    currentStyle.removeLayer(routeMarketLayerId);
                } catch (NullPointerException e) {
                    Log.w("cargarBotonCaminarA;","NullPointerException in MainAtivity->cargarBotonCaminarA");
                }
                try {
                    currentStyle.removeSource(ROUTE_SOURCE_ID);
                } catch (NullPointerException e) {
                    Log.w("cargarBotonCaminarA;","NullPointerException in MainAtivity->cargarBotonCaminarA");
                }
                try {
                    currentStyle.removeSource(ICON_SOURCE_ID);
                } catch (NullPointerException e) {
                    Log.w("cargarBotonCaminarA;","NullPointerException in MainAtivity->cargarBotonCaminarA");
                }
                showRoute(posicionUltimaAnotacion.getLatitude(),posicionUltimaAnotacion.getLongitude());
            }
        });
        return botonCaminarA;
    }

    @SuppressWarnings("MissingPermission")
    private void showRouteClick(LatLng point) {
        if(!rutaActiva)
        {
            locationEngine.getLastLocation(callback);
            if(myLastLocation != null) {
                changeLocation(myLastLocation.getLatitude(), myLastLocation.getLongitude());
                rutaActiva = true;
                showRoute(point.getLatitude(),point.getLongitude());
            } else {
                toast("No se pudo obtener la ubicación actual");
            }
        }
        else
        {
            rutaActiva = false;
            if(routeLayerId != null && routeMarketLayerId != null) {
                Style currentStyle = map.getStyle();
                currentStyle.removeLayer(routeLayerId);
                currentStyle.removeLayer(routeMarketLayerId);
                currentStyle.removeSource(ROUTE_SOURCE_ID);
                currentStyle.removeSource(ICON_SOURCE_ID);
                routeMarketLayerId = null;
                routeLayerId = null;
            }
        }
    }

    /**
     * Funcion similar a loadMap
     * No realiza transacciones en la base de datos,
     * */
    private void shortLoadMap() {
        setContentView(R.layout.activity_main);
        mapView = (MapView) findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        /**************************************/
        mapView.getMapAsync(new OnMapReadyCallback() {

            @SuppressWarnings("MissingPermission")
            @Override
            public void onMapReady(@NonNull final MapboxMap mapboxMap) {
                map = mapboxMap;
                map.addOnMapLongClickListener( new MapboxMap.OnMapLongClickListener(){
                    @Override
                    public boolean onMapLongClick(@NonNull LatLng point) {
                        /**
                         * OBTENER LA RUTA MAS CORTA DE AQUI EN ADELANTE
                         **/
                        showRouteClick(point);
                        return false;
                    }
                });
                mapboxMap.setStyle(new Style.Builder().fromUri("mapbox://styles/naranitas/ckfm1n2h0039q19qp0juz6wfn"), new Style.OnStyleLoaded() {
                    @Override
                    public void onStyleLoaded(@NonNull Style style) {
                        mStyle = style;
                        //Añade los marcadores que hay en la base de datos local.

                        // Set the boundary area for the map camera
                        mapboxMap.setLatLngBoundsForCameraTarget(RESTRICTED_BOUNDS_AREA);
                        //Añadimos los marcadores.
                        // Set the minimum zoom level of the map camera
                        mapboxMap.setMinZoomPreference(15);
                        requestLocation(style);
                        symbolLayerManager = new SymbolManager(mapView, map, style);
                        cargarImagenes(style);
                        addMarkers( map, PuntoDeInteres.leer(getBaseContext()));
                    }
                });



                map.addOnMapClickListener(new MapboxMap.OnMapClickListener() {
                    @Override
                    public boolean onMapClick(@NonNull LatLng point) {
                        hideDetails();
                        return false;
                    }
                });

                map.addOnCameraMoveListener(new MapboxMap.OnCameraMoveListener() {
                    @Override
                    public void onCameraMove() {
                        hideDetails();
                    }
                });
            }
        });
        /**************************************/
        getSupportActionBar().setDisplayShowTitleEnabled(false);//esto es para evitar que salga el label de GuiaUCR predeterminado
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.app_bar_main);
        slidingRootNavBuilder = new SlidingRootNavBuilder(this)
                .withMenuOpened(false)
                .withContentClickableWhenMenuOpened(false)
                .withSavedState(savedInstanceState)
                .withMenuLayout(R.layout.drawer_menu)
                .inject();



        ImageButton buttonAddPoint = cargarBotonAddPoint();
        ImageButton buttonYo = cargarBotonYo();
        ImageButton filtrar = cargarBotonFiltrar();
        ImageButton centrar = cargarBotonCentrar();
        Button about = cargarBotonAbout();
        Button cerrarSesionButton = cargarBotonCerraSesion();
        ImageButton menu = cargarBotonMenu();
        TextView marcadores_favoritos = cargarTextoMarcadoresFavoritos();
        cargarBotonFavoritos();
        ImageButton botonDetalles = cargarBotonDetalles();
        ImageButton botonCaminarA = cargarBotonCaminarA();
    }

    private void loadMap() {
        //Esto es para pruebas
        deleteAllData();//Se borran todos los datos que habian en la base de datos.
        deleteAllMarkers();
//        addPointsToFireBase(FirebaseDatabase.getInstance().getReference().child("PuntosDeInteres"));
//        addMarkersToFireBase(FirebaseDatabase.getInstance().getReference().child("MarcadoresFavoritos"));
        addDataToDB();// Se vuelven a insertar
        addMarcadoresToDB();

        marcadoresFavoritos = MarcadorFavorito.leer(getApplicationContext());

        getScreenSize();
        Mapbox.getInstance(this, getString(R.string.mapbox_access_token));
        setContentView(R.layout.activity_main);
        getSupportActionBar().setDisplayShowTitleEnabled(false);//esto es para evitar que salga el label de GuiaUCR predeterminado
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.app_bar_main);
        slidingRootNavBuilder = new SlidingRootNavBuilder(this)
                .withMenuOpened(false)
                .withContentClickableWhenMenuOpened(false)
                .withSavedState(savedInstanceState)
                .withMenuLayout(R.layout.drawer_menu)
                .inject();
        mapView = (MapView) findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);

        ImageButton buttonAddPoint = cargarBotonAddPoint();
        ImageButton buttonYo = cargarBotonYo();
        ImageButton filtrar = cargarBotonFiltrar();
        ImageButton centrar = cargarBotonCentrar();

        //Historia de usuario: NAR-2-2 Implementar Menú Drawer.
        //        Tarea: CNA-36. Agregar botón de About.
        //        Driver: Steven Barahona
        //        Navigators: Sebastián Vargas y Fabian Rojas.

        Button about = cargarBotonAbout();

        Button cerrarSesionButton = cargarBotonCerraSesion();

        //Historia de usuario: NAR-2-2 Implementar Menú Drawer.
        //        Tarea: CNA-31. Agregar el drawer.
        //        Driver: Pablo Ruiz
        //        Navigators: Steven Barahona y Fábian Rojas.

        ImageButton menu = cargarBotonMenu();

        TextView marcadores_favoritos = cargarTextoMarcadoresFavoritos();

        cargarBotonFavoritos();

        ImageButton botonDetalles = cargarBotonDetalles();

        ImageButton botonCaminarA = cargarBotonCaminarA();

        mapView.getMapAsync(new OnMapReadyCallback() {

            @SuppressWarnings("MissingPermission")
            @Override
            public void onMapReady(@NonNull final MapboxMap mapboxMap) {
                map = mapboxMap;
                map.addOnMapLongClickListener( new MapboxMap.OnMapLongClickListener(){
                    @Override
                    public boolean onMapLongClick(@NonNull LatLng point) {
                        /**
                         * OBTENER LA RUTA MAS CORTA DE AQUI EN ADELANTE
                         **/
                        showRouteClick(point);
                        return false;
                    }
                });
                final List<PuntoDeInteres> puntosDeInteres = PuntoDeInteres.leer(getBaseContext());
                mapboxMap.setStyle(new Style.Builder().fromUri("mapbox://styles/naranitas/ckfm1n2h0039q19qp0juz6wfn"), new Style.OnStyleLoaded() {
                    @Override
                    public void onStyleLoaded(@NonNull Style style) {
                        mStyle = style;
                        //Añade los marcadores que hay en la base de datos local.

                        // Set the boundary area for the map camera
                        mapboxMap.setLatLngBoundsForCameraTarget(RESTRICTED_BOUNDS_AREA);
                        //Añadimos los marcadores.
                        // Set the minimum zoom level of the map camera
                        mapboxMap.setMinZoomPreference(15);
                        requestLocation(style);
                        symbolLayerManager = new SymbolManager(mapView, map, style);
                        cargarImagenes(style);
//                        syncDatabases();
                        //final List<PuntoDeInteres> puntosDeInteres = PuntoDeInteres.leer(getBaseContext());
                        addMarkers( map, PuntoDeInteres.leer(getBaseContext()));
                        if(myLastLocation != null) {
                            changeLocation(myLastLocation.getLatitude(), myLastLocation.getLongitude());
                        }
                    }
                });

                map.addOnMapClickListener(new MapboxMap.OnMapClickListener() {
                    @Override
                    public boolean onMapClick(@NonNull LatLng point) {
                        hideDetails();
                        return false;
                    }
                });

                map.addOnCameraMoveListener(new MapboxMap.OnCameraMoveListener() {
                    @Override
                    public void onCameraMove() {
                        hideDetails();
                    }
                });
            }
        });
    }//loadMap

    private void showRoute(double latitude, double longitude) {

        origin = Point.fromLngLat(myLastLocation.getLongitude(), myLastLocation.getLatitude());
        destination = Point.fromLngLat(longitude, latitude);
        initSource(map.getStyle());
        initLayers(map.getStyle());
        // Get the directions route from the Mapbox Directions API
        getRoute(map, origin, destination);
    }

    private void irDetalles(String nombrePunto){
        Intent intent = new Intent(this, PuntoDeInteresDetailsActivity.class);
        intent.putExtra("nombrePunto", nombrePunto);
        startActivity(intent);
    }

    private void irAbout(){
        Intent intent = new Intent(this, AboutActivity.class);
        startActivity(intent);
    }

    private void cerrarSesion(){
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.signOut();
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
        finish();
    }

    @SuppressWarnings("MissingPermission")
    private void requestLocation(Style style)
    {
        LocationEngineRequest request = new LocationEngineRequest.Builder(DEFAULT_INTERVAL_IN_MILLISECONDS)
                .setPriority(LocationEngineRequest.PRIORITY_NO_POWER)
                .setMaxWaitTime(DEFAULT_MAX_WAIT_TIME)
                .build();
        permissionsListener = new PermissionsListener() {
            @Override
            public void onExplanationNeeded(List<String> permissionsToExplain) {
                CharSequence text = "La aplicación está intentando obtener su ubicación actual";
                Toast toast = Toast.makeText(getApplicationContext(), text,  Toast.LENGTH_SHORT);
                toast.show();
            }

            @Override
            public void onPermissionResult(boolean granted) {
                if (granted) {

                } else {
                    // User denied the permission
                    CharSequence text = "No se pudo obtener permisos";
                    Toast toast = Toast.makeText(getApplicationContext(), text,  Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        };

        permissionsManager = new PermissionsManager(permissionsListener);
        permissionsManager.requestLocationPermissions(this);
        if(PermissionsManager.areLocationPermissionsGranted(this))
        {
            LocationListeningCallback callback = new LocationListeningCallback(this);
            locationEngine = LocationEngineProvider.getBestLocationEngine(this);
            locationEngine.getLastLocation(callback);

            LocationComponentOptions locationComponentOptions = LocationComponentOptions.builder(this)
                    .build();

            locationComponent = map.getLocationComponent();

            LocationComponentActivationOptions locationComponentActivationOptions = LocationComponentActivationOptions
                    .builder(this, style)
                    .locationComponentOptions(locationComponentOptions)
                    .build();
            locationComponent.activateLocationComponent(locationComponentActivationOptions);
            locationComponent.setLocationComponentEnabled(true);
        }
        else
        {
            toast("No se tiene permisos para acceder a la ubicación actual");
        }
    }

    /**
     * Add the route and marker sources to the map
     */
    private void initSource(@NonNull Style loadedMapStyle) {
        loadedMapStyle.addSource(new GeoJsonSource(ROUTE_SOURCE_ID));

        GeoJsonSource iconGeoJsonSource = new GeoJsonSource(ICON_SOURCE_ID, FeatureCollection.fromFeatures(new Feature[] {
                Feature.fromGeometry(Point.fromLngLat(origin.longitude(), origin.latitude())),
                Feature.fromGeometry(Point.fromLngLat(destination.longitude(), destination.latitude()))}));
        loadedMapStyle.addSource(iconGeoJsonSource);
    }

    private void getRoute(MapboxMap mapboxMap, Point origin, Point destination) {
        client = MapboxDirections.builder()
                .origin(origin)
                .destination(destination)
                .overview(DirectionsCriteria.OVERVIEW_FULL)
                .profile(DirectionsCriteria.PROFILE_WALKING)
                .accessToken(getString(R.string.mapbox_access_token))
                .build();

        client.enqueueCall(new Callback<DirectionsResponse>() {
            @Override
            public void onResponse(Call<DirectionsResponse> call, Response<DirectionsResponse> response) {
// You can get the generic HTTP info about the response
                /*
                Timber.d("Response code: " + response.code());
                if (response.body() == null) {
                    Timber.e("No routes found, make sure you set the right user and access token.");
                    return;
                } else if (response.body().routes().size() < 1) {
                    Timber.e("No routes found");
                    return;
                }
                */

// Get the directions route
                currentRoute = response.body().routes().get(0);

// Make a toast which displays the route's distance
                /*
                Toast.makeText(DirectionsActivity.this, String.format(
                        getString(R.string.directions_activity_toast_message),
                        currentRoute.distance()), Toast.LENGTH_SHORT).show();
                        */


                if (mapboxMap != null) {
                    mapboxMap.getStyle(new Style.OnStyleLoaded() {
                        @Override
                        public void onStyleLoaded(@NonNull Style style) {

// Retrieve and update the source designated for showing the directions route
                            GeoJsonSource source = style.getSourceAs(ROUTE_SOURCE_ID);

// Create a LineString with the directions route's geometry and
// reset the GeoJSON source for the route LineLayer source
                            if (source != null) {
                                source.setGeoJson(LineString.fromPolyline(currentRoute.geometry(), PRECISION_6));
                            }
                        }
                    });
                }
            }
            @Override
            public void onFailure(Call<DirectionsResponse> call, Throwable throwable) {
                toast("No sirvio");
            }
        });
    }

    private void stopCameraMovement() {
        CameraPosition currentCameraPosition = map.getCameraPosition();
        map.animateCamera(CameraUpdateFactory.newCameraPosition(currentCameraPosition), 1);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        stopCameraMovement();
        int id = item.getItemId();
        final List<PuntoDeInteres> puntosDeInteres = PuntoDeInteres.leer(getBaseContext());

        if (id == R.id.action_search) {
            setContentView(R.layout.search_suggestions);

            lista_sugerencias = (ListView) findViewById(R.id.list);

            lista_sugerencias.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    myMenuSearch.collapseActionView();
                    shortLoadMap();
                    final PuntoDeInteres puntoSeleccionado = (PuntoDeInteres) adapter.getItem(position);

                    // Se obtienen la latitud y longitud del punto seleccionado
                    final LatLng latLng = new LatLng();
                    latLng.setLatitude(puntoSeleccionado.getCoordenadaX());
                    latLng.setLongitude(puntoSeleccionado.getCoordenadaY());
                    // Se posiciona la cámara en el punto seleccionado
                    CameraPosition camera = new CameraPosition.Builder().target(latLng).zoom(18).build();
                    map.animateCamera(CameraUpdateFactory.newCameraPosition(camera), 1000, new MapboxMap.CancelableCallback() {
                        @Override
                        public void onCancel() {}

                        @Override
                        public void onFinish() {
                            Location location_punto_seleccionado = new Location("");
                            location_punto_seleccionado.setLatitude(latLng.getLatitude());
                            location_punto_seleccionado.setLongitude(latLng.getLongitude());

                            int millisecondSpeed = 2000;
                            CameraPosition position = new CameraPosition.Builder()
                                    .target(new LatLng(location_punto_seleccionado.getLatitude(), location_punto_seleccionado.getLongitude()))
                                    .zoom(18)
                                    .build();
                            map.animateCamera(CameraUpdateFactory.newCameraPosition(position), millisecondSpeed);
                        }
                    });
                }
            });

            adapter = new CustomListAdapter(this, puntosDeInteres, imgid);
            lista_sugerencias.setAdapter(adapter);
        }

        return super.onOptionsItemSelected(item);
    }


@Override
public boolean onCreateOptionsMenu(final Menu menu) {
    getMenuInflater().inflate(R.menu.options_menu, menu);

    /*
        Historia de usuario: CNA-10 Buscar un punto de interés.
        Tarea: CNA-25. Mostrar ícono de búsqueda en el menú.
        Driver: Sharon Bejarano Hernández.
        Navigators: Sebastián Vargas Soto.
    */

    final MenuItem searchItem = menu.findItem(R.id.action_search);
    final androidx.appcompat.widget.SearchView searchView = (androidx.appcompat.widget.SearchView) searchItem.getActionView();

    myMenuSearch = searchItem;;

    SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
    searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
    searchView.setIconifiedByDefault(false);

    searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
        @Override
        public boolean onQueryTextSubmit(String query) {
            searchItem.collapseActionView();
            return false;
        }

        @Override
        public boolean onQueryTextChange(String newText) {
            adapter.getFilter().filter(newText);
            return true;
        }
    });

    searchItem.setOnActionExpandListener(new MenuItem.OnActionExpandListener() {
        @Override
        public boolean onMenuItemActionExpand(MenuItem item) {
            return true;
        }

        @Override
        public boolean onMenuItemActionCollapse(MenuItem item) {
            shortLoadMap();

            return true;
        }
    });

    return true;
}

    @Override
    protected void onStart() {
        super.onStart();
        mapView.onStart();

    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (locationEngine != null) {
            locationEngine.removeLocationUpdates(callback);
        }
        mapView.onStop();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onMapReady(@NonNull MapboxMap mapboxMap) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    private void addDataToDB(){
        // Añadimos los puntos de interes.
        for (PuntoDeInteres punto:PuntosDeInteres.puntosDeInteresPublic) {
            PuntoDeInteres.insertar(getBaseContext(), punto);
        }
    }

    private void addMarcadoresToDB(){
        // Añadimos los puntos de interes.
        for (MarcadorFavorito marcador:MarcadorFavorito.favoritos_public) {
            MarcadorFavorito.insertar(getBaseContext(), marcador);
        }
    }

    /**
     * Metodo que elemina todos los datos de la base de datos .
     */
    private void deleteAllData(){
        PuntoDeInteres.eliminarDatos(getBaseContext());
    }

    private void deleteAllMarkers(){
        MarcadorFavorito.eliminar(getBaseContext());
    }

    private void pressAddButton() {
        Toast.makeText(getBaseContext(), "Presione la ubicacion en el mapa que desea marcar.", Toast.LENGTH_LONG).show();
        map.addOnMapClickListener(new MapboxMap.OnMapClickListener() {
            @Override
            public boolean onMapClick(@NonNull LatLng point) {
                if (clicked) {
                    double latitude = point.getLatitude();
                    double longitude = point.getLongitude();
                    AddPointDialog addPointDialog = new AddPointDialog();
                    addPointDialog.coordenadaX = latitude;
                    addPointDialog.coordenadaY = longitude;
                    addPointDialog.show(getSupportFragmentManager(), "addPointDialog");
                    clicked = false;
                    addMarkers(map, PuntoDeInteres.leer(getBaseContext()));
                }
                return true;
            }
        });
    }
    @Override
    public void updateScreen() {
        addMarkers(map, PuntoDeInteres.leer(getBaseContext()));
    }
    private boolean handleClickIcon(PointF screenPoint){
        return false;
    }

    /**
     * Metodo de prueba para cargar datos a firebase desde PuntosDeInteres.
     * @param puntosDeInteres root de firebase en el que se van a insertar los datos.
     */
    private void addPointsToFireBase(DatabaseReference puntosDeInteres){
        for(PuntoDeInteres punto : PuntosDeInteres.puntosDeInteresPublic){
            DatabaseReference puntoActual = puntosDeInteres.push();
            puntoActual.child("nombre").setValue(punto.getNombre());
            puntoActual.child("descripcion").setValue(punto.getDescripcion());
            puntoActual.child("coordenadaX").setValue(punto.getCoordenadaX());
            puntoActual.child("coordenadaY").setValue(punto.getCoordenadaY());
            puntoActual.child("tipo").setValue(punto.getTipo());
            puntoActual.child("telefono").setValue(punto.getTelefono());
            puntoActual.child("pagina").setValue(punto.getPagina());
            //se crea un Blob para poder almacenar la foto
            Blob blob = Blob.fromBytes(punto.getFoto());
            puntoActual.child("foto").setValue(blob.toString());
        }
    }

    /**
     * Metodo de prueba para cargar datos a firebase desde PuntosDeInteres.
     * @param marcadoresFavoritos root de firebase en el que se van a insertar los datos.
     */
    private void addMarkersToFireBase(DatabaseReference marcadoresFavoritos){
        for(MarcadorFavorito marcadorFavorito : MarcadorFavorito.favoritos_public){
            DatabaseReference marcadorActual = marcadoresFavoritos.push();
            marcadorActual.child("nombre").setValue(marcadorFavorito.getNombre());
            marcadorActual.child("descripcion").setValue(marcadorFavorito.getDescripcion());
            marcadorActual.child("latitud").setValue(marcadorFavorito.getLatitud());
            marcadorActual.child("longitud").setValue(marcadorFavorito.getLongitud());
            marcadorActual.child("usuario").setValue(marcadorFavorito.getUsuario());
            marcadorActual.child("tipoIcono").setValue(marcadorFavorito.getTipoIcono());
            marcadorActual.child("nombrePunto").setValue(marcadorFavorito.getNombrePunto());
        }
    }

    /**
     * Metodo que sincroniza las base de datos local, con la base de datos de firebase. Y una vez la actualiza añade los marcadores nuevos al mapa.
     */
    private void syncDatabases(){
        hacerVisibleBarraProgreso();
        final TaskCompletionSource<String> tcs = new TaskCompletionSource<>();
        //Obitiene la referencia al documento root.
        mDatabaseReference  = firebaseDatabase.getReference();
        DatabaseReference puntosDeInteres = mDatabaseReference.child("PuntosDeInteres");
        //Datos consultados de firebase
        final List<PuntoDeInteres> listaDePuntos = new ArrayList<>();
        puntosDeInteres.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()) {
                    for(DataSnapshot punto : dataSnapshot.getChildren()){
                        String nombre = punto.child("nombre").getValue().toString();
                        String descripcion = punto.child("descripcion").getValue().toString();
                        String tipo = punto.child("tipo").getValue().toString();
                        String telefono = punto.child("telefono").getValue().toString();
                        String pagina = punto.child("pagina").getValue().toString();
                        double coordenadaX = (double) punto.child("coordenadaX").getValue();
                        double coordenadaY = (double) punto.child("coordenadaY").getValue();
                        Blob blob = Blob.fromBytes(punto.child("foto").getValue().toString().getBytes());
                        byte[] foto = (byte[]) blob.toBytes();
                        //Los que no estan en la base de datos se añaden.
                        if(PuntoDeInteres.leerPuntoDeInteresPorNombre(getBaseContext(), nombre) == null) {
                            listaDePuntos.add(new PuntoDeInteres(nombre, coordenadaX,coordenadaY, descripcion,tipo, telefono, pagina, foto));
                            PuntoDeInteres.insertar(getBaseContext(), new PuntoDeInteres(nombre, coordenadaX,coordenadaY, descripcion,tipo, telefono, pagina, foto));
                        }
                    }
                }
                //hasta que se encuentre una herramienta de sincronizacion se añaden los marcadores faltantes.
                addMarkers(map,PuntoDeInteres.leer(getBaseContext()));
                hacerInvisibleBarraProgreso();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        DatabaseReference marcadoresFavoritos = mDatabaseReference.child("MarcadoresFavoritos");
        //Datos consultados de firebase
        final List<MarcadorFavorito> listaDeMarcadores = new ArrayList<>();
        marcadoresFavoritos.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()) {
                    for(DataSnapshot marcador : dataSnapshot.getChildren()){
                        String nombre = marcador.child("nombre").getValue().toString();
                        double latitud = (double) marcador.child("latitud").getValue();
                        double longitud = (double) marcador.child("longitud").getValue();
                        int usuario = Integer.parseInt(marcador.child("usuario").getValue().toString());
                        String descripcion = marcador.child("descripcion").getValue().toString();
                        String tipoIcono = marcador.child("tipoIcono").getValue().toString();
                        String nombrePunto = marcador.child("nombrePunto").getValue().toString();
                        //Los que no estan en la base de datos se añaden.
                        if(MarcadorFavorito.leerMarcadorPorNombre(getBaseContext(), nombre) == null) {
                            listaDeMarcadores.add(new MarcadorFavorito(nombre, latitud, longitud, usuario, descripcion, tipoIcono, nombrePunto));
                            MarcadorFavorito.insertar(getBaseContext(), new MarcadorFavorito(nombre, latitud, longitud, usuario, descripcion, tipoIcono, nombrePunto));
                        }
                    }
                }
//                //hasta que se encuentre una herramienta de sincronizacion se añaden los marcadores faltantes.
//                addMarkers(map,PuntoDeInteres.leer(getBaseContext()));
//                hacerInvisibleBarraProgreso();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    /**
     * Metodo que añade los marcadores al mapa.
     * @param mapboxMap
     * @param puntosDeInteres
     */
    private void addMarkers(final MapboxMap mapboxMap , List<PuntoDeInteres> puntosDeInteres){
        List<Feature> symbolLayerIconFeatureList = new ArrayList<>();
        boolean[] filtros = {getPreferenceValue("facultad").equals("on"),
                             getPreferenceValue("soda").equals("on"),
                             getPreferenceValue("parada").equals("on"),
                             getPreferenceValue("fotocopiadora").equals("on")};
        //permitimos que hayan iconos cerca.
        symbolLayerManager.deleteAll();
        symbolLayerManager.setIconAllowOverlap(true);
        symbolLayerManager.setTextAllowOverlap(true);
        symbolLayerManager.addClickListener(new OnSymbolClickListener() {
            @Override
            public boolean onAnnotationClick(Symbol symbol) {
                TextView titulo = (TextView) findViewById(R.id.titulo);
                TextView descripcion = (TextView) findViewById(R.id.descripcion);
                titulo.setText(symbol.getTextField());
                descripcion.setText(PuntoDeInteres.leerDescripcion(getBaseContext(), symbol.getTextField()));

                marcador_seleccionado = MarcadorFavorito.leerMarcadorPorNombre(getApplicationContext(), titulo.getText().toString());

                if (marcador_seleccionado == null) {
                    botonFavorito.setChecked(false);
                } else {
                    botonFavorito.setChecked(true);
                }

                posicionUltimaAnotacion = symbol.getLatLng();

                 /*
                ImageButton caminarA = findViewById(R.id.caminar_a);
                ImageButton detalles = findViewById(R.id.detalles);
                caminarA.setImageResource(R.drawable.walk_to_celeste);
                detalles.setImageResource(R.drawable.detalles_celeste);
                  */

                CameraPosition camera = new CameraPosition.Builder().target(symbol.getLatLng()).zoom(18).build();
                mapboxMap.animateCamera(CameraUpdateFactory.newCameraPosition(camera), 1000, new MapboxMap.CancelableCallback() {
                    @Override
                    public void onCancel() {
                        //findViewById(R.id.contenedor).setVisibility(View.GONE);
                    }

                    @Override
                    public void onFinish() {
                        showDetails();
                    }
                });
                return false;
            }


        });
        float offsetX = 0f;
        float offsetY = -9f;
        for (MarcadorFavorito marcador: marcadoresFavoritos) {
            symbolLayerManager.create(new SymbolOptions()
                    .withLatLng(new LatLng(marcador.getLatitud(), marcador.getLongitud()))
                    .withIconImage(ICONO_MARCADOR)
                    .withIconSize(1.0f)
                    .withTextField(marcador.getNombre())
                    .withTextSize(8.0f)
                    .withTextColor("#fff")
                    .withIconOffset(new Float[]{offsetX, offsetY}));
        }
        for (PuntoDeInteres punto:puntosDeInteres) {
            switch (punto.getTipo())
            {
                case "Facultad":
                    if(filtros[0]) {
                        symbolLayerManager.create(new SymbolOptions()
                                .withLatLng(new LatLng(punto.getCoordenadaX(), punto.getCoordenadaY()))
                                .withIconImage(ICONO_FACULTADES)
                                .withIconSize(1.0f)
                                .withTextField(punto.getNombre())
                                .withTextSize(8.0f)
                                .withTextColor("#fff")
                                .withIconOffset(new Float[]{offsetX, offsetY}));
                    }
                    break;
                case "Soda":
                    if(filtros[1]) {
                        symbolLayerManager.create(new SymbolOptions()
                                .withLatLng(new LatLng(punto.getCoordenadaX(), punto.getCoordenadaY()))
                                .withIconImage(ICONO_SODAS)
                                .withIconSize(1.0f)
                                .withTextField(punto.getNombre())
                                .withTextSize(8.0f)
                                .withTextColor("#fff")
                                .withIconOffset(new Float[]{offsetX, offsetY}));
                    }
                    break;
                case "Parada":
                    if(filtros[2]) {
                        symbolLayerManager.create(new SymbolOptions()
                                .withLatLng(new LatLng(punto.getCoordenadaX(), punto.getCoordenadaY()))
                                .withIconImage(ICONO_PARADAS)
                                .withIconSize(1.0f)
                                .withTextField(punto.getNombre())
                                .withTextSize(8.0f)
                                .withTextColor("#fff")
                                .withIconOffset(new Float[]{offsetX, offsetY}));
                    }
                    break;
                case "Fotocopiadora":
                    if(filtros[3]) {
                        symbolLayerManager.create(new SymbolOptions()
                                .withLatLng(new LatLng(punto.getCoordenadaX(), punto.getCoordenadaY()))
                                .withIconImage(ICONO_FOTOCOPIADORAS)
                                .withIconSize(1.0f)
                                .withTextField(punto.getNombre())
                                .withTextSize(8.0f)
                                .withTextColor("#fff")
                                .withIconOffset(new Float[]{offsetX, offsetY}));
                    }
                    break;
                case "Personalizado":
                    symbolLayerManager.create(new SymbolOptions()
                            .withLatLng(new LatLng(punto.getCoordenadaX(), punto.getCoordenadaY()))
                            .withIconImage(ICONO_FAVORITO)
                            .withIconSize(1.0f)
                            .withTextField(punto.getNombre())
                            .withTextSize(8.0f)
                            .withTextColor("#fff")
                            .withIconOffset(new Float[]{offsetX, offsetY}));
                    break;
            }
        }
    }

    private void cargarImagenes(@NonNull Style loadedMapStyle) {
        //Cargamos las imagenes
        loadedMapStyle.addImage(ICONO_FACULTADES, BitmapUtils.getBitmapFromDrawable(getResources().getDrawable(R.drawable.ic_facultadad_blue)));
        loadedMapStyle.addImage(ICONO_FOTOCOPIADORAS, BitmapUtils.getBitmapFromDrawable(getResources().getDrawable(R.drawable.ic_fotocopiadora_green)));
        loadedMapStyle.addImage(ICONO_PARADAS, BitmapUtils.getBitmapFromDrawable(getResources().getDrawable(R.drawable.ic_parada_red)));
        loadedMapStyle.addImage(ICONO_SODAS, BitmapUtils.getBitmapFromDrawable(getResources().getDrawable(R.drawable.ic_soda_yellow)));
        loadedMapStyle.addImage(ICONO_FAVORITO, BitmapUtils.getBitmapFromDrawable(getResources().getDrawable(R.drawable.ic_fav_icon)));
        loadedMapStyle.addImage(ICONO_MARCADOR, BitmapUtils.getBitmapFromDrawable(getResources().getDrawable(R.drawable.ic_baseline_location_on_24)));
    }

    public void getScreenSize() {
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        height = (int)(displayMetrics.heightPixels / displayMetrics.density);
        width = (int)(displayMetrics.widthPixels / displayMetrics.density);
    }

    private void showDetails() {
        //Pone visible el contenedor de los marcadores y los botones

        findViewById(R.id.contenedor).setVisibility(View.VISIBLE);
        findViewById(R.id.caminar_a).setVisibility(View.VISIBLE);
        findViewById(R.id.detalles).setVisibility(View.VISIBLE);


        LinearLayout ll = (LinearLayout) findViewById(R.id.popup);
        ViewGroup.LayoutParams params = ll.getLayoutParams();
        params.height = (int) (-2);
        ll.setLayoutParams(params);
    }

    private void hideDetails() {
        try{
            findViewById(R.id.contenedor).setVisibility(View.GONE);
            findViewById(R.id.caminar_a).setVisibility(View.GONE);
            findViewById(R.id.detalles).setVisibility(View.GONE);
        LinearLayout ll = (LinearLayout) findViewById(R.id.popup);
        if((ll.getHeight() != 0)) {
            ViewGroup.LayoutParams params = ll.getLayoutParams();
            params.height = 0;
            ll.setLayoutParams(params);
        }
        }
        catch(NullPointerException ex) {
            //probablemente la vista ya no esta disponible
            Log.w("HIDE_DETAILES_MAIN_ACT",ex.getMessage());
        }

    }

    private String getPreferenceValue(String filtro)
    {
        SharedPreferences sp = getSharedPreferences("filtros",0);
        String str = sp.getString(filtro,"on");
        return str;
    }

    private void hacerVisibleBarraProgreso(){
        ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBarMainActivity);
        progressBar.setVisibility(View.VISIBLE);
    }

    private void hacerInvisibleBarraProgreso(){
        ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBarMainActivity);
        progressBar.setVisibility(View.INVISIBLE);
    }

    private void setMapFocus(int numeroFinca){
        switch (numeroFinca){
            case 1:
                CameraPosition cameraPosition = new CameraPosition.Builder()
                        .target(new LatLng(9.937423,-84.050419))
                        .zoom(15.5)
                        .build();
                map.animateCamera(CameraUpdateFactory
                        .newCameraPosition(cameraPosition), 2000);
                break;
            case 2:
                CameraPosition cameraPosition2 = new CameraPosition.Builder()
                        .target(new LatLng(9.943743, -84.044856))
                        .zoom(15.5)
                        .build();
                map.animateCamera(CameraUpdateFactory
                        .newCameraPosition(cameraPosition2), 2000);
                break;
            case 3:
                CameraPosition cameraPosition3 = new CameraPosition.Builder()
                        .target(new LatLng(9.938433, -84.043687))
                        .zoom(15.5)
                        .build();
                map.animateCamera(CameraUpdateFactory
                        .newCameraPosition(cameraPosition3), 2000);
                break;
        }
    }

    private void setMapFocusOnMarker(int markerId) {
        slidingRootNavBuilder.closeMenu(true);

        MarcadorFavorito marcadorSeleccionado = MarcadorFavorito.leerPorId(getApplicationContext(), String.valueOf(markerId));

//        toast(marcadorSeleccionado.getLongitud() + " " + marcadorSeleccionado.getLatitud());

        CameraPosition position = new CameraPosition.Builder()
                .target(new LatLng(marcadorSeleccionado.getLatitud(), marcadorSeleccionado.getLongitud()))
                .zoom(18)
                .build();
        map.animateCamera(CameraUpdateFactory
                .newCameraPosition(position), 2000);
    }

    private boolean estaLogueado(){
        FirebaseAuth fAuth;
        fAuth = FirebaseAuth.getInstance();
        if(fAuth.getCurrentUser() != null){
          return true;
        }
        return false;
    }
}

