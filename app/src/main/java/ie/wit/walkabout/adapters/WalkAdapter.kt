package ie.wit.walkabout.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import ie.wit.walkabout.databinding.CardWalkBinding
import ie.wit.walkabout.models.WalkaboutModel
import ie.wit.walkabout.utils.customTransformation

interface WalkClickListener {
    fun onWalkClick(walk: WalkaboutModel)}

class WalkAdapter constructor(private var walks: ArrayList<WalkaboutModel>,
                              private val listener: WalkClickListener,
                              private val readOnly: Boolean)
    : RecyclerView.Adapter<WalkAdapter.MainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        val binding = CardWalkBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return MainHolder(binding,readOnly)
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val walk = walks[holder.adapterPosition]
        holder.bind(walk,listener)
    }

    fun removeAt(position: Int) {
        walks.removeAt(position)
        notifyItemRemoved(position)
    }
    override fun getItemCount(): Int = walks.size

    inner class MainHolder(val binding : CardWalkBinding, private val readOnly : Boolean) :
                            RecyclerView.ViewHolder(binding.root) {

        val readOnlyRow = readOnly

        fun bind(walk: WalkaboutModel, listener: WalkClickListener) {
            binding.root.tag = walk
            binding.walk = walk
            Picasso.get().load(walk.profilepic.toUri())
                .resize(200, 200)
                .transform(customTransformation())
                .centerCrop()
                .into(binding.imageIcon)
            binding.root.setOnClickListener { listener.onWalkClick(walk) }
            binding.executePendingBindings()
        }
    }
}