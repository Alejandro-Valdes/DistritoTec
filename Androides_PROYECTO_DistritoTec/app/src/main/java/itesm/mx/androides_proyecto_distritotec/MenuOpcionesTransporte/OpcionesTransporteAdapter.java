package itesm.mx.androides_proyecto_distritotec.MenuOpcionesTransporte;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

import itesm.mx.androides_proyecto_distritotec.R;

/**
 * OpcionTransporteAdapter
 *
 * Clase adaptador de las opciones de transporte, obtiene los elementos de la ListView
 *
 * @author Jose Eduardo Elizondo Lozano A01089591
 * @author Oliver Alejandro Martínez Quiroz A01280416
 * @author Jesús Alejandro Valdés Valdés A0099044
 *
 * Version 1.0
 *
 */
public class OpcionesTransporteAdapter extends BaseExpandableListAdapter{

    private Context context; // Contexto de la aplicacion
    private HashMap<String, List<String>> hmOpciones; // Mapeo de la lista de rutas
    private List<String> liOpciones; // Lista de rutas

    /**
     * OpcionesTransporteAdapter
     *
     * Metodo constructor de la clase OpcionesTransporteAdapter
     *
     * @param context
     * @param hmOpciones
     * @param liOpciones
     */
    public OpcionesTransporteAdapter(Context context, HashMap<String, List<String>> hmOpciones,
                                     List<String> liOpciones){
        this.context = context;
        this.hmOpciones = hmOpciones;
        this.liOpciones = liOpciones;
    }

    /**
     * getGroupCount
     *
     * @return un valor <code>int</code> regresa el tamaño de la lista
     */
    @Override
    public int getGroupCount() {
        return liOpciones.size();
    }

    /**
     * getChildenCount
     *
     * @param groupPosition
     * @return un valor <code>int</code> regresa la cantidad de hijos de la list view padre
     */
    @Override
    public int getChildrenCount(int groupPosition) {
        return hmOpciones.get(liOpciones.get(groupPosition)).size();
    }

    /**
     * getGroup
     *
     * @param groupPosition
     * @return un valor <code>Object</code> regresa la ListView padre
     */
    @Override
    public Object getGroup(int groupPosition) {
        return liOpciones.get(groupPosition);
    }

    /**
     * getChild
     *
     * @param groupPosition
     * @param childPosition
     * @return un valor <code>Object</code> regresa el hijo selecionado
     */
    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return hmOpciones.get(liOpciones.get(groupPosition)).get(childPosition);
    }

    /**
     * getGroupId
     *
     * @param groupPosition
     * @return un valor <code>long</code> regresa la posicion del padre
     */
    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    /**
     *
     * @param groupPosition
     * @param childPosition
     * @return un valor <code>long</code> regresa la posicion del hijo
     */
    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    /**
     * hasStableIds
     *
     * @return un valor <code>boolean</code> falso
     */
    @Override
    public boolean hasStableIds() {
        return false;
    }

    /**
     * getGroupView
     *
     * @param groupPosition
     * @param isExpanded
     * @param convertView
     * @param parent
     * @return un valor <code>View</code> regresa la vista del padre
     */
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

        String strGroupName = (String) getGroup(groupPosition);
        if(convertView==null){
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.parentlayout, parent, false);
        }

        TextView tvParent = (TextView) convertView.findViewById(R.id.tvParent);
        tvParent.setText(strGroupName);

        return convertView;
    }

    /**
     * getChildView
     *
     * @param groupPosition
     * @param childPosition
     * @param isLastChild
     * @param convertView
     * @param parent
     * @return un valor <code>View</code> regresa la vista del hijo
     */
    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        String strChildName = (String) getChild(groupPosition,childPosition);
        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.childlayout, parent, false);
        }

        TextView tvChild = (TextView) convertView.findViewById(R.id.tvChild);
        tvChild.setText(strChildName);
        return convertView;
    }

    /**
     * isChildDelectable
     *
     * @param groupPosition
     * @param childPosition
     * @return un valor <code>boolean</code> true
     */
    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
