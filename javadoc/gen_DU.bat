@REM generate DecisionUnits
"C:\Program Files\Java\jdk1.6.0_20\bin"\javadoc ^
	-d s:\ARSIN_V01\javadoc\website\DecisionUnits ^
	-sourcepath S:\ARSIN_V01\DecisionUnits\src ^
	-overview S:\ARSIN_V01\DecisionUnits\src\overview.html ^
	-subpackages decisionunit:pa:pa._v38:pa.enums: ^
	-exclude pa._v30:pa._v19: ^
    -use ^
    -splitIndex ^
    -version ^
    -author ^
    -doctitle "Decision Unit v38" ^
    -private ^
	-quiet		
	