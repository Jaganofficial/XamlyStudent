package com.xamly.xamlystudent.uicomponents

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun question(option: String,color: Color,returnChoice:(String)->Unit) {

    var showDialog = remember{
        mutableStateOf(false)
    }

    if(showDialog.value)
        optionSelectDialog(showDialog)


    Surface(modifier = Modifier
        .padding(15.dp)
        .fillMaxWidth()
        .clickable() {
            returnChoice(option)
        }
        .height(45.dp), color = color, shape = RoundedCornerShape(10.dp)) {
        Row(modifier = Modifier.fillMaxSize(), horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically) {
            Text(text = option, style = TextStyle(color = Color.Black, fontSize = 21.sp))
        }
    }

}