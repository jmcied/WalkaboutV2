package ie.wit.walkabout.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ie.wit.walkabout.R
import ie.wit.walkabout.databinding.CardWalkBinding
import ie.wit.walkabout.models.WalkaboutModel

interface WalkClickListener {
    fun onWalkClick(walk: WalkaboutModel)}

class WalkAdapter constructor(private var walks: List<WalkaboutModel>,
                              private val listener: WalkClickListener)
    : RecyclerView.Adapter<WalkAdapter.MainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        val binding = CardWalkBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return MainHolder(binding)
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val walk = walks[holder.adapterPosition]
        holder.bind(walk,listener)
    }

    override fun getItemCount(): Int = walks.size

    inner class MainHolder(val binding : CardWalkBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(walk: WalkaboutModel, listener: WalkClickListener) {
/*          binding.walkTitle.text = walk.title
            binding.difficulty.text = walk.difficulty.toString()
            binding.terrain.text = walk.terrain*/
            //binding.root.tag = walk._id
            binding.walk = walk
            binding.imageIcon.setImageResource(R.mipmap.ic_launcher_round)
            binding.root.setOnClickListener { listener.onWalkClick(walk) }
            binding.executePendingBindings()
        }
    }
}