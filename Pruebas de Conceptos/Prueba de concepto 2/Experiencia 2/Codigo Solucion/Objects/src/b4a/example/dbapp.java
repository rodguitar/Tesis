package b4a.example;


import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.BALayout;
import anywheresoftware.b4a.debug.*;

public class dbapp {
private static dbapp mostCurrent = new dbapp();
public static Object getObject() {
    throw new RuntimeException("Code module does not support this method.");
}
 public anywheresoftware.b4a.keywords.Common __c = null;
public static anywheresoftware.b4a.sql.SQL _mi_bd = null;
public b4a.example.main _main = null;
public b4a.example.charts _charts = null;
public static String  _creabd(anywheresoftware.b4a.BA _ba) throws Exception{
String _ssql = "";
 //BA.debugLineNum = 9;BA.debugLine="Sub CreaBD";
 //BA.debugLineNum = 10;BA.debugLine="Mi_BD.Initialize(File.DirInternal,\"DBApp.db\",True";
_mi_bd.Initialize(anywheresoftware.b4a.keywords.Common.File.getDirInternal(),"DBApp.db",anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 12;BA.debugLine="Dim sSql As String";
_ssql = "";
 //BA.debugLineNum = 14;BA.debugLine="Try";
try { //BA.debugLineNum = 15;BA.debugLine="sSql = \"CREATE TABLE IF NOT EXISTS REVIEW \"";
_ssql = "CREATE TABLE IF NOT EXISTS REVIEW ";
 //BA.debugLineNum = 16;BA.debugLine="sSql = sSql & \"(id_review INTEGER PRIMARY KEY AU";
_ssql = _ssql+"(id_review INTEGER PRIMARY KEY AUTOINCREMENT, ";
 //BA.debugLineNum = 17;BA.debugLine="sSql = sSql & \"titulo TEXT, \"";
_ssql = _ssql+"titulo TEXT, ";
 //BA.debugLineNum = 18;BA.debugLine="sSql = sSql & \"nota TEXT, \"";
_ssql = _ssql+"nota TEXT, ";
 //BA.debugLineNum = 19;BA.debugLine="sSql = sSql & \"tipo TEXT, \"";
_ssql = _ssql+"tipo TEXT, ";
 //BA.debugLineNum = 20;BA.debugLine="sSql = sSql & \"fecha_registro TIMESTAMP DEFAULT";
_ssql = _ssql+"fecha_registro TIMESTAMP DEFAULT CURRENT_TIMESTAMP)";
 //BA.debugLineNum = 21;BA.debugLine="Mi_BD.ExecNonQuery(sSql)";
_mi_bd.ExecNonQuery(_ssql);
 } 
       catch (Exception e15) {
			(_ba.processBA == null ? _ba : _ba.processBA).setLastException(e15); //BA.debugLineNum = 23;BA.debugLine="Log(\"catch ERROR CREATE TABLE IF NOT EXISTS REVI";
anywheresoftware.b4a.keywords.Common.Log("catch ERROR CREATE TABLE IF NOT EXISTS REVIEW: "+anywheresoftware.b4a.keywords.Common.LastException(_ba).getMessage());
 };
 //BA.debugLineNum = 25;BA.debugLine="End Sub";
return "";
}
public static String  _insertareview(anywheresoftware.b4a.BA _ba,String _titulo,String _nota,String _tipo) throws Exception{
String _fecha = "";
 //BA.debugLineNum = 28;BA.debugLine="Sub InsertaReview(titulo As String, nota As String";
 //BA.debugLineNum = 29;BA.debugLine="Dim fecha As String";
_fecha = "";
 //BA.debugLineNum = 30;BA.debugLine="Try";
try { //BA.debugLineNum = 31;BA.debugLine="fecha=Mi_BD.ExecQuerySingleResult(\"SELECT dateti";
_fecha = _mi_bd.ExecQuerySingleResult("SELECT datetime(CURRENT_TIMESTAMP,'localtime')");
 } 
       catch (Exception e23) {
			(_ba.processBA == null ? _ba : _ba.processBA).setLastException(e23); //BA.debugLineNum = 33;BA.debugLine="Log(\"catch ERROR INSERT INTO REVIEW: \"&LastExcep";
anywheresoftware.b4a.keywords.Common.Log("catch ERROR INSERT INTO REVIEW: "+anywheresoftware.b4a.keywords.Common.LastException(_ba).getMessage());
 };
 //BA.debugLineNum = 36;BA.debugLine="Try";
try { //BA.debugLineNum = 37;BA.debugLine="Mi_BD.ExecNonQuery2(\"INSERT INTO REVIEW (titulo,";
_mi_bd.ExecNonQuery2("INSERT INTO REVIEW (titulo,nota,tipo,fecha_registro) VALUES (?,?,?,'"+_fecha+"')",anywheresoftware.b4a.keywords.Common.ArrayToList(new Object[]{(Object)(_titulo),(Object)(_nota),(Object)(_tipo)}));
 } 
       catch (Exception e28) {
			(_ba.processBA == null ? _ba : _ba.processBA).setLastException(e28); //BA.debugLineNum = 39;BA.debugLine="Log(\"catch ERROR INSERT INTO REVIEW: \"&LastExcep";
anywheresoftware.b4a.keywords.Common.Log("catch ERROR INSERT INTO REVIEW: "+anywheresoftware.b4a.keywords.Common.LastException(_ba).getMessage());
 };
 //BA.debugLineNum = 41;BA.debugLine="End Sub";
return "";
}
public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 3;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 6;BA.debugLine="Dim Mi_BD As SQL";
_mi_bd = new anywheresoftware.b4a.sql.SQL();
 //BA.debugLineNum = 7;BA.debugLine="End Sub";
return "";
}
}
