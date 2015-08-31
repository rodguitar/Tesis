package b4a.example;

import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.BALayout;
import anywheresoftware.b4a.debug.*;

public class charts {
private static charts mostCurrent = new charts();
public static Object getObject() {
    throw new RuntimeException("Code module does not support this method.");
}
 public anywheresoftware.b4a.keywords.Common __c = null;
public b4a.example.main _main = null;
public b4a.example.dbapp _dbapp = null;
public static class _pieitem{
public boolean IsInitialized;
public String Name;
public float Value;
public int Color;
public void Initialize() {
IsInitialized = true;
Name = "";
Value = 0f;
Color = 0;
}
@Override
		public String toString() {
			return BA.TypeToString(this, false);
		}}
public static class _piedata{
public boolean IsInitialized;
public anywheresoftware.b4a.objects.collections.List Items;
public anywheresoftware.b4a.objects.PanelWrapper Target;
public anywheresoftware.b4a.objects.drawable.CanvasWrapper Canvas;
public int GapDegrees;
public float LegendTextSize;
public int LegendBackColor;
public void Initialize() {
IsInitialized = true;
Items = new anywheresoftware.b4a.objects.collections.List();
Target = new anywheresoftware.b4a.objects.PanelWrapper();
Canvas = new anywheresoftware.b4a.objects.drawable.CanvasWrapper();
GapDegrees = 0;
LegendTextSize = 0f;
LegendBackColor = 0;
}
@Override
		public String toString() {
			return BA.TypeToString(this, false);
		}}
public static class _graphinternal{
public boolean IsInitialized;
public int originX;
public int zeroY;
public int originY;
public int maxY;
public float intervalX;
public int gw;
public int gh;
public void Initialize() {
IsInitialized = true;
originX = 0;
zeroY = 0;
originY = 0;
maxY = 0;
intervalX = 0f;
gw = 0;
gh = 0;
}
@Override
		public String toString() {
			return BA.TypeToString(this, false);
		}}
public static class _graph{
public boolean IsInitialized;
public b4a.example.charts._graphinternal GI;
public String Title;
public String YAxis;
public String XAxis;
public float YStart;
public float YEnd;
public float YInterval;
public int AxisColor;
public int NbIntervals;
public void Initialize() {
IsInitialized = true;
GI = new b4a.example.charts._graphinternal();
Title = "";
YAxis = "";
XAxis = "";
YStart = 0f;
YEnd = 0f;
YInterval = 0f;
AxisColor = 0;
NbIntervals = 0;
}
@Override
		public String toString() {
			return BA.TypeToString(this, false);
		}}
public static class _linepoint{
public boolean IsInitialized;
public String X;
public float Y;
public float[] YArray;
public boolean ShowTick;
public void Initialize() {
IsInitialized = true;
X = "";
Y = 0f;
YArray = new float[0];
;
ShowTick = false;
}
@Override
		public String toString() {
			return BA.TypeToString(this, false);
		}}
public static class _linedata{
public boolean IsInitialized;
public anywheresoftware.b4a.objects.collections.List Points;
public anywheresoftware.b4a.objects.collections.List LinesColors;
public anywheresoftware.b4a.objects.PanelWrapper Target;
public anywheresoftware.b4a.objects.drawable.CanvasWrapper Canvas;
public boolean AutomaticScale;
public void Initialize() {
IsInitialized = true;
Points = new anywheresoftware.b4a.objects.collections.List();
LinesColors = new anywheresoftware.b4a.objects.collections.List();
Target = new anywheresoftware.b4a.objects.PanelWrapper();
Canvas = new anywheresoftware.b4a.objects.drawable.CanvasWrapper();
AutomaticScale = false;
}
@Override
		public String toString() {
			return BA.TypeToString(this, false);
		}}
public static class _bardata{
public boolean IsInitialized;
public anywheresoftware.b4a.objects.collections.List Points;
public anywheresoftware.b4a.objects.collections.List BarsColors;
public anywheresoftware.b4a.objects.PanelWrapper Target;
public anywheresoftware.b4a.objects.drawable.CanvasWrapper Canvas;
public boolean Stacked;
public int BarsWidth;
public void Initialize() {
IsInitialized = true;
Points = new anywheresoftware.b4a.objects.collections.List();
BarsColors = new anywheresoftware.b4a.objects.collections.List();
Target = new anywheresoftware.b4a.objects.PanelWrapper();
Canvas = new anywheresoftware.b4a.objects.drawable.CanvasWrapper();
Stacked = false;
BarsWidth = 0;
}
@Override
		public String toString() {
			return BA.TypeToString(this, false);
		}}
public static String  _addbarcolor(anywheresoftware.b4a.BA _ba,b4a.example.charts._bardata _bd,int _color) throws Exception{
 //BA.debugLineNum = 46;BA.debugLine="Sub AddBarColor(BD As BarData, Color As Int)";
 //BA.debugLineNum = 47;BA.debugLine="If BD.BarsColors.IsInitialized = False Then BD.BarsColors.Initialize";
if (_bd.BarsColors.IsInitialized()==anywheresoftware.b4a.keywords.Common.False) { 
_bd.BarsColors.Initialize();};
 //BA.debugLineNum = 48;BA.debugLine="BD.BarsColors.Add(Color)";
_bd.BarsColors.Add((Object)(_color));
 //BA.debugLineNum = 49;BA.debugLine="End Sub";
return "";
}
public static String  _addbarpoint(anywheresoftware.b4a.BA _ba,b4a.example.charts._bardata _bd,String _x,float[] _yarray) throws Exception{
b4a.example.charts._linepoint _b = null;
 //BA.debugLineNum = 28;BA.debugLine="Sub AddBarPoint (BD As BarData, X As String, YArray() As Float)";
 //BA.debugLineNum = 29;BA.debugLine="If BD.Points.IsInitialized = False Then";
if (_bd.Points.IsInitialized()==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 30;BA.debugLine="BD.Points.Initialize";
_bd.Points.Initialize();
 //BA.debugLineNum = 32;BA.debugLine="Dim b As LinePoint";
_b = new b4a.example.charts._linepoint();
 //BA.debugLineNum = 33;BA.debugLine="b.Initialize";
_b.Initialize();
 //BA.debugLineNum = 34;BA.debugLine="b.X = \"\"";
_b.X = "";
 //BA.debugLineNum = 35;BA.debugLine="b.ShowTick = False";
_b.ShowTick = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 36;BA.debugLine="BD.Points.Add(b)";
_bd.Points.Add((Object)(_b));
 };
 //BA.debugLineNum = 38;BA.debugLine="Dim b As LinePoint 'using the same structure of Line charts";
_b = new b4a.example.charts._linepoint();
 //BA.debugLineNum = 39;BA.debugLine="b.Initialize";
_b.Initialize();
 //BA.debugLineNum = 40;BA.debugLine="b.X = X";
_b.X = _x;
 //BA.debugLineNum = 41;BA.debugLine="b.YArray = YArray";
_b.YArray = _yarray;
 //BA.debugLineNum = 42;BA.debugLine="b.ShowTick = True";
_b.ShowTick = anywheresoftware.b4a.keywords.Common.True;
 //BA.debugLineNum = 43;BA.debugLine="BD.Points.Add(b)";
_bd.Points.Add((Object)(_b));
 //BA.debugLineNum = 44;BA.debugLine="End Sub";
return "";
}
public static String  _addlinecolor(anywheresoftware.b4a.BA _ba,b4a.example.charts._linedata _ld,int _color) throws Exception{
 //BA.debugLineNum = 196;BA.debugLine="Sub AddLineColor(LD As LineData, Color As Int)";
 //BA.debugLineNum = 197;BA.debugLine="If LD.LinesColors.IsInitialized = False Then LD.LinesColors.Initialize";
if (_ld.LinesColors.IsInitialized()==anywheresoftware.b4a.keywords.Common.False) { 
_ld.LinesColors.Initialize();};
 //BA.debugLineNum = 198;BA.debugLine="LD.LinesColors.Add(Color)";
_ld.LinesColors.Add((Object)(_color));
 //BA.debugLineNum = 199;BA.debugLine="End Sub";
return "";
}
public static String  _addlinemultiplepoints(anywheresoftware.b4a.BA _ba,b4a.example.charts._linedata _ld,String _x,float[] _yarray,boolean _showtick) throws Exception{
b4a.example.charts._linepoint _p = null;
 //BA.debugLineNum = 186;BA.debugLine="Sub AddLineMultiplePoints(LD As LineData, X As String, YArray() As Float, ShowTick As Boolean)";
 //BA.debugLineNum = 187;BA.debugLine="If LD.Points.IsInitialized = False Then LD.Points.Initialize";
if (_ld.Points.IsInitialized()==anywheresoftware.b4a.keywords.Common.False) { 
_ld.Points.Initialize();};
 //BA.debugLineNum = 188;BA.debugLine="Dim p As LinePoint";
_p = new b4a.example.charts._linepoint();
 //BA.debugLineNum = 189;BA.debugLine="p.Initialize";
_p.Initialize();
 //BA.debugLineNum = 190;BA.debugLine="p.X = X";
_p.X = _x;
 //BA.debugLineNum = 191;BA.debugLine="p.YArray = YArray";
_p.YArray = _yarray;
 //BA.debugLineNum = 192;BA.debugLine="p.ShowTick = ShowTick";
_p.ShowTick = _showtick;
 //BA.debugLineNum = 193;BA.debugLine="LD.Points.Add(p)";
_ld.Points.Add((Object)(_p));
 //BA.debugLineNum = 194;BA.debugLine="End Sub";
return "";
}
public static String  _addlinepoint(anywheresoftware.b4a.BA _ba,b4a.example.charts._linedata _ld,String _x,float _y,boolean _showtick) throws Exception{
b4a.example.charts._linepoint _p = null;
 //BA.debugLineNum = 176;BA.debugLine="Sub AddLinePoint (LD As LineData, X As String, Y As Float, ShowTick As Boolean)";
 //BA.debugLineNum = 177;BA.debugLine="If LD.Points.IsInitialized = False Then LD.Points.Initialize";
if (_ld.Points.IsInitialized()==anywheresoftware.b4a.keywords.Common.False) { 
_ld.Points.Initialize();};
 //BA.debugLineNum = 178;BA.debugLine="Dim p As LinePoint";
_p = new b4a.example.charts._linepoint();
 //BA.debugLineNum = 179;BA.debugLine="p.Initialize";
_p.Initialize();
 //BA.debugLineNum = 180;BA.debugLine="p.X = X";
_p.X = _x;
 //BA.debugLineNum = 181;BA.debugLine="p.Y = Y";
_p.Y = _y;
 //BA.debugLineNum = 182;BA.debugLine="p.ShowTick = ShowTick";
_p.ShowTick = _showtick;
 //BA.debugLineNum = 183;BA.debugLine="LD.Points.Add(p)";
_ld.Points.Add((Object)(_p));
 //BA.debugLineNum = 184;BA.debugLine="End Sub";
return "";
}
public static String  _addpieitem(anywheresoftware.b4a.BA _ba,b4a.example.charts._piedata _pd,String _name,float _value,int _color) throws Exception{
b4a.example.charts._pieitem _i = null;
 //BA.debugLineNum = 258;BA.debugLine="Sub AddPieItem(PD As PieData, Name As String, Value As Float, Color As Int)";
 //BA.debugLineNum = 259;BA.debugLine="If PD.Items.IsInitialized = False Then PD.Items.Initialize";
if (_pd.Items.IsInitialized()==anywheresoftware.b4a.keywords.Common.False) { 
_pd.Items.Initialize();};
 //BA.debugLineNum = 260;BA.debugLine="If Color = 0 Then Color = Colors.RGB(Rnd(0, 255), Rnd(0, 255), Rnd(0, 255))";
if (_color==0) { 
_color = anywheresoftware.b4a.keywords.Common.Colors.RGB(anywheresoftware.b4a.keywords.Common.Rnd((int) (0),(int) (255)),anywheresoftware.b4a.keywords.Common.Rnd((int) (0),(int) (255)),anywheresoftware.b4a.keywords.Common.Rnd((int) (0),(int) (255)));};
 //BA.debugLineNum = 261;BA.debugLine="Dim i As PieItem";
_i = new b4a.example.charts._pieitem();
 //BA.debugLineNum = 262;BA.debugLine="i.Initialize";
_i.Initialize();
 //BA.debugLineNum = 263;BA.debugLine="i.Name = Name";
_i.Name = _name;
 //BA.debugLineNum = 264;BA.debugLine="i.Value = Value";
_i.Value = _value;
 //BA.debugLineNum = 265;BA.debugLine="i.Color = Color";
_i.Color = _color;
 //BA.debugLineNum = 266;BA.debugLine="PD.Items.Add(i)";
_pd.Items.Add((Object)(_i));
 //BA.debugLineNum = 267;BA.debugLine="End Sub";
return "";
}
public static int  _calcpointtopixel(anywheresoftware.b4a.BA _ba,float _py,b4a.example.charts._graph _g) throws Exception{
 //BA.debugLineNum = 248;BA.debugLine="Sub calcPointToPixel(py As Float, G As Graph) As Int";
 //BA.debugLineNum = 249;BA.debugLine="If G.YStart < 0 AND G.YEnd > 0 Then";
if (_g.YStart<0 && _g.YEnd>0) { 
 //BA.debugLineNum = 250;BA.debugLine="Return G.GI.zeroY - (G.GI.originY - G.GI.maxY) * py / (G.YEnd - G.YStart)";
if (true) return (int) (_g.GI.zeroY-(_g.GI.originY-_g.GI.maxY)*_py/(double)(_g.YEnd-_g.YStart));
 }else {
 //BA.debugLineNum = 252;BA.debugLine="Return G.GI.originY - (G.GI.originY - G.GI.maxY) * (py - G.YStart) / (G.YEnd - G.YStart)";
if (true) return (int) (_g.GI.originY-(_g.GI.originY-_g.GI.maxY)*(_py-_g.YStart)/(double)(_g.YEnd-_g.YStart));
 };
 //BA.debugLineNum = 254;BA.debugLine="End Sub";
return 0;
}
public static String  _calcscale(anywheresoftware.b4a.BA _ba,b4a.example.charts._graph _g,b4a.example.charts._linedata _ld) throws Exception{
double _scalelog = 0;
double _scalemant = 0;
double _scaleexp = 0;
double _scaledelta = 0;
double _valdiff = 0;
double _scalemin = 0;
double _scalemax = 0;
double _scaleinterval = 0;
double _log1 = 0;
double _log2 = 0;
double _log25 = 0;
double _log5 = 0;
double _log10 = 0;
int _nbmin = 0;
int _nbusedintervals = 0;
int _nbusedintervalstop = 0;
int _nbusedintervalsbottom = 0;
int _nbintervalstomove = 0;
double[] _valminmax = null;
 //BA.debugLineNum = 413;BA.debugLine="Sub CalcScale(G As Graph, LD As LineData)";
 //BA.debugLineNum = 414;BA.debugLine="Dim ScaleLog, ScaleMant, ScaleExp, ScaleDelta, ValDiff As Double";
_scalelog = 0;
_scalemant = 0;
_scaleexp = 0;
_scaledelta = 0;
_valdiff = 0;
 //BA.debugLineNum = 415;BA.debugLine="Dim ScaleMin, ScaleMax, ScaleInterval As Double";
_scalemin = 0;
_scalemax = 0;
_scaleinterval = 0;
 //BA.debugLineNum = 416;BA.debugLine="Dim Log1, Log2, Log25, Log5, Log10 As Double";
_log1 = 0;
_log2 = 0;
_log25 = 0;
_log5 = 0;
_log10 = 0;
 //BA.debugLineNum = 417;BA.debugLine="Dim nbMin, NbUsedIntervals, NbUsedIntervalsTop, NbUsedIntervalsBottom, NbIntervalsToMove As Int";
_nbmin = 0;
_nbusedintervals = 0;
_nbusedintervalstop = 0;
_nbusedintervalsbottom = 0;
_nbintervalstomove = 0;
 //BA.debugLineNum = 418;BA.debugLine="Dim ValMinMax(2) As Double";
_valminmax = new double[(int) (2)];
;
 //BA.debugLineNum = 420;BA.debugLine="ValMinMax = GetLineMinMaxValues(LD)";
_valminmax = _getlineminmaxvalues(_ba,_ld);
 //BA.debugLineNum = 423;BA.debugLine="If ValMinMax(0) = ValMinMax(1) Then";
if (_valminmax[(int) (0)]==_valminmax[(int) (1)]) { 
 //BA.debugLineNum = 424;BA.debugLine="ScaleInterval = 1";
_scaleinterval = 1;
 //BA.debugLineNum = 425;BA.debugLine="ScaleMin = Floor(ValMinMax(0)) -ScaleInterval * G.NbIntervals / 2";
_scalemin = anywheresoftware.b4a.keywords.Common.Floor(_valminmax[(int) (0)])-_scaleinterval*_g.NbIntervals/(double)2;
 //BA.debugLineNum = 426;BA.debugLine="ScaleMax = ScaleMin + ScaleInterval * G.NbIntervals";
_scalemax = _scalemin+_scaleinterval*_g.NbIntervals;
 //BA.debugLineNum = 427;BA.debugLine="G.YStart = ScaleMin";
_g.YStart = (float) (_scalemin);
 //BA.debugLineNum = 428;BA.debugLine="G.YEnd = ScaleMax";
_g.YEnd = (float) (_scalemax);
 //BA.debugLineNum = 429;BA.debugLine="G.YInterval = ScaleInterval";
_g.YInterval = (float) (_scaleinterval);
 //BA.debugLineNum = 430;BA.debugLine="Return";
if (true) return "";
 };
 //BA.debugLineNum = 434;BA.debugLine="Log1 = 0.00000000000001";
_log1 = 0.00000000000001;
 //BA.debugLineNum = 435;BA.debugLine="Log2 = Logarithm(2, 10) + 0.00000000000001";
_log2 = anywheresoftware.b4a.keywords.Common.Logarithm(2,10)+0.00000000000001;
 //BA.debugLineNum = 436;BA.debugLine="Log25 = Logarithm(2.5, 10) + 0.00000000000001";
_log25 = anywheresoftware.b4a.keywords.Common.Logarithm(2.5,10)+0.00000000000001;
 //BA.debugLineNum = 437;BA.debugLine="Log5 = Logarithm(5, 10) + 0.00000000000001";
_log5 = anywheresoftware.b4a.keywords.Common.Logarithm(5,10)+0.00000000000001;
 //BA.debugLineNum = 438;BA.debugLine="Log10 = Logarithm(10, 10) + 0.00000000000001";
_log10 = anywheresoftware.b4a.keywords.Common.Logarithm(10,10)+0.00000000000001;
 //BA.debugLineNum = 440;BA.debugLine="ValDiff = ValMinMax(1) - ValMinMax(0)";
_valdiff = _valminmax[(int) (1)]-_valminmax[(int) (0)];
 //BA.debugLineNum = 441;BA.debugLine="ScaleDelta = ValDiff / G.NbIntervals";
_scaledelta = _valdiff/(double)_g.NbIntervals;
 //BA.debugLineNum = 443;BA.debugLine="ScaleLog = Logarithm(ScaleDelta, 10)";
_scalelog = anywheresoftware.b4a.keywords.Common.Logarithm(_scaledelta,10);
 //BA.debugLineNum = 444;BA.debugLine="ScaleExp = Floor(ScaleLog)";
_scaleexp = anywheresoftware.b4a.keywords.Common.Floor(_scalelog);
 //BA.debugLineNum = 445;BA.debugLine="If ValDiff >= 1 Then";
if (_valdiff>=1) { 
 //BA.debugLineNum = 446;BA.debugLine="ScaleMant = ScaleLog - ScaleExp";
_scalemant = _scalelog-_scaleexp;
 }else {
 //BA.debugLineNum = 448;BA.debugLine="ScaleMant = Abs(ScaleExp) + ScaleLog";
_scalemant = anywheresoftware.b4a.keywords.Common.Abs(_scaleexp)+_scalelog;
 };
 //BA.debugLineNum = 452;BA.debugLine="If ScaleMant <= Log1 Then";
if (_scalemant<=_log1) { 
 //BA.debugLineNum = 453;BA.debugLine="ScaleMant = 0";
_scalemant = 0;
 }else if(_scalemant>_log1 && _scalemant<=_log2) { 
 //BA.debugLineNum = 455;BA.debugLine="ScaleMant = Logarithm(2, 10)";
_scalemant = anywheresoftware.b4a.keywords.Common.Logarithm(2,10);
 }else if(_scalemant>_log2 && _scalemant<=_log25) { 
 //BA.debugLineNum = 457;BA.debugLine="ScaleMant = Logarithm(2.5, 10)";
_scalemant = anywheresoftware.b4a.keywords.Common.Logarithm(2.5,10);
 }else if(_scalemant>_log25 && _scalemant<=_log5) { 
 //BA.debugLineNum = 459;BA.debugLine="ScaleMant = Logarithm(5, 10)";
_scalemant = anywheresoftware.b4a.keywords.Common.Logarithm(5,10);
 }else if(_scalemant>_log5 && _scalemant<=_log10) { 
 //BA.debugLineNum = 461;BA.debugLine="ScaleMant = 1";
_scalemant = 1;
 };
 //BA.debugLineNum = 463;BA.debugLine="ScaleInterval = Power(10, ScaleExp + ScaleMant)";
_scaleinterval = anywheresoftware.b4a.keywords.Common.Power(10,_scaleexp+_scalemant);
 //BA.debugLineNum = 466;BA.debugLine="If ValMinMax(0) < 0 AND ValMinMax(1) > 0 Then";
if (_valminmax[(int) (0)]<0 && _valminmax[(int) (1)]>0) { 
 //BA.debugLineNum = 467;BA.debugLine="NbUsedIntervalsTop = Ceil(ValMinMax(1) / ScaleInterval - 0.00000000000001)";
_nbusedintervalstop = (int) (anywheresoftware.b4a.keywords.Common.Ceil(_valminmax[(int) (1)]/(double)_scaleinterval-0.00000000000001));
 //BA.debugLineNum = 468;BA.debugLine="NbUsedIntervalsBottom = Ceil(Abs(ValMinMax(0)) / ScaleInterval - 0.00000000000001)";
_nbusedintervalsbottom = (int) (anywheresoftware.b4a.keywords.Common.Ceil(anywheresoftware.b4a.keywords.Common.Abs(_valminmax[(int) (0)])/(double)_scaleinterval-0.00000000000001));
 //BA.debugLineNum = 470;BA.debugLine="If NbUsedIntervalsTop + NbUsedIntervalsBottom > G.NbIntervals Then";
if (_nbusedintervalstop+_nbusedintervalsbottom>_g.NbIntervals) { 
 //BA.debugLineNum = 472;BA.debugLine="If ScaleMant <= Log1 Then";
if (_scalemant<=_log1) { 
 //BA.debugLineNum = 473;BA.debugLine="ScaleMant = Logarithm(2, 10)";
_scalemant = anywheresoftware.b4a.keywords.Common.Logarithm(2,10);
 }else if(_scalemant>_log1 && _scalemant<=_log2) { 
 //BA.debugLineNum = 475;BA.debugLine="ScaleMant = Logarithm(2.5, 10)";
_scalemant = anywheresoftware.b4a.keywords.Common.Logarithm(2.5,10);
 }else if(_scalemant>_log2 && _scalemant<=_log25) { 
 //BA.debugLineNum = 477;BA.debugLine="ScaleMant = Logarithm(5, 10)";
_scalemant = anywheresoftware.b4a.keywords.Common.Logarithm(5,10);
 }else if(_scalemant>_log25 && _scalemant<=_log5) { 
 //BA.debugLineNum = 479;BA.debugLine="ScaleMant = 1";
_scalemant = 1;
 }else if(_scalemant>_log5 && _scalemant<=_log10) { 
 //BA.debugLineNum = 481;BA.debugLine="ScaleMant = Logarithm(20, 10)";
_scalemant = anywheresoftware.b4a.keywords.Common.Logarithm(20,10);
 };
 //BA.debugLineNum = 483;BA.debugLine="ScaleInterval = Power(10, ScaleExp + ScaleMant)";
_scaleinterval = anywheresoftware.b4a.keywords.Common.Power(10,_scaleexp+_scalemant);
 };
 };
 //BA.debugLineNum = 488;BA.debugLine="nbMin = Floor(ValMinMax(0) / ScaleInterval)";
_nbmin = (int) (anywheresoftware.b4a.keywords.Common.Floor(_valminmax[(int) (0)]/(double)_scaleinterval));
 //BA.debugLineNum = 489;BA.debugLine="If Abs(ValMinMax(0)) <= 0.0000000000001 Then";
if (anywheresoftware.b4a.keywords.Common.Abs(_valminmax[(int) (0)])<=0.0000000000001) { 
 //BA.debugLineNum = 490;BA.debugLine="ScaleMin = 0";
_scalemin = 0;
 }else if(_valminmax[(int) (0)]>=0) { 
 //BA.debugLineNum = 492;BA.debugLine="ScaleMin = nbMin * ScaleInterval";
_scalemin = _nbmin*_scaleinterval;
 }else if(_valminmax[(int) (0)]<0 && _valminmax[(int) (1)]>0) { 
 //BA.debugLineNum = 494;BA.debugLine="ScaleMin = Floor(ValMinMax(0) / ScaleInterval + 0.00000000000001) * ScaleInterval";
_scalemin = anywheresoftware.b4a.keywords.Common.Floor(_valminmax[(int) (0)]/(double)_scaleinterval+0.00000000000001)*_scaleinterval;
 }else {
 //BA.debugLineNum = 496;BA.debugLine="ScaleMin = Floor(ValMinMax(0) / ScaleInterval + 0.00000000000001) * ScaleInterval";
_scalemin = anywheresoftware.b4a.keywords.Common.Floor(_valminmax[(int) (0)]/(double)_scaleinterval+0.00000000000001)*_scaleinterval;
 };
 //BA.debugLineNum = 500;BA.debugLine="If Abs(ScaleMin) > 0.0000000000001 Then";
if (anywheresoftware.b4a.keywords.Common.Abs(_scalemin)>0.0000000000001) { 
 //BA.debugLineNum = 501;BA.debugLine="If ValMinMax(0) < 0 AND ValMinMax(1) > 0 Then";
if (_valminmax[(int) (0)]<0 && _valminmax[(int) (1)]>0) { 
 //BA.debugLineNum = 502;BA.debugLine="NbUsedIntervalsTop = Ceil(ValMinMax(1) / ScaleInterval - 0.00000000000001)";
_nbusedintervalstop = (int) (anywheresoftware.b4a.keywords.Common.Ceil(_valminmax[(int) (1)]/(double)_scaleinterval-0.00000000000001));
 //BA.debugLineNum = 503;BA.debugLine="NbUsedIntervalsBottom = Ceil(Abs(ValMinMax(0)) / ScaleInterval - 0.00000000000001)";
_nbusedintervalsbottom = (int) (anywheresoftware.b4a.keywords.Common.Ceil(anywheresoftware.b4a.keywords.Common.Abs(_valminmax[(int) (0)])/(double)_scaleinterval-0.00000000000001));
 //BA.debugLineNum = 504;BA.debugLine="NbUsedIntervals = NbUsedIntervalsTop + NbUsedIntervalsBottom";
_nbusedintervals = (int) (_nbusedintervalstop+_nbusedintervalsbottom);
 //BA.debugLineNum = 505;BA.debugLine="If NbUsedIntervalsTop - NbUsedIntervalsBottom = 1 Then";
if (_nbusedintervalstop-_nbusedintervalsbottom==1) { 
 //BA.debugLineNum = 506;BA.debugLine="NbIntervalsToMove = G.NbIntervals / 2 - NbUsedIntervalsBottom";
_nbintervalstomove = (int) (_g.NbIntervals/(double)2-_nbusedintervalsbottom);
 }else {
 //BA.debugLineNum = 508;BA.debugLine="NbIntervalsToMove = (G.NbIntervals - NbUsedIntervals) / 2";
_nbintervalstomove = (int) ((_g.NbIntervals-_nbusedintervals)/(double)2);
 };
 }else {
 //BA.debugLineNum = 511;BA.debugLine="NbUsedIntervals = Ceil(ValDiff / ScaleInterval - 0.00000000000001)";
_nbusedintervals = (int) (anywheresoftware.b4a.keywords.Common.Ceil(_valdiff/(double)_scaleinterval-0.00000000000001));
 //BA.debugLineNum = 512;BA.debugLine="NbIntervalsToMove = (G.NbIntervals - NbUsedIntervals) / 2";
_nbintervalstomove = (int) ((_g.NbIntervals-_nbusedintervals)/(double)2);
 };
 //BA.debugLineNum = 514;BA.debugLine="ScaleMin = ScaleMin - ScaleInterval * NbIntervalsToMove";
_scalemin = _scalemin-_scaleinterval*_nbintervalstomove;
 };
 //BA.debugLineNum = 518;BA.debugLine="ScaleMax = ScaleMin + ScaleInterval * G.NbIntervals";
_scalemax = _scalemin+_scaleinterval*_g.NbIntervals;
 //BA.debugLineNum = 520;BA.debugLine="G.YStart = ScaleMin";
_g.YStart = (float) (_scalemin);
 //BA.debugLineNum = 521;BA.debugLine="G.YEnd = ScaleMax";
_g.YEnd = (float) (_scalemax);
 //BA.debugLineNum = 522;BA.debugLine="G.YInterval = ScaleInterval";
_g.YInterval = (float) (_scaleinterval);
 //BA.debugLineNum = 523;BA.debugLine="End Sub";
return "";
}
public static float  _calcslice(anywheresoftware.b4a.BA _ba,anywheresoftware.b4a.objects.drawable.CanvasWrapper _canvas,int _radius,float _startingdegree,float _percent,int _color,int _gapdegrees) throws Exception{
float _b = 0f;
int _cx = 0;
int _cy = 0;
anywheresoftware.b4a.objects.drawable.CanvasWrapper.PathWrapper _p = null;
float _gap = 0f;
int _i = 0;
 //BA.debugLineNum = 303;BA.debugLine="Sub calcSlice(Canvas As Canvas, Radius As Int, _  		StartingDegree As Float, Percent As Float, Color As Int, GapDegrees As Int) As Float";
 //BA.debugLineNum = 305;BA.debugLine="Dim b As Float";
_b = 0f;
 //BA.debugLineNum = 306;BA.debugLine="b = 360 * Percent";
_b = (float) (360*_percent);
 //BA.debugLineNum = 308;BA.debugLine="Dim cx, cy As Int";
_cx = 0;
_cy = 0;
 //BA.debugLineNum = 309;BA.debugLine="cx = Canvas.Bitmap.Width / 2";
_cx = (int) (_canvas.getBitmap().getWidth()/(double)2);
 //BA.debugLineNum = 310;BA.debugLine="cy = Canvas.Bitmap.Height / 2";
_cy = (int) (_canvas.getBitmap().getHeight()/(double)2);
 //BA.debugLineNum = 311;BA.debugLine="Dim p As Path";
_p = new anywheresoftware.b4a.objects.drawable.CanvasWrapper.PathWrapper();
 //BA.debugLineNum = 312;BA.debugLine="p.Initialize(cx, cy)";
_p.Initialize((float) (_cx),(float) (_cy));
 //BA.debugLineNum = 313;BA.debugLine="Dim gap As Float";
_gap = 0f;
 //BA.debugLineNum = 314;BA.debugLine="gap = Percent * GapDegrees / 2";
_gap = (float) (_percent*_gapdegrees/(double)2);
 //BA.debugLineNum = 315;BA.debugLine="For i = StartingDegree + gap To StartingDegree + b - gap Step 10";
{
final int step257 = (int) (10);
final int limit257 = (int) (_startingdegree+_b-_gap);
for (_i = (int) (_startingdegree+_gap); (step257 > 0 && _i <= limit257) || (step257 < 0 && _i >= limit257); _i = ((int)(0 + _i + step257))) {
 //BA.debugLineNum = 316;BA.debugLine="p.LineTo(cx + 2 * Radius * SinD(i), cy + 2 * Radius * CosD(i))";
_p.LineTo((float) (_cx+2*_radius*anywheresoftware.b4a.keywords.Common.SinD(_i)),(float) (_cy+2*_radius*anywheresoftware.b4a.keywords.Common.CosD(_i)));
 }
};
 //BA.debugLineNum = 318;BA.debugLine="p.LineTo(cx + 2 * Radius * SinD(StartingDegree + b - gap), cy + 2 * Radius * CosD(StartingDegree + b - gap))";
_p.LineTo((float) (_cx+2*_radius*anywheresoftware.b4a.keywords.Common.SinD(_startingdegree+_b-_gap)),(float) (_cy+2*_radius*anywheresoftware.b4a.keywords.Common.CosD(_startingdegree+_b-_gap)));
 //BA.debugLineNum = 319;BA.debugLine="p.LineTo(cx, cy)";
_p.LineTo((float) (_cx),(float) (_cy));
 //BA.debugLineNum = 320;BA.debugLine="Canvas.ClipPath(p) 'We are limiting the drawings to the required slice";
_canvas.ClipPath((android.graphics.Path)(_p.getObject()));
 //BA.debugLineNum = 321;BA.debugLine="Canvas.DrawCircle(cx, cy, Radius, Color, True, 0)";
_canvas.DrawCircle((float) (_cx),(float) (_cy),(float) (_radius),_color,anywheresoftware.b4a.keywords.Common.True,(float) (0));
 //BA.debugLineNum = 322;BA.debugLine="Canvas.RemoveClip";
_canvas.RemoveClip();
 //BA.debugLineNum = 323;BA.debugLine="Return b";
if (true) return _b;
 //BA.debugLineNum = 324;BA.debugLine="End Sub";
return 0f;
}
public static anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper  _createlegend(anywheresoftware.b4a.BA _ba,b4a.example.charts._piedata _pd) throws Exception{
anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper _bmp = null;
float _textheight = 0f;
float _textwidth = 0f;
int _i = 0;
b4a.example.charts._pieitem _it = null;
anywheresoftware.b4a.objects.drawable.CanvasWrapper _c = null;
 //BA.debugLineNum = 326;BA.debugLine="Sub createLegend(PD As PieData) As Bitmap";
 //BA.debugLineNum = 327;BA.debugLine="Dim bmp As Bitmap";
_bmp = new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper();
 //BA.debugLineNum = 328;BA.debugLine="If PD.LegendTextSize = 0 Then PD.LegendTextSize = 15";
if (_pd.LegendTextSize==0) { 
_pd.LegendTextSize = (float) (15);};
 //BA.debugLineNum = 329;BA.debugLine="Dim textHeight, textWidth As Float";
_textheight = 0f;
_textwidth = 0f;
 //BA.debugLineNum = 330;BA.debugLine="textHeight = PD.Canvas.MeasureStringHeight(\"M\", Typeface.DEFAULT_BOLD, PD.LegendTextSize)";
_textheight = _pd.Canvas.MeasureStringHeight("M",anywheresoftware.b4a.keywords.Common.Typeface.DEFAULT_BOLD,_pd.LegendTextSize);
 //BA.debugLineNum = 331;BA.debugLine="For i = 0 To PD.Items.Size - 1";
{
final int step272 = 1;
final int limit272 = (int) (_pd.Items.getSize()-1);
for (_i = (int) (0); (step272 > 0 && _i <= limit272) || (step272 < 0 && _i >= limit272); _i = ((int)(0 + _i + step272))) {
 //BA.debugLineNum = 332;BA.debugLine="Dim it As PieItem";
_it = new b4a.example.charts._pieitem();
 //BA.debugLineNum = 333;BA.debugLine="it = PD.Items.Get(i)";
_it = (b4a.example.charts._pieitem)(_pd.Items.Get(_i));
 //BA.debugLineNum = 334;BA.debugLine="textWidth = Max(textWidth, PD.Canvas.MeasureStringWidth(it.Name, Typeface.DEFAULT_BOLD, PD.LegendTextSize))";
_textwidth = (float) (anywheresoftware.b4a.keywords.Common.Max(_textwidth,_pd.Canvas.MeasureStringWidth(_it.Name,anywheresoftware.b4a.keywords.Common.Typeface.DEFAULT_BOLD,_pd.LegendTextSize)));
 }
};
 //BA.debugLineNum = 336;BA.debugLine="bmp.InitializeMutable(textWidth + 20dip, 10dip +(textHeight + 10dip) * PD.Items.Size)";
_bmp.InitializeMutable((int) (_textwidth+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (20))),(int) (anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (10))+(_textheight+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (10)))*_pd.Items.getSize()));
 //BA.debugLineNum = 337;BA.debugLine="Dim c As Canvas";
_c = new anywheresoftware.b4a.objects.drawable.CanvasWrapper();
 //BA.debugLineNum = 338;BA.debugLine="c.Initialize2(bmp)";
_c.Initialize2((android.graphics.Bitmap)(_bmp.getObject()));
 //BA.debugLineNum = 339;BA.debugLine="c.DrawColor(PD.LegendBackColor)";
_c.DrawColor(_pd.LegendBackColor);
 //BA.debugLineNum = 340;BA.debugLine="For i = 0 To PD.Items.Size - 1";
{
final int step281 = 1;
final int limit281 = (int) (_pd.Items.getSize()-1);
for (_i = (int) (0); (step281 > 0 && _i <= limit281) || (step281 < 0 && _i >= limit281); _i = ((int)(0 + _i + step281))) {
 //BA.debugLineNum = 341;BA.debugLine="Dim it As PieItem";
_it = new b4a.example.charts._pieitem();
 //BA.debugLineNum = 342;BA.debugLine="it = PD.Items.Get(i)";
_it = (b4a.example.charts._pieitem)(_pd.Items.Get(_i));
 //BA.debugLineNum = 343;BA.debugLine="c.DrawText(it.Name, 10dip, (i + 1) * (textHeight + 10dip), Typeface.DEFAULT_BOLD, PD.LegendTextSize, _ 			it.Color, \"LEFT\")";
_c.DrawText(_ba,_it.Name,(float) (anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (10))),(float) ((_i+1)*(_textheight+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (10)))),anywheresoftware.b4a.keywords.Common.Typeface.DEFAULT_BOLD,_pd.LegendTextSize,_it.Color,BA.getEnumFromString(android.graphics.Paint.Align.class,"LEFT"));
 }
};
 //BA.debugLineNum = 346;BA.debugLine="Return bmp";
if (true) return _bmp;
 //BA.debugLineNum = 347;BA.debugLine="End Sub";
return null;
}
public static String  _drawbarschart(anywheresoftware.b4a.BA _ba,b4a.example.charts._graph _g,b4a.example.charts._bardata _bd,int _backcolor) throws Exception{
b4a.example.charts._linepoint _point = null;
anywheresoftware.b4a.objects.drawable.CanvasWrapper.RectWrapper _rect = null;
int _numberofbars = 0;
int _i = 0;
int _a = 0;
 //BA.debugLineNum = 51;BA.debugLine="Sub DrawBarsChart(G As Graph, BD As BarData, BackColor As Int)";
 //BA.debugLineNum = 52;BA.debugLine="If BD.Points.Size = 0 Then";
if (_bd.Points.getSize()==0) { 
 //BA.debugLineNum = 53;BA.debugLine="ToastMessageShow(\"Missing bars points.\", True)";
anywheresoftware.b4a.keywords.Common.ToastMessageShow("Missing bars points.",anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 54;BA.debugLine="Return";
if (true) return "";
 };
 //BA.debugLineNum = 56;BA.debugLine="BD.Canvas.Initialize(BD.Target)";
_bd.Canvas.Initialize((android.view.View)(_bd.Target.getObject()));
 //BA.debugLineNum = 57;BA.debugLine="BD.Canvas.DrawColor(BackColor)";
_bd.Canvas.DrawColor(_backcolor);
 //BA.debugLineNum = 58;BA.debugLine="Dim point As LinePoint";
_point = new b4a.example.charts._linepoint();
 //BA.debugLineNum = 59;BA.debugLine="point = BD.Points.Get(1)";
_point = (b4a.example.charts._linepoint)(_bd.Points.Get((int) (1)));
 //BA.debugLineNum = 60;BA.debugLine="If BD.Stacked = False Then";
if (_bd.Stacked==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 61;BA.debugLine="drawGraph(G, BD.Canvas, BD.Target, BD.Points, True, BD.BarsWidth)";
_drawgraph(_ba,_g,_bd.Canvas,(anywheresoftware.b4a.objects.ConcreteViewWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.ConcreteViewWrapper(), (android.view.View)(_bd.Target.getObject())),_bd.Points,anywheresoftware.b4a.keywords.Common.True,_bd.BarsWidth);
 }else {
 //BA.debugLineNum = 64;BA.debugLine="drawGraph(G, BD.Canvas, BD.Target, BD.Points, True, BD.BarsWidth / point.YArray.Length)";
_drawgraph(_ba,_g,_bd.Canvas,(anywheresoftware.b4a.objects.ConcreteViewWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.ConcreteViewWrapper(), (android.view.View)(_bd.Target.getObject())),_bd.Points,anywheresoftware.b4a.keywords.Common.True,(int) (_bd.BarsWidth/(double)_point.YArray.length));
 };
 //BA.debugLineNum = 68;BA.debugLine="Dim Rect As Rect";
_rect = new anywheresoftware.b4a.objects.drawable.CanvasWrapper.RectWrapper();
 //BA.debugLineNum = 69;BA.debugLine="Rect.Initialize(0, 0, 0, G.GI.originY)";
_rect.Initialize((int) (0),(int) (0),(int) (0),_g.GI.originY);
 //BA.debugLineNum = 70;BA.debugLine="Dim numberOfBars As Int";
_numberofbars = 0;
 //BA.debugLineNum = 71;BA.debugLine="numberOfBars = point.YArray.Length";
_numberofbars = _point.YArray.length;
 //BA.debugLineNum = 73;BA.debugLine="For i = 1 To BD.Points.Size - 1";
{
final int step47 = 1;
final int limit47 = (int) (_bd.Points.getSize()-1);
for (_i = (int) (1); (step47 > 0 && _i <= limit47) || (step47 < 0 && _i >= limit47); _i = ((int)(0 + _i + step47))) {
 //BA.debugLineNum = 74;BA.debugLine="point = BD.Points.Get(i)";
_point = (b4a.example.charts._linepoint)(_bd.Points.Get(_i));
 //BA.debugLineNum = 75;BA.debugLine="For a = 0 To numberOfBars - 1";
{
final int step49 = 1;
final int limit49 = (int) (_numberofbars-1);
for (_a = (int) (0); (step49 > 0 && _a <= limit49) || (step49 < 0 && _a >= limit49); _a = ((int)(0 + _a + step49))) {
 //BA.debugLineNum = 76;BA.debugLine="If BD.Stacked = False Then";
if (_bd.Stacked==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 77;BA.debugLine="Rect.Left = G.GI.originX + G.GI.intervalX * (i - 0.5) + (a - numberOfBars / 2) * BD.BarsWidth";
_rect.setLeft((int) (_g.GI.originX+_g.GI.intervalX*(_i-0.5)+(_a-_numberofbars/(double)2)*_bd.BarsWidth));
 //BA.debugLineNum = 78;BA.debugLine="Rect.Right = Rect.Left + BD.BarsWidth";
_rect.setRight((int) (_rect.getLeft()+_bd.BarsWidth));
 //BA.debugLineNum = 79;BA.debugLine="If G.YStart < 0 AND G.YEnd > 0 Then";
if (_g.YStart<0 && _g.YEnd>0) { 
 //BA.debugLineNum = 80;BA.debugLine="If point.YArray(a) > 0 Then";
if (_point.YArray[_a]>0) { 
 //BA.debugLineNum = 81;BA.debugLine="Rect.Top = calcPointToPixel(point.YArray(a), G)";
_rect.setTop(_calcpointtopixel(_ba,_point.YArray[_a],_g));
 //BA.debugLineNum = 82;BA.debugLine="Rect.Bottom = G.GI.zeroY";
_rect.setBottom(_g.GI.zeroY);
 }else {
 //BA.debugLineNum = 84;BA.debugLine="Rect.Bottom = calcPointToPixel(point.YArray(a), G)";
_rect.setBottom(_calcpointtopixel(_ba,_point.YArray[_a],_g));
 //BA.debugLineNum = 85;BA.debugLine="Rect.Top = G.GI.zeroY";
_rect.setTop(_g.GI.zeroY);
 };
 }else {
 //BA.debugLineNum = 88;BA.debugLine="Rect.Top = calcPointToPixel(point.YArray(a), G)";
_rect.setTop(_calcpointtopixel(_ba,_point.YArray[_a],_g));
 //BA.debugLineNum = 89;BA.debugLine="Rect.Bottom = G.GI.originY";
_rect.setBottom(_g.GI.originY);
 };
 }else {
 //BA.debugLineNum = 92;BA.debugLine="Rect.Left = G.GI.originX + G.GI.intervalX * (i - 0.5) - BD.BarsWidth / 2";
_rect.setLeft((int) (_g.GI.originX+_g.GI.intervalX*(_i-0.5)-_bd.BarsWidth/(double)2));
 //BA.debugLineNum = 93;BA.debugLine="Rect.Right = Rect.Left + BD.BarsWidth";
_rect.setRight((int) (_rect.getLeft()+_bd.BarsWidth));
 //BA.debugLineNum = 94;BA.debugLine="If a = 0 Then";
if (_a==0) { 
 //BA.debugLineNum = 95;BA.debugLine="Rect.Top = calcPointToPixel(0, G)";
_rect.setTop(_calcpointtopixel(_ba,(float) (0),_g));
 };
 //BA.debugLineNum = 97;BA.debugLine="Rect.Bottom = Rect.Top";
_rect.setBottom(_rect.getTop());
 //BA.debugLineNum = 98;BA.debugLine="Rect.Top = Rect.Bottom + calcPointToPixel(point.YArray(a), G) - G.GI.originY";
_rect.setTop((int) (_rect.getBottom()+_calcpointtopixel(_ba,_point.YArray[_a],_g)-_g.GI.originY));
 };
 //BA.debugLineNum = 100;BA.debugLine="BD.Canvas.DrawRect(Rect, BD.BarsColors.Get(a), True, 1dip)";
_bd.Canvas.DrawRect((android.graphics.Rect)(_rect.getObject()),(int)(BA.ObjectToNumber(_bd.BarsColors.Get(_a))),anywheresoftware.b4a.keywords.Common.True,(float) (anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (1))));
 }
};
 }
};
 //BA.debugLineNum = 103;BA.debugLine="BD.Target.Invalidate";
_bd.Target.Invalidate();
 //BA.debugLineNum = 104;BA.debugLine="End Sub";
return "";
}
public static String  _drawgraph(anywheresoftware.b4a.BA _ba,b4a.example.charts._graph _g,anywheresoftware.b4a.objects.drawable.CanvasWrapper _canvas,anywheresoftware.b4a.objects.ConcreteViewWrapper _target,anywheresoftware.b4a.objects.collections.List _points,boolean _bars,int _barswidth) throws Exception{
b4a.example.charts._graphinternal _gi = null;
b4a.example.charts._linepoint _p = null;
int _i = 0;
int _y = 0;
float _yvalue = 0f;
int _x = 0;
 //BA.debugLineNum = 108;BA.debugLine="Sub drawGraph (G As Graph, Canvas As Canvas, Target As View, Points As List, Bars As Boolean, BarsWidth As Int)";
 //BA.debugLineNum = 109;BA.debugLine="Dim GI As GraphInternal";
_gi = new b4a.example.charts._graphinternal();
 //BA.debugLineNum = 110;BA.debugLine="G.GI = GI";
_g.GI = _gi;
 //BA.debugLineNum = 111;BA.debugLine="GI.Initialize";
_gi.Initialize();
 //BA.debugLineNum = 112;BA.debugLine="GI.maxY = 35dip													'top margin";
_gi.maxY = anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (35));
 //BA.debugLineNum = 113;BA.debugLine="GI.originX = 50dip											'left margin";
_gi.originX = anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (50));
 //BA.debugLineNum = 114;BA.debugLine="GI.originY = Target.Height - 70dip			'bottom margin";
_gi.originY = (int) (_target.getHeight()-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (70)));
 //BA.debugLineNum = 115;BA.debugLine="GI.gw = Target.Width - 70dip 						'graph width";
_gi.gw = (int) (_target.getWidth()-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (70)));
 //BA.debugLineNum = 116;BA.debugLine="GI.gh = GI.originY - GI.maxY 						'graph height";
_gi.gh = (int) (_gi.originY-_gi.maxY);
 //BA.debugLineNum = 117;BA.debugLine="If G.YStart < 0 AND G.YEnd > 0 Then";
if (_g.YStart<0 && _g.YEnd>0) { 
 //BA.debugLineNum = 118;BA.debugLine="GI.zeroY = GI.maxY + GI.gh * G.YEnd / (G.YEnd - G.YStart)";
_gi.zeroY = (int) (_gi.maxY+_gi.gh*_g.YEnd/(double)(_g.YEnd-_g.YStart));
 }else {
 //BA.debugLineNum = 120;BA.debugLine="GI.zeroY = GI.originY";
_gi.zeroY = _gi.originY;
 };
 //BA.debugLineNum = 122;BA.debugLine="If Bars Then";
if (_bars) { 
 //BA.debugLineNum = 123;BA.debugLine="Dim p As LinePoint";
_p = new b4a.example.charts._linepoint();
 //BA.debugLineNum = 124;BA.debugLine="p = Points.Get(1)";
_p = (b4a.example.charts._linepoint)(_points.Get((int) (1)));
 //BA.debugLineNum = 125;BA.debugLine="GI.intervalX = (GI.gw - p.YArray.Length / 2 * BarsWidth) / (Points.Size - 1)";
_gi.intervalX = (float) ((_gi.gw-_p.YArray.length/(double)2*_barswidth)/(double)(_points.getSize()-1));
 }else {
 //BA.debugLineNum = 127;BA.debugLine="GI.intervalX = GI.gw / (Points.Size - 1)";
_gi.intervalX = (float) (_gi.gw/(double)(_points.getSize()-1));
 };
 //BA.debugLineNum = 130;BA.debugLine="Canvas.DrawLine(GI.originX, GI.originY + 2dip, GI.originX, GI.maxY - 2dip, G.AxisColor, 2dip)";
_canvas.DrawLine((float) (_gi.originX),(float) (_gi.originY+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (2))),(float) (_gi.originX),(float) (_gi.maxY-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (2))),_g.AxisColor,(float) (anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (2))));
 //BA.debugLineNum = 131;BA.debugLine="For i = 0 To (G.YEnd - G.YStart) / G.Yinterval + 1";
{
final int step101 = 1;
final int limit101 = (int) ((_g.YEnd-_g.YStart)/(double)_g.YInterval+1);
for (_i = (int) (0); (step101 > 0 && _i <= limit101) || (step101 < 0 && _i >= limit101); _i = ((int)(0 + _i + step101))) {
 //BA.debugLineNum = 132;BA.debugLine="Dim y As Int";
_y = 0;
 //BA.debugLineNum = 133;BA.debugLine="Dim yValue As Float";
_yvalue = 0f;
 //BA.debugLineNum = 134;BA.debugLine="yValue = G.YStart + G.YInterval * i";
_yvalue = (float) (_g.YStart+_g.YInterval*_i);
 //BA.debugLineNum = 135;BA.debugLine="If yValue > G.YEnd Then Continue";
if (_yvalue>_g.YEnd) { 
if (true) continue;};
 //BA.debugLineNum = 136;BA.debugLine="y = calcPointToPixel(yValue, G)";
_y = _calcpointtopixel(_ba,_yvalue,_g);
 //BA.debugLineNum = 137;BA.debugLine="Canvas.DrawLine(GI.originX, y, GI.originX - 5dip, y, G.AxisColor, 2dip)";
_canvas.DrawLine((float) (_gi.originX),(float) (_y),(float) (_gi.originX-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))),(float) (_y),_g.AxisColor,(float) (anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (2))));
 //BA.debugLineNum = 138;BA.debugLine="If i < (G.YEnd - G.YStart) / G.Yinterval Then";
if (_i<(_g.YEnd-_g.YStart)/(double)_g.YInterval) { 
 //BA.debugLineNum = 139;BA.debugLine="Canvas.DrawLine(GI.originX, y, GI.originX + GI.gw, y, G.AxisColor, 1dip)";
_canvas.DrawLine((float) (_gi.originX),(float) (_y),(float) (_gi.originX+_gi.gw),(float) (_y),_g.AxisColor,(float) (anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (1))));
 }else {
 //BA.debugLineNum = 141;BA.debugLine="Canvas.DrawLine(GI.originX, y, GI.originX + GI.gw, y, G.AxisColor, 2dip)";
_canvas.DrawLine((float) (_gi.originX),(float) (_y),(float) (_gi.originX+_gi.gw),(float) (_y),_g.AxisColor,(float) (anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (2))));
 };
 //BA.debugLineNum = 143;BA.debugLine="Canvas.DrawText(NumberFormat(yValue, 1, 2), GI.originX - 8dip, y + 5dip,Typeface.DEFAULT, Main.FontSize - 3, G.AxisColor, \"RIGHT\")";
_canvas.DrawText(_ba,anywheresoftware.b4a.keywords.Common.NumberFormat(_yvalue,(int) (1),(int) (2)),(float) (_gi.originX-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (8))),(float) (_y+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))),anywheresoftware.b4a.keywords.Common.Typeface.DEFAULT,(float) (mostCurrent._main._fontsize-3),_g.AxisColor,BA.getEnumFromString(android.graphics.Paint.Align.class,"RIGHT"));
 }
};
 //BA.debugLineNum = 146;BA.debugLine="Canvas.DrawLine(GI.originX+ GI.gw, GI.originY + 1dip, GI.originX + GI.gw, GI.maxY - 1dip, G.AxisColor, 2dip) 'last vertical line";
_canvas.DrawLine((float) (_gi.originX+_gi.gw),(float) (_gi.originY+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (1))),(float) (_gi.originX+_gi.gw),(float) (_gi.maxY-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (1))),_g.AxisColor,(float) (anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (2))));
 //BA.debugLineNum = 148;BA.debugLine="Canvas.DrawText(G.Title, Target.Width / 2, 30dip, Typeface.DEFAULT_BOLD, Main.FontSize - 2, G.AxisColor, \"CENTER\")";
_canvas.DrawText(_ba,_g.Title,(float) (_target.getWidth()/(double)2),(float) (anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (30))),anywheresoftware.b4a.keywords.Common.Typeface.DEFAULT_BOLD,(float) (mostCurrent._main._fontsize-2),_g.AxisColor,BA.getEnumFromString(android.graphics.Paint.Align.class,"CENTER"));
 //BA.debugLineNum = 149;BA.debugLine="Canvas.DrawText(G.XAxis, Target.Width / 2, GI.originY + 60dip, Typeface.DEFAULT, Main.FontSize - 3, G.AxisColor, \"CENTER\")";
_canvas.DrawText(_ba,_g.XAxis,(float) (_target.getWidth()/(double)2),(float) (_gi.originY+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (60))),anywheresoftware.b4a.keywords.Common.Typeface.DEFAULT,(float) (mostCurrent._main._fontsize-3),_g.AxisColor,BA.getEnumFromString(android.graphics.Paint.Align.class,"CENTER"));
 //BA.debugLineNum = 150;BA.debugLine="Canvas.DrawTextRotated(G.YAxis, 15dip, Target.Height / 2, Typeface.DEFAULT, Main.FontSize - 3, G.AxisColor, \"CENTER\", -90)";
_canvas.DrawTextRotated(_ba,_g.YAxis,(float) (anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (15))),(float) (_target.getHeight()/(double)2),anywheresoftware.b4a.keywords.Common.Typeface.DEFAULT,(float) (mostCurrent._main._fontsize-3),_g.AxisColor,BA.getEnumFromString(android.graphics.Paint.Align.class,"CENTER"),(float) (-90));
 //BA.debugLineNum = 152;BA.debugLine="Canvas.DrawLine(GI.originX, GI.originY, GI.originX + GI.gw, GI.originY, G.AxisColor, 2dip)";
_canvas.DrawLine((float) (_gi.originX),(float) (_gi.originY),(float) (_gi.originX+_gi.gw),(float) (_gi.originY),_g.AxisColor,(float) (anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (2))));
 //BA.debugLineNum = 153;BA.debugLine="For i = 0 To Points.Size - 1";
{
final int step120 = 1;
final int limit120 = (int) (_points.getSize()-1);
for (_i = (int) (0); (step120 > 0 && _i <= limit120) || (step120 < 0 && _i >= limit120); _i = ((int)(0 + _i + step120))) {
 //BA.debugLineNum = 154;BA.debugLine="Dim p As LinePoint";
_p = new b4a.example.charts._linepoint();
 //BA.debugLineNum = 155;BA.debugLine="p = Points.Get(i)";
_p = (b4a.example.charts._linepoint)(_points.Get(_i));
 //BA.debugLineNum = 156;BA.debugLine="If p.ShowTick Then";
if (_p.ShowTick) { 
 //BA.debugLineNum = 157;BA.debugLine="Dim x As Int";
_x = 0;
 //BA.debugLineNum = 158;BA.debugLine="If Bars = False Then";
if (_bars==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 159;BA.debugLine="x = GI.originX + i * GI.intervalX";
_x = (int) (_gi.originX+_i*_gi.intervalX);
 //BA.debugLineNum = 160;BA.debugLine="If i < Points.Size - 1 Then";
if (_i<_points.getSize()-1) { 
 //BA.debugLineNum = 161;BA.debugLine="Canvas.DrawLine(x, GI.originY, x, GI.maxY, G.AxisColor, 1dip) 'vertical lines";
_canvas.DrawLine((float) (_x),(float) (_gi.originY),(float) (_x),(float) (_gi.maxY),_g.AxisColor,(float) (anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (1))));
 };
 }else {
 //BA.debugLineNum = 164;BA.debugLine="x = GI.originX + (i - 0.5) * GI.intervalX";
_x = (int) (_gi.originX+(_i-0.5)*_gi.intervalX);
 //BA.debugLineNum = 165;BA.debugLine="Canvas.DrawLine(x, GI.originY, x, GI.originY + 5dip, G.AxisColor, 2dip)";
_canvas.DrawLine((float) (_x),(float) (_gi.originY),(float) (_x),(float) (_gi.originY+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))),_g.AxisColor,(float) (anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (2))));
 };
 //BA.debugLineNum = 167;BA.debugLine="If p.x.Length > 0 Then";
if (_p.X.length()>0) { 
 //BA.debugLineNum = 168;BA.debugLine="Canvas.DrawTextRotated(p.x, x, GI.originY + 12dip, Typeface.DEFAULT, Main.FontSize - 4, G.AxisColor, \"RIGHT\", -45)";
_canvas.DrawTextRotated(_ba,_p.X,(float) (_x),(float) (_gi.originY+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (12))),anywheresoftware.b4a.keywords.Common.Typeface.DEFAULT,(float) (mostCurrent._main._fontsize-4),_g.AxisColor,BA.getEnumFromString(android.graphics.Paint.Align.class,"RIGHT"),(float) (-45));
 };
 };
 }
};
 //BA.debugLineNum = 172;BA.debugLine="End Sub";
return "";
}
public static String  _drawhorizontallinetext(anywheresoftware.b4a.BA _ba,b4a.example.charts._graph _g,b4a.example.charts._bardata _bd,double _y,int _color,float _stroke,String _text,String _align) throws Exception{
float _x1 = 0f;
float _x2 = 0f;
float _y1 = 0f;
 //BA.debugLineNum = 362;BA.debugLine="Sub DrawHorizontalLineText(G As Graph, BD As BarData, y As Double, Color As Int, Stroke As Float, Text As String, Align As String)";
 //BA.debugLineNum = 363;BA.debugLine="Dim x1, x2, y1 As Float";
_x1 = 0f;
_x2 = 0f;
_y1 = 0f;
 //BA.debugLineNum = 364;BA.debugLine="x1 = G.GI.originX";
_x1 = (float) (_g.GI.originX);
 //BA.debugLineNum = 365;BA.debugLine="x2 = G.GI.originX + G.GI.gw";
_x2 = (float) (_g.GI.originX+_g.GI.gw);
 //BA.debugLineNum = 366;BA.debugLine="y1 = G.GI.maxY + (G.YEnd - y) * G.GI.gh / (G.YEnd - G.YStart)";
_y1 = (float) (_g.GI.maxY+(_g.YEnd-_y)*_g.GI.gh/(double)(_g.YEnd-_g.YStart));
 //BA.debugLineNum = 367;BA.debugLine="BD.Canvas.DrawLine(x1, y1, x2, y1, Color, Stroke)";
_bd.Canvas.DrawLine(_x1,_y1,_x2,_y1,_color,_stroke);
 //BA.debugLineNum = 368;BA.debugLine="Select Align";
switch (BA.switchObjectToInt(_align,"LEFT","LEFTUP","CENTERUP","RIGHT")) {
case 0:
 //BA.debugLineNum = 370;BA.debugLine="BD.Canvas.DrawText(Text, x1 - 2dip, y1 + 4dip, Typeface.DEFAULT, Main.FontSize - 3, Color, \"RIGHT\")";
_bd.Canvas.DrawText(_ba,_text,(float) (_x1-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (2))),(float) (_y1+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (4))),anywheresoftware.b4a.keywords.Common.Typeface.DEFAULT,(float) (mostCurrent._main._fontsize-3),_color,BA.getEnumFromString(android.graphics.Paint.Align.class,"RIGHT"));
 break;
case 1:
 //BA.debugLineNum = 372;BA.debugLine="BD.Canvas.DrawText(Text, x1 + 2dip, y1 - 2dip, Typeface.DEFAULT, Main.FontSize - 3, Color, \"LEFT\")";
_bd.Canvas.DrawText(_ba,_text,(float) (_x1+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (2))),(float) (_y1-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (2))),anywheresoftware.b4a.keywords.Common.Typeface.DEFAULT,(float) (mostCurrent._main._fontsize-3),_color,BA.getEnumFromString(android.graphics.Paint.Align.class,"LEFT"));
 break;
case 2:
 //BA.debugLineNum = 374;BA.debugLine="BD.Canvas.DrawText(Text, (x1 + x2) / 2, y1 - 2dip, Typeface.DEFAULT, Main.FontSize - 3, Color, \"CENTER\")";
_bd.Canvas.DrawText(_ba,_text,(float) ((_x1+_x2)/(double)2),(float) (_y1-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (2))),anywheresoftware.b4a.keywords.Common.Typeface.DEFAULT,(float) (mostCurrent._main._fontsize-3),_color,BA.getEnumFromString(android.graphics.Paint.Align.class,"CENTER"));
 break;
case 3:
 //BA.debugLineNum = 376;BA.debugLine="BD.Canvas.DrawText(Text, x2 + 2dip, y1 + 4dip, Typeface.DEFAULT, Main.FontSize - 3, Color, \"LEFT\")";
_bd.Canvas.DrawText(_ba,_text,(float) (_x2+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (2))),(float) (_y1+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (4))),anywheresoftware.b4a.keywords.Common.Typeface.DEFAULT,(float) (mostCurrent._main._fontsize-3),_color,BA.getEnumFromString(android.graphics.Paint.Align.class,"LEFT"));
 break;
}
;
 //BA.debugLineNum = 378;BA.debugLine="BD.Target.Invalidate";
_bd.Target.Invalidate();
 //BA.debugLineNum = 379;BA.debugLine="End Sub";
return "";
}
public static String  _drawlinechart(anywheresoftware.b4a.BA _ba,b4a.example.charts._graph _g,b4a.example.charts._linedata _ld,int _backcolor) throws Exception{
b4a.example.charts._linepoint _point = null;
float[] _py2 = null;
int _i = 0;
int _a = 0;
float _py = 0f;
 //BA.debugLineNum = 201;BA.debugLine="Sub DrawLineChart(G As Graph, LD As LineData, BackColor As Int)";
 //BA.debugLineNum = 202;BA.debugLine="If LD.Points.Size = 0 Then";
if (_ld.Points.getSize()==0) { 
 //BA.debugLineNum = 203;BA.debugLine="ToastMessageShow(\"Missing line points.\", True)";
anywheresoftware.b4a.keywords.Common.ToastMessageShow("Missing line points.",anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 204;BA.debugLine="Return";
if (true) return "";
 };
 //BA.debugLineNum = 206;BA.debugLine="LD.Canvas.Initialize(LD.Target)";
_ld.Canvas.Initialize((android.view.View)(_ld.Target.getObject()));
 //BA.debugLineNum = 207;BA.debugLine="LD.Canvas.DrawColor(BackColor)";
_ld.Canvas.DrawColor(_backcolor);
 //BA.debugLineNum = 209;BA.debugLine="If LD.AutomaticScale = True Then";
if (_ld.AutomaticScale==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 210;BA.debugLine="GetLineMinMaxValues(LD)";
_getlineminmaxvalues(_ba,_ld);
 //BA.debugLineNum = 211;BA.debugLine="CalcScale(G, LD)";
_calcscale(_ba,_g,_ld);
 };
 //BA.debugLineNum = 214;BA.debugLine="drawGraph(G, LD.Canvas, LD.Target, LD.Points, False, 0)";
_drawgraph(_ba,_g,_ld.Canvas,(anywheresoftware.b4a.objects.ConcreteViewWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.ConcreteViewWrapper(), (android.view.View)(_ld.Target.getObject())),_ld.Points,anywheresoftware.b4a.keywords.Common.False,(int) (0));
 //BA.debugLineNum = 216;BA.debugLine="Dim point As LinePoint";
_point = new b4a.example.charts._linepoint();
 //BA.debugLineNum = 217;BA.debugLine="point = LD.Points.Get(0)";
_point = (b4a.example.charts._linepoint)(_ld.Points.Get((int) (0)));
 //BA.debugLineNum = 219;BA.debugLine="If point.YArray.Length > 0 Then";
if (_point.YArray.length>0) { 
 //BA.debugLineNum = 221;BA.debugLine="Dim py2(point.YArray.Length) As Float";
_py2 = new float[_point.YArray.length];
;
 //BA.debugLineNum = 223;BA.debugLine="For i = 0 To py2.Length - 1";
{
final int step178 = 1;
final int limit178 = (int) (_py2.length-1);
for (_i = (int) (0); (step178 > 0 && _i <= limit178) || (step178 < 0 && _i >= limit178); _i = ((int)(0 + _i + step178))) {
 //BA.debugLineNum = 224;BA.debugLine="py2(i) = point.YArray(i)";
_py2[_i] = _point.YArray[_i];
 }
};
 //BA.debugLineNum = 227;BA.debugLine="For i = 1 To LD.Points.Size - 1";
{
final int step181 = 1;
final int limit181 = (int) (_ld.Points.getSize()-1);
for (_i = (int) (1); (step181 > 0 && _i <= limit181) || (step181 < 0 && _i >= limit181); _i = ((int)(0 + _i + step181))) {
 //BA.debugLineNum = 228;BA.debugLine="point = LD.Points.Get(i)";
_point = (b4a.example.charts._linepoint)(_ld.Points.Get(_i));
 //BA.debugLineNum = 229;BA.debugLine="For a = 0 To py2.Length - 1";
{
final int step183 = 1;
final int limit183 = (int) (_py2.length-1);
for (_a = (int) (0); (step183 > 0 && _a <= limit183) || (step183 < 0 && _a >= limit183); _a = ((int)(0 + _a + step183))) {
 //BA.debugLineNum = 230;BA.debugLine="LD.Canvas.DrawLine(G.GI.originX + G.GI.intervalX * (i - 1), calcPointToPixel(py2(a), G), G.GI.originX + G.GI.intervalX * i, calcPointToPixel(point.YArray(a), G), LD.LinesColors.Get(a), 2dip)";
_ld.Canvas.DrawLine((float) (_g.GI.originX+_g.GI.intervalX*(_i-1)),(float) (_calcpointtopixel(_ba,_py2[_a],_g)),(float) (_g.GI.originX+_g.GI.intervalX*_i),(float) (_calcpointtopixel(_ba,_point.YArray[_a],_g)),(int)(BA.ObjectToNumber(_ld.LinesColors.Get(_a))),(float) (anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (2))));
 //BA.debugLineNum = 231;BA.debugLine="py2(a) = point.YArray(a)";
_py2[_a] = _point.YArray[_a];
 }
};
 }
};
 }else {
 //BA.debugLineNum = 236;BA.debugLine="Dim py As Float";
_py = 0f;
 //BA.debugLineNum = 237;BA.debugLine="py = point.Y";
_py = _point.Y;
 //BA.debugLineNum = 238;BA.debugLine="For i = 1 To LD.Points.Size - 1";
{
final int step191 = 1;
final int limit191 = (int) (_ld.Points.getSize()-1);
for (_i = (int) (1); (step191 > 0 && _i <= limit191) || (step191 < 0 && _i >= limit191); _i = ((int)(0 + _i + step191))) {
 //BA.debugLineNum = 239;BA.debugLine="point = LD.Points.Get(i)";
_point = (b4a.example.charts._linepoint)(_ld.Points.Get(_i));
 //BA.debugLineNum = 240;BA.debugLine="LD.Canvas.DrawLine(G.GI.originX + G.GI.intervalX * (i - 1), calcPointToPixel(py, G) _ 				, G.GI.originX + G.GI.intervalX * i, calcPointToPixel(point.Y, G), LD.LinesColors.Get(0), 2dip)";
_ld.Canvas.DrawLine((float) (_g.GI.originX+_g.GI.intervalX*(_i-1)),(float) (_calcpointtopixel(_ba,_py,_g)),(float) (_g.GI.originX+_g.GI.intervalX*_i),(float) (_calcpointtopixel(_ba,_point.Y,_g)),(int)(BA.ObjectToNumber(_ld.LinesColors.Get((int) (0)))),(float) (anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (2))));
 //BA.debugLineNum = 242;BA.debugLine="py = point.Y";
_py = _point.Y;
 }
};
 };
 //BA.debugLineNum = 245;BA.debugLine="LD.Target.Invalidate";
_ld.Target.Invalidate();
 //BA.debugLineNum = 246;BA.debugLine="End Sub";
return "";
}
public static anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper  _drawpie(anywheresoftware.b4a.BA _ba,b4a.example.charts._piedata _pd,int _backcolor,boolean _createlegendbitmap) throws Exception{
int _radius = 0;
int _total = 0;
int _i = 0;
b4a.example.charts._pieitem _it = null;
float _startingangle = 0f;
int _gapdegrees = 0;
 //BA.debugLineNum = 269;BA.debugLine="Sub DrawPie (PD As PieData, BackColor As Int, CreateLegendBitmap As Boolean) As Bitmap";
 //BA.debugLineNum = 270;BA.debugLine="If PD.Items.Size = 0 Then";
if (_pd.Items.getSize()==0) { 
 //BA.debugLineNum = 271;BA.debugLine="ToastMessageShow(\"Missing pie values.\", True)";
anywheresoftware.b4a.keywords.Common.ToastMessageShow("Missing pie values.",anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 272;BA.debugLine="Return Null";
if (true) return (anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper(), (android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null));
 };
 //BA.debugLineNum = 274;BA.debugLine="PD.Canvas.Initialize(PD.Target)";
_pd.Canvas.Initialize((android.view.View)(_pd.Target.getObject()));
 //BA.debugLineNum = 275;BA.debugLine="PD.Canvas.DrawColor(BackColor)";
_pd.Canvas.DrawColor(_backcolor);
 //BA.debugLineNum = 276;BA.debugLine="Dim Radius As Int";
_radius = 0;
 //BA.debugLineNum = 277;BA.debugLine="Radius = Min(PD.Canvas.Bitmap.Width, PD.Canvas.Bitmap.Height) * 0.8 / 2";
_radius = (int) (anywheresoftware.b4a.keywords.Common.Min(_pd.Canvas.getBitmap().getWidth(),_pd.Canvas.getBitmap().getHeight())*0.8/(double)2);
 //BA.debugLineNum = 278;BA.debugLine="Dim total As Int";
_total = 0;
 //BA.debugLineNum = 279;BA.debugLine="For i = 0 To PD.Items.Size - 1";
{
final int step226 = 1;
final int limit226 = (int) (_pd.Items.getSize()-1);
for (_i = (int) (0); (step226 > 0 && _i <= limit226) || (step226 < 0 && _i >= limit226); _i = ((int)(0 + _i + step226))) {
 //BA.debugLineNum = 280;BA.debugLine="Dim it As PieItem";
_it = new b4a.example.charts._pieitem();
 //BA.debugLineNum = 281;BA.debugLine="it = PD.Items.Get(i)";
_it = (b4a.example.charts._pieitem)(_pd.Items.Get(_i));
 //BA.debugLineNum = 282;BA.debugLine="total = total + it.Value";
_total = (int) (_total+_it.Value);
 }
};
 //BA.debugLineNum = 284;BA.debugLine="Dim startingAngle As Float";
_startingangle = 0f;
 //BA.debugLineNum = 285;BA.debugLine="startingAngle = 0";
_startingangle = (float) (0);
 //BA.debugLineNum = 286;BA.debugLine="Dim GapDegrees As Int";
_gapdegrees = 0;
 //BA.debugLineNum = 287;BA.debugLine="If PD.Items.Size = 1 Then GapDegrees = 0 Else GapDegrees = PD.GapDegrees";
if (_pd.Items.getSize()==1) { 
_gapdegrees = (int) (0);}
else {
_gapdegrees = _pd.GapDegrees;};
 //BA.debugLineNum = 288;BA.debugLine="For i = 0 To PD.Items.Size - 1";
{
final int step235 = 1;
final int limit235 = (int) (_pd.Items.getSize()-1);
for (_i = (int) (0); (step235 > 0 && _i <= limit235) || (step235 < 0 && _i >= limit235); _i = ((int)(0 + _i + step235))) {
 //BA.debugLineNum = 289;BA.debugLine="Dim it As PieItem";
_it = new b4a.example.charts._pieitem();
 //BA.debugLineNum = 290;BA.debugLine="it = PD.Items.Get(i)";
_it = (b4a.example.charts._pieitem)(_pd.Items.Get(_i));
 //BA.debugLineNum = 291;BA.debugLine="startingAngle = startingAngle + _  			calcSlice(PD.Canvas, Radius, startingAngle, it.Value / total, it.Color, GapDegrees)";
_startingangle = (float) (_startingangle+_calcslice(_ba,_pd.Canvas,_radius,_startingangle,(float) (_it.Value/(double)_total),_it.Color,_gapdegrees));
 }
};
 //BA.debugLineNum = 294;BA.debugLine="PD.Target.Invalidate";
_pd.Target.Invalidate();
 //BA.debugLineNum = 295;BA.debugLine="If CreateLegendBitmap Then";
if (_createlegendbitmap) { 
 //BA.debugLineNum = 296;BA.debugLine="Return createLegend(PD)";
if (true) return _createlegend(_ba,_pd);
 }else {
 //BA.debugLineNum = 298;BA.debugLine="Return Null";
if (true) return (anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper(), (android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null));
 };
 //BA.debugLineNum = 300;BA.debugLine="End Sub";
return null;
}
public static double[]  _getlineminmaxvalues(anywheresoftware.b4a.BA _ba,b4a.example.charts._linedata _ld) throws Exception{
int _j = 0;
b4a.example.charts._linepoint _points = null;
double[] _minmax = null;
int _i = 0;
float[] _yvals = null;
 //BA.debugLineNum = 384;BA.debugLine="Sub GetLineMinMaxValues(LD As LineData) As Double()";
 //BA.debugLineNum = 385;BA.debugLine="Dim j, j As Int";
_j = 0;
_j = 0;
 //BA.debugLineNum = 386;BA.debugLine="Dim points As LinePoint";
_points = new b4a.example.charts._linepoint();
 //BA.debugLineNum = 387;BA.debugLine="Dim MinMax(2) As Double";
_minmax = new double[(int) (2)];
;
 //BA.debugLineNum = 388;BA.debugLine="MinMax(1) = -1E10";
_minmax[(int) (1)] = -1e10;
 //BA.debugLineNum = 389;BA.debugLine="MinMax(0) = 1E10";
_minmax[(int) (0)] = 1e10;
 //BA.debugLineNum = 391;BA.debugLine="For i = 0 To LD.points.Size - 1";
{
final int step312 = 1;
final int limit312 = (int) (_ld.Points.getSize()-1);
for (_i = (int) (0); (step312 > 0 && _i <= limit312) || (step312 < 0 && _i >= limit312); _i = ((int)(0 + _i + step312))) {
 //BA.debugLineNum = 392;BA.debugLine="Dim YVals() As Float";
_yvals = new float[(int) (0)];
;
 //BA.debugLineNum = 393;BA.debugLine="points = LD.points.Get(i)";
_points = (b4a.example.charts._linepoint)(_ld.Points.Get(_i));
 //BA.debugLineNum = 394;BA.debugLine="If points.YArray.Length > 0 Then";
if (_points.YArray.length>0) { 
 //BA.debugLineNum = 396;BA.debugLine="YVals = points.YArray";
_yvals = _points.YArray;
 //BA.debugLineNum = 397;BA.debugLine="For j = 0 To points.YArray.Length - 1";
{
final int step317 = 1;
final int limit317 = (int) (_points.YArray.length-1);
for (_j = (int) (0); (step317 > 0 && _j <= limit317) || (step317 < 0 && _j >= limit317); _j = ((int)(0 + _j + step317))) {
 //BA.debugLineNum = 398;BA.debugLine="MinMax(1) = Max(MinMax(1),YVals(j))";
_minmax[(int) (1)] = anywheresoftware.b4a.keywords.Common.Max(_minmax[(int) (1)],_yvals[_j]);
 //BA.debugLineNum = 399;BA.debugLine="MinMax(0) = Min(MinMax(0),YVals(j))";
_minmax[(int) (0)] = anywheresoftware.b4a.keywords.Common.Min(_minmax[(int) (0)],_yvals[_j]);
 }
};
 }else {
 //BA.debugLineNum = 403;BA.debugLine="MinMax(1) = Max(MinMax(1),points.Y)";
_minmax[(int) (1)] = anywheresoftware.b4a.keywords.Common.Max(_minmax[(int) (1)],_points.Y);
 //BA.debugLineNum = 404;BA.debugLine="MinMax(0) = Min(MinMax(0),points.Y)";
_minmax[(int) (0)] = anywheresoftware.b4a.keywords.Common.Min(_minmax[(int) (0)],_points.Y);
 };
 }
};
 //BA.debugLineNum = 407;BA.debugLine="Return MinMax";
if (true) return _minmax;
 //BA.debugLineNum = 408;BA.debugLine="End Sub";
return null;
}
public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 4;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 13;BA.debugLine="Type PieItem (Name As String, Value As Float, Color As Int)";
;
 //BA.debugLineNum = 14;BA.debugLine="Type PieData (Items As List, Target As Panel, Canvas As Canvas, GapDegrees As Int, _ 		LegendTextSize As Float, LegendBackColor As Int)";
;
 //BA.debugLineNum = 16;BA.debugLine="Type GraphInternal (originX As Int, zeroY As Int, originY As Int, maxY As Int, intervalX As Float, gw As Int, gh As Int)";
;
 //BA.debugLineNum = 19;BA.debugLine="Type Graph (GI As GraphInternal, Title As String, YAxis As String, XAxis As String, YStart As Float, _  		YEnd As Float, YInterval As Float, AxisColor As Int, NbIntervals As Int)";
;
 //BA.debugLineNum = 21;BA.debugLine="Type LinePoint (X As String, Y As Float, YArray() As Float, ShowTick As Boolean)";
;
 //BA.debugLineNum = 23;BA.debugLine="Type LineData (Points As List, LinesColors As List, Target As Panel, Canvas As Canvas, AutomaticScale As Boolean)";
;
 //BA.debugLineNum = 24;BA.debugLine="Type BarData (Points As List, BarsColors As List, Target As Panel, Canvas As Canvas, Stacked As Boolean, BarsWidth As Int)";
;
 //BA.debugLineNum = 25;BA.debugLine="End Sub";
return "";
}
}
