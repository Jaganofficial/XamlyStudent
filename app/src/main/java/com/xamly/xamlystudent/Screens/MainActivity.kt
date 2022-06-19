package com.xamly.xamlystudent.Screens

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.xamly.xamlystudent.R
import com.xamly.xamlystudent.Screens.ScheduledExams.ScheduledExams

class MainActivity : ComponentActivity() {
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        auth = Firebase.auth

        val user = auth.currentUser

        setContent {
//            val studentEmail = remember {
//                mutableStateOf("")
//            }
//            val studentPassword = remember{
//                mutableStateOf("")
//            }
//            XamlyStudentTheme {
//
//                if (user != null) {
//                    CircularProgressIndicator(modifier = Modifier.size(40.dp))
//                    intent = Intent(this@MainActivity, ScheduledExams::class.java)
//                    startActivity(intent)
//                } else {
//
//                    Column(modifier = Modifier
//                        .fillMaxSize()
//                        .padding(10.dp)) {
//
//                        Text(text = "Enter the student email", modifier = Modifier.padding(15.dp))
//
//                        OutlinedTextField(value = studentEmail.value, onValueChange = {
//                            studentEmail.value = it
//                        })
//
//                        Divider(modifier = Modifier.height(15.dp))
//
//                        Text(
//                            text = "Enter the studentEmail password",
//                            modifier = Modifier.padding(15.dp)
//                        )
//
//
//                        OutlinedTextField(value = studentPassword.value, onValueChange = {
//                            studentPassword.value = it
//                        })
//
//
//                        Divider(modifier = Modifier.height(15.dp), color = Color.White)
//
//                        Button(onClick = {
//                            if(studentEmail.value.trim()!="" && studentPassword.value.trim()!=""){
//                                doStudentLogIn(studentEmail.value,studentPassword.value)
//                            }
//                            else
//                            {
//                                Toast.makeText(this@MainActivity,"Please fill all the Login details",Toast.LENGTH_LONG).show()
//                            }
//                        }) {
//                                Text(text = "LogIn")
//                        }
//
//                    }
//                }
//
//            }

                if (user != null) {
                    MainContent()
                    intent = Intent(this@MainActivity, ScheduledExams::class.java)
                    startActivity(intent)
                    finish()
                } else {
                MainContent()
            }
        }
    }

    private fun doStudentLogIn(email: String, password: String) {

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    intent = Intent(this, ScheduledExams::class.java)
                    startActivity(intent)
                    finish()
                    Toast.makeText(this@MainActivity,"LoggedIn SuccessFully!",Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(
                        baseContext, "Something went wrong, Please try again!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

    }


    @Composable
    private fun MainContent() {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        listOf(
                            MaterialTheme.colors.primaryVariant,
                            MaterialTheme.colors.primary
                        )
                    )
                )
        ) {
            Image(
                painter = painterResource(id = R.drawable.image1),
                contentDescription = "doodle", modifier = Modifier
                    .fillMaxSize()
                    .alpha(0.2f),
                contentScale = ContentScale.Crop
            )
            var scrollableState= rememberScrollState()
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollableState),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                LogoText()
                Surface(shape = RoundedCornerShape(100.dp)) {
                    Image(
                        painter = painterResource(id = R.drawable.image2),
                        contentDescription = "Students logo",
                        modifier = Modifier.size(220.dp), contentScale = ContentScale.Crop
                    )
                }
                LoginBox()
                Spacer(modifier = Modifier.height(175.dp))
            }
        }
    }

    @Composable
    private fun LogoText() {
        Text(
            text = buildAnnotatedString {
                withStyle(SpanStyle(fontSize = 75.sp, color = Color.White)) {
                    append("X")
                }
                append("amly.")
            }, color = Color.White, fontSize = 50.sp, fontFamily = FontFamily(Font(R.font.roboto))
        )
    }

    @Composable
    private fun LoginBox() {
        val focusManager = LocalFocusManager.current

        val studentEmail = remember {
            mutableStateOf("")
        }
        val studentPassword = remember {
            mutableStateOf("")
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Students Login",
                color = Color.White,
                fontSize = 38.sp,
                modifier = Modifier.padding(5.dp),
                fontWeight = FontWeight.Bold,
            )
            OutlinedTextField(modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
                value = studentEmail.value,
                placeholder = { Text(text = "Email", style = TextStyle(color=Color.LightGray)) },
                onValueChange = {
                                studentEmail.value=it
                },textStyle = TextStyle(color = Color.White),
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = {
                        focusManager.moveFocus(FocusDirection.Down)
                    }
                ),
                colors = TextFieldDefaults.textFieldColors(
                    focusedIndicatorColor = Color.White,
                    focusedLabelColor = Color.White,
                    backgroundColor = Color.White,
                    unfocusedIndicatorColor = Color.LightGray
                ),
                shape = RoundedCornerShape(10.dp),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Email,
                        contentDescription = "email",
                        tint = Color.LightGray,
                        modifier = Modifier.size(25.dp)
                    )
                }
            )
            OutlinedTextField(modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
                value = studentPassword.value, textStyle = TextStyle(color = Color.White),
                placeholder = { Text(text = "Password", style = TextStyle(color=Color.LightGray)) },
                onValueChange = {
                                studentPassword.value=it
                },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                visualTransformation = PasswordVisualTransformation(),
                colors = TextFieldDefaults.textFieldColors(
                    focusedIndicatorColor = Color.White,
                    focusedLabelColor = Color.White,
                    backgroundColor = Color.White, unfocusedIndicatorColor = Color.LightGray

                ),
                shape = RoundedCornerShape(10.dp),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Lock,
                        contentDescription = "password",
                        tint = Color.LightGray,
                        modifier = Modifier.size(25.dp)
                    )
                }
            )
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp), shape = RoundedCornerShape(15.dp),colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color.White), onClick = {
                        if(isValid(studentEmail.value,studentPassword.value))
                        {
                            doStudentLogIn(studentEmail.value,studentPassword.value)
                        }
                    else{
                        Toast.makeText(this@MainActivity,"Please fill all the fields",Toast.LENGTH_LONG).show()
                        }
                }
            ) {
                Text(text = "Login")
            }

        }
    }


    private fun isValid(email: String, pass: String): Boolean {
        if (email.trim().isEmpty() || pass.trim().isEmpty()) {
            return false;
        }
        return true;
    }

}
