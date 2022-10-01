package workflow.contacts.adapters

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.recyclerview.widget.RecyclerView
import workflow.contacts.Contact
import workflow.contacts.R
import kotlin.random.Random


class ContactAdapter(private val context: Context, private val values: List<Contact>) :
    RecyclerView.Adapter<ContactAdapter.ViewHolder>() {

    private val colors = listOf<Int>(
        ContextCompat.getColor(context, R.color.random1),
        ContextCompat.getColor(context, R.color.random2),
        ContextCompat.getColor(context, R.color.random4),
        ContextCompat.getColor(context, R.color.random5),
        ContextCompat.getColor(context, R.color.random6),
        ContextCompat.getColor(context, R.color.random7)
    )

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name = view.findViewById<TextView>(R.id.nameField)
        val phone = view.findViewById<TextView>(R.id.phoneField)
        val image = view.findViewById<TextView>(R.id.contactImage)
        val contact = view.findViewById<LinearLayout>(R.id.Contact)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(LayoutInflater.from(context).inflate(R.layout.contact_view, parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val (name, phone) = values[position]

        holder.name.text = name
        holder.phone.text = phone
        holder.image.text = name[0].toString()

        val background = AppCompatResources.getDrawable(context, R.drawable.contact_image)
        val newBackground = background?.let { DrawableCompat.wrap(it) }
        newBackground?.let { DrawableCompat.setTint(it, colors[Random.nextInt(0, 6)]) }
        holder.image.background = newBackground

        holder.contact.setOnClickListener {
            val callIntent = Intent(Intent.ACTION_DIAL)
            callIntent.data = Uri.parse("tel:$phone")
            context.startActivity(callIntent)
        }
    }

    override fun getItemCount(): Int {
        return values.size
    }
}