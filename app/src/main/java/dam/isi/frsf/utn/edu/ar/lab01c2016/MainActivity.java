package dam.isi.frsf.utn.edu.ar.lab01c2016;

import android.graphics.Color;
import android.icu.text.NumberFormat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.PhoneNumberUtils;
import android.text.method.NumberKeyListener;
import android.util.Patterns;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button = (Button) findViewById(R.id.buttonPlazo);
        button.setOnClickListener( new View.OnClickListener(){
            @Override
            public void onClick(View v){
                EditText email = (EditText) findViewById(R.id.editTextEmail);
                EditText cuit = (EditText) findViewById(R.id.editTextCuit);
                EditText importe = (EditText) findViewById(R.id.editTextImporte);
                SeekBar dias = (SeekBar) findViewById(R.id.seekBar);
                CheckBox checkRenovarVencimiento = (CheckBox)
                        findViewById(R.id.checkBoxRenovarVencimiento);
                Boolean valid = true;
                if (!Patterns.EMAIL_ADDRESS.matcher(email.getText()).matches()){
                    valid = false;
                }
                //TODO: checkear

                if( cuit.getText().toString().matches("[0-9]{11}")){
                    valid = false;
                }
                // TODO checkear
                if(importe.getText().toString().matches("[-]?[0-9]*\\.?[0-9]*")){
                    valid = false;
                }
                double tasa = 0.0;
                double numDias = 0.0;
                double interes = 0.0;
                if(!importe.getText().toString().isEmpty()){
                    interes = Double.parseDouble(importe.getText().toString()) *
                            (Math.pow(1+(tasa/100),(numDias/360))-1);
                }
                TextView resultado = (TextView) findViewById(R.id.textViewResultado);

                if (valid){
                    String strAux = "Plazo fijo realizado. Recibirá " + interes + " al vencimiento!";
                    resultado.setTextColor(getResources().getColor(R.color.VERDE));
                    resultado.setText(strAux);
                }
                else {
                    resultado.setText(R.string.mensajeResultadoError);
                    resultado.setTextColor(getResources().getColor(R.color.ROJO));
                }

            }
        });
    }
}