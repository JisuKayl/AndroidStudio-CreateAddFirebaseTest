package com.example.createaddfirebase


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Toast
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity : AppCompatActivity() {

    // creating variables for our edit text
    lateinit var courseNameEdt: EditText
    lateinit var courseDurationEdt: EditText
    lateinit var courseDescriptionEdt: EditText

    // creating variable for button
    lateinit var submitCourseBtn: ImageButton
    lateinit var idBtnViewCourses: ImageButton

    // creating strings for storing
    // our values from edittext fields.
    var courseName: String = ""
    var courseDuration: String = ""
    var courseDescription: String = ""

    // creating a variable
    // for Firebase Firestore.
    lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        // getting our instance
        // from Firebase Firestore.
        db = FirebaseFirestore.getInstance()

        // initializing our edittext and buttons
        courseNameEdt = findViewById(R.id.idEdtCourseName)
        courseDescriptionEdt = findViewById(R.id.idEdtCourseDescription)
        courseDurationEdt = findViewById(R.id.idEdtCourseDuration)
        submitCourseBtn = findViewById(R.id.idBtnSubmitCourse)
        idBtnViewCourses = findViewById(R.id.idBtnViewCourses)

        // adding on click listener for button
        submitCourseBtn.setOnClickListener {
            // getting data from edittext fields.
            courseName = courseNameEdt.text.toString()
            courseDescription = courseDescriptionEdt.text.toString()
            courseDuration = courseDurationEdt.text.toString()

            // validating the text fields if empty or not.
            when {
                //TextUtils.isEmpty(CharSequence str): Checks if the provided CharSequence (which includes String) is empty or null.
                TextUtils.isEmpty(courseName) -> courseNameEdt.setError("Please enter Course Name")
                TextUtils.isEmpty(courseDescription) -> courseDescriptionEdt.setError("Please enter Course Description")
                TextUtils.isEmpty(courseDuration) -> courseDurationEdt.setError("Please enter Course Duration")
                else -> {
                    // calling method to add data to Firebase Firestore.
                    addDataToFirestore(courseName, courseDescription, courseDuration)
                    val intent = Intent(this, CourseDetails::class.java)
                    startActivity(intent)
                    finish() //
                }
            }
        }

        idBtnViewCourses.setOnClickListener {
            // opening a new activity on button click
            val intent = Intent(this@MainActivity, CourseDetails::class.java)
            startActivity(intent)
        }

    }


        private fun addDataToFirestore(courseName: String, courseDescription: String, courseDuration: String) {
            // creating a collection reference
            // for our Firebase Firestore database.
            val dbCourses: CollectionReference = db.collection("Courses")

            // adding our data to our courses object class.
            val courses = Courses(courseName, courseDescription, courseDuration)

            // below method is used to add data to Firebase Firestore.
            dbCourses.add(courses)
                .addOnSuccessListener(OnSuccessListener<DocumentReference> { documentReference ->
                    // after the data addition is successful
                    // we are displaying a success toast message.
                    Toast.makeText(this@MainActivity, "Your Course has been added to Firebase Firestore",
                        Toast.LENGTH_SHORT).show()
                })
                .addOnFailureListener(OnFailureListener { e ->
                    // this method is called when the data addition process is failed.
                    // displaying a toast message when data addition is failed.
                    Toast.makeText(this@MainActivity, "Failed to add course \n$e", Toast.LENGTH_SHORT).show()
                })
        }
    }
