package workflow.contacts


import android.Manifest.permission.READ_CONTACTS
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat.checkSelfPermission

class MainActivity : AppCompatActivity() {

    private val contactRequestID = 1337

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        getContacts()

    }

    private fun getContacts() {
        getPermission()
    }

    private fun getPermission() {
        if (checkSelfPermission(this, READ_CONTACTS) != PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(READ_CONTACTS), contactRequestID)
        } else {
            this.fetchAllContacts()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int,
        permissions: Array<String>, grantResults: IntArray)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            contactRequestID -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PERMISSION_GRANTED) {
                    getContacts()
                } else {
                    Toast.makeText(this, "See you next time....", Toast.LENGTH_SHORT).show()
                }
                return
            }
        }
    }
}