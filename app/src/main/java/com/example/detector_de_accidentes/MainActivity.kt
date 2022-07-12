package com.example.detector_de_accidentes

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.detector_de_accidentes.ui.theme.DetectorDeAccidentesTheme
import com.example.detector_de_accidentes.firebase.models.User
import com.example.detector_de_accidentes.firebase.repository.UserRepository
import com.example.detector_de_accidentes.firebase.service.AuthService
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DetectorDeAccidentesTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    BuildContentRegister()
                }
            }
        }
    }
}

@Composable
fun BuildContentRegister() {
    val mContext = LocalContext.current
    Scaffold {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(horizontal = 32.dp)
        ) {
            val inputNameState = remember { mutableStateOf(TextFieldValue()) }
            OutlinedTextField(
                value = inputNameState.value,
                onValueChange = { inputNameState.value = it },
                label = { Text(text = "Nombres y Apellidos") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = null
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
            )
            val inputEmailState = remember { mutableStateOf(TextFieldValue()) }
            OutlinedTextField(
                value = inputEmailState.value,
                onValueChange = { inputEmailState.value = it },
                label = { Text(text = "Email") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Email,
                        contentDescription = null
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
            )
            val inputPwdState = remember { mutableStateOf(TextFieldValue()) }
            OutlinedTextField(
                value = inputPwdState.value,
                onValueChange = { inputPwdState.value = it },
                label = { Text(text = "Constraseña") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Lock,
                        contentDescription = null
                    )
                },
                modifier = Modifier.fillMaxWidth()
            )
            Button(
                onClick = {
                    register(
                        inputNameState.value.text, inputEmailState.value.text,
                        inputPwdState.value.text, mContext
                    )
                },
                modifier = Modifier
                    .width(220.dp)
                    .padding(top = 24.dp)
            ) {
                Text(text = "Registrar")
            }
        }
    }
}

fun register(fullName: String, email: String, password: String, context: Context) {
    val user = User(
        fullname = fullName,
        email = email
    )
    val auth2: Task<AuthResult> =
        AuthService.firebaseRegister(email, password)
    auth2.addOnCompleteListener { task ->
        if (task.isSuccessful) {
            UserRepository.saveUser(user)
            Toast.makeText(context, "Successful register", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    DetectorDeAccidentesTheme {
        BuildContentRegister()
    }
}