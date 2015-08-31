package b4a.example;


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
			processBA = new BA(this.getApplicationContext(), null, null, "b4a.example", "b4a.example.main");
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
		activityBA = new BA(this, layout, processBA, "b4a.example", "b4a.example.main");
        
        processBA.sharedProcessBA.activityBA = new java.lang.ref.WeakReference<BA>(activityBA);
        anywheresoftware.b4a.objects.ViewWrapper.lastId = 0;
        _activity = new ActivityWrapper(activityBA, "activity");
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        if (BA.isShellModeRuntimeCheck(processBA)) {
			if (isFirst)
				processBA.raiseEvent2(null, true, "SHELL", false);
			processBA.raiseEvent2(null, true, "CREATE", true, "b4a.example.main", processBA, activityBA, _activity, anywheresoftware.b4a.keywords.Common.Density, mostCurrent);
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
public flm.b4a.gesturedetector.GestureDetectorForB4A _gd = null;
public anywheresoftware.b4a.objects.PanelWrapper _pnlpie = null;
public anywheresoftware.b4a.objects.TabHostWrapper _tabhost1 = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imageview1 = null;
public anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper _legend = null;
public anywheresoftware.b4a.objects.SpinnerWrapper _spinnernota = null;
public anywheresoftware.b4a.objects.ListViewWrapper _listviewnotas = null;
public anywheresoftware.b4a.objects.collections.List _listaingresos = null;
public anywheresoftware.b4a.objects.EditTextWrapper _edittexttitulo = null;
public anywheresoftware.b4a.objects.SpinnerWrapper _spinnertipo = null;
public b4a.example.charts _charts = null;

public static boolean isAnyActivityVisible() {
    boolean vis = false;
vis = vis | (main.mostCurrent != null);
return vis;}
public static class _ingreso{
public boolean IsInitialized;
public String Titulo;
public double Nota;
public String Tipo;
public void Initialize() {
IsInitialized = true;
Titulo = "";
Nota = 0;
Tipo = "";
}
@Override
		public String toString() {
			return BA.TypeToString(this, false);
		}}
public static String  _activity_create(boolean _firsttime) throws Exception{
 //BA.debugLineNum = 44;BA.debugLine="Sub Activity_Create(FirstTime As Boolean)";
 //BA.debugLineNum = 46;BA.debugLine="GD.SetOnGestureListener(Activity, \"Gesture\")";
mostCurrent._gd.SetOnGestureListener(processBA,(android.view.View)(mostCurrent._activity.getObject()),"Gesture");
 //BA.debugLineNum = 48;BA.debugLine="TabHost1.Initialize(\"TabHost1\")";
mostCurrent._tabhost1.Initialize(mostCurrent.activityBA,"TabHost1");
 //BA.debugLineNum = 51;BA.debugLine="Activity.AddView(TabHost1, 0, 0, 100%x, 100%y)";
mostCurrent._activity.AddView((android.view.View)(mostCurrent._tabhost1.getObject()),(int) (0),(int) (0),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA));
 //BA.debugLineNum = 52;BA.debugLine="TabHost1.AddTab(\"Ingreso de Reseñas\",\"Layout1\")";
mostCurrent._tabhost1.AddTab(mostCurrent.activityBA,"Ingreso de Reseñas","Layout1");
 //BA.debugLineNum = 53;BA.debugLine="Activity.Title=\"Desafío 1\"";
mostCurrent._activity.setTitle((Object)("Desafío 1"));
 //BA.debugLineNum = 55;BA.debugLine="listaIngresos.Initialize";
mostCurrent._listaingresos.Initialize();
 //BA.debugLineNum = 57;BA.debugLine="LLenaSpinnerNota";
_llenaspinnernota();
 //BA.debugLineNum = 58;BA.debugLine="SpinnerTipo.Add(\"Solo Título\")";
mostCurrent._spinnertipo.Add("Solo Título");
 //BA.debugLineNum = 59;BA.debugLine="SpinnerTipo.Add(\"Texto\")";
mostCurrent._spinnertipo.Add("Texto");
 //BA.debugLineNum = 60;BA.debugLine="SpinnerTipo.Add(\"Voz\")";
mostCurrent._spinnertipo.Add("Voz");
 //BA.debugLineNum = 61;BA.debugLine="TabGraficoPie";
_tabgraficopie();
 //BA.debugLineNum = 63;BA.debugLine="End Sub";
return "";
}
public static String  _activity_pause(boolean _userclosed) throws Exception{
 //BA.debugLineNum = 69;BA.debugLine="Sub Activity_Pause (UserClosed As Boolean)";
 //BA.debugLineNum = 71;BA.debugLine="End Sub";
return "";
}
public static String  _activity_resume() throws Exception{
 //BA.debugLineNum = 65;BA.debugLine="Sub Activity_Resume";
 //BA.debugLineNum = 67;BA.debugLine="End Sub";
return "";
}
public static String  _addvalorgrafico(anywheresoftware.b4a.objects.collections.Map _datos) throws Exception{
b4a.example.charts._piedata _pd = null;
String _k = "";
 //BA.debugLineNum = 82;BA.debugLine="Sub addValorGrafico(datos As Map)";
 //BA.debugLineNum = 84;BA.debugLine="Dim PD As PieData";
_pd = new b4a.example.charts._piedata();
 //BA.debugLineNum = 85;BA.debugLine="PD.Initialize";
_pd.Initialize();
 //BA.debugLineNum = 86;BA.debugLine="PD.Target = pnlPie 'Set the target view";
_pd.Target = mostCurrent._pnlpie;
 //BA.debugLineNum = 88;BA.debugLine="For Each k As String In datos.Keys";
final anywheresoftware.b4a.BA.IterableList group45 = _datos.Keys();
final int groupLen45 = group45.getSize();
for (int index45 = 0;index45 < groupLen45 ;index45++){
_k = BA.ObjectToString(group45.Get(index45));
 //BA.debugLineNum = 89;BA.debugLine="Charts.AddPieItem(PD, k, datos.Get(k), 0)";
mostCurrent._charts._addpieitem(mostCurrent.activityBA,_pd,_k,(float)(BA.ObjectToNumber(_datos.Get((Object)(_k)))),(int) (0));
 }
;
 //BA.debugLineNum = 92;BA.debugLine="PD.GapDegrees = 0 'Total size of gaps between sli";
_pd.GapDegrees = (int) (0);
 //BA.debugLineNum = 93;BA.debugLine="PD.LegendBackColor = Colors.ARGB(150, 100, 100, 1";
_pd.LegendBackColor = anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (150),(int) (100),(int) (100),(int) (100));
 //BA.debugLineNum = 99;BA.debugLine="legend = Charts.DrawPie(PD, Colors.Gray, True)";
mostCurrent._legend = mostCurrent._charts._drawpie(mostCurrent.activityBA,_pd,anywheresoftware.b4a.keywords.Common.Colors.Gray,anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 101;BA.debugLine="If ImageView1.IsInitialized=False Then ImageView1";
if (mostCurrent._imageview1.IsInitialized()==anywheresoftware.b4a.keywords.Common.False) { 
mostCurrent._imageview1.Initialize(mostCurrent.activityBA,"");};
 //BA.debugLineNum = 102;BA.debugLine="ImageView1.RemoveView";
mostCurrent._imageview1.RemoveView();
 //BA.debugLineNum = 103;BA.debugLine="ImageView1.SetBackgroundImage(legend)";
mostCurrent._imageview1.SetBackgroundImage((android.graphics.Bitmap)(mostCurrent._legend.getObject()));
 //BA.debugLineNum = 105;BA.debugLine="pnlPie.AddView(ImageView1, 10dip, 10dip, legend.W";
mostCurrent._pnlpie.AddView((android.view.View)(mostCurrent._imageview1.getObject()),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (10)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (10)),mostCurrent._legend.getWidth(),mostCurrent._legend.getHeight());
 //BA.debugLineNum = 106;BA.debugLine="End Sub";
return "";
}
public static String  _buttoningresar_click() throws Exception{
b4a.example.main._ingreso _reseña = null;
 //BA.debugLineNum = 154;BA.debugLine="Sub ButtonIngresar_Click";
 //BA.debugLineNum = 156;BA.debugLine="If EditTextTitulo.Text.Trim=\"\" Then";
if ((mostCurrent._edittexttitulo.getText().trim()).equals("")) { 
 //BA.debugLineNum = 157;BA.debugLine="Msgbox(\"Falta ingresar el Título\",\"Error\")";
anywheresoftware.b4a.keywords.Common.Msgbox("Falta ingresar el Título","Error",mostCurrent.activityBA);
 }else {
 //BA.debugLineNum = 159;BA.debugLine="Dim Reseña As Ingreso";
_reseña = new b4a.example.main._ingreso();
 //BA.debugLineNum = 160;BA.debugLine="Reseña.Initialize";
_reseña.Initialize();
 //BA.debugLineNum = 161;BA.debugLine="Reseña.Titulo=EditTextTitulo.Text";
_reseña.Titulo = mostCurrent._edittexttitulo.getText();
 //BA.debugLineNum = 162;BA.debugLine="Reseña.Nota=SpinnerNota.SelectedItem";
_reseña.Nota = (double)(Double.parseDouble(mostCurrent._spinnernota.getSelectedItem()));
 //BA.debugLineNum = 163;BA.debugLine="Reseña.Tipo=SpinnerTipo.SelectedItem";
_reseña.Tipo = mostCurrent._spinnertipo.getSelectedItem();
 //BA.debugLineNum = 165;BA.debugLine="listaIngresos.Add(Reseña)";
mostCurrent._listaingresos.Add((Object)(_reseña));
 //BA.debugLineNum = 167;BA.debugLine="addValorGrafico(CalculaEstadistica(listaIngresos";
_addvalorgrafico(_calculaestadistica(mostCurrent._listaingresos));
 //BA.debugLineNum = 169;BA.debugLine="ListViewNotas.AddTwoLines(Reseña.Nota, Reseña.Ti";
mostCurrent._listviewnotas.AddTwoLines(BA.NumberToString(_reseña.Nota),_reseña.Titulo+" ("+_reseña.Tipo+")");
 //BA.debugLineNum = 170;BA.debugLine="Msgbox(\"Has ingresado tu Reseña correctamente\",\"";
anywheresoftware.b4a.keywords.Common.Msgbox("Has ingresado tu Reseña correctamente","Exito",mostCurrent.activityBA);
 };
 //BA.debugLineNum = 173;BA.debugLine="End Sub";
return "";
}
public static anywheresoftware.b4a.objects.collections.Map  _calculaestadistica(anywheresoftware.b4a.objects.collections.List _lista) throws Exception{
b4a.example.main._ingreso _reseñaactual = null;
anywheresoftware.b4a.objects.collections.Map _mapa = null;
int _suma = 0;
int _i = 0;
int _j = 0;
 //BA.debugLineNum = 108;BA.debugLine="Sub CalculaEstadistica(lista As List) As Map";
 //BA.debugLineNum = 109;BA.debugLine="Dim ReseñaActual As Ingreso";
_reseñaactual = new b4a.example.main._ingreso();
 //BA.debugLineNum = 110;BA.debugLine="Dim Mapa As Map";
_mapa = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 111;BA.debugLine="Dim suma As Int = 0";
_suma = (int) (0);
 //BA.debugLineNum = 113;BA.debugLine="For i=0 To 2";
{
final int step60 = 1;
final int limit60 = (int) (2);
for (_i = (int) (0); (step60 > 0 && _i <= limit60) || (step60 < 0 && _i >= limit60); _i = ((int)(0 + _i + step60))) {
 //BA.debugLineNum = 114;BA.debugLine="For j=0 To listaIngresos.Size-1";
{
final int step61 = 1;
final int limit61 = (int) (mostCurrent._listaingresos.getSize()-1);
for (_j = (int) (0); (step61 > 0 && _j <= limit61) || (step61 < 0 && _j >= limit61); _j = ((int)(0 + _j + step61))) {
 //BA.debugLineNum = 115;BA.debugLine="ReseñaActual=lista.Get(j)";
_reseñaactual = (b4a.example.main._ingreso)(_lista.Get(_j));
 //BA.debugLineNum = 116;BA.debugLine="Select i";
switch (BA.switchObjectToInt(_i,(int)(Double.parseDouble("0")),(int)(Double.parseDouble("1")),(int)(Double.parseDouble("2")))) {
case 0:
 //BA.debugLineNum = 118;BA.debugLine="If ReseñaActual.Tipo=\"Solo Título\" Then";
if ((_reseñaactual.Tipo).equals("Solo Título")) { 
 //BA.debugLineNum = 119;BA.debugLine="suma=suma+1";
_suma = (int) (_suma+1);
 };
 break;
case 1:
 //BA.debugLineNum = 123;BA.debugLine="If ReseñaActual.Tipo=\"Texto\" Then";
if ((_reseñaactual.Tipo).equals("Texto")) { 
 //BA.debugLineNum = 124;BA.debugLine="suma=suma+1";
_suma = (int) (_suma+1);
 };
 break;
case 2:
 //BA.debugLineNum = 128;BA.debugLine="If ReseñaActual.Tipo=\"Voz\" Then";
if ((_reseñaactual.Tipo).equals("Voz")) { 
 //BA.debugLineNum = 129;BA.debugLine="suma=suma+1";
_suma = (int) (_suma+1);
 };
 break;
}
;
 }
};
 //BA.debugLineNum = 135;BA.debugLine="If Mapa.IsInitialized=False Then Mapa.Initialize";
if (_mapa.IsInitialized()==anywheresoftware.b4a.keywords.Common.False) { 
_mapa.Initialize();};
 //BA.debugLineNum = 136;BA.debugLine="If i=0 Then Mapa.Put(\"Solo Título\",suma)";
if (_i==0) { 
_mapa.Put((Object)("Solo Título"),(Object)(_suma));};
 //BA.debugLineNum = 137;BA.debugLine="If i=1 Then Mapa.Put(\"Texto\",suma)";
if (_i==1) { 
_mapa.Put((Object)("Texto"),(Object)(_suma));};
 //BA.debugLineNum = 138;BA.debugLine="If i=2 Then Mapa.Put(\"Voz\",suma)";
if (_i==2) { 
_mapa.Put((Object)("Voz"),(Object)(_suma));};
 //BA.debugLineNum = 140;BA.debugLine="suma=0";
_suma = (int) (0);
 }
};
 //BA.debugLineNum = 143;BA.debugLine="Return Mapa";
if (true) return _mapa;
 //BA.debugLineNum = 144;BA.debugLine="End Sub";
return null;
}
public static String  _gesture_onfling(float _velocityx,float _velocityy,Object _motionevent1,Object _motionevent2) throws Exception{
 //BA.debugLineNum = 175;BA.debugLine="Sub Gesture_onFling(velocityX As Float, velocityY";
 //BA.debugLineNum = 176;BA.debugLine="Log(\"   onFling velocityX = \" & velocityX & \", ve";
anywheresoftware.b4a.keywords.Common.Log("   onFling velocityX = "+BA.NumberToString(_velocityx)+", velocityY = "+BA.NumberToString(_velocityy)+", ev1 = "+BA.ObjectToString(_motionevent1)+", ev2 = "+BA.ObjectToString(_motionevent2));
 //BA.debugLineNum = 177;BA.debugLine="If velocityX<0 Then";
if (_velocityx<0) { 
 //BA.debugLineNum = 178;BA.debugLine="TabHost1.CurrentTab=(TabHost1.CurrentTab+1) Mod";
mostCurrent._tabhost1.setCurrentTab((int) ((mostCurrent._tabhost1.getCurrentTab()+1)%mostCurrent._tabhost1.getTabCount()));
 }else {
 //BA.debugLineNum = 180;BA.debugLine="If TabHost1.CurrentTab=0 Then";
if (mostCurrent._tabhost1.getCurrentTab()==0) { 
 //BA.debugLineNum = 181;BA.debugLine="TabHost1.CurrentTab=TabHost1.TabCount-1";
mostCurrent._tabhost1.setCurrentTab((int) (mostCurrent._tabhost1.getTabCount()-1));
 }else {
 //BA.debugLineNum = 183;BA.debugLine="TabHost1.CurrentTab=(TabHost1.CurrentTab-1) Mod";
mostCurrent._tabhost1.setCurrentTab((int) ((mostCurrent._tabhost1.getCurrentTab()-1)%mostCurrent._tabhost1.getTabCount()));
 };
 };
 //BA.debugLineNum = 188;BA.debugLine="End Sub";
return "";
}
public static String  _globals() throws Exception{
 //BA.debugLineNum = 23;BA.debugLine="Sub Globals";
 //BA.debugLineNum = 26;BA.debugLine="Dim GD As GestureDetector";
mostCurrent._gd = new flm.b4a.gesturedetector.GestureDetectorForB4A();
 //BA.debugLineNum = 27;BA.debugLine="Dim pnlPie As Panel";
mostCurrent._pnlpie = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 28;BA.debugLine="Dim TabHost1 As TabHost";
mostCurrent._tabhost1 = new anywheresoftware.b4a.objects.TabHostWrapper();
 //BA.debugLineNum = 30;BA.debugLine="Dim ImageView1 As ImageView";
mostCurrent._imageview1 = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 31;BA.debugLine="Dim legend As Bitmap";
mostCurrent._legend = new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper();
 //BA.debugLineNum = 33;BA.debugLine="Private SpinnerNota As Spinner";
mostCurrent._spinnernota = new anywheresoftware.b4a.objects.SpinnerWrapper();
 //BA.debugLineNum = 34;BA.debugLine="Private ListViewNotas As ListView";
mostCurrent._listviewnotas = new anywheresoftware.b4a.objects.ListViewWrapper();
 //BA.debugLineNum = 36;BA.debugLine="Dim listaIngresos As List";
mostCurrent._listaingresos = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 39;BA.debugLine="Private EditTextTitulo As EditText";
mostCurrent._edittexttitulo = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 41;BA.debugLine="Private SpinnerTipo As Spinner";
mostCurrent._spinnertipo = new anywheresoftware.b4a.objects.SpinnerWrapper();
 //BA.debugLineNum = 42;BA.debugLine="End Sub";
return "";
}
public static String  _llenaspinnernota() throws Exception{
double _i = 0;
 //BA.debugLineNum = 146;BA.debugLine="Sub LLenaSpinnerNota";
 //BA.debugLineNum = 147;BA.debugLine="Dim I As Double=7.0";
_i = 7.0;
 //BA.debugLineNum = 148;BA.debugLine="Do While I>=1.0";
while (_i>=1.0) {
 //BA.debugLineNum = 149;BA.debugLine="SpinnerNota.add(Round2(I,1))";
mostCurrent._spinnernota.Add(BA.NumberToString(anywheresoftware.b4a.keywords.Common.Round2(_i,(int) (1))));
 //BA.debugLineNum = 150;BA.debugLine="I=I-0.1";
_i = _i-0.1;
 }
;
 //BA.debugLineNum = 152;BA.debugLine="End Sub";
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
}public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 15;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 18;BA.debugLine="Dim FontSize = 12 As Float";
_fontsize = (float) (12);
 //BA.debugLineNum = 19;BA.debugLine="Dim MaxVal, MinVal, ScaleMin, ScaleMax, ScaleInte";
_maxval = 0;
_minval = 0;
_scalemin = 0;
_scalemax = 0;
_scaleinterval = 0;
 //BA.debugLineNum = 20;BA.debugLine="Type Ingreso (Titulo As String, Nota As Double, T";
;
 //BA.debugLineNum = 21;BA.debugLine="End Sub";
return "";
}
public static String  _tabgraficopie() throws Exception{
anywheresoftware.b4a.objects.PanelWrapper _p = null;
 //BA.debugLineNum = 73;BA.debugLine="Sub TabGraficoPie";
 //BA.debugLineNum = 74;BA.debugLine="Dim p As Panel";
_p = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 75;BA.debugLine="p.Initialize(\"\")";
_p.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 76;BA.debugLine="pnlPie.Initialize(\"pnlPie\")";
mostCurrent._pnlpie.Initialize(mostCurrent.activityBA,"pnlPie");
 //BA.debugLineNum = 77;BA.debugLine="p.AddView(pnlPie, 0, 0, 100%x, 100%y - 100dip)";
_p.AddView((android.view.View)(mostCurrent._pnlpie.getObject()),(int) (0),(int) (0),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),(int) (anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (100))));
 //BA.debugLineNum = 78;BA.debugLine="TabHost1.AddTab2(\"Resumen\", p)";
mostCurrent._tabhost1.AddTab2("Resumen",(android.view.View)(_p.getObject()));
 //BA.debugLineNum = 80;BA.debugLine="End Sub";
return "";
}
}
