package dcns.ru.gpsclient;

import android.location.LocationManager;
import android.preference.PreferenceManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import org.osmdroid.api.IMapController;
import org.osmdroid.config.Configuration;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.util.LocationUtils;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.compass.CompassOverlay;
import org.osmdroid.views.overlay.compass.InternalCompassOrientationProvider;
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider;
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay;

public class MainActivity extends AppCompatActivity {

    private MapView mapView;
    private IMapController mapController;
    private MyLocationNewOverlay myLocationNewOverlay;
    private CompassOverlay compassOverlay;

    @Override
    public void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);
        Configuration.getInstance().load(getApplicationContext(), PreferenceManager.getDefaultSharedPreferences(getApplicationContext()));

        setContentView(R.layout.activity_main);
        mapView = (MapView) findViewById(R.id.mapView);
        mapView.setBuiltInZoomControls(true);
        mapView.setMultiTouchControls(true);

        GpsMyLocationProvider provider = new GpsMyLocationProvider(this);
        provider.addLocationSource(LocationManager.NETWORK_PROVIDER);
        myLocationNewOverlay = new MyLocationNewOverlay(provider, mapView);
        myLocationNewOverlay.enableMyLocation();

        compassOverlay = new CompassOverlay(this, new InternalCompassOrientationProvider(this),mapView);
        compassOverlay.enableCompass();

//        mapView.getOverlays().add(myLocationNewOverlay);

        mapController = mapView.getController();
        mapController.setZoom(15.0);
        mapController.setCenter(new GeoPoint(48.480309, 135.072003));

        myLocationNewOverlay.setDrawAccuracyEnabled(true);

        myLocationNewOverlay.runOnFirstFix(new Runnable(){
            public void run() {
                final GeoPoint myLocation = myLocationNewOverlay.getMyLocation();
                if (myLocation != null) {
                    MainActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mapView.getOverlays().add(myLocationNewOverlay);
                            mapController.animateTo(myLocationNewOverlay.getMyLocation());
                        }
                    });
                };

            }
        });
    }


    @Override
    public void onResume(){
        super.onResume();
        Configuration.getInstance().load(getApplicationContext(), PreferenceManager.getDefaultSharedPreferences(getApplicationContext()));
        if (mapView!=null)
            mapView.onResume();
        myLocationNewOverlay.enableMyLocation();
        compassOverlay.disableCompass();
    }

    @Override
    public void onPause(){
        super.onPause();
        Configuration.getInstance().save(getApplicationContext(), PreferenceManager.getDefaultSharedPreferences(getApplicationContext()));
        if (mapView!=null)
            mapView.onPause();
        myLocationNewOverlay.disableMyLocation();
        compassOverlay.disableCompass();
    }
}
