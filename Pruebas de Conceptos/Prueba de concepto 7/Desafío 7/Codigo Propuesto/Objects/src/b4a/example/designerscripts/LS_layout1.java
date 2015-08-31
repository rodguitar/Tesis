package b4a.example.designerscripts;
import anywheresoftware.b4a.objects.TextViewWrapper;
import anywheresoftware.b4a.objects.ImageViewWrapper;
import anywheresoftware.b4a.BA;


public class LS_layout1{

public static void LS_general(java.util.LinkedHashMap<String, anywheresoftware.b4a.keywords.LayoutBuilder.ViewWrapperAndAnchor> views, int width, int height, float scale) {
anywheresoftware.b4a.keywords.LayoutBuilder.setScaleRate(0.3);
//BA.debugLineNum = 2;BA.debugLine="AutoScaleAll"[layout1/General script]
anywheresoftware.b4a.keywords.LayoutBuilder.scaleAll(views);
//BA.debugLineNum = 3;BA.debugLine="Label1.SetTopAndBottom(10%y,20%y)"[layout1/General script]
views.get("label1").vw.setTop((int)((10d / 100 * height)));
views.get("label1").vw.setHeight((int)((20d / 100 * height) - ((10d / 100 * height))));
//BA.debugLineNum = 4;BA.debugLine="LabelCuenta.SetTopAndBottom(30%y,40%y)"[layout1/General script]
views.get("labelcuenta").vw.setTop((int)((30d / 100 * height)));
views.get("labelcuenta").vw.setHeight((int)((40d / 100 * height) - ((30d / 100 * height))));
//BA.debugLineNum = 5;BA.debugLine="ButtonStart.SetTopAndBottom(50%y,70%y)"[layout1/General script]
views.get("buttonstart").vw.setTop((int)((50d / 100 * height)));
views.get("buttonstart").vw.setHeight((int)((70d / 100 * height) - ((50d / 100 * height))));
//BA.debugLineNum = 6;BA.debugLine="ButtonStart.SetLeftAndRight(15%x,45%x)"[layout1/General script]
views.get("buttonstart").vw.setLeft((int)((15d / 100 * width)));
views.get("buttonstart").vw.setWidth((int)((45d / 100 * width) - ((15d / 100 * width))));
//BA.debugLineNum = 7;BA.debugLine="ButtonStop.SetTopAndBottom(50%y,70%y)"[layout1/General script]
views.get("buttonstop").vw.setTop((int)((50d / 100 * height)));
views.get("buttonstop").vw.setHeight((int)((70d / 100 * height) - ((50d / 100 * height))));
//BA.debugLineNum = 8;BA.debugLine="ButtonStop.SetLeftAndRight(55%x,85%x)"[layout1/General script]
views.get("buttonstop").vw.setLeft((int)((55d / 100 * width)));
views.get("buttonstop").vw.setWidth((int)((85d / 100 * width) - ((55d / 100 * width))));

}
}