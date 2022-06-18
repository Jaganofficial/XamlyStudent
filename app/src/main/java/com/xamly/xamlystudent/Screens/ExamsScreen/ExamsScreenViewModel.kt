package com.xamly.xamlystudent.Screens.ExamsScreen

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.xamly.xamlystudent.Models.Questions

class ExamsScreenViewModel() : ViewModel() {

    val database = Firebase.database.reference.child("questions")
    val quizList = mutableStateOf<List<Questions>>(listOf())
    val quizListListener = object : ValueEventListener {
        override fun onDataChange(dataSnapshot: DataSnapshot) {
            val mutableList: MutableList<Questions> = mutableListOf()
            dataSnapshot.children.forEach {
                it?.let {
                    val q = Questions(
                        it.child("ans").value.toString(),
                        it.child("op1").value.toString(),
                        it.child("op2").value.toString(),
                        it.child("op3").value.toString(),
                        it.child("op4").value.toString(),
                        it.child("questionId").value.toString(),
                        it.child("questionTitle").value.toString()
                    )
                    Log.e("value", q.toString())
                    mutableList.add(q)
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