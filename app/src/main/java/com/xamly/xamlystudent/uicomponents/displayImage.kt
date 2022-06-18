package com.xamly.xamlystudent.uicomponents

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import kotlin.random.Random

@Composable
fun displayImage(painter: Painter) {


    val x = remember {
        mutableStateOf(Random.nextInt(170, 255))
    }
    val y = remember {
        mutableStateOf(Random.nextInt(170, 255))
    }
    val z = remember {
        mutableStateOf(Random.nextInt(170, 255))
    }

    Surface(modifier = Modifier
        .fillMaxWidth()
        .padding(25.dp), shape = RoundedCornerShape(15.dp), color = Color(x.value,y.value,z.value)){
        Image(painter = painter, contentDescription = "Question Image", contentScale = ContentScale.Crop)
    }

}