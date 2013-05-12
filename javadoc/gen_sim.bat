@REM generate Sim
"C:\Program Files\Java\jdk1.6.0_20\bin"\javadoc ^
	-d s:\ARSIN_V01\javadoc\website\Sim ^
	-sourcepath S:\ARSIN_V01\Sim\src ^
	-subpackages ARSsim:sim:creation: ^
    -use ^
    -splitIndex ^
    -version ^
    -author ^
    -doctitle "Sim" ^
	-overview S:\ARSIN_V01\Sim\src\overview.html ^
	-quiet	