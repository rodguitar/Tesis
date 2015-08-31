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
 //BA.debugLineNum = 10;BA.debugLine="Mi_BD.Initialize(File.DirInternal,\"DBApp.db\",True)' Se crea el archivo en el directorio interno del Smartphone";
_mi_bd.Initialize(anywheresoftware.b4a.keywords.Common.File.getDirInternal(),"DBApp.db",anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 12;BA.debugLine="Dim sSql As String";
_ssql = "";
 //BA.debugLineNum = 14;BA.debugLine="Try";
try { //BA.debugLineNum = 15;BA.debugLine="sSql = \"CREATE TABLE IF NOT EXISTS REVIEW \"";
_ssql = "CREATE TABLE IF NOT EXISTS REVIEW ";
 //BA.debugLineNum = 16;BA.debugLine="sSql = sSql & \"(id_review INTEGER PRIMARY KEY AUTOINCREMENT, \"";
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
 //BA.debugLineNum = 22;BA.debugLine="sSql = sSql & \"fecha_registro TIMESTAMP DEFAULT CURRENT_TIMESTAMP)\"";
_ssql = _ssql+"fecha_registro TIMESTAMP DEFAULT CURRENT_TIMESTAMP)";
 //BA.debugLineNum = 23;BA.debugLine="Mi_BD.ExecNonQuery(sSql)";
_mi_bd.ExecNonQuery(_ssql);
 } 
       catch (Exception e17) {
			(_ba.processBA == null ? _ba : _ba.processBA).setLastException(e17); //BA.debugLineNum = 25;BA.debugLine="Log(\"catch ERROR CREATE TABLE IF NOT EXISTS REVIEW: \"&LastException.Message)";
anywheresoftware.b4a.keywords.Common.Log("catch ERROR CREATE TABLE IF NOT EXISTS REVIEW: "+anywheresoftware.b4a.keywords.Common.LastException(_ba).getMessage());
 };
 //BA.debugLineNum = 27;BA.debugLine="End Sub";
return "";
}
public static String  _editareview(anywheresoftware.b4a.BA _ba,String _titulo,String _id_review) throws Exception{
 //BA.debugLineNum = 45;BA.debugLine="Sub EditaReview(titulo As String, id_review As String)";
 //BA.debugLineNum = 46;BA.debugLine="Try";
try { //BA.debugLineNum = 47;BA.debugLine="Mi_BD.ExecNonQuery(\"UPDATE REVIEW SET titulo='\"& titulo &\"' WHERE id_review='\"& id_review &\"'\")";
_mi_bd.ExecNonQuery("UPDATE REVIEW SET titulo='"+_titulo+"' WHERE id_review='"+_id_review+"'");
 //BA.debugLineNum = 48;BA.debugLine="Log(\"OK UPDATE REVIEW Realizado.\")";
anywheresoftware.b4a.keywords.Common.Log("OK UPDATE REVIEW Realizado.");
 } 
       catch (Exception e38) {
			(_ba.processBA == null ? _ba : _ba.processBA).setLastException(e38); //BA.debugLineNum = 51;BA.debugLine="Log(\"catch UPDATE REVIEW DBApp   \" & LastException.Message)";
anywheresoftware.b4a.keywords.Common.Log("catch UPDATE REVIEW DBApp   "+anywheresoftware.b4a.keywords.Common.LastException(_ba).getMessage());
 };
 //BA.debugLineNum = 54;BA.debugLine="End Sub";
return "";
}
public static String  _eliminareview(anywheresoftware.b4a.BA _ba,String _id_review) throws Exception{
 //BA.debugLineNum = 56;BA.debugLine="Sub EliminaReview(id_review As String)";
 //BA.debugLineNum = 58;BA.debugLine="Try";
try { //BA.debugLineNum = 59;BA.debugLine="Mi_BD.ExecNonQuery(\"DELETE from REVIEW where id_review= '\"& id_review &\"' \")";
_mi_bd.ExecNonQuery("DELETE from REVIEW where id_review= '"+_id_review+"' ");
 //BA.debugLineNum = 60;BA.debugLine="Log(\"OK DELETE from REVIEW Realizado.\")";
anywheresoftware.b4a.keywords.Common.Log("OK DELETE from REVIEW Realizado.");
 } 
       catch (Exception e46) {
			(_ba.processBA == null ? _ba : _ba.processBA).setLastException(e46); //BA.debugLineNum = 63;BA.debugLine="Log(\"catch DELETE from REVIEW DBApp   \" & LastException.Message)";
anywheresoftware.b4a.keywords.Common.Log("catch DELETE from REVIEW DBApp   "+anywheresoftware.b4a.keywords.Common.LastException(_ba).getMessage());
 };
 //BA.debugLineNum = 66;BA.debugLine="End Sub";
return "";
}
public static String  _insertareview(anywheresoftware.b4a.BA _ba,String _titulo,String _nota,String _tipo,String _comentario_texto,String _nombre_voz) throws Exception{
String _fecha = "";
 //BA.debugLineNum = 30;BA.debugLine="Sub InsertaReview(titulo As String, nota As String, tipo As String, comentario_texto As String,nombre_voz As String)";
 //BA.debugLineNum = 31;BA.debugLine="Dim fecha As String";
_fecha = "";
 //BA.debugLineNum = 32;BA.debugLine="Try";
try { //BA.debugLineNum = 33;BA.debugLine="fecha=Mi_BD.ExecQuerySingleResult(\"SELECT datetime(CURRENT_TIMESTAMP,'localtime')\")";
_fecha = _mi_bd.ExecQuerySingleResult("SELECT datetime(CURRENT_TIMESTAMP,'localtime')");
 } 
       catch (Exception e25) {
			(_ba.processBA == null ? _ba : _ba.processBA).setLastException(e25); //BA.debugLineNum = 35;BA.debugLine="Log(\"catch ERROR INSERT INTO REVIEW: \"&LastException.Message)";
anywheresoftware.b4a.keywords.Common.Log("catch ERROR INSERT INTO REVIEW: "+anywheresoftware.b4a.keywords.Common.LastException(_ba).getMessage());
 };
 //BA.debugLineNum = 38;BA.debugLine="Try";
try { //BA.debugLineNum = 39;BA.debugLine="Mi_BD.ExecNonQuery2(\"INSERT INTO REVIEW (titulo,nota,tipo,comentario_texto,nombre_voz,fecha_registro) VALUES (?,?,?,?,?,'\"&fecha&\"')\", Array As Object(titulo,nota,tipo,comentario_texto,nombre_voz))";
_mi_bd.ExecNonQuery2("INSERT INTO REVIEW (titulo,nota,tipo,comentario_texto,nombre_voz,fecha_registro) VALUES (?,?,?,?,?,'"+_fecha+"')",anywheresoftware.b4a.keywords.Common.ArrayToList(new Object[]{(Object)(_titulo),(Object)(_nota),(Object)(_tipo),(Object)(_comentario_texto),(Object)(_nombre_voz)}));
 } 
       catch (Exception e30) {
			(_ba.processBA == null ? _ba : _ba.processBA).setLastException(e30); //BA.debugLineNum = 41;BA.debugLine="Log(\"catch ERROR INSERT INTO REVIEW: \"&LastException.Message)";
anywheresoftware.b4a.keywords.Common.Log("catch ERROR INSERT INTO REVIEW: "+anywheresoftware.b4a.keywords.Common.LastException(_ba).getMessage());
 };
 //BA.debugLineNum = 43;BA.debugLine="End Sub";
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
