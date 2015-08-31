package b4a.example.graficos;


import anywheresoftware.b4a.B4AMenuItem;
import android.app.Activity;
import android.os.Bundle;
import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.BALayout;
import anywheresoftware.b4a.B4AActivity;
import anywheresoftware.b4a.ObjectWrapper;
import anywheresoftware.b4a.objects.ActivityWrapper;
import java.lang.reflect.InvocationTargetException;
import anywheresoftware.b4a.B4AUncaughtException;
import anywheresoftware.b4a.debug.*;
import java.lang.ref.WeakReference;

public class main extends Activity implements B4AActivity{
	public static main mostCurrent;
	static boolean afterFirstLayout;
	static boolean isFirst = true;
    private static boolean processGlobalsRun = false;
	BALayout layout;
	public static BA processBA;
	BA activityBA;
    ActivityWrapper _activity;
    java.util.ArrayList<B4AMenuItem> menuItems;
	public static final boolean fullScreen = true;
	public static final boolean includeTitle = true;
    public static WeakReference<Activity> previousOne;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (isFirst) {
			processBA = new BA(this.getApplicationContext(), null, null, "b4a.example.graficos", "b4a.example.graficos.main");
			processBA.loadHtSubs(this.getClass());
	        float deviceScale = getApplicationContext().getResources().getDisplayMetrics().density;
	        BALayout.setDeviceScale(deviceScale);
            
		}
		else if (previousOne != null) {
			Activity p = previousOne.get();
			if (p != null && p != this) {
                BA.LogInfo("Killing previous instance (main).");
				p.finish();
			}
		}
        processBA.runHook("oncreate", this, null);
		if (!includeTitle) {
        	this.getWindow().requestFeature(android.view.Window.FEATURE_NO_TITLE);
        }
        if (fullScreen) {
        	getWindow().setFlags(android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN,   
        			android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
		mostCurrent = this;
        processBA.sharedProcessBA.activityBA = null;
		layout = new BALayout(this);
		setContentView(layout);
		afterFirstLayout = false;
		BA.handler.postDelayed(new WaitForLayout(), 5);

	}
	private static class WaitForLayout implements Runnable {
		public void run() {
			if (afterFirstLayout)
				return;
			if (mostCurrent == null)
				return;
            
			if (mostCurrent.layout.getWidth() == 0) {
				BA.handler.postDelayed(this, 5);
				return;
			}
			mostCurrent.layout.getLayoutParams().height = mostCurrent.layout.getHeight();
			mostCurrent.layout.getLayoutParams().width = mostCurrent.layout.getWidth();
			afterFirstLayout = true;
			mostCurrent.afterFirstLayout();
		}
	}
	private void afterFirstLayout() {
        if (this != mostCurrent)
			return;
		activityBA = new BA(this, layout, processBA, "b4a.example.graficos", "b4a.example.graficos.main");
        
        processBA.sharedProcessBA.activityBA = new java.lang.ref.WeakReference<BA>(activityBA);
        anywheresoftware.b4a.objects.ViewWrapper.lastId = 0;
        _activity = new ActivityWrapper(activityBA, "activity");
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        if (BA.isShellModeRuntimeCheck(processBA)) {
			if (isFirst)
				processBA.raiseEvent2(null, true, "SHELL", false);
			processBA.raiseEvent2(null, true, "CREATE", true, "b4a.example.graficos.main", processBA, activityBA, _activity, anywheresoftware.b4a.keywords.Common.Density, mostCurrent);
			_activity.reinitializeForShell(activityBA, "activity");
		}
        initializeProcessGlobals();		
        initializeGlobals();
        
        BA.LogInfo("** Activity (main) Create, isFirst = " + isFirst + " **");
        processBA.raiseEvent2(null, true, "activity_create", false, isFirst);
		isFirst = false;
		if (this != mostCurrent)
			return;
        processBA.setActivityPaused(false);
        BA.LogInfo("** Activity (main) Resume **");
        processBA.raiseEvent(null, "activity_resume");
        if (android.os.Build.VERSION.SDK_INT >= 11) {
			try {
				android.app.Activity.class.getMethod("invalidateOptionsMenu").invoke(this,(Object[]) null);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}
	public void addMenuItem(B4AMenuItem item) {
		if (menuItems == null)
			menuItems = new java.util.ArrayList<B4AMenuItem>();
		menuItems.add(item);
	}
	@Override
	public boolean onCreateOptionsMenu(android.view.Menu menu) {
		super.onCreateOptionsMenu(menu);
        try {
            if (processBA.subExists("activity_actionbarhomeclick")) {
                Class.forName("android.app.ActionBar").getMethod("setHomeButtonEnabled", boolean.class).invoke(
                    getClass().getMethod("getActionBar").invoke(this), true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (processBA.runHook("oncreateoptionsmenu", this, new Object[] {menu}))
            return true;
		if (menuItems == null)
			return false;
		for (B4AMenuItem bmi : menuItems) {
			android.view.MenuItem mi = menu.add(bmi.title);
			if (bmi.drawable != null)
				mi.setIcon(bmi.drawable);
            if (android.os.Build.VERSION.SDK_INT >= 11) {
				try {
                    if (bmi.addToBar) {
				        android.view.MenuItem.class.getMethod("setShowAsAction", int.class).invoke(mi, 1);
                    }
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			mi.setOnMenuItemClickListener(new B4AMenuItemsClickListener(bmi.eventName.toLowerCase(BA.cul)));
		}
        
		return true;
	}   
 @Override
 public boolean onOptionsItemSelected(android.view.MenuItem item) {
    if (item.getItemId() == 16908332) {
        processBA.raiseEvent(null, "activity_actionbarhomeclick");
        return true;
    }
    else
        return super.onOptionsItemSelected(item); 
}
@Override
 public boolean onPrepareOptionsMenu(android.view.Menu menu) {
    super.onPrepareOptionsMenu(menu);
    processBA.runHook("onprepareoptionsmenu", this, new Object[] {menu});
    return true;
    
 }
 protected void onStart() {
    super.onStart();
    processBA.runHook("onstart", this, null);
}
 protected void onStop() {
    super.onStop();
    processBA.runHook("onstop", this, null);
}
    public void onWindowFocusChanged(boolean hasFocus) {
       super.onWindowFocusChanged(hasFocus);
       if (processBA.subExists("activity_windowfocuschanged"))
           processBA.raiseEvent2(null, true, "activity_windowfocuschanged", false, hasFocus);
    }
	private class B4AMenuItemsClickListener implements android.view.MenuItem.OnMenuItemClickListener {
		private final String eventName;
		public B4AMenuItemsClickListener(String eventName) {
			this.eventName = eventName;
		}
		public boolean onMenuItemClick(android.view.MenuItem item) {
			processBA.raiseEvent(item.getTitle(), eventName + "_click");
			return true;
		}
	}
    public static Class<?> getObject() {
		return main.class;
	}
    private Boolean onKeySubExist = null;
    private Boolean onKeyUpSubExist = null;
	@Override
	public boolean onKeyDown(int keyCode, android.view.KeyEvent event) {
		if (onKeySubExist == null)
			onKeySubExist = processBA.subExists("activity_keypress");
		if (onKeySubExist) {
			if (keyCode == anywheresoftware.b4a.keywords.constants.KeyCodes.KEYCODE_BACK &&
					android.os.Build.VERSION.SDK_INT >= 18) {
				HandleKeyDelayed hk = new HandleKeyDelayed();
				hk.kc = keyCode;
				BA.handler.post(hk);
				return true;
			}
			else {
				boolean res = new HandleKeyDelayed().runDirectly(keyCode);
				if (res)
					return true;
			}
		}
		return super.onKeyDown(keyCode, event);
	}
	private class HandleKeyDelayed implements Runnable {
		int kc;
		public void run() {
			runDirectly(kc);
		}
		public boolean runDirectly(int keyCode) {
			Boolean res =  (Boolean)processBA.raiseEvent2(_activity, false, "activity_keypress", false, keyCode);
			if (res == null || res == true) {
                return true;
            }
            else if (keyCode == anywheresoftware.b4a.keywords.constants.KeyCodes.KEYCODE_BACK) {
				finish();
				return true;
			}
            return false;
		}
		
	}
    @Override
	public boolean onKeyUp(int keyCode, android.view.KeyEvent event) {
		if (onKeyUpSubExist == null)
			onKeyUpSubExist = processBA.subExists("activity_keyup");
		if (onKeyUpSubExist) {
			Boolean res =  (Boolean)processBA.raiseEvent2(_activity, false, "activity_keyup", false, keyCode);
			if (res == null || res == true)
				return true;
		}
		return super.onKeyUp(keyCode, event);
	}
	@Override
	public void onNewIntent(android.content.Intent intent) {
        super.onNewIntent(intent);
		this.setIntent(intent);
        processBA.runHook("onnewintent", this, new Object[] {intent});
	}
    @Override 
	public void onPause() {
		super.onPause();
        if (_activity == null) //workaround for emulator bug (Issue 2423)
            return;
		anywheresoftware.b4a.Msgbox.dismiss(true);
        BA.LogInfo("** Activity (main) Pause, UserClosed = " + activityBA.activity.isFinishing() + " **");
        processBA.raiseEvent2(_activity, true, "activity_pause", false, activityBA.activity.isFinishing());		
        processBA.setActivityPaused(true);
        mostCurrent = null;
        if (!activityBA.activity.isFinishing())
			previousOne = new WeakReference<Activity>(this);
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        processBA.runHook("onpause", this, null);
	}

	@Override
	public void onDestroy() {
        super.onDestroy();
		previousOne = null;
        processBA.runHook("ondestroy", this, null);
	}
    @Override 
	public void onResume() {
		super.onResume();
        mostCurrent = this;
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        if (activityBA != null) { //will be null during activity create (which waits for AfterLayout).
        	ResumeMessage rm = new ResumeMessage(mostCurrent);
        	BA.handler.post(rm);
        }
        processBA.runHook("onresume", this, null);
	}
    private static class ResumeMessage implements Runnable {
    	private final WeakReference<Activity> activity;
    	public ResumeMessage(Activity activity) {
    		this.activity = new WeakReference<Activity>(activity);
    	}
		public void run() {
			if (mostCurrent == null || mostCurrent != activity.get())
				return;
			processBA.setActivityPaused(false);
            BA.LogInfo("** Activity (main) Resume **");
		    processBA.raiseEvent(mostCurrent._activity, "activity_resume", (Object[])null);
		}
    }
	@Override
	protected void onActivityResult(int requestCode, int resultCode,
	      android.content.Intent data) {
		processBA.onActivityResult(requestCode, resultCode, data);
        processBA.runHook("onactivityresult", this, new Object[] {requestCode, resultCode});
	}
	private static void initializeGlobals() {
		processBA.raiseEvent2(null, true, "globals", false, (Object[])null);
	}

public anywheresoftware.b4a.keywords.Common __c = null;
public static float _fontsize = 0f;
public static double _maxval = 0;
public static double _minval = 0;
public static double _scalemin = 0;
public static double _scalemax = 0;
public static double _scaleinterval = 0;
public anywheresoftware.b4a.objects.PanelWrapper _pnlpie = null;
public anywheresoftware.b4a.objects.PanelWrapper _pnlline = null;
public anywheresoftware.b4a.objects.PanelWrapper _pnl2lines = null;
public anywheresoftware.b4a.objects.PanelWrapper _pnlbars = null;
public anywheresoftware.b4a.objects.PanelWrapper _pnlstackedbars = null;
public anywheresoftware.b4a.objects.TabHostWrapper _tabhost1 = null;
public b4a.example.graficos.charts _charts = null;

public static boolean isAnyActivityVisible() {
    boolean vis = false;
vis = vis | (main.mostCurrent != null);
return vis;}
public static String  _activity_create(boolean _firsttime) throws Exception{
 //BA.debugLineNum = 25;BA.debugLine="Sub Activity_Create(FirstTime As Boolean)";
 //BA.debugLineNum = 26;BA.debugLine="Activity.Title=\"Gráficos Chart\"";
mostCurrent._activity.setTitle((Object)("Gráficos Chart"));
 //BA.debugLineNum = 27;BA.debugLine="TabHost1.Initialize(\"TabHost1\")";
mostCurrent._tabhost1.Initialize(mostCurrent.activityBA,"TabHost1");
 //BA.debugLineNum = 28;BA.debugLine="Activity.AddView(TabHost1, 0, 0, 100%x, 100%y)";
mostCurrent._activity.AddView((android.view.View)(mostCurrent._tabhost1.getObject()),(int) (0),(int) (0),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA));
 //BA.debugLineNum = 31;BA.debugLine="CreatePieTab";
_createpietab();
 //BA.debugLineNum = 32;BA.debugLine="CreateLineTab";
_createlinetab();
 //BA.debugLineNum = 33;BA.debugLine="Create2LinesTab";
_create2linestab();
 //BA.debugLineNum = 34;BA.debugLine="CreateBarsTab";
_createbarstab();
 //BA.debugLineNum = 35;BA.debugLine="CreateStackedBarsTab";
_createstackedbarstab();
 //BA.debugLineNum = 36;BA.debugLine="End Sub";
return "";
}
public static String  _activity_pause(boolean _userclosed) throws Exception{
 //BA.debugLineNum = 189;BA.debugLine="Sub Activity_Pause (UserClosed As Boolean)";
 //BA.debugLineNum = 191;BA.debugLine="End Sub";
return "";
}
public static String  _activity_resume() throws Exception{
 //BA.debugLineNum = 185;BA.debugLine="Sub Activity_Resume";
 //BA.debugLineNum = 187;BA.debugLine="End Sub";
return "";
}
public static String  _create2linestab() throws Exception{
anywheresoftware.b4a.objects.PanelWrapper _p = null;
b4a.example.graficos.charts._linedata _ld = null;
int _i = 0;
b4a.example.graficos.charts._graph _g = null;
 //BA.debugLineNum = 101;BA.debugLine="Sub Create2LinesTab";
 //BA.debugLineNum = 102;BA.debugLine="Dim p As Panel";
_p = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 103;BA.debugLine="p.Initialize(\"\")";
_p.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 104;BA.debugLine="pnl2Lines.Initialize(\"pnl2Lines\")";
mostCurrent._pnl2lines.Initialize(mostCurrent.activityBA,"pnl2Lines");
 //BA.debugLineNum = 105;BA.debugLine="p.AddView(pnl2Lines, 0, 0, 95%x, 100%y - 100dip)";
_p.AddView((android.view.View)(mostCurrent._pnl2lines.getObject()),(int) (0),(int) (0),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (95),mostCurrent.activityBA),(int) (anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (100))));
 //BA.debugLineNum = 106;BA.debugLine="TabHost1.AddTab2(\"2 Lines Chart\", p)";
mostCurrent._tabhost1.AddTab2("2 Lines Chart",(android.view.View)(_p.getObject()));
 //BA.debugLineNum = 108;BA.debugLine="Dim LD As LineData";
_ld = new b4a.example.graficos.charts._linedata();
 //BA.debugLineNum = 109;BA.debugLine="LD.Initialize";
_ld.Initialize();
 //BA.debugLineNum = 110;BA.debugLine="LD.Target = pnl2Lines";
_ld.Target = mostCurrent._pnl2lines;
 //BA.debugLineNum = 111;BA.debugLine="Charts.AddLineColor(LD, Colors.Red) 'First line c";
mostCurrent._charts._addlinecolor(mostCurrent.activityBA,_ld,anywheresoftware.b4a.keywords.Common.Colors.Red);
 //BA.debugLineNum = 112;BA.debugLine="Charts.AddLineColor(LD, Colors.Blue) 'Second line";
mostCurrent._charts._addlinecolor(mostCurrent.activityBA,_ld,anywheresoftware.b4a.keywords.Common.Colors.Blue);
 //BA.debugLineNum = 113;BA.debugLine="For i = 0 To 360 Step 10";
{
final int step88 = (int) (10);
final int limit88 = (int) (360);
for (_i = (int) (0); (step88 > 0 && _i <= limit88) || (step88 < 0 && _i >= limit88); _i = ((int)(0 + _i + step88))) {
 //BA.debugLineNum = 116;BA.debugLine="Charts.AddLineMultiplePoints(LD, i, Array As Flo";
mostCurrent._charts._addlinemultiplepoints(mostCurrent.activityBA,_ld,BA.NumberToString(_i),new float[]{(float) (anywheresoftware.b4a.keywords.Common.SinD(_i)),(float) (anywheresoftware.b4a.keywords.Common.CosD(_i))},_i%90==0);
 }
};
 //BA.debugLineNum = 118;BA.debugLine="Dim G As Graph";
_g = new b4a.example.graficos.charts._graph();
 //BA.debugLineNum = 119;BA.debugLine="G.Initialize";
_g.Initialize();
 //BA.debugLineNum = 120;BA.debugLine="G.Title = \"2 Lines Chart (Sine & Cosine)\"";
_g.Title = "2 Lines Chart (Sine & Cosine)";
 //BA.debugLineNum = 121;BA.debugLine="G.XAxis = \"Degrees\"";
_g.XAxis = "Degrees";
 //BA.debugLineNum = 122;BA.debugLine="G.YAxis = \"Values\"";
_g.YAxis = "Values";
 //BA.debugLineNum = 123;BA.debugLine="G.YStart = -1";
_g.YStart = (float) (-1);
 //BA.debugLineNum = 124;BA.debugLine="G.YEnd = 1";
_g.YEnd = (float) (1);
 //BA.debugLineNum = 125;BA.debugLine="G.YInterval = 0.2";
_g.YInterval = (float) (0.2);
 //BA.debugLineNum = 126;BA.debugLine="G.AxisColor = Colors.Black";
_g.AxisColor = anywheresoftware.b4a.keywords.Common.Colors.Black;
 //BA.debugLineNum = 127;BA.debugLine="Charts.DrawLineChart(G, LD, Colors.White)";
mostCurrent._charts._drawlinechart(mostCurrent.activityBA,_g,_ld,anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 128;BA.debugLine="End Sub";
return "";
}
public static String  _createbarstab() throws Exception{
anywheresoftware.b4a.objects.PanelWrapper _p = null;
b4a.example.graficos.charts._bardata _bd = null;
int _i = 0;
b4a.example.graficos.charts._graph _g = null;
 //BA.debugLineNum = 37;BA.debugLine="Sub CreateBarsTab";
 //BA.debugLineNum = 41;BA.debugLine="Dim p As Panel";
_p = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 42;BA.debugLine="p.Initialize(\"\")";
_p.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 43;BA.debugLine="pnlBars.Initialize(\"pnlBars\")";
mostCurrent._pnlbars.Initialize(mostCurrent.activityBA,"pnlBars");
 //BA.debugLineNum = 44;BA.debugLine="p.AddView(pnlBars, 0, 0, 95%x, 100%y - 100dip)";
_p.AddView((android.view.View)(mostCurrent._pnlbars.getObject()),(int) (0),(int) (0),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (95),mostCurrent.activityBA),(int) (anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (100))));
 //BA.debugLineNum = 45;BA.debugLine="TabHost1.AddTab2(\"Bars Chart\", p)";
mostCurrent._tabhost1.AddTab2("Bars Chart",(android.view.View)(_p.getObject()));
 //BA.debugLineNum = 46;BA.debugLine="Dim BD As BarData";
_bd = new b4a.example.graficos.charts._bardata();
 //BA.debugLineNum = 47;BA.debugLine="BD.Initialize";
_bd.Initialize();
 //BA.debugLineNum = 48;BA.debugLine="BD.Target = pnlBars";
_bd.Target = mostCurrent._pnlbars;
 //BA.debugLineNum = 49;BA.debugLine="BD.BarsWidth = 15dip";
_bd.BarsWidth = anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (15));
 //BA.debugLineNum = 50;BA.debugLine="BD.Stacked = False";
_bd.Stacked = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 51;BA.debugLine="Charts.AddBarColor(BD, MakeTransparent(Colors.Dar";
mostCurrent._charts._addbarcolor(mostCurrent.activityBA,_bd,_maketransparent(anywheresoftware.b4a.keywords.Common.Colors.DarkGray,(int) (230)));
 //BA.debugLineNum = 52;BA.debugLine="Charts.AddBarColor(BD, MakeTransparent(Colors.Gra";
mostCurrent._charts._addbarcolor(mostCurrent.activityBA,_bd,_maketransparent(anywheresoftware.b4a.keywords.Common.Colors.Gray,(int) (230)));
 //BA.debugLineNum = 53;BA.debugLine="Charts.AddBarColor(BD, MakeTransparent(Colors.Lig";
mostCurrent._charts._addbarcolor(mostCurrent.activityBA,_bd,_maketransparent(anywheresoftware.b4a.keywords.Common.Colors.LightGray,(int) (230)));
 //BA.debugLineNum = 54;BA.debugLine="For i = 1 To 4";
{
final int step32 = 1;
final int limit32 = (int) (4);
for (_i = (int) (1); (step32 > 0 && _i <= limit32) || (step32 < 0 && _i >= limit32); _i = ((int)(0 + _i + step32))) {
 //BA.debugLineNum = 55;BA.debugLine="Charts.AddBarPoint(BD, 2005 + i, Array As Float(";
mostCurrent._charts._addbarpoint(mostCurrent.activityBA,_bd,BA.NumberToString(2005+_i),new float[]{(float) (anywheresoftware.b4a.keywords.Common.Rnd((int) (0),(int) (1000))),(float) (anywheresoftware.b4a.keywords.Common.Rnd((int) (0),(int) (1000))),(float) (anywheresoftware.b4a.keywords.Common.Rnd((int) (0),(int) (1000)))});
 }
};
 //BA.debugLineNum = 57;BA.debugLine="Dim G As Graph";
_g = new b4a.example.graficos.charts._graph();
 //BA.debugLineNum = 58;BA.debugLine="G.Initialize";
_g.Initialize();
 //BA.debugLineNum = 59;BA.debugLine="G.Title = \"Bars Chart\"";
_g.Title = "Bars Chart";
 //BA.debugLineNum = 60;BA.debugLine="G.XAxis = \"Year\"";
_g.XAxis = "Year";
 //BA.debugLineNum = 61;BA.debugLine="G.YAxis = \"Values\"";
_g.YAxis = "Values";
 //BA.debugLineNum = 62;BA.debugLine="G.YStart = 0";
_g.YStart = (float) (0);
 //BA.debugLineNum = 63;BA.debugLine="G.YEnd = 1000";
_g.YEnd = (float) (1000);
 //BA.debugLineNum = 64;BA.debugLine="G.YInterval = 100";
_g.YInterval = (float) (100);
 //BA.debugLineNum = 65;BA.debugLine="G.AxisColor = Colors.Black";
_g.AxisColor = anywheresoftware.b4a.keywords.Common.Colors.Black;
 //BA.debugLineNum = 66;BA.debugLine="Charts.DrawBarsChart(G, BD, Colors.White)";
mostCurrent._charts._drawbarschart(mostCurrent.activityBA,_g,_bd,anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 67;BA.debugLine="End Sub";
return "";
}
public static String  _createlinetab() throws Exception{
anywheresoftware.b4a.objects.PanelWrapper _p = null;
b4a.example.graficos.charts._linedata _ld = null;
int _i = 0;
b4a.example.graficos.charts._graph _g = null;
 //BA.debugLineNum = 130;BA.debugLine="Sub CreateLineTab";
 //BA.debugLineNum = 131;BA.debugLine="Dim p As Panel";
_p = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 132;BA.debugLine="p.Initialize(\"\")";
_p.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 133;BA.debugLine="pnlLine.Initialize(\"pnlLine\")";
mostCurrent._pnlline.Initialize(mostCurrent.activityBA,"pnlLine");
 //BA.debugLineNum = 134;BA.debugLine="p.AddView(pnlLine, 0, 0, 95%x, 100%y - 100dip)";
_p.AddView((android.view.View)(mostCurrent._pnlline.getObject()),(int) (0),(int) (0),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (95),mostCurrent.activityBA),(int) (anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (100))));
 //BA.debugLineNum = 135;BA.debugLine="TabHost1.AddTab2(\"Line Chart\", p)";
mostCurrent._tabhost1.AddTab2("Line Chart",(android.view.View)(_p.getObject()));
 //BA.debugLineNum = 136;BA.debugLine="Dim LD As LineData";
_ld = new b4a.example.graficos.charts._linedata();
 //BA.debugLineNum = 137;BA.debugLine="LD.Initialize";
_ld.Initialize();
 //BA.debugLineNum = 138;BA.debugLine="LD.Target = pnlLine";
_ld.Target = mostCurrent._pnlline;
 //BA.debugLineNum = 139;BA.debugLine="Charts.AddLineColor(LD, Colors.Red)";
mostCurrent._charts._addlinecolor(mostCurrent.activityBA,_ld,anywheresoftware.b4a.keywords.Common.Colors.Red);
 //BA.debugLineNum = 140;BA.debugLine="For i = 0 To 360 Step 10";
{
final int step112 = (int) (10);
final int limit112 = (int) (360);
for (_i = (int) (0); (step112 > 0 && _i <= limit112) || (step112 < 0 && _i >= limit112); _i = ((int)(0 + _i + step112))) {
 //BA.debugLineNum = 141;BA.debugLine="Charts.AddLinePoint(LD, i, SinD(i), i Mod 90 = 0";
mostCurrent._charts._addlinepoint(mostCurrent.activityBA,_ld,BA.NumberToString(_i),(float) (anywheresoftware.b4a.keywords.Common.SinD(_i)),_i%90==0);
 }
};
 //BA.debugLineNum = 143;BA.debugLine="Dim G As Graph";
_g = new b4a.example.graficos.charts._graph();
 //BA.debugLineNum = 144;BA.debugLine="G.Initialize";
_g.Initialize();
 //BA.debugLineNum = 145;BA.debugLine="G.Title = \"Line Chart (Sine)\"";
_g.Title = "Line Chart (Sine)";
 //BA.debugLineNum = 146;BA.debugLine="G.XAxis = \"Degrees\"";
_g.XAxis = "Degrees";
 //BA.debugLineNum = 147;BA.debugLine="G.YAxis = \"Values\"";
_g.YAxis = "Values";
 //BA.debugLineNum = 148;BA.debugLine="G.YStart = -1";
_g.YStart = (float) (-1);
 //BA.debugLineNum = 149;BA.debugLine="G.YEnd = 1";
_g.YEnd = (float) (1);
 //BA.debugLineNum = 150;BA.debugLine="G.YInterval = 0.2";
_g.YInterval = (float) (0.2);
 //BA.debugLineNum = 151;BA.debugLine="G.AxisColor = Colors.Black";
_g.AxisColor = anywheresoftware.b4a.keywords.Common.Colors.Black;
 //BA.debugLineNum = 152;BA.debugLine="Charts.DrawLineChart(G, LD, Colors.White)";
mostCurrent._charts._drawlinechart(mostCurrent.activityBA,_g,_ld,anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 153;BA.debugLine="End Sub";
return "";
}
public static String  _createpietab() throws Exception{
anywheresoftware.b4a.objects.PanelWrapper _p = null;
b4a.example.graficos.charts._piedata _pd = null;
anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper _legend = null;
anywheresoftware.b4a.objects.ImageViewWrapper _imageview1 = null;
 //BA.debugLineNum = 155;BA.debugLine="Sub CreatePieTab";
 //BA.debugLineNum = 156;BA.debugLine="Dim p As Panel";
_p = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 157;BA.debugLine="p.Initialize(\"\")";
_p.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 158;BA.debugLine="pnlPie.Initialize(\"pnlPie\")";
mostCurrent._pnlpie.Initialize(mostCurrent.activityBA,"pnlPie");
 //BA.debugLineNum = 159;BA.debugLine="p.AddView(pnlPie, 0, 0, 100%x, 100%y - 100dip)";
_p.AddView((android.view.View)(mostCurrent._pnlpie.getObject()),(int) (0),(int) (0),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),(int) (anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (100))));
 //BA.debugLineNum = 160;BA.debugLine="TabHost1.AddTab2(\"Pie Chart\", p)";
mostCurrent._tabhost1.AddTab2("Pie Chart",(android.view.View)(_p.getObject()));
 //BA.debugLineNum = 161;BA.debugLine="Dim PD As PieData";
_pd = new b4a.example.graficos.charts._piedata();
 //BA.debugLineNum = 162;BA.debugLine="PD.Initialize";
_pd.Initialize();
 //BA.debugLineNum = 163;BA.debugLine="PD.Target = pnlPie 'Set the target view";
_pd.Target = mostCurrent._pnlpie;
 //BA.debugLineNum = 165;BA.debugLine="Charts.AddPieItem(PD, \"Item #1\", 120, 0)";
mostCurrent._charts._addpieitem(mostCurrent.activityBA,_pd,"Item #1",(float) (120),(int) (0));
 //BA.debugLineNum = 166;BA.debugLine="Charts.AddPieItem(PD, \"Item #2\", 25, 0)";
mostCurrent._charts._addpieitem(mostCurrent.activityBA,_pd,"Item #2",(float) (25),(int) (0));
 //BA.debugLineNum = 167;BA.debugLine="Charts.AddPieItem(PD, \"Item #3\", 50, 0)";
mostCurrent._charts._addpieitem(mostCurrent.activityBA,_pd,"Item #3",(float) (50),(int) (0));
 //BA.debugLineNum = 168;BA.debugLine="Charts.AddPieItem(PD, \"Item #4\", 190, 0)";
mostCurrent._charts._addpieitem(mostCurrent.activityBA,_pd,"Item #4",(float) (190),(int) (0));
 //BA.debugLineNum = 169;BA.debugLine="Charts.AddPieItem(PD, \"Item #5\", 350, 0)";
mostCurrent._charts._addpieitem(mostCurrent.activityBA,_pd,"Item #5",(float) (350),(int) (0));
 //BA.debugLineNum = 170;BA.debugLine="PD.GapDegrees = 0 'Total size of gaps between sli";
_pd.GapDegrees = (int) (0);
 //BA.debugLineNum = 171;BA.debugLine="PD.LegendBackColor = Colors.ARGB(150, 100, 100, 1";
_pd.LegendBackColor = anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (150),(int) (100),(int) (100),(int) (100));
 //BA.debugLineNum = 172;BA.debugLine="Dim legend As Bitmap";
_legend = new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper();
 //BA.debugLineNum = 177;BA.debugLine="legend = Charts.DrawPie(PD, Colors.Gray, True)";
_legend = mostCurrent._charts._drawpie(mostCurrent.activityBA,_pd,anywheresoftware.b4a.keywords.Common.Colors.Gray,anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 178;BA.debugLine="Dim ImageView1 As ImageView";
_imageview1 = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 179;BA.debugLine="ImageView1.Initialize(\"\")";
_imageview1.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 180;BA.debugLine="ImageView1.SetBackgroundImage(legend)";
_imageview1.SetBackgroundImage((android.graphics.Bitmap)(_legend.getObject()));
 //BA.debugLineNum = 181;BA.debugLine="pnlPie.AddView(ImageView1, 10dip, 10dip, legend.W";
mostCurrent._pnlpie.AddView((android.view.View)(_imageview1.getObject()),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (10)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (10)),_legend.getWidth(),_legend.getHeight());
 //BA.debugLineNum = 183;BA.debugLine="End Sub";
return "";
}
public static String  _createstackedbarstab() throws Exception{
anywheresoftware.b4a.objects.PanelWrapper _p = null;
b4a.example.graficos.charts._bardata _bd = null;
int _i = 0;
b4a.example.graficos.charts._graph _g = null;
 //BA.debugLineNum = 69;BA.debugLine="Sub CreateStackedBarsTab";
 //BA.debugLineNum = 70;BA.debugLine="Dim p As Panel";
_p = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 71;BA.debugLine="p.Initialize(\"\")";
_p.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 72;BA.debugLine="pnlStackedBars.Initialize(\"pnlStackedBars\")";
mostCurrent._pnlstackedbars.Initialize(mostCurrent.activityBA,"pnlStackedBars");
 //BA.debugLineNum = 73;BA.debugLine="p.AddView(pnlStackedBars, 0, 0, 95%x, 100%y - 100";
_p.AddView((android.view.View)(mostCurrent._pnlstackedbars.getObject()),(int) (0),(int) (0),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (95),mostCurrent.activityBA),(int) (anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (100))));
 //BA.debugLineNum = 74;BA.debugLine="TabHost1.AddTab2(\"Stacked Bars Chart\", p)";
mostCurrent._tabhost1.AddTab2("Stacked Bars Chart",(android.view.View)(_p.getObject()));
 //BA.debugLineNum = 75;BA.debugLine="Dim BD As BarData";
_bd = new b4a.example.graficos.charts._bardata();
 //BA.debugLineNum = 76;BA.debugLine="BD.Initialize";
_bd.Initialize();
 //BA.debugLineNum = 77;BA.debugLine="BD.Target = pnlStackedBars";
_bd.Target = mostCurrent._pnlstackedbars;
 //BA.debugLineNum = 78;BA.debugLine="BD.BarsWidth = 40dip";
_bd.BarsWidth = anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (40));
 //BA.debugLineNum = 79;BA.debugLine="BD.Stacked = True 'Makes it a stacked bars chart";
_bd.Stacked = anywheresoftware.b4a.keywords.Common.True;
 //BA.debugLineNum = 80;BA.debugLine="Charts.AddBarColor(BD, MakeTransparent(Colors.Dar";
mostCurrent._charts._addbarcolor(mostCurrent.activityBA,_bd,_maketransparent(anywheresoftware.b4a.keywords.Common.Colors.DarkGray,(int) (230)));
 //BA.debugLineNum = 81;BA.debugLine="Charts.AddBarColor(BD, MakeTransparent(Colors.Gra";
mostCurrent._charts._addbarcolor(mostCurrent.activityBA,_bd,_maketransparent(anywheresoftware.b4a.keywords.Common.Colors.Gray,(int) (230)));
 //BA.debugLineNum = 82;BA.debugLine="Charts.AddBarColor(BD, MakeTransparent(Colors.Lig";
mostCurrent._charts._addbarcolor(mostCurrent.activityBA,_bd,_maketransparent(anywheresoftware.b4a.keywords.Common.Colors.LightGray,(int) (230)));
 //BA.debugLineNum = 83;BA.debugLine="For i = 1 To 4";
{
final int step60 = 1;
final int limit60 = (int) (4);
for (_i = (int) (1); (step60 > 0 && _i <= limit60) || (step60 < 0 && _i >= limit60); _i = ((int)(0 + _i + step60))) {
 //BA.debugLineNum = 84;BA.debugLine="Charts.AddBarPoint(BD, 2005 + i, Array As Float(";
mostCurrent._charts._addbarpoint(mostCurrent.activityBA,_bd,BA.NumberToString(2005+_i),new float[]{(float) (anywheresoftware.b4a.keywords.Common.Rnd((int) (0),(int) (500))),(float) (anywheresoftware.b4a.keywords.Common.Rnd((int) (0),(int) (500))),(float) (anywheresoftware.b4a.keywords.Common.Rnd((int) (0),(int) (500)))});
 }
};
 //BA.debugLineNum = 86;BA.debugLine="Dim G As Graph";
_g = new b4a.example.graficos.charts._graph();
 //BA.debugLineNum = 87;BA.debugLine="G.Initialize";
_g.Initialize();
 //BA.debugLineNum = 88;BA.debugLine="G.Title = \"Stacked Bars Chart\"";
_g.Title = "Stacked Bars Chart";
 //BA.debugLineNum = 89;BA.debugLine="G.XAxis = \"Year\"";
_g.XAxis = "Year";
 //BA.debugLineNum = 90;BA.debugLine="G.YAxis = \"Values\"";
_g.YAxis = "Values";
 //BA.debugLineNum = 91;BA.debugLine="G.YStart = 0";
_g.YStart = (float) (0);
 //BA.debugLineNum = 92;BA.debugLine="G.YEnd = 1000";
_g.YEnd = (float) (1000);
 //BA.debugLineNum = 93;BA.debugLine="G.YInterval = 100";
_g.YInterval = (float) (100);
 //BA.debugLineNum = 94;BA.debugLine="G.AxisColor = Colors.Black";
_g.AxisColor = anywheresoftware.b4a.keywords.Common.Colors.Black;
 //BA.debugLineNum = 95;BA.debugLine="Charts.DrawBarsChart(G, BD, Colors.White)";
mostCurrent._charts._drawbarschart(mostCurrent.activityBA,_g,_bd,anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 96;BA.debugLine="End Sub";
return "";
}

public static void initializeProcessGlobals() {
    
    if (main.processGlobalsRun == false) {
	    main.processGlobalsRun = true;
		try {
		        main._process_globals();
charts._process_globals();
		
        } catch (Exception e) {
			throw new RuntimeException(e);
		}
    }
}public static String  _globals() throws Exception{
 //BA.debugLineNum = 20;BA.debugLine="Sub Globals";
 //BA.debugLineNum = 21;BA.debugLine="Dim pnlPie, pnlLine, pnl2Lines, pnlBars, pnlStack";
mostCurrent._pnlpie = new anywheresoftware.b4a.objects.PanelWrapper();
mostCurrent._pnlline = new anywheresoftware.b4a.objects.PanelWrapper();
mostCurrent._pnl2lines = new anywheresoftware.b4a.objects.PanelWrapper();
mostCurrent._pnlbars = new anywheresoftware.b4a.objects.PanelWrapper();
mostCurrent._pnlstackedbars = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 22;BA.debugLine="Dim TabHost1 As TabHost";
mostCurrent._tabhost1 = new anywheresoftware.b4a.objects.TabHostWrapper();
 //BA.debugLineNum = 23;BA.debugLine="End Sub";
return "";
}
public static int  _maketransparent(int _color,int _alpha) throws Exception{
 //BA.debugLineNum = 98;BA.debugLine="Sub MakeTransparent(Color As Int, Alpha As Int) As";
 //BA.debugLineNum = 99;BA.debugLine="Return Bit.AND(Color, Bit.OR(0x00FFFFFF, Bit.Shif";
if (true) return anywheresoftware.b4a.keywords.Common.Bit.And(_color,anywheresoftware.b4a.keywords.Common.Bit.Or((int) (0x00ffffff),anywheresoftware.b4a.keywords.Common.Bit.ShiftLeft(_alpha,(int) (24))));
 //BA.debugLineNum = 100;BA.debugLine="End Sub";
return 0;
}
public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 15;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 16;BA.debugLine="Dim FontSize = 12 As Float";
_fontsize = (float) (12);
 //BA.debugLineNum = 17;BA.debugLine="Dim MaxVal, MinVal, ScaleMin, ScaleMax, ScaleInte";
_maxval = 0;
_minval = 0;
_scalemin = 0;
_scalemax = 0;
_scaleinterval = 0;
 //BA.debugLineNum = 18;BA.debugLine="End Sub";
return "";
}
}
