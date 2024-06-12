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

public class frmlevels extends Activity implements B4AActivity{
	public static frmlevels mostCurrent;
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
    public static boolean dontPause;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        mostCurrent = this;
		if (processBA == null) {
			processBA = new BA(this.getApplicationContext(), null, null, "b4a.example", "b4a.example.frmlevels");
			processBA.loadHtSubs(this.getClass());
	        float deviceScale = getApplicationContext().getResources().getDisplayMetrics().density;
	        BALayout.setDeviceScale(deviceScale);
            
		}
		else if (previousOne != null) {
			Activity p = previousOne.get();
			if (p != null && p != this) {
                BA.LogInfo("Killing previous instance (frmlevels).");
				p.finish();
			}
		}
        processBA.setActivityPaused(true);
        processBA.runHook("oncreate", this, null);
		if (!includeTitle) {
        	this.getWindow().requestFeature(android.view.Window.FEATURE_NO_TITLE);
        }
        if (fullScreen) {
        	getWindow().setFlags(android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN,   
        			android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
		
        processBA.sharedProcessBA.activityBA = null;
		layout = new BALayout(this);
		setContentView(layout);
		afterFirstLayout = false;
        WaitForLayout wl = new WaitForLayout();
        if (anywheresoftware.b4a.objects.ServiceHelper.StarterHelper.startFromActivity(this, processBA, wl, false))
		    BA.handler.postDelayed(wl, 5);

	}
	static class WaitForLayout implements Runnable {
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
		activityBA = new BA(this, layout, processBA, "b4a.example", "b4a.example.frmlevels");
        
        processBA.sharedProcessBA.activityBA = new java.lang.ref.WeakReference<BA>(activityBA);
        anywheresoftware.b4a.objects.ViewWrapper.lastId = 0;
        _activity = new ActivityWrapper(activityBA, "activity");
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        if (BA.isShellModeRuntimeCheck(processBA)) {
			if (isFirst)
				processBA.raiseEvent2(null, true, "SHELL", false);
			processBA.raiseEvent2(null, true, "CREATE", true, "b4a.example.frmlevels", processBA, activityBA, _activity, anywheresoftware.b4a.keywords.Common.Density, mostCurrent);
			_activity.reinitializeForShell(activityBA, "activity");
		}
        initializeProcessGlobals();		
        initializeGlobals();
        
        BA.LogInfo("** Activity (frmlevels) Create " + (isFirst ? "(first time)" : "") + " **");
        processBA.raiseEvent2(null, true, "activity_create", false, isFirst);
		isFirst = false;
		if (this != mostCurrent)
			return;
        processBA.setActivityPaused(false);
        BA.LogInfo("** Activity (frmlevels) Resume **");
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
			processBA.raiseEventFromUI(item.getTitle(), eventName + "_click");
			return true;
		}
	}
    public static Class<?> getObject() {
		return frmlevels.class;
	}
    private Boolean onKeySubExist = null;
    private Boolean onKeyUpSubExist = null;
	@Override
	public boolean onKeyDown(int keyCode, android.view.KeyEvent event) {
        if (processBA.runHook("onkeydown", this, new Object[] {keyCode, event}))
            return true;
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
        if (processBA.runHook("onkeyup", this, new Object[] {keyCode, event}))
            return true;
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
        if (_activity == null)
            return;
        if (this != mostCurrent)
			return;
		anywheresoftware.b4a.Msgbox.dismiss(true);
        if (!dontPause)
            BA.LogInfo("** Activity (frmlevels) Pause, UserClosed = " + activityBA.activity.isFinishing() + " **");
        else
            BA.LogInfo("** Activity (frmlevels) Pause event (activity is not paused). **");
        if (mostCurrent != null)
            processBA.raiseEvent2(_activity, true, "activity_pause", false, activityBA.activity.isFinishing());		
        if (!dontPause) {
            processBA.setActivityPaused(true);
            mostCurrent = null;
        }

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
            frmlevels mc = mostCurrent;
			if (mc == null || mc != activity.get())
				return;
			processBA.setActivityPaused(false);
            BA.LogInfo("** Activity (frmlevels) Resume **");
            if (mc != mostCurrent)
                return;
		    processBA.raiseEvent(mc._activity, "activity_resume", (Object[])null);
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
    public void onRequestPermissionsResult(int requestCode,
        String permissions[], int[] grantResults) {
        for (int i = 0;i < permissions.length;i++) {
            Object[] o = new Object[] {permissions[i], grantResults[i] == 0};
            processBA.raiseEventFromDifferentThread(null,null, 0, "activity_permissionresult", true, o);
        }
            
    }

public anywheresoftware.b4a.keywords.Common __c = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblselect = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnlvl1 = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imglvl2locked = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imglvl3locked = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnlvl2 = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnlvl3 = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnback = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblselectshadow = null;
public b4a.example.dateutils _dateutils = null;
public b4a.example.main _main = null;
public b4a.example.starter _starter = null;
public b4a.example.functions _functions = null;
public b4a.example.frmtitlescreen _frmtitlescreen = null;
public b4a.example.frmregister _frmregister = null;
public b4a.example.frmlogin _frmlogin = null;
public b4a.example.frmoptions _frmoptions = null;
public b4a.example.frmabout _frmabout = null;
public b4a.example.frmleadership _frmleadership = null;
public b4a.example.frmgameplay _frmgameplay = null;
public b4a.example.frmlanguageselect _frmlanguageselect = null;
public b4a.example.frmlevel2 _frmlevel2 = null;
public b4a.example.frmlevel3 _frmlevel3 = null;
public b4a.example.xuiviewsutils _xuiviewsutils = null;

public static void initializeProcessGlobals() {
             try {
                Class.forName(BA.applicationContext.getPackageName() + ".main").getMethod("initializeProcessGlobals").invoke(null, null);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
}
public static String  _activity_create(boolean _firsttime) throws Exception{
 //BA.debugLineNum = 26;BA.debugLine="Sub Activity_Create(FirstTime As Boolean)";
 //BA.debugLineNum = 30;BA.debugLine="Activity.LoadLayout(\"FrmLevels\")";
mostCurrent._activity.LoadLayout("FrmLevels",mostCurrent.activityBA);
 //BA.debugLineNum = 31;BA.debugLine="If Functions.Spanish = True Then";
if (mostCurrent._functions._spanish /*boolean*/ ==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 32;BA.debugLine="LblSelect.Text = \"Seleccionar Nivel\"";
mostCurrent._lblselect.setText(BA.ObjectToCharSequence("Seleccionar Nivel"));
 //BA.debugLineNum = 33;BA.debugLine="LblSelectShadow.Text = \"Seleccionar Nivel\"";
mostCurrent._lblselectshadow.setText(BA.ObjectToCharSequence("Seleccionar Nivel"));
 //BA.debugLineNum = 34;BA.debugLine="BtnLvl1.Text = \"1 - Fácil\"";
mostCurrent._btnlvl1.setText(BA.ObjectToCharSequence("1 - Fácil"));
 //BA.debugLineNum = 35;BA.debugLine="BtnLvl2.Text = \"2 - Intermedio\"";
mostCurrent._btnlvl2.setText(BA.ObjectToCharSequence("2 - Intermedio"));
 //BA.debugLineNum = 36;BA.debugLine="BtnLvl3.Text = \"3 - Experto\"";
mostCurrent._btnlvl3.setText(BA.ObjectToCharSequence("3 - Experto"));
 }else {
 //BA.debugLineNum = 38;BA.debugLine="LblSelect.Text = \"Level Select\"";
mostCurrent._lblselect.setText(BA.ObjectToCharSequence("Level Select"));
 //BA.debugLineNum = 39;BA.debugLine="LblSelectShadow.Text = \"Level Select\"";
mostCurrent._lblselectshadow.setText(BA.ObjectToCharSequence("Level Select"));
 //BA.debugLineNum = 40;BA.debugLine="BtnLvl1.Text = \"1 - Easy\"";
mostCurrent._btnlvl1.setText(BA.ObjectToCharSequence("1 - Easy"));
 //BA.debugLineNum = 41;BA.debugLine="BtnLvl2.Text = \"2 - Medium\"";
mostCurrent._btnlvl2.setText(BA.ObjectToCharSequence("2 - Medium"));
 //BA.debugLineNum = 42;BA.debugLine="BtnLvl3.Text = \"3 - Hard\"";
mostCurrent._btnlvl3.setText(BA.ObjectToCharSequence("3 - Hard"));
 };
 //BA.debugLineNum = 44;BA.debugLine="End Sub";
return "";
}
public static String  _activity_pause(boolean _userclosed) throws Exception{
 //BA.debugLineNum = 62;BA.debugLine="Sub Activity_Pause (UserClosed As Boolean)";
 //BA.debugLineNum = 64;BA.debugLine="End Sub";
return "";
}
public static String  _activity_resume() throws Exception{
 //BA.debugLineNum = 46;BA.debugLine="Sub Activity_Resume";
 //BA.debugLineNum = 47;BA.debugLine="If Functions.Spanish = True Then";
if (mostCurrent._functions._spanish /*boolean*/ ==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 48;BA.debugLine="LblSelect.Text = \"Seleccionar Nivel\"";
mostCurrent._lblselect.setText(BA.ObjectToCharSequence("Seleccionar Nivel"));
 //BA.debugLineNum = 49;BA.debugLine="LblSelectShadow.Text = \"Seleccionar Nivel\"";
mostCurrent._lblselectshadow.setText(BA.ObjectToCharSequence("Seleccionar Nivel"));
 //BA.debugLineNum = 50;BA.debugLine="BtnLvl1.Text = \"1 - Fácil\"";
mostCurrent._btnlvl1.setText(BA.ObjectToCharSequence("1 - Fácil"));
 //BA.debugLineNum = 51;BA.debugLine="BtnLvl2.Text = \"2 - Intermedio\"";
mostCurrent._btnlvl2.setText(BA.ObjectToCharSequence("2 - Intermedio"));
 //BA.debugLineNum = 52;BA.debugLine="BtnLvl3.Text = \"3 - Experto\"";
mostCurrent._btnlvl3.setText(BA.ObjectToCharSequence("3 - Experto"));
 }else {
 //BA.debugLineNum = 54;BA.debugLine="LblSelect.Text = \"Level Select\"";
mostCurrent._lblselect.setText(BA.ObjectToCharSequence("Level Select"));
 //BA.debugLineNum = 55;BA.debugLine="LblSelectShadow.Text = \"Level Select\"";
mostCurrent._lblselectshadow.setText(BA.ObjectToCharSequence("Level Select"));
 //BA.debugLineNum = 56;BA.debugLine="BtnLvl1.Text = \"1 - Easy\"";
mostCurrent._btnlvl1.setText(BA.ObjectToCharSequence("1 - Easy"));
 //BA.debugLineNum = 57;BA.debugLine="BtnLvl2.Text = \"2 - Medium\"";
mostCurrent._btnlvl2.setText(BA.ObjectToCharSequence("2 - Medium"));
 //BA.debugLineNum = 58;BA.debugLine="BtnLvl3.Text = \"3 - Hard\"";
mostCurrent._btnlvl3.setText(BA.ObjectToCharSequence("3 - Hard"));
 };
 //BA.debugLineNum = 60;BA.debugLine="End Sub";
return "";
}
public static String  _btnback_click() throws Exception{
 //BA.debugLineNum = 104;BA.debugLine="Private Sub BtnBack_Click";
 //BA.debugLineNum = 105;BA.debugLine="StartActivity(\"FrmTitleScreen\")";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)("FrmTitleScreen"));
 //BA.debugLineNum = 106;BA.debugLine="End Sub";
return "";
}
public static String  _btnlvl1_click() throws Exception{
 //BA.debugLineNum = 67;BA.debugLine="Private Sub BtnLvl1_Click";
 //BA.debugLineNum = 68;BA.debugLine="If Functions.Spanish = True Then";
if (mostCurrent._functions._spanish /*boolean*/ ==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 69;BA.debugLine="Functions.Levels = \"Fácil\"";
mostCurrent._functions._levels /*String*/  = "Fácil";
 }else {
 //BA.debugLineNum = 71;BA.debugLine="Functions.Levels = \"Easy\"";
mostCurrent._functions._levels /*String*/  = "Easy";
 };
 //BA.debugLineNum = 73;BA.debugLine="StartActivity(\"FrmGamePlay\")";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)("FrmGamePlay"));
 //BA.debugLineNum = 74;BA.debugLine="End Sub";
return "";
}
public static String  _btnlvl2_click() throws Exception{
 //BA.debugLineNum = 76;BA.debugLine="Private Sub BtnLvl2_Click";
 //BA.debugLineNum = 77;BA.debugLine="If Functions.Spanish = True Then";
if (mostCurrent._functions._spanish /*boolean*/ ==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 78;BA.debugLine="Functions.Levels = \"Intermedio\"";
mostCurrent._functions._levels /*String*/  = "Intermedio";
 }else {
 //BA.debugLineNum = 80;BA.debugLine="Functions.Levels = \"Medium\"";
mostCurrent._functions._levels /*String*/  = "Medium";
 };
 //BA.debugLineNum = 82;BA.debugLine="If Functions.Level2Pass = True Then";
if (mostCurrent._functions._level2pass /*boolean*/ ==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 83;BA.debugLine="StartActivity(\"FrmLevel2\")";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)("FrmLevel2"));
 }else {
 //BA.debugLineNum = 85;BA.debugLine="MsgboxAsync(\"You need to win more levels to unlc";
anywheresoftware.b4a.keywords.Common.MsgboxAsync(BA.ObjectToCharSequence("You need to win more levels to unlcok this one"),BA.ObjectToCharSequence("Level Locked"),processBA);
 };
 //BA.debugLineNum = 88;BA.debugLine="End Sub";
return "";
}
public static String  _btnlvl3_click() throws Exception{
 //BA.debugLineNum = 90;BA.debugLine="Private Sub BtnLvl3_Click";
 //BA.debugLineNum = 91;BA.debugLine="If Functions.Spanish = True Then";
if (mostCurrent._functions._spanish /*boolean*/ ==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 92;BA.debugLine="Functions.Levels = \"Difícil\"";
mostCurrent._functions._levels /*String*/  = "Difícil";
 }else {
 //BA.debugLineNum = 94;BA.debugLine="Functions.Levels = \"Hard\"";
mostCurrent._functions._levels /*String*/  = "Hard";
 };
 //BA.debugLineNum = 96;BA.debugLine="If Functions.Level3Pass = True Then";
if (mostCurrent._functions._level3pass /*boolean*/ ==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 97;BA.debugLine="StartActivity(\"FrmLevel3\")";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)("FrmLevel3"));
 }else {
 //BA.debugLineNum = 99;BA.debugLine="MsgboxAsync(\"You need to win more levels to unlc";
anywheresoftware.b4a.keywords.Common.MsgboxAsync(BA.ObjectToCharSequence("You need to win more levels to unlcok this one"),BA.ObjectToCharSequence("Level Locked"),processBA);
 };
 //BA.debugLineNum = 102;BA.debugLine="End Sub";
return "";
}
public static String  _globals() throws Exception{
 //BA.debugLineNum = 12;BA.debugLine="Sub Globals";
 //BA.debugLineNum = 16;BA.debugLine="Private LblSelect As Label";
mostCurrent._lblselect = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 17;BA.debugLine="Private BtnLvl1 As Button";
mostCurrent._btnlvl1 = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 18;BA.debugLine="Private ImgLvl2Locked As ImageView";
mostCurrent._imglvl2locked = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 19;BA.debugLine="Private ImgLvl3Locked As ImageView";
mostCurrent._imglvl3locked = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 20;BA.debugLine="Private BtnLvl2 As Button";
mostCurrent._btnlvl2 = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 21;BA.debugLine="Private BtnLvl3 As Button";
mostCurrent._btnlvl3 = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 22;BA.debugLine="Private BtnBack As Button";
mostCurrent._btnback = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 23;BA.debugLine="Private LblSelectShadow As Label";
mostCurrent._lblselectshadow = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 24;BA.debugLine="End Sub";
return "";
}
public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 6;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 10;BA.debugLine="End Sub";
return "";
}
}
