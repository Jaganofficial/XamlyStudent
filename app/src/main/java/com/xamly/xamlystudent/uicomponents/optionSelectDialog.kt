package com.xamly.xamlystudent.uicomponents

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
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
fun optionSelectDialog(openDialogCustom: MutableState<Boolean>) {
    Dialog(onDismissRequest = { openDialogCustom.value = false }) {
        CustomDialogUI(openDialogCustom = openDialogCustom)
    }
}

//Layout
@Composable
private fun CustomDialogUI(modifier: Modifier = Modifier, openDialogCustom: MutableState<Boolean>) {
    Card(
        //shape = MaterialTheme.shapes.medium,
        shape = RoundedCornerShape(10.dp),
        // modifier = modifier.size(280.dp, 240.dp)
        modifier = Modifier.padding(10.dp, 5.dp, 10.dp, 10.dp),
        elevation = 8.dp
    ) {
        Column(
            modifier
                .background(Color.White)
        ) {

            Image(
                painter = painterResource(id = R.drawable.notification),
                contentDescription = null,
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .padding(top = 35.dp)
                    .height(170.dp)
                    .fillMaxWidth(),
                )

            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "You Choose : A",
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(top = 5.dp)
                        .fillMaxWidth(),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis, fontSize = 21.sp
                )
                Text(
                    text = "Press note that you can't change the answer after the submission",
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
                    .background(Color(15, 82, 186)),
                horizontalArrangement = Arrangement.SpaceAround
            ) {

                TextButton(onClick = {
                    openDialogCustom.value = false
                }) {

                    Text(
                        "Clear",
                        color = Color.LightGray,
                        modifier = Modifier.padding(top = 5.dp, bottom = 5.dp), fontSize = 19.sp
                    )
                }
                TextButton(onClick = {
                    openDialogCustom.value = false
                }) {
                    Text(
                        "Submit",
                        color = Color.White,
                        modifier = Modifier.padding(top = 5.dp, bottom = 5.dp), fontSize = 19.sp
                    )
                }
            }
        }
    }
}
