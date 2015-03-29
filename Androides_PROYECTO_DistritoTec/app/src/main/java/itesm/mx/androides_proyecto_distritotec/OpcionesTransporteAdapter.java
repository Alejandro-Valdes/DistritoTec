package itesm.mx.androides_proyecto_distritotec;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Alejandro Valdes on 28-Mar-15.
 */
public class OpcionesTransporteAdapter extends BaseExpandableListAdapter{

    private Context context;
    private HashMap<String, List<String>> hmOpciones;
    private List<String> liOpciones;

    public OpcionesTransporteAdapter(Context context, HashMap<String, List<String>> hmOpciones,
                                     List<String> liOpciones){
        this.context = context;
        this.hmOpciones = hmOpciones;
        this.liOpciones = liOpciones;
    }

    @Override
    public int getGroupCount() {
        return liOpciones.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return hmOpciones.get(liOpciones.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return liOpciones.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return hmOpciones.get(liOpciones.get(groupPosition)).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

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

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
}
