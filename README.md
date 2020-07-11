# Android Social Media Application <img src="/app/src/main/ic_launcher-playstore.png" alt="Android Social Media Application" width="80" align="center"/>

[](https://img.shields.io/github/issues/DrkXo/Android-Social-Media-Application)
> ***Final year project of B.tech in C.S.E. In way of creating this project developer.android, GitHub, stackoverflow was our guide.***
 
## System Installation Steps
- Download our project from [Here](https://github.com/DrkXo/Android-Social-Media-Application).
- Download [Android Studio](https://developer.android.com/studio).
- Open the project in Android Studio.
- You need google-services.json. Create a Firebase project in the [Firebase console](https://console.firebase.google.com), if you don't already have one. Go to your
project and click ‘Add Firebase to your Android app’. Follow the setup steps. At
the end, you'll download a google-services.json file which you should add to your
project folder /app/ .
- Setup Realtime database. In firebase console go to DEVELOP->Database-> Get
Started -> choose tab ‘RULES’ and past this:
 ```
- {
− "rules": {
− ".read": "true",
− ".write": "true"
− }
− }
```
- If you haven't yet specified your app's SHA-1 fingerprint, do so from the Settings
page Settings page of the Firebase console. See Authenticating Your Client for
details on how to get your app's SHA-1 fingerprint.
- Deploy Firebase functions in your app from Tools-Firebase window. It will
generate necessary resource file in your res(generated)/values/ .
- Get your Google Map API key from here (https://console.cloud.google.com). Then
navigate to AndroidManifest and paste your API key there.
- Deploy Firebase functions in Android Studio Project –> Tools –> Firebase.
- After you have successfully connected you application with Google Firebase and
Google Map API.
## System Usage Instructions
- Open Project in android studio.
- Go to Build then ->
  - Clean Project.
  - Rebuild Project.

