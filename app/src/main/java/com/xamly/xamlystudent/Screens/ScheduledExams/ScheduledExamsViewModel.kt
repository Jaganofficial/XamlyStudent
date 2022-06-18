package com.xamly.xamlystudent.Screens.ScheduledExams

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.*
import com.google.firebase.ktx.Firebase
import com.xamly.xamlystudent.Models.Quiz

class ScheduledExamsViewModel : ViewModel() {
    val database = Firebase.database.reference.child("quiz")
    val quizList = mutableStateOf<List<Quiz>>(listOf())
    val quizListListener = object : ValueEventListener {
        override fun onDataChange(dataSnapshot: DataSnapshot) {
            val mutableList: MutableList<Quiz> = mutableListOf()
            dataSnapshot.children.forEach {
                it?.let {
                    val q = Quiz(
                        it.child("quizId").value.toString(),
                        it.child("quizName").value.toString(),
                        it.child("scheduledDate").value.toString(),
                        it.child("scheduledTime").value.toString(),
                        Integer.parseInt(it.child("duration").value.toString()),
                        it.child("acceptResponse").value.toString().toBoolean()
                    )
                    mutableList.add(q)
                    Log.e("value", it.value.toString())
                }
            }
            quizList.value = mutableList.toList()
        }

        override fun onCancelled(databaseError: DatabaseError) {
        }
    }

    init {
        database.addValueEventListener(quizListListener)
    }
}

