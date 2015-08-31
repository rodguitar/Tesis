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
public b4a.example.googlemaps _googlemaps = null;
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
 //BA.debugLineNum = 20;BA.debugLine="sSql = sSql & \"comentario_texto TEXT, \"";
_ssql = _ssql+"comentario_texto TEXT, ";
 //BA.debugLineNum = 21;BA.debugLine="sSql = sSql & \"nombre_voz TEXT, \"";
_ssql = _ssql+"nombre_voz TEXT, ";
 //BA.debugLineNum = 22;BA.debugLine="sSql = sSql & \"latitud TEXT, \"";
_ssql = _ssql+"latitud TEXT, ";
 //BA.debugLineNum = 23;BA.debugLine="sSql = sSql & \"longitud TEXT, \"";
_ssql = _ssql+"longitud TEXT, ";
 //BA.debugLineNum = 24;BA.debugLine="sSql = sSql & \"fecha_registro TIMESTAMP DEFAULT";
_ssql = _ssql+"fecha_registro TIMESTAMP DEFAULT CURRENT_TIMESTAMP)";
 //BA.debugLineNum = 25;BA.debugLine="Mi_BD.ExecNonQuery(sSql)";
_mi_bd.ExecNonQuery(_ssql);
 } 
       catch (Exception e19) {
			(_ba.processBA == null ? _ba : _ba.processBA).setLastException(e19); //BA.debugLineNum = 27;BA.debugLine="Log(\"catch ERROR CREATE TABLE IF NOT EXISTS REVI";
anywheresoftware.b4a.keywords.Common.Log("catch ERROR CREATE TABLE IF NOT EXISTS REVIEW: "+anywheresoftware.b4a.keywords.Common.LastException(_ba).getMessage());
 };
 //BA.debugLineNum = 29;BA.debugLine="End Sub";
return "";
}
public static String  _editareview(anywheresoftware.b4a.BA _ba,String _titulo,String _id_review) throws Exception{
 //BA.debugLineNum = 47;BA.debugLine="Sub EditaReview(titulo As String, id_review As Str";
 //BA.debugLineNum = 48;BA.debugLine="Try";
try { //BA.debugLineNum = 49;BA.debugLine="Mi_BD.ExecNonQuery(\"UPDATE REVIEW SET titulo='\"&";
_mi_bd.ExecNonQuery("UPDATE REVIEW SET titulo='"+_titulo+"' WHERE id_review='"+_id_review+"'");
 //BA.debugLineNum = 50;BA.debugLine="Log(\"OK UPDATE REVIEW Realizado.\")";
anywheresoftware.b4a.keywords.Common.Log("OK UPDATE REVIEW Realizado.");
 } 
       catch (Exception e40) {
			(_ba.processBA == null ? _ba : _ba.processBA).setLastException(e40); //BA.debugLineNum = 53;BA.debugLine="Log(\"catch UPDATE REVIEW DBApp   \" & LastExcepti";
anywheresoftware.b4a.keywords.Common.Log("catch UPDATE REVIEW DBApp   "+anywheresoftware.b4a.keywords.Common.LastException(_ba).getMessage());
 };
 //BA.debugLineNum = 56;BA.debugLine="End Sub";
return "";
}
public static String  _eliminareview(anywheresoftware.b4a.BA _ba,String _id_review) throws Exception{
 //BA.debugLineNum = 58;BA.debugLine="Sub EliminaReview(id_review As String)";
 //BA.debugLineNum = 60;BA.debugLine="Try";
try { //BA.debugLineNum = 61;BA.debugLine="Mi_BD.ExecNonQuery(\"DELETE from REVIEW where id_";
_mi_bd.ExecNonQuery("DELETE from REVIEW where id_review= '"+_id_review+"' ");
 //BA.debugLineNum = 62;BA.debugLine="Log(\"OK DELETE from REVIEW Realizado.\")";
anywheresoftware.b4a.keywords.Common.Log("OK DELETE from REVIEW Realizado.");
 } 
       catch (Exception e48) {
			(_ba.processBA == null ? _ba : _ba.processBA).setLastException(e48); //BA.debugLineNum = 65;BA.debugLine="Log(\"catch DELETE from REVIEW DBApp   \" & LastEx";
anywheresoftware.b4a.keywords.Common.Log("catch DELETE from REVIEW DBApp   "+anywheresoftware.b4a.keywords.Common.LastException(_ba).getMessage());
 };
 //BA.debugLineNum = 68;BA.debugLine="End Sub";
return "";
}
public static String  _insertareview(anywheresoftware.b4a.BA _ba,String _titulo,String _nota,String _tipo,String _comentario_texto,String _nombre_voz,double _latitud,double _longitud) throws Exception{
String _fecha = "";
 //BA.debugLineNum = 32;BA.debugLine="Sub InsertaReview(titulo As String, nota As String";
 //BA.debugLineNum = 33;BA.debugLine="Dim fecha As String";
_fecha = "";
 //BA.debugLineNum = 34;BA.debugLine="Try";
try { //BA.debugLineNum = 35;BA.debugLine="fecha=Mi_BD.ExecQuerySingleResult(\"SELECT dateti";
_fecha = _mi_bd.ExecQuerySingleResult("SELECT datetime(CURRENT_TIMESTAMP,'localtime')");
 } 
       catch (Exception e27) {
			(_ba.processBA == null ? _ba : _ba.processBA).setLastException(e27); //BA.debugLineNum = 37;BA.debugLine="Log(\"catch ERROR INSERT INTO REVIEW: \"&LastExcep";
anywheresoftware.b4a.keywords.Common.Log("catch ERROR INSERT INTO REVIEW: "+anywheresoftware.b4a.keywords.Common.LastException(_ba).getMessage());
 };
 //BA.debugLineNum = 40;BA.debugLine="Try";
try { //BA.debugLineNum = 41;BA.debugLine="Mi_BD.ExecNonQuery2(\"INSERT INTO REVIEW (titulo,";
_mi_bd.ExecNonQuery2("INSERT INTO REVIEW (titulo,nota,tipo,comentario_texto,nombre_voz,latitud,longitud,fecha_registro) VALUES (?,?,?,?,?,?,?,'"+_fecha+"')",anywheresoftware.b4a.keywords.Common.ArrayToList(new Object[]{(Object)(_titulo),(Object)(_nota),(Object)(_tipo),(Object)(_comentario_texto),(Object)(_nombre_voz),(Object)(_latitud),(Object)(_longitud)}));
 } 
       catch (Exception e32) {
			(_ba.processBA == null ? _ba : _ba.processBA).setLastException(e32); //BA.debugLineNum = 43;BA.debugLine="Log(\"catch ERROR INSERT INTO REVIEW: \"&LastExcep";
anywheresoftware.b4a.keywords.Common.Log("catch ERROR INSERT INTO REVIEW: "+anywheresoftware.b4a.keywords.Common.LastException(_ba).getMessage());
 };
 //BA.debugLineNum = 45;BA.debugLine="End Sub";
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
