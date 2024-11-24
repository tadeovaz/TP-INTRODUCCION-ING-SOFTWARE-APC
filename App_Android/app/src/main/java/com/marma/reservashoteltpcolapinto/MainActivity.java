package com.marma.reservashoteltpcolapinto;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.marma.reservashoteltpcolapinto.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private static final String SERVICIOS = "Servicios";
    private static final String CONTACTO = "Contacto";
    private ActivityMainBinding binding;
    private String currentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setFragments();
        setListener();
    }

    private void setFragments() {
        Fragment servicios = new ServiciosFragment();
        Fragment contacto = new ReservasFragment();

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        transaction.add(R.id.fragment_container, servicios, SERVICIOS);
        transaction.add(R.id.fragment_container, contacto, CONTACTO);
        transaction.hide(contacto);
        this.currentFragment = SERVICIOS;
        transaction.commit();
    }

    private void setListener(){
        binding.contacto.setOnClickListener(v -> {
            cambiarColorDeTexto(binding.contacto, binding.viewContacto);
            showFragment(CONTACTO);
        });

        binding.servicios.setOnClickListener(v -> {
            cambiarColorDeTexto(binding.servicios, binding.viewServicios);
            showFragment(SERVICIOS);
        });
    }

    private void showFragment(String tag) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();

        if(isGoingRight(tag)){
            transaction.setCustomAnimations(
                    R.anim.slide_in_right,
                    R.anim.slide_out_right
            );
        }
        else{
            transaction.setCustomAnimations(
                    R.anim.slide_in_left,
                    R.anim.slide_out_left
            );
        }

        this.currentFragment = tag;
        for (Fragment fragment : manager.getFragments()) {
            if(fragment.getTag() != null && fragment.getTag().equals(tag)) {
                transaction.show(fragment);
            } else {
                transaction.hide(fragment);
            }
        }
        transaction.commit();
    }

    private boolean isGoingRight(String tag) {
      return tag.equals(CONTACTO);
    }

    private void cambiarColorDeTexto(TextView tv, View view) {
        binding.servicios.setTextColor(getResources().getColor(R.color.primary));
        binding.contacto.setTextColor(getResources().getColor(R.color.primary));

        binding.viewContacto.setVisibility(View.INVISIBLE);
        binding.viewServicios.setVisibility(View.INVISIBLE);

        tv.setTextColor(getResources().getColor(R.color.secondary));
        view.setVisibility(View.VISIBLE);
    }
}