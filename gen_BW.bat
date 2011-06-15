@REM generate BW
"C:\Program Files\Java\jdk1.6.0_20\bin"\javadoc ^
	-d s:\javadoc\BW ^
	-sourcepath S:\ARSIN_V01\BW\src ^
	-subpackages ARSsim:bw:resources: ^
    -use ^
    -splitIndex ^
    -version ^
    -author ^
    -doctitle "BW - Bubble World" ^
    -private ^
	-quiet
	