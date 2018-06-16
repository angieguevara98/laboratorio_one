package com.example.luist.laboratorio_one;

import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.RingtoneManager;
import android.os.Handler;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class OrderActivity extends AppCompatActivity {

    private Spinner list_spinner_pizza;
    private String precio_tipo_pizza ;
    private String precio_complemento_queso = "0";
    private String precio_complemento_jamon = "0";
    private String select_tipomasa = "";

    Calendar fecha = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        list_spinner_pizza = (Spinner) findViewById(R.id.spinner_list);
    }

    public void obtenemoscomplementos(View view) {

        boolean checked = ((CheckBox) view).isChecked();
        switch (view.getId()) {
            case R.id.extraQueso:
                if (checked){
                    precio_complemento_queso = "4";}
                    else{
                    precio_complemento_queso = "0";
                }
                break;
            case R.id.extraJamon:
                if (checked){
                    precio_complemento_jamon = "8";}
                    else{
                    precio_complemento_jamon = "0";
                }
                break;
        }
    }

    public void tipomasa(View view) {

        boolean checked = ((RadioButton) view).isChecked();
        switch (view.getId()) {
            case R.id.masaGruesa:
                if (checked){
                    select_tipomasa = "Masa Gruesa";}
                break;
            case R.id.masaDelgada:
                if (checked){
                    select_tipomasa = "Masa Delgada";}
                break;
            case R.id.masaArtesanal:
                if (checked){
                    select_tipomasa = "Masa Artesanal";}
                break;
        }
    }

    public void showvalue(View view){

        String item = (String)list_spinner_pizza.getSelectedItem();
        long IdSpinner = list_spinner_pizza.getSelectedItemId();
        if(IdSpinner == 0){
            precio_tipo_pizza = "38";
        }else if(IdSpinner == 1){
            precio_tipo_pizza = "42";
        }else if(IdSpinner == 2){
            precio_tipo_pizza = "36";
        }else if(IdSpinner == 3){
            precio_tipo_pizza = "56";
        }

        int valor = Integer.parseInt(precio_tipo_pizza) + Integer.parseInt(precio_complemento_queso) + Integer.parseInt(precio_complemento_jamon);

        int dia_semana = fecha.get(Calendar.DAY_OF_WEEK);

        //Si es sabado realizamos un descuento del 30%
        if (dia_semana==7)
        {
            valor = valor - ((valor * 30)/100);
        }

        String result= String.valueOf(valor);

        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle("Confirmacion de pedido");
        alertDialog.setMessage("Su pedido de " + item + " con " + select_tipomasa + " a S/." + result + " + IGV esta en proceso de envio" );
        // Alert dialog button
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Alert dialog action goes here
                        dialog.dismiss();// use dismiss to cancel alert dialog
                    }
                });
        alertDialog.show();

        new Handler().postDelayed(new Runnable() {
            public void run() {
                showNotification();
            }
        }, 3000);

    }

    public void showNotification() {

        //Intent intent = new Intent(this, ScrollViewActivity.class);
        //intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        //PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);

        // Notification
        Notification notification = new NotificationCompat.Builder(this,"Default")
                .setContentTitle("Solicitud Pedido")
                .setContentText("Se esta realizando su pedido...favor de esperar")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setColor(ContextCompat.getColor(this, R.color.colorPrimary))
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                // .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .build();

        // Notification manager
        NotificationManager notificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, notification);

    }

}
