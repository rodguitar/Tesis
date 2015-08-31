package mediaplayer.mio;

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
	public static final boolean fullScreen = false;
	public static final boolean includeTitle = true;
    public static WeakReference<Activity> previousOne;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (isFirst) {
			processBA = new BA(this.getApplicationContext(), null, null, "mediaplayer.mio", "mediaplayer.mio.main");
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
		activityBA = new BA(this, layout, processBA, "mediaplayer.mio", "mediaplayer.mio.main");
        
        processBA.sharedProcessBA.activityBA = new java.lang.ref.WeakReference<BA>(activityBA);
        anywheresoftware.b4a.objects.ViewWrapper.lastId = 0;
        _activity = new ActivityWrapper(activityBA, "activity");
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        if (BA.isShellModeRuntimeCheck(processBA)) {
			if (isFirst)
				processBA.raiseEvent2(null, true, "SHELL", false);
			processBA.raiseEvent2(null, true, "CREATE", true, "mediaplayer.mio.main", processBA, activityBA, _activity, anywheresoftware.b4a.keywords.Common.Density, mostCurrent);
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
//        try {
//            if (processBA.subExists("activity_actionbarhomeclick")) {
//                Class.forName("android.app.ActionBar").getMethod("setHomeButtonEnabled", boolean.class).invoke(
//                    getClass().getMethod("getActionBar").invoke(this), true);
//                BA.Log("adding event");
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
		return true;
	}   
// @Override
// public boolean onOptionsItemSelected(android.view.MenuItem item) {
//    if (item.getItemId() == 16908332) {
//        processBA.raiseEvent(null, "activity_actionbarhomeclick");
//        return true;
//    }
//    else
//        return super.onOptionsItemSelected(item); 
//}
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
	}

	@Override
	public void onDestroy() {
        super.onDestroy();
		previousOne = null;
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
	}
	private static void initializeGlobals() {
		processBA.raiseEvent2(null, true, "globals", false, (Object[])null);
	}

public anywheresoftware.b4a.keywords.Common __c = null;
public static anywheresoftware.b4a.objects.Timer _timer1 = null;
public static anywheresoftware.b4a.objects.MediaPlayerWrapper _mp = null;
public static boolean _pausa = false;
public anywheresoftware.b4a.objects.CompoundButtonWrapper.ToggleButtonWrapper _togglebutton1 = null;
public anywheresoftware.b4a.objects.ButtonWrapper _button1 = null;
public anywheresoftware.b4a.objects.ButtonWrapper _button2 = null;
public anywheresoftware.b4a.objects.ButtonWrapper _button3 = null;
public anywheresoftware.b4a.objects.SeekBarWrapper _seekbar1 = null;
public anywheresoftware.b4a.objects.SeekBarWrapper _seekbar2 = null;
public anywheresoftware.b4a.objects.LabelWrapper _label2 = null;
public anywheresoftware.b4a.objects.LabelWrapper _label3 = null;

public static boolean isAnyActivityVisible() {
    boolean vis = false;
vis = vis | (main.mostCurrent != null);
return vis;}
public static String  _activity_create(boolean _firsttime) throws Exception{
 //BA.debugLineNum = 29;BA.debugLine="Sub Activity_Create(FirstTime As Boolean)";
 //BA.debugLineNum = 30;BA.debugLine="Activity.LoadLayout(\"Layout\") ' Cargamos el Designer";
mostCurrent._activity.LoadLayout("Layout",mostCurrent.activityBA);
 //BA.debugLineNum = 32;BA.debugLine="MP.Initialize";
_mp.Initialize();
 //BA.debugLineNum = 33;BA.debugLine="MP.Load(File.DirAssets, \"test_cbr.mp3\")";
_mp.Load(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"test_cbr.mp3");
 //BA.debugLineNum = 37;BA.debugLine="Timer1.Initialize(\"Timer1\", 500) ' 1000 = 1 segundo";
_timer1.Initialize(processBA,"Timer1",(long) (500));
 //BA.debugLineNum = 38;BA.debugLine="Timer1.Enabled = True";
_timer1.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 39;BA.debugLine="End Sub";
return "";
}
public static String  _button1_click() throws Exception{
int _minutos = 0;
int _segundos = 0;
 //BA.debugLineNum = 40;BA.debugLine="Sub Button1_Click";
 //BA.debugLineNum = 41;BA.debugLine="If Not(MP.IsPlaying) Then";
if (anywheresoftware.b4a.keywords.Common.Not(_mp.IsPlaying())) { 
 //BA.debugLineNum = 42;BA.debugLine="If PAUSA Then";
if (_pausa) { 
 //BA.debugLineNum = 43;BA.debugLine="MP.play";
_mp.Play();
 //BA.debugLineNum = 44;BA.debugLine="PAUSA=False";
_pausa = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 46;BA.debugLine="Dim minutos, segundos As Int";
_minutos = 0;
_segundos = 0;
 //BA.debugLineNum = 47;BA.debugLine="segundos = Round(MP.duration / 1000)";
_segundos = (int) (anywheresoftware.b4a.keywords.Common.Round(_mp.getDuration()/(double)1000));
 //BA.debugLineNum = 48;BA.debugLine="minutos = Floor(segundos / 60) Mod 60";
_minutos = (int) (anywheresoftware.b4a.keywords.Common.Floor(_segundos/(double)60)%60);
 //BA.debugLineNum = 49;BA.debugLine="segundos = segundos Mod 60";
_segundos = (int) (_segundos%60);
 //BA.debugLineNum = 51;BA.debugLine="Label2.Text = \"Duración  \" & NumberFormat(minutos,1,0) & \": \" & NumberFormat(segundos,2,0)";
mostCurrent._label2.setText((Object)("Duración  "+anywheresoftware.b4a.keywords.Common.NumberFormat(_minutos,(int) (1),(int) (0))+": "+anywheresoftware.b4a.keywords.Common.NumberFormat(_segundos,(int) (2),(int) (0))));
 }else {
 //BA.debugLineNum = 54;BA.debugLine="MP.Load(File.DirAssets, \"test_cbr.mp3\")";
_mp.Load(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"test_cbr.mp3");
 //BA.debugLineNum = 55;BA.debugLine="MP.play";
_mp.Play();
 //BA.debugLineNum = 58;BA.debugLine="Dim minutos, segundos As Int";
_minutos = 0;
_segundos = 0;
 //BA.debugLineNum = 59;BA.debugLine="segundos = Round(MP.duration / 1000)";
_segundos = (int) (anywheresoftware.b4a.keywords.Common.Round(_mp.getDuration()/(double)1000));
 //BA.debugLineNum = 60;BA.debugLine="minutos = Floor(segundos / 60) Mod 60";
_minutos = (int) (anywheresoftware.b4a.keywords.Common.Floor(_segundos/(double)60)%60);
 //BA.debugLineNum = 61;BA.debugLine="segundos = segundos Mod 60";
_segundos = (int) (_segundos%60);
 //BA.debugLineNum = 63;BA.debugLine="Label2.Text = \"Duración  \" & NumberFormat(minutos,1,0) & \": \" & NumberFormat(segundos,2,0)";
mostCurrent._label2.setText((Object)("Duración  "+anywheresoftware.b4a.keywords.Common.NumberFormat(_minutos,(int) (1),(int) (0))+": "+anywheresoftware.b4a.keywords.Common.NumberFormat(_segundos,(int) (2),(int) (0))));
 };
 };
 //BA.debugLineNum = 66;BA.debugLine="End Sub";
return "";
}
public static String  _button2_click() throws Exception{
 //BA.debugLineNum = 67;BA.debugLine="Sub Button2_Click";
 //BA.debugLineNum = 68;BA.debugLine="If MP.IsPlaying Then";
if (_mp.IsPlaying()) { 
 //BA.debugLineNum = 69;BA.debugLine="MP.pause";
_mp.Pause();
 //BA.debugLineNum = 70;BA.debugLine="PAUSA=True";
_pausa = anywheresoftware.b4a.keywords.Common.True;
 };
 //BA.debugLineNum = 72;BA.debugLine="End Sub";
return "";
}
public static String  _button3_click() throws Exception{
 //BA.debugLineNum = 73;BA.debugLine="Sub Button3_Click";
 //BA.debugLineNum = 75;BA.debugLine="MP.Stop";
_mp.Stop();
 //BA.debugLineNum = 76;BA.debugLine="PAUSA=False";
_pausa = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 78;BA.debugLine="Label3.Text = \"Posición actual  00:00\"";
mostCurrent._label3.setText((Object)("Posición actual  00:00"));
 //BA.debugLineNum = 79;BA.debugLine="seekbar2.Value=0";
mostCurrent._seekbar2.setValue((int) (0));
 //BA.debugLineNum = 80;BA.debugLine="End Sub";
return "";
}

public static void initializeProcessGlobals() {
    
    if (main.processGlobalsRun == false) {
	    main.processGlobalsRun = true;
		try {
		        main._process_globals();
		
        } catch (Exception e) {
			throw new RuntimeException(e);
		}
    }
}public static String  _globals() throws Exception{
 //BA.debugLineNum = 20;BA.debugLine="Sub Globals";
 //BA.debugLineNum = 21;BA.debugLine="Dim PAUSA As Boolean";
_pausa = false;
 //BA.debugLineNum = 22;BA.debugLine="Dim ToggleButton1 As ToggleButton";
mostCurrent._togglebutton1 = new anywheresoftware.b4a.objects.CompoundButtonWrapper.ToggleButtonWrapper();
 //BA.debugLineNum = 23;BA.debugLine="Dim Button1, Button2, Button3 As Button";
mostCurrent._button1 = new anywheresoftware.b4a.objects.ButtonWrapper();
mostCurrent._button2 = new anywheresoftware.b4a.objects.ButtonWrapper();
mostCurrent._button3 = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 24;BA.debugLine="Dim SeekBar1, SeekBar2 As SeekBar";
mostCurrent._seekbar1 = new anywheresoftware.b4a.objects.SeekBarWrapper();
mostCurrent._seekbar2 = new anywheresoftware.b4a.objects.SeekBarWrapper();
 //BA.debugLineNum = 25;BA.debugLine="Dim Label2, Label3 As Label";
mostCurrent._label2 = new anywheresoftware.b4a.objects.LabelWrapper();
mostCurrent._label3 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 27;BA.debugLine="End Sub";
return "";
}
public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 12;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 16;BA.debugLine="Dim Timer1 As Timer";
_timer1 = new anywheresoftware.b4a.objects.Timer();
 //BA.debugLineNum = 17;BA.debugLine="Dim MP As MediaPlayer";
_mp = new anywheresoftware.b4a.objects.MediaPlayerWrapper();
 //BA.debugLineNum = 18;BA.debugLine="End Sub";
return "";
}
public static String  _seekbar1_valuechanged(int _value,boolean _userchanged) throws Exception{
 //BA.debugLineNum = 81;BA.debugLine="Sub SeekBar1_ValueChanged (Value As Int, UserChanged As Boolean)";
 //BA.debugLineNum = 84;BA.debugLine="MP.SetVolume(SeekBar1.Value/100, SeekBar1.Value/100)";
_mp.SetVolume((float) (mostCurrent._seekbar1.getValue()/(double)100),(float) (mostCurrent._seekbar1.getValue()/(double)100));
 //BA.debugLineNum = 87;BA.debugLine="End Sub";
return "";
}
public static String  _seekbar2_valuechanged(int _value,boolean _userchanged) throws Exception{
 //BA.debugLineNum = 114;BA.debugLine="Sub SeekBar2_ValueChanged (Value As Int, UserChanged As Boolean)";
 //BA.debugLineNum = 116;BA.debugLine="If UserChanged = False Then Return";
if (_userchanged==anywheresoftware.b4a.keywords.Common.False) { 
if (true) return "";};
 //BA.debugLineNum = 117;BA.debugLine="MP.position = Value / 100 * MP.duration";
_mp.setPosition((int) (_value/(double)100*_mp.getDuration()));
 //BA.debugLineNum = 118;BA.debugLine="If MP.IsPlaying Then";
if (_mp.IsPlaying()) { 
 //BA.debugLineNum = 119;BA.debugLine="MP.Play";
_mp.Play();
 };
 //BA.debugLineNum = 121;BA.debugLine="End Sub";
return "";
}
public static String  _timer1_tick() throws Exception{
int _minutes = 0;
int _segundes = 0;
 //BA.debugLineNum = 98;BA.debugLine="Sub Timer1_tick";
 //BA.debugLineNum = 100;BA.debugLine="If MP.IsPlaying Then";
if (_mp.IsPlaying()) { 
 //BA.debugLineNum = 102;BA.debugLine="SeekBar2.Value=MP.position / MP.duration * 100";
mostCurrent._seekbar2.setValue((int) (_mp.getPosition()/(double)_mp.getDuration()*100));
 //BA.debugLineNum = 104;BA.debugLine="Dim minutes, segundes As Int";
_minutes = 0;
_segundes = 0;
 //BA.debugLineNum = 105;BA.debugLine="segundes = Round(MP.position / 1000)";
_segundes = (int) (anywheresoftware.b4a.keywords.Common.Round(_mp.getPosition()/(double)1000));
 //BA.debugLineNum = 106;BA.debugLine="minutes = Floor(segundes / 60) Mod 60";
_minutes = (int) (anywheresoftware.b4a.keywords.Common.Floor(_segundes/(double)60)%60);
 //BA.debugLineNum = 107;BA.debugLine="segundes = segundes Mod 60";
_segundes = (int) (_segundes%60);
 //BA.debugLineNum = 109;BA.debugLine="Label3.Text = \"Posición actual  \" & NumberFormat(minutes,1,0) & \": \" & NumberFormat(segundes,2,0)";
mostCurrent._label3.setText((Object)("Posición actual  "+anywheresoftware.b4a.keywords.Common.NumberFormat(_minutes,(int) (1),(int) (0))+": "+anywheresoftware.b4a.keywords.Common.NumberFormat(_segundes,(int) (2),(int) (0))));
 };
 //BA.debugLineNum = 112;BA.debugLine="End Sub";
return "";
}
public static String  _togglebutton1_checkedchange(boolean _checked) throws Exception{
 //BA.debugLineNum = 89;BA.debugLine="Sub ToggleButton1_CheckedChange(Checked As Boolean)";
 //BA.debugLineNum = 91;BA.debugLine="If Checked = True Then";
if (_checked==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 92;BA.debugLine="MP.Looping = True";
_mp.setLooping(anywheresoftware.b4a.keywords.Common.True);
 }else {
 //BA.debugLineNum = 94;BA.debugLine="MP.Looping = False";
_mp.setLooping(anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 96;BA.debugLine="End Sub";
return "";
}
}
