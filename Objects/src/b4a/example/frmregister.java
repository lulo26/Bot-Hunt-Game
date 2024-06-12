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

public class frmregister extends Activity implements B4AActivity{
	public static frmregister mostCurrent;
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
			processBA = new BA(this.getApplicationContext(), null, null, "b4a.example", "b4a.example.frmregister");
			processBA.loadHtSubs(this.getClass());
	        float deviceScale = getApplicationContext().getResources().getDisplayMetrics().density;
	        BALayout.setDeviceScale(deviceScale);
            
		}
		else if (previousOne != null) {
			Activity p = previousOne.get();
			if (p != null && p != this) {
                BA.LogInfo("Killing previous instance (frmregister).");
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
		activityBA = new BA(this, layout, processBA, "b4a.example", "b4a.example.frmregister");
        
        processBA.sharedProcessBA.activityBA = new java.lang.ref.WeakReference<BA>(activityBA);
        anywheresoftware.b4a.objects.ViewWrapper.lastId = 0;
        _activity = new ActivityWrapper(activityBA, "activity");
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        if (BA.isShellModeRuntimeCheck(processBA)) {
			if (isFirst)
				processBA.raiseEvent2(null, true, "SHELL", false);
			processBA.raiseEvent2(null, true, "CREATE", true, "b4a.example.frmregister", processBA, activityBA, _activity, anywheresoftware.b4a.keywords.Common.Density, mostCurrent);
			_activity.reinitializeForShell(activityBA, "activity");
		}
        initializeProcessGlobals();		
        initializeGlobals();
        
        BA.LogInfo("** Activity (frmregister) Create " + (isFirst ? "(first time)" : "") + " **");
        processBA.raiseEvent2(null, true, "activity_create", false, isFirst);
		isFirst = false;
		if (this != mostCurrent)
			return;
        processBA.setActivityPaused(false);
        BA.LogInfo("** Activity (frmregister) Resume **");
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
		return frmregister.class;
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
            BA.LogInfo("** Activity (frmregister) Pause, UserClosed = " + activityBA.activity.isFinishing() + " **");
        else
            BA.LogInfo("** Activity (frmregister) Pause event (activity is not paused). **");
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
            frmregister mc = mostCurrent;
			if (mc == null || mc != activity.get())
				return;
			processBA.setActivityPaused(false);
            BA.LogInfo("** Activity (frmregister) Resume **");
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
public anywheresoftware.b4a.objects.EditTextWrapper _txtuser = null;
public anywheresoftware.b4a.objects.EditTextWrapper _txtpass = null;
public anywheresoftware.b4a.objects.EditTextWrapper _txtname = null;
public anywheresoftware.b4a.objects.EditTextWrapper _txtemail = null;
public anywheresoftware.b4a.objects.LabelWrapper _lbluser = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblpass = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblname = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblemail = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnexit = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnregister = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblcreateuser = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblcreateusershadow = null;
public b4a.example.dateutils _dateutils = null;
public b4a.example.main _main = null;
public b4a.example.starter _starter = null;
public b4a.example.functions _functions = null;
public b4a.example.frmtitlescreen _frmtitlescreen = null;
public b4a.example.frmlogin _frmlogin = null;
public b4a.example.frmoptions _frmoptions = null;
public b4a.example.frmabout _frmabout = null;
public b4a.example.frmleadership _frmleadership = null;
public b4a.example.frmlevels _frmlevels = null;
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
 //BA.debugLineNum = 29;BA.debugLine="Sub Activity_Create(FirstTime As Boolean)";
 //BA.debugLineNum = 30;BA.debugLine="Activity.LoadLayout(\"FrmRegister\")";
mostCurrent._activity.LoadLayout("FrmRegister",mostCurrent.activityBA);
 //BA.debugLineNum = 31;BA.debugLine="If Functions.Spanish = True Then";
if (mostCurrent._functions._spanish /*boolean*/ ==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 32;BA.debugLine="LblUser.Text = \"Usuario\"";
mostCurrent._lbluser.setText(BA.ObjectToCharSequence("Usuario"));
 //BA.debugLineNum = 33;BA.debugLine="LblPass.Text = \"Contraseña\"";
mostCurrent._lblpass.setText(BA.ObjectToCharSequence("Contraseña"));
 //BA.debugLineNum = 34;BA.debugLine="LblName.Text = \"Nombre\"";
mostCurrent._lblname.setText(BA.ObjectToCharSequence("Nombre"));
 //BA.debugLineNum = 35;BA.debugLine="LblEmail.Text = \"Correo\"";
mostCurrent._lblemail.setText(BA.ObjectToCharSequence("Correo"));
 //BA.debugLineNum = 36;BA.debugLine="BtnExit.Text = \"Salir\"";
mostCurrent._btnexit.setText(BA.ObjectToCharSequence("Salir"));
 //BA.debugLineNum = 37;BA.debugLine="BtnRegister.Text = \"Registrarse\"";
mostCurrent._btnregister.setText(BA.ObjectToCharSequence("Registrarse"));
 //BA.debugLineNum = 38;BA.debugLine="LblCreateUser.Text = \"Crear Usuario\"";
mostCurrent._lblcreateuser.setText(BA.ObjectToCharSequence("Crear Usuario"));
 //BA.debugLineNum = 39;BA.debugLine="LblCreateUserShadow.Text = \"Crear Usuario\"";
mostCurrent._lblcreateusershadow.setText(BA.ObjectToCharSequence("Crear Usuario"));
 }else {
 //BA.debugLineNum = 41;BA.debugLine="LblUser.Text = \"Username\"";
mostCurrent._lbluser.setText(BA.ObjectToCharSequence("Username"));
 //BA.debugLineNum = 42;BA.debugLine="LblPass.Text = \"Password\"";
mostCurrent._lblpass.setText(BA.ObjectToCharSequence("Password"));
 //BA.debugLineNum = 43;BA.debugLine="LblName.Text = \"Name\"";
mostCurrent._lblname.setText(BA.ObjectToCharSequence("Name"));
 //BA.debugLineNum = 44;BA.debugLine="LblEmail.Text = \"Email\"";
mostCurrent._lblemail.setText(BA.ObjectToCharSequence("Email"));
 //BA.debugLineNum = 45;BA.debugLine="BtnExit.Text = \"Exit\"";
mostCurrent._btnexit.setText(BA.ObjectToCharSequence("Exit"));
 //BA.debugLineNum = 46;BA.debugLine="BtnRegister.Text = \"Register\"";
mostCurrent._btnregister.setText(BA.ObjectToCharSequence("Register"));
 //BA.debugLineNum = 47;BA.debugLine="LblCreateUser.Text = \"Create User\"";
mostCurrent._lblcreateuser.setText(BA.ObjectToCharSequence("Create User"));
 //BA.debugLineNum = 48;BA.debugLine="LblCreateUserShadow.Text = \"Create User\"";
mostCurrent._lblcreateusershadow.setText(BA.ObjectToCharSequence("Create User"));
 };
 //BA.debugLineNum = 51;BA.debugLine="End Sub";
return "";
}
public static String  _activity_pause(boolean _userclosed) throws Exception{
 //BA.debugLineNum = 75;BA.debugLine="Sub Activity_Pause (UserClosed As Boolean)";
 //BA.debugLineNum = 77;BA.debugLine="End Sub";
return "";
}
public static String  _activity_resume() throws Exception{
 //BA.debugLineNum = 53;BA.debugLine="Sub Activity_Resume";
 //BA.debugLineNum = 54;BA.debugLine="If Functions.Spanish = True Then";
if (mostCurrent._functions._spanish /*boolean*/ ==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 55;BA.debugLine="LblUser.Text = \"Usuario\"";
mostCurrent._lbluser.setText(BA.ObjectToCharSequence("Usuario"));
 //BA.debugLineNum = 56;BA.debugLine="LblPass.Text = \"Contraseña\"";
mostCurrent._lblpass.setText(BA.ObjectToCharSequence("Contraseña"));
 //BA.debugLineNum = 57;BA.debugLine="LblName.Text = \"Nombre\"";
mostCurrent._lblname.setText(BA.ObjectToCharSequence("Nombre"));
 //BA.debugLineNum = 58;BA.debugLine="LblEmail.Text = \"Correo\"";
mostCurrent._lblemail.setText(BA.ObjectToCharSequence("Correo"));
 //BA.debugLineNum = 59;BA.debugLine="BtnExit.Text = \"Salir\"";
mostCurrent._btnexit.setText(BA.ObjectToCharSequence("Salir"));
 //BA.debugLineNum = 60;BA.debugLine="BtnRegister.Text = \"Registrarse\"";
mostCurrent._btnregister.setText(BA.ObjectToCharSequence("Registrarse"));
 //BA.debugLineNum = 61;BA.debugLine="LblCreateUser.Text = \"Crear Usuario\"";
mostCurrent._lblcreateuser.setText(BA.ObjectToCharSequence("Crear Usuario"));
 //BA.debugLineNum = 62;BA.debugLine="LblCreateUserShadow.Text = \"Crear Usuario\"";
mostCurrent._lblcreateusershadow.setText(BA.ObjectToCharSequence("Crear Usuario"));
 }else {
 //BA.debugLineNum = 64;BA.debugLine="LblUser.Text = \"Username\"";
mostCurrent._lbluser.setText(BA.ObjectToCharSequence("Username"));
 //BA.debugLineNum = 65;BA.debugLine="LblPass.Text = \"Password\"";
mostCurrent._lblpass.setText(BA.ObjectToCharSequence("Password"));
 //BA.debugLineNum = 66;BA.debugLine="LblName.Text = \"Name\"";
mostCurrent._lblname.setText(BA.ObjectToCharSequence("Name"));
 //BA.debugLineNum = 67;BA.debugLine="LblEmail.Text = \"Email\"";
mostCurrent._lblemail.setText(BA.ObjectToCharSequence("Email"));
 //BA.debugLineNum = 68;BA.debugLine="BtnExit.Text = \"Exit\"";
mostCurrent._btnexit.setText(BA.ObjectToCharSequence("Exit"));
 //BA.debugLineNum = 69;BA.debugLine="BtnRegister.Text = \"Register\"";
mostCurrent._btnregister.setText(BA.ObjectToCharSequence("Register"));
 //BA.debugLineNum = 70;BA.debugLine="LblCreateUser.Text = \"Create User\"";
mostCurrent._lblcreateuser.setText(BA.ObjectToCharSequence("Create User"));
 //BA.debugLineNum = 71;BA.debugLine="LblCreateUserShadow.Text = \"Create User\"";
mostCurrent._lblcreateusershadow.setText(BA.ObjectToCharSequence("Create User"));
 };
 //BA.debugLineNum = 73;BA.debugLine="End Sub";
return "";
}
public static String  _btnexit_click() throws Exception{
 //BA.debugLineNum = 123;BA.debugLine="Private Sub BtnExit_Click";
 //BA.debugLineNum = 124;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 //BA.debugLineNum = 125;BA.debugLine="End Sub";
return "";
}
public static void  _btnregister_click() throws Exception{
ResumableSub_BtnRegister_Click rsub = new ResumableSub_BtnRegister_Click(null);
rsub.resume(processBA, null);
}
public static class ResumableSub_BtnRegister_Click extends BA.ResumableSub {
public ResumableSub_BtnRegister_Click(b4a.example.frmregister parent) {
this.parent = parent;
}
b4a.example.frmregister parent;
anywheresoftware.b4a.objects.collections.List _listdata = null;
int _result = 0;

@Override
public void resume(BA ba, Object[] result) throws Exception{

    while (true) {
        switch (state) {
            case -1:
return;

case 0:
//C
this.state = 1;
 //BA.debugLineNum = 81;BA.debugLine="If TxtUser.Text.Length > 2 And TxtPass.Text.Lengt";
if (true) break;

case 1:
//if
this.state = 38;
if (parent.mostCurrent._txtuser.getText().length()>2 && parent.mostCurrent._txtpass.getText().length()>5 && parent.mostCurrent._txtname.getText().length()>2 && parent.mostCurrent._txtemail.getText().length()>6) { 
this.state = 3;
}else {
this.state = 37;
}if (true) break;

case 3:
//C
this.state = 4;
 //BA.debugLineNum = 82;BA.debugLine="If File.Exists(File.DirInternal, \"GameData.txt\")";
if (true) break;

case 4:
//if
this.state = 35;
if (anywheresoftware.b4a.keywords.Common.File.Exists(anywheresoftware.b4a.keywords.Common.File.getDirInternal(),"GameData.txt")) { 
this.state = 6;
}else {
this.state = 24;
}if (true) break;

case 6:
//C
this.state = 7;
 //BA.debugLineNum = 85;BA.debugLine="Dim ListData As List";
_listdata = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 86;BA.debugLine="ListData = File.ReadList(File.DirInternal, \"Gam";
_listdata = anywheresoftware.b4a.keywords.Common.File.ReadList(anywheresoftware.b4a.keywords.Common.File.getDirInternal(),"GameData.txt");
 //BA.debugLineNum = 87;BA.debugLine="If ListData.Size = 0 Then";
if (true) break;

case 7:
//if
this.state = 22;
if (_listdata.getSize()==0) { 
this.state = 9;
}else {
this.state = 11;
}if (true) break;

case 9:
//C
this.state = 22;
 //BA.debugLineNum = 88;BA.debugLine="ListData.Add(TxtUser.Text & \",\" & TxtPass.Text";
_listdata.Add((Object)(parent.mostCurrent._txtuser.getText()+","+parent.mostCurrent._txtpass.getText()+","+parent.mostCurrent._txtname.getText()+","+parent.mostCurrent._txtemail.getText()+","+"0"));
 if (true) break;

case 11:
//C
this.state = 12;
 //BA.debugLineNum = 90;BA.debugLine="If Functions.Func_SearchUser(TxtUser.Text) The";
if (true) break;

case 12:
//if
this.state = 21;
if (parent.mostCurrent._functions._func_searchuser /*boolean*/ (mostCurrent.activityBA,parent.mostCurrent._txtuser.getText())) { 
this.state = 14;
}else {
this.state = 16;
}if (true) break;

case 14:
//C
this.state = 21;
 //BA.debugLineNum = 91;BA.debugLine="MsgboxAsync (\"user already exists\", \"Error!\")";
anywheresoftware.b4a.keywords.Common.MsgboxAsync(BA.ObjectToCharSequence("user already exists"),BA.ObjectToCharSequence("Error!"),processBA);
 if (true) break;

case 16:
//C
this.state = 17;
 //BA.debugLineNum = 93;BA.debugLine="ListData.Add(TxtUser.Text & \",\" & TxtPass.Tex";
_listdata.Add((Object)(parent.mostCurrent._txtuser.getText()+","+parent.mostCurrent._txtpass.getText()+","+parent.mostCurrent._txtname.getText()+","+parent.mostCurrent._txtemail.getText()+","+"0"));
 //BA.debugLineNum = 94;BA.debugLine="File.WriteList(File.DirInternal, \"GameData.tx";
anywheresoftware.b4a.keywords.Common.File.WriteList(anywheresoftware.b4a.keywords.Common.File.getDirInternal(),"GameData.txt",_listdata);
 //BA.debugLineNum = 95;BA.debugLine="MsgboxAsync(\"User created successfully\", \"Con";
anywheresoftware.b4a.keywords.Common.MsgboxAsync(BA.ObjectToCharSequence("User created successfully"),BA.ObjectToCharSequence("Congrats!"),processBA);
 //BA.debugLineNum = 96;BA.debugLine="Wait For MsgBox_Result (Result As Int)";
anywheresoftware.b4a.keywords.Common.WaitFor("msgbox_result", processBA, this, null);
this.state = 39;
return;
case 39:
//C
this.state = 17;
_result = (Integer) result[0];
;
 //BA.debugLineNum = 97;BA.debugLine="If Result = DialogResponse.POSITIVE Then";
if (true) break;

case 17:
//if
this.state = 20;
if (_result==anywheresoftware.b4a.keywords.Common.DialogResponse.POSITIVE) { 
this.state = 19;
}if (true) break;

case 19:
//C
this.state = 20;
 //BA.debugLineNum = 98;BA.debugLine="Activity.Finish";
parent.mostCurrent._activity.Finish();
 if (true) break;

case 20:
//C
this.state = 21;
;
 if (true) break;

case 21:
//C
this.state = 22;
;
 if (true) break;

case 22:
//C
this.state = 35;
;
 if (true) break;

case 24:
//C
this.state = 25;
 //BA.debugLineNum = 103;BA.debugLine="File.Copy(File.DirAssets, \"GameData.txt\", File.";
anywheresoftware.b4a.keywords.Common.File.Copy(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"GameData.txt",anywheresoftware.b4a.keywords.Common.File.getDirInternal(),"GameData.txt");
 //BA.debugLineNum = 104;BA.debugLine="Dim ListData As List";
_listdata = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 105;BA.debugLine="ListData = File.ReadList(File.DirInternal, \"Gam";
_listdata = anywheresoftware.b4a.keywords.Common.File.ReadList(anywheresoftware.b4a.keywords.Common.File.getDirInternal(),"GameData.txt");
 //BA.debugLineNum = 106;BA.debugLine="If ListData.Size = 0 Then";
if (true) break;

case 25:
//if
this.state = 34;
if (_listdata.getSize()==0) { 
this.state = 27;
}else {
this.state = 29;
}if (true) break;

case 27:
//C
this.state = 34;
 //BA.debugLineNum = 107;BA.debugLine="ListData.Add(TxtUser.Text & \",\" & TxtPass.Text";
_listdata.Add((Object)(parent.mostCurrent._txtuser.getText()+","+parent.mostCurrent._txtpass.getText()+","+parent.mostCurrent._txtname.getText()+","+parent.mostCurrent._txtemail.getText()+","+"0"));
 if (true) break;

case 29:
//C
this.state = 30;
 //BA.debugLineNum = 109;BA.debugLine="File.WriteList(File.DirInternal,\"GameData.txt\"";
anywheresoftware.b4a.keywords.Common.File.WriteList(anywheresoftware.b4a.keywords.Common.File.getDirInternal(),"GameData.txt",_listdata);
 //BA.debugLineNum = 110;BA.debugLine="MsgboxAsync(\"User created successfully\", \"Cong";
anywheresoftware.b4a.keywords.Common.MsgboxAsync(BA.ObjectToCharSequence("User created successfully"),BA.ObjectToCharSequence("Congrats!"),processBA);
 //BA.debugLineNum = 111;BA.debugLine="Wait For MsgBox_Result (Result As Int)";
anywheresoftware.b4a.keywords.Common.WaitFor("msgbox_result", processBA, this, null);
this.state = 40;
return;
case 40:
//C
this.state = 30;
_result = (Integer) result[0];
;
 //BA.debugLineNum = 112;BA.debugLine="If Result = DialogResponse.POSITIVE Then";
if (true) break;

case 30:
//if
this.state = 33;
if (_result==anywheresoftware.b4a.keywords.Common.DialogResponse.POSITIVE) { 
this.state = 32;
}if (true) break;

case 32:
//C
this.state = 33;
 //BA.debugLineNum = 113;BA.debugLine="Activity.Finish";
parent.mostCurrent._activity.Finish();
 if (true) break;

case 33:
//C
this.state = 34;
;
 if (true) break;

case 34:
//C
this.state = 35;
;
 if (true) break;

case 35:
//C
this.state = 38;
;
 if (true) break;

case 37:
//C
this.state = 38;
 //BA.debugLineNum = 119;BA.debugLine="MsgboxAsync(\"Make sure to fill all the required";
anywheresoftware.b4a.keywords.Common.MsgboxAsync(BA.ObjectToCharSequence("Make sure to fill all the required data"),BA.ObjectToCharSequence("Error!"),processBA);
 if (true) break;

case 38:
//C
this.state = -1;
;
 //BA.debugLineNum = 121;BA.debugLine="End Sub";
if (true) break;

            }
        }
    }
}
public static void  _msgbox_result(int _result) throws Exception{
}
public static String  _globals() throws Exception{
 //BA.debugLineNum = 11;BA.debugLine="Sub Globals";
 //BA.debugLineNum = 15;BA.debugLine="Private TxtUser As EditText";
mostCurrent._txtuser = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 16;BA.debugLine="Private TxtPass As EditText";
mostCurrent._txtpass = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 17;BA.debugLine="Private TxtName As EditText";
mostCurrent._txtname = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 18;BA.debugLine="Private TxtEmail As EditText";
mostCurrent._txtemail = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 19;BA.debugLine="Private LblUser As Label";
mostCurrent._lbluser = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 20;BA.debugLine="Private LblPass As Label";
mostCurrent._lblpass = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 21;BA.debugLine="Private LblName As Label";
mostCurrent._lblname = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 22;BA.debugLine="Private LblEmail As Label";
mostCurrent._lblemail = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 23;BA.debugLine="Private BtnExit As Button";
mostCurrent._btnexit = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 24;BA.debugLine="Private BtnRegister As Button";
mostCurrent._btnregister = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 25;BA.debugLine="Private LblCreateUser As Label";
mostCurrent._lblcreateuser = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 26;BA.debugLine="Private LblCreateUserShadow As Label";
mostCurrent._lblcreateusershadow = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 27;BA.debugLine="End Sub";
return "";
}
public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 6;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 9;BA.debugLine="End Sub";
return "";
}
}
