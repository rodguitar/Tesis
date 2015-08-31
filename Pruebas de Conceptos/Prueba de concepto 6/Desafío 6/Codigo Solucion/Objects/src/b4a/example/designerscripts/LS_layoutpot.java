package b4a.example.designerscripts;
import anywheresoftware.b4a.objects.TextViewWrapper;
import anywheresoftware.b4a.objects.ImageViewWrapper;
import anywheresoftware.b4a.BA;


public class LS_layoutpot{

public static void LS_general(java.util.LinkedHashMap<String, anywheresoftware.b4a.keywords.LayoutBuilder.ViewWrapperAndAnchor> views, int width, int height, float scale) {
anywheresoftware.b4a.keywords.LayoutBuilder.setScaleRate(0.3);
//BA.debugLineNum = 2;BA.debugLine="AutoScaleAll"[layoutpot/General script]
anywheresoftware.b4a.keywords.LayoutBuilder.scaleAll(views);
//BA.debugLineNum = 3;BA.debugLine="LabelTitulo.SetTopAndBottom(8%y,18%y)"[layoutpot/General script]
views.get("labeltitulo").vw.setTop((int)((8d / 100 * height)));
views.get("labeltitulo").vw.setHeight((int)((18d / 100 * height) - ((8d / 100 * height))));
//BA.debugLineNum = 4;BA.debugLine="LabelValorEscritura.SetTopAndBottom(35%y,45%Y)"[layoutpot/General script]
views.get("labelvalorescritura").vw.setTop((int)((35d / 100 * height)));
views.get("labelvalorescritura").vw.setHeight((int)((45d / 100 * height) - ((35d / 100 * height))));
//BA.debugLineNum = 5;BA.debugLine="SeekBar1.Top=45%y"[layoutpot/General script]
views.get("seekbar1").vw.setTop((int)((45d / 100 * height)));
//BA.debugLineNum = 6;BA.debugLine="SeekBar1.SetLeftAndRight(5%x,95%x)"[layoutpot/General script]
views.get("seekbar1").vw.setLeft((int)((5d / 100 * width)));
views.get("seekbar1").vw.setWidth((int)((95d / 100 * width) - ((5d / 100 * width))));

}
}