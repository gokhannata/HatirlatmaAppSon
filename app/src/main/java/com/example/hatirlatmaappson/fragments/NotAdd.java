package com.example.hatirlatmaappson.fragments;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.example.hatirlatmaappson.Not;
import com.example.hatirlatmaappson.Notification_receiver;
import com.example.hatirlatmaappson.R;
import com.example.hatirlatmaappson.SettingsActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class NotAdd extends Fragment {

    DatabaseReference db;
    EditText editTextNotBaslik;//Not Başlık
    EditText editTextNotIcerik;//Not İçerik
    TextView textViewTarih;//Not Tarih
    TextView textViewSaat;//Eklenecek textViewSaat
    TextView textButonKaydet;
    Button buttonKaydet, buttonTarih,buttonSaat;
    Calendar calendar;
    DatePickerDialog datePickerDialog;
    TimePickerDialog timePickerDialog;
    TimePicker alarmTimePicker;
    MenuItem settings;
    String uID;
    FirebaseAuth mAuth;
    int guncelYil,guncelAy,guncelGun,saat,dakika,guncelSaat,guncelDakika,yil,ay,gun;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_not_add, container, false);

        mAuth = FirebaseAuth.getInstance();
        uID = mAuth.getUid();

        editTextNotBaslik = view.findViewById(R.id.editTextNotBaslik);
        editTextNotIcerik = view.findViewById(R.id.editTextNotIcerik);
        textViewTarih = view.findViewById(R.id.textViewNotTarihi);
        textViewSaat=view.findViewById(R.id.textViewNotSaati);
        buttonKaydet = view.findViewById(R.id.buttonNotKaydet);
        buttonTarih = view.findViewById(R.id.buttonTarih);
        buttonSaat=view.findViewById(R.id.buttonSaat);

        /*
        Buralar yeni eklendi.
         */
        buttonSaat.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {

                  calendar=Calendar.getInstance();
                  saat = calendar.get(Calendar.HOUR_OF_DAY);
                  dakika = calendar.get(Calendar.MINUTE);
                  //System.out.println("dakika"+dakika+"saat:"+saat);
                  TimePickerDialog tpd = new TimePickerDialog(getActivity(),
                          new TimePickerDialog.OnTimeSetListener() {
                              @Override
                              public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                  // hourOfDay ve minute değerleri seçilen saat değerleridir.
                                  // Edittextte bu değerleri gösteriyoruz.
                                  textViewSaat.setText(hourOfDay + ":" + minute);
                                  guncelSaat=hourOfDay;
                                  guncelDakika=minute;
                              }
                          }, saat, dakika, true);
                  tpd.setButton(TimePickerDialog.BUTTON_POSITIVE, "Seç", tpd);
                  tpd.setButton(TimePickerDialog.BUTTON_NEGATIVE, "İptal", tpd);
                  tpd.show();


              }
          });

        //Buraya kadar
        //bunlar sistem saatini aldığı için çalışmıyor.düzenleyeceksin onDateSet içindekileri
        buttonTarih.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar = Calendar.getInstance();
                gun = calendar.get(Calendar.DAY_OF_MONTH);
                ay = calendar.get(Calendar.MONTH);
                yil = calendar.get(Calendar.YEAR);
                //System.out.println("gün."+day+" ay:"+month+" yıl:"+year);
                datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        textViewTarih.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                        guncelGun=dayOfMonth;
                        guncelAy=month;
                        guncelYil=year;
                    }
                }, gun, ay, yil);

                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
                datePickerDialog.show();
            }
        });

        if (uID != null) {
            db = FirebaseDatabase.getInstance().getReference(uID).child("Notlar");

        } else {
            System.out.println("uıd boş olamaz");
        }
        buttonKaydet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createNotificationChannel();
                //Calendar cal=Calendar.getInstance();
                calendar.set(Calendar.YEAR,(guncelYil));
                calendar.set(Calendar.MONTH,(guncelAy+1));
                calendar.set(Calendar.DAY_OF_MONTH,(guncelGun));
                calendar.set(Calendar.HOUR_OF_DAY,(guncelSaat-1));
                calendar.set(Calendar.MINUTE,(guncelDakika));
                AlarmManager alarmManager=(AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
                Intent intent=new Intent(getActivity(),Notification_receiver.class);
                PendingIntent pendingIntent=PendingIntent.getBroadcast(getActivity(),0,intent,0);
                calendar.setTimeInMillis(System.currentTimeMillis());
                alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),AlarmManager.INTERVAL_DAY,pendingIntent);
                long systemTime=System.currentTimeMillis();
                /*

                Calendar calendar=Calendar.getInstance();
                calendar.set(Calendar.MINUTE,guncelDakika);
                calendar.set(Calendar.HOUR_OF_DAY,guncelSaat-1);
                calendar.set(Calendar.DAY_OF_MONTH,guncelGun);
                calendar.set(Calendar.MONTH,guncelAy+1);
                calendar.set(Calendar.YEAR,guncelYil);
                //System.out.println("dakika"+dakika+"tarih:"+day);

                 */
                //System.out.println("Yıl:"+guncelYil+" Ay:"+(guncelAy+1)+" Gün:"+guncelGun+" Saat:"+(guncelSaat-1)+" dakika:"+guncelDakika);
                //long time=60*60*1000;
                //alarmManager.set(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),pendingIntent);
                /*
                Calendar calendar=Calendar.getInstance();
                Intent intent=new Intent(getActivity(), Notification_receiver.class);
                PendingIntent pendingIntent=PendingIntent.getBroadcast(getActivity(),0,intent,0);
                AlarmManager alarmManager=(AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
                alarmManager.set(AlarmManager.RTC,calendar.getTimeInMillis(),pendingIntent);
                //alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),AlarmManager.INTERVAL_DAY,pendingIntent);
                calendar.set(Calendar.MINUTE,dakika);
                calendar.set(Calendar.HOUR_OF_DAY,saat);
                calendar.set(Calendar.DAY_OF_MONTH,day);
                calendar.set(Calendar.MONTH,month-1);
                calendar.set(Calendar.YEAR,year);
                 */
                btnNotKaydet();
            }
        });
        return view;
    }
    private void createNotificationChannel(){
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            CharSequence name="Reminder App";
            String description="Açıklama buraya gelecek";
            int importance= NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel=new NotificationChannel("notify",name,importance);
            channel.setDescription(description);

            NotificationManager notificationManager=getActivity().getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);

        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu,menu);
        super.onCreateOptionsMenu(menu,inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id=item.getItemId();
        if(id==R.id.setting){
            Intent intent=new Intent(getActivity(), SettingsActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    private void btnNotKaydet() {

        if (!editTextNotBaslik.getText().toString().isEmpty() && !editTextNotIcerik.getText().toString().isEmpty() ) {
            String ad = editTextNotBaslik.getText().toString().trim();
            String icerik = editTextNotIcerik.getText().toString().trim();

            String tarih = textViewTarih.getText().toString();
            String saat=textViewSaat.getText().toString();
            String id = db.push().getKey();
            String notDurum = "0";
            Not not = new Not(id, ad, icerik,tarih,saat,notDurum);

            db.child(id).setValue(not).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    final AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());
                    alertDialog.setTitle("Not Eklendi");

                    alertDialog
                            .setMessage("Not ekleme başarılı.")
                            .setCancelable(false)
                            .setPositiveButton("Tamam", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    editTextNotBaslik.setText("");
                                    editTextNotIcerik.setText("");
                                    dialog.cancel();
                                }
                            });
                    AlertDialog alert = alertDialog.create();
                    alert.show();

                }
            })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getActivity(), "HATA! Not ekleme başarısız", Toast.LENGTH_SHORT).show();
                        }
                    });

        } else {
            Toast.makeText(getContext(), "Lütfen alanları doldurunuz.", Toast.LENGTH_SHORT).show();
        }
    }
}
