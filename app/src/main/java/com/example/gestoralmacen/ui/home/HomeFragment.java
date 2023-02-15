package com.example.gestoralmacen.ui.home;

import static android.content.Context.MODE_PRIVATE;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.example.gestoralmacen.R;

public class HomeFragment extends Fragment {

    private TextView tnombre;
    public static final String MY_PREFS_NAME = "MySession";
    public static final String id = "idKey";
    public static final String nombre = "nombreKey";
    public static final String apellido = "apellidoKey";

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

           View root =  inflater.inflate(R.layout.fragment_home, container, false);
           setHasOptionsMenu(true);

           SharedPreferences shared = getActivity().getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
           String cnombre = (shared.getString(nombre, ""));
           String capellido = (shared.getString(apellido, ""));

           tnombre = root.findViewById(R.id.user_name);
           tnombre.setText(cnombre+" "+capellido);

           return root;
    }
}
