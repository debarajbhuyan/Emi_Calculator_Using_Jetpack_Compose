package mata.devraj.emicalculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import mata.devraj.emicalculator.ui.theme.EmiCalculatorTheme
import java.util.Locale

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            EmiCalculatorTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    CalculatorApp(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun CalculatorApp(modifier: Modifier = Modifier) {
    EmiCalculatorScreen()
}
@Composable
fun EmiCalculatorScreen() {
    var principal by remember { mutableStateOf("") }
    var interestRate by remember { mutableStateOf("") }
    var tenure by remember { mutableStateOf("") }
    var emiResult by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        OutlinedTextField(
            value = principal,
            onValueChange = { principal = it },
            label = { Text("Principal Amount") }
        )
        OutlinedTextField(
            value = interestRate,
            onValueChange = { interestRate = it },
            label = { Text("Annual Interest Rate (%)") }
        )
        OutlinedTextField(
            value = tenure,
            onValueChange = { tenure = it },
            label = { Text("Loan Tenure (Months)") }
        )
        Button(onClick = {
            // Perform EMI calculation here
            val p = principal.toDoubleOrNull() ?: 0.0
            val r = interestRate.toDoubleOrNull() ?: 0.0
            val n = tenure.toIntOrNull() ?: 0

            if (p > 0 && r > 0 && n > 0) {
                val monthlyRate = r / 12 / 100
                val emi = (p * monthlyRate * Math.pow(1 + monthlyRate, n.toDouble())) / (Math.pow(1 + monthlyRate, n.toDouble()) - 1)
                emiResult = String.format(Locale.getDefault(), "%.2f", emi) // Format to 2 decimal places

                /* emiResult = String.format("%.2f", emi) -- not write directly

                 Use "Locale.getDefault()" to get the default locale for the current device.
                 Now The fresh code will be:
                 emiResult = String.format(Locale.getDefault(), "%.2f", emi)

                 Note:- Different locales have different conventions for formatting numbers, especially when it comes to decimal separators and grouping separators (like commas or periods). If you implicitly rely on the default locale, your app might display numbers incorrectly for users in other regions, leading to confusion or even errors

                 Explanation:
                    Locale.getDefault():- This retrieves the user's current locale setting on their device.
                    String.format(Locale.getDefault(), "%.2f", emi):- This formats the EMI value (emi) to two decimal places using the user's locale, ensuring that the number is displayed in a way that's familiar and correct for them. Alternative: If you want to target a specific locale (e.g., US English), you can use Locale.US instead of Locale.getDefault().

                     Best Practices:
                    Consider User Preferences: Ideally, allow users to choose their preferred locale within your app settings.
                    Test with Different Locales: Always test your app with different locale settings to ensure numbers are displayed correctly for all users. By explicitly handling locales, you'll make your EMI calculator more robust, user-friendly, and accessible to a global audience. Let me know if you have any other questions or need further clarification!
                 */
            } else {
                emiResult = "Invalid input"
            }
        }) {
            Text("Calculate EMI")
        }
        if (emiResult.isNotEmpty()) {
            Text("EMI: $emiResult")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    EmiCalculatorTheme {
        CalculatorApp()
    }
}