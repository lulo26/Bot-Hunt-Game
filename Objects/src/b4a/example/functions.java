package b4a.example;


import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.BALayout;
import anywheresoftware.b4a.debug.*;

public class functions {
private static functions mostCurrent = new functions();
public static Object getObject() {
    throw new RuntimeException("Code module does not support this method.");
}
 public anywheresoftware.b4a.keywords.Common __c = null;
public static String _player = "";
public static boolean _spanish = false;
public static boolean _level2pass = false;
public static boolean _level3pass = false;
public static String _levels = "";
public static long _score = 0L;
public b4a.example.dateutils _dateutils = null;
public b4a.example.main _main = null;
public b4a.example.starter _starter = null;
public b4a.example.frmtitlescreen _frmtitlescreen = null;
public b4a.example.frmregister _frmregister = null;
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
public static boolean  _func_confirmuser(anywheresoftware.b4a.BA _ba,String _user,String _pass) throws Exception{
anywheresoftware.b4a.objects.collections.List _listuser = null;
boolean _found = false;
Object _line = null;
String _value = "";
String[] _arrayfind = null;
 //BA.debugLineNum = 31;BA.debugLine="Public Sub Func_ConfirmUser (user As String, pass";
 //BA.debugLineNum = 32;BA.debugLine="Dim ListUser As List";
_listuser = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 33;BA.debugLine="Dim found As Boolean = False";
_found = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 34;BA.debugLine="If File.Exists(File.DirInternal, \"GameData.txt\")";
if (anywheresoftware.b4a.keywords.Common.File.Exists(anywheresoftware.b4a.keywords.Common.File.getDirInternal(),"GameData.txt")) { 
 //BA.debugLineNum = 35;BA.debugLine="ListUser = File.ReadList(File.DirInternal, \"Game";
_listuser = anywheresoftware.b4a.keywords.Common.File.ReadList(anywheresoftware.b4a.keywords.Common.File.getDirInternal(),"GameData.txt");
 //BA.debugLineNum = 36;BA.debugLine="For Each line In ListUser";
{
final anywheresoftware.b4a.BA.IterableList group5 = _listuser;
final int groupLen5 = group5.getSize()
;int index5 = 0;
;
for (; index5 < groupLen5;index5++){
_line = group5.Get(index5);
 //BA.debugLineNum = 37;BA.debugLine="Dim value As String = line";
_value = BA.ObjectToString(_line);
 //BA.debugLineNum = 38;BA.debugLine="Dim arrayFind() As String = Regex.Split(\",\",val";
_arrayfind = anywheresoftware.b4a.keywords.Common.Regex.Split(",",_value);
 //BA.debugLineNum = 39;BA.debugLine="If arrayFind(0) = user And arrayFind(1) = pass";
if ((_arrayfind[(int) (0)]).equals(_user) && (_arrayfind[(int) (1)]).equals(_pass)) { 
 //BA.debugLineNum = 40;BA.debugLine="found = True";
_found = anywheresoftware.b4a.keywords.Common.True;
 };
 }
};
 };
 //BA.debugLineNum = 44;BA.debugLine="Return found";
if (true) return _found;
 //BA.debugLineNum = 45;BA.debugLine="End Sub";
return false;
}
public static int  _func_searchscore(anywheresoftware.b4a.BA _ba,String _user) throws Exception{
anywheresoftware.b4a.objects.collections.List _listuser = null;
long _scores = 0L;
Object _line = null;
String _value = "";
String[] _arrayfind = null;
 //BA.debugLineNum = 47;BA.debugLine="Public Sub Func_SearchScore (user As String) As In";
 //BA.debugLineNum = 48;BA.debugLine="Dim ListUser As List";
_listuser = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 49;BA.debugLine="Dim scores As Long";
_scores = 0L;
 //BA.debugLineNum = 50;BA.debugLine="If File.Exists(File.DirInternal, \"GameData.txt\")";
if (anywheresoftware.b4a.keywords.Common.File.Exists(anywheresoftware.b4a.keywords.Common.File.getDirInternal(),"GameData.txt")) { 
 //BA.debugLineNum = 51;BA.debugLine="ListUser = File.ReadList(File.DirInternal, \"Game";
_listuser = anywheresoftware.b4a.keywords.Common.File.ReadList(anywheresoftware.b4a.keywords.Common.File.getDirInternal(),"GameData.txt");
 //BA.debugLineNum = 52;BA.debugLine="For Each line In ListUser";
{
final anywheresoftware.b4a.BA.IterableList group5 = _listuser;
final int groupLen5 = group5.getSize()
;int index5 = 0;
;
for (; index5 < groupLen5;index5++){
_line = group5.Get(index5);
 //BA.debugLineNum = 53;BA.debugLine="Dim value As String = line";
_value = BA.ObjectToString(_line);
 //BA.debugLineNum = 54;BA.debugLine="Dim arrayFind() As String = Regex.Split(\",\",val";
_arrayfind = anywheresoftware.b4a.keywords.Common.Regex.Split(",",_value);
 //BA.debugLineNum = 55;BA.debugLine="If arrayFind(0) = user Then";
if ((_arrayfind[(int) (0)]).equals(_user)) { 
 //BA.debugLineNum = 56;BA.debugLine="scores = arrayFind(4)";
_scores = (long)(Double.parseDouble(_arrayfind[(int) (4)]));
 };
 }
};
 };
 //BA.debugLineNum = 60;BA.debugLine="Return scores";
if (true) return (int) (_scores);
 //BA.debugLineNum = 61;BA.debugLine="End Sub";
return 0;
}
public static boolean  _func_searchuser(anywheresoftware.b4a.BA _ba,String _user) throws Exception{
anywheresoftware.b4a.objects.collections.List _listuser = null;
boolean _found = false;
Object _line = null;
String _value = "";
String[] _arrayfind = null;
 //BA.debugLineNum = 15;BA.debugLine="Public Sub Func_SearchUser (user As String) As Boo";
 //BA.debugLineNum = 16;BA.debugLine="Dim ListUser As List";
_listuser = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 17;BA.debugLine="Dim found As Boolean = False";
_found = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 18;BA.debugLine="If File.Exists(File.DirInternal, \"GameData.txt\")";
if (anywheresoftware.b4a.keywords.Common.File.Exists(anywheresoftware.b4a.keywords.Common.File.getDirInternal(),"GameData.txt")) { 
 //BA.debugLineNum = 19;BA.debugLine="ListUser = File.ReadList(File.DirInternal, \"Game";
_listuser = anywheresoftware.b4a.keywords.Common.File.ReadList(anywheresoftware.b4a.keywords.Common.File.getDirInternal(),"GameData.txt");
 //BA.debugLineNum = 20;BA.debugLine="For Each line In ListUser";
{
final anywheresoftware.b4a.BA.IterableList group5 = _listuser;
final int groupLen5 = group5.getSize()
;int index5 = 0;
;
for (; index5 < groupLen5;index5++){
_line = group5.Get(index5);
 //BA.debugLineNum = 21;BA.debugLine="Dim value As String = line";
_value = BA.ObjectToString(_line);
 //BA.debugLineNum = 22;BA.debugLine="Dim arrayFind() As String = Regex.Split(\",\",val";
_arrayfind = anywheresoftware.b4a.keywords.Common.Regex.Split(",",_value);
 //BA.debugLineNum = 23;BA.debugLine="If arrayFind(0) = user Then";
if ((_arrayfind[(int) (0)]).equals(_user)) { 
 //BA.debugLineNum = 24;BA.debugLine="found = True";
_found = anywheresoftware.b4a.keywords.Common.True;
 };
 }
};
 };
 //BA.debugLineNum = 28;BA.debugLine="Return found";
if (true) return _found;
 //BA.debugLineNum = 29;BA.debugLine="End Sub";
return false;
}
public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 3;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 6;BA.debugLine="Dim player As String = \"\"";
_player = "";
 //BA.debugLineNum = 7;BA.debugLine="Dim Spanish As Boolean = False";
_spanish = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 8;BA.debugLine="Dim Level2Pass As Boolean = False";
_level2pass = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 9;BA.debugLine="Dim Level3Pass As Boolean = False";
_level3pass = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 10;BA.debugLine="Dim Levels As String = \"\"";
_levels = "";
 //BA.debugLineNum = 11;BA.debugLine="Dim score As Long = 0";
_score = (long) (0);
 //BA.debugLineNum = 13;BA.debugLine="End Sub";
return "";
}
}
