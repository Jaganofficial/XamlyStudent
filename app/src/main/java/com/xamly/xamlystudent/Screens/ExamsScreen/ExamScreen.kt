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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.auth.ktx.auth
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
        val positionMap = mutableMapOf<String , String>()




        setContent {

            var questionList = getQuestionList(questionId)

            val showResult = remember {
                mutableStateOf(false)
            }

            val showQuestions = remember {
                mutableStateOf(true)
            }

            Surface(color = Color(1, 46, 81)) {
                Box(contentAlignment = Alignment.TopCenter) {

                    if(questionList.value.isNullOrEmpty())
                    {
                        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                            Text(text = "Loading! Please wait...",style= TextStyle(color=Color.White))
                        }
                    }
                    else {
                        LazyColumn()
                        {
                            items(questionList.value)
                            {
                                if(showQuestions.value) {
                                    showQuestion(
                                        it,
                                        questionList.value.indexOf(it) + 1, count = questionList.value.size
                                    ) { ans, no ->
                                        if (("" + no).equals("${it.ans}")) {
                                            Log.d("VALUE2", "" + no + " correct " + it.ans)
                                            answerMap.put(it.questionId, "$no")
                                            positionMap.put(it.questionId,"")
                                        } else {
                                            Log.d("VALUE2", "" + no + " try again " + it.ans)
                                            answerMap.put(it.questionId, "")
                                            positionMap.put(it.questionId,"$no")
                                        }
                                    }
                                }
                            }
                        }

                        if(!showResult.value)
                        {
                            showTimer()
                        Row(Modifier.fillMaxSize(), verticalAlignment = Alignment.Bottom, horizontalArrangement = Arrangement.Center){
                            Button(shape = RoundedCornerShape(topStart = 14.dp,topEnd = 14.dp), modifier = Modifier
                                .width(150.dp), colors = ButtonDefaults.buttonColors(backgroundColor = Color.White), onClick = {
                                Log.d("VALUE2",answerMap.toString())
                                showResult.value=true
                                showQuestions.value=false
                            }) {
                                Text(text = "Submit", style = TextStyle(Color(13, 31, 96, 255), fontSize = 21.sp))
                            }
                        }
                        }
                    }
                }
            }

            if(showResult.value)
            {
                Box(modifier = Modifier
                    .fillMaxSize()
                    .background(color = Color(4, 8, 65, 255))) {
                    LazyColumn()
                    {
                        items(questionList.value)
                        {
                            com.xamly.xamlystudent.uicomponents.showResult(
                                question = it,answerMap=answerMap,positionmap=positionMap,
                                position = questionList.value.indexOf(it)+1,count=questionList.value.size
                            )
                        }
                    }
                }

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
                    contentAlignment = Alignment.Center, modifier = Modifier.background(color = Color.White)
                ) {
                    Timer(
                        totalTime = 200L * 1000L,
                        modifier = Modifier
                            .height(35.dp)
                            .width(150.dp)
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
                Row(horizontalArrangement =Arrangement.SpaceEvenly, verticalAlignment = Alignment.CenterVertically) {
                    Icon(painter = painterResource(id = com.xamly.xamlystudent.R.drawable.duration), contentDescription ="Timer", modifier = Modifier.size(40.dp) )
                    Text(
                        text = (currentTime / 1000L).toString(),
                        fontSize = 21.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(5, 135, 160, 255)
                    )
                }

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
                Toast.makeText(this@ExamScreen,"The time is up! Your response will be auto submitted", Toast.LENGTH_LONG).show()
                finish()
            }

        }
    }

}