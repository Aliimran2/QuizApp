package com.miassolutions.quiztime.ui

import android.text.Layout
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.miassolutions.quiztime.data.QuizModel
import com.miassolutions.quiztime.databinding.ItemQuizBinding

class QuizListAdapter(private val navToQuiz : (QuizModel) -> Unit) : RecyclerView.Adapter<QuizListAdapter.QuizViewHolder>() {

    private var quizList = mutableListOf<QuizModel>()

    fun submit(newList: MutableList<QuizModel>) {
        quizList = newList
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): QuizViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return QuizViewHolder(ItemQuizBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(
        holder: QuizViewHolder,
        position: Int,
    ) {
        val currentItem = quizList[position]
        holder.bind(currentItem)

    }

    override fun getItemCount(): Int {
        return quizList.size
    }

   inner class QuizViewHolder(private val binding: ItemQuizBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: QuizModel) {
            binding.apply {
                tvQuizTitle.text = item.title
                tvQuizSubtitle.text = item.subtitle
                tvTime.text = "${item.timer} min"

                root.setOnClickListener { navToQuiz(item) }
            }
        }
    }
}