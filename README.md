# Android-File-Picker-With-Scoped-Storage
Android file picker for all android versions


#### Pick file
```kotlin
    val fileIntent = Intent(Intent.ACTION_GET_CONTENT)
    fileIntent.addCategory(Intent.CATEGORY_OPENABLE)
    fileIntent.type = "*/*"
    startActivityForResult(fileIntent, REQUEST_FILE)
```