package flm.b4a.gesturedemo;

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
			processBA = new BA(this.getApplicationContext(), null, null, "flm.b4a.gesturedemo", "flm.b4a.gesturedemo.main");
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
		activityBA = new BA(this, layout, processBA, "flm.b4a.gesturedemo", "flm.b4a.gesturedemo.main");
        
        processBA.sharedProcessBA.activityBA = new java.lang.ref.WeakReference<BA>(activityBA);
        anywheresoftware.b4a.objects.ViewWrapper.lastId = 0;
        _activity = new ActivityWrapper(activityBA, "activity");
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        if (BA.isShellModeRuntimeCheck(processBA)) {
			if (isFirst)
				processBA.raiseEvent2(null, true, "SHELL", false);
			processBA.raiseEvent2(null, true, "CREATE", true, "flm.b4a.gesturedemo.main", processBA, activityBA, _activity, anywheresoftware.b4a.keywords.Common.Density, mostCurrent);
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
public flm.b4a.gesturedetector.GestureDetectorForB4A _gd = null;
public anywheresoftware.b4a.objects.PanelWrapper _pnltwofingers = null;
public anywheresoftware.b4a.objects.drawable.CanvasWrapper _c = null;
  public Object[] GetGlobals() {
		return new Object[] {"Activity",mostCurrent._activity,"C",mostCurrent._c,"GD",mostCurrent._gd,"pnlTwoFingers",mostCurrent._pnltwofingers};
}

public static boolean isAnyActivityVisible() {
    boolean vis = false;
vis = vis | (main.mostCurrent != null);
return vis;}

public static void killProgram() {
     {
            Activity __a = null;
            if (main.previousOne != null) {
				__a = main.previousOne.get();
			}
            else {
                BA ba = main.mostCurrent.processBA.sharedProcessBA.activityBA.get();
                if (ba != null) __a = ba.activity;
            }
            if (__a != null)
				__a.finish();}

}
public static String  _activity_create(boolean _firsttime) throws Exception{
try {
		Debug.PushSubsStack("Activity_Create (main) ","main",0,mostCurrent.activityBA,mostCurrent,23);
anywheresoftware.b4a.objects.LabelWrapper _lbl = null;
anywheresoftware.b4a.objects.CompoundButtonWrapper.CheckBoxWrapper _chbx = null;
Debug.locals.put("FirstTime", _firsttime);
 BA.debugLineNum = 23;BA.debugLine="Sub Activity_Create(FirstTime As Boolean)";
Debug.ShouldStop(4194304);
 BA.debugLineNum = 24;BA.debugLine="GD.SetOnGestureListener(Activity, \"Gesture\")";
Debug.ShouldStop(8388608);
mostCurrent._gd.SetOnGestureListener(processBA,(android.view.View)(mostCurrent._activity.getObject()),"Gesture");
 BA.debugLineNum = 26;BA.debugLine="Dim lbl As Label";
Debug.ShouldStop(33554432);
_lbl = new anywheresoftware.b4a.objects.LabelWrapper();Debug.locals.put("lbl", _lbl);
 BA.debugLineNum = 27;BA.debugLine="lbl.Initialize(\"\")";
Debug.ShouldStop(67108864);
_lbl.Initialize(mostCurrent.activityBA,"");
 BA.debugLineNum = 28;BA.debugLine="lbl.Text = \"Touch the screen, double-tap it, move your finger, and see the result in the Log. Try also to pinch or rotate two fingers together.\"";
Debug.ShouldStop(134217728);
_lbl.setText((Object)("Touch the screen, double-tap it, move your finger, and see the result in the Log. Try also to pinch or rotate two fingers together."));
 BA.debugLineNum = 29;BA.debugLine="lbl.TextColor = Colors.White";
Debug.ShouldStop(268435456);
_lbl.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.White);
 BA.debugLineNum = 30;BA.debugLine="lbl.TextSize = 18";
Debug.ShouldStop(536870912);
_lbl.setTextSize((float) (18));
 BA.debugLineNum = 31;BA.debugLine="Activity.AddView(lbl, 15dip, 15dip, 100%x-30dip, 100%y-30dip)";
Debug.ShouldStop(1073741824);
mostCurrent._activity.AddView((android.view.View)(_lbl.getObject()),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (15)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (15)),(int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA)-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (30))),(int) (anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (30))));
 BA.debugLineNum = 33;BA.debugLine="pnlTwoFingers.Initialize(\"\")";
Debug.ShouldStop(1);
mostCurrent._pnltwofingers.Initialize(mostCurrent.activityBA,"");
 BA.debugLineNum = 34;BA.debugLine="pnlTwoFingers.Color = Colors.Green";
Debug.ShouldStop(2);
mostCurrent._pnltwofingers.setColor(anywheresoftware.b4a.keywords.Common.Colors.Green);
 BA.debugLineNum = 35;BA.debugLine="pnlTwoFingers.Visible = False";
Debug.ShouldStop(4);
mostCurrent._pnltwofingers.setVisible(anywheresoftware.b4a.keywords.Common.False);
 BA.debugLineNum = 36;BA.debugLine="Activity.AddView(pnlTwoFingers, 25%x, 25%y, 50%x, 50%y)";
Debug.ShouldStop(8);
mostCurrent._activity.AddView((android.view.View)(mostCurrent._pnltwofingers.getObject()),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (25),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (25),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (50),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (50),mostCurrent.activityBA));
 BA.debugLineNum = 38;BA.debugLine="Dim chbx As CheckBox";
Debug.ShouldStop(32);
_chbx = new anywheresoftware.b4a.objects.CompoundButtonWrapper.CheckBoxWrapper();Debug.locals.put("chbx", _chbx);
 BA.debugLineNum = 39;BA.debugLine="chbx.Initialize(\"Chbx\")";
Debug.ShouldStop(64);
_chbx.Initialize(mostCurrent.activityBA,"Chbx");
 BA.debugLineNum = 40;BA.debugLine="chbx.Checked = False";
Debug.ShouldStop(128);
_chbx.setChecked(anywheresoftware.b4a.keywords.Common.False);
 BA.debugLineNum = 41;BA.debugLine="chbx.Text = \"Disable long press to improve scrolling detection\"";
Debug.ShouldStop(256);
_chbx.setText((Object)("Disable long press to improve scrolling detection"));
 BA.debugLineNum = 42;BA.debugLine="chbx.TextColor = Colors.White";
Debug.ShouldStop(512);
_chbx.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.White);
 BA.debugLineNum = 43;BA.debugLine="chbx.TextSize = 18";
Debug.ShouldStop(1024);
_chbx.setTextSize((float) (18));
 BA.debugLineNum = 44;BA.debugLine="Activity.AddView(chbx, 15dip, 100%y - 60dip, 100%x-30dip, 45dip)";
Debug.ShouldStop(2048);
mostCurrent._activity.AddView((android.view.View)(_chbx.getObject()),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (15)),(int) (anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (60))),(int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA)-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (30))),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (45)));
 BA.debugLineNum = 45;BA.debugLine="End Sub";
Debug.ShouldStop(4096);
return "";
}
catch (Exception e) {
			Debug.ErrorCaught(e);
			throw e;
		} 
finally {
			Debug.PopSubsStack();
		}}
public static String  _activity_pause(boolean _userclosed) throws Exception{
try {
		Debug.PushSubsStack("Activity_Pause (main) ","main",0,mostCurrent.activityBA,mostCurrent,50);
Debug.locals.put("UserClosed", _userclosed);
 BA.debugLineNum = 50;BA.debugLine="Sub Activity_Pause (UserClosed As Boolean)";
Debug.ShouldStop(131072);
 BA.debugLineNum = 51;BA.debugLine="End Sub";
Debug.ShouldStop(262144);
return "";
}
catch (Exception e) {
			Debug.ErrorCaught(e);
			throw e;
		} 
finally {
			Debug.PopSubsStack();
		}}
public static String  _activity_resume() throws Exception{
try {
		Debug.PushSubsStack("Activity_Resume (main) ","main",0,mostCurrent.activityBA,mostCurrent,47);
 BA.debugLineNum = 47;BA.debugLine="Sub Activity_Resume";
Debug.ShouldStop(16384);
 BA.debugLineNum = 48;BA.debugLine="End Sub";
Debug.ShouldStop(32768);
return "";
}
catch (Exception e) {
			Debug.ErrorCaught(e);
			throw e;
		} 
finally {
			Debug.PopSubsStack();
		}}
public static String  _chbx_checkedchange(boolean _checked) throws Exception{
try {
		Debug.PushSubsStack("Chbx_CheckedChange (main) ","main",0,mostCurrent.activityBA,mostCurrent,132);
Debug.locals.put("Checked", _checked);
 BA.debugLineNum = 132;BA.debugLine="Sub Chbx_CheckedChange(Checked As Boolean)";
Debug.ShouldStop(8);
 BA.debugLineNum = 133;BA.debugLine="GD.EnableLongPress(Not(Checked))";
Debug.ShouldStop(16);
mostCurrent._gd.EnableLongPress(anywheresoftware.b4a.keywords.Common.Not(_checked));
 BA.debugLineNum = 134;BA.debugLine="End Sub";
Debug.ShouldStop(32);
return "";
}
catch (Exception e) {
			Debug.ErrorCaught(e);
			throw e;
		} 
finally {
			Debug.PopSubsStack();
		}}
public static String  _gesture_ondoubletap(float _x,float _y,Object _motionevent) throws Exception{
try {
		Debug.PushSubsStack("Gesture_onDoubleTap (main) ","main",0,mostCurrent.activityBA,mostCurrent,111);
Debug.locals.put("X", _x);
Debug.locals.put("Y", _y);
Debug.locals.put("MotionEvent", _motionevent);
 BA.debugLineNum = 111;BA.debugLine="Sub Gesture_onDoubleTap(X As Float, Y As Float, MotionEvent As Object)";
Debug.ShouldStop(16384);
 BA.debugLineNum = 112;BA.debugLine="Log(\"   onDoubleTap x = \" & X & \", y = \" & Y & \", ev = \" & MotionEvent)";
Debug.ShouldStop(32768);
anywheresoftware.b4a.keywords.Common.Log("   onDoubleTap x = "+BA.NumberToString(_x)+", y = "+BA.NumberToString(_y)+", ev = "+BA.ObjectToString(_motionevent));
 BA.debugLineNum = 113;BA.debugLine="ToastMessageShow(\"Double-Tap\", False)";
Debug.ShouldStop(65536);
anywheresoftware.b4a.keywords.Common.ToastMessageShow("Double-Tap",anywheresoftware.b4a.keywords.Common.False);
 BA.debugLineNum = 114;BA.debugLine="End Sub";
Debug.ShouldStop(131072);
return "";
}
catch (Exception e) {
			Debug.ErrorCaught(e);
			throw e;
		} 
finally {
			Debug.PopSubsStack();
		}}
public static String  _gesture_ondown(float _x,float _y,Object _motionevent) throws Exception{
try {
		Debug.PushSubsStack("Gesture_onDown (main) ","main",0,mostCurrent.activityBA,mostCurrent,75);
Debug.locals.put("X", _x);
Debug.locals.put("Y", _y);
Debug.locals.put("MotionEvent", _motionevent);
 BA.debugLineNum = 75;BA.debugLine="Sub Gesture_onDown(X As Float, Y As Float, MotionEvent As Object)";
Debug.ShouldStop(1024);
 BA.debugLineNum = 76;BA.debugLine="Log(\"   onDown x = \" & X & \", y = \" & Y & \", ev = \" & MotionEvent)";
Debug.ShouldStop(2048);
anywheresoftware.b4a.keywords.Common.Log("   onDown x = "+BA.NumberToString(_x)+", y = "+BA.NumberToString(_y)+", ev = "+BA.ObjectToString(_motionevent));
 BA.debugLineNum = 77;BA.debugLine="Log(\"      pressure = \" & GD.getPressure(MotionEvent, 0))";
Debug.ShouldStop(4096);
anywheresoftware.b4a.keywords.Common.Log("      pressure = "+BA.NumberToString(mostCurrent._gd.getPressure((android.view.MotionEvent)(_motionevent),(int) (0))));
 BA.debugLineNum = 78;BA.debugLine="Log(\"      size = \" & GD.getSize(MotionEvent, 0))";
Debug.ShouldStop(8192);
anywheresoftware.b4a.keywords.Common.Log("      size = "+BA.NumberToString(mostCurrent._gd.getSize((android.view.MotionEvent)(_motionevent),(int) (0))));
 BA.debugLineNum = 79;BA.debugLine="End Sub";
Debug.ShouldStop(16384);
return "";
}
catch (Exception e) {
			Debug.ErrorCaught(e);
			throw e;
		} 
finally {
			Debug.PopSubsStack();
		}}
public static String  _gesture_onfling(float _velocityx,float _velocityy,Object _motionevent1,Object _motionevent2) throws Exception{
try {
		Debug.PushSubsStack("Gesture_onFling (main) ","main",0,mostCurrent.activityBA,mostCurrent,127);
Debug.locals.put("velocityX", _velocityx);
Debug.locals.put("velocityY", _velocityy);
Debug.locals.put("MotionEvent1", _motionevent1);
Debug.locals.put("MotionEvent2", _motionevent2);
 BA.debugLineNum = 127;BA.debugLine="Sub Gesture_onFling(velocityX As Float, velocityY As Float, MotionEvent1 As Object, MotionEvent2 As Object)";
Debug.ShouldStop(1073741824);
 BA.debugLineNum = 128;BA.debugLine="Log(\"   onFling velocityX = \" & velocityX & \", velocityY = \" & velocityY & \", ev1 = \" & MotionEvent1 & \", ev2 = \" & MotionEvent2)";
Debug.ShouldStop(-2147483648);
anywheresoftware.b4a.keywords.Common.Log("   onFling velocityX = "+BA.NumberToString(_velocityx)+", velocityY = "+BA.NumberToString(_velocityy)+", ev1 = "+BA.ObjectToString(_motionevent1)+", ev2 = "+BA.ObjectToString(_motionevent2));
 BA.debugLineNum = 129;BA.debugLine="ToastMessageShow(\"Fling\", False)";
Debug.ShouldStop(1);
anywheresoftware.b4a.keywords.Common.ToastMessageShow("Fling",anywheresoftware.b4a.keywords.Common.False);
 BA.debugLineNum = 130;BA.debugLine="End Sub";
Debug.ShouldStop(2);
return "";
}
catch (Exception e) {
			Debug.ErrorCaught(e);
			throw e;
		} 
finally {
			Debug.PopSubsStack();
		}}
public static String  _gesture_onlongpress(float _x,float _y,Object _motionevent) throws Exception{
try {
		Debug.PushSubsStack("Gesture_onLongPress (main) ","main",0,mostCurrent.activityBA,mostCurrent,118);
Debug.locals.put("X", _x);
Debug.locals.put("Y", _y);
Debug.locals.put("MotionEvent", _motionevent);
 BA.debugLineNum = 118;BA.debugLine="Sub Gesture_onLongPress(X As Float, Y As Float, MotionEvent As Object)";
Debug.ShouldStop(2097152);
 BA.debugLineNum = 119;BA.debugLine="Log(\"   onLongPress x=\" & X & \", y=\" & Y & \", ev=\" & MotionEvent)";
Debug.ShouldStop(4194304);
anywheresoftware.b4a.keywords.Common.Log("   onLongPress x="+BA.NumberToString(_x)+", y="+BA.NumberToString(_y)+", ev="+BA.ObjectToString(_motionevent));
 BA.debugLineNum = 120;BA.debugLine="ToastMessageShow(\"Long Press\", False)";
Debug.ShouldStop(8388608);
anywheresoftware.b4a.keywords.Common.ToastMessageShow("Long Press",anywheresoftware.b4a.keywords.Common.False);
 BA.debugLineNum = 121;BA.debugLine="End Sub";
Debug.ShouldStop(16777216);
return "";
}
catch (Exception e) {
			Debug.ErrorCaught(e);
			throw e;
		} 
finally {
			Debug.PopSubsStack();
		}}
public static String  _gesture_onpinchclose(float _newdistance,float _previousdistance,Object _motionevent) throws Exception{
try {
		Debug.PushSubsStack("Gesture_onPinchClose (main) ","main",0,mostCurrent.activityBA,mostCurrent,93);
Debug.locals.put("NewDistance", _newdistance);
Debug.locals.put("PreviousDistance", _previousdistance);
Debug.locals.put("MotionEvent", _motionevent);
 BA.debugLineNum = 93;BA.debugLine="Sub Gesture_onPinchClose(NewDistance As Float, PreviousDistance As Float, MotionEvent As Object)";
Debug.ShouldStop(268435456);
 BA.debugLineNum = 94;BA.debugLine="Log(\"   onPinchClose dist=\" & NewDistance & \", prevdist=\" & PreviousDistance & \", ev=\" & MotionEvent)";
Debug.ShouldStop(536870912);
anywheresoftware.b4a.keywords.Common.Log("   onPinchClose dist="+BA.NumberToString(_newdistance)+", prevdist="+BA.NumberToString(_previousdistance)+", ev="+BA.ObjectToString(_motionevent));
 BA.debugLineNum = 95;BA.debugLine="ResizePanel(MotionEvent, Colors.Red)";
Debug.ShouldStop(1073741824);
_resizepanel(_motionevent,anywheresoftware.b4a.keywords.Common.Colors.Red);
 BA.debugLineNum = 96;BA.debugLine="End Sub";
Debug.ShouldStop(-2147483648);
return "";
}
catch (Exception e) {
			Debug.ErrorCaught(e);
			throw e;
		} 
finally {
			Debug.PopSubsStack();
		}}
public static String  _gesture_onpinchopen(float _newdistance,float _previousdistance,Object _motionevent) throws Exception{
try {
		Debug.PushSubsStack("Gesture_onPinchOpen (main) ","main",0,mostCurrent.activityBA,mostCurrent,89);
Debug.locals.put("NewDistance", _newdistance);
Debug.locals.put("PreviousDistance", _previousdistance);
Debug.locals.put("MotionEvent", _motionevent);
 BA.debugLineNum = 89;BA.debugLine="Sub Gesture_onPinchOpen(NewDistance As Float, PreviousDistance As Float, MotionEvent As Object)";
Debug.ShouldStop(16777216);
 BA.debugLineNum = 90;BA.debugLine="Log(\"   onPinchOpen dist=\" & NewDistance & \", prevdist=\" & PreviousDistance & \", ev=\" & MotionEvent)";
Debug.ShouldStop(33554432);
anywheresoftware.b4a.keywords.Common.Log("   onPinchOpen dist="+BA.NumberToString(_newdistance)+", prevdist="+BA.NumberToString(_previousdistance)+", ev="+BA.ObjectToString(_motionevent));
 BA.debugLineNum = 91;BA.debugLine="ResizePanel(MotionEvent, Colors.Green)";
Debug.ShouldStop(67108864);
_resizepanel(_motionevent,anywheresoftware.b4a.keywords.Common.Colors.Green);
 BA.debugLineNum = 92;BA.debugLine="End Sub";
Debug.ShouldStop(134217728);
return "";
}
catch (Exception e) {
			Debug.ErrorCaught(e);
			throw e;
		} 
finally {
			Debug.PopSubsStack();
		}}
public static String  _gesture_onpointerdown(int _ptrindex,int _pid,Object _motionevent) throws Exception{
try {
		Debug.PushSubsStack("Gesture_onPointerDown (main) ","main",0,mostCurrent.activityBA,mostCurrent,80);
Debug.locals.put("ptrIndex", _ptrindex);
Debug.locals.put("PID", _pid);
Debug.locals.put("MotionEvent", _motionevent);
 BA.debugLineNum = 80;BA.debugLine="Sub Gesture_onPointerDown(ptrIndex As Int, PID As Int, MotionEvent As Object)";
Debug.ShouldStop(32768);
 BA.debugLineNum = 81;BA.debugLine="Log(\"onPointerDown ptrindex=\" & ptrIndex & \", pid=\" & PID & \", ev=\" & MotionEvent)";
Debug.ShouldStop(65536);
anywheresoftware.b4a.keywords.Common.Log("onPointerDown ptrindex="+BA.NumberToString(_ptrindex)+", pid="+BA.NumberToString(_pid)+", ev="+BA.ObjectToString(_motionevent));
 BA.debugLineNum = 82;BA.debugLine="ResizePanel(MotionEvent, Colors.White)";
Debug.ShouldStop(131072);
_resizepanel(_motionevent,anywheresoftware.b4a.keywords.Common.Colors.White);
 BA.debugLineNum = 83;BA.debugLine="pnlTwoFingers.Visible = True";
Debug.ShouldStop(262144);
mostCurrent._pnltwofingers.setVisible(anywheresoftware.b4a.keywords.Common.True);
 BA.debugLineNum = 84;BA.debugLine="End Sub";
Debug.ShouldStop(524288);
return "";
}
catch (Exception e) {
			Debug.ErrorCaught(e);
			throw e;
		} 
finally {
			Debug.PopSubsStack();
		}}
public static String  _gesture_onpointerup(int _ptrindex,int _pid,Object _motionevent) throws Exception{
try {
		Debug.PushSubsStack("Gesture_onPointerUp (main) ","main",0,mostCurrent.activityBA,mostCurrent,85);
Debug.locals.put("ptrIndex", _ptrindex);
Debug.locals.put("PID", _pid);
Debug.locals.put("MotionEvent", _motionevent);
 BA.debugLineNum = 85;BA.debugLine="Sub Gesture_onPointerUp(ptrIndex As Int, PID As Int, MotionEvent As Object)";
Debug.ShouldStop(1048576);
 BA.debugLineNum = 86;BA.debugLine="Log(\"onPointerUp ptrindex=\" & ptrIndex & \", pid=\" & PID & \", ev=\" & MotionEvent)";
Debug.ShouldStop(2097152);
anywheresoftware.b4a.keywords.Common.Log("onPointerUp ptrindex="+BA.NumberToString(_ptrindex)+", pid="+BA.NumberToString(_pid)+", ev="+BA.ObjectToString(_motionevent));
 BA.debugLineNum = 87;BA.debugLine="pnlTwoFingers.Visible = False";
Debug.ShouldStop(4194304);
mostCurrent._pnltwofingers.setVisible(anywheresoftware.b4a.keywords.Common.False);
 BA.debugLineNum = 88;BA.debugLine="End Sub";
Debug.ShouldStop(8388608);
return "";
}
catch (Exception e) {
			Debug.ErrorCaught(e);
			throw e;
		} 
finally {
			Debug.PopSubsStack();
		}}
public static String  _gesture_onrotation(double _degrees,Object _motionevent) throws Exception{
try {
		Debug.PushSubsStack("Gesture_onRotation (main) ","main",0,mostCurrent.activityBA,mostCurrent,97);
Debug.locals.put("Degrees", _degrees);
Debug.locals.put("MotionEvent", _motionevent);
 BA.debugLineNum = 97;BA.debugLine="Sub Gesture_onRotation(Degrees As Double, MotionEvent As Object)";
Debug.ShouldStop(1);
 BA.debugLineNum = 98;BA.debugLine="Log(\"   onRotation degrees=\" & Degrees & \", ev=\" & MotionEvent)";
Debug.ShouldStop(2);
anywheresoftware.b4a.keywords.Common.Log("   onRotation degrees="+BA.NumberToString(_degrees)+", ev="+BA.ObjectToString(_motionevent));
 BA.debugLineNum = 99;BA.debugLine="If pnlTwoFingers.Width > 0 AND pnlTwoFingers.Height > 0 Then";
Debug.ShouldStop(4);
if (mostCurrent._pnltwofingers.getWidth()>0 && mostCurrent._pnltwofingers.getHeight()>0) { 
 BA.debugLineNum = 100;BA.debugLine="C.Initialize(pnlTwoFingers)";
Debug.ShouldStop(8);
mostCurrent._c.Initialize((android.view.View)(mostCurrent._pnltwofingers.getObject()));
 BA.debugLineNum = 101;BA.debugLine="C.DrawTextRotated(\"Angle=\" & Round(Degrees) & \"°\", pnlTwoFingers.Width / 2, pnlTwoFingers.Height / 2, Typeface.DEFAULT_BOLD, 16, Colors.Black, \"CENTER\", Degrees)";
Debug.ShouldStop(16);
mostCurrent._c.DrawTextRotated(mostCurrent.activityBA,"Angle="+BA.NumberToString(anywheresoftware.b4a.keywords.Common.Round(_degrees))+"°",(float) (mostCurrent._pnltwofingers.getWidth()/(double)2),(float) (mostCurrent._pnltwofingers.getHeight()/(double)2),anywheresoftware.b4a.keywords.Common.Typeface.DEFAULT_BOLD,(float) (16),anywheresoftware.b4a.keywords.Common.Colors.Black,BA.getEnumFromString(android.graphics.Paint.Align.class,"CENTER"),(float) (_degrees));
 };
 BA.debugLineNum = 103;BA.debugLine="End Sub";
Debug.ShouldStop(64);
return "";
}
catch (Exception e) {
			Debug.ErrorCaught(e);
			throw e;
		} 
finally {
			Debug.PopSubsStack();
		}}
public static String  _gesture_onscroll(float _distancex,float _distancey,Object _motionevent1,Object _motionevent2) throws Exception{
try {
		Debug.PushSubsStack("Gesture_onScroll (main) ","main",0,mostCurrent.activityBA,mostCurrent,122);
Debug.locals.put("distanceX", _distancex);
Debug.locals.put("distanceY", _distancey);
Debug.locals.put("MotionEvent1", _motionevent1);
Debug.locals.put("MotionEvent2", _motionevent2);
 BA.debugLineNum = 122;BA.debugLine="Sub Gesture_onScroll(distanceX As Float, distanceY As Float, MotionEvent1 As Object, MotionEvent2 As Object)";
Debug.ShouldStop(33554432);
 BA.debugLineNum = 123;BA.debugLine="Log(\"   onScroll distanceX = \" & distanceX & \", distanceY = \" & distanceY & \", ev1 = \" & MotionEvent1 & \", ev2=\" & MotionEvent2)";
Debug.ShouldStop(67108864);
anywheresoftware.b4a.keywords.Common.Log("   onScroll distanceX = "+BA.NumberToString(_distancex)+", distanceY = "+BA.NumberToString(_distancey)+", ev1 = "+BA.ObjectToString(_motionevent1)+", ev2="+BA.ObjectToString(_motionevent2));
 BA.debugLineNum = 124;BA.debugLine="Log(\"      X1, Y1 = \" & GD.getX(MotionEvent1, 0) & \", \" & GD.getY(MotionEvent1, 0))";
Debug.ShouldStop(134217728);
anywheresoftware.b4a.keywords.Common.Log("      X1, Y1 = "+BA.NumberToString(mostCurrent._gd.getX((android.view.MotionEvent)(_motionevent1),(int) (0)))+", "+BA.NumberToString(mostCurrent._gd.getY((android.view.MotionEvent)(_motionevent1),(int) (0))));
 BA.debugLineNum = 125;BA.debugLine="Log(\"      X2, Y2 = \" & GD.getX(MotionEvent2, 0) & \", \" & GD.getY(MotionEvent2, 0))";
Debug.ShouldStop(268435456);
anywheresoftware.b4a.keywords.Common.Log("      X2, Y2 = "+BA.NumberToString(mostCurrent._gd.getX((android.view.MotionEvent)(_motionevent2),(int) (0)))+", "+BA.NumberToString(mostCurrent._gd.getY((android.view.MotionEvent)(_motionevent2),(int) (0))));
 BA.debugLineNum = 126;BA.debugLine="End Sub";
Debug.ShouldStop(536870912);
return "";
}
catch (Exception e) {
			Debug.ErrorCaught(e);
			throw e;
		} 
finally {
			Debug.PopSubsStack();
		}}
public static String  _gesture_onshowpress(float _x,float _y,Object _motionevent) throws Exception{
try {
		Debug.PushSubsStack("Gesture_onShowPress (main) ","main",0,mostCurrent.activityBA,mostCurrent,115);
Debug.locals.put("X", _x);
Debug.locals.put("Y", _y);
Debug.locals.put("MotionEvent", _motionevent);
 BA.debugLineNum = 115;BA.debugLine="Sub Gesture_onShowPress(X As Float, Y As Float, MotionEvent As Object)";
Debug.ShouldStop(262144);
 BA.debugLineNum = 116;BA.debugLine="Log(\"   onShowPress x=\" & X & \", y=\" & Y & \", ev=\" & MotionEvent)";
Debug.ShouldStop(524288);
anywheresoftware.b4a.keywords.Common.Log("   onShowPress x="+BA.NumberToString(_x)+", y="+BA.NumberToString(_y)+", ev="+BA.ObjectToString(_motionevent));
 BA.debugLineNum = 117;BA.debugLine="End Sub";
Debug.ShouldStop(1048576);
return "";
}
catch (Exception e) {
			Debug.ErrorCaught(e);
			throw e;
		} 
finally {
			Debug.PopSubsStack();
		}}
public static String  _gesture_onsingletapconfirmed(float _x,float _y,Object _motionevent) throws Exception{
try {
		Debug.PushSubsStack("Gesture_onSingleTapConfirmed (main) ","main",0,mostCurrent.activityBA,mostCurrent,107);
Debug.locals.put("X", _x);
Debug.locals.put("Y", _y);
Debug.locals.put("MotionEvent", _motionevent);
 BA.debugLineNum = 107;BA.debugLine="Sub Gesture_onSingleTapConfirmed(X As Float, Y As Float, MotionEvent As Object)";
Debug.ShouldStop(1024);
 BA.debugLineNum = 108;BA.debugLine="Log(\"   onSingleTapConfirmed x = \" & X & \", y = \" & Y & \", ev = \" & MotionEvent)";
Debug.ShouldStop(2048);
anywheresoftware.b4a.keywords.Common.Log("   onSingleTapConfirmed x = "+BA.NumberToString(_x)+", y = "+BA.NumberToString(_y)+", ev = "+BA.ObjectToString(_motionevent));
 BA.debugLineNum = 109;BA.debugLine="ToastMessageShow(\"Single-Tap\", False)";
Debug.ShouldStop(4096);
anywheresoftware.b4a.keywords.Common.ToastMessageShow("Single-Tap",anywheresoftware.b4a.keywords.Common.False);
 BA.debugLineNum = 110;BA.debugLine="End Sub";
Debug.ShouldStop(8192);
return "";
}
catch (Exception e) {
			Debug.ErrorCaught(e);
			throw e;
		} 
finally {
			Debug.PopSubsStack();
		}}
public static String  _gesture_onsingletapup(float _x,float _y,Object _motionevent) throws Exception{
try {
		Debug.PushSubsStack("Gesture_onSingleTapUp (main) ","main",0,mostCurrent.activityBA,mostCurrent,104);
Debug.locals.put("X", _x);
Debug.locals.put("Y", _y);
Debug.locals.put("MotionEvent", _motionevent);
 BA.debugLineNum = 104;BA.debugLine="Sub Gesture_onSingleTapUp(X As Float, Y As Float, MotionEvent As Object)";
Debug.ShouldStop(128);
 BA.debugLineNum = 105;BA.debugLine="Log(\"   onSingleTapUp x = \" & X & \", y = \" & Y & \", ev = \" & MotionEvent)";
Debug.ShouldStop(256);
anywheresoftware.b4a.keywords.Common.Log("   onSingleTapUp x = "+BA.NumberToString(_x)+", y = "+BA.NumberToString(_y)+", ev = "+BA.ObjectToString(_motionevent));
 BA.debugLineNum = 106;BA.debugLine="End Sub";
Debug.ShouldStop(512);
return "";
}
catch (Exception e) {
			Debug.ErrorCaught(e);
			throw e;
		} 
finally {
			Debug.PopSubsStack();
		}}
public static boolean  _gesture_ontouch(int _action,float _x,float _y,Object _motionevent) throws Exception{
try {
		Debug.PushSubsStack("Gesture_onTouch (main) ","main",0,mostCurrent.activityBA,mostCurrent,71);
Debug.locals.put("Action", _action);
Debug.locals.put("X", _x);
Debug.locals.put("Y", _y);
Debug.locals.put("MotionEvent", _motionevent);
 BA.debugLineNum = 71;BA.debugLine="Sub Gesture_onTouch(Action As Int, X As Float, Y As Float, MotionEvent As Object) As Boolean";
Debug.ShouldStop(64);
 BA.debugLineNum = 72;BA.debugLine="Log(\"onTouch action=\" & Action & \", x=\" & X & \", y=\" & Y & \", ev=\" & MotionEvent)";
Debug.ShouldStop(128);
anywheresoftware.b4a.keywords.Common.Log("onTouch action="+BA.NumberToString(_action)+", x="+BA.NumberToString(_x)+", y="+BA.NumberToString(_y)+", ev="+BA.ObjectToString(_motionevent));
 BA.debugLineNum = 73;BA.debugLine="Return True 'True = Handle this touch event, False = Ignore it";
Debug.ShouldStop(256);
if (true) return anywheresoftware.b4a.keywords.Common.True;
 BA.debugLineNum = 74;BA.debugLine="End Sub";
Debug.ShouldStop(512);
return false;
}
catch (Exception e) {
			Debug.ErrorCaught(e);
			throw e;
		} 
finally {
			Debug.PopSubsStack();
		}}

public static void initializeProcessGlobals() {
    if (mostCurrent != null && mostCurrent.activityBA != null) {
Debug.StartDebugging(mostCurrent.activityBA, 19985, new int[] {5}, "cf2a997b-c1de-4c5f-a7fa-ee968ba416fd");}

    if (main.processGlobalsRun == false) {
	    main.processGlobalsRun = true;
		try {
		        main._process_globals();
		
        } catch (Exception e) {
			throw new RuntimeException(e);
		}
    }
}public static String  _globals() throws Exception{
 //BA.debugLineNum = 17;BA.debugLine="Sub Globals";
 //BA.debugLineNum = 18;BA.debugLine="Dim GD As GestureDetector";
mostCurrent._gd = new flm.b4a.gesturedetector.GestureDetectorForB4A();
 //BA.debugLineNum = 19;BA.debugLine="Dim pnlTwoFingers As Panel";
mostCurrent._pnltwofingers = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 20;BA.debugLine="Dim C As Canvas";
mostCurrent._c = new anywheresoftware.b4a.objects.drawable.CanvasWrapper();
 //BA.debugLineNum = 21;BA.debugLine="End Sub";
return "";
}
public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 12;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 15;BA.debugLine="End Sub";
return "";
}
public static String  _resizepanel(Object _motionevent,int _color) throws Exception{
try {
		Debug.PushSubsStack("ResizePanel (main) ","main",0,mostCurrent.activityBA,mostCurrent,53);
int _left = 0;
int _top = 0;
int _width = 0;
int _height = 0;
Debug.locals.put("MotionEvent", _motionevent);
Debug.locals.put("Color", _color);
 BA.debugLineNum = 53;BA.debugLine="Sub ResizePanel(MotionEvent As Object, Color As Int)";
Debug.ShouldStop(1048576);
 BA.debugLineNum = 54;BA.debugLine="Dim Left, Top, Width, Height As Int";
Debug.ShouldStop(2097152);
_left = 0;Debug.locals.put("Left", _left);
_top = 0;Debug.locals.put("Top", _top);
_width = 0;Debug.locals.put("Width", _width);
_height = 0;Debug.locals.put("Height", _height);
 BA.debugLineNum = 55;BA.debugLine="If GD.getX(MotionEvent, 0) > GD.getX(MotionEvent, 1) Then";
Debug.ShouldStop(4194304);
if (mostCurrent._gd.getX((android.view.MotionEvent)(_motionevent),(int) (0))>mostCurrent._gd.getX((android.view.MotionEvent)(_motionevent),(int) (1))) { 
 BA.debugLineNum = 56;BA.debugLine="Left = GD.getX(MotionEvent, 1)";
Debug.ShouldStop(8388608);
_left = (int) (mostCurrent._gd.getX((android.view.MotionEvent)(_motionevent),(int) (1)));Debug.locals.put("Left", _left);
 }else {
 BA.debugLineNum = 58;BA.debugLine="Left = GD.getX(MotionEvent, 0)";
Debug.ShouldStop(33554432);
_left = (int) (mostCurrent._gd.getX((android.view.MotionEvent)(_motionevent),(int) (0)));Debug.locals.put("Left", _left);
 };
 BA.debugLineNum = 60;BA.debugLine="If GD.getY(MotionEvent, 0) > GD.getY(MotionEvent, 1) Then";
Debug.ShouldStop(134217728);
if (mostCurrent._gd.getY((android.view.MotionEvent)(_motionevent),(int) (0))>mostCurrent._gd.getY((android.view.MotionEvent)(_motionevent),(int) (1))) { 
 BA.debugLineNum = 61;BA.debugLine="Top = GD.getY(MotionEvent, 1)";
Debug.ShouldStop(268435456);
_top = (int) (mostCurrent._gd.getY((android.view.MotionEvent)(_motionevent),(int) (1)));Debug.locals.put("Top", _top);
 }else {
 BA.debugLineNum = 63;BA.debugLine="Top = GD.getY(MotionEvent, 0)";
Debug.ShouldStop(1073741824);
_top = (int) (mostCurrent._gd.getY((android.view.MotionEvent)(_motionevent),(int) (0)));Debug.locals.put("Top", _top);
 };
 BA.debugLineNum = 65;BA.debugLine="Width = Abs(GD.getX(MotionEvent, 0) - GD.getX(MotionEvent, 1))";
Debug.ShouldStop(1);
_width = (int) (anywheresoftware.b4a.keywords.Common.Abs(mostCurrent._gd.getX((android.view.MotionEvent)(_motionevent),(int) (0))-mostCurrent._gd.getX((android.view.MotionEvent)(_motionevent),(int) (1))));Debug.locals.put("Width", _width);
 BA.debugLineNum = 66;BA.debugLine="Height = Abs(GD.getY(MotionEvent, 0) - GD.getY(MotionEvent, 1))";
Debug.ShouldStop(2);
_height = (int) (anywheresoftware.b4a.keywords.Common.Abs(mostCurrent._gd.getY((android.view.MotionEvent)(_motionevent),(int) (0))-mostCurrent._gd.getY((android.view.MotionEvent)(_motionevent),(int) (1))));Debug.locals.put("Height", _height);
 BA.debugLineNum = 67;BA.debugLine="pnlTwoFingers.SetLayout(Left, Top, Width, Height)";
Debug.ShouldStop(4);
mostCurrent._pnltwofingers.SetLayout(_left,_top,_width,_height);
 BA.debugLineNum = 68;BA.debugLine="pnlTwoFingers.Color = Color";
Debug.ShouldStop(8);
mostCurrent._pnltwofingers.setColor(_color);
 BA.debugLineNum = 69;BA.debugLine="End Sub";
Debug.ShouldStop(16);
return "";
}
catch (Exception e) {
			Debug.ErrorCaught(e);
			throw e;
		} 
finally {
			Debug.PopSubsStack();
		}}
}
