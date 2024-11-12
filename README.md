# Overview

This is a simple SDK that recommends body size absed based on Body Mass Index (BMI).


# Requirements

Android SDK 28+

# Installation

### Gradle

Add the IdealSize SDK to your Android project by including it in your Gradle dependencies.

In your project’s `settings.gradle` file, add the repository link:

```groovy
dependencyResolutionManagement {
    repositories {
        mavenCentral()
        google()
        maven { url "https://github.com/alexshikov/IdealSize-Android" }
    }
}
```

Then, in your module's build.gradle file, add the dependency:

```groovy
dependencies {
    implementation "com.example:ideal-size:1.0.0"
}
```

Run ./gradlew build to sync and build the project.


# Usage

### Get Recommendation from known Body Mass Index

```kotlin
import com.example.idealsize.IdealSize
import com.example.idealsize.SizeType

try {
    val size = IdealSize.sizeByBMI(18.0f)
    println("Recommended size: $size")
} catch (e: InvalidBodyMassIndexException) {
    println("BMI value is out of range.")
}
```

### Show Recommendations View

In your MainActivity, you can launch the IdealSize InputActivity as a separate screen:

```kotlin
// other imports
import com.example.idealsize.InputActivity

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainScreen()
        }
    }
}

@Composable
fun MainScreen() {
    val context = LocalContext.current

    Scaffold { padding ->
        Box(
            modifier = Modifier.fillMaxSize().padding(padding),
            contentAlignment = Alignment.Center
        ) {
            Button(onClick = {
                // Start InputActivity
                context.startActivity(Intent(context, InputActivity::class.java))
            }) {
                Text("Get Recommendations")
            }
        }
    }
}
```


# Roadmap

1. Add Maven Central publishing
2. ...

# Contribution

(Coming soon)

# Licence

MIT. See [License](./LICENSE)