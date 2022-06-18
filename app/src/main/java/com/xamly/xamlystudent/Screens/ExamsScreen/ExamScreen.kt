package com.xamly.xamlystudent.Screens.ExamsScreen

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.xamly.xamlystudent.Models.Questions
import com.xamly.xamlystudent.uicomponents.showQuestion
import kotlinx.coroutines.delay


class ExamScreen : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportActionBar?.hide()

        var questionId= intent.getStringExtra("QuesionID")

        val answerMap = mutableMapOf<String , String>()




        setContent {

            var questionList = getQuestionList(questionId)

            val showResult = remember {
                mutableStateOf(false)
            }

            Surface(color = Color(1, 46, 81)) {
                Box(contentAlignment = Alignment.TopCenter) {

                    if(questionList.value.isNullOrEmpty())
                    {
                        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                            Text(text = "No data available! Please wait...",style= TextStyle(color=Color.White))
                        }
                    }
                    else {
                        LazyColumn()
                        {
                            items(questionList.value)
                            {
                                showQuestion(it,questionList.value.indexOf(it)+1) {ans, no->
                                    if((""+no).equals("${it.ans}"))
                                    {
                                        Log.d("VALUE2",""+no+" correct "+it.ans)
                                        answerMap.put(it.questionId,"C")
                                    }
                                    else{
                                        Log.d("VALUE2",""+no+" try again "+it.ans)
                                        answerMap.put(it.questionId,"C")
                                    }
                                }
                            }
                        }
                        showTimer()

                        Row(Modifier.fillMaxSize(), verticalAlignment = Alignment.Bottom, horizontalArrangement = Arrangement.Center){
                            Button(onClick = {
                                Log.d("VALUE2",answerMap.toString())
                                showResult.value=true
                            }) {
                                Text(text = "Submit Test")
                            }
                        }

                    }
                }
            }

            if(showResult.value)
            {

            }

        }
    }




    private fun getQuestionList(questionId: String?): MutableState<List<Questions>> {
        val database = questionId?.let { Firebase.database.reference.child("questions").child(it) }
        val questionList = mutableStateOf<List<Questions>>(listOf())
        if (database != null) {
            database.addValueEventListener(object : ValueEventListener{
                override fun onDataChange(dataSnapshot: DataSnapshot){
                    val mutableList: MutableList<Questions> = mutableListOf()

                    dataSnapshot.children.forEach {
                        it?.let {
                            val q = Questions(
                                it.child("ans").value.toString(),
                                it.child("op1").value.toString(),
                                it.child("op2").value.toString(),
                                it.child("op3").value.toString(),
                                it.child("op4").value.toString(),
                                it.child("questionId").value.toString(),
                                it.child("questionTitle").value.toString()
                            )
                            mutableList.add(q)
                        }
                    }
                    questionList.value = mutableList.toList()

                }

                override fun onCancelled(databaseError: DatabaseError) {
                }
            })
        }
        return questionList
    }

    @Composable
    fun showTimer(){
        Surface(
            color = Color.Transparent

        ) {
            Surface(shape = RoundedCornerShape(bottomStart =  14.dp, bottomEnd = 14.dp), color = Color.Transparent) {
                Box(
                    contentAlignment = Alignment.Center, modifier = Modifier.background(Brush.verticalGradient(
                        listOf(
                            Color(118, 213, 118, 255),
                            Color.Transparent
                        )
                    ))
                ) {
                    Timer(
                        totalTime = 10L * 1000L,
                        modifier = Modifier
                            .height(45.dp)
                            .width(200.dp)
                    )
                }
            }
        }
    }

    @Composable
    fun Timer(

        totalTime: Long,

        modifier: Modifier = Modifier.padding(21.dp),

        initialValue: Float = 1f,
        strokeWidth: Dp = 5.dp
    ) {
        var size = remember {
            mutableStateOf(IntSize.Zero)
        }
        var value by remember {
            mutableStateOf(initialValue)
        }
        var currentTime by remember {
            mutableStateOf(totalTime)
        }
        var isTimerRunning by remember {
            mutableStateOf(true)
        }
        LaunchedEffect(key1 = currentTime, key2 = isTimerRunning) {
            if(currentTime > 0 && isTimerRunning) {
                delay(100L)
                currentTime -= 100L
                value = currentTime / totalTime.toFloat()
            }
        }
        Box(
            contentAlignment = Alignment.Center,
            modifier = modifier
                .onSizeChanged {
                    size.value = it
                }
        ) {
            if(currentTime>0) {
                Text(
                    text = (currentTime / 1000L).toString(),
                    fontSize = 21.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
            else{
                Text(
                    text ="Completed",
                    fontSize = 21.sp,
                    fontWeight = FontWeight.Bold,
                    color =Color(0xFFFFFFFF)
                )
            }

            if(currentTime<=0)
            {
                Toast.makeText(this@ExamScreen,"Done", Toast.LENGTH_LONG).show()
            }

        }
    }

}