#!/bin/bash
# Installs IntelliJ templates

echo "Installing template files..."

find /Applications -path "*.app" -prune \( -name "*Android Studio*"  \) -print0 | while read -d $'\0' folder
do
  echo "\nInstalling to $folder"
  cp -frv $( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )/MVPFenXiaoActivity "$folder/Contents/plugins/android/lib/templates/activities/"
done


echo "Done."
echo ""
echo "Restart AndroidStudio"
