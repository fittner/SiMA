@REM generate DecisionUnitInterface
"C:\Program Files\Java\jdk1.6.0_20\bin"\javadoc ^
	-d s:\javadoc\DecisionUnitInterface ^
	-sourcepath S:\ARSIN_V01\DecisionUnitInterface\src ^
	-subpackages bfg:config:du:factories:panels:statictools: ^
    -use ^
    -splitIndex ^
    -version ^
    -author ^
    -doctitle "Decision Unit Interfaces and Static/Global Tools" ^
    -private ^
	-quiet	
	