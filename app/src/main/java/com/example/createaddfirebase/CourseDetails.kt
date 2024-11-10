package com.example.createaddfirebase

import CourseRVAdapter
import android.content.Intent
//import Courses
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot

class CourseDetails : AppCompatActivity() {

    // creating variables for our recycler view,
    // array list, adapter, firebase firestore
    // and our progress bar.
    private lateinit var courseRV: RecyclerView
    private lateinit var coursesArrayList: ArrayList<Courses>
    private lateinit var courseRVAdapter: CourseRVAdapter
    private lateinit var db: FirebaseFirestore
    private lateinit var loadingPB: ProgressBar
    private lateinit var backBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_course_details)

        // initializing our variables.
        courseRV = findViewById(R.id.idRVCourses)
        loadingPB = findViewById(R.id.idProgressBar)
        backBtn = findViewById(R.id.backBtn)

        // initializing our variable for firebase
        // firestore and getting its instance.
        db = FirebaseFirestore.getInstance()

        // creating our new array list
        coursesArrayList = ArrayList()
        courseRV.setHasFixedSize(true)
        courseRV.layoutManager = LinearLayoutManager(this)

        // adding our array list to our recycler view adapter class.
        courseRVAdapter = CourseRVAdapter(coursesArrayList, this)

        // setting adapter to our recycler view.
        courseRV.adapter = courseRVAdapter

        //back button on click
        backBtn.setOnClickListener {
            var i = Intent(this, MainActivity::class.java)
            startActivity(i)
        }

        // below line is use to get the data from Firebase Firestore.
        // previously we were saving data on a reference of Courses
        // now we will be getting the data from the same reference.
        db.collection("Courses").get()
            .addOnSuccessListener(OnSuccessListener<QuerySnapshot> { queryDocumentSnapshots ->
                // after getting the data we are calling on success method
                // and inside this method we are checking if the received
                // query snapshot is empty or not.
                if (!queryDocumentSnapshots.isEmpty) {
                    // if the snapshot is not empty we are
                    // hiding our progress bar and adding
                    // our data in a list.
                    loadingPB.visibility = View.GONE
                    val list: List<DocumentSnapshot> = queryDocumentSnapshots.documents
                    for (d in list) {
                        // after getting this list we are passing
                        // that list to our object class.
                        val c = d.toObject(Courses::class.java)

                        // Below is the updated line of code which we have to
                        // add to pass the document id inside our modal class.
                        // We are setting our document id with d.id property.
                        c?.id = d.id

                        // and we will pass this object class
                        // inside our arraylist which we have
                        // created for recycler view.
                        coursesArrayList.add(c!!)
                    }
                    // after adding the data to recycler view.
                    // we are calling recycler view notifyDataSetChanged
                    // method to notify that data has been changed in recycler view.
                    courseRVAdapter.notifyDataSetChanged()
                } else {
                    // if the snapshot is empty we are displaying a toast message.
                    Toast.makeText(this@CourseDetails, "No data found in Database", Toast.LENGTH_SHORT).show()
                }
            })
            .addOnFailureListener(OnFailureListener { e ->
                // if we do not get any data or any error we are displaying
                // a toast message that we do not get any data
                Toast.makeText(this@CourseDetails, "Failed to get the data. $e", Toast.LENGTH_SHORT).show()
            })
    }

}