#!/bin/bash
# qmillionaire.sh
# CST8221 - JAP - Assignment 02
# Author:      Andrei Cojocaru, 041032238
# Professor:  Dr. James Mwangi
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

# Set up directories
SRC_DIR=src
DOC_DIR=doc
BIN_DIR=bin
RES_DIR=resources
IMG_DIR=images
MAIN_CLASS=qmillionaire.QuantumMillionaire
JAR_NAME=QuantumMillionaire.jar

# === PATH TO JAVAFX SDK ===
# Uses the JavaFXPath environment variable if set (marking machine), otherwise falls back to local SDK.
if [ -n "$JavaFXPath" ]; then
    JAVAFX_LIB=$JavaFXPath
else
    JAVAFX_LIB=../javafx-sdk-21.0.5/lib
fi

# === SOURCE FILE LIST (MVC: root + model + view + controller) ===
SOURCES="$SRC_DIR/qmillionaire/*.java $SRC_DIR/qmillionaire/model/*.java $SRC_DIR/qmillionaire/view/*.java $SRC_DIR/qmillionaire/controller/*.java"

echo 'Starting Javadoc ...........................'
# Clean previous doc
echo Cleaning previous javadoc...
rm -rf $DOC_DIR
javadoc --module-path "$JAVAFX_LIB" --add-modules javafx.controls,javafx.fxml -d $DOC_DIR -sourcepath $SRC_DIR -subpackages qmillionaire

echo 'Compiling ..................................'
# Clean previous build
echo Cleaning previous build...
rm -rf $BIN_DIR
mkdir -p $BIN_DIR
# === COMPILE JAVA SOURCE FILES WITH JAVAFX MODULES ===
echo Compiling JavaFX project...
javac --module-path "$JAVAFX_LIB" --add-modules javafx.controls,javafx.fxml -d $BIN_DIR $SOURCES

echo 'Creating Jar ...............................'
# Copy resources and images to bin
echo Copying resources and images...
cp -r $RES_DIR $BIN_DIR/resources/
cp -r $IMG_DIR $BIN_DIR/images/
# Create manifest file
echo Creating manifest...
echo "Main-Class: $MAIN_CLASS" > manifest.txt
cd $BIN_DIR
# Create JAR file
jar cfm ../$JAR_NAME ../manifest.txt *
cd ..
# Clean up manifest
rm manifest.txt

# Run the JAR
echo 'Running Jar ................................'
# === RUN THE JAR WITH JAVAFX MODULES ===
echo Running JavaFX application...
java --module-path "$JAVAFX_LIB" -Djava.library.path="$JAVAFX_LIB" --add-modules javafx.controls,javafx.fxml --enable-native-access=javafx.graphics -jar $JAR_NAME

echo 'End ........................................'
