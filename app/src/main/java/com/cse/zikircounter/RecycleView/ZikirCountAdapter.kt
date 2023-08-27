import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.cse.zikircounter.R
import com.cse.zikircounter.RoomDB.ZikirCount
import com.cse.zikircounter.RoomDB.ZikirDatabase


class ZikirCountAdapter(
    private var dataList: List<ZikirCount>,
    private val db: ZikirDatabase,
    var listener: Listener,
) : RecyclerView.Adapter<ZikirCountAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_zikir_count, parent, false)
        return ViewHolder(view)
    }

    @SuppressLint("SuspiciousIndentation")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = dataList[position]
        holder.bind(item)


        val DeleteBTN = holder.itemView.findViewById<ImageButton>(R.id.delete)

        DeleteBTN.setOnClickListener(View.OnClickListener {

            val deletedItem = dataList[position] // Get the item being deleted
            db.getZikirDao().deleteItemByName(deletedItem.name)

            val mutableDataList = dataList.toMutableList() // Convert to mutable list
            mutableDataList.removeAt(position)
            dataList = mutableDataList.toList() // Convert back to immutable list if needed
            notifyItemRemoved(position)
            notifyItemRangeChanged(position, itemCount)
        })



        holder.itemView.setOnClickListener {
            listener.zikirClick(item)
        }
    }
    

    override fun getItemCount(): Int {
        return dataList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: ZikirCount) {
            itemView.findViewById<TextView>(R.id.nameTextView).text = item.name
            itemView.findViewById<TextView>(R.id.numberTextView).text = item.number.toString()
            itemView.findViewById<ImageButton>(R.id.delete)
        }
    }

    interface Listener {
        fun zikirClick(zikircount: ZikirCount)
    }

}




