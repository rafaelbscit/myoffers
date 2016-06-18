#!/bin/bash
# Create new package to app MyOffers

echo "Copy to apk file"
cp ./app/build/outputs/apk/app-debug.apk ./app/build/outputs/apk/MyOffers.apk

echo "Zip to apk"
rm -rf MyOffers.zip
zip MyOffers.zip ./app/build/outputs/apk/MyOffers.apk

echo "Add apk to git"
cp ./app/build/outputs/apk/app-debug.apk ./MyOffers-beta.apk
git add --force ./MyOffers-beta.apk

echo "Add to git"
git add ./MyOffers.zip
git ci -m "[rafaelbs] Generate new version MyOffers.zip"

echo "finish"
