
"C:\Program Files\Java\jdk1.6.0_20\bin"\javadoc ^
	-d s:\javadoc ^
	-sourcepath S:\ARS\PA\BWv1\DecisionUnitInterface\src;S:\ARS\PA\BWv1\BW\src;S:\ARS\PA\BWv1\DecisionUnitMasonInspectors\src;S:\ARS\PA\BWv1\DecisionUnits\src; ^
	-subpackages bw:ARSsim:resources:sim:bfg:config:decisionunit:du:enums:factories:statictools:inspectors:resources:bfg:lifeCycle:LocalizationOrientation:memory:pa:resources:simple:symbolization: ^
        -use ^
        -splitIndex ^
        -version ^
        -author ^
        -doctitle "BW - Bubble World" ^
        -private ^
	-quiet

@REM	-d "\\jupiter\ars.ict.tuwien.ac.at\apache\javadoc" ^
@REM unfortunately, javadoc for windows sanitzes the path name and replaces  \\ with \ -> samba resouce
@REM is not accessible

@REM	-sourcepath S:\ARS\PA\BWv1\DecisionUnitInterface\src;S:\ARS\PA\BWv1\BW\src;S:\ARS\PA\BWv1\DecisionUnitMasonInspectors\src;S:\ARS\PA\BWv1\DecisionUnits\src; ^
