package com.xamly.xamlystudent.uicomponents

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Done
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun questionResult(option: String, color: Color,isCorrect: Boolean?)
{

    if(isCorrect == true)
    {
        Surface(modifier = Modifier
            .padding(15.dp)
            .fillMaxWidth()
            .height(45.dp), color = Color(16, 191, 34, 255), shape = RoundedCornerShape(10.dp)) {
            Row(modifier = Modifier.fillMaxSize(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                Text(text = option, style = TextStyle(color = Color.White, fontSize = 21.sp))
                Icon(imageVector = Icons.Default.Done, contentDescription = "Correct Option", tint = Color.White)
            }
        }
    }
    else if(isCorrect==false){
        Surface(modifier = Modifier
            .padding(15.dp)
            .fillMaxWidth()
            .height(45.dp), color = Color(191, 16, 66, 255), shape = RoundedCornerShape(10.dp)) {
            Row(modifier = Modifier.fillMaxSize(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                Text(text = option, style = TextStyle(color = Color.White, fontSize = 21.sp))
                Icon(imageVector = Icons.Default.Close, contentDescription = "Correct Option", tint = Color.White)
            }
        }
    }
    else
    {
        Surface(modifier = Modifier
            .padding(15.dp)
            .fillMaxWidth()
            .height(45.dp), color = color, shape = RoundedCornerShape(10.dp)) {
            Row(modifier = Modifier.fillMaxSize(), horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically) {
                Text(text = option, style = TextStyle(color = Color.Black, fontSize = 21.sp))
            }
        }
    }

}