package com.example.petmanagment.ui.home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.net.Uri;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.petmanagment.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class DateActivity extends AppCompatActivity{
    private static final int PERMISSION_GRANTED = 1;
    private Spinner spinner;
    private Spinner spinner2;
    private String selected;
    private String selectedP;
    private List<String>pets;
    private Spinner pSpinner;
    private CalendarView calendar;
    private String selectedDate;
    private TextView sDate;
    final int callbackId = 42;
    GregorianCalendar storeDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date);
        final Spinner cSpinner = (Spinner) findViewById(R.id.spinner);
        pSpinner = (Spinner) findViewById(R.id.spinner2);
        FirebaseUser  user = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        List <String> customers = new ArrayList<>();
        pets = new ArrayList<>();
        calendar = (CalendarView) findViewById (R.id.calendarView);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        selectedDate = sdf.format(new Date(calendar.getDate()));
        sDate = (TextView)  findViewById(R.id.selectedDate);
        sDate.setText(selectedDate);
        Button confirmBtn = (Button) findViewById(R.id.confirmBtn);

        checkPermission(callbackId, Manifest.permission.READ_CALENDAR, Manifest.permission.WRITE_CALENDAR);


        db.collection(user.getEmail()).get().addOnSuccessListener(queryDocumentSnapshots -> {
            for(QueryDocumentSnapshot q: queryDocumentSnapshots){
                customers.add(q.getId());
            }
        }).addOnCompleteListener(task -> {
            final ArrayAdapter<String> content = new ArrayAdapter<String>
                    (getApplicationContext(), R.layout.spinner_item, customers);
            content.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            cSpinner.setAdapter(content);
        });

       cSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
           @Override
           public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
               if(!pets.isEmpty()) pets.clear();
               selected = adapterView.getItemAtPosition(i).toString();
               db.collection(user.getEmail()).document(selected).collection(selected).get().addOnSuccessListener(queryDocumentSnapshots -> {
                   for(QueryDocumentSnapshot q : queryDocumentSnapshots){
                       pets.add(q.getId());
                   }
               }).addOnCompleteListener(task1 -> {
                   final ArrayAdapter<String> content2 = new ArrayAdapter<>
                           (getApplicationContext(), R.layout.spinner_item, pets);
                   content2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                   pSpinner.setAdapter(content2);
               });
           }

           @Override
           public void onNothingSelected(AdapterView<?> adapterView) {

           }
       });

       pSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
           @Override
           public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
               selectedP = adapterView.getItemAtPosition(i).toString();

           }

           @Override
           public void onNothingSelected(AdapterView<?> adapterView) {

           }
       });

       calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
           @Override
           public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {
               sDate.setText(String.format("%s/%s/%s",String.valueOf(i2), String.valueOf(i1+1),String.valueOf(i)));
               storeDate = new GregorianCalendar(i,i1,i2);
           }
       });

       confirmBtn.setOnClickListener(view -> {
           ContentResolver cr = this.getContentResolver();
           ContentValues cv = new ContentValues();
           cv.put(CalendarContract.Events.CALENDAR_ID,1);
           cv.put(CalendarContract.Events.TITLE, String.format("Date with %s, visiting %s", selected, selectedP));
           cv.put(CalendarContract.Events.DESCRIPTION, String.format("Normal date"));
           cv.put(CalendarContract.Events.DTSTART, storeDate.getTimeInMillis());
           cv.put(CalendarContract.Events.DTEND, storeDate.getTimeInMillis()+(60*60*1000));
           cv.put(CalendarContract.Events.EVENT_TIMEZONE, Calendar.getInstance().getTimeZone().getID());
           Uri uri = cr.insert(CalendarContract.Events.CONTENT_URI,cv);
           Toast.makeText(this, "Event succesfully added", Toast.LENGTH_LONG).show();
       });

    }
    private void checkPermission(int callbackId, String... permissionsId) {
        boolean permissions = true;
        for (String p : permissionsId) {
            permissions = permissions && ContextCompat.checkSelfPermission(this, p) == PERMISSION_GRANTED;
        }

        if (!permissions)
            ActivityCompat.requestPermissions(this, permissionsId, callbackId);
    }

}