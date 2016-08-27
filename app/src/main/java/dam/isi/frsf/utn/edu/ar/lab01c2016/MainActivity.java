package dam.isi.frsf.utn.edu.ar.lab01c2016;

import android.graphics.Color;
import android.icu.text.NumberFormat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.PhoneNumberUtils;
import android.text.method.NumberKeyListener;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button = (Button) findViewById(R.id.buttonPlazo);
        button.setOnClickListener( new View.OnClickListener(){
            @Override
            public void onClick(View v){
                EditText etEmail = (EditText) findViewById(R.id.editTextEmail);
                EditText etCuit = (EditText) findViewById(R.id.editTextCuit);
                EditText etImporte = (EditText) findViewById(R.id.editTextImporte);
                SeekBar dias = (SeekBar) findViewById(R.id.seekBar);
                TextView tvMonto = (TextView) findViewById(R.id.textViewMontoNum);
                TextView resultado = (TextView) findViewById(R.id.textViewResultado);
                Boolean valid = true;

                if (!Patterns.EMAIL_ADDRESS.matcher(etEmail.getText()).matches()){
                    valid = false;
                    etEmail.setError(getResources().getString(R.string.emailError));
                }

                if( !etCuit.getText().toString().matches("[0-9]{11}")){
                    valid = false;
                    etCuit.setError(getResources().getString(R.string.cuitError));
                }

                String strImporte = etImporte.getText().toString();
                if(!strImporte.matches("[-]?[0-9]*\\.?[0-9]*")
                        || Objects.equals(strImporte, "") || Objects.equals(strImporte, ".")){
                    valid = false;
                    etImporte.setError(getResources().getString(R.string.importeError));
                }

                if (!valid){
                    resultado.setText(R.string.mensajeResultadoError);
                    resultado.setTextColor(getResources().getColor(R.color.ROJO));
                    return;
                }

                double dImporte = Double.parseDouble(strImporte);
                double numDias = dias.getProgress();
                double tasa;
                if(dImporte <= 5000){
                    if(numDias < 30) {
                        tasa = Double.parseDouble(getResources().getString(R.string.tasa0_5000_menos30));
                    } else {
                        tasa = Double.parseDouble(getResources().getString(R.string.tasa0_5000_30oMas));
                    }
                } else if(dImporte <= 99999) {
                    if(numDias < 30) {
                        tasa = Double.parseDouble(getResources().getString(R.string.tasa5000_99999_menos30));
                    } else {
                        tasa = Double.parseDouble(getResources().getString(R.string.tasa5000_99999_30oMas));
                    }
                } else {
                    if(numDias < 30) {
                        tasa = Double.parseDouble(getResources().getString(R.string.tasaMas99999_menos30));
                    } else {
                        tasa = Double.parseDouble(getResources().getString(R.string.tasaMas99999_30oMas));
                    }
                }


                double dInteres = dImporte * (Math.pow(1 + (tasa / 100), (numDias / 360)) - 1);

                String strAux = String.format("$%.1f",dInteres);
                tvMonto.setText(strAux);
                strAux = String.format("Plazo fijo realizado. Recibirá %.1f al vencimiento!",dInteres);
                resultado.setTextColor(getResources().getColor(R.color.VERDE));
                resultado.setText(strAux);

            }
        });

        SeekBar seekBar = (SeekBar) findViewById(R.id.seekBar);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                TextView tvCantidadDias = (TextView) findViewById(R.id.textViewCantidadDiasNum);
                tvCantidadDias.setText(String.format("..%d", progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });
    }
}