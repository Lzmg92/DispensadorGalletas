package com.example.leslie.dispensadorgalletas10;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TabHost;

public class MainActivity extends AppCompatActivity {

    private TabHost th;
    private TabHost.TabSpec spec;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        iniciaVars();
        construyeTabs();
    }

    private void construyeTabs() {
        spec = th.newTabSpec("TAG1");
        spec.setContent(R.id.tab1);
        spec.setIndicator("Log");
        th.addTab(spec);

        spec = th.newTabSpec("TAG2");
        spec.setContent(R.id.tab2);
        spec.setIndicator("Solicitud");
        th.addTab(spec);

        spec = th.newTabSpec("TAG3");
        spec.setContent(R.id.tab3);
        spec.setIndicator("Registro");
        th.addTab(spec);

        spec = th.newTabSpec("TAG4");
        spec.setContent(R.id.tab4);
        spec.setIndicator("?");
        th.addTab(spec);
    }

    private void iniciaVars() {
        th = (TabHost) findViewById(R.id.tabs);
        th.setup();
    }
}