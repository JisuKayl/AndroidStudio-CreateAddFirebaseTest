package com.example.createaddfirebase

//import Courses
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.firestore.FirebaseFirestore

class UpdateCourse : AppCompatActivity() {

    // Creating variables for our edit text
    private lateinit var courseNameEdt: EditText
    private lateinit var courseDurationEdt: EditText
    private lateinit var courseDescriptionEdt: EditText

    // Creating strings for storing our values from EditText fields.
    private var courseName: String = ""
    private var courseDuration: String = ""
    private var courseDescription: String = ""

    // Creating a variable for FirebaseFirestore.
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_course)

        val courses = intent.getSerializableExtra("course") as Courses

        // Getting our instance from Firebase Firestore.
        db = FirebaseFirestore.getInstance()

        // Initializing our EditText and buttons
        courseNameEdt = findViewById(R.id.idEdtCourseName)
        courseDescriptionEdt = findViewById(R.id.idEdtCourseDescription)
        courseDurationEdt = findViewById(R.id.idEdtCourseDuration)

        // Creating a variable for the button
        val updateCourseBtn: Button = findViewById(R.id.idBtnUpdateCourse)

        courseNameEdt.setText(courses.courseName)
        courseDescriptionEdt.setText(courses.courseDescription)
        courseDurationEdt.setText(courses.courseDuration)

            updateCourseBtn.setOnClickListener(View.OnClickListener {
                courseName = courseNameEdt.text.toString()
                courseDescription = courseDescriptionEdt.text.toString()
                courseDuration = courseDurationEdt.text.toString()

                // Validating the text fields if empty or not.
                if (TextUtils.isEmpty(courseName)) {
                    courseNameEdt.setError("Please enter Course Name")
                } else if (TextUtils.isEmpty(courseDescription)) {
                    courseDescriptionEdt.setError("Please enter Course Description")
                } else if (TextUtils.isEmpty(courseDuration)) {
                    courseDurationEdt.setError("Please enter Course Duration")
                } else {
                    // Calling a method to update our course.
                    // We are passing our object class, course name,
                    // course description, and course duration from our EditText field.
                    updateCourses(courses, courseName, courseDescription, courseDuration)
                    // Adding an Intent to redirect to MainActivity after updating the course.
                    val intent = Intent(this@UpdateCourse, CourseDetails::class.java)
                    startActivity(intent)
                    finish() //
                }
            })

        val deleteBtn: Button = findViewById(R.id.idBtnDeleteCourse)

        // Adding onClickListener for delete button
        deleteBtn.setOnClickListener {
            // Calling method to delete the course.
            deleteCourse(courses)

            val intent = Intent(this@UpdateCourse, CourseDetails::class.java)
            startActivity(intent)
            finish() //
        }
    }

    private fun updateCourses(
        courses: Courses,
        courseName: String,
        courseDescription: String,
        courseDuration: String
    ) {
        // Inside this method, we are passing our updated values
        // inside our object class, and later on we
        // will pass our whole object to Firebase Firestore.
        val updatedCourse = Courses(courseName, courseDescription, courseDuration)

        // After passing data to object class, we are
        // sending it to Firebase with a specific document id.
        // Below line is used to get the collection of our Firebase Firestore.
        db.collection("Courses").
            // Below line is used to set the id of
            // the document where we have to perform
            // the update operation.
        document(courses.id!!).
            // After setting our document id, we are
            // passing our whole object class to it.
        set(updatedCourse).
            // After passing our object class, we are
            // calling a method for the success listener.
        addOnSuccessListener(OnSuccessListener<Void> {
            // On successful completion of this process,
            // we are displaying the toast message.
            Toast.makeText(this@UpdateCourse, "Course has been updated..", Toast.LENGTH_SHORT).show()
        }).addOnFailureListener(OnFailureListener {
            // Inside the failure method, we are
            // displaying a failure message.
            Toast.makeText(this@UpdateCourse, "Failed to update the data..", Toast.LENGTH_SHORT).show()
        })
    }



    private fun deleteCourse(courses: Courses) {
        // Below line is for getting the collection
        // where we are storing our courses.
        db.collection("Courses")
            // After that, we are getting the document
            // which we have to delete.
            .document(courses.id!!)
            // After passing the document id, we are calling
            // the delete method to delete this document.
            .delete()
            // After deleting, call onCompleteListener
            // method to delete this data.
            .addOnCompleteListener { task ->
                // Inside onComplete method, we are checking
                // if the task is successful or not.
                if (task.isSuccessful) {
                    // This method is called when the task is successful
                    // After deleting, we are starting our MainActivity.
                    Toast.makeText(this@UpdateCourse, "Course has been deleted from Database.", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this@UpdateCourse, MainActivity::class.java)
                    startActivity(intent)
                } else {
                    // If the delete operation is failed
                    // we are displaying a toast message.
                    Toast.makeText(this@UpdateCourse, "Failed to delete the course. ", Toast.LENGTH_SHORT).show()
                }
            }
    }


}