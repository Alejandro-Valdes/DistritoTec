package itesm.mx.androides_proyecto_distritotec.SideBar;

/**
 * NavDrawerItem
 *
 * Clase que le pone iconos y canitdades a los items del side bar
 *
 * @author Jose Eduardo Elizondo Lozano A01089591
 * @author Oliver Alejandro Martínez Quiroz A01280416
 * @author Jesús Alejandro Valdés Valdés A0099044
 *
 * Version 1.0
 *
 */

public class NavDrawerItem {

    private String title;
    private int icon;
    private String count = "0";
    // boolean to set visiblity of the counter
    private boolean isCounterVisible = false;

    public NavDrawerItem(){}

    public NavDrawerItem(String title, int icon){
        this.title = title;
        this.icon = icon;
    }

    public NavDrawerItem(String title, int icon, boolean isCounterVisible, String count){
        this.title = title;
        this.icon = icon;
        this.isCounterVisible = isCounterVisible;
        this.count = count;
    }

    /**
     * getTitle
     *
     * @return
     */
    public String getTitle(){
        return this.title;
    }

    /**
     * getIcon
     *
     * @return
     */
    public int getIcon(){
        return this.icon;
    }

    /**
     * getCount
     *
     * @return
     */
    public String getCount(){
        return this.count;
    }

    /**
     * getCounterVisibility
     *
     * @return
     */
    public boolean getCounterVisibility(){
        return this.isCounterVisible;
    }

    /**
     * setTitle
     *
     * @param title
     */
    public void setTitle(String title){
        this.title = title;
    }

    /**
     * setIcon
     *
     * @param icon
     */
    public void setIcon(int icon){
        this.icon = icon;
    }

    /**
     * setCount
     *
     * @param count
     */
    public void setCount(String count){
        this.count = count;
    }

    /**
     * setCounterVisibility
     *
     * @param isCounterVisible
     */
    public void setCounterVisibility(boolean isCounterVisible){
        this.isCounterVisible = isCounterVisible;
    }
}