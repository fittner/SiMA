@REM generate DecisionUnitInterface
"C:\Program Files\Java\jdk1.6.0_20\bin"\javadoc ^
	-d s:\ARSIN_V01\javadoc\website\DecisionUnitInterface ^
	-sourcepath S:\ARSIN_V01\DecisionUnitInterface\src ^
	-overview S:\ARSIN_V01\DecisionUnitInterface\src\overview.html ^
	-subpackages bfg:config:du:factories:panels:statictools: ^
    -use ^
    -splitIndex ^
    -version ^
    -author ^
    -doctitle "Decision Unit Interfaces and Static/Global Tools" ^
	-quiet	
	