package com.xamly.xamlystudent.Screens.ScheduledExams

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.xamly.xamlystudent.Screens.ExamsScreen.ExamScreen
import com.xamly.xamlystudent.uicomponents.scheduledexamcard
import com.xamly.xamlystudent.uicomponents.takeExamDialog

class ScheduledExams : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            val showAlert = remember {
                mutableStateOf(false)
            }

            var questionId = remember {
                mutableStateOf("")
            }

            if(showAlert.value)
                takeExamDialog(openDialogCustom = showAlert,modifier=Modifier.clickable{
                    intent= Intent(this@ScheduledExams, ExamScreen::class.java)
                    showAlert.value=false
                    intent.putExtra("QuesionID",questionId.value)
                    startActivity(intent)
                })


            val database = remember{
                Firebase.database
            }
            val myRef = remember{
                database.getReference("quiz")
            }

            var snapShot = mutableListOf<DataSnapshot>()

            val examsScheduled : ScheduledExamsViewModel = viewModel()

            Log.e("FIREBASE2",""+snapShot)

                Surface(modifier = Modifier.fillMaxSize()) {
                    if(examsScheduled.quizList.value.isNullOrEmpty())
                    {
                        Text(text = "No Exams Scheduled! Hurray ;)")
                    }
                    else {
                        LazyColumn()
                        {
                            items(examsScheduled.quizList.value)
                            {
                                scheduledexamcard(modifier = Modifier.clickable {
                                    showAlert.value = true
                                    questionId.value=it.quizId
                                }, it)
                            }
                        }
                    }
                }
        }
    }
}