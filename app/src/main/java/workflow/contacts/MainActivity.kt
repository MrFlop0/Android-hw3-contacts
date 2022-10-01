package workflow.contacts


import android.Manifest.permission.READ_CONTACTS
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat.checkSelfPermission
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import workflow.contacts.adapters.ContactAdapter

class MainActivity : AppCompatActivity() {

    private val contactRequestID = 1337
    private lateinit var recycler: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        getStarted()

    }

    private fun getStarted() {
        recycler = findViewById(R.id.recyclerView)
        recycler.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, true)
        recycler.adapter = getAdapter()

        if ((recycler.adapter as ContactAdapter).itemCount > 0) {
            Toast.makeText(
                this,
                "Have found ${(recycler.adapter as ContactAdapter).itemCount} contact",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun getAdapter(): ContactAdapter {
        return ContactAdapter(this, getContacts())
    }


    private fun getContacts(): List<Contact> {
        return if (checkSelfPermission(this, READ_CONTACTS) != PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(READ_CONTACTS), contactRequestID)
            emptyList()
        } else {
            this.fetchAllContacts().distinctBy { it.name }.sortedBy { it.name }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>, grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            contactRequestID -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PERMISSION_GRANTED) {
                    getStarted()
                } else {
                    Toast.makeText(this, "See you next time....", Toast.LENGTH_SHORT).show()
                }
                return
            }
        }
    }
}