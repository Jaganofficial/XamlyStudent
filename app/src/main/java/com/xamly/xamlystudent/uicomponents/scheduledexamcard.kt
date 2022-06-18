package com.xamly.xamlystudent.uicomponents


import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.xamly.xamlystudent.Models.Quiz

@Composable
fun scheduledexamcard(modifier: Modifier = Modifier, data: Quiz) {
    
    Surface(modifier = modifier
        .fillMaxWidth()
        .padding(5.dp)) {
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(15.dp), horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column() {
                    Text(
                        text = data.quizName,
                        style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 25.sp),
                        modifier = Modifier.padding(5.dp)
                    )
                    Text(
                        text = data.scheduledTime,
                        style = TextStyle(fontWeight = FontWeight.Light, fontSize = 16.sp),
                        modifier = Modifier.padding(5.dp)
                    )
                    Text(
                        text = data.scheduledDate,
                        style = TextStyle(fontWeight = FontWeight.Light, fontSize = 16.sp),
                        modifier = Modifier.padding(5.dp)
                    )
                }
                Column(verticalArrangement = Arrangement.Center) {
                    Text(
                        text = "Status:",
                        style = TextStyle(fontWeight = FontWeight.Light, fontSize = 16.sp),
                        modifier = Modifier.padding(5.dp)
                    )
                    Button(onClick = { /*TODO*/ }) {
                        Text(text = "Upcoming")
                    }
                    Text(
                        text = "Questions: 15",
                        style = TextStyle(fontWeight = FontWeight.Light, fontSize = 16.sp),
                        modifier = Modifier.padding(5.dp)
                    )
                    Text(
                        text = "Marks: 30",
                        style = TextStyle(fontWeight = FontWeight.Light, fontSize = 16.sp),
                        modifier = Modifier.padding(5.dp)
                    )
                }
            }
            Divider(modifier = Modifier.padding(5.dp))
        }
    }
    
}