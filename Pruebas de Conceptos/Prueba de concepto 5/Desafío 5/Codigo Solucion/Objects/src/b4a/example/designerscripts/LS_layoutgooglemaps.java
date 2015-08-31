package b4a.example.designerscripts;
import anywheresoftware.b4a.objects.TextViewWrapper;
import anywheresoftware.b4a.objects.ImageViewWrapper;
import anywheresoftware.b4a.BA;


public class LS_layoutgooglemaps{

public static void LS_general(java.util.LinkedHashMap<String, anywheresoftware.b4a.keywords.LayoutBuilder.ViewWrapperAndAnchor> views, int width, int height, float scale) {
anywheresoftware.b4a.keywords.LayoutBuilder.setScaleRate(0.3);
anywheresoftware.b4a.keywords.LayoutBuilder.scaleAll(views);
views.get("spinnerreviewg").vw.setTop((int)(0d));
views.get("spinnerreviewg").vw.setHeight((int)((10d / 100 * height) - (0d)));
views.get("webviewgoogle").vw.setTop((int)((10d / 100 * height)));
views.get("webviewgoogle").vw.setHeight((int)((100d / 100 * height) - ((10d / 100 * height))));

}
}