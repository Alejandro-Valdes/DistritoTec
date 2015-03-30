package itesm.mx.androides_proyecto_distritotec.SideBar;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import itesm.mx.androides_proyecto_distritotec.R;

public class NotificacionesFragment extends Fragment {

    public NotificacionesFragment(){}
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.notificaciones_fragment, container, false);
        return rootView;
    }
}