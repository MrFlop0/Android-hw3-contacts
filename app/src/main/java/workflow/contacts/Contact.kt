package workflow.contacts

import android.annotation.SuppressLint
import android.content.Context
import android.provider.ContactsContract

data class Contact(val name: String, val phoneNumber: String)

@SuppressLint("Range")
fun Context.fetchAllContacts(): List<Contact> {
    contentResolver.query(
        ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
        null,
        null,
        null,
        null
    )
        .use { cursor ->
            if (cursor == null) return emptyList()
            val builder = ArrayList<Contact>()
            while (cursor.moveToNext()) {
                val name =
                    cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME))
                        ?: ""
                val phoneNumber =
                    cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
                        ?: ""

                builder.add(Contact(name, phoneNumber))
            }
            return builder
        }
}