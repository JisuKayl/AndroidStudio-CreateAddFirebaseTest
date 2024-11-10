import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.createaddfirebase.Courses
import com.example.createaddfirebase.R
import com.example.createaddfirebase.UpdateCourse

class CourseRVAdapter(private var coursesArrayList: ArrayList<Courses>, private var context: Context) :
    RecyclerView.Adapter<CourseRVAdapter.ViewHolder>() {

    // creating constructor for our adapter class
    init {
        this.coursesArrayList = coursesArrayList
        this.context = context
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // passing our layout file for displaying our card item
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.course_item, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // setting data to our text views from our modal class.
        val courses = coursesArrayList[position]
        holder.courseNameTV.text = courses.courseName
        holder.courseDurationTV.text = courses.courseDuration
        holder.courseDescTV.text = courses.courseDescription
    }

    override fun getItemCount(): Int {
        // returning the size of our array list.
        return coursesArrayList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // creating variables for our text views.
        val courseNameTV: TextView = itemView.findViewById(R.id.idTVCourseName)
        val courseDurationTV: TextView = itemView.findViewById(R.id.idTVCourseDuration)
        val courseDescTV: TextView = itemView.findViewById(R.id.idTVCourseDescription)

        init {
            // Adding an onClickListener for our item of recycler view.
            itemView.setOnClickListener {
                // After clicking on the item of recycler view,
                // we are passing our course object to the new activity.
                val courses = coursesArrayList[adapterPosition]

                // Creating a new intent.
                val intent = Intent(context, UpdateCourse::class.java)

                // Putting our course object as an extra to the intent.
                intent.putExtra("course", courses)

                // After passing the data, we are starting our activity.
                context.startActivity(intent)
            }
        }
    }


}
