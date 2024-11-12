package com.example.idealsize

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.idealsize.ui.theme.IdealSizeTheme
import androidx.compose.ui.window.Dialog

class InputActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            IdealSizeTheme {
                InputScreen(
                    // TODO send calculated Size back to caller
                    onClose = { finish() } // Close the activity
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InputScreen(onClose: () -> Unit) {
    var height by remember { mutableStateOf("") }
    var weight by remember { mutableStateOf("") }
    var recommendedSize by remember { mutableStateOf(SizeType.S) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var showResultDialog by remember { mutableStateOf(false) }

    // "Get Recommendation" button handler
    fun recommendButtonClick() {
        val heightValue = height.toFloatOrNull()
        val weightValue = weight.toFloatOrNull()

        if (heightValue == null || weightValue == null || heightValue <= 0 || weightValue <= 0) {
            errorMessage = "Height and weight should be positive numbers."
            return;
        }

        errorMessage = null

        // Calculate BMI: BMI = weight (kg) / (height (m) * height (m))
        val heightInMeters = heightValue / 100
        val bmi = weightValue / (heightInMeters * heightInMeters)

        try {
            recommendedSize = IdealSize.sizeByBMI(bmi)
            showResultDialog = true
        } catch (e: InvalidBodyMassIndexException) {
            errorMessage = "Height or Weight are outside of natural body parameters"
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Find Your Perfect Fit ") },
                actions = {
                    TextButton(onClick = { onClose() }) {
                        Text("Close")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.weight(1f))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(16.dp)
            ) {
                Text("Height: ",
                    modifier = Modifier.width(70.dp),
                    style = MaterialTheme.typography.bodyLarge,
                )

                OutlinedTextField(
                    value = height,
                    onValueChange = { height = it },
                    modifier = Modifier.width(200.dp),
                    label = { Text("Height") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
                )

                Text(
                    text = "cm",
                    modifier = Modifier.padding(start = 8.dp),
                    style = MaterialTheme.typography.bodyLarge
                )
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(16.dp)
            ) {
                Text("Weight: ",
                    modifier = Modifier.width(70.dp),
                    style = MaterialTheme.typography.bodyLarge)

                OutlinedTextField(
                    value = weight,
                    onValueChange = { weight = it },
                    modifier = Modifier.width(200.dp),
                    label = { Text("Weight") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
                )

                Text(
                    text = "kg",
                    modifier = Modifier.padding(start = 8.dp),
                    style = MaterialTheme.typography.bodyLarge
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = {
                    recommendButtonClick()
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Get Size Recommendation")
            }

            errorMessage?.let {
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = it, color = MaterialTheme.colorScheme.error)
            }

            if (showResultDialog) {
                ResultScreen(recommendedSize, onDismiss = {
                    onClose()
                })
            }
        }
    }
}

@Composable
fun ResultScreen(size: SizeType, onDismiss: () -> Unit) {
    Dialog(onDismissRequest = {}) {
        Surface {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
            ) {
                Text("Based on your info, size $size is recommended", style = MaterialTheme.typography.titleLarge)

                Spacer(modifier = Modifier.height(16.dp))

                Text(text = size.toString(), style = MaterialTheme.typography.headlineMedium)

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = { onDismiss() },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("OK")
                }
            }
        }
    }
}
