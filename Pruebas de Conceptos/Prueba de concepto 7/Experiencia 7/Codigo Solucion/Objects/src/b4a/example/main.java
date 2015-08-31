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
	public static final boolean includeTitle = false;
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
public static anywheresoftware.b4a.agraham.ioio.B4AIOIO _yoyo = null;
public static anywheresoftware.b4a.objects.Timer _timer_fotoresistor = null;
public static anywheresoftware.b4a.agraham.ioio.B4AIOIO.B4ASpiMaster _spi = null;
public static byte[] _wbuffer = null;
public anywheresoftware.b4a.agraham.ioio.B4AIOIO.B4AAnalogueInputWrapper _pin39_fotoresistor = null;
public anywheresoftware.b4a.objects.LabelWrapper _labelvaloranalogo = null;
public static float _valoranalogofotor = 0f;

public static boolean isAnyActivityVisible() {
    boolean vis = false;
vis = vis | (main.mostCurrent != null);
return vis;}
public static String  _activity_create(boolean _firsttime) throws Exception{
 //BA.debugLineNum = 33;BA.debugLine="Sub Activity_Create(FirstTime As Boolean)";
 //BA.debugLineNum = 35;BA.debugLine="Activity.LoadLayout(\"Layout1\")";
mostCurrent._activity.LoadLayout("Layout1",mostCurrent.activityBA);
 //BA.debugLineNum = 36;BA.debugLine="IOIOConnect";
_ioioconnect();
 //BA.debugLineNum = 37;BA.debugLine="configuracionSPI_pot";
_configuracionspi_pot();
 //BA.debugLineNum = 38;BA.debugLine="Timer_Fotoresistor.Initialize(\"LoopFotoresistor\",";
_timer_fotoresistor.Initialize(processBA,"LoopFotoresistor",(long) (100));
 //BA.debugLineNum = 39;BA.debugLine="Timer_Fotoresistor.Enabled=True";
_timer_fotoresistor.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 40;BA.debugLine="End Sub";
return "";
}
public static String  _activity_pause(boolean _userclosed) throws Exception{
 //BA.debugLineNum = 46;BA.debugLine="Sub Activity_Pause (UserClosed As Boolean)";
 //BA.debugLineNum = 47;BA.debugLine="If UserClosed Then";
if (_userclosed) { 
 //BA.debugLineNum = 48;BA.debugLine="IOIOClose";
_ioioclose();
 //BA.debugLineNum = 49;BA.debugLine="ExitApplication";
anywheresoftware.b4a.keywords.Common.ExitApplication();
 };
 //BA.debugLineNum = 51;BA.debugLine="End Sub";
return "";
}
public static String  _activity_resume() throws Exception{
 //BA.debugLineNum = 42;BA.debugLine="Sub Activity_Resume";
 //BA.debugLineNum = 44;BA.debugLine="End Sub";
return "";
}
public static String  _configuracionspi_pot() throws Exception{
int[] _slavepins = null;
Object[] _slavemodes = null;
 //BA.debugLineNum = 75;BA.debugLine="Sub configuracionSPI_pot";
 //BA.debugLineNum = 76;BA.debugLine="Dim slavepins() As Int";
_slavepins = new int[(int) (0)];
;
 //BA.debugLineNum = 78;BA.debugLine="slavepins = Array As Int(7)";
_slavepins = new int[]{(int) (7)};
 //BA.debugLineNum = 79;BA.debugLine="Dim slavemodes() As Object";
_slavemodes = new Object[(int) (0)];
{
int d0 = _slavemodes.length;
for (int i0 = 0;i0 < d0;i0++) {
_slavemodes[i0] = new Object();
}
}
;
 //BA.debugLineNum = 81;BA.debugLine="slavemodes = Array As Object( SPI.OP_NORMAL)";
_slavemodes = new Object[]{(Object)(_spi.OP_NORMAL)};
 //BA.debugLineNum = 82;BA.debugLine="SPI=YOYO.OpenSpiMaster(9, SPI.IP_PULL_UP, 3, SPI.";
_spi.setObject((ioio.lib.api.SpiMaster)(_yoyo.OpenSpiMaster((int) (9),_spi.IP_PULL_UP,(int) (3),_spi.OP_NORMAL,(int) (6),_spi.OP_NORMAL,_slavepins,_slavemodes,_spi.RATE_1M,anywheresoftware.b4a.keywords.Common.False,anywheresoftware.b4a.keywords.Common.False)));
 //BA.debugLineNum = 84;BA.debugLine="End Sub";
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
 //BA.debugLineNum = 24;BA.debugLine="Sub Globals";
 //BA.debugLineNum = 27;BA.debugLine="Dim wBuffer(2) As Byte";
_wbuffer = new byte[(int) (2)];
;
 //BA.debugLineNum = 28;BA.debugLine="Dim pin39_fotoresistor As AnalogInput";
mostCurrent._pin39_fotoresistor = new anywheresoftware.b4a.agraham.ioio.B4AIOIO.B4AAnalogueInputWrapper();
 //BA.debugLineNum = 29;BA.debugLine="Private LabelValorAnalogo As Label";
mostCurrent._labelvaloranalogo = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 30;BA.debugLine="Dim ValorAnalogoFotoR As Float";
_valoranalogofotor = 0f;
 //BA.debugLineNum = 31;BA.debugLine="End Sub";
return "";
}
public static String  _ioioclose() throws Exception{
 //BA.debugLineNum = 64;BA.debugLine="Sub IOIOClose";
 //BA.debugLineNum = 65;BA.debugLine="Try";
try { //BA.debugLineNum = 66;BA.debugLine="pin39_fotoresistor.Close";
mostCurrent._pin39_fotoresistor.Close();
 //BA.debugLineNum = 67;BA.debugLine="SPI.Close";
_spi.Close();
 //BA.debugLineNum = 68;BA.debugLine="YOYO.Disconnect";
_yoyo.Disconnect();
 //BA.debugLineNum = 69;BA.debugLine="YOYO.WaitForDisconnect";
_yoyo.WaitForDisconnect();
 } 
       catch (Exception e42) {
			processBA.setLastException(e42); //BA.debugLineNum = 71;BA.debugLine="Log(\"YOYO Close Exception: \"& LastException.Mess";
anywheresoftware.b4a.keywords.Common.Log("YOYO Close Exception: "+anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA).getMessage());
 };
 //BA.debugLineNum = 73;BA.debugLine="End Sub";
return "";
}
public static String  _ioioconnect() throws Exception{
 //BA.debugLineNum = 53;BA.debugLine="Sub IOIOConnect";
 //BA.debugLineNum = 54;BA.debugLine="Try";
try { //BA.debugLineNum = 55;BA.debugLine="YOYO.Initialize";
_yoyo.Initialize();
 //BA.debugLineNum = 56;BA.debugLine="YOYO.WaitForConnect";
_yoyo.WaitForConnect();
 //BA.debugLineNum = 57;BA.debugLine="pin39_fotoresistor= YOYO.OpenAnalogInput(39)";
mostCurrent._pin39_fotoresistor.setObject((ioio.lib.api.AnalogInput)(_yoyo.OpenAnalogInput((int) (39))));
 } 
       catch (Exception e32) {
			processBA.setLastException(e32); //BA.debugLineNum = 60;BA.debugLine="Log(\"YOYO Exception: \"& LastException.Message)";
anywheresoftware.b4a.keywords.Common.Log("YOYO Exception: "+anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA).getMessage());
 };
 //BA.debugLineNum = 62;BA.debugLine="End Sub";
return "";
}
public static String  _loopfotoresistor_tick() throws Exception{
 //BA.debugLineNum = 86;BA.debugLine="Sub LoopFotoresistor_Tick";
 //BA.debugLineNum = 87;BA.debugLine="ValorAnalogoFotoR=pin39_fotoresistor.Read";
_valoranalogofotor = mostCurrent._pin39_fotoresistor.Read();
 //BA.debugLineNum = 88;BA.debugLine="wBuffer(0)=0x00";
_wbuffer[(int) (0)] = (byte) (0x00);
 //BA.debugLineNum = 89;BA.debugLine="wBuffer(1)=NumberFormat(-128*ValorAnalogoFotoR+12";
_wbuffer[(int) (1)] = (byte)(Double.parseDouble(anywheresoftware.b4a.keywords.Common.NumberFormat(-128*_valoranalogofotor+128,(int) (1),(int) (0))));
 //BA.debugLineNum = 90;BA.debugLine="Log(NumberFormat(-128*ValorAnalogoFotoR+128,1,0))";
anywheresoftware.b4a.keywords.Common.Log(anywheresoftware.b4a.keywords.Common.NumberFormat(-128*_valoranalogofotor+128,(int) (1),(int) (0)));
 //BA.debugLineNum = 92;BA.debugLine="LabelValorAnalogo.Text = ValorAnalogoFotoR";
mostCurrent._labelvaloranalogo.setText((Object)(_valoranalogofotor));
 //BA.debugLineNum = 93;BA.debugLine="SPI.WriteRead(0,wBuffer,wBuffer.Length,2,Null,0)";
_spi.WriteRead((int) (0),_wbuffer,_wbuffer.length,(int) (2),(byte[])(anywheresoftware.b4a.keywords.Common.Null),(int) (0));
 //BA.debugLineNum = 94;BA.debugLine="End Sub";
return "";
}
public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 16;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 19;BA.debugLine="Dim YOYO As IOIO";
_yoyo = new anywheresoftware.b4a.agraham.ioio.B4AIOIO();
 //BA.debugLineNum = 20;BA.debugLine="Dim Timer_Fotoresistor As Timer";
_timer_fotoresistor = new anywheresoftware.b4a.objects.Timer();
 //BA.debugLineNum = 21;BA.debugLine="Dim SPI As SpiMaster";
_spi = new anywheresoftware.b4a.agraham.ioio.B4AIOIO.B4ASpiMaster();
 //BA.debugLineNum = 22;BA.debugLine="End Sub";
return "";
}
}