package com.xamly.xamlystudent.Screens.ScheduledExams

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.xamly.xamlystudent.Models.Quiz
import com.xamly.xamlystudent.Screens.ExamsScreen.ExamScreen
import com.xamly.xamlystudent.uicomponents.takeExamDialog
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class ScheduledExams : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContent {


            val database = remember{
                Firebase.database
            }
            val myRef = remember{
                database.getReference("quiz")
            }

            var snapShot = mutableListOf<DataSnapshot>()

            val examsScheduled : ScheduledExamsViewModel = viewModel()

                Surface(modifier = Modifier.fillMaxSize()) {
                    if(examsScheduled.quizList.value.isNullOrEmpty())
                    {
                        Row(modifier = Modifier.fillMaxSize(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center) {
//                            Text(text = "No Exams Scheduled! Hurray ;)")
                            CircularProgressIndicator(modifier = Modifier.size(25.dp))
                        }
                    }
                    else {

                        var onProgressList = mutableListOf<Quiz>()
                        var upComingList = mutableListOf<Quiz>()

                        for(it in examsScheduled.quizList.value)
                        {
                               val current = LocalDateTime.now()
                                val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
                                val formatted = current.format(formatter)

                                val calendar = Calendar.getInstance()

                                var hour24hrs = calendar.get(Calendar.HOUR_OF_DAY);
                                var minutes = calendar.get(Calendar.MINUTE)
                                var currentmilli = (hour24hrs*60*60*1000)+(minutes*60*1000)

                                var giventime= it.scheduledTime.split(":")

                                var hMilli= giventime[0].toInt() * 60 * 60 *1000;
                                var minMilli=giventime[1].toInt() * 60 * 1000;
                                var durationMilli = it.duration*60*1000;

                                var totalmilli=hMilli+minMilli+durationMilli

                                val d1 = formatted
                                val d2 = it.scheduledDate
                                Log.d("DATE1",formatted+" "+it.scheduledDate)
                                Log.d("DATE1","Time "+(hMilli)+" "+minMilli+" current -> "+currentmilli+" total -> "+totalmilli)
                                val cmp = d1.compareTo(d2)

                                if(cmp==0 && currentmilli>(hMilli+minMilli)&&currentmilli<(totalmilli))
                                {
                                    onProgressList.add(it)
                                }
                               else if(cmp < 0){
                                   upComingList.add(it)
                                }
                        }


                        Column(modifier = Modifier) {
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(250.dp),
                                shape = RoundedCornerShape(bottomEnd = 350.dp, bottomStart = 35.dp)
                            ) {
                                Box()
                                {

                                    Image(
                                        painter = painterResource(id = com.xamly.xamlystudent.R.drawable.img),
                                        contentDescription = "",
                                        contentScale = ContentScale.Crop
                                    )
                                    Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center)
                                    {
                                        Text(
                                            text = buildAnnotatedString {
                                                append("Upcoming\n")
                                                withStyle(
                                                    style = SpanStyle(
                                                        Color(40, 33, 129, 255),
                                                        fontSize = 37.sp,
                                                    )
                                                )
                                                {
                                                    append("Xams..\n")
                                                }
                                            },
                                            color = Color.White,
                                            fontSize = 35.sp,
                                            fontFamily = FontFamily.SansSerif,
                                            fontWeight = FontWeight.ExtraBold,
                                            modifier = Modifier.offset(15.dp)
                                        )
                                    }
                                }
                            }
                            Text(text = "Ongoing Exams", style = TextStyle(fontSize = 24.sp, fontWeight = FontWeight.Bold), modifier = Modifier.padding(15.dp))
                            LazyVerticalGridDemo(onProgressList=onProgressList)
                            Text(text = "Upcoming Exams", style = TextStyle(fontSize = 21.sp, fontWeight = FontWeight.Bold), modifier = Modifier.padding(15.dp))
                            LazyVerticalGrid(onProgressList = upComingList)
                        }
//                        LazyColumn()
//                        {
//                            items(examsScheduled.quizList.value)
//                            {
//
//                                val current = LocalDateTime.now()
//                                val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
//                                val formatted = current.format(formatter)
//
//                                val calendar = Calendar.getInstance()
//                                var currentmilli = calendar.timeInMillis
//
//
//
//                                var giventime= it.scheduledTime.split(":")
//
//                                var hMilli= giventime[0].toInt() * 60 * 60 *1000;
//                                var minMilli=giventime[1].toInt() * 60 * 1000;
//                                var durationMilli = it.duration*60*1000;
//
//                                var totalmilli=hMilli+minMilli+durationMilli
//
//                                val d1 = formatted
//                                val d2 = it.scheduledDate
//                                Log.d("DATE1",formatted+" "+it.scheduledDate)
//
//                                var examState=false
//
//                                val cmp = d1.compareTo(d2)
//                                if(cmp==0 && currentmilli>(hMilli+minMilli)&&currentmilli<(totalmilli))
//                                {
//                                    examState=true
//                                }
//
//
////                                scheduledexamcard(modifier = Modifier.clickable {
////                                    showAlert.value = true
////                                    questionId.value=it.quizId
////                                }, it,examstate=examState,count=examsScheduled.quizList.value.size)
//
//                            }
//                        }
                    }
                }
        }
    }

    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    fun LazyVerticalGridDemo(onProgressList: MutableList<Quiz>,modifier:Modifier=Modifier) {

        val showAlert = remember {
            mutableStateOf(false)
        }


        var questionId = remember {
            mutableStateOf("")
        }

        if(showAlert.value)
            takeExamDialog(openDialogCustom = showAlert,modifier=Modifier.clickable{
                intent= Intent(this@ScheduledExams, ExamScreen::class.java)
                intent.putExtra("QuesionID",questionId.value)
                Log.d("IDVALUE",questionId.value)
                startActivity(intent)
                showAlert.value=false
            })

        LazyVerticalGrid(
            cells = GridCells.Adaptive(128.dp),
            contentPadding = PaddingValues(
                start = 12.dp,
                top = 16.dp,
                end = 12.dp,
                bottom = 16.dp
            ),
            content = {
                items(onProgressList) { index ->
                    Card(
                        backgroundColor = Color(238, 247, 255, 255),
                        modifier = modifier
                            .clickable {
                                questionId.value = index.quizId
                                showAlert.value = true
                            }
                            .padding(3.dp)
                            .fillMaxWidth(),
                        elevation = 6.dp,
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Column() {
                            Text(
                                text = index.quizName,
                                fontSize = 21.sp,
                                textAlign = TextAlign.Left,
                                modifier = Modifier.padding(5.dp)
                            )

                            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                Text(
                                    text = index.scheduledDate,
                                    fontSize = 14.sp,
                                    textAlign = TextAlign.Left,
                                    style=TextStyle(color = Color.Gray),
                                    modifier = Modifier.padding(5.dp)
                                )
                                Text(
                                    text = index.scheduledTime,
                                    fontSize = 14.sp,
                                    textAlign = TextAlign.Left,
                                    style=TextStyle(color = Color.Gray),
                                    modifier = Modifier.padding(5.dp)
                                )
                            }
                            Spacer(modifier = Modifier.height(15.dp))

                            Row(modifier = Modifier.padding(5.dp)) {
                                Icon( painter = painterResource(id =com.xamly.xamlystudent.R.drawable.duration), contentDescription ="Duration", modifier = Modifier.size(35.dp) )
                                Text(
                                    text = ""+index.duration,
                                    fontSize = 16.sp,
                                    textAlign = TextAlign.Left,
                                    style=TextStyle(color = Color.Gray),
                                    modifier = Modifier.padding(5.dp)
                                )
                            }

                        }

                    }
                }
            }
        )
    }

    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    fun LazyVerticalGrid(onProgressList: MutableList<Quiz>,modifier:Modifier=Modifier) {

        val showAlert = remember {
            mutableStateOf(false)
        }


        var questionId = remember {
            mutableStateOf("")
        }

        if(showAlert.value)
            takeExamDialog(openDialogCustom = showAlert,modifier=Modifier.clickable{
                intent= Intent(this@ScheduledExams, ExamScreen::class.java)
                intent.putExtra("QuesionID",questionId.value)
                Log.d("IDVALUE",questionId.value)
                startActivity(intent)
                showAlert.value=false
            })

        LazyVerticalGrid(
            cells = GridCells.Adaptive(128.dp),
            contentPadding = PaddingValues(
                start = 12.dp,
                top = 16.dp,
                end = 12.dp,
                bottom = 16.dp
            ),
            content = {
                items(onProgressList) { index ->
                    Card(
                        backgroundColor = Color(238, 247, 255, 255),
                        modifier = modifier
                            .clickable {
                                Toast.makeText(this@ScheduledExams,"The test is not yet started!",Toast.LENGTH_LONG).show()
                            }
                            .padding(3.dp)
                            .fillMaxWidth(),
                        elevation = 6.dp,
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Column() {
                            Text(
                                text = index.quizName,
                                fontSize = 21.sp,
                                textAlign = TextAlign.Left,
                                modifier = Modifier.padding(5.dp)
                            )

                            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                Text(
                                    text = index.scheduledDate,
                                    fontSize = 14.sp,
                                    textAlign = TextAlign.Left,
                                    style=TextStyle(color = Color.Gray),
                                    modifier = Modifier.padding(5.dp)
                                )
                                Text(
                                    text = index.scheduledTime,
                                    fontSize = 14.sp,
                                    textAlign = TextAlign.Left,
                                    style=TextStyle(color = Color.Gray),
                                    modifier = Modifier.padding(5.dp)
                                )
                            }
                            Spacer(modifier = Modifier.height(15.dp))

                            Row(modifier = Modifier.padding(5.dp)) {
                                Icon( painter = painterResource(id =com.xamly.xamlystudent.R.drawable.duration), contentDescription ="Duration", modifier = Modifier.size(35.dp) )
                                Text(
                                    text = ""+index.duration,
                                    fontSize = 16.sp,
                                    textAlign = TextAlign.Left,
                                    style=TextStyle(color = Color.Gray),
                                    modifier = Modifier.padding(5.dp)
                                )
                            }

                        }

                    }
                }
            }
        )
    }
}