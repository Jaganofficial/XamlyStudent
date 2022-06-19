package com.xamly.xamlystudent.uicomponents

import android.net.Uri
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlin.random.Random


@Composable
fun displayImage(painter: Painter,id:String) {


    val x = remember {
        mutableStateOf(Random.nextInt(170, 255))
    }
    val y = remember {
        mutableStateOf(Random.nextInt(170, 255))
    }
    val z = remember {
        mutableStateOf(Random.nextInt(170, 255))
    }

    var storageRef = Firebase.storage.reference
    val imagesRef = storageRef.child("QuestionImages").child("$id.jpg")
    var uri: MutableState<Uri?> = remember {
     mutableStateOf(null)
    }
    imagesRef.downloadUrl.addOnSuccessListener {
        uri.value=it
    }

    if(uri.value!=null){

        val painter= rememberAsyncImagePainter(uri.value, placeholder = painterResource(id = com.xamly.xamlystudent.R.drawable.image2))
        Log.d("IMAGE1","$id "+painter.toString()+" $uri")
        Surface(modifier = Modifier
            .fillMaxWidth()
            .padding(25.dp), shape = RoundedCornerShape(15.dp), color = Color(x.value,y.value,z.value)){
            Image(painter = painter, contentDescription = "Question Image", contentScale = ContentScale.Crop)
        }
    }

}