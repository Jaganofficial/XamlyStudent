package com.xamly.xamlystudent.Screens

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.xamly.xamlystudent.Screens.ScheduledExams.ScheduledExams
import com.xamly.xamlystudent.ui.theme.XamlyStudentTheme

class MainActivity : ComponentActivity() {
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        auth = Firebase.auth

        val user = auth.currentUser

        setContent {
            val studentEmail = remember {
                mutableStateOf("")
            }
            val studentPassword = remember{
                mutableStateOf("")
            }
            XamlyStudentTheme {

                if (user != null) {
                    CircularProgressIndicator(modifier = Modifier.size(40.dp))
                    intent = Intent(this@MainActivity, ScheduledExams::class.java)
                    startActivity(intent)
                } else {

                    Column(modifier = Modifier
                        .fillMaxSize()
                        .padding(10.dp)) {

                        Text(text = "Enter the student email", modifier = Modifier.padding(15.dp))

                        OutlinedTextField(value = studentEmail.value, onValueChange = {
                            studentEmail.value = it
                        })

                        Divider(modifier = Modifier.height(15.dp))

                        Text(
                            text = "Enter the studentEmail password",
                            modifier = Modifier.padding(15.dp)
                        )


                        OutlinedTextField(value = studentPassword.value, onValueChange = {
                            studentPassword.value = it
                        })


                        Divider(modifier = Modifier.height(15.dp), color = Color.White)

                        Button(onClick = {
                            if(studentEmail.value.trim()!="" && studentPassword.value.trim()!=""){
                                doStudentLogIn(studentEmail.value,studentPassword.value)
                            }
                            else
                            {
                                Toast.makeText(this@MainActivity,"Please fill all the Login details",Toast.LENGTH_LONG).show()
                            }
                        }) {
                                Text(text = "LogIn")
                        }

                    }
                }

            }
        }
    }

    private fun doStudentLogIn(email: String, password: String) {

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("LOGIN", "createUserWithEmail:success")

                    val user = auth.currentUser

                    intent = Intent(this, ScheduledExams::class.java)
                    startActivity(intent)

                } else {
                    // If sign in fails, display a message to the user.
                    Log.d("LOGIN", "createUserWithEmail:failure", task.exception)
                    Toast.makeText(baseContext, "Something went wrong, Please try again!",
                        Toast.LENGTH_SHORT).show()
                }
            }

    }



}

