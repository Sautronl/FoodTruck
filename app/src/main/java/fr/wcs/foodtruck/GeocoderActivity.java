package fr.wcs.foodtruck;

import android.content.Intent;
import android.location.Address;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class GeocoderActivity extends AppCompatActivity {

    int jour;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    final DatabaseReference coordonnerRef = database.getReference("Coordonner");

    AddressResultReceiver mResultReceiver;

    EditText latitudeEdit, longitudeEdit, addressEdit;
    ProgressBar progressBar;
    TextView infoText;
    TextView infoTextLat;
    TextView infoTextLong;
    CheckBox checkBox;
    Button actionButton;
    Double lat;
    Double lon;
    String adrs;

    boolean fetchAddress;
    int fetchType = ConstantsAdminGeocoder.USE_ADDRESS_LOCATION;

    private static final String TAG = "MAIN_ACTIVITY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_geocoder);

        longitudeEdit = (EditText) findViewById(R.id.longitudeEdit);
        latitudeEdit = (EditText) findViewById(R.id.latitudeEdit);
        addressEdit = (EditText) findViewById(R.id.addressEdit);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        infoText = (TextView) findViewById(R.id.infoText);
        infoTextLat = (TextView) findViewById(R.id.infoTextLat);
        infoTextLong = (TextView) findViewById(R.id.infoTextLong);
        checkBox = (CheckBox) findViewById(R.id.checkbox);
        actionButton = (Button) findViewById(R.id.actionButton) ;

        mResultReceiver = new AddressResultReceiver(null);
    }

    public void onRadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();

        switch(view.getId()) {
            case R.id.radioAddress:
                if (checked) {
                    fetchAddress = false;
                    fetchType = ConstantsAdminGeocoder.USE_ADDRESS_NAME;
                    longitudeEdit.setEnabled(false);
                    latitudeEdit.setEnabled(false);
                    addressEdit.setEnabled(true);
                    addressEdit.requestFocus();
                }
                break;
            case R.id.radioLocation:
                if (checked) {
                    fetchAddress = true;
                    fetchType = ConstantsAdminGeocoder.USE_ADDRESS_LOCATION;
                    latitudeEdit.setEnabled(true);
                    latitudeEdit.requestFocus();
                    longitudeEdit.setEnabled(true);
                    addressEdit.setEnabled(false);
                }
                break;
        }
    }

    public void onButtonClicked(View view) {
        Intent intent = new Intent(this, GeocodeAddressIntentService.class);
        intent.putExtra(ConstantsAdminGeocoder.RECEIVER, mResultReceiver);
        intent.putExtra(ConstantsAdminGeocoder.FETCH_TYPE_EXTRA, fetchType);
        if(fetchType == ConstantsAdminGeocoder.USE_ADDRESS_NAME) {
            if(addressEdit.getText().length() == 0) {
                Toast.makeText(this, "Veillez remplir votre adresse", Toast.LENGTH_LONG).show();
                return;
            }
            intent.putExtra(ConstantsAdminGeocoder.LOCATION_NAME_DATA_EXTRA, addressEdit.getText().toString());
        }
        else {
            if(latitudeEdit.getText().length() == 0 || longitudeEdit.getText().length() == 0) {
                Toast.makeText(this,
                        "Veillez Confirmer votre adresse",
                        Toast.LENGTH_LONG).show();
                return;
            }
            intent.putExtra(ConstantsAdminGeocoder.LOCATION_LATITUDE_DATA_EXTRA,
                    Double.parseDouble(latitudeEdit.getText().toString()));
            intent.putExtra(ConstantsAdminGeocoder.LOCATION_LONGITUDE_DATA_EXTRA,
                    Double.parseDouble(longitudeEdit.getText().toString()));
        }
        infoText.setVisibility(View.INVISIBLE);
        infoTextLat.setVisibility(View.INVISIBLE);
        infoTextLong.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.VISIBLE);
        Log.e(TAG, "Starting Service");
        startService(intent);




    }

    class AddressResultReceiver extends ResultReceiver {
        public AddressResultReceiver(Handler handler) {
            super(handler);
        }

        @Override
        protected void onReceiveResult(int resultCode, final Bundle resultData) {
            if (resultCode == ConstantsAdminGeocoder.SUCCESS_RESULT) {
                final Address address = resultData.getParcelable(ConstantsAdminGeocoder.RESULT_ADDRESS);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        progressBar.setVisibility(View.GONE);
                        infoText.setVisibility(View.VISIBLE);
                        lat = address.getLatitude();
                        lon = address.getLongitude();
                        adrs = addressEdit.getText().toString();
                        infoTextLat.setText("Latitude: " + address.getLatitude() + "\n" );
                        infoTextLong.setText("Longitude: " + address.getLongitude() + "\n");
                        infoText.setText("Adresse: " + resultData.getString(ConstantsAdminGeocoder.RESULT_DATA_KEY));

                        jour = getIntent().getIntExtra("jour", 0);

                        if (jour == 0){
                            GeocoderModel coord = new GeocoderModel(lat, lon, adrs);
                            coordonnerRef.child("1 Lundi").setValue(coord);
                            addPreviousAdrs("1 Lundi/adrs");
                        }else if (jour == 1){
                            GeocoderModel coord = new GeocoderModel(lat, lon, adrs);
                            coordonnerRef.child("2 Mardi").setValue(coord);
                            addPreviousAdrs("2 Mardi/adrs");
                        }else if (jour == 2) {
                            GeocoderModel coord = new GeocoderModel(lat, lon, adrs);
                            coordonnerRef.child("3 Mercredi").setValue(coord);
                            addPreviousAdrs("3 Mercredi/adrs");
                        }else if (jour == 3) {
                            GeocoderModel coord = new GeocoderModel(lat, lon, adrs);
                            coordonnerRef.child("4 Jeudi").setValue(coord);
                            addPreviousAdrs("4 Jeudi/adrs");
                        }else if (jour == 4) {
                            GeocoderModel coord = new GeocoderModel(lat, lon, adrs);
                            coordonnerRef.child("5 Vendredi").setValue(coord);
                            addPreviousAdrs("5 Vendredi/adrs");
                        }

                    }
                });
            }
            else {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        progressBar.setVisibility(View.GONE );
                        infoText.setVisibility(View.VISIBLE);
                        infoText.setText(resultData.getString(ConstantsAdminGeocoder.RESULT_DATA_KEY));
                        infoTextLong.setText(resultData.getString(ConstantsAdminGeocoder.RESULT_DATA_KEY));
                        infoTextLat.setText(resultData.getString(ConstantsAdminGeocoder.RESULT_DATA_KEY));

                    }
                });
            }
        }
    }

    private void addPreviousAdrs(String adrprev){
        coordonnerRef.child(adrprev).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String adrsprevious = dataSnapshot.getValue(String.class);
                addressEdit.setText(adrsprevious);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

}
