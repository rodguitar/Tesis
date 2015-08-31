package b4a.example.designerscripts;
import anywheresoftware.b4a.objects.TextViewWrapper;
import anywheresoftware.b4a.objects.ImageViewWrapper;
import anywheresoftware.b4a.BA;


public class LS_layoutbusca{

public static void LS_general(java.util.LinkedHashMap<String, anywheresoftware.b4a.keywords.LayoutBuilder.ViewWrapperAndAnchor> views, int width, int height, float scale) {
anywheresoftware.b4a.keywords.LayoutBuilder.setScaleRate(0.3);
anywheresoftware.b4a.keywords.LayoutBuilder.scaleAll(views);
views.get("panelbusca").vw.setHeight((int)((23d / 100 * height)));
views.get("labelbusca").vw.setTop((int)((views.get("panelbusca").vw.getHeight())/100d*7d));
views.get("labelbusca").vw.setHeight((int)((views.get("panelbusca").vw.getHeight())/100d*40d));
views.get("edittextbusca").vw.setTop((int)((views.get("labelbusca").vw.getTop() + views.get("labelbusca").vw.getHeight())+(views.get("panelbusca").vw.getHeight())/100d*6d));
views.get("edittextbusca").vw.setHeight((int)((views.get("panelbusca").vw.getHeight())/100d*40d));
views.get("listviewresultados").vw.setTop((int)((views.get("panelbusca").vw.getTop() + views.get("panelbusca").vw.getHeight())+(views.get("panelbusca").vw.getTop())));
views.get("listviewresultados").vw.setHeight((int)((100d / 100 * height)-(views.get("panelbusca").vw.getTop()) - ((views.get("panelbusca").vw.getTop() + views.get("panelbusca").vw.getHeight())+(views.get("panelbusca").vw.getTop()))));

}
}