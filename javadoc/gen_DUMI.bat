@REM generate DecisionUnitMasonInspectors
"C:\Program Files\Java\jdk1.6.0_20\bin"\javadoc ^
	-d s:\ARSIN_V01\javadoc\website\DecisionUnitMasonInspectors ^
	-sourcepath S:\ARSIN_V01\DecisionUnitMasonInspectors\src ^
	-overview S:\ARSIN_V01\DecisionUnitMasonInspectors\src\overview.html ^
	-subpackages inspectors:resources: ^
    -use ^
    -splitIndex ^
    -version ^
    -author ^
    -doctitle "Decision Unit Inspectors" ^
    -private ^
	-quiet		
	