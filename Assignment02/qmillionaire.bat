@echo off
REM qmillionaire.bat
REM CST8221 - JAP - Assignment 02
REM Author:      Andrei Cojocaru, 041032238
REM Professor:  Dr. James Mwangi
echo '............................................'
echo '..     ALGONQUIN COLLEGE - JAP - 26S      ..'
echo '............................................'
echo '                  [JAVAFX]                  '
echo '  ........=====...........................  '
echo '  ......=+===.............................  '
echo '  ......===+=........=+...................  '
echo '  .......===.........=+...=+.=+..+=...+=..  '
echo '  ...=========....==.=+.+=.=.+=+===.+=.=..  '
echo '  ....==========...==+..====...==...====..  '
echo '  ..==.=======............................  '
echo '  ...=============........................  '
echo '.                                           '
echo '.            [Andrei Cojocaru]              '
echo '............................................'

REM Set up directories
set SRC_DIR=src
set DOC_DIR=doc
set BIN_DIR=bin
set RES_DIR=resources
set IMG_DIR=images
set MAIN_CLASS=qmillionaire.QuantumMillionaire
set JAR_NAME=QuantumMillionaire.jar

REM === PATH TO JAVAFX SDK ===
REM Uses the JavaFXPath environment variable provided by the marking machine.
set JAVAFX_LIB=%JavaFXPath%
REM <<< MY PATH >>> local SDK used during development (Andrei) - uncomment to build offline:
REM set JAVAFX_LIB=..\javafx-sdk-21.0.5\lib

REM === SOURCE FILE LIST (MVC: root + model + view + controller) ===
set SOURCES=%SRC_DIR%\qmillionaire\*.java %SRC_DIR%\qmillionaire\model\*.java %SRC_DIR%\qmillionaire\view\*.java %SRC_DIR%\qmillionaire\controller\*.java

echo 'Starting Javadoc ...........................'
REM Clean previous doc
echo Cleaning previous javadoc...
rd /s /q %DOC_DIR%
javadoc --module-path "%JAVAFX_LIB%" --add-modules javafx.controls,javafx.fxml -d %DOC_DIR% -sourcepath %SRC_DIR% -subpackages qmillionaire

echo 'Compiling ..................................'
REM Clean previous build
echo Cleaning previous build...
rd /s /q %BIN_DIR%
mkdir %BIN_DIR%
REM === COMPILE JAVA SOURCE FILES WITH JAVAFX MODULES ===
echo Compiling JavaFX project...
javac --module-path "%JAVAFX_LIB%" --add-modules javafx.controls,javafx.fxml -d %BIN_DIR% %SOURCES%

echo 'Creating Jar ...............................'
REM Copy resources and images to bin
echo Copying resources and images...
xcopy /s /y /i %RES_DIR% %BIN_DIR%\resources\
xcopy /s /y /i %IMG_DIR% %BIN_DIR%\images\
REM Create manifest file
echo Creating manifest...
echo Main-Class: %MAIN_CLASS% > manifest.txt
cd %BIN_DIR%
REM Create JAR file
jar cfm ../%JAR_NAME% ../manifest.txt *
cd ..
REM Clean up manifest
del manifest.txt

REM Run the JAR
echo 'Running Jar ................................'
REM === RUN THE JAR WITH JAVAFX MODULES ===
echo Running JavaFX application...
java --module-path "%JAVAFX_LIB%" -Djava.library.path="%JAVAFX_LIB%\bin" --add-modules javafx.controls,javafx.fxml --enable-native-access=javafx.graphics -jar %JAR_NAME%

echo 'End ........................................'
