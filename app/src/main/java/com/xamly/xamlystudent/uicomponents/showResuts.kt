package com.xamly.xamlystudent.uicomponents

import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.xamly.xamlystudent.Models.Questions
import com.xamly.xamlystudent.R
import kotlin.random.Random

@Composable
fun showResult(
    question: Questions,
    position: Int,
    answerMap: MutableMap<String, String>,
    positionmap: MutableMap<String, String>,
    count: Int
) {

    Column(modifier = Modifier
        .fillMaxSize()) {
        Spacer(modifier = Modifier.height(25.dp))
        Row(modifier = Modifier.fillMaxWidth()) {

            Text(
                text = "$position/$count",
                style = TextStyle(
                    color = Color.White,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 21.sp
                ),
                modifier = Modifier.padding(15.dp)
            )
        }
        Divider(modifier = Modifier.padding( 15.dp),color= Color.White)

        Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = question.questionTitle, style = TextStyle(color= Color.White, fontSize = 24.sp))


            displayImage(painter = painterResource(id = R.drawable.image1),question.questionId)

            val x = remember {
                mutableStateOf(Random.nextInt(170, 255))
            }
            val y = remember {
                mutableStateOf(Random.nextInt(170, 255))
            }
            val z = remember {
                mutableStateOf(Random.nextInt(170, 255))
            }

            var isCorrect :Boolean?


            Column {
                if(answerMap[question.questionId].equals("0"))
                {
                    isCorrect=true
                }
                else if(positionmap.get(question.questionId).equals("0"))
                {
                    isCorrect=false
                }
                else{
                    isCorrect=null
                }
                questionResult(option = question.op1, color = Color(x.value,y.value,z.value), isCorrect = isCorrect)

                if(answerMap[question.questionId].equals("1"))
                {
                    isCorrect=true
                }
                else if(positionmap.get(question.questionId).equals("1"))
                {
                    isCorrect=false
                }
                else{
                    isCorrect=null
                }
                questionResult(option = question.op2, color = Color(x.value,y.value,z.value), isCorrect = isCorrect)

                if(answerMap[question.questionId].equals("2"))
                {
                    isCorrect=true
                }
                else if(positionmap.get(question.questionId).equals("2"))
                {
                    isCorrect=false
                }
                else{
                    isCorrect=null
                }
                questionResult(option = question.op3, color = Color(x.value,y.value,z.value), isCorrect = isCorrect)

                if(answerMap[question.questionId].equals("3"))
                {
                    isCorrect=true
                }
                else if(positionmap.get(question.questionId).equals("3"))
                {
                    isCorrect=false
                }
                else{
                    isCorrect=null
                }
                questionResult(option = question.op4, color = Color(x.value,y.value,z.value), isCorrect = isCorrect)

                Spacer(modifier = Modifier.height(25.dp))

            }

        }

    }

}