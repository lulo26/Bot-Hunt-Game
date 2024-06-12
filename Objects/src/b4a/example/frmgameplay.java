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

public class frmgameplay extends Activity implements B4AActivity{
	public static frmgameplay mostCurrent;
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
			processBA = new BA(this.getApplicationContext(), null, null, "b4a.example", "b4a.example.frmgameplay");
			processBA.loadHtSubs(this.getClass());
	        float deviceScale = getApplicationContext().getResources().getDisplayMetrics().density;
	        BALayout.setDeviceScale(deviceScale);
            
		}
		else if (previousOne != null) {
			Activity p = previousOne.get();
			if (p != null && p != this) {
                BA.LogInfo("Killing previous instance (frmgameplay).");
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
		activityBA = new BA(this, layout, processBA, "b4a.example", "b4a.example.frmgameplay");
        
        processBA.sharedProcessBA.activityBA = new java.lang.ref.WeakReference<BA>(activityBA);
        anywheresoftware.b4a.objects.ViewWrapper.lastId = 0;
        _activity = new ActivityWrapper(activityBA, "activity");
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        if (BA.isShellModeRuntimeCheck(processBA)) {
			if (isFirst)
				processBA.raiseEvent2(null, true, "SHELL", false);
			processBA.raiseEvent2(null, true, "CREATE", true, "b4a.example.frmgameplay", processBA, activityBA, _activity, anywheresoftware.b4a.keywords.Common.Density, mostCurrent);
			_activity.reinitializeForShell(activityBA, "activity");
		}
        initializeProcessGlobals();		
        initializeGlobals();
        
        BA.LogInfo("** Activity (frmgameplay) Create " + (isFirst ? "(first time)" : "") + " **");
        processBA.raiseEvent2(null, true, "activity_create", false, isFirst);
		isFirst = false;
		if (this != mostCurrent)
			return;
        processBA.setActivityPaused(false);
        BA.LogInfo("** Activity (frmgameplay) Resume **");
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
		return frmgameplay.class;
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
            BA.LogInfo("** Activity (frmgameplay) Pause, UserClosed = " + activityBA.activity.isFinishing() + " **");
        else
            BA.LogInfo("** Activity (frmgameplay) Pause event (activity is not paused). **");
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
            frmgameplay mc = mostCurrent;
			if (mc == null || mc != activity.get())
				return;
			processBA.setActivityPaused(false);
            BA.LogInfo("** Activity (frmgameplay) Resume **");
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
public static anywheresoftware.b4a.objects.Timer _timerbot1 = null;
public static anywheresoftware.b4a.objects.Timer _timerbothit = null;
public static anywheresoftware.b4a.objects.Timer _timerbothitstop = null;
public static anywheresoftware.b4a.objects.Timer _timerbotmiss = null;
public static anywheresoftware.b4a.objects.Timer _timerbotmissdown = null;
public static anywheresoftware.b4a.objects.Timer _timerbotmissrest = null;
public static anywheresoftware.b4a.objects.Timer _timerbotmissstop = null;
public static anywheresoftware.b4a.objects.Timer _timerbotmissdownstop = null;
public static anywheresoftware.b4a.objects.Timer _timerbotmissreststop = null;
public static anywheresoftware.b4a.objects.MediaPlayerWrapper _soundshotwin = null;
public static anywheresoftware.b4a.objects.MediaPlayerWrapper _soundmiss = null;
public static anywheresoftware.b4a.objects.MediaPlayerWrapper _soundbotshot = null;
public static anywheresoftware.b4a.objects.MediaPlayerWrapper _soundstageclear = null;
public static anywheresoftware.b4a.objects.MediaPlayerWrapper _soundlevelclear = null;
public static anywheresoftware.b4a.objects.MediaPlayerWrapper _sounddefeat = null;
public static anywheresoftware.b4a.objects.MediaPlayerWrapper _soundperfectscore = null;
public static anywheresoftware.b4a.objects.MediaPlayerWrapper _soundbothurt = null;
public static anywheresoftware.b4a.objects.MediaPlayerWrapper _soundbotflying = null;
public static anywheresoftware.b4a.objects.MediaPlayerWrapper _soundnobullets = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imgbgday = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imgbot = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imggrassday = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblhit = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imghit1 = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imghit2 = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imghit3 = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imghit4 = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imghit5 = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imghit6 = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblscoretxt = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblscore = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblshot = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imgshot1 = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imgshot2 = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imgshot3 = null;
public static int _clickshot = 0;
public static int _clickmiss = 0;
public static long _score = 0L;
public static int _botrnd = 0;
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
public b4a.example.frmlevels _frmlevels = null;
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
 //BA.debugLineNum = 59;BA.debugLine="Sub Activity_Create(FirstTime As Boolean)";
 //BA.debugLineNum = 60;BA.debugLine="Activity.LoadLayout(\"FrmLevel1\")";
mostCurrent._activity.LoadLayout("FrmLevel1",mostCurrent.activityBA);
 //BA.debugLineNum = 61;BA.debugLine="FrmTitleScreen.SoundMedley.Pause";
mostCurrent._frmtitlescreen._soundmedley /*anywheresoftware.b4a.objects.MediaPlayerWrapper*/ .Pause();
 //BA.debugLineNum = 63;BA.debugLine="If Functions.Spanish = True Then";
if (mostCurrent._functions._spanish /*boolean*/ ==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 64;BA.debugLine="LblScoreTxt.Text = \"PUNTAJE\"";
mostCurrent._lblscoretxt.setText(BA.ObjectToCharSequence("PUNTAJE"));
 //BA.debugLineNum = 65;BA.debugLine="LblShot.Text = \"TIRO\"";
mostCurrent._lblshot.setText(BA.ObjectToCharSequence("TIRO"));
 }else {
 //BA.debugLineNum = 67;BA.debugLine="LblScoreTxt.Text = \"SCORE\"";
mostCurrent._lblscoretxt.setText(BA.ObjectToCharSequence("SCORE"));
 //BA.debugLineNum = 68;BA.debugLine="LblShot.Text = \"SHOT\"";
mostCurrent._lblshot.setText(BA.ObjectToCharSequence("SHOT"));
 };
 //BA.debugLineNum = 71;BA.debugLine="Score = Functions.score";
_score = mostCurrent._functions._score /*long*/ ;
 //BA.debugLineNum = 73;BA.debugLine="TimerBot1.Initialize (\"TimerBot1\", 50)";
_timerbot1.Initialize(processBA,"TimerBot1",(long) (50));
 //BA.debugLineNum = 74;BA.debugLine="TimerBot1.Enabled = True";
_timerbot1.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 75;BA.debugLine="TimerBotHit.Initialize (\"TimerBotHit\", 100)";
_timerbothit.Initialize(processBA,"TimerBotHit",(long) (100));
 //BA.debugLineNum = 76;BA.debugLine="TimerBotHitStop.Initialize (\"TimerBotHitStop\", 10";
_timerbothitstop.Initialize(processBA,"TimerBotHitStop",(long) (1000));
 //BA.debugLineNum = 77;BA.debugLine="TimerBotMiss.Initialize (\"TimerBotMiss\", 50)";
_timerbotmiss.Initialize(processBA,"TimerBotMiss",(long) (50));
 //BA.debugLineNum = 78;BA.debugLine="TimerBotMissDown.Initialize(\"TimerBotMissDown\",50";
_timerbotmissdown.Initialize(processBA,"TimerBotMissDown",(long) (50));
 //BA.debugLineNum = 79;BA.debugLine="TimerBotMissStop.Initialize (\"TimerBotMissStop\",";
_timerbotmissstop.Initialize(processBA,"TimerBotMissStop",(long) (1600));
 //BA.debugLineNum = 80;BA.debugLine="TimerBotMissDownStop.Initialize(\"TimerBotMissDown";
_timerbotmissdownstop.Initialize(processBA,"TimerBotMissDownStop",(long) (1600));
 //BA.debugLineNum = 81;BA.debugLine="TimerBotMissRest.Initialize(\"TimerBotMissRest\",50";
_timerbotmissrest.Initialize(processBA,"TimerBotMissRest",(long) (500));
 //BA.debugLineNum = 82;BA.debugLine="TimerBotMissRestStop.Initialize(\"TimerBotMissRest";
_timerbotmissreststop.Initialize(processBA,"TimerBotMissRestStop",(long) (1000));
 //BA.debugLineNum = 85;BA.debugLine="SoundShotWin.Initialize";
_soundshotwin.Initialize();
 //BA.debugLineNum = 86;BA.debugLine="SoundMiss.Initialize";
_soundmiss.Initialize();
 //BA.debugLineNum = 87;BA.debugLine="SoundBotShot.Initialize";
_soundbotshot.Initialize();
 //BA.debugLineNum = 88;BA.debugLine="SoundStageClear.Initialize";
_soundstageclear.Initialize();
 //BA.debugLineNum = 89;BA.debugLine="soundLevelClear.Initialize";
_soundlevelclear.Initialize();
 //BA.debugLineNum = 90;BA.debugLine="SoundDefeat.Initialize";
_sounddefeat.Initialize();
 //BA.debugLineNum = 91;BA.debugLine="SoundPerfectScore.Initialize";
_soundperfectscore.Initialize();
 //BA.debugLineNum = 92;BA.debugLine="SoundBotFlying.Initialize";
_soundbotflying.Initialize();
 //BA.debugLineNum = 93;BA.debugLine="SoundBotHurt.Initialize";
_soundbothurt.Initialize();
 //BA.debugLineNum = 94;BA.debugLine="SoundNoBullets.Initialize";
_soundnobullets.Initialize();
 //BA.debugLineNum = 96;BA.debugLine="SoundShotWin.Load(File.DirAssets, \"ShotWin.mp3\")";
_soundshotwin.Load(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"ShotWin.mp3");
 //BA.debugLineNum = 97;BA.debugLine="SoundMiss.Load(File.DirAssets, \"Miss [laugh].mp3\"";
_soundmiss.Load(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"Miss [laugh].mp3");
 //BA.debugLineNum = 98;BA.debugLine="SoundBotShot.Load(File.DirAssets, \"Shot.mp3\")";
_soundbotshot.Load(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"Shot.mp3");
 //BA.debugLineNum = 99;BA.debugLine="SoundStageClear.Load(File.DirAssets, \"Stage Clear";
_soundstageclear.Load(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"Stage Clear.mp3");
 //BA.debugLineNum = 100;BA.debugLine="soundLevelClear.Load(File.DirAssets, \"Level Clear";
_soundlevelclear.Load(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"Level Clear.mp3");
 //BA.debugLineNum = 101;BA.debugLine="SoundDefeat.Load(File.DirAssets, \"Defeat.mp3\")";
_sounddefeat.Load(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"Defeat.mp3");
 //BA.debugLineNum = 102;BA.debugLine="SoundPerfectScore.Load(File.DirAssets, \"Perfect S";
_soundperfectscore.Load(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"Perfect Score.mp3");
 //BA.debugLineNum = 103;BA.debugLine="SoundBotHurt.Load(File.DirAssets, \"BotHurt.mp3\")";
_soundbothurt.Load(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"BotHurt.mp3");
 //BA.debugLineNum = 104;BA.debugLine="SoundBotFlying.Load(File.DirAssets, \"BotFlying.mp";
_soundbotflying.Load(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"BotFlying.mp3");
 //BA.debugLineNum = 105;BA.debugLine="SoundNoBullets.Load(File.DirAssets, \"NoMoreBullet";
_soundnobullets.Load(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"NoMoreBullets.mp3");
 //BA.debugLineNum = 107;BA.debugLine="ImgBot.Left = 290dip";
mostCurrent._imgbot.setLeft(anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (290)));
 //BA.debugLineNum = 108;BA.debugLine="ImgBot.Top = 340dip";
mostCurrent._imgbot.setTop(anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (340)));
 //BA.debugLineNum = 109;BA.debugLine="End Sub";
return "";
}
public static String  _activity_pause(boolean _userclosed) throws Exception{
 //BA.debugLineNum = 121;BA.debugLine="Sub Activity_Pause (UserClosed As Boolean)";
 //BA.debugLineNum = 122;BA.debugLine="If FrmTitleScreen.soundoff = False Then";
if (mostCurrent._frmtitlescreen._soundoff /*boolean*/ ==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 123;BA.debugLine="FrmTitleScreen.SoundMedley.Play";
mostCurrent._frmtitlescreen._soundmedley /*anywheresoftware.b4a.objects.MediaPlayerWrapper*/ .Play();
 //BA.debugLineNum = 124;BA.debugLine="FrmTitleScreen.SoundMedley.Looping = True";
mostCurrent._frmtitlescreen._soundmedley /*anywheresoftware.b4a.objects.MediaPlayerWrapper*/ .setLooping(anywheresoftware.b4a.keywords.Common.True);
 };
 //BA.debugLineNum = 126;BA.debugLine="TimerBot1.Enabled = False";
_timerbot1.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 127;BA.debugLine="End Sub";
return "";
}
public static String  _activity_resume() throws Exception{
 //BA.debugLineNum = 111;BA.debugLine="Sub Activity_Resume";
 //BA.debugLineNum = 112;BA.debugLine="If Functions.Spanish = True Then";
if (mostCurrent._functions._spanish /*boolean*/ ==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 113;BA.debugLine="LblScoreTxt.Text = \"PUNTAJE\"";
mostCurrent._lblscoretxt.setText(BA.ObjectToCharSequence("PUNTAJE"));
 //BA.debugLineNum = 114;BA.debugLine="LblShot.Text = \"TIRO\"";
mostCurrent._lblshot.setText(BA.ObjectToCharSequence("TIRO"));
 }else {
 //BA.debugLineNum = 116;BA.debugLine="LblScoreTxt.Text = \"SCORE\"";
mostCurrent._lblscoretxt.setText(BA.ObjectToCharSequence("SCORE"));
 //BA.debugLineNum = 117;BA.debugLine="LblShot.Text = \"SHOT\"";
mostCurrent._lblshot.setText(BA.ObjectToCharSequence("SHOT"));
 };
 //BA.debugLineNum = 119;BA.debugLine="End Sub";
return "";
}
public static String  _globals() throws Exception{
 //BA.debugLineNum = 33;BA.debugLine="Sub Globals";
 //BA.debugLineNum = 36;BA.debugLine="Private ImgBgDay As ImageView";
mostCurrent._imgbgday = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 37;BA.debugLine="Private ImgBot As ImageView";
mostCurrent._imgbot = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 38;BA.debugLine="Private ImgGrassDay As ImageView";
mostCurrent._imggrassday = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 39;BA.debugLine="Private LblHit As Label";
mostCurrent._lblhit = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 40;BA.debugLine="Private ImgHit1 As ImageView";
mostCurrent._imghit1 = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 41;BA.debugLine="Private ImgHit2 As ImageView";
mostCurrent._imghit2 = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 42;BA.debugLine="Private ImgHit3 As ImageView";
mostCurrent._imghit3 = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 43;BA.debugLine="Private ImgHit4 As ImageView";
mostCurrent._imghit4 = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 44;BA.debugLine="Private ImgHit5 As ImageView";
mostCurrent._imghit5 = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 45;BA.debugLine="Private ImgHit6 As ImageView";
mostCurrent._imghit6 = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 46;BA.debugLine="Private LblScoreTxt As Label";
mostCurrent._lblscoretxt = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 47;BA.debugLine="Private LblScore As Label";
mostCurrent._lblscore = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 48;BA.debugLine="Private LblShot As Label";
mostCurrent._lblshot = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 49;BA.debugLine="Private ImgShot1 As ImageView";
mostCurrent._imgshot1 = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 50;BA.debugLine="Private ImgShot2 As ImageView";
mostCurrent._imgshot2 = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 51;BA.debugLine="Private ImgShot3 As ImageView";
mostCurrent._imgshot3 = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 53;BA.debugLine="Dim clickShot As Int = 0";
_clickshot = (int) (0);
 //BA.debugLineNum = 54;BA.debugLine="Dim clickmiss As Int = 0";
_clickmiss = (int) (0);
 //BA.debugLineNum = 55;BA.debugLine="Dim Score As Long";
_score = 0L;
 //BA.debugLineNum = 56;BA.debugLine="Dim botRND As Int";
_botrnd = 0;
 //BA.debugLineNum = 57;BA.debugLine="End Sub";
return "";
}
public static String  _imgbot_click() throws Exception{
 //BA.debugLineNum = 130;BA.debugLine="Private Sub ImgBot_Click";
 //BA.debugLineNum = 131;BA.debugLine="If clickmiss > 2 Then";
if (_clickmiss>2) { 
 //BA.debugLineNum = 132;BA.debugLine="ImgBot.Enabled = False";
mostCurrent._imgbot.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 }else {
 //BA.debugLineNum = 134;BA.debugLine="SoundBotShot.Play";
_soundbotshot.Play();
 };
 //BA.debugLineNum = 136;BA.debugLine="clickShot = clickShot + 1";
_clickshot = (int) (_clickshot+1);
 //BA.debugLineNum = 138;BA.debugLine="If clickShot = 1 And clickmiss < 3 Then";
if (_clickshot==1 && _clickmiss<3) { 
 //BA.debugLineNum = 139;BA.debugLine="ImgBot.Enabled = False";
mostCurrent._imgbot.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 140;BA.debugLine="ImgBot.Bitmap = LoadBitmap(File.DirAssets, \"BotH";
mostCurrent._imgbot.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"BotHit.png").getObject()));
 //BA.debugLineNum = 141;BA.debugLine="ImgHit1.Bitmap = LoadBitmap(File.DirAssets, \"Hit";
mostCurrent._imghit1.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"HitsWin.png").getObject()));
 //BA.debugLineNum = 142;BA.debugLine="SoundBotHurt.Play";
_soundbothurt.Play();
 //BA.debugLineNum = 143;BA.debugLine="TimerBotHit.Enabled = True";
_timerbothit.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 144;BA.debugLine="TimerBotHitStop.Enabled = True";
_timerbothitstop.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 145;BA.debugLine="If clickmiss = 0 Then";
if (_clickmiss==0) { 
 //BA.debugLineNum = 146;BA.debugLine="Score = Score + 1500";
_score = (long) (_score+1500);
 //BA.debugLineNum = 147;BA.debugLine="LblScore.Text = Score";
mostCurrent._lblscore.setText(BA.ObjectToCharSequence(_score));
 //BA.debugLineNum = 148;BA.debugLine="clickmiss = 0";
_clickmiss = (int) (0);
 };
 //BA.debugLineNum = 150;BA.debugLine="If clickmiss = 1 Then";
if (_clickmiss==1) { 
 //BA.debugLineNum = 151;BA.debugLine="Score = Score + 1000";
_score = (long) (_score+1000);
 //BA.debugLineNum = 152;BA.debugLine="LblScore.Text = Score";
mostCurrent._lblscore.setText(BA.ObjectToCharSequence(_score));
 //BA.debugLineNum = 153;BA.debugLine="clickmiss = 0";
_clickmiss = (int) (0);
 };
 //BA.debugLineNum = 155;BA.debugLine="If clickmiss = 2 Then";
if (_clickmiss==2) { 
 //BA.debugLineNum = 156;BA.debugLine="Score = Score + 500";
_score = (long) (_score+500);
 //BA.debugLineNum = 157;BA.debugLine="LblScore.Text = Score";
mostCurrent._lblscore.setText(BA.ObjectToCharSequence(_score));
 //BA.debugLineNum = 158;BA.debugLine="clickmiss = 0";
_clickmiss = (int) (0);
 };
 };
 //BA.debugLineNum = 162;BA.debugLine="End Sub";
return "";
}
public static String  _misspanel_click() throws Exception{
 //BA.debugLineNum = 165;BA.debugLine="Private Sub MissPanel_Click";
 //BA.debugLineNum = 166;BA.debugLine="clickmiss = clickmiss + 1";
_clickmiss = (int) (_clickmiss+1);
 //BA.debugLineNum = 167;BA.debugLine="If clickmiss = 1 And clickShot < 1 Then";
if (_clickmiss==1 && _clickshot<1) { 
 //BA.debugLineNum = 168;BA.debugLine="ImgShot1.Visible = False";
mostCurrent._imgshot1.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 169;BA.debugLine="SoundBotShot.Play";
_soundbotshot.Play();
 };
 //BA.debugLineNum = 171;BA.debugLine="If clickmiss = 2 And clickShot < 1 Then";
if (_clickmiss==2 && _clickshot<1) { 
 //BA.debugLineNum = 172;BA.debugLine="ImgShot2.Visible = False";
mostCurrent._imgshot2.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 173;BA.debugLine="SoundBotShot.Play";
_soundbotshot.Play();
 };
 //BA.debugLineNum = 175;BA.debugLine="If clickmiss = 3 And clickShot < 1 Then";
if (_clickmiss==3 && _clickshot<1) { 
 //BA.debugLineNum = 176;BA.debugLine="ImgHit1.Bitmap = LoadBitmap(File.DirAssets, \"Hit";
mostCurrent._imghit1.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"HitsMiss.png").getObject()));
 //BA.debugLineNum = 177;BA.debugLine="ImgShot3.Visible = False";
mostCurrent._imgshot3.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 178;BA.debugLine="SoundBotShot.Play";
_soundbotshot.Play();
 };
 //BA.debugLineNum = 180;BA.debugLine="If clickmiss > 3 Then";
if (_clickmiss>3) { 
 //BA.debugLineNum = 181;BA.debugLine="SoundNoBullets.Play";
_soundnobullets.Play();
 };
 //BA.debugLineNum = 183;BA.debugLine="End Sub";
return "";
}
public static String  _misspanel2_click() throws Exception{
 //BA.debugLineNum = 184;BA.debugLine="Private Sub MissPanel2_Click";
 //BA.debugLineNum = 185;BA.debugLine="clickmiss = clickmiss + 1";
_clickmiss = (int) (_clickmiss+1);
 //BA.debugLineNum = 186;BA.debugLine="If clickmiss = 1 And clickShot < 1 Then";
if (_clickmiss==1 && _clickshot<1) { 
 //BA.debugLineNum = 187;BA.debugLine="ImgShot1.Visible = False";
mostCurrent._imgshot1.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 188;BA.debugLine="SoundBotShot.Play";
_soundbotshot.Play();
 };
 //BA.debugLineNum = 190;BA.debugLine="If clickmiss = 2 And clickShot < 1 Then";
if (_clickmiss==2 && _clickshot<1) { 
 //BA.debugLineNum = 191;BA.debugLine="ImgShot2.Visible = False";
mostCurrent._imgshot2.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 192;BA.debugLine="SoundBotShot.Play";
_soundbotshot.Play();
 };
 //BA.debugLineNum = 194;BA.debugLine="If clickmiss = 3 And clickShot < 1 Then";
if (_clickmiss==3 && _clickshot<1) { 
 //BA.debugLineNum = 195;BA.debugLine="ImgHit1.Bitmap = LoadBitmap(File.DirAssets, \"Hit";
mostCurrent._imghit1.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"HitsMiss.png").getObject()));
 //BA.debugLineNum = 196;BA.debugLine="ImgShot3.Visible = False";
mostCurrent._imgshot3.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 197;BA.debugLine="SoundBotShot.Play";
_soundbotshot.Play();
 };
 //BA.debugLineNum = 199;BA.debugLine="If clickmiss > 3 Then";
if (_clickmiss>3) { 
 //BA.debugLineNum = 200;BA.debugLine="SoundNoBullets.Play";
_soundnobullets.Play();
 };
 //BA.debugLineNum = 202;BA.debugLine="End Sub";
return "";
}
public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 6;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 11;BA.debugLine="Dim TimerBot1 As Timer";
_timerbot1 = new anywheresoftware.b4a.objects.Timer();
 //BA.debugLineNum = 12;BA.debugLine="Dim TimerBotHit As Timer";
_timerbothit = new anywheresoftware.b4a.objects.Timer();
 //BA.debugLineNum = 13;BA.debugLine="Dim TimerBotHitStop As Timer";
_timerbothitstop = new anywheresoftware.b4a.objects.Timer();
 //BA.debugLineNum = 14;BA.debugLine="Dim TimerBotMiss As Timer";
_timerbotmiss = new anywheresoftware.b4a.objects.Timer();
 //BA.debugLineNum = 15;BA.debugLine="Dim TimerBotMissDown As Timer";
_timerbotmissdown = new anywheresoftware.b4a.objects.Timer();
 //BA.debugLineNum = 16;BA.debugLine="Dim TimerBotMissRest As Timer";
_timerbotmissrest = new anywheresoftware.b4a.objects.Timer();
 //BA.debugLineNum = 17;BA.debugLine="Dim TimerBotMissStop As Timer";
_timerbotmissstop = new anywheresoftware.b4a.objects.Timer();
 //BA.debugLineNum = 18;BA.debugLine="Dim TimerBotMissDownStop As Timer";
_timerbotmissdownstop = new anywheresoftware.b4a.objects.Timer();
 //BA.debugLineNum = 19;BA.debugLine="Dim TimerBotMissRestStop As Timer";
_timerbotmissreststop = new anywheresoftware.b4a.objects.Timer();
 //BA.debugLineNum = 21;BA.debugLine="Dim SoundShotWin As MediaPlayer";
_soundshotwin = new anywheresoftware.b4a.objects.MediaPlayerWrapper();
 //BA.debugLineNum = 22;BA.debugLine="Dim SoundMiss As MediaPlayer";
_soundmiss = new anywheresoftware.b4a.objects.MediaPlayerWrapper();
 //BA.debugLineNum = 23;BA.debugLine="Dim SoundBotShot As MediaPlayer";
_soundbotshot = new anywheresoftware.b4a.objects.MediaPlayerWrapper();
 //BA.debugLineNum = 24;BA.debugLine="Dim SoundStageClear As MediaPlayer";
_soundstageclear = new anywheresoftware.b4a.objects.MediaPlayerWrapper();
 //BA.debugLineNum = 25;BA.debugLine="Dim soundLevelClear As MediaPlayer";
_soundlevelclear = new anywheresoftware.b4a.objects.MediaPlayerWrapper();
 //BA.debugLineNum = 26;BA.debugLine="Dim SoundDefeat As MediaPlayer";
_sounddefeat = new anywheresoftware.b4a.objects.MediaPlayerWrapper();
 //BA.debugLineNum = 27;BA.debugLine="Dim SoundPerfectScore As MediaPlayer";
_soundperfectscore = new anywheresoftware.b4a.objects.MediaPlayerWrapper();
 //BA.debugLineNum = 28;BA.debugLine="Dim SoundBotHurt As MediaPlayer";
_soundbothurt = new anywheresoftware.b4a.objects.MediaPlayerWrapper();
 //BA.debugLineNum = 29;BA.debugLine="Dim SoundBotFlying As MediaPlayer";
_soundbotflying = new anywheresoftware.b4a.objects.MediaPlayerWrapper();
 //BA.debugLineNum = 30;BA.debugLine="Dim SoundNoBullets As MediaPlayer";
_soundnobullets = new anywheresoftware.b4a.objects.MediaPlayerWrapper();
 //BA.debugLineNum = 31;BA.debugLine="End Sub";
return "";
}
public static String  _timerbot1_tick() throws Exception{
 //BA.debugLineNum = 205;BA.debugLine="Sub TimerBot1_Tick";
 //BA.debugLineNum = 206;BA.debugLine="SoundBotFlying.Play";
_soundbotflying.Play();
 //BA.debugLineNum = 207;BA.debugLine="ImgBot.Enabled = True";
mostCurrent._imgbot.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 208;BA.debugLine="ImgBot.Visible = True";
mostCurrent._imgbot.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 209;BA.debugLine="ImgBot.Top = ImgBot.Top - 5dip";
mostCurrent._imgbot.setTop((int) (mostCurrent._imgbot.getTop()-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))));
 //BA.debugLineNum = 210;BA.debugLine="ImgBot.Left = ImgBot.Left + 5dip";
mostCurrent._imgbot.setLeft((int) (mostCurrent._imgbot.getLeft()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))));
 //BA.debugLineNum = 211;BA.debugLine="If clickShot = 1 And clickmiss < 3 Then";
if (_clickshot==1 && _clickmiss<3) { 
 //BA.debugLineNum = 212;BA.debugLine="TimerBot1.Enabled = False";
_timerbot1.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 213;BA.debugLine="SoundBotFlying.Stop";
_soundbotflying.Stop();
 };
 //BA.debugLineNum = 215;BA.debugLine="If ImgBot.Top = -170dip Or ImgBot.Left = 720dip O";
if (mostCurrent._imgbot.getTop()==-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (170)) || mostCurrent._imgbot.getLeft()==anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (720)) || mostCurrent._imgbot.getLeft()==-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (150))) { 
 //BA.debugLineNum = 216;BA.debugLine="ImgBot.Visible = False";
mostCurrent._imgbot.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 217;BA.debugLine="ImgBot.Enabled = False";
mostCurrent._imgbot.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 218;BA.debugLine="ImgBot.Left = 290dip";
mostCurrent._imgbot.setLeft(anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (290)));
 //BA.debugLineNum = 219;BA.debugLine="ImgBot.Top = 350dip";
mostCurrent._imgbot.setTop(anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (350)));
 //BA.debugLineNum = 220;BA.debugLine="TimerBot1.Enabled = False";
_timerbot1.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 221;BA.debugLine="TimerBotMiss.Enabled = True";
_timerbotmiss.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 222;BA.debugLine="TimerBotMissStop.Enabled = True";
_timerbotmissstop.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 223;BA.debugLine="ImgHit1.Bitmap = LoadBitmap(File.DirAssets, \"Hit";
mostCurrent._imghit1.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"HitsMiss.png").getObject()));
 };
 //BA.debugLineNum = 225;BA.debugLine="End Sub";
return "";
}
public static String  _timerbothit_tick() throws Exception{
 //BA.debugLineNum = 261;BA.debugLine="Sub TimerBotHit_Tick";
 //BA.debugLineNum = 262;BA.debugLine="ImgBot.Visible = True";
mostCurrent._imgbot.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 263;BA.debugLine="botRND = Rnd(1,3)";
_botrnd = anywheresoftware.b4a.keywords.Common.Rnd((int) (1),(int) (3));
 //BA.debugLineNum = 264;BA.debugLine="If botRND = 1 Then";
if (_botrnd==1) { 
 //BA.debugLineNum = 265;BA.debugLine="ImgBot.Bitmap = LoadBitmap(File.DirAssets, \"BotH";
mostCurrent._imgbot.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"BotHit2.png").getObject()));
 };
 //BA.debugLineNum = 267;BA.debugLine="If botRND = 2 Then";
if (_botrnd==2) { 
 //BA.debugLineNum = 268;BA.debugLine="ImgBot.Bitmap = LoadBitmap(File.DirAssets, \"BotH";
mostCurrent._imgbot.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"BotHit.png").getObject()));
 };
 //BA.debugLineNum = 270;BA.debugLine="End Sub";
return "";
}
public static String  _timerbothitstop_tick() throws Exception{
 //BA.debugLineNum = 272;BA.debugLine="Sub TimerBotHitStop_Tick";
 //BA.debugLineNum = 273;BA.debugLine="TimerBotHit.Enabled = False";
_timerbothit.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 274;BA.debugLine="TimerBotHitStop.Enabled = False";
_timerbothitstop.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 275;BA.debugLine="ImgBot.Visible = False";
mostCurrent._imgbot.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 276;BA.debugLine="End Sub";
return "";
}
public static String  _timerbotmiss_tick() throws Exception{
 //BA.debugLineNum = 228;BA.debugLine="Sub TimerBotMiss_Tick";
 //BA.debugLineNum = 229;BA.debugLine="ImgBot.Visible = True";
mostCurrent._imgbot.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 230;BA.debugLine="ImgBot.Enabled = False";
mostCurrent._imgbot.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 231;BA.debugLine="ImgBot.Bitmap = LoadBitmap(File.DirAssets, \"gamep";
mostCurrent._imgbot.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"gameplayBot.png").getObject()));
 //BA.debugLineNum = 232;BA.debugLine="ImgBot.Top = ImgBot.Top - 7dip";
mostCurrent._imgbot.setTop((int) (mostCurrent._imgbot.getTop()-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (7))));
 //BA.debugLineNum = 233;BA.debugLine="End Sub";
return "";
}
public static String  _timerbotmissdown_tick() throws Exception{
 //BA.debugLineNum = 237;BA.debugLine="Sub TimerBotMissDown_Tick";
 //BA.debugLineNum = 238;BA.debugLine="ImgBot.Top = ImgBot.Top + 8dip";
mostCurrent._imgbot.setTop((int) (mostCurrent._imgbot.getTop()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (8))));
 //BA.debugLineNum = 239;BA.debugLine="End Sub";
return "";
}
public static String  _timerbotmissdownstop_tick() throws Exception{
 //BA.debugLineNum = 254;BA.debugLine="Sub TimerBotMissDownStop_tick";
 //BA.debugLineNum = 256;BA.debugLine="TimerBotMissDown.Enabled = False";
_timerbotmissdown.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 257;BA.debugLine="TimerBotMissDownStop.Enabled = False";
_timerbotmissdownstop.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 258;BA.debugLine="End Sub";
return "";
}
public static String  _timerbotmissrest_tick() throws Exception{
 //BA.debugLineNum = 234;BA.debugLine="Sub TimerBotMissRest_tick";
 //BA.debugLineNum = 235;BA.debugLine="ImgBot.Top = ImgBot.Top";
mostCurrent._imgbot.setTop(mostCurrent._imgbot.getTop());
 //BA.debugLineNum = 236;BA.debugLine="End Sub";
return "";
}
public static String  _timerbotmissreststop_tick() throws Exception{
 //BA.debugLineNum = 248;BA.debugLine="Sub TimerBotMissRestStop_tick";
 //BA.debugLineNum = 249;BA.debugLine="TimerBotMissRest.Enabled = False";
_timerbotmissrest.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 250;BA.debugLine="TimerBotMissRestStop.Enabled = False";
_timerbotmissreststop.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 251;BA.debugLine="TimerBotMissDown.Enabled = True";
_timerbotmissdown.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 252;BA.debugLine="TimerBotMissDownStop.Enabled = True";
_timerbotmissdownstop.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 253;BA.debugLine="End Sub";
return "";
}
public static String  _timerbotmissstop_tick() throws Exception{
 //BA.debugLineNum = 241;BA.debugLine="Sub TimerBotMissStop_Tick";
 //BA.debugLineNum = 242;BA.debugLine="SoundMiss.Play";
_soundmiss.Play();
 //BA.debugLineNum = 243;BA.debugLine="TimerBotMiss.Enabled = False";
_timerbotmiss.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 244;BA.debugLine="TimerBotMissStop.Enabled = False";
_timerbotmissstop.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 245;BA.debugLine="TimerBotMissRest.Enabled = True";
_timerbotmissrest.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 246;BA.debugLine="TimerBotMissRestStop.Enabled = True";
_timerbotmissreststop.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 247;BA.debugLine="End Sub";
return "";
}
}
