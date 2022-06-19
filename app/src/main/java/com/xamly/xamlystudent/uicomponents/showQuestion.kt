package com.xamly.xamlystudent.uicomponents

import android.graphics.fonts.FontStyle
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
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
import kotlin.random.Random

@Composable
fun showQuestion(question: Questions, position: Int,count: Int, returnChoice: (String,Int) -> Unit) {

    Column(modifier = Modifier
        .fillMaxSize()) {
        Spacer(modifier = Modifier.height(25.dp))
        Row(modifier = Modifier.fillMaxWidth()) {

            Text(
                text = "$position/15",
                style = TextStyle(
                    color = Color.White,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 21.sp
                ),
                modifier = Modifier.padding(15.dp)
            )
        }
            Divider(modifier = Modifier.padding( 15.dp),color=Color.White)
            
        Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = question.questionTitle, style = TextStyle(color= Color.White, fontSize = 24.sp))


            displayImage(painter = painterResource(id = com.xamly.xamlystudent.R.drawable.image1),question.questionId)
            
            val x = remember {
                mutableStateOf(Random.nextInt(170, 255))
            }
            val y = remember {
                mutableStateOf(Random.nextInt(170, 255))
            }
            val z = remember {
                mutableStateOf(Random.nextInt(170, 255))
            }

            var selectedOne=remember{
                mutableStateOf(false)
            }
            var selectedTwo=remember{
                mutableStateOf(false)
            }
            var selectedThree=remember{
                mutableStateOf(false)
            }
            var selectedFour=remember{
                mutableStateOf(false)
            }

            Column {

//                if(selectedOne.value) {
//                    question(option = question.op1, color = Color(206, 104, 2, 255)) {
//                        returnChoice(it, 0)
//                        selectedOne.value=true
//                        selectedTwo.value=false
//                        selectedThree.value=false
//                        selectedFour.value=false
//                    }
//                    Log.e("HII","OneClick")
//                }
//                else {
//                    question(option = question.op1, color = Color(x.value,y.value,z.value)) {
//                        returnChoice(it, 0)
//                        selectedOne.value=true
//                        selectedTwo.value=false
//                        selectedThree.value=false
//                        selectedFour.value=false
//                        Log.e("Hello","OneClick")
//                    }
//                }
//
//                if(selectedTwo.value) {
//                    question(option = question.op2, color = Color(206, 104, 2, 255)) {
//                        returnChoice(it, 1)
//                        selectedOne.value=false
//                        selectedTwo.value=true
//                        selectedThree.value=false
//                        selectedFour.value=false
//                    }
//                }
//                else {
//                    question(option = question.op2, color = Color(x.value,y.value,z.value)) {
//                        selectedOne.value=false
//                        selectedTwo.value=true
//                        selectedThree.value=false
//                        selectedFour.value=false
//                        returnChoice(it, 1)
//                    }
//                }
//
//                if(selectedThree.value) {
//                    question(option = question.op3, color = Color(206, 104, 2, 255)) {
//                        returnChoice(it, 2)
//                        selectedOne.value=false
//                        selectedTwo.value=false
//                        selectedThree.value=true
//                        selectedFour.value=false
//
//                    }
//                }
//                else {
//                    question(option = question.op3, color = Color(x.value,y.value,z.value)) {
//                        selectedOne.value=false
//                        selectedTwo.value=false
//                        selectedThree.value=true
//                        selectedFour.value=false
//                        returnChoice(it, 2)
//                    }
//                }
//
//                if(selectedFour.value) {
//                    question(option = question.op4, color = Color(206, 104, 2, 255)) {
//                        returnChoice(it, 3)
//                        selectedOne.value=false
//                        selectedTwo.value=false
//                        selectedThree.value=false
//                        selectedFour.value=true
//
//                    }
//                }
//                else {
//                    question(option = question.op4, color = Color(x.value,y.value,z.value)) {
//                        selectedOne.value=false
//                        selectedTwo.value=false
//                        selectedThree.value=false
//                        selectedFour.value=true
//                        returnChoice(it, 3)
//                    }
//                }

                question(option = question.op1, color = Color(x.value,y.value,z.value)) {
                    returnChoice(it, 0)
                }
                question(option = question.op2, color = Color(x.value,y.value,z.value)) {
                returnChoice(it, 1)
            }
                question(option = question.op3, color = Color(x.value,y.value,z.value)) {
                returnChoice(it, 2)
            }
                question(option = question.op4, color = Color(x.value,y.value,z.value)) {
                returnChoice(it, 3)
            }

                }

                Spacer(modifier = Modifier.height(25.dp))
                
            }

        }

}