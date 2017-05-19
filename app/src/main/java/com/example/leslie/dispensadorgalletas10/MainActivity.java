package com.example.leslie.dispensadorgalletas10;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.ValueDependentColor;
import com.jjoe64.graphview.helper.StaticLabelsFormatter;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;
import java.io.IOException;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TabHost th;
    private TabHost.TabSpec spec;
    String Galleta1 = "Galleta 1";
    String Galleta2 = "Galleta 2";
    String Galleta3 = "Galleta 3";

    // widgets de tab login
    EditText txt_usr, txt_pass;
    Button btn_login;
    String user, pass = "";

    // widgets tab perfil
    Button btn_logout;
    TextView lbl_usr, lbl_rol;

    // widgets tab galletas
    ImageButton btn_g1, btn_g2, btn_g3;
    int galleta = 0;

    // widgets tab registro
    ListView lv_registro;

    // widgets tab estado_admin
    ListView lv_estado, lv_cantidad;

    // widgets tab estadistica_admin
    GraphView graph;

    // widgets taba ayuda

    // ksoap
    private final static String NAMESPACE ="http://Servicios/"; // definir
    public static String URL="http://192.168.0.21:8080/WSOnlineMusic/ActionService?WSDL"; // definir
    private SoapObject request=null;
    private SoapSerializationEnvelope envelope= null;
    private SoapPrimitive resultRequestSoap= null;
    // login
    private final static String METHOD_NAME_LOGIN="ConsultarUsuario";
    private final static String SOAP_ACTION_LOGIN ="http://Servicios/ConsultarUsuario";    // definir
    // peticion
    private final static String METHOD_NAME_REQ="Comprar";
    private final static String SOAP_ACTION_REQ ="http://Servicios/Comprar";      // definir

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //getActionBar().hide();
        iniciaVars();
        construyeTabs();
        visibleLogin();
    }

    private void iniciaVars() {

        th = (TabHost) findViewById(R.id.tabs);
        th.setup();

        // tab login
        txt_usr = (EditText)findViewById(R.id.txt_user);
        txt_pass = (EditText)findViewById(R.id.txt_pass);
        btn_login = (Button)findViewById(R.id.btn_login);
        btn_login.setOnClickListener(this);

        // tab perfil
        lbl_usr = (TextView)findViewById(R.id.lbl_usr);
        lbl_rol = (TextView)findViewById(R.id.lbl_rol);
        btn_logout = (Button)findViewById(R.id.btn_logout);
        btn_logout.setOnClickListener(this);

        // tab galletas
        btn_g1 = (ImageButton)findViewById(R.id.btnG1);
        btn_g2 = (ImageButton)findViewById(R.id.btnG2);
        btn_g3 = (ImageButton)findViewById(R.id.btnG3);
        btn_g1.setOnClickListener(this);
        btn_g2.setOnClickListener(this);
        btn_g3.setOnClickListener(this);

        // tab registro
        lv_registro = (ListView)findViewById(R.id.lista_registro);

        // tab estado_admin
        lv_cantidad = (ListView)findViewById(R.id.list_gaveta);
        lv_estado = (ListView)findViewById(R.id.list_producto);

        // tab estadistica_admin
        graph = (GraphView)findViewById(R.id.grafica);

        // tab ayuda

    }

    private void construyeTabs() {
        //tag 0
        spec = th.newTabSpec("TAG_login");
        spec.setContent(R.id.tab1);
        spec.setIndicator("",getResources().getDrawable(R.drawable.usr));
        th.addTab(spec);

        //tag 1
        spec = th.newTabSpec("TAG_perfil");
        spec.setContent(R.id.tab5);
        spec.setIndicator("",getResources().getDrawable(R.drawable.usr));
        th.addTab(spec);

        //tag 2
        spec = th.newTabSpec("TAG_galletas");
        spec.setContent(R.id.tab2);
        spec.setIndicator("",getResources().getDrawable(R.drawable.cookie));
        th.addTab(spec);

        //tag 3
        spec = th.newTabSpec("TAG_registro");
        spec.setContent(R.id.tab3);
        spec.setIndicator("",getResources().getDrawable(R.drawable.reg));
        th.addTab(spec);

        //tag 4
        spec = th.newTabSpec("TAG_status_ad");
        spec.setContent(R.id.tab6);
        spec.setIndicator("",getResources().getDrawable(R.drawable.reg));
        th.addTab(spec);

        //tag 5
        spec = th.newTabSpec("TAG_estadistica_ad");
        spec.setContent(R.id.tab7);
        spec.setIndicator("",getResources().getDrawable(R.drawable.stat));
        th.addTab(spec);

        //tag 6
        spec = th.newTabSpec("TAG_ayuda");
        spec.setContent(R.id.tab4);
        spec.setIndicator("",getResources().getDrawable(R.drawable.help));
        th.addTab(spec);
    }

    public void visibleLogin(){
        th.getTabWidget().getChildTabViewAt(0).setVisibility(View.VISIBLE);
        th.getTabWidget().getChildTabViewAt(1).setVisibility(View.GONE);
        th.getTabWidget().getChildTabViewAt(2).setVisibility(View.GONE);
        th.getTabWidget().getChildTabViewAt(3).setVisibility(View.GONE);
        th.getTabWidget().getChildTabViewAt(4).setVisibility(View.GONE);
        th.getTabWidget().getChildTabViewAt(5).setVisibility(View.GONE);
        th.getTabWidget().getChildTabViewAt(6).setVisibility(View.GONE);
        th.setCurrentTabByTag("TAG_login");
    }

    public void visiblePerfil(){
        th.getTabWidget().getChildTabViewAt(0).setVisibility(View.GONE);
        th.getTabWidget().getChildTabViewAt(1).setVisibility(View.VISIBLE);
        th.getTabWidget().getChildTabViewAt(2).setVisibility(View.VISIBLE);
        th.getTabWidget().getChildTabViewAt(3).setVisibility(View.VISIBLE);
        th.getTabWidget().getChildTabViewAt(4).setVisibility(View.GONE);
        th.getTabWidget().getChildTabViewAt(5).setVisibility(View.GONE);
        th.getTabWidget().getChildTabViewAt(6).setVisibility(View.VISIBLE);
        th.setCurrentTabByTag("TAG_galletas");
        llenarRegistro();
    }

    public void visibleAdmin(){
        th.getTabWidget().getChildTabViewAt(0).setVisibility(View.GONE);
        th.getTabWidget().getChildTabViewAt(1).setVisibility(View.VISIBLE);
        th.getTabWidget().getChildTabViewAt(2).setVisibility(View.GONE);
        th.getTabWidget().getChildTabViewAt(3).setVisibility(View.GONE);
        th.getTabWidget().getChildTabViewAt(4).setVisibility(View.VISIBLE);
        th.getTabWidget().getChildTabViewAt(5).setVisibility(View.VISIBLE);
        th.getTabWidget().getChildTabViewAt(6).setVisibility(View.VISIBLE);
        th.setCurrentTabByTag("TAG_status_ad");
        llenarEstado("Ok","Error","Ok");
        llenarEstadistica("10","20","15");
        graficarEstadisticas(20,50,60);
    }

    void llenarRegistro(){
        String[] values = new String[] { "Registro 1",
                "Registro 2",
                "Registro 3",
                "Registro 4",
                "Registro 5",
                "Registro 6",
                "Registro 7",
                "Registro 8"
        };
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, values);
        lv_registro.setAdapter(adapter);
    }

    void llenarEstadistica(String valg1, String valg2, String valg3){
        String[] values = new String[] {
                "Cantidad Disponible "+Galleta1+": "+valg1,
                "Cantidad Disponible "+Galleta2+": "+valg2,
                "Cantidad Disponible "+Galleta3+": "+valg3
        };
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, values);
        lv_estado.setAdapter(adapter);
    }

    void llenarEstado(String valg1, String valg2, String valg3){
        String[] values = new String[] {
                "Estado "+Galleta1+": "+valg1,
                "Estado "+Galleta2+": "+valg2,
                "Estado "+Galleta3+": "+valg3
        };
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, values);
        lv_cantidad.setAdapter(adapter);
    }

    void graficarEstadisticas(int g1, int g2, int g3){

        BarGraphSeries<DataPoint> series = new BarGraphSeries<>(new DataPoint[] {
                new DataPoint(0, 0),
                new DataPoint(1, g1),
                new DataPoint(2, g2),
                new DataPoint(3, g3),
                new DataPoint(4, 0)
        });
        graph.addSeries(series);

        series.setValueDependentColor(new ValueDependentColor<DataPoint>() {
            @Override
            public int get(DataPoint data) {
                return Color.rgb((int) data.getX()*255/4, (int) Math.abs(data.getY()*255/6), 100);
            }
        });

        series.setSpacing(5);

        StaticLabelsFormatter staticLabelsFormatter = new StaticLabelsFormatter(graph);
        staticLabelsFormatter.setHorizontalLabels(new String[] {"",Galleta1, Galleta2, Galleta3,""});
        graph.getGridLabelRenderer().setLabelFormatter(staticLabelsFormatter);
    }

    public void mostrarToast(String mensaje){
        Toast toast = Toast.makeText(this, mensaje, Toast.LENGTH_SHORT);
        toast.show();
    }

    public void alertaGalleta(String tipoGaleta) {
        new AlertDialog.Builder(MainActivity.this)
                .setTitle("Galleta Solicitada")
                .setMessage("Se ha solicitado la galleta"+tipoGaleta)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                }).show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_login:{
                user = txt_usr.getText().toString();
                pass = txt_pass.getText().toString();
                //new Login().execute();
                //visiblePerfil();
                //visibleAdmin();
                break;
            }
            case R.id.btn_logout:{
                visibleLogin();
                user = "";
                pass = "";
                break;
            }
            case R.id.btnG1:{
                galleta = 1;
                alertaGalleta(Galleta1);
                //new PeticionGalleta().execute();
                break;
            }
            case R.id.btnG2:{
                galleta = 2;
                alertaGalleta(Galleta2);
                //new PeticionGalleta().execute();
                break;
            }
            case R.id.btnG3:{
                galleta = 3;
                alertaGalleta(Galleta3);
                //new PeticionGalleta().execute();
                break;
            }

        }
    }

    /*------------------------------------------------AsyncTasks para WS---------------------------------------------*/

    private class Login extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {

            request = new SoapObject(NAMESPACE, METHOD_NAME_LOGIN);

            request.addProperty("nombre", user);
            request.addProperty("correo", pass);

            envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.setOutputSoapObject(request);

            HttpTransportSE transporte = new HttpTransportSE(URL);
            try {
                transporte.call(SOAP_ACTION_LOGIN, envelope);
                resultRequestSoap = (SoapPrimitive) envelope.getResponse();

            } catch (IOException e) {
                mostrarToast(e.getMessage());
                e.printStackTrace();
            } catch (XmlPullParserException e) {
                mostrarToast(e.getMessage());
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result){
            String resultado=resultRequestSoap.toString();
            if(resultado.equals("Usuario Valido")){
                lbl_usr.setText(user);
                lbl_rol.setText("Cliente");
                visiblePerfil();
                txt_usr.setText("");
                txt_pass.setText("");
            }else if(resultado.equals("Usuario Valido Admin")){    ////////////// verificar
                lbl_usr.setText(user);
                lbl_rol.setText("Admin");
                visibleAdmin();
                txt_usr.setText("");
                txt_pass.setText("");
            }else{
                mostrarToast("Datos incorrectos");
            }
            super.onPostExecute(result);
        }
    }


    private class PeticionGalleta extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {

            // en galleta esta el valor XD

            request = new SoapObject(NAMESPACE, METHOD_NAME_REQ);

           // request.addProperty("nombre", user);
           // request.addProperty("correo", pass);

            envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.setOutputSoapObject(request);

            HttpTransportSE transporte = new HttpTransportSE(URL);
            try {
                transporte.call(SOAP_ACTION_REQ, envelope);
                resultRequestSoap = (SoapPrimitive) envelope.getResponse();

            } catch (IOException e) {
                mostrarToast(e.getMessage());
                e.printStackTrace();
            } catch (XmlPullParserException e) {
                mostrarToast(e.getMessage());
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result){
            galleta = 0;
            String resultado=resultRequestSoap.toString();
            int res = Integer.parseInt(resultado);

            super.onPostExecute(result);
        }
    }

}

