package fr.wcs.foodtruck;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class AdminEvent extends AppCompatActivity {

    //deb
    private final String TAG = "AdminEvent";

    private EditText input_name,input_details, input_date;
    private ListView list_data;
    private ProgressBar circular_progress;

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;

    private List<EventModel> list_events = new ArrayList<>();

    private EventModel selectedEvent; //hold event when we select item in listView

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_event);

        //Add Toolbar
        Toolbar toolbar =(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Control
        circular_progress = (ProgressBar)findViewById(R.id.circular_progress);

        //Formulaire
        input_name = (EditText)findViewById(R.id.name);
        input_details = (EditText)findViewById(R.id.details);
        input_date = (EditText)findViewById(R.id.date);

        //Liste d'events
        list_data = (ListView)findViewById(R.id.list_data);
        list_data.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                EventModel event = (EventModel) adapterView.getItemAtPosition(i);
                selectedEvent = event;
                input_name.setText(event.getName());
                input_details.setText(event.getDetails());
                input_date.setText(event.getDate());
            }
        });

        input_date.setFocusable(false);

        //Date Picker
        final Calendar myCalendar = Calendar.getInstance();
        final DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

                input_date.setText(sdf.format(myCalendar.getTime()));
            }
        };

        input_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog date =
                new DatePickerDialog(AdminEvent.this, datePickerListener,
                        myCalendar.get(Calendar.YEAR),
                        myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH));
                date.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);//Désactive les jours précédents
                date.show();

            }
        });

        //Firebase
        initFirebase();
        addEventFirebaseListener();

    }

    private void initFirebase() {
        FirebaseApp.initializeApp(this);
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mFirebaseDatabase.getReference();
    }

    private void addEventFirebaseListener() {

        //Progressing
        circular_progress.setVisibility(View.VISIBLE);
        list_data.setVisibility(View.INVISIBLE);

        mDatabaseReference.child("events").orderByChild("date").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (list_events.size() > 0)
                    list_events.clear();
                for(DataSnapshot postSnapshot:dataSnapshot.getChildren()){
                    EventModel event = postSnapshot.getValue(EventModel.class);
                    list_events.add(event);
                }
                ListEventAdapter adapter = new ListEventAdapter(AdminEvent.this,list_events);
                list_data.setAdapter(adapter);
                circular_progress.setVisibility(View.INVISIBLE);
                list_data.setVisibility(View.VISIBLE);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    //Clic on item menu (toolbar)
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_add)
        {
            createEvent();
        }
        else if(item.getItemId() == R.id.menu_save)
        {
            EventModel event = new EventModel(selectedEvent.getEid(),input_name.getText().toString(),
                    input_details.getText().toString(), input_date.getText().toString());
            updateEvent(event);
        }
        else if(item.getItemId() == R.id.menu_remove)
        {
            deleteEvent(selectedEvent);
        }

        else if(item.getItemId() == R.id.backButton)
        {
            Intent intent = new Intent(AdminEvent.this, AdminAccueil.class);
            startActivity(intent);
        }
        return true;
    }

    //Create Event
    private void createEvent() {
        EventModel event = new EventModel(UUID.randomUUID().toString(),input_name.getText().toString(),
                input_details.getText().toString(), input_date.getText().toString());
        mDatabaseReference.child("events").child(event.getEid()).setValue(event);
        clearEditText();
    }

    //Update Event
    private void updateEvent(EventModel event) {
        mDatabaseReference.child("events").child(event.getEid()).child("name").setValue(event.getName());
        mDatabaseReference.child("events").child(event.getEid()).child("details").setValue(event.getDetails());
        mDatabaseReference.child("events").child(event.getEid()).child("date").setValue(event.getDate());
        clearEditText();
    }

    //Delete Event
    private void deleteEvent(EventModel selectedEvent) {
        mDatabaseReference.child("events").child(selectedEvent.getEid()).removeValue();
        clearEditText();
    }

    private void clearEditText() {
        input_name.setText("");
        input_details.setText("");
        input_date.setText("");
    }
}
