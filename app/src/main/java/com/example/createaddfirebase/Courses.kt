package com.example.createaddfirebase

import com.google.firebase.firestore.Exclude
import java.io.Serializable

// This is a Kotlin class named Courses.

class Courses : Serializable {

    @Exclude
    var id: String? = null

        // Variables for storing course-related data.
        // These variables are nullable strings, allowing them to be either non-null strings or null.
        var courseName: String? = null
        var courseDescription: String? = null
        var courseDuration: String? = null

        // Empty constructor.
        // This constructor is required for certain scenarios, such as Firebase data retrieval,
        // where the class needs to be instantiated without providing initial values.
        constructor()

        // Constructor that takes parameters to initialize the class properties.
        // This secondary constructor is useful when creating an instance of the class
        // with specific values for courseName, courseDescription, and courseDuration.
        constructor(courseName: String?, courseDescription: String?, courseDuration: String?) {
            // Assigning the provided values to the corresponding properties of the class.
            this.courseName = courseName
            this.courseDescription = courseDescription
            this.courseDuration = courseDuration
        }
    }




