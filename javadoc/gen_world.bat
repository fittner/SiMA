@REM generate BW
"C:\Program Files\Java\jdk1.6.0_20\bin"\javadoc ^
	-d s:\ARSIN_V01\javadoc\website\BW ^
	-sourcepath S:\ARSIN_V01\World\src ^
	-overview S:\ARSIN_V01\BW\World\overview.html ^
	-subpackages ARSsim:bw:resources: ^
    -use ^
    -splitIndex ^
    -version ^
    -author ^
    -doctitle "BW - Bubble World" ^
	-quiet
	