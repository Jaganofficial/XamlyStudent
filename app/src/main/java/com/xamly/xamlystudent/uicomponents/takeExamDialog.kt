package com.xamly.xamlystudent.uicomponents

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.xamly.xamlystudent.R


@Composable
fun takeExamDialog(openDialogCustom: MutableState<Boolean>,modifier: Modifier=Modifier) {
    Dialog(onDismissRequest = { openDialogCustom.value = false }) {
        CustomDialogUI(openDialogCustom = openDialogCustom, modifier = modifier)
    }
}



@Composable
private fun CustomDialogUI(modifier: Modifier = Modifier, openDialogCustom: MutableState<Boolean>) {
    Card(
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier.padding(10.dp, 5.dp, 10.dp, 10.dp),
        elevation = 8.dp
    ) {
        Column(
            modifier
                .background(Color.White)
        ) {

            Image(
                painter = painterResource(id = R.drawable.writing),
                contentDescription = null,
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .padding(top = 35.dp)
                    .height(170.dp)
                    .fillMaxWidth(),
            )

            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Important! You are about to take the test...",
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(top = 5.dp)
                        .fillMaxWidth(),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis, fontSize = 21.sp
                )
                Text(
                    text = "All the Best! and Please note that you can take this exam only once!",
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(top = 10.dp, start = 25.dp, end = 25.dp)
                        .fillMaxWidth()
                )
            }

            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp)
                    .background(Color(68,110,6)),
                horizontalArrangement = Arrangement.SpaceAround
            ) {

                TextButton(onClick = {
                    openDialogCustom.value = false
                }) {

                    Text(
                        "Clear",
                        color = Color.White,
                        modifier = Modifier.padding(top = 5.dp, bottom = 5.dp), fontSize = 19.sp
                    )
                }


                TextButton(onClick = {
                    openDialogCustom.value = false
                }) {
                    Text(
                        "Take Test",
                        color = Color.White,
                        modifier =modifier.padding(top = 5.dp, bottom = 5.dp), fontSize = 19.sp
                    )
                }
            }
        }
    }
}
